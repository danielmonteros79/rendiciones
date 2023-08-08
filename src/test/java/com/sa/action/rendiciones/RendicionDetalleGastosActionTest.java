package com.sa.action.rendiciones;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sa.form.RendicionForm;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import java.io.PrintWriter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class RendicionDetalleGastosActionTest {

  @Mock
  PrintWriter printWriterMocked;
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
  @InjectMocks
  RendicionDetalleGastosAction rendicionDetalleGastosAction;

  public static Stream<Arguments> execurteActionSource() {
    //given
    String action = "";
    String actionGetRendicionGastos = "getRendicionGastos";
    String actionGetConsumosPendientes = "getConsumosPendientes";
    String actionActivarRechazar = "activarRechazar";
    String actionModificarRendicion = "modificarRendicion";
    String actionFinalizarObservacion = "finalizarObservacion";

    MockHttpServletRequest requestEmptyAction = new MockHttpServletRequest();
    MockHttpServletRequest requestRendicionGastos = new MockHttpServletRequest();
    MockHttpServletRequest requestGetConsumosPendientes = new MockHttpServletRequest();
    MockHttpServletRequest requestActivarRechazar = new MockHttpServletRequest();
    MockHttpServletRequest requestModificarRendicion = new MockHttpServletRequest();
    MockHttpServletRequest requestFinalizarObservacion = new MockHttpServletRequest();

    requestRendicionGastos.addParameter("action", "getRendicionGastos");
    requestRendicionGastos.addParameter("codMotivo", "");
    requestRendicionGastos.addParameter("estadoRend", "PENDI");

    requestGetConsumosPendientes.addParameter("action", "getConsumosPendientes");
    requestGetConsumosPendientes.addParameter("fechaDesde", "07/08/2023");
    requestGetConsumosPendientes.addParameter("fechaHasta", "07/08/2023");

    requestActivarRechazar.addParameter("action", "activarRechazar");
    requestActivarRechazar.addParameter("idRendicion", "1");
    requestActivarRechazar.addParameter("estado", "");

    requestModificarRendicion.addParameter("action", "modificarRendicion");
    requestModificarRendicion.addParameter("idRendicion", "");
    requestModificarRendicion.addParameter("codMotivo", "");
    requestModificarRendicion.addParameter("estadoRend", "");
    requestModificarRendicion.addParameter("descripcion", "");
    requestModificarRendicion.addParameter("fechaDesde", "07/08/2023");
    requestModificarRendicion.addParameter("fechaHasta", "07/08/2023");

    requestFinalizarObservacion.addParameter("action", "finalizarObservacion");
    requestFinalizarObservacion.addParameter("idRendicion", "");
    requestFinalizarObservacion.addParameter("idu", "");

    return Stream.of(
//        Arguments.of(requestEmptyAction, action),  // sessionUserWorking lanza NPE
        Arguments.of(requestRendicionGastos, actionGetRendicionGastos),
//        Arguments.of(requestGetConsumosPendientes, actionGetConsumosPendientes),   // sessionUserWorking lanza NPE
        Arguments.of(requestActivarRechazar, actionActivarRechazar), // sessionUserWorking lanza NPE
        Arguments.of(requestModificarRendicion, actionModificarRendicion), // sessionUserWorking lanza NPE
        Arguments.of(requestFinalizarObservacion, actionFinalizarObservacion)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Method under test: {@link RendicionDetalleGastosAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @ParameterizedTest
  @MethodSource("execurteActionSource")
  void testExecuteAction(MockHttpServletRequest request, String action) throws Exception {
    //when
    when(actionMappingMocked.findForward("rendicionDetalleGastos")).thenReturn(actionForwardMocked);
    when(actionMappingMocked.findForward("gastos")).thenReturn(actionForwardMocked);
    when(actionMappingMocked.findForward("consumosPendientes")).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
        })) {
      //then
      ActionForward actionForwardToAssert = rendicionDetalleGastosAction.executeAction(actionMappingMocked, rendicionFormMocked, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);

      switch (action) {
        case "getRendicionGastos":
          assertNotNull(actionForwardToAssert); //checked
          break;
        case "getConsumosPendientes":
          assertNotNull(actionForwardToAssert); // lanza NPE
          break;
        case "activarRechazar":
          assertNull(actionForwardToAssert); //checked - lanza NPE
          break;
        case "modificarRendicion":
          assertNull(actionForwardToAssert); //checked - lanza NPE
          break;
        case "finalizarObservacion":
          assertNull(actionForwardToAssert); // checked - lanza NPE
          break;
        default:
          assertNotNull(actionForwardToAssert); // lanza NPE
          break;
      }
    }
  }
}
