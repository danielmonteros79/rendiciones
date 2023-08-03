package com.sa.action.cierre;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CierreDetalleActionTest {

  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  RendicionForm rendicionFormMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  HttpSession httpSessionMocked;
  @Mock
  ServletContext servletContextMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @InjectMocks
  CierreDetalleAction cierreDetalleAction;

  public static Stream<Arguments> executeActionHappyTrailSource() {
    //given
    Usuario user = new Usuario("", "", "", 1, "", new ArrayList<>());
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();
    rendicion.setFechaDesde(new Date());
    rendicion.setFechaHasta(new Date());
    rendicion.setMotivoRechazo("SI");
    rendicionList.add(rendicion);

    return Stream.of(Arguments.of(rendicionList, user));
  }

  public static Stream<Arguments> executeActionIfElseBlockSource() {
    //given
    String motivoRechazo = "SI";
    Usuario user = new Usuario("", "", "", 1, "", new ArrayList<>());
    Rendicion rendicion = new Rendicion();
    List<Rendicion> rendicionList = new ArrayList<>();
    List<Rendicion> rendicionEmptyList = new ArrayList<>();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletRequest requestActionGastos = new MockHttpServletRequest();
    HttpSession session = new MockHttpSession();

    rendicion.setFechaDesde(new Date());
    rendicion.setFechaHasta(new Date());
    rendicion.setMotivoRechazo("SI");
    rendicionList.add(rendicion);

    request.addParameter("action", "");
    request.addParameter("codigo", "");
    request.addParameter("usuarioRend", "");
    request.setHttpSession(session);

    requestActionGastos.addParameter("action", "getRendicionGastos");
    requestActionGastos.addParameter("codigo", "");
    requestActionGastos.addParameter("usuarioRend", "");

    return Stream.of(
        Arguments.of(requestActionGastos, rendicionList, user, motivoRechazo), //OK - Gastos
        Arguments.of(request, rendicionEmptyList, user, motivoRechazo), // OK - Rendiciones vacias
        Arguments.of(request, rendicionList, user, ""),
        Arguments.of(request, rendicionList, user, motivoRechazo)
                    );
  }

  public static Stream<Arguments> getRendicionGastos() {
    //given
    Gastos gastos = new Gastos();
    List<Gastos> gastosList = new ArrayList<>();
    MockHttpServletRequest request = new MockHttpServletRequest();

    gastosList.add(gastos);

    request.addParameter("idRendicion", "");
    request.addParameter("usuarioRend", "");
    request.addParameter("codMotivo", "");

    return Stream.of(Arguments.of(request, gastosList));
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionHappyTrailSource")
  @DisplayName("Should execute action happy trail")
  void shouldExecuteActionHappyTrail(List<Rendicion> rendicionList, Usuario user) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("");
    when(httpServletRequestMocked.getParameter("codigo")).thenReturn("");
    when(httpServletRequestMocked.getParameter("usuarioRend")).thenReturn("");
    when(actionMappingMocked.findForward("gastos")).thenReturn(actionForwardMocked);
    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("rendicion.link.thuban")).thenReturn("");
    //then
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
          when(mockManagerTransaction.getDataReturn()).thenReturn(user);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("message");
        })) {
      ActionForward actionForwardToAssert = cierreDetalleAction.executeAction(actionMappingMocked, rendicionFormMocked, samWebApplicationMocked,
          samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionIfElseBlockSource")
  @DisplayName("Should execute action if else block")
  void shouldExecuteActionIfElseBlock(MockHttpServletRequest request, List<Rendicion> rendicionList, Usuario user, String motivoRechazo) throws Exception {
    //when
    when(actionMappingMocked.findForward("gastos")).thenReturn(actionForwardMocked);
    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("rendicion.link.thuban")).thenReturn("");
    when(rendicionFormMocked.getMotivoRechazo()).thenReturn(motivoRechazo);
    //then
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
          when(mockManagerTransaction.getDataReturn()).thenReturn(user);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("message");
        })) {
      ActionForward actionForwardToAssert = cierreDetalleAction.executeAction(actionMappingMocked, rendicionFormMocked, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("getRendicionGastos")
  @DisplayName("Should get rendicion gastos")
  void shouldGetRendicionGastos(MockHttpServletRequest request, List<Gastos> gastosList) throws Exception {
    //when
    when(actionMappingMocked.findForward("gastos")).thenReturn(actionForwardMocked);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(gastosList);
        })) {
      //then
      Method getRendicionGastosMocked = CierreDetalleAction.class.getDeclaredMethod("getRendicionGastos", SAMWebClient.class, ActionMapping.class, HttpServletRequest.class);
      getRendicionGastosMocked.setAccessible(true);
      ActionForward actionForwardToAssert = (ActionForward) getRendicionGastosMocked.invoke(cierreDetalleAction, samWebClientMocked, actionMappingMocked, request);
      assertNotNull(actionForwardToAssert);
    }
  }
}
