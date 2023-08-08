package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.*;
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
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CombosActionTest {

  @Mock
  HttpSession httpSessionMocked;
  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMocked;
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
  CombosAction combosAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String actionGetTiposGasto = "getTiposGasto";
    String actionGetDelegados = "getDelegados";

    Usuario usuario = new Usuario("", "", "", 1, "", new ArrayList<>());
    ComboGasto comboGasto = new ComboGasto();
    List<ComboGasto> comboGastoList = new ArrayList<>();
    comboGastoList.add(comboGasto);

    return Stream.of(
        Arguments.of(actionGetTiposGasto, comboGastoList, usuario),  // sessionUserWorking lanza NPE
        Arguments.of(actionGetDelegados, comboGastoList, usuario) // sessionUser lanza NPE
                    );
  }

  public static Stream<Arguments> executeActionComboOpcion2Source() {
    //given
    String actionGetMonedas = "getMonedas";
    String actionGetTiposComprobante = "getTiposComprobante";

    Usuario usuario = new Usuario("", "", "", 1, "", new ArrayList<>());
    ComboOpcion2 comboOpcion2 = new ComboOpcion2();
    List<ComboOpcion2> comboOpcion2List = new ArrayList<>();
    comboOpcion2List.add(comboOpcion2);

    return Stream.of(
        Arguments.of(actionGetMonedas, comboOpcion2List, usuario),
        Arguments.of(actionGetTiposComprobante, comboOpcion2List, usuario)
                    );
  }

  public static Stream<Arguments> executeActionComboMotivoSource() {
    //given
    String actionGetMotivos = "getMotivos";

    Usuario usuario = new Usuario("", "", "", 1, "", new ArrayList<>());
    ComboMotivo comboMotivo = new ComboMotivo();
    List<ComboMotivo> comboMotivosList = new ArrayList<>();
    comboMotivosList.add(comboMotivo);

    return Stream.of(Arguments.of(actionGetMotivos, comboMotivosList, usuario));  // sessionUserWorking lanza NPE
  }

  public static Stream<Arguments> executeActionComboOpcionSource() {
    //given
    String actionGetFechasResumenes = "getFechasResumenes";

    Usuario usuario = new Usuario("", "", "", 1, "", new ArrayList<>());
    ComboOpcion comboOpcion = new ComboOpcion();
    List<ComboOpcion> comboOpcionList = new ArrayList<>();
    comboOpcionList.add(comboOpcion);

    return Stream.of(Arguments.of(actionGetFechasResumenes, comboOpcionList, usuario));  // sessionUserWorking lanza NPE
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute action")
  void shouldExecuteAction(String action, List<ComboGasto> comboGastoList, Usuario usuario) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("codMotivo")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuario);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(comboGastoList);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
      //then
      ActionForward actionForwardToAssert = combosAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked, samWebClientMocked,
          httpServletRequestMocked, httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionComboOpcion2Source")
  @DisplayName("Should execute action comboOpcion2")
  void shouldExecuteActionComboOpcion2(String action, List<ComboOpcion2> comboOpcion2List, Usuario usuario) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("codMotivo")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuario);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(comboOpcion2List);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
      //then
      ActionForward actionForwardToAssert = combosAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked, samWebClientMocked,
          httpServletRequestMocked, httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionComboMotivoSource")
  @DisplayName("Should execute action comboMotivo")
  void shouldExecuteActionComboMotivo(String action, List<ComboMotivo> comboMotivoList, Usuario usuario) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("codMotivo")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuario);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(comboMotivoList);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
      //then
      ActionForward actionForwardToAssert = combosAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked, samWebClientMocked,
          httpServletRequestMocked, httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionComboOpcionSource")
  @DisplayName("Should execute action comboOpcion")
  void shouldExecuteActionComboOpcion(String action, List<ComboOpcion> comboOpcionList, Usuario usuario) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("codMotivo")).thenReturn("1");
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("userWorking")).thenReturn(usuario);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(comboOpcionList);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
      //then
      ActionForward actionForwardToAssert = combosAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked, samWebClientMocked,
          httpServletRequestMocked, httpServletResponseMocked);
      assertNull(actionForwardToAssert);
    }
  }
}
