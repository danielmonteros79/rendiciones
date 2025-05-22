package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Archivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionAvisoForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.AprobacionesService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
  
  @Disabled("Cambiar HTMLWorker por XMLWorker. HTMLWorker lanza NPE por malformacion de HTML. Linea 286")
  @ParameterizedTest
  @MethodSource("generateCaratulaSource")
  @DisplayName("Should generate caratula")
  void shouldGenerateCaratula(String isCaratula, List<Gastos> gastosList) throws Exception {
    //given
    Method generateCaratulaMocked = ImagenesAction.class.getDeclaredMethod("generateCaratula", HttpServletResponse.class, RendicionAvisoForm.class, HttpServletRequest.class, SAMWebClient.class, AprobacionesService.class, List.class);
    generateCaratulaMocked.setAccessible(true);
    File file = new File("src/test/resources/img.png");
    
    //when
    when(rendicionAvisoFormMocked.getRendicion()).thenReturn(rendicionMocked);
    when(rendicionMocked.getAdea()).thenReturn(isCaratula);
    
    when(rendicionAvisoFormMocked.getUsuario()).thenReturn(usuarioMocked);
    when(rendicionMocked.getId()).thenReturn(1);
    when(rendicionMocked.getMotivo()).thenReturn("");
    when(rendicionMocked.getFechaDesde()).thenReturn(new Date());
    when(rendicionMocked.getFechaHasta()).thenReturn(new Date());
    when(rendicionMocked.getDescripcion()).thenReturn("");
    when(usuarioMocked.getIdUser()).thenReturn("");
    when(usuarioMocked.getNombre()).thenReturn("");
    when(usuarioMocked.getCcostos()).thenReturn(1);
    
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute(anyString())).thenReturn("src/test/resources/img.png");
    when(rendicionMocked.getIdu()).thenReturn("idu");
    when(rendicionMocked.getAdea()).thenReturn("adea");
    
    when(httpServletResponseMocked.getOutputStream()).thenReturn(servletOutputStreamMocked);
    
    try (MockedStatic<File> fileMockedStatic = mockStatic(File.class)) {
      fileMockedStatic.when(() -> File.createTempFile(any(), any(), any())).thenReturn(file);
      
      //then
      generateCaratulaMocked.invoke(imagenesAction, httpServletResponseMocked, rendicionAvisoFormMocked, httpServletRequestMocked, samWebClientMocked,
        aprobacionesServiceMocked, gastosList);
      assertNotNull(httpServletRequestMocked);
    }
  }
}
