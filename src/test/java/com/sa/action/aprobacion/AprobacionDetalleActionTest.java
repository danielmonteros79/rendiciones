package com.sa.action.aprobacion;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import com.sa.services.UsuarioService;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
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
  @Mock
  HttpSession httpSessionMocked;
  @Mock
  ServletContext servletContextMocked;
  @InjectMocks
  AprobacionDetalleAction aprobacionDetalleAction;

  public static Stream<Arguments> executeActionHappyTrailSource() {
    //given
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();

    Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());

    rendicion.setUsuarioRendicion("");
    rendicion.setFechaDesde(new Date());
    rendicion.setFechaHasta(new Date());
    rendicion.setId(0);
    rendicion.setMotivo("");
    rendicion.setDescripcion("");
    rendicion.setCodMotivo("");
    rendicion.setMotivoRechazo("");
    rendicionList.add(rendicion);

    return Stream.of(Arguments.of(rendicionList, usuario));
  }

  public static Stream<Arguments> executeActionHandleErrorsSource() {
    //given
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();
    List<Rendicion> rendicionEmptyList = new ArrayList<>();

    Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());

    rendicion.setUsuarioRendicion("");
    rendicion.setFechaDesde(new Date());
    rendicion.setFechaHasta(new Date());
    rendicion.setId(0);
    rendicion.setMotivo("");
    rendicion.setDescripcion("");
    rendicion.setCodMotivo("");
    rendicion.setMotivoRechazo("");
    rendicionList.add(rendicion);

    return Stream.of(
        Arguments.of(rendicionEmptyList, rendicionList, usuario), // Ok
        Arguments.of(rendicionList, rendicionEmptyList, usuario),
        Arguments.of(rendicionList, rendicionList, usuario)
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
    Usuario user = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
    usuarioList.add(user);
    Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", usuarioList);

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

  public static Stream<Arguments> aprobarRechazarObservarSource() {
    //given
    String methodAprobar = "aprobar";
    String methodRechazar = "rechazar";
    String methodObservar = "observar";

    return Stream.of(
        Arguments.of(methodAprobar),
        Arguments.of(methodRechazar),
        Arguments.of(methodObservar)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
    aprobacionDetalleAction.setSessionUserWorking(usuario);
  }

  @ParameterizedTest
  @MethodSource("executeActionHappyTrailSource")
  @DisplayName("Should execute action in happy trail")
  void shouldExecuteActionInHappyTrail(List<Rendicion> rendicionList, Usuario usuario) throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("rendicion.link.thuban")).thenReturn("");
    when(httpServletRequestMocked.getParameter("action")).thenReturn("gastos");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("");
    when(httpServletRequestMocked.getParameter("codigo")).thenReturn("");
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuario);

    when(actionMappingMock.findForward(anyString())).thenReturn(actionForwardMock);

    try (MockedConstruction<AprobacionesService> aprobacionesServiceMC = mockConstruction(AprobacionesService.class, (mockAprobacionesService, context) -> {
      when(mockAprobacionesService.getAprobacionesPendientes(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(rendicionList);
      doReturn("").when(mockAprobacionesService).getMsg();
    })) {
      try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class,
          (mockRendicionesService, context) -> {
            when(mockRendicionesService.obtenerListadoRendiciones(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(rendicionList);
            doReturn("").when(mockRendicionesService).getMsg();
          })) {
        try (MockedConstruction<UsuarioService> usuarioServiceMC = mockConstruction(UsuarioService.class,
            (mockUsuarioService, context) -> {
              when(mockUsuarioService.obtenerDelegadosUsuario(anyString())).thenReturn(usuario);
              doReturn("").when(mockUsuarioService).getMsg();
            })) {
          //then
          ActionForward actionForwardToAssert = aprobacionDetalleAction.executeAction(actionMappingMock, rendicionFormMock, samWebApplicationMocked,
              samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
          assertNotNull(actionForwardToAssert);
        }
      }
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionHandleErrorsSource")
  @DisplayName("Should handle errors in happy trail")
  void shouldHandleErrorsInHappyTrail(List<Rendicion> rendicionList, List<Rendicion> rendicionList2, Usuario usuario) throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("rendicion.link.thuban")).thenReturn("");
    when(httpServletRequestMocked.getParameter("action")).thenReturn("gastos");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("");
    when(httpServletRequestMocked.getParameter("codigo")).thenReturn("");
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuario);

    when(actionMappingMock.findForward(anyString())).thenReturn(actionForwardMock);

    try (MockedConstruction<AprobacionesService> aprobacionesServiceMC = mockConstruction(AprobacionesService.class, (mockAprobacionesService, context) -> {
      when(mockAprobacionesService.getAprobacionesPendientes(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(rendicionList);
    })) {
      try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class,
          (mockRendicionesService, context) -> {
            when(mockRendicionesService.obtenerListadoRendiciones(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(rendicionList2 );
          })) {
        try (MockedConstruction<ManagerTransaction> managerTransactionMC = mockConstruction(ManagerTransaction.class,
            (mockManagerTransaction, context) -> {
              doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
              when(mockManagerTransaction.getDataReturn()).thenReturn(null);
            })) {
          //then
          ActionForward actionForwardToAssert = aprobacionDetalleAction.executeAction(actionMappingMock, rendicionFormMock, samWebApplicationMocked,
              samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
          assertNotNull(actionForwardToAssert);
        }
      }
    }
  }

  @Test
  @DisplayName("Should catch an exception")
  void shouldCatchAnException() throws Exception {
    //given
    Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuario);
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("gastos");
    when(actionMappingMock.findForward(anyString())).thenReturn(actionForwardMock);
    //then
    ActionForward actionForwardToAssert = aprobacionDetalleAction.executeAction(actionMappingMock, rendicionFormMock, samWebApplicationMocked,
        samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNotNull(actionForwardToAssert);
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
  @MethodSource("aprobarRechazarObservarSource")
  @DisplayName("Should catch an exception when approving")
  void shouldCatchAnExceptionWhenApproving(String method) throws Exception {
    //when
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    Method aprobarMocked = AprobacionDetalleAction.class.getDeclaredMethod(method, SAMWebClient.class, ActionMapping.class, HttpServletRequest.class,
        HttpServletResponse.class);
    aprobarMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) aprobarMocked.invoke(aprobacionDetalleAction, samWebClientMocked, actionMappingMock, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}
