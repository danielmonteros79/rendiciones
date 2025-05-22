package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Gastos;
import com.sa.entities.Usuario;
import com.sa.form.RendicionDetalleForm;
import com.sa.manager.ManagerTransaction;
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
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class GastosActionTest {
  
  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMock;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  RendicionDetalleForm rendicionDetalleFormMocked;
  @InjectMocks
  GastosAction gastosAction;
  
  public static Stream<Arguments> executeActionSource() {
    //given
    String opcion = "";
    String opcionAlta = "ALTA";
    String opcionBaja = "BAJA";
    String opcionCons = "CONS";
    
    RendicionDetalleForm rendicionDetalleForm = new RendicionDetalleForm();
    rendicionDetalleForm.setIdRendicion("0");
    rendicionDetalleForm.setCentroCostos("0");
    rendicionDetalleForm.setImporteCupon("2,5");
    rendicionDetalleForm.setFechaGasto("08/08/2023");
    rendicionDetalleForm.setCostosDestino("0");
    rendicionDetalleForm.setMonto("0");
    rendicionDetalleForm.setGasto("GASTOGASTOGASTOGASTOGASTOGASTOGASTOGASTOGASTOGASTOGASTOGASTOGASTO");
    rendicionDetalleForm.setIdGasto("0");
    rendicionDetalleForm.setTipoComprobante("0006");
    rendicionDetalleForm.setFactura("000009");
    rendicionDetalleForm.setObservacionGasto("");
    rendicionDetalleForm.setCupon("0");
    rendicionDetalleForm.setImporteCupon("2.5");
    rendicionDetalleForm.setCupDeb("0");
    rendicionDetalleForm.setCupCred("1");
    
    Gastos gastos = new Gastos();
    List<Gastos> gastosList = new ArrayList<>();
    gastosList.add(gastos);
    
    return Stream.of(
      Arguments.of(opcion, rendicionDetalleForm, gastosList),
      Arguments.of(opcionAlta, rendicionDetalleForm, gastosList),
      Arguments.of(opcionBaja, rendicionDetalleForm, gastosList),
      Arguments.of(opcionCons, rendicionDetalleForm,gastosList),
      Arguments.of(opcionCons, rendicionDetalleForm, new ArrayList<>())
                    );
  }
  
  public static Stream<Arguments> executeActionExceptionSource() {
    //given
    String opcionAlta = "ALTA";
    RendicionDetalleForm rendicionDetalleForm = new RendicionDetalleForm();
    
    return Stream.of(Arguments.of(opcionAlta, rendicionDetalleForm));
  }
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Usuario usuario = new Usuario("", "", "", 1, "", new ArrayList<>());
    usuario.setIdUser("0");
    gastosAction.setSessionUserWorking(usuario);
  }
  
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute action")
  void shouldExecuteAction(String opcion, RendicionDetalleForm rendicionDetalleForm, List<Gastos> gastosList) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("opcion")).thenReturn(opcion);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
      (mockManagerTransaction, context) -> {
        doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
        when(mockManagerTransaction.getDataReturn()).thenReturn(0);
        when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        when(mockManagerTransaction.getDataReturnList()).thenReturn(gastosList);
      })) {
      
      //then
      ActionForward actionForwardToAssert = gastosAction.executeAction(actionMappingMock, rendicionDetalleForm, samWebApplicationMocked,
        samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }
  
  @Test
  @DisplayName("Should catch an Exception")
  void shouldCatchAnException() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("opcion")).thenReturn("CONS");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = gastosAction.executeAction(actionMappingMock, rendicionDetalleFormMocked, samWebApplicationMocked,
      samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}
