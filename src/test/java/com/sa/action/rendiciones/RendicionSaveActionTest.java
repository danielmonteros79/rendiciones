package com.sa.action.rendiciones;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sa.form.RendicionForm;
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
import org.mockito.*;

import java.util.ArrayList;
import java.util.stream.Stream;

class RendicionSaveActionTest {

  @Mock
  ActionForward actionForwardMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  RendicionForm rendicionFormMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @InjectMocks
  RendicionSaveAction rendicionSaveAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String idRendicion = "idRendicion";
    Usuario usuario = new Usuario("", "", "", 1, "" , new ArrayList<>());
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest request = new MockHttpServletRequest();

    httpSession.setAttribute("usuario", usuario);
    request.setHttpSession(httpSession);

    return Stream.of(
        Arguments.of(request, idRendicion),
        Arguments.of(request, "")
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * Method under test: {@link RendicionSaveAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Test executeAction")
  void testExecuteAction(MockHttpServletRequest request, String idRendicion) throws Exception {
    //when
    when(rendicionFormMocked.getFechaDesde()).thenReturn("07/08/2023");
    when(rendicionFormMocked.getFechaHasta()).thenReturn("07/08/2023");
    when(actionMappingMocked.findForward("detalleGastos")).thenReturn(actionForwardMocked);
    when(actionMappingMocked.findForward("failure")).thenReturn(actionForwardMocked);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturn()).thenReturn(idRendicion);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("message");
        })) {
      //then
      ActionForward actionForwardToAssert = rendicionSaveAction.executeAction(actionMappingMocked, rendicionFormMocked, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  @Test
  void testRendicionDetalleGastos() throws Exception {
    //when
    when(actionMappingMocked.findForward("rendicionDetalleGastos")).thenReturn(actionForwardMocked);
      //then
      ActionForward actionForwardToAssert = rendicionSaveAction.rendicionDetalleGastos(actionMappingMocked, rendicionFormMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
  }
}
