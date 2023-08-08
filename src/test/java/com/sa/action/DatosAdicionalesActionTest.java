package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.DatosPantallaDinamica;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
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
}
