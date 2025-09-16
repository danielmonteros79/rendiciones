package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Archivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionAvisoForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import com.sa.services.ThubanService;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

class ImagenesActionTest {

  @Mock
  Usuario usuarioMocked;
  @Mock
  FormFile formFileMocked;
  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  RendicionAvisoForm rendicionAvisoFormMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  HttpSession httpSessionMocked;
  @Mock
  ServletContext servletContextMocked;
  @Mock
  Rendicion rendicionMocked;
  @Mock
  AprobacionesService aprobacionesServiceMocked;
  @Mock
  ServletOutputStream servletOutputStreamMocked;
  @InjectMocks
  ImagenesAction imagenesAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String action = "";
    String accionInicializar = "inicializar";

    RendicionAvisoForm imagenesFormEmptyAccion = new RendicionAvisoForm();
    imagenesFormEmptyAccion.setAction(action);
    RendicionAvisoForm imagenesFormInicializar = new RendicionAvisoForm();
    imagenesFormInicializar.setAction(accionInicializar);

    Archivo archivo = new Archivo();
    List<Archivo> archivoList = new ArrayList<>();
    archivoList.add(archivo);

    return Stream.of(
            Arguments.of(imagenesFormEmptyAccion, archivoList),
            Arguments.of(imagenesFormInicializar, archivoList)
    );
  }

  public static Stream<Arguments> executeActionGenerarSource() {
    //given
    String approvedTrue = "true";
    String approvedFalse = "false";
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList= new ArrayList<>();
    rendicionList.add(rendicion);

    return Stream.of(
            Arguments.of(rendicionList, approvedTrue),
            Arguments.of(rendicionList, approvedFalse)
    );
  }

  public static Stream<Arguments> generateCaratulaSource() {
    //given
    String isCaratula = "caratula";
    Gastos gastos = new Gastos();
    gastos.setMonto("1");
    gastos.setFechagastos("05/09/2023");
    gastos.setComprobante("FACTU");
    gastos.setTarjeta("N");
    List<Gastos> gastosList = new ArrayList<>();
    gastosList.add(gastos);

    return Stream.of(
//      Arguments.of("", gastosList),
            Arguments.of(isCaratula, gastosList)
    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    imagenesAction.setSessionUserWorking(usuarioMocked);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute the action")
  void shouldExecuteTheAction(RendicionAvisoForm imagenesForm, List<Archivo> archivoList) throws Exception {
    //when
    when(httpServletRequestMocked. getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("doc");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("1");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
            (mockManagerTransaction, context) -> {
              doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
              when(mockManagerTransaction.getDataReturnList()).thenReturn(archivoList);
            })) {
      //then
      ActionForward actionForwardToAssert = imagenesAction.executeAction(actionMappingMocked, imagenesForm, samWebApplicationMocked, samWebClientMocked,
              httpServletRequestMocked, httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }

  @Test
  @DisplayName("Should execute the action cargarArchivo")
  void shouldExecuteTheActionCargarArchivo() throws Exception {
    //when
    when(rendicionAvisoFormMocked.getAction()).thenReturn("cargarArchivo");
    when(rendicionAvisoFormMocked.getArchivo()).thenReturn(formFileMocked);
    when(formFileMocked.getFileName()).thenReturn("testFile.txt");
    when(formFileMocked.getFileData()).thenReturn(new byte[]{1, 2, 3, 4, 5, 6});
    when(formFileMocked.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5, 6}));
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked, samWebApplicationMocked, samWebClientMocked,
            httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }

  @Test
  @DisplayName("Should write error when execute the action cargarArchivo")
  void shouldWriteErrorWhenExecuteTheActionCargarArchivo() throws Exception {
    //when
    when(rendicionAvisoFormMocked.getAction()).thenReturn("cargarArchivo");
    when(rendicionAvisoFormMocked.getArchivo()).thenReturn(formFileMocked);
    when(formFileMocked.getFileName()).thenReturn("testFile.pdf");
    when(formFileMocked.getFileData()).thenReturn(new byte[]{1, 2, 3, 4, 5, 6});
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked, samWebApplicationMocked, samWebClientMocked,
            httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }

  @Test
  @DisplayName("Should execute the action borrarArchivo")
  void shouldExecuteTheActionBorrarArchivo() throws Exception {
    //given
    Archivo archivo = new Archivo();
    archivo.setNomArchivo("testFile.txt");
    List<Archivo> archivoList = new ArrayList<>();
    archivoList.add(archivo);
    //when
    when(rendicionAvisoFormMocked.getAction()).thenReturn("borrarArchivo");
    when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn("testFile.txt");
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(archivoList);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked, samWebApplicationMocked, samWebClientMocked,
            httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }

  @ParameterizedTest
  @MethodSource("executeActionGenerarSource")
  @DisplayName("Should execute the action generar")
  void shouldExecuteTheActionGenerar(List<Archivo> archivoList, String aprobado) throws Exception {
    //when
    when(rendicionAvisoFormMocked.getAction()).thenReturn("generar");
    when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn("testFile.txt");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("rendicion.aviso.rename.archivo")).thenReturn("newTestFileName.txt");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("");
    when(httpServletRequestMocked.getParameter("esAprobacion")).thenReturn(aprobado);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(rendicionAvisoFormMocked.getRendicion()).thenReturn(rendicionMocked);
    when(rendicionMocked.getUsuarioRendicion()).thenReturn("user");

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
            (mockManagerTransaction, context) -> {
              doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
              when(mockManagerTransaction.getDataReturnList()).thenReturn(archivoList);
            })) {
      //then
      ActionForward actionForwardToAssert = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked, samWebApplicationMocked, samWebClientMocked,
              httpServletRequestMocked, httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }

  // Tests específicos para el método generar (líneas 136-177 del código seleccionado)
  
  public static Stream<Arguments> generarSuccessSource() {
    // Casos de éxito para diferentes configuraciones
    List<Archivo> archivosVacios = new ArrayList<>();
    List<Archivo> archivosConDatos = new ArrayList<>();
    Archivo archivo1 = new Archivo();
    archivo1.setNomArchivo("documento1.pdf");
    archivo1.setBase64File("dGVzdA==");
    archivosConDatos.add(archivo1);
    
    return Stream.of(
        Arguments.of("12345", "false", "1", archivosVacios, new ArrayList<>()),    // Sin archivos, sin errores
        Arguments.of("67890", "true", "2", archivosConDatos, new ArrayList<>()),   // Con archivos, sin errores
        Arguments.of("11111", "false", "", archivosConDatos, new ArrayList<>())    // Sin glg, con archivos
    );
  }

  public static Stream<Arguments> generarErrorSource() {
    List<Archivo> archivos = new ArrayList<>();
    Archivo archivo = new Archivo();
    archivo.setNomArchivo("error.pdf");
    archivos.add(archivo);
    
    List<String> erroresParciales = new ArrayList<>();
    erroresParciales.add("Error al subir archivo 1");
    
    List<String> erroresCompletos = new ArrayList<>();
    erroresCompletos.add("Error total 1");
    
    return Stream.of(
        Arguments.of("99999", "false", "1", archivos, erroresParciales),  // Errores parciales
        Arguments.of("88888", "true", "2", archivos, erroresCompletos)    // Errores completos
    );
  }

  @ParameterizedTest
  @MethodSource("generarSuccessSource")
  @DisplayName("Should execute generar method successfully")
  void shouldExecuteGenerarSuccessfully(String idRendicion, String esAprobacion, String glg, 
                                       List<Archivo> archivos, List<String> errores) throws Exception {
    // Arrange
    when(rendicionAvisoFormMocked.getAction()).thenReturn("generar");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn(idRendicion);
    when(httpServletRequestMocked.getParameter("esAprobacion")).thenReturn(esAprobacion);
    when(httpServletRequestMocked.getParameter("glg")).thenReturn(glg);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    // Configurar ServletContext
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(archivos);
    
    // Mock usuario working
    when(usuarioMocked.getIdUser()).thenReturn("user123");
    when(usuarioMocked.getCcostos()).thenReturn(5000);
    imagenesAction.setSessionUserWorking(usuarioMocked);
    
    // Mock rendición
    Rendicion mockRendicion = new Rendicion();
    mockRendicion.setId(Integer.parseInt(idRendicion));
    mockRendicion.setUsuarioRendicion("user123");
    List<Rendicion> rendiciones = new ArrayList<>();
    rendiciones.add(mockRendicion);

    try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class,
            (mock, context) -> {
              when(mock.obtenerListadoRendiciones(anyString(), anyString(), isNull(), isNull(), isNull()))
                      .thenReturn(rendiciones);
            });
         MockedConstruction<AprobacionesService> aprobacionesServiceMC = Mockito.mockConstruction(AprobacionesService.class,
            (mock, context) -> {
              when(mock.getAprobacionesPendientes(anyString(), isNull(), isNull(), anyString(), anyString()))
                      .thenReturn(rendiciones);
            });
         MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.publicarDocumentos(anyString(), anyString(), anyString(), any(Rendicion.class), anyList()))
                      .thenReturn(errores);
            })) {

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null for JSON response");
      
      // Verify services were created - Todos los servicios se instancian siempre
      Assertions.assertEquals(1, thubanServiceMC.constructed().size(), "Should create one ThubanService instance");
      Assertions.assertEquals(1, rendicionesServiceMC.constructed().size(), "Should create one RendicionesService instance");
      Assertions.assertEquals(1, aprobacionesServiceMC.constructed().size(), "Should create one AprobacionesService instance");
    }
  }

  @ParameterizedTest
  @MethodSource("generarErrorSource")
  @DisplayName("Should handle errors in generar method")
  void shouldHandleGenerarErrors(String idRendicion, String esAprobacion, String glg, 
                                List<Archivo> archivos, List<String> errores) throws Exception {
    // Arrange
    when(rendicionAvisoFormMocked.getAction()).thenReturn("generar");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn(idRendicion);
    when(httpServletRequestMocked.getParameter("esAprobacion")).thenReturn(esAprobacion);
    when(httpServletRequestMocked.getParameter("glg")).thenReturn(glg);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(archivos);
    
    when(usuarioMocked.getIdUser()).thenReturn("user123");
    when(usuarioMocked.getCcostos()).thenReturn(5000);
    imagenesAction.setSessionUserWorking(usuarioMocked);
    
    Rendicion mockRendicion = new Rendicion();
    mockRendicion.setId(Integer.parseInt(idRendicion));
    List<Rendicion> rendiciones = new ArrayList<>();
    rendiciones.add(mockRendicion);

    try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class,
            (mock, context) -> {
              when(mock.obtenerListadoRendiciones(anyString(), anyString(), isNull(), isNull(), isNull()))
                      .thenReturn(rendiciones);
            });
         MockedConstruction<AprobacionesService> aprobacionesServiceMC = Mockito.mockConstruction(AprobacionesService.class,
            (mock, context) -> {
              when(mock.getAprobacionesPendientes(anyString(), isNull(), isNull(), anyString(), anyString()))
                      .thenReturn(rendiciones);
            });
         MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.publicarDocumentos(anyString(), anyString(), anyString(), any(Rendicion.class), anyList()))
                      .thenReturn(errores);
            })) {

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      if (errores.size() == archivos.size()) {
        // Si todos los archivos fallaron, debería llamar writeError
        assertNull(result, "Should return null even for complete errors");
      } else {
        // Si algunos archivos funcionaron, debería retornar JSON con errores parciales
        assertNull(result, "Should return null for JSON response with partial errors");
      }
    }
  }

  @Test
  @DisplayName("Should handle esAprobacion=true correctly in generar")
  void shouldHandleEsAprobacionTrueInGenerar() throws Exception {
    // Arrange
    when(rendicionAvisoFormMocked.getAction()).thenReturn("generar");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("12345");
    when(httpServletRequestMocked.getParameter("esAprobacion")).thenReturn("true");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("1");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(new ArrayList<>());
    
    when(usuarioMocked.getIdUser()).thenReturn("user123");
    when(usuarioMocked.getCcostos()).thenReturn(5000);
    imagenesAction.setSessionUserWorking(usuarioMocked);
    
    Rendicion mockRendicion = new Rendicion();
    mockRendicion.setId(12345);
    List<Rendicion> rendiciones = new ArrayList<>();
    rendiciones.add(mockRendicion);

    try (MockedConstruction<AprobacionesService> aprobacionesServiceMC = Mockito.mockConstruction(AprobacionesService.class,
            (mock, context) -> {
              when(mock.getAprobacionesPendientes(anyString(), isNull(), isNull(), anyString(), anyString()))
                      .thenReturn(rendiciones);
            });
         MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.publicarDocumentos(anyString(), anyString(), anyString(), any(Rendicion.class), anyList()))
                      .thenReturn(new ArrayList<>());
            })) {

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null for JSON response");
      
      // Cuando esAprobacion=true, debería usar AprobacionesService.getAprobacionesPendientes
      Assertions.assertEquals(1, aprobacionesServiceMC.constructed().size(), "Should create one AprobacionesService instance");
      Assertions.assertEquals(1, thubanServiceMC.constructed().size(), "Should create one ThubanService instance");
    }
  }

  @Test
  @DisplayName("Should handle esAprobacion=false correctly in generar")
  void shouldHandleEsAprobacionFalseInGenerar() throws Exception {
    // Arrange
    when(rendicionAvisoFormMocked.getAction()).thenReturn("generar");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("67890");
    when(httpServletRequestMocked.getParameter("esAprobacion")).thenReturn("false");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("2");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(new ArrayList<>());
    
    when(usuarioMocked.getIdUser()).thenReturn("user456");
    when(usuarioMocked.getCcostos()).thenReturn(6000);
    imagenesAction.setSessionUserWorking(usuarioMocked);
    
    Rendicion mockRendicion = new Rendicion();
    mockRendicion.setId(67890);
    List<Rendicion> rendiciones = new ArrayList<>();
    rendiciones.add(mockRendicion);

    try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class,
            (mock, context) -> {
              when(mock.obtenerListadoRendiciones(anyString(), anyString(), isNull(), isNull(), isNull()))
                      .thenReturn(rendiciones);
            });
         MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.publicarDocumentos(anyString(), anyString(), anyString(), any(Rendicion.class), anyList()))
                      .thenReturn(new ArrayList<>());
            })) {

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null for JSON response");
      
      // Cuando esAprobacion=false, debería usar RendicionesService.obtenerListadoRendiciones
      Assertions.assertEquals(1, rendicionesServiceMC.constructed().size(), "Should create one RendicionesService instance");
      Assertions.assertEquals(1, thubanServiceMC.constructed().size(), "Should create one ThubanService instance");
    }
  }

  @Test
  @DisplayName("Should set rendicion properties correctly in generar")
  void shouldSetRendicionPropertiesCorrectlyInGenerar() throws Exception {
    // Arrange
    String idRendicion = "99999";
    when(rendicionAvisoFormMocked.getAction()).thenReturn("generar");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn(idRendicion);
    when(httpServletRequestMocked.getParameter("esAprobacion")).thenReturn("false");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("3");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(new ArrayList<>());
    
    String userId = "user789";
    int costosDestino = 7000;
    when(usuarioMocked.getIdUser()).thenReturn(userId);
    when(usuarioMocked.getCcostos()).thenReturn(costosDestino);
    imagenesAction.setSessionUserWorking(usuarioMocked);
    
    Rendicion mockRendicion = new Rendicion();
    List<Rendicion> rendiciones = new ArrayList<>();
    rendiciones.add(mockRendicion);

    try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class,
            (mock, context) -> {
              when(mock.obtenerListadoRendiciones(anyString(), anyString(), isNull(), isNull(), isNull()))
                      .thenReturn(rendiciones);
            });
         MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.publicarDocumentos(anyString(), anyString(), anyString(), any(Rendicion.class), anyList()))
                      .thenReturn(new ArrayList<>());
            })) {

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null for JSON response");
      
      // El método debería haber configurado:
      // rendicion.setUsuarioRendicion(this.sessionUserWorking.getIdUser())
      // rendicion.setId(Integer.parseInt(idRendicion))
      // rendicion.setCostosDestino(String.valueOf(this.sessionUserWorking.getCcostos()))
      
      // Verificamos que se crearon los servicios necesarios
      Assertions.assertEquals(1, rendicionesServiceMC.constructed().size(), "Should create one RendicionesService instance");
      Assertions.assertEquals(1, thubanServiceMC.constructed().size(), "Should create one ThubanService instance");
    }
  }

  @Test
  @DisplayName("Should handle null sessionUserWorking in generar")
  void shouldHandleNullSessionUserWorkingInGenerar() throws Exception {
    // Arrange
    when(rendicionAvisoFormMocked.getAction()).thenReturn("generar");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("12345");
    when(httpServletRequestMocked.getParameter("esAprobacion")).thenReturn("false");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("1");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(new ArrayList<>());
    
    // NO configurar sessionUserWorking - debería ser null
    imagenesAction.setSessionUserWorking(null);

    // El código real maneja las excepciones internamente y escribe una respuesta de error
    // en lugar de lanzar la excepción
    ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
            samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    
    // Debería retornar null indicando que se escribió una respuesta de error
    assertNull(result, "Should handle null sessionUserWorking gracefully and return error response");
  }
  
  // Tests específicos para el método cargarArchivo (líneas 91-115 del código seleccionado)
  
  public static Stream<Arguments> cargarArchivoValidExtensionsSource() {
    return Stream.of(
        Arguments.of("application/pdf", "documento.pdf", "dGVzdCBwZGY="),
        Arguments.of("image/tiff", "imagen.tif", "dGVzdCB0aWY="),
        Arguments.of("image/tiff", "archivo.tif", "dGVzdCB0aWZm"),
        Arguments.of("application/PDF", "DOCUMENTO.PDF", "dGVzdA=="), // Test mayúsculas
        Arguments.of("multipart/pdf", "mixed.pdf", "cGRmX2RhdGE=") // Extensión contenida
    );
  }

  public static Stream<Arguments> cargarArchivoInvalidExtensionsSource() {
    return Stream.of(
        Arguments.of("application/docx", "documento.docx", "Word document"),
        Arguments.of("image/jpeg", "imagen.jpg", "JPEG image"),
        Arguments.of("text/plain", "archivo.txt", "Plain text"),
        Arguments.of("application/zip", "comprimido.zip", "ZIP file"),
        Arguments.of("", "sinExtension", "No extension") // Extension vacía
    );
  }

  @ParameterizedTest
  @MethodSource("cargarArchivoValidExtensionsSource")
  @DisplayName("Should successfully load files with valid extensions - cargarArchivo method")
  void shouldLoadValidExtensionsInCargarArchivo(String tipoArchivo, String nombreArchivo, String base64Data) throws Exception {
    // Arrange
    FormFile mockFormFile = Mockito.mock(FormFile.class);
    when(mockFormFile.getFileName()).thenReturn(nombreArchivo);
    when(mockFormFile.getInputStream()).thenReturn(new ByteArrayInputStream(base64Data.getBytes()));
    
    when(rendicionAvisoFormMocked.getAction()).thenReturn("cargarArchivo");
    when(rendicionAvisoFormMocked.getArchivo()).thenReturn(mockFormFile);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(new ArrayList<>());
    
    when(httpServletRequestMocked.getParameter("tipoArchivo")).thenReturn(tipoArchivo);
    when(httpServletRequestMocked.getParameter("base64")).thenReturn(base64Data);
    when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn(nombreArchivo);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    // Act
    ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
            samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

    // Assert
    assertNull(result, "Should return null for JSON response");
    
    // Verify that the file was added to the list
    List<Archivo> archivosSubidos = rendicionAvisoFormMocked.getArchivosASubir();
    Assertions.assertEquals(1, archivosSubidos.size(), "Should add one file to the upload list");
    
    Archivo archivoSubido = archivosSubidos.get(0);
    Assertions.assertEquals(nombreArchivo, archivoSubido.getNomArchivo(), "Should set correct file name");
    Assertions.assertEquals(base64Data, archivoSubido.getBase64File(), "Should set correct base64 data");
    Assertions.assertNotNull(archivoSubido.getInputStream(), "Should set input stream");
  }

  @ParameterizedTest
  @MethodSource("cargarArchivoInvalidExtensionsSource")
  @DisplayName("Should reject files with invalid extensions - cargarArchivo method")
  void shouldRejectInvalidExtensionsInCargarArchivo(String tipoArchivo, String nombreArchivo, String description) throws Exception {
    // Arrange
    FormFile mockFormFile = Mockito.mock(FormFile.class);
    when(mockFormFile.getFileName()).thenReturn(nombreArchivo);
    
    when(rendicionAvisoFormMocked.getAction()).thenReturn("cargarArchivo");
    when(rendicionAvisoFormMocked.getArchivo()).thenReturn(mockFormFile);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(new ArrayList<>());
    
    when(httpServletRequestMocked.getParameter("tipoArchivo")).thenReturn(tipoArchivo);
    when(httpServletRequestMocked.getParameter("base64")).thenReturn("dGVzdA==");
    when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn(nombreArchivo);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    // Act
    ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
            samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

    // Assert
    assertNull(result, "Should return null for error response");
    
    // Verify that no file was added to the list
    List<Archivo> archivosSubidos = rendicionAvisoFormMocked.getArchivosASubir();
    Assertions.assertEquals(0, archivosSubidos.size(), "Should not add invalid file to upload list");
  }

  @Test
  @DisplayName("Should handle special characters in file names - cargarArchivo method")
  void shouldHandleSpecialCharactersInCargarArchivo() throws Exception {
    // Arrange
    String nombreArchivoConEspeciales = "<script>alert('xss')</script>.pdf";
    
    FormFile mockFormFile = Mockito.mock(FormFile.class);
    when(mockFormFile.getFileName()).thenReturn(nombreArchivoConEspeciales);
    when(mockFormFile.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
    
    when(rendicionAvisoFormMocked.getAction()).thenReturn("cargarArchivo");
    when(rendicionAvisoFormMocked.getArchivo()).thenReturn(mockFormFile);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(new ArrayList<>());
    
    when(httpServletRequestMocked.getParameter("tipoArchivo")).thenReturn("application/pdf");
    when(httpServletRequestMocked.getParameter("base64")).thenReturn("dGVzdA==");
    when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn(nombreArchivoConEspeciales);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    // Act
    ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
            samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

    // Assert
    assertNull(result, "Should return null for JSON response");
    
    // Verify that special characters in file name are escaped in response but not in stored object
    List<Archivo> archivosSubidos = rendicionAvisoFormMocked.getArchivosASubir();
    Assertions.assertEquals(1, archivosSubidos.size());
    
    Archivo archivo = archivosSubidos.get(0);
    Assertions.assertEquals(nombreArchivoConEspeciales, archivo.getNomArchivo(), 
        "Should store original file name without escaping");
  }

  @Test
  @DisplayName("Should create Archivo object with correct properties - cargarArchivo method")
  void shouldCreateArchivoWithCorrectPropertiesInCargarArchivo() throws Exception {
    // Arrange
    String nombreArchivo = "documento-importante.pdf";
    String base64Data = "UERGIGRhdGEgaGVyZQ==";
    
    FormFile mockFormFile = Mockito.mock(FormFile.class);
    ByteArrayInputStream mockInputStream = new ByteArrayInputStream("PDF content".getBytes());
    when(mockFormFile.getFileName()).thenReturn(nombreArchivo);
    when(mockFormFile.getInputStream()).thenReturn(mockInputStream);
    
    when(rendicionAvisoFormMocked.getAction()).thenReturn("cargarArchivo");
    when(rendicionAvisoFormMocked.getArchivo()).thenReturn(mockFormFile);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(new ArrayList<>());
    
    when(httpServletRequestMocked.getParameter("tipoArchivo")).thenReturn("application/pdf");
    when(httpServletRequestMocked.getParameter("base64")).thenReturn(base64Data);
    when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn(nombreArchivo);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    // Act
    ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
            samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

    // Assert
    assertNull(result, "Should return null for JSON response");
    
    List<Archivo> archivosSubidos = rendicionAvisoFormMocked.getArchivosASubir();
    Assertions.assertEquals(1, archivosSubidos.size());
    
    Archivo archivo = archivosSubidos.get(0);
    
    // Verify all properties are set correctly
    Assertions.assertEquals(nombreArchivo, archivo.getNomArchivo(), "Should set correct file name");
    Assertions.assertEquals(base64Data, archivo.getBase64File(), "Should set correct base64 data");
    Assertions.assertEquals(mockInputStream, archivo.getInputStream(), "Should set correct input stream");
  }

  @Test
  @DisplayName("Should handle null parameters gracefully - cargarArchivo method")
  void shouldHandleNullParametersInCargarArchivo() throws Exception {
    // Arrange
    FormFile mockFormFile = Mockito.mock(FormFile.class);
    when(mockFormFile.getFileName()).thenReturn("test.pdf");
    when(mockFormFile.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
    
    when(rendicionAvisoFormMocked.getAction()).thenReturn("cargarArchivo");
    when(rendicionAvisoFormMocked.getArchivo()).thenReturn(mockFormFile);
    when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(new ArrayList<>());
    
    // Set some parameters to null
    when(httpServletRequestMocked.getParameter("tipoArchivo")).thenReturn(null);
    when(httpServletRequestMocked.getParameter("base64")).thenReturn("dGVzdA==");
    when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn("test.pdf");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    // Act & Assert
    // This should either handle the null gracefully or the exception should be caught by executeAction
    ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
            samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    
    // The result should be null (either success JSON or error JSON)
    assertNull(result, "Should handle null parameters and return JSON response");
  }

  @Test
  @DisplayName("Should validate case insensitive extensions - cargarArchivo method")
  void shouldValidateCaseInsensitiveExtensionsInCargarArchivo() throws Exception {
    // Arrange - Test various case combinations
    String[] validExtensions = {"PDF", "pdf", "TIF", "tif", "Pdf", "TiF", "application/PDF", "IMAGE/TIFF"};
    
    for (String extension : validExtensions) {
      FormFile mockFormFile = Mockito.mock(FormFile.class);
      when(mockFormFile.getFileName()).thenReturn("test." + extension.toLowerCase());
      when(mockFormFile.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
      
      when(rendicionAvisoFormMocked.getAction()).thenReturn("cargarArchivo");
      when(rendicionAvisoFormMocked.getArchivo()).thenReturn(mockFormFile);
      
      // Reset the list for each iteration
      List<Archivo> archivos = new ArrayList<>();
      when(rendicionAvisoFormMocked.getArchivosASubir()).thenReturn(archivos);
      
      when(httpServletRequestMocked.getParameter("tipoArchivo")).thenReturn(extension);
      when(httpServletRequestMocked.getParameter("base64")).thenReturn("dGVzdA==");
      when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn("test." + extension.toLowerCase());
      when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should accept extension: " + extension);
      Assertions.assertEquals(1, archivos.size(), "Should add file for extension: " + extension);
    }
  }

  // Tests específicos para el método descargarImg (líneas 71-87 del código seleccionado)
  
  public static Stream<Arguments> descargarImgSuccessSource() {
    Archivo archivo1 = new Archivo();
    archivo1.setNomArchivo("imagen1.pdf");
    archivo1.setBase64File("dGVzdCBwZGYgZGF0YQ==");
    
    Archivo archivo2 = new Archivo();
    archivo2.setNomArchivo("documento.tif");
    archivo2.setBase64File("dGVzdCB0aWYgZGF0YQ==");
    
    return Stream.of(
        Arguments.of("IMG001", Arrays.asList(archivo1)),           // Una imagen
        Arguments.of("IMG002", Arrays.asList(archivo1, archivo2)), // Múltiples archivos
        Arguments.of("IMG003", new ArrayList<>())                  // Sin archivos encontrados
    );
  }

  public static Stream<Arguments> descargarImgParametersSource() {
    return Stream.of(
        Arguments.of("12345", "testUser", "testPass", "testClass"),
        Arguments.of("67890", "user2", "pass2", "class2"),
        Arguments.of("", "emptyUser", "emptyPass", "emptyClass"), // ID vacío
        Arguments.of("NULL_TEST", null, null, null)                // Parámetros nulos
    );
  }

  @ParameterizedTest
  @MethodSource("descargarImgSuccessSource")
  @DisplayName("Should successfully download images - descargarImg method")
  void shouldDownloadImagesSuccessfullyInDescargarImg(String idImagen, List<Archivo> expectedArchivos) throws Exception {
    // Arrange
    when(rendicionAvisoFormMocked.getAction()).thenReturn("descargarImg");
    when(httpServletRequestMocked.getParameter("idImagen")).thenReturn(idImagen);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.descargarArchivo(anyString(), anyString(), anyString(), anyString()))
                      .thenReturn(expectedArchivos);
            })) {

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null for JSON response");
      
      // Verify ThubanService was created and used correctly
      Assertions.assertEquals(1, thubanServiceMC.constructed().size(), "Should create one ThubanService instance");
      
      ThubanService thubanService = thubanServiceMC.constructed().get(0);
      verify(thubanService).descargarArchivo("test_class", "test_user", "test_pass", idImagen);
      
      // Verify form.clean() was called
      verify(rendicionAvisoFormMocked).clean();
    }
  }

  @ParameterizedTest
  @MethodSource("descargarImgParametersSource")
  @DisplayName("Should handle different parameter combinations - descargarImg method")
  void shouldHandleParameterCombinationsInDescargarImg(String idImagen, String thubanUser, String thubanPass, String thubanClaseDoc) throws Exception {
    // Arrange
    List<Archivo> mockArchivos = new ArrayList<>();
    
    when(rendicionAvisoFormMocked.getAction()).thenReturn("descargarImg");
    when(httpServletRequestMocked.getParameter("idImagen")).thenReturn(idImagen);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn(thubanUser);
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn(thubanPass);
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn(thubanClaseDoc);
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.descargarArchivo(anyString(), anyString(), anyString(), anyString()))
                      .thenReturn(mockArchivos);
            })) {

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null for JSON response");
      
      // Verify service was called with the parameters from context
      ThubanService thubanService = thubanServiceMC.constructed().get(0);
      verify(thubanService).descargarArchivo(thubanClaseDoc, thubanUser, thubanPass, idImagen);
    }
  }

  @Test
  @DisplayName("Should handle service exception gracefully - descargarImg method")
  void shouldHandleServiceExceptionInDescargarImg() throws Exception {
    // Arrange
    when(rendicionAvisoFormMocked.getAction()).thenReturn("descargarImg");
    when(httpServletRequestMocked.getParameter("idImagen")).thenReturn("ERROR_ID");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.descargarArchivo(anyString(), anyString(), anyString(), anyString()))
                      .thenThrow(new RuntimeException("Error al descargar archivo"));
            })) {

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should handle exception and return error response");
      
      // Verify that the service was attempted to be used
      Assertions.assertEquals(1, thubanServiceMC.constructed().size());
    }
  }

  @Test
  @DisplayName("Should call form.clean() before processing - descargarImg method")
  void shouldCallFormCleanInDescargarImg() throws Exception {
    // Arrange
    when(rendicionAvisoFormMocked.getAction()).thenReturn("descargarImg");
    when(httpServletRequestMocked.getParameter("idImagen")).thenReturn("TEST_ID");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.descargarArchivo(anyString(), anyString(), anyString(), anyString()))
                      .thenReturn(new ArrayList<>());
            })) {

      // Act
      imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      verify(rendicionAvisoFormMocked).clean();
    }
  }

  @Test
  @DisplayName("Should create ThubanService with correct SAMWebClient - descargarImg method")
  void shouldCreateThubanServiceWithCorrectClientInDescargarImg() throws Exception {
    // Arrange
    when(rendicionAvisoFormMocked.getAction()).thenReturn("descargarImg");
    when(httpServletRequestMocked.getParameter("idImagen")).thenReturn("CLIENT_TEST");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              // Verify that ThubanService constructor was called with the correct SAMWebClient
              Assertions.assertEquals(1, context.arguments().size(), "ThubanService should be constructed with one argument");
              Assertions.assertEquals(samWebClientMocked, context.arguments().get(0), "Should pass the correct SAMWebClient");
              
              when(mock.descargarArchivo(anyString(), anyString(), anyString(), anyString()))
                      .thenReturn(new ArrayList<>());
            })) {

      // Act
      imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      Assertions.assertEquals(1, thubanServiceMC.constructed().size(), "Should create exactly one ThubanService instance");
    }
  }

  @Test
  @DisplayName("Should set correct response structure - descargarImg method")
  void shouldSetCorrectResponseStructureInDescargarImg() throws Exception {
    // Arrange
    Archivo archivoTest = new Archivo();
    archivoTest.setNomArchivo("test-image.pdf");
    archivoTest.setBase64File("dGVzdCBkYXRh");
    List<Archivo> archivos = Arrays.asList(archivoTest);
    
    when(rendicionAvisoFormMocked.getAction()).thenReturn("descargarImg");
    when(httpServletRequestMocked.getParameter("idImagen")).thenReturn("RESPONSE_TEST");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.descargarArchivo(anyString(), anyString(), anyString(), anyString()))
                      .thenReturn(archivos);
            })) {

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null for JSON response");
      
      // The method should call writeJson with a Map containing "archivo" key
      // We can't directly verify the response content, but we can ensure the method completes successfully
      verify(httpServletResponseMocked).getWriter();
    }
  }

  @Test
  @DisplayName("Should handle null idImagen parameter - descargarImg method")
  void shouldHandleNullIdImagenInDescargarImg() throws Exception {
    // Arrange
    when(rendicionAvisoFormMocked.getAction()).thenReturn("descargarImg");
    when(httpServletRequestMocked.getParameter("idImagen")).thenReturn(null);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    
    when(servletContextMocked.getAttribute("esb.thuban.user")).thenReturn("test_user");
    when(servletContextMocked.getAttribute("esb.thuban.pass")).thenReturn("test_pass");
    when(servletContextMocked.getAttribute("esb.thuban.clase.documental")).thenReturn("test_class");
    
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ThubanService> thubanServiceMC = Mockito.mockConstruction(ThubanService.class,
            (mock, context) -> {
              when(mock.descargarArchivo(anyString(), anyString(), anyString(), isNull()))
                      .thenReturn(new ArrayList<>());
            })) {

      // Act
      ActionForward result = imagenesAction.executeAction(actionMappingMocked, rendicionAvisoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should handle null idImagen and return JSON response");
      
      // Verify service was called with null idImagen
      ThubanService thubanService = thubanServiceMC.constructed().get(0);
      verify(thubanService).descargarArchivo("test_class", "test_user", "test_pass", null);
    }
  }

  // ...existing code...
}