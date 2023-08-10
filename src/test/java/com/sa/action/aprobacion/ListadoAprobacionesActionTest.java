package com.sa.action.aprobacion;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Rendicion;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
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

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ListadoAprobacionesActionTest {

  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  ActionForm actionFormMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @InjectMocks
  ListadoAprobacionesAction listadoAprobacionesAction;
  @Captor
  ArgumentCaptor<String> argumentCaptor;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionForward actionForward = new ActionForward();
    String actionFiltrar = "filtrar";
    String actionAprobar = "aprobar";
    String actionSuccess = "success";

    MockHttpServletRequest requestActionFiltrar = new MockHttpServletRequest();
    requestActionFiltrar.addParameter("action", "filtrar");

    MockHttpServletRequest requestActionAprobar = new MockHttpServletRequest();
    requestActionAprobar.addParameter("action", "aprobar");

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("glg", "1234567");

    return Stream.of(
        Arguments.of(request, actionSuccess, actionForward),
        Arguments.of(requestActionFiltrar, actionFiltrar, actionForward),
        Arguments.of(requestActionAprobar, actionAprobar, actionForward)
                    );
  }

  public static Stream<Arguments> filtrarSource() {
    //given
    MockHttpServletRequest request = new MockHttpServletRequest();
    ActionForward actionForward = new ActionForward();
    List<Rendicion> rendicionList = new ArrayList<>();
    Rendicion rendicion = new Rendicion();

    rendicionList.add(rendicion);

    return Stream.of(
        Arguments.of(request, actionForward, rendicionList)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Disabled("sessionUserWorking lanza NPE - reveer")
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute Action")
  void shouldExecuteAction(MockHttpServletRequest request, String action, ActionForward actionForward) throws Exception {
    //when
    when(actionMappingMocked.findForward(action)).thenReturn(actionForward);
    //then
    ActionForward actionForwardToAssert = listadoAprobacionesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
        samWebClientMocked, request, httpServletResponseMocked);
    assertNotNull(actionForwardToAssert);
  }

  @Disabled("sessionUserWorking lanza NPE - reveer")
  @ParameterizedTest
  @MethodSource("filtrarSource")
  @DisplayName("Should filter rendiciones")
  void shouldFilterRendiciones(MockHttpServletRequest request, ActionForward actionForward, List<Rendicion> rendicionList) throws Exception {
    //when
    when(actionMappingMocked.findForward("aprobaciones")).thenReturn(actionForward);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          when(mockManagerTransaction.getDataReturnList()).thenReturn(rendicionList);
          when(mockManagerTransaction.getDataReturn()).thenReturn("");
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
      //then
      Method filtrarMocked = ListadoAprobacionesAction.class.getDeclaredMethod("filtrar", ActionMapping.class, SAMWebClient.class, HttpServletRequest.class,
          HttpServletResponse.class);
      filtrarMocked.setAccessible(true);
      ActionForward actionForwardToAssert = (ActionForward) filtrarMocked.invoke(listadoAprobacionesAction, actionMappingMocked, samWebClientMocked, request,
          httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @Disabled("sessionUserWorking lanza NPE - reveer")
  @Test
  @DisplayName("Should aprove rendiciones")
  void shouldAproveRendiciones() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("idRendiciones")).thenReturn("[\"1\", \"2\", \"3\"]");
    when(httpServletRequestMocked.getParameter("glg")).thenReturn("glgValue");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(
        ManagerTransaction.class, (mockManagerTransaction, context) -> {
      when(mockManagerTransaction.getDataReturn()).thenReturn("");
    })) {
      //then
      Method filtrarMocked = ListadoAprobacionesAction.class.getDeclaredMethod("aprobar", ActionMapping.class, SAMWebClient.class, HttpServletRequest.class, HttpServletResponse.class);
      filtrarMocked.setAccessible(true);
      ActionForward actionForwardToAssert = (ActionForward) filtrarMocked.invoke(listadoAprobacionesAction, actionMappingMocked, samWebClientMocked, httpServletRequestMocked,
          httpServletResponseMocked);

      verify(httpServletRequestMocked, times(2)).getParameter(argumentCaptor.capture());
      List<String> parameterValues = argumentCaptor.getAllValues();
      assertEquals("idRendiciones", parameterValues.get(0));
      assertEquals("glg", parameterValues.get(1));
      assertNotNull(actionForwardToAssert);
    }
  }
}
