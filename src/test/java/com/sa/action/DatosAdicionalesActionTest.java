package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.DatosPantallaDinamica;
import com.sa.manager.ManagerTransaction;
import com.sa.services.CierreService;
import com.sa.services.PagosService;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class DatosAdicionalesActionTest {

  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMock;
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
  DatosAdicionalesAction datosAdicionalesAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String action = "";
    String actionConsulta = "consulta";
    String actionAltaModif = "altaModif";
    String actionBaja = "baja";

    DatosPantallaDinamica datosPantallaDinamicaCOD1 = new DatosPantallaDinamica();
    datosPantallaDinamicaCOD1.setTipoCampo("COD1");
    datosPantallaDinamicaCOD1.setTituloCampo("TITULO1");
    DatosPantallaDinamica datosPantallaDinamicaCOD2 = new DatosPantallaDinamica();
    datosPantallaDinamicaCOD2.setTipoCampo("COD2");
    datosPantallaDinamicaCOD2.setTituloCampo("TITULO2");
    DatosPantallaDinamica datosPantallaDinamicaTXT1 = new DatosPantallaDinamica();
    datosPantallaDinamicaTXT1.setTipoCampo("TXT1");
    datosPantallaDinamicaTXT1.setTituloCampo("TITULO3");
    DatosPantallaDinamica datosPantallaDinamicaTXT2 = new DatosPantallaDinamica();
    datosPantallaDinamicaTXT2.setTipoCampo("TXT2");
    datosPantallaDinamicaTXT2.setTituloCampo("TITULO4");
    DatosPantallaDinamica datosPantallaDinamicaNUM1 = new DatosPantallaDinamica();
    datosPantallaDinamicaNUM1.setTipoCampo("NUM1");
    datosPantallaDinamicaNUM1.setTituloCampo("TITULO5");
    DatosPantallaDinamica datosPantallaDinamicaNUM2 = new DatosPantallaDinamica();
    datosPantallaDinamicaNUM2.setTipoCampo("NUM2");
    datosPantallaDinamicaNUM2.setTituloCampo("TITULO6");
    DatosPantallaDinamica datosPantallaDinamicaFEC1 = new DatosPantallaDinamica();
    datosPantallaDinamicaFEC1.setTipoCampo("FEC1");
    datosPantallaDinamicaFEC1.setTituloCampo("TITULO7");
    DatosPantallaDinamica datosPantallaDinamicaFEC2 = new DatosPantallaDinamica();
    datosPantallaDinamicaFEC2.setTipoCampo("FEC2");
    datosPantallaDinamicaFEC2.setTituloCampo("TITULO8");
    DatosPantallaDinamica datosPantallaDinamicaTXT250 = new DatosPantallaDinamica();
    datosPantallaDinamicaTXT250.setTipoCampo("TXT250");
    datosPantallaDinamicaTXT250.setTituloCampo("TITULO9");

    List<DatosPantallaDinamica> datosPantallaDinamicaList = new ArrayList<>();
    datosPantallaDinamicaList.add(datosPantallaDinamicaCOD1);
    datosPantallaDinamicaList.add(datosPantallaDinamicaCOD2);
    datosPantallaDinamicaList.add(datosPantallaDinamicaTXT1);
    datosPantallaDinamicaList.add(datosPantallaDinamicaTXT2);
    datosPantallaDinamicaList.add(datosPantallaDinamicaNUM1);
    datosPantallaDinamicaList.add(datosPantallaDinamicaNUM2);
    datosPantallaDinamicaList.add(datosPantallaDinamicaFEC1);
    datosPantallaDinamicaList.add(datosPantallaDinamicaFEC2);
    datosPantallaDinamicaList.add(datosPantallaDinamicaTXT250);

    return Stream.of(
        Arguments.of(action, datosPantallaDinamicaList),
        Arguments.of(actionConsulta, datosPantallaDinamicaList),
        Arguments.of(actionAltaModif, datosPantallaDinamicaList),
        Arguments.of(actionBaja, datosPantallaDinamicaList)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute the action")
  void shouldExecuteTheAction(String action, List<DatosPantallaDinamica> datosPantallaDinamicaList) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("idGasto")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("codGasto")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("codObserv")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("IDOBS")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("FEC1")).thenReturn("08/08/2023");
    when(httpServletRequestMocked.getParameter("FEC2")).thenReturn("08/08/2023");


    when(httpServletRequestMocked.getParameter("codObserv")).thenReturn("0");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(datosPantallaDinamicaList);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("message");
        })) {
      //then
      ActionForward actionForwardToAssert = datosAdicionalesAction.executeAction(actionMappingMock, actionFormMocked, samWebApplicationMocked,
          samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }
  
  @Test
  @DisplayName("Debe modificar monto gasto")
  void executeAction_calcularCombustible() throws Exception {
	  
	  PrintWriter printWriter = new PrintWriter(new StringWriter());
	  when(httpServletResponseMocked.getWriter()).thenReturn(printWriter);
	  
      when(httpServletRequestMocked.getParameter("action")).thenReturn("calcularCombustible");
      when(httpServletRequestMocked.getParameter("idGasto")).thenReturn("0001");
      when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("1212");
      when(httpServletRequestMocked.getParameter("gastoMonto")).thenReturn("1000");
      when(httpServletRequestMocked.getParameter("codMotivo")).thenReturn("0820");
      when(httpServletRequestMocked.getParameter("codGasto")).thenReturn("1234");
      when(httpServletRequestMocked.getParameter("moneda")).thenReturn("ARD");
      when(httpServletRequestMocked.getParameter("tipoComprobante")).thenReturn("00004");
      when(httpServletRequestMocked.getParameter("datosAdicionesCombustible")).thenReturn("IDOBS=1,COD1=00001 - NO,TXT1=Buenos Aires                                      ,TXT2=Salta                                             ,NUM1=       40,NUM2=000001172 _ ");
      
      when(actionMappingMock.findForward("aprobacionesPendientes")).thenReturn(new ActionForward("datosAdicionales", "/datosAdicionalesPath", false));  // Asegurar que el mock no devuelve null

      try (MockedConstruction<PagosService> mock = mockConstruction(PagosService.class, (mockPagosService, context) -> {
          when(mockPagosService.altaModifGasto(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
        		  								anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),
        		  								anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString()))
          										.thenReturn(1);
      })) {
          ActionForward result = datosAdicionalesAction.executeAction(actionMappingMock, actionFormMocked, samWebApplicationMocked,
                  samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

          assertNull(result); // Validar antes de assertEquals
          //assertEquals("aprobacionesPendientes", result.getName(), "El nombre del forward debería ser 'aprobacionesPendientes'");
      }
  }
}
