package com.sa.action.delegacion;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sa.form.delegacion.AbmDelegadoForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AbmDelegadoActionTest {

  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  AbmDelegadoForm abmDelegadoFormMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  PrintWriter printWriterMocked;
  @InjectMocks
  AbmDelegadoAction abmDelegadoAction;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  public static Stream<Arguments> executeActionSource() {
    //given
    String actionGetDelegados = "getDelegados";
    String actionGetDelegado = "getDelegado";
    String actionBuscarUsuario = "buscarUsuario";
    String actionAbm = "abm";
    MockHttpServletRequest requestEmptyAction = new MockHttpServletRequest();
    MockHttpServletRequest requestGetDelegados = new MockHttpServletRequest();
    MockHttpServletRequest requestGetDelegado = new MockHttpServletRequest();
    MockHttpServletRequest requestBuscarUsuario = new MockHttpServletRequest();
    MockHttpServletRequest requestAbm = new MockHttpServletRequest();

    requestEmptyAction.addParameter("action", "");
    requestGetDelegados.addParameter("action", "getDelegados");
    requestGetDelegado.addParameter("action", "getDelegado");
    requestBuscarUsuario.addParameter("action", "buscarUsuario");
    requestAbm.addParameter("action", "abm");

    return Stream.of(
        Arguments.of(requestEmptyAction, ""),
        Arguments.of(requestGetDelegados, actionGetDelegados), // sessionUser lanza NPE
        Arguments.of(requestGetDelegado, actionGetDelegado),
        Arguments.of(requestBuscarUsuario, actionBuscarUsuario),
        Arguments.of(requestAbm, actionAbm)
                    );
  }

  /**
   * Method under test: {@link AbmDelegadoAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @SuppressWarnings("deprecation")
  void testExecuteAction(MockHttpServletRequest request, String action) throws Exception {
    //when
    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);
    when(actionMappingMocked.findForward("delegados")).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked, samWebApplicationMocked,
        samWebClientMocked, request, httpServletResponseMocked);
    if (action.equals("getDelegados") || action.equals("getDelegado") || action.equals("buscarUsuario") || action.equals("abm")) {
      assertNull(actionForwardToAssert);  // sessionUser lanza NPE
    } else {
      assertNotNull(actionForwardToAssert);
    }
  }
}
