package com.sa.action.delegacion;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.form.delegacion.AbmDelegadoForm;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForm;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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
  HttpServletRequest httpServletRequestMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  PrintWriter printWriterMocked;
  @InjectMocks
  AbmDelegadoAction abmDelegadoAction;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    Usuario usuario = new Usuario("", "", "", 0, "", new ArrayList<>());
    abmDelegadoAction.setSessionUser(usuario);
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
    HttpSession session = new MockHttpSession();

    ParametriaUsuarioDelegado parametriaUsuarioDelegado = new ParametriaUsuarioDelegado();
    parametriaUsuarioDelegado.setId(1);
    List<ParametriaUsuarioDelegado> parametriaUsuarioDelegadoList = new ArrayList<>();
    parametriaUsuarioDelegadoList.add(parametriaUsuarioDelegado);

    session.setAttribute("delegacionesActivas", parametriaUsuarioDelegadoList);

    requestEmptyAction.addParameter("action", "");

    requestGetDelegados.addParameter("action", "getDelegados");
    requestGetDelegado.addParameter("action", "getDelegado");
    requestGetDelegado.addParameter("id", "1");
    requestGetDelegado.setHttpSession(session);

    requestBuscarUsuario.addParameter("action", "buscarUsuario");
    requestBuscarUsuario.addParameter("legajo", "legajo");

    requestAbm.addParameter("action", "abm");

    return Stream.of(
        Arguments.of(requestEmptyAction, ""),
        Arguments.of(requestGetDelegados, actionGetDelegados),
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
  @DisplayName("should execute action")
  void shouldExecuteAction(MockHttpServletRequest request, String action) throws Exception {
    //when
    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);

    when(abmDelegadoFormMocked.getOpcion()).thenReturn("BAJA");
    when(abmDelegadoFormMocked.getDelegadoUser()).thenReturn("delegado");
    when(abmDelegadoFormMocked.getFeDesde()).thenReturn("15/08/2023");
    when(abmDelegadoFormMocked.getFeHasta()).thenReturn("15/08/2023");

    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(null);
          when(mockManagerTransaction.getDataReturn()).thenReturn(null);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
      //then
      ActionForward actionForwardToAssert = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);
      if (action.equals("getDelegado") || action.equals("buscarUsuario") || action.equals("abm")) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @Test
  @DisplayName("should catch exception")
  void shouldCatchException() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("getDelegados");
    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked, samWebApplicationMocked,
        samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}
