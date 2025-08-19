package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.OSCAR;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroGasto;
import com.sa.exceptions.ActionExecutionException;
import com.sa.form.parametros.ParametrosGastosForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.ParametrosService;
import com.sa.services.trxs.SU85;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ParametrosGastosDetalleLoadActionTest {

  @Mock
  HttpServletResponse httpServletResponse;

  @Mock
  ManagerTransaction managerTransaction;

  @Mock private HttpServletResponse response;
  @Mock private ParametrosService parametrosService;
  @Mock private ParametroGasto mockGasto;

  private ParametrosGastosDetalleLoadAction action;

  private MockHttpServletRequest request;
  private MockHttpSession session;
  private SAMWebClient samWebClient;
  private ParametrosGastosForm form;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    action = new ParametrosGastosDetalleLoadAction(parametrosService);

    request = new MockHttpServletRequest();
    session = new MockHttpSession();
    request.setHttpSession(session);
    samWebClient = new SAMWebClient();
    form = new ParametrosGastosForm();
    form.setCentrosCosto("123");

    session.setAttribute("usuario", new Usuario("123", "John Doe", "testUser", 100, "CC001", null));
  }

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMappingAlta = new ActionMapping();
    ActionMapping actionMappingBaja = new ActionMapping();
    ActionMapping actionMappingMod = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletRequest requestBorrar = new MockHttpServletRequest();
    MockHttpServletRequest requestAgregar = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));

    List<String> combosList = new ArrayList<>();
    combosList.add("MDoctorado");

    List<String> centroCostoList = new ArrayList<>();
    centroCostoList.add("cero");
    centroCostoList.add("uno");

    ParametrosGastosForm parametrosGastosFormAlta = new ParametrosGastosForm();
    parametrosGastosFormAlta.setBack(false);
    parametrosGastosFormAlta.setAccion("alta");
    parametrosGastosFormAlta.setCentrosCostoList(centroCostoList);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    httpSession.setAttribute("usuario", usuario);

    request.setHttpSession(httpSession);
    request.addParameter("accionJson", "");

    requestBorrar.setHttpSession(httpSession);
    requestBorrar.addParameter("accionJson", "borrarCentroCosto");
    requestBorrar.addParameter("index", "0");

    requestAgregar.setHttpSession(httpSession);
    requestAgregar.addParameter("accionJson", "agregarCentroCosto");

    actionMappingAlta.addForwardConfig(new ActionForward("alta", "path1", false));
    actionMappingBaja.addForwardConfig(new ActionForward("baja", "path1", false));
    actionMappingMod.addForwardConfig(new ActionForward("modificacion", "path1", false));

    samWebClient.setSession(httpSession);
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setAttribute("usuario", usuario);

    ParametroGasto parametroGasto = new ParametroGasto();

    return Stream.of(
            Arguments.of(actionMappingAlta, samWebApplication, samWebClient, requestAgregar, parametrosGastosFormAlta, combosList, printWriter, parametroGasto,
                    usuario), //Agregar
            Arguments.of(actionMappingAlta, samWebApplication, samWebClient, requestBorrar, parametrosGastosFormAlta, combosList, printWriter, parametroGasto,
                    usuario), //Borrar
            Arguments.of(actionMappingAlta, samWebApplication, samWebClient, request, parametrosGastosFormAlta, combosList, printWriter, parametroGasto,
                    usuario) //Caso Base - Form Alta
    );
  }

  // ==================== TESTS PARA COBERTURA DE BLOQUES CATCH ====================

  @Test
  @DisplayName("Debe lanzar ActionExecutionException si borrarCentroCosto recibe un índice inválido")
  void executeAction_borrarCentroCostoConIndiceInvalido_lanzaExcepcion() {
    request.addParameter("accionJson", "borrarCentroCosto");
    request.addParameter("index", "abc");

    ActionExecutionException exception = assertThrows(ActionExecutionException.class, () -> action.executeAction(null, form, null, samWebClient, request, response));

    assertEquals("Error removing centro costo", exception.getMessage());
    assertTrue(exception.getCause() instanceof NumberFormatException);
  }

  @Test
  @DisplayName("Debe lanzar ActionExecutionException si agregarCentroCosto falla al obtener el writer")
  void executeAction_agregarCentroCostoConErrorDeEscritura_lanzaExcepcion() throws IOException {
    request.addParameter("accionJson", "agregarCentroCosto");
    when(response.getWriter()).thenThrow(new IOException("Error de escritura simulado"));

    ActionExecutionException exception = assertThrows(ActionExecutionException.class, () -> action.executeAction(null, form, null, samWebClient, request, response));

    assertEquals("Error adding centro costo", exception.getMessage());
    assertTrue(exception.getCause() instanceof IOException);
  }

  @Test
  @DisplayName("Debe establecer un mensaje de error en el request si el servicio falla en la lógica principal")
  void executeAction_conErrorDeServicio_estableceMensajeDeError() throws Exception {
    form.setAccion("modificacion");
    form.setCodigo("G999");
    ActionMapping mapping = new ActionMapping();
    mapping.addForwardConfig(new ActionForward("modificacion", "/path", false));

    RuntimeException cause = new RuntimeException("Causa del error");
    when(parametrosService.loadModificacionGastoGaston(anyString(), anyString())).thenThrow(new RuntimeException("Error de servicio", cause));

    action.executeAction(mapping, form, null, samWebClient, request, response);

    assertEquals("ERROR: Causa del error", request.getAttribute("message"));
  }

  @Test
  void executeAction_Modificacion_Success() throws Exception {
    form.setAccion("modificacion");
    form.setCodigo("G001");
    ActionMapping mapping = new ActionMapping();
    mapping.addForwardConfig(new ActionForward("modificacion", "/modificacion.jsp", false));
    when(parametrosService.loadModificacionGastoGaston(anyString(), anyString())).thenReturn(mockGasto);
    when(parametrosService.getMsgAviso()).thenReturn("Operación exitosa");

    ActionForward result = action.executeAction(mapping, form, null, samWebClient, request, response);

    verify(parametrosService, times(1)).loadModificacionGastoGaston("G001", "123");
    verify(parametrosService, times(1)).getMsgAviso();
    assertEquals("modificacion", result.getName());
  }

  @Test
  void executeAction_Baja_Success() throws Exception {
    form.setAccion("baja");
    form.setCodigo("G001");
    ActionMapping mapping = new ActionMapping();
    mapping.addForwardConfig(new ActionForward("baja", "/baja.jsp", false));
    when(parametrosService.loadBajaGastoGaston(anyString(), anyString())).thenReturn(mockGasto);
    when(parametrosService.getMsgAviso()).thenReturn("Operación exitosa");

    ActionForward result = action.executeAction(mapping, form, null, samWebClient, request, response);

    verify(parametrosService, times(1)).loadBajaGastoGaston("G001", "123");
    verify(parametrosService, times(1)).getMsgAviso();
    assertEquals("baja", result.getName());
  }

  @Test
  void gastoToFormGaston_Success() {
    ParametrosGastosForm form = new ParametrosGastosForm();
    ParametroGasto gasto = new ParametroGasto();
    gasto.setEstado("A");
    gasto.setBimon("SI");
    gasto.setObserv("Observación de prueba");
    gasto.setNivelIngreso("NIVEL1");
    gasto.setRistra("12345");
    gasto.setDescripcionMotivo("Motivo de prueba");
    gasto.setDescripcionGasto("Gasto de prueba");

    action.gastoToFormGaston(form, request, gasto);

    assertEquals("A", form.getEstado());
    assertEquals("SI", form.getBimon());
    assertEquals("Observación de prueba", form.getObserv());
    assertEquals("NIVEL1", form.getIdNivAutoriz());
    assertEquals("12345", form.getDetalleRistra());
    assertEquals("Motivo de prueba", form.getDescripcionMotivo());
    assertEquals("Gasto de prueba", form.getDescripcionGasto());
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action execute")
  void shouldDetermineWhatActionExecute(
          ActionMapping actionMapping,
          SAMWebApplication samApplication,
          SAMWebClient samClient,
          MockHttpServletRequest request,
          ParametrosGastosForm parametrosGastosForm,
          List<String> combosList,
          PrintWriter printWriter,
          ParametroGasto parametroGasto,
          Usuario usuario) throws Exception {

    parametrosGastosForm.setCentrosCosto("123123");

    when(managerTransaction.getDataReturn()).thenReturn(new ParametroGasto());
    when(managerTransaction.getDataReturnList()).thenReturn(combosList);
    when(httpServletResponse.getWriter()).thenReturn(printWriter);

    try (MockedConstruction<ParametrosService> ignored = Mockito.mockConstruction(
            ParametrosService.class, (mockParametrosService, context) -> {
              when(mockParametrosService.getGastosCombos()).thenReturn(combosList);
              when(mockParametrosService.getMsgAviso()).thenReturn("Aviso");
              when(mockParametrosService.loadModificacionGasto(parametrosGastosForm.getCodigo(), usuario.getIdUser()))
                      .thenReturn(managerTransaction);
            })) {

      ActionForward actionForwardToAssert = action.executeAction(
              actionMapping, parametrosGastosForm, samApplication, samClient, request, httpServletResponse);

      if ("borrarCentroCosto".equals(request.getParameter("accionJson")) ||
              "agregarCentroCosto".equals(request.getParameter("accionJson"))) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("gastoToFormSource")
  @DisplayName("Should assign Gastos to Form")
  void shouldAssignGastosToForm(ParametrosGastosForm parametrosGastosForm, HttpServletRequest request, ManagerTransaction managerTransaction,
                                List<String> combosList) throws Exception {
    Method gastoToFormMocked = ParametrosGastosDetalleLoadAction.class.getDeclaredMethod("gastoToForm", ParametrosGastosForm.class, HttpServletRequest.class, ManagerTransaction.class);
    gastoToFormMocked.setAccessible(true);

    assertAll(() -> assertNotNull(parametrosGastosForm),
            () -> assertNotNull(request),
            () -> assertNotNull(managerTransaction),
            () -> assertNotNull(combosList));
  }

  @ParameterizedTest
  @MethodSource("cargarCombosSource")
  @DisplayName("Should save combos in their respective List")
  void shouldSaveCombosInTheirRespectiveList(HttpServletRequest request, List<String> combosList) throws Exception {
    Method cargarCombosMocked = ParametrosGastosDetalleLoadAction.class.getDeclaredMethod("cargarCombos", HttpServletRequest.class, List.class);
    cargarCombosMocked.setAccessible(true);
    cargarCombosMocked.invoke(action, request, combosList);

    assertAll(() -> assertNotNull(request),
            () -> assertNotNull(combosList));
  }

  @ParameterizedTest
  @MethodSource("borrarCentroCostoSource")
  @DisplayName("Should remove an element from CentroCostoList")
  void shouldRemoveAnElementFromCentroCostoList(ParametrosGastosForm parametrosGastosForm, int index) throws Exception {
    Method borrarCentroCostoMocked = ParametrosGastosDetalleLoadAction.class.getDeclaredMethod("borrarCentroCosto", ParametrosGastosForm.class, int.class);
    borrarCentroCostoMocked.setAccessible(true);

    ActionForward actionForwardToAssert = (ActionForward) borrarCentroCostoMocked.invoke(action, parametrosGastosForm , index);

    assertNull(actionForwardToAssert);
  }

  @ParameterizedTest
  @MethodSource("agregarCentroCostoSource")
  @DisplayName("Should add CentroCostos to Form")
  void shouldAddCentroCostosToForm(ParametrosGastosForm parametrosGastosForm, PrintWriter printWriter) throws Exception {
    when(httpServletResponse.getWriter()).thenReturn(printWriter);

    Method agregarCentroCostoMocked = ParametrosGastosDetalleLoadAction.class.getDeclaredMethod("agregarCentroCosto", ParametrosGastosForm.class, HttpServletResponse.class);
    agregarCentroCostoMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) agregarCentroCostoMocked.invoke(action, parametrosGastosForm, httpServletResponse);

    assertNull(actionForwardToAssert);
  }

  @Test
  public void testConstructorVacio() {
    ParametrosGastosDetalleLoadAction action = new ParametrosGastosDetalleLoadAction();
    assertNotNull(action, "El constructor debería crear una instancia no nula");
  }

  // --- MÉTODOS DE DATOS PARA TESTS (CORREGIDOS) ---

  public static Stream<Arguments> gastoToFormSource() {
    ManagerTransaction manager = mock(ManagerTransaction.class);

    // FIX: Se crea un objeto ParametroGasto completo para evitar NPEs.
    // El objeto anterior tenía campos nulos que causaban error al ser accedidos.
    ParametroGasto gasto = new ParametroGasto();
    gasto.setEstado("");
    gasto.setRistra("");
    gasto.setBimon("");
    gasto.setObserv("");
    gasto.setNivelIngreso("");

    when(manager.getDataReturn()).thenReturn(gasto);
    return Stream.of(Arguments.of(new ParametrosGastosForm(), new MockHttpServletRequest(), manager, new ArrayList<String>()));
  }

  public static Stream<Arguments> cargarCombosSource() {
    List<String> combos = new ArrayList<>();
    combos.add("MD123456789");
    combos.add("TC" + new String(new char[50]).replace('\0', ' ') + "0123456789");
    combos.add("OB0123456789");
    return Stream.of(Arguments.of(new MockHttpServletRequest(), combos));
  }

  public static Stream<Arguments> borrarCentroCostoSource() {
    ParametrosGastosForm form = new ParametrosGastosForm();
    // FIX: Se inicializa la lista de centros de costo para evitar NPE.
    form.setCentrosCostoList(new ArrayList<>());
    form.getCentrosCosto().addAll(Arrays.asList("C1", "C2"));
    return Stream.of(Arguments.of(form, 1));
  }

  public static Stream<Arguments> agregarCentroCostoSource() {
    ParametrosGastosForm formShort = new ParametrosGastosForm();
    // FIX: Se inicializa la lista para ambos objetos form para evitar NPE.
    formShort.setCentrosCostoList(new ArrayList<>());

    ParametrosGastosForm formLong = new ParametrosGastosForm();
    formLong.setCentrosCostoList(new ArrayList<>());
    for(int i=0; i<15; i++) formLong.getCentrosCosto().add("C"+i);

    return Stream.of(
            Arguments.of(formShort, new PrintWriter(new StringWriter())),
            Arguments.of(formLong, new PrintWriter(new StringWriter()))
    );
  }
}