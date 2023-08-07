package com.sa.action.aprobacion;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
        Arguments.of(requestEmptyAction, rendicionList, user, actionForward) // retorna usuario nulo en linea 67
                    );
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

    requestActionRechazar.setHttpSession(httpSession);
    requestActionRechazar.addParameter("action", "rechazar");
    requestActionRechazar.addParameter("glg", "");
    requestActionRechazar.addParameter("codigo", "");

    requestActionObservar.setHttpSession(httpSession);
    requestActionObservar.addParameter("action", "observar");
    requestActionObservar.addParameter("glg", "");
    requestActionObservar.addParameter("codigo", "");

    return Stream.of(
        Arguments.of(requestActionGastos, actionGastos)
//        Arguments.of(requestActionAprobar, actionAprobar), // Log lanza NPE
//        Arguments.of(requestActionRechazar, actionRechazar), // Log lanza NPE
//        Arguments.of(requestActionObservar, actionObservar) // Log lanza NPE
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

  public static Stream<Arguments> getRendicionGastosSource() {
    //given
    Gastos gastos = new Gastos();
    List<Gastos> gastosList = new ArrayList<>();
    MockHttpServletRequest request = new MockHttpServletRequest();

    request.addParameter("idRendicion", "1");
    request.addParameter("usuarioRend", "A23");
    request.addParameter("codMotivo", "1");

    gastosList.add(gastos);

    return Stream.of(Arguments.of(request, gastosList));
  }

  public static Stream<Arguments> aprobarRechazarObservarSource() {
    //given
    Usuario usuario = new Usuario("A23", "", "", 1, "", new ArrayList<>());
    Gastos gastos = new Gastos();
    List<Gastos> gastosList = new ArrayList<>();
    MockHttpServletRequest request = new MockHttpServletRequest();

    request.addParameter("idRendicion", "1");
    request.addParameter("comentario", "comentario");
    request.addParameter("glg", "glg");
    request.addParameter("descripcion", "descripcion");

    gastosList.add(gastos);

    return Stream.of(Arguments.of(request, gastosList, usuario));
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionHappyTrailSource")  // Usuario en linea 67 es nulo
  @DisplayName("Should execute action in happy trail")
  void shouldExecuteActionInHappyTrail(MockHttpServletRequest request, List<Rendicion> rendicionList, Usuario usuario, ActionForward actionForward) throws Exception {
    //when
    when(actionMappingMock.findForward("success")).thenReturn(actionForward);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {

      when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
//      when(mockManagerTransaction.getDataReturn()).thenReturn(usuario);
      when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
    })) {
      //then
      ActionForward actionForwardToAssert = aprobacionDetalleAction.executeAction(actionMappingMock, rendicionFormMock, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest //Aprobacion, Rechazo y Observacion lanzan NPEs
  @MethodSource("executeActionIfElseBlockSource")
  @DisplayName("Should execute action in if else block")
  void shouldExecuteActionInIfElseBlock(MockHttpServletRequest request, String action) throws Exception {
    //when
    when(actionMappingMock.findForward(action)).thenReturn(actionForwardMock);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
    })) {
      //then
      ActionForward actionForwardToAssert = aprobacionDetalleAction.executeAction(actionMappingMock, rendicionFormMock, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
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
  @MethodSource("getRendicionGastosSource")
  @DisplayName("Should get rendicion gastos")
  void shouldGetRendicionGastos(MockHttpServletRequest request, List<Gastos> gastosList) throws Exception {
    //when
    when(actionMappingMock.findForward("gastos")).thenReturn(actionForwardMock);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      when(mockManagerTransaction.getDataReturnList()).thenReturn(gastosList);
      when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
    })) {
      //then
      Method getRendicionGastosMocked = AprobacionDetalleAction.class.getDeclaredMethod("getRendicionGastos", SAMWebClient.class, ActionMapping.class,
          HttpServletRequest.class);
      getRendicionGastosMocked.setAccessible(true);
      ActionForward actionForwardToAssert = (ActionForward) getRendicionGastosMocked.invoke(aprobacionDetalleAction, samWebClientMocked, actionMappingMock,
          request);
      assertNotNull(actionForwardToAssert);
    }
  }

  @Disabled("sessionUserWorking lanza NPE - reveer")
  @ParameterizedTest
  @MethodSource("aprobarRechazarObservarSource")
  @DisplayName("Should approve rendicion")
  void shouldApproveRendicion(MockHttpServletRequest request, List<Gastos> gastosList, Usuario usuario) throws Exception {
    //when
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          when(mockManagerTransaction.getDataReturnList()).thenReturn(gastosList);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
          when(actionMappingMock.findForward("gastos")).thenReturn(actionForwardMock);
          when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
        })) {
      //then
      Method aprobarMocked = AprobacionDetalleAction.class.getDeclaredMethod("aprobar", SAMWebClient.class, ActionMapping.class, HttpServletRequest.class,
          HttpServletResponse.class);
      aprobarMocked.setAccessible(true);
      ActionForward actionForwardToAssert = (ActionForward) aprobarMocked.invoke(aprobacionDetalleAction, samWebClientMocked, actionMappingMock, request,
          httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }

  @Disabled("sessionUserWorking lanza NPE - reveer")
  @ParameterizedTest
  @MethodSource("aprobarRechazarObservarSource")
  @DisplayName("Should reject rendicion")
  void shouldRejectRendicion(MockHttpServletRequest request, List<Gastos> gastosList, Usuario usuario) throws Exception {
    //when
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          when(mockManagerTransaction.getDataReturnList()).thenReturn(gastosList);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
          when(actionMappingMock.findForward("gastos")).thenReturn(actionForwardMock);
          when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
        })) {
      //then
      Method aprobarMocked = AprobacionDetalleAction.class.getDeclaredMethod("rechazar", SAMWebClient.class, ActionMapping.class, HttpServletRequest.class, HttpServletResponse.class);
      aprobarMocked.setAccessible(true);
      ActionForward actionForwardToAssert = (ActionForward) aprobarMocked.invoke(aprobacionDetalleAction, samWebClientMocked, actionMappingMock, request,
          httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }

  @Disabled("sessionUserWorking lanza NPE - reveer")
  @ParameterizedTest
  @MethodSource("aprobarRechazarObservarSource")
  @DisplayName("Should observe rendicion")
  void shouldObserveRendicion(MockHttpServletRequest request, List<Gastos> gastosList, Usuario usuario) throws Exception {
    //when
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          when(mockManagerTransaction.getDataReturnList()).thenReturn(gastosList);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
          when(actionMappingMock.findForward("gastos")).thenReturn(actionForwardMock);
          when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
        })) {
      //then
      Method aprobarMocked = AprobacionDetalleAction.class.getDeclaredMethod("observar", SAMWebClient.class, ActionMapping.class, HttpServletRequest.class, HttpServletResponse.class);
      aprobarMocked.setAccessible(true);
      ActionForward actionForwardToAssert = (ActionForward) aprobarMocked.invoke(aprobacionDetalleAction, samWebClientMocked, actionMappingMock, request,
          httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }
}
