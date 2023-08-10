package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.ImagenesForm;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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
  ImagenesForm imagenesFormMocked;
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
  @InjectMocks
  ImagenesAction imagenesAction;

  public static Stream<Arguments> executeActionSource() {
    //then
    String action = "";
    String accionInicializar = "inicializar";

    ImagenesForm imagenesFormEmptyAccion = new ImagenesForm();
    imagenesFormEmptyAccion.setAction(action);
    ImagenesForm imagenesFormInicializar = new ImagenesForm();
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
    //then
    String approvedTrue = "true";
    String approvedFalse = "false";
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList= new ArrayList<>();
    rendicionList.add(rendicion);

    return Stream.of(
        Arguments.of(rendicionList, approvedTrue), // sessionUserWorking lanza NPE
        Arguments.of(rendicionList, approvedFalse) // sessionUserWorking lanza NPE
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute the action")
  void shouldExecuteTheAction(ImagenesForm imagenesForm, List<Archivo> archivoList) throws Exception {
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
    when(imagenesFormMocked.getAction()).thenReturn("cargarArchivo");
    when(imagenesFormMocked.getArchivo()).thenReturn(formFileMocked);
    when(formFileMocked.getFileName()).thenReturn("testFile.txt");
    when(formFileMocked.getFileData()).thenReturn(new byte[]{1, 2, 3, 4, 5, 6});
    when(formFileMocked.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5, 6}));
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = imagenesAction.executeAction(actionMappingMocked, imagenesFormMocked, samWebApplicationMocked, samWebClientMocked,
        httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }

  @Test
  @DisplayName("Should write error when execute the action cargarArchivo")
  void shouldWriteErrorWhenExecuteTheActionCargarArchivo() throws Exception {
    //when
    when(imagenesFormMocked.getAction()).thenReturn("cargarArchivo");
    when(imagenesFormMocked.getArchivo()).thenReturn(formFileMocked);
    when(formFileMocked.getFileName()).thenReturn("testFile.pdf");
    when(formFileMocked.getFileData()).thenReturn(new byte[]{1, 2, 3, 4, 5, 6});
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = imagenesAction.executeAction(actionMappingMocked, imagenesFormMocked, samWebApplicationMocked, samWebClientMocked,
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
    when(imagenesFormMocked.getAction()).thenReturn("borrarArchivo");
    when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn("testFile.txt");
    when(imagenesFormMocked.getArchivosASubir()).thenReturn(archivoList);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = imagenesAction.executeAction(actionMappingMocked, imagenesFormMocked, samWebApplicationMocked, samWebClientMocked,
        httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }

  @ParameterizedTest
  @MethodSource("executeActionGenerarSource")
  @DisplayName("Should execute the action generar")
  void shouldExecuteTheActionGenerar(List<Archivo> archivoList, String aprobado) throws Exception {
    //when
    when(imagenesFormMocked.getAction()).thenReturn("generar");
    when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn("testFile.txt");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("rendicion.aviso.rename.archivo")).thenReturn("newTestFileName.txt");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("");
    when(httpServletRequestMocked.getParameter("esAprobacion")).thenReturn(aprobado);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(archivoList);
        })) {
      //then
      ActionForward actionForwardToAssert = imagenesAction.executeAction(actionMappingMocked, imagenesFormMocked, samWebApplicationMocked, samWebClientMocked,
          httpServletRequestMocked, httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }
}
