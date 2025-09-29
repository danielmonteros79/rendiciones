package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Cupones;
import com.sa.entities.Gastos;
import com.sa.entities.Usuario;
import com.sa.manager.ManagerTransaction;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CuponesActionTest {

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
  SAMWebApplication samWebApplicationMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @InjectMocks
  CuponesAction cuponesAction;

  public static Stream<Arguments> executeActionSource() {
    //then
    String action = "";
    String actionConsulta = "consulta";
    String fechaDesde = "08/08/2023";

    Cupones cupones = new Cupones();
    List<Cupones> cuponesList = new ArrayList<>();
    cuponesList.add(cupones);

    return Stream.of(
        Arguments.of(action, cuponesList, fechaDesde),
        Arguments.of(actionConsulta, cuponesList, ""),
        Arguments.of(actionConsulta, cuponesList, fechaDesde)
                    );
  }

  public static Stream<Arguments> executeActionGastosSource() {
    //then
    String actionAsignar = "asignar";

    Gastos gastos = new Gastos();
    gastos.setMonto("1.0");
    gastos.setMoneda("ARS");
    List<Gastos> gastosList = new ArrayList<>();
    gastosList.add(gastos);
    List<Gastos> gastosEmptyList = new ArrayList<>();

    return Stream.of(
        Arguments.of(actionAsignar, gastosList),
        Arguments.of(actionAsignar, gastosEmptyList)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Usuario usuario = new Usuario("0", "", "", 1, "", new ArrayList<>());
    cuponesAction.setSessionUserWorking(usuario);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute action")
  void shouldExecuteAction(String action, List<Cupones> cuponesList, String fechaDesde) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("idGasto")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("codMotivo")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("montoMin")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("moneda")).thenReturn("ARS");
    when(httpServletRequestMocked.getParameter("fechaDesde")).thenReturn(fechaDesde);
    when(httpServletRequestMocked.getParameter("fechaHasta")).thenReturn("08/08/2023");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(cuponesList);
        })) {
      //then
      ActionForward actionForwardToAssert = cuponesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked, samWebClientMocked,
          httpServletRequestMocked, httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionGastosSource")
  @DisplayName("Should execute action gastos")
  void shouldExecuteActionGastos(String action, List<Gastos> gastosList) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("idGasto")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("codMotivo")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("montoMin")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("moneda")).thenReturn("ARS");
    when(httpServletRequestMocked.getParameter("fechaDesde")).thenReturn("08/08/2023");
    when(httpServletRequestMocked.getParameter("fechaHasta")).thenReturn("08/08/2023");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(gastosList);
        })) {
      try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class,
          (mockPagosService, context) -> {
            doNothing().when(mockPagosService).asignarCupon(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(), anyString());
            when(mockPagosService.getMsg()).thenReturn("msg");
          })) {
        //then
        ActionForward actionForwardToAssert = cuponesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked, samWebClientMocked,
            httpServletRequestMocked, httpServletResponseMocked);
        assertNull(actionForwardToAssert);
      }
    }
  }

  @Test
  @DisplayName("Should catch exception")
  void shouldCatchException() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("consulta");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("idGasto")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("codMotivo")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("montoMin")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("moneda")).thenReturn("ARS");
    when(httpServletRequestMocked.getParameter("fechaDesde")).thenReturn("08/08/2023");
    when(httpServletRequestMocked.getParameter("fechaHasta")).thenReturn("08/08/2023");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    //then
    ActionForward actionForwardToAssert = cuponesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked, samWebClientMocked,
        httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}
