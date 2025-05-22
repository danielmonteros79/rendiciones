package com.sa.action.cierre;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CierreOrdenDePagoActionTest {

  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMock;
  @Mock
  ActionForward actionForwardMock;
  @Mock
  ActionForm actionFormMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @InjectMocks
  CierreOrdenDePagoAction cierreOrdenDePagoAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String action = "";
    String actionFiltrar = "filtrar";
    String actionGenerar = "generar";
    String actionSuspender = "suspender";

    return Stream.of(
        Arguments.of(action),
        Arguments.of(actionFiltrar),
        Arguments.of(actionGenerar),
        Arguments.of(actionSuspender)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Usuario usuario = new Usuario("", "", "", 0, "", new ArrayList<>());
    cierreOrdenDePagoAction.setSessionUserWorking(usuario);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute action")
  void shouldExecuteAction(String action) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);
    when(httpServletRequestMocked.getParameter("idRendiciones")).thenReturn("[\"1\", \"2\", \"3\"]");
    when(actionMappingMock.findForward(anyString())).thenReturn(actionForwardMock);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(httpServletRequestMocked.getParameter("motivo")).thenReturn("");
    when(httpServletRequestMocked.getParameter("usuario")).thenReturn("");
    when(httpServletRequestMocked.getParameter("fechaDesde")).thenReturn("");
    when(httpServletRequestMocked.getParameter("fechaHasta")).thenReturn("");

    when(actionMappingMock.findForward(anyString())).thenReturn(actionForwardMock);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(null);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
      //then
      ActionForward actionForwardToAssert = cierreOrdenDePagoAction.executeAction(actionMappingMock, actionFormMocked, samWebApplicationMocked,
          samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      if (action.equals("generar") || action.equals("suspender")) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @Test
  @DisplayName("Should catch exception")
  void shouldCatchException() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("filtrar");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = cierreOrdenDePagoAction.executeAction(actionMappingMock, actionFormMocked, samWebApplicationMocked,
        samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}
