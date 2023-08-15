package com.sa.action.aprobacion;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AprobacionDetalleActionTest {

  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMock;
  @Mock
  ActionForward actionForwardMock;
  @Mock
  RendicionForm rendicionFormMock;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @InjectMocks
  AprobacionDetalleAction aprobacionDetalleAction;

  public static Stream<Arguments> executeActionHappyTrailSource() {
    //given
    ActionForward actionForward = new ActionForward();
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest requestEmptyAction = new MockHttpServletRequest();
    Rendicion rendicionUsuario = new Rendicion();
    Rendicion rendicionUsuarioEmpty = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();
    List<Rendicion> rendicionListEmpty = new ArrayList<>();
    List<Rendicion> rendicionListUserEmpty = new ArrayList<>();
    List<Usuario> usuarioList = new ArrayList<>();
    Usuario user = new Usuario("", "", "", 1, "", new ArrayList<>());
    usuarioList.add(user);
    Usuario usuario = new Usuario("", "", "", 1, "", usuarioList);

    rendicionUsuario.setUsuarioRendicion("");
    rendicionList.add(rendicionUsuario);
    rendicionListUserEmpty.add(rendicionUsuarioEmpty);

    httpSession.setAttribute("rendicion.link.thuban", "");
    httpSession.setAttribute("userWorking", usuario);

    requestEmptyAction.setHttpSession(httpSession);
    requestEmptyAction.addParameter("glg", "");
    requestEmptyAction.addParameter("codigo", "");

    return Stream.of(Arguments.of(requestEmptyAction, rendicionList, user, actionForward));
  }

  public static Stream<Arguments> executeActionIfElseBlockSource() {
    //given
    String actionGastos = "gastos";
    String actionAprobar = "aprobar";
    String actionRechazar = "rechazar";
    String actionObservar = "observar";
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest requestActionGastos = new MockHttpServletRequest();
    MockHttpServletRequest requestActionAprobar = new MockHttpServletRequest();
    MockHttpServletRequest requestActionRechazar = new MockHttpServletRequest();
    MockHttpServletRequest requestActionObservar = new MockHttpServletRequest();
    List<Usuario> usuarioList = new ArrayList<>();
    Usuario user = new Usuario("", "", "", 1, "", new ArrayList<>());
    usuarioList.add(user);
    Usuario usuario = new Usuario("", "", "", 1, "", usuarioList);

    httpSession.setAttribute("rendicion.link.thuban", "");
    httpSession.setAttribute("userWorking", usuario);

    requestActionGastos.setHttpSession(httpSession);
    requestActionGastos.addParameter("action", "getRendicionGastos");
    requestActionGastos.addParameter("glg", "");
    requestActionGastos.addParameter("codigo", "");

    requestActionAprobar.setHttpSession(httpSession);
    requestActionAprobar.addParameter("action", "aprobar");
    requestActionAprobar.addParameter("glg", "");
    requestActionAprobar.addParameter("codigo", "");
    requestActionAprobar.addParameter("idRendicion", "1");
    requestActionAprobar.addParameter("comentario", "comentario");
    requestActionAprobar.addParameter("descripcion", "descripcion");

    requestActionRechazar.setHttpSession(httpSession);
    requestActionRechazar.addParameter("action", "rechazar");
    requestActionRechazar.addParameter("glg", "");
    requestActionRechazar.addParameter("codigo", "");
    requestActionRechazar.addParameter("idRendicion", "1");
    requestActionRechazar.addParameter("comentario", "comentario");
    requestActionRechazar.addParameter("descripcion", "descripcion");

    requestActionObservar.setHttpSession(httpSession);
    requestActionObservar.addParameter("action", "observar");
    requestActionObservar.addParameter("glg", "");
    requestActionObservar.addParameter("codigo", "");
    requestActionObservar.addParameter("idRendicion", "1");
    requestActionObservar.addParameter("comentario", "comentario");
    requestActionObservar.addParameter("descripcion", "descripcion");

    return Stream.of(
        Arguments.of(requestActionGastos, actionGastos, usuario),
        Arguments.of(requestActionAprobar, actionAprobar, usuario),
        Arguments.of(requestActionRechazar, actionRechazar, usuario),
        Arguments.of(requestActionObservar, actionObservar, usuario)
                    );
  }

  public static Stream<Arguments> getIdUsuarioRendicionSource() {
    //given
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest requestEmptyAction = new MockHttpServletRequest();
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();
    List<Rendicion> rendicionListEmpty = new ArrayList<>();
    List<Usuario> usuarioList = new ArrayList<>();
    Usuario user = new Usuario("", "", "", 1, "", new ArrayList<>());
    usuarioList.add(user);
    Usuario usuario = new Usuario("", "", "", 1, "", usuarioList);

    rendicion.setUsuarioRendicion("");
    rendicionList.add(rendicion);

    httpSession.setAttribute("rendicion.link.thuban", "");
    httpSession.setAttribute("userWorking", usuario);

    requestEmptyAction.setHttpSession(httpSession);
    requestEmptyAction.addParameter("glg", "");
    requestEmptyAction.addParameter("codigo", "");

    return Stream.of(
        Arguments.of(requestEmptyAction, rendicionListEmpty),
        Arguments.of(requestEmptyAction, rendicionList)
                    );
  }

  public static Stream<Arguments> getRendicionSource() {
    //given
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();
    List<Rendicion> rendicionListEmpty = new ArrayList<>();

    rendicion.setUsuarioRendicion("");
    rendicionList.add(rendicion);

    return Stream.of(
        Arguments.of(rendicionListEmpty),
        Arguments.of(rendicionList)
                    );
  }

  public static Stream<Arguments> getSessionUserWorkingSource() {
    //given
    Usuario usuario = new Usuario("A23", "", "", 1, "", new ArrayList<>());
    MockHttpServletRequest request = new MockHttpServletRequest();
    HttpSession httpSession = new MockHttpSession();

    httpSession.setAttribute("userWorking", usuario);
    request.setHttpSession(httpSession);

    return Stream.of(Arguments.of(request));
  }

  public static Stream<Arguments> aprobarRechazarObservarExceptionSource() {
    //given
    String actionAprobar = "aprobar";
    String actionRechazar = "rechazar";
    String actionObservar = "observar";

    return Stream.of(
        Arguments.of(actionAprobar),
        Arguments.of(actionRechazar),
        Arguments.of(actionObservar)
                    );
  }

  public static Stream<Arguments> executeActionHappyTrailHandleErrorSource() {
    //given
    ActionForward actionForward = new ActionForward();
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest requestEmptyAction = new MockHttpServletRequest();
    List<Usuario> usuarioList = new ArrayList<>();
    Usuario user = new Usuario("", "", "", 1, "", new ArrayList<>());
    usuarioList.add(user);
    Usuario usuario = new Usuario("", "", "", 1, "", usuarioList);

    Rendicion rendicionUsuarioEmpty = new Rendicion();
    List<Rendicion> rendicionListIDUserRendicionEmpty = new ArrayList<>();
    rendicionListIDUserRendicionEmpty.add(rendicionUsuarioEmpty);

    httpSession.setAttribute("rendicion.link.thuban", "");
    httpSession.setAttribute("userWorking", usuario);

    requestEmptyAction.setHttpSession(httpSession);
    requestEmptyAction.addParameter("glg", "");
    requestEmptyAction.addParameter("codigo", "");

    return Stream.of(
        Arguments.of(requestEmptyAction, rendicionListIDUserRendicionEmpty, user, actionForward)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionHappyTrailSource") // Revisar 2da instancia de ManagerTransaction
  @DisplayName("Should execute action in happy trail")
  void shouldExecuteActionInHappyTrail(MockHttpServletRequest request, List<Rendicion> rendicionList, Usuario usuario, ActionForward actionForward) throws Exception {
    //given
    aprobacionDetalleAction.setSessionUserWorking(usuario);
    //when
    when(actionMappingMock.findForward("success")).thenReturn(actionForward);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
      when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
      when(mockManagerTransaction.getDataReturn()).thenReturn("");
      when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
    })) {
      //then
      ActionForward actionForwardToAssert = aprobacionDetalleAction.executeAction(actionMappingMock, rendicionFormMock, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionHappyTrailHandleErrorSource")
  @DisplayName("Should handle errors in happy trail")
  void shouldHandleErrorsInHappyTrail(MockHttpServletRequest request, List<Rendicion> rendicionList, Usuario usuario, ActionForward actionForward) throws Exception {
    //when
    when(actionMappingMock.findForward("success")).thenReturn(actionForward);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {

      when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
      when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
    })) {
      //then
      ActionForward actionForwardToAssert = aprobacionDetalleAction.executeAction(actionMappingMock, rendicionFormMock, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionIfElseBlockSource")
  @DisplayName("Should execute action in if else block")
  void shouldExecuteActionInIfElseBlock(MockHttpServletRequest request, String action, Usuario usuario) throws Exception {
    //given
    aprobacionDetalleAction.setSessionUserWorking(usuario);
    //when
    when(actionMappingMock.findForward(action)).thenReturn(actionForwardMock);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      when(mockManagerTransaction.getDataReturn()).thenReturn("OPERACION EFECTUADA");
      when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    })) {
      //then
      ActionForward actionForwardToAssert = aprobacionDetalleAction.executeAction(actionMappingMock, rendicionFormMock, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);
      if (action.equals("aprobar") || action.equals("rechazar") || action.equals("observar")) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("getIdUsuarioRendicionSource")
  @DisplayName("Should get ID usuario rendicion")
  void shouldGetIdUsuarioRendicion(MockHttpServletRequest request, List<Rendicion> rendicionList) throws Exception {
    //when
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
      when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
    })) {
      //then
      Method getIdUsuarioRendicionMocked = AprobacionDetalleAction.class.getDeclaredMethod("getIdUsuarioRendicion", HttpServletRequest.class, String.class,
          String.class, SAMWebClient.class);
      getIdUsuarioRendicionMocked.setAccessible(true);
      String idUsuarioToAssert = (String) getIdUsuarioRendicionMocked.invoke(aprobacionDetalleAction, request, "", "", samWebClientMocked);
      if (!rendicionList.isEmpty()) {
        assertEquals("", idUsuarioToAssert);
      } else {
        assertNull(idUsuarioToAssert);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("getRendicionSource")
  @DisplayName("Should get rendicion")
  void shouldGetRendicion(List<Rendicion> rendicionList) throws Exception {
    //when
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
      when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
    })) {
      //then
      Method getRendicionMocked = AprobacionDetalleAction.class.getDeclaredMethod("getRendicion", String.class, String.class, SAMWebClient.class);
      getRendicionMocked.setAccessible(true);
      Rendicion rendicionToAssert = (Rendicion) getRendicionMocked.invoke(aprobacionDetalleAction, "", "", samWebClientMocked);
      if (!rendicionList.isEmpty()) {
        assertNotNull(rendicionToAssert);
      } else {
        assertNull(rendicionToAssert);
      }
    }
  }

  @Test
  @DisplayName("Should get usuario rendicion")
  void shouldGetUsuarioRendicion() throws Exception {
    //given
    Usuario usuario = new Usuario("", "", "", 1, "", new ArrayList<>());
    //when
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      when(mockManagerTransaction.getDataReturn()).thenReturn(usuario);
      when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
    })) {
      //then
      Method getUsuarioRendicionMocked = AprobacionDetalleAction.class.getDeclaredMethod("getUsuarioRendicion", String.class, SAMWebClient.class);
      getUsuarioRendicionMocked.setAccessible(true);
      Usuario usuarioToAssert = (Usuario) getUsuarioRendicionMocked.invoke(aprobacionDetalleAction, "", samWebClientMocked);
      assertNotNull(usuarioToAssert);
    }
  }


  @Test
  @DisplayName("Should handle error")
  void shouldHandleError() throws Exception {
    //when
    when(actionMappingMock.findForward("success")).thenReturn(actionForwardMock);
    //then
    Method handleErrorMocked = AprobacionDetalleAction.class.getDeclaredMethod("handleError", ActionMapping.class, HttpServletRequest.class, String.class);
    handleErrorMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) handleErrorMocked.invoke(aprobacionDetalleAction, actionMappingMock, httpServletRequestMocked,
        "error message");
    assertNotNull(actionForwardToAssert);
  }

  @Test
  @DisplayName("Should set message")
  void shouldSetMessage() throws Exception {
    //given
    HttpServletRequest request = new MockHttpServletRequest();
    //then
    Method setMessageMocked = AprobacionDetalleAction.class.getDeclaredMethod("setMessage", HttpServletRequest.class, String.class);
    setMessageMocked.setAccessible(true);
    setMessageMocked.invoke(aprobacionDetalleAction, request, "message");
    assertEquals("message", request.getAttribute("message"));
  }

  @Test
  @DisplayName("Should set error message")
  void shouldSetErrorMessage() throws Exception {
    //given
    HttpServletRequest request = new MockHttpServletRequest();
    Exception exception = new Exception("error message");
    //then
    Method setErrorMessageMocked = AprobacionDetalleAction.class.getDeclaredMethod("setErrorMessage", HttpServletRequest.class, Exception.class);
    setErrorMessageMocked.setAccessible(true);
    setErrorMessageMocked.invoke(aprobacionDetalleAction, request, exception);
    assertEquals("error message", request.getAttribute("message"));
  }

  @ParameterizedTest
  @MethodSource("getSessionUserWorkingSource")
  @DisplayName("Should get session from user")
  void shouldGetSessionFromUser(MockHttpServletRequest request) throws Exception {
    //then
    Method getSessionFromUserMocked = AprobacionDetalleAction.class.getDeclaredMethod("getSessionUserWorking", HttpServletRequest.class);
    getSessionFromUserMocked.setAccessible(true);
    Usuario usuarioToAssert = (Usuario) getSessionFromUserMocked.invoke(aprobacionDetalleAction, request);
    assertAll(() -> assertNotNull(usuarioToAssert),
        () -> assertEquals("A23", usuarioToAssert.getIdUser()));
  }

  @ParameterizedTest
  @MethodSource("aprobarRechazarObservarExceptionSource")
  @DisplayName("Should catch an Exception on action")
  void shouldCatchAnExceptionOnAction(String action) throws Exception {
    //when
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    Method actionMocked = AprobacionDetalleAction.class.getDeclaredMethod(action, SAMWebClient.class, ActionMapping.class, HttpServletRequest.class,
        HttpServletResponse.class);
    actionMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) actionMocked.invoke(aprobacionDetalleAction, samWebClientMocked, actionMappingMock, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}
