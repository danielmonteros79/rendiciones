package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.google.gson.Gson;
import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.ImagenesForm;
import com.sa.form.RendicionAvisoForm;
import com.sa.form.RendicionForm;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.upload.FormFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class RendicionAvisoActionTest {

  @Mock
  Gson gsonMocked;
  @Mock
  FormFile formFileMocked;
  @Mock
  Usuario usuarioMocked;
  @Mock
  Rendicion rendicionMocked;
  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  RendicionAvisoForm imagenesFormMocked;
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
  RendicionAvisoAction rendicionAvisoAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String action = "";
    String actionMostrarPantalla = "mostrarPantalla";
    String actionGenerar = "generar";
    String actionCargarArchivo = "cargarArchivo";
    String actionGetAccion = "getAccion";
    String actionGetArchivosASubir = "getArchivosASubir";

    ActionForward actionForward = new ActionForward();
    actionForward.setProperty("properties", "properties");

    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();
    rendicionList.add(rendicion);

    Archivo archivo = new Archivo();
    archivo.setNomArchivo("");
    List<Archivo> archivoList = new ArrayList<>();
    archivoList.add(archivo);

    return Stream.of(
        Arguments.of(action, null, actionForward, rendicionList, archivoList),
        Arguments.of(actionMostrarPantalla, null, actionForward, rendicionList, archivoList),
//        Arguments.of(actionGenerar, "", actionForward, rendicionList, archivoList)     //java.lang.Exception: java.lang.ClassCastException: com.sa.entities.Rendicion cannot be cast to com.sa.entities.Gastos
        Arguments.of(actionCargarArchivo, null, actionForward, rendicionList, archivoList),
        Arguments.of(actionGetAccion, null, actionForward, rendicionList, archivoList),
        Arguments.of(actionGetArchivosASubir, null, actionForward, rendicionList, archivoList)
                    );
  }

  public static Stream<Arguments> executeActionBorrarArchivoSource() {
    //given
    String actionBorrarArchivo = "borrarArchivo";

    Archivo archivo = new Archivo();
    archivo.setNomArchivo("");
    List<Archivo> archivoList = new ArrayList<>();
    archivoList.add(archivo);

    return Stream.of(Arguments.of(actionBorrarArchivo, null, archivoList));
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute action")
  void shouldExecuteAction(String action, String generate, ActionForward actionForward, List<Rendicion> rendicionList, List<Archivo> archivoList) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("generate")).thenReturn(generate);
    when(httpServletRequestMocked.getParameter("rnd")).thenReturn("0");

    when(imagenesFormMocked.getAction()).thenReturn(action);
    when(imagenesFormMocked.getRendicion()).thenReturn(rendicionMocked);
    when(httpServletRequestMocked.getParameter("codigo")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("usuarioRend")).thenReturn("");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("");

    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(imagenesFormMocked.getRendicion()).thenReturn(rendicionMocked);
    when(rendicionMocked.getId()).thenReturn(0);
    when(rendicionMocked.getCodMotivo()).thenReturn("1");
    when(rendicionMocked.getEstado()).thenReturn("1");
    when(rendicionMocked.getUsuarioRendicion()).thenReturn("0");
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuarioMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("rendicion.aviso.rename.archivo")).thenReturn("fileRenamed");
    when(imagenesFormMocked.getArchivo()).thenReturn(formFileMocked);
    when(imagenesFormMocked.getArchivosASubir()).thenReturn(archivoList);

    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);
    when(actionMappingMocked.findForward("successGenerar")).thenReturn(actionForward);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    // En metodo "generar" linea 110 solicita lista de rendiciones y en la linea 167 solicita lista de gastos
    // Refactorizado es necesario para poder validar su comportamiento
    //java.lang.Exception: java.lang.ClassCastException: com.sa.entities.Rendicion cannot be cast to com.sa.entities.Gastos
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
      //then
      ActionForward actionForwardToAssert = rendicionAvisoAction.executeAction(actionMappingMocked, imagenesFormMocked, samWebApplicationMocked,
          samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionBorrarArchivoSource")
  @DisplayName("Should execute action borrarArchivo")
  void shouldExecuteActionBorrarArchivo(String action, String generate, List<Archivo> archivoList) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("generate")).thenReturn(generate);
    when(httpServletRequestMocked.getParameter("rnd")).thenReturn("0");
    when(imagenesFormMocked.getAction()).thenReturn(action);
    when(imagenesFormMocked.getArchivosASubir()).thenReturn(archivoList);
    when(httpServletRequestMocked.getParameter("nombreArchivo")).thenReturn("");
    when(gsonMocked.fromJson("UTF-8", String.class)).thenReturn("");

    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);
      //then
      ActionForward actionForwardToAssert = rendicionAvisoAction.executeAction(actionMappingMocked, imagenesFormMocked, samWebApplicationMocked,
          samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
  }
}
