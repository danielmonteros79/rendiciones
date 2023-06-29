package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.OSCAR;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroGasto;
import com.sa.form.parametros.ParametrosGastosForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.ParametrosService;
import com.sa.services.trxs.SU85;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ParametrosGastosDetalleLoadActionTest {

  @Mock
  HttpServletResponse httpServletResponse;

  @Mock
  ManagerTransaction managerTransaction;

  @InjectMocks
  ParametrosGastosDetalleLoadAction parametrosGastosDetalleLoadAction;

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
    centroCostoList.add("dos");
    centroCostoList.add("tres");

    StringBuilder ristra= new StringBuilder();
    for (int i = 0; i < 18; i++) {
      ristra.append("iiii");
    }

    ParametrosGastosForm parametrosGastosFormAlta = new ParametrosGastosForm();
    parametrosGastosFormAlta.setEstado("");
    parametrosGastosFormAlta.setMotivo("");
    parametrosGastosFormAlta.setBack(false);
    parametrosGastosFormAlta.setAccion("alta");
    parametrosGastosFormAlta.setCentrosCostoList(centroCostoList);

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
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
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    ManagerTransaction manager = new ManagerTransaction(new SU85());
    ParametroGasto parametroGasto = (ParametroGasto) manager.getDataReturn();
    parametroGasto.setRistra(ristra.toString());

    return Stream.of(
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, requestAgregar, parametrosGastosFormAlta, combosList, printWriter, parametroGasto,
            usuario), //Agregar
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, requestBorrar, parametrosGastosFormAlta, combosList, printWriter, parametroGasto,
            usuario), //Borrar
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, request, parametrosGastosFormAlta, combosList, printWriter, parametroGasto,
            usuario) //Caso Base - Form Alta
                    );
  }

  public static Stream<Arguments> executeActionFormOptionsSource() {
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
    centroCostoList.add("dos");
    centroCostoList.add("tres");

    StringBuilder ristra= new StringBuilder();
    for (int i = 0; i < 18; i++) {
      ristra.append("iiii");
    }

    ParametrosGastosForm parametrosGastosFormAlta = new ParametrosGastosForm();
    parametrosGastosFormAlta.setEstado("");
    parametrosGastosFormAlta.setMotivo("");
    parametrosGastosFormAlta.setBack(false);
    parametrosGastosFormAlta.setAccion("alta");
    parametrosGastosFormAlta.setCentrosCostoList(centroCostoList);

    ParametrosGastosForm parametrosGastosFormBaja = new ParametrosGastosForm();
    parametrosGastosFormBaja.setEstado("");
    parametrosGastosFormBaja.setMotivo("");
    parametrosGastosFormBaja.setBack(false);
    parametrosGastosFormBaja.setAccion("baja");
    parametrosGastosFormBaja.setCentrosCostoList(centroCostoList);

    ParametrosGastosForm parametrosGastosFormMod = new ParametrosGastosForm();
    parametrosGastosFormMod.setEstado("");
    parametrosGastosFormMod.setMotivo("");
    parametrosGastosFormMod.setBack(false);
    parametrosGastosFormMod.setAccion("modificacion");
    parametrosGastosFormMod.setCentrosCostoList(centroCostoList);

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
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
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    ManagerTransaction manager = new ManagerTransaction(new SU85());
    ParametroGasto parametroGasto = (ParametroGasto) manager.getDataReturn();
    parametroGasto.setRistra(ristra.toString());

    return Stream.of(
        Arguments.of(actionMappingMod, samWebApplication, samWebClient, request, parametrosGastosFormMod, combosList, printWriter, parametroGasto, usuario),
        //Form Mod
         Arguments.of(actionMappingBaja, samWebApplication, samWebClient, request, parametrosGastosFormBaja, combosList, printWriter, parametroGasto, usuario)
        //Form Baja
                    );
  }

  public static Stream<Arguments> gastoToFormSource() {
    //given
    StringBuilder ristra= new StringBuilder();
    for (int i = 0; i < 18; i++) {
      ristra.append("iiii");
    }
    List<String> centroCostos = new ArrayList<>();
    List<String> combosList = new ArrayList<>();
    ParametrosGastosForm parametrosGastosForm = new ParametrosGastosForm();
    HttpServletRequest request = new MockHttpServletRequest();
    ManagerTransaction manager = new ManagerTransaction(new SU85());
    ParametroGasto parametroGasto = (ParametroGasto) manager.getDataReturn();
    parametroGasto.setEstado("Estado");
    parametroGasto.setCcostos("Costos");
    parametroGasto.setRistra(ristra.toString());
    parametroGasto.setOscar(new OSCAR("Oscar"));
    parametroGasto.setMaInclExcl("Excel");
    parametroGasto.setComprob("Comprobante");
    parametroGasto.setAntiguedad("Antiguedad");
    parametroGasto.setBimon("Bimon");
    parametroGasto.setAutoriz("Autoriz");
    parametroGasto.setObserv("Observ");
    parametroGasto.setNivelIngreso("Nivel");
    parametroGasto.setPlazoAprob("Plazo");
    parametroGasto.setCentrosCosto(centroCostos);

    return Stream.of(Arguments.of(parametrosGastosForm, request, manager, combosList));
  }

  public static Stream<Arguments> cargarCombosSource() {
    //given
    HttpServletRequest request = new MockHttpServletRequest();
    List<String> combosList = new ArrayList<>();
    combosList.add("MD0123456789");
    combosList.add("TC01234567890123456789012345678901234567890123456789");
    combosList.add("OB0123456789");
    return Stream.of(Arguments.of(request, combosList));
  }

  public static Stream<Arguments> borrarCentroCostoSource() {
    //given
    List<String> centroCostos = new ArrayList<>();
    centroCostos.add("CentroCosto0");
    centroCostos.add("CentroCosto1");
    centroCostos.add("CentroCosto2");
    ParametrosGastosForm parametrosGastosForm = new ParametrosGastosForm();
    parametrosGastosForm.setCentrosCostoList(centroCostos);

    return Stream.of(Arguments.of(parametrosGastosForm, 0));
  }

  public static Stream<Arguments> agregarCentroCostoSource() {
    //given
    List<String> centroCostosShortList = new ArrayList<>();
    centroCostosShortList.add("CentroCosto0");
    centroCostosShortList.add("CentroCosto1");
    centroCostosShortList.add("CentroCosto2");
    ParametrosGastosForm parametrosGastosFormShortList = new ParametrosGastosForm();
    parametrosGastosFormShortList.setCentrosCostoList(centroCostosShortList);
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));

    List<String> centroCostosLongList = new ArrayList<>();
    do {
      centroCostosLongList.add("");
    } while (centroCostosLongList.size() < 15);
    ParametrosGastosForm parametrosGastosFormLongList = new ParametrosGastosForm();
    parametrosGastosFormLongList.setCentrosCostoList(centroCostosLongList);

    return Stream.of(
        Arguments.of(parametrosGastosFormShortList, printWriter),
        Arguments.of(parametrosGastosFormLongList, printWriter)
        );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action execute")
  void shouldDetermineWhatActionExecute(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request,
                                        ParametrosGastosForm parametrosGastosForm, List<String> combosList, PrintWriter printWriter,
                                        ParametroGasto parametroGasto, Usuario usuario) throws Exception {
    //given
    Method cargarCombosMocked = ParametrosGastosDetalleLoadAction.class.getDeclaredMethod("cargarCombos", HttpServletRequest.class, List.class);
    cargarCombosMocked.setAccessible(true);
    cargarCombosMocked.invoke(parametrosGastosDetalleLoadAction, request, combosList);
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(managerTransaction.getDataReturn()).thenReturn(parametroGasto);
          when(managerTransaction.getDataReturnList()).thenReturn(combosList);
          when(mockParametrosService.getGastosCombos()).thenReturn(combosList);
          when(mockParametrosService.getMsgAviso()).thenReturn("Aviso");
          when(mockParametrosService.loadModificacionGasto(parametrosGastosForm.getCodigo(), usuario.getIdUser())).thenReturn(managerTransaction);
          when(httpServletResponse.getWriter()).thenReturn(printWriter);
        })) {
      //then
      ActionForward actionForwardToAssert = parametrosGastosDetalleLoadAction.executeAction(actionMapping, parametrosGastosForm, samApplication, samClient,
          request, httpServletResponse);
      if (request.getParameter("accionJson").equals("borrarCentroCosto") || request.getParameter("accionJson").equals("agregarCentroCosto")) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionFormOptionsSource")
  @DisplayName("Should determine the action to execute with different form actions")
  void shouldDetermineTheActionToExecuteWithDifferentFormActions(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request,
                                                                 ParametrosGastosForm parametrosGastosForm, List<String> combosList, PrintWriter printWriter,
                                                                 ParametroGasto parametroGasto, Usuario usuario) throws Exception {
    //given
    Method cargarCombosMocked = ParametrosGastosDetalleLoadAction.class.getDeclaredMethod("cargarCombos", HttpServletRequest.class, List.class);
    cargarCombosMocked.setAccessible(true);
    cargarCombosMocked.invoke(parametrosGastosDetalleLoadAction, request, combosList);
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(managerTransaction.getDataReturn()).thenReturn(parametroGasto);
          when(managerTransaction.getDataReturnList()).thenReturn(combosList);
          when(mockParametrosService.getGastosCombos()).thenReturn(combosList);
          when(mockParametrosService.getMsgAviso()).thenReturn("Aviso");
          when(mockParametrosService.loadModificacionGasto(parametrosGastosForm.getCodigo(), usuario.getIdUser())).thenReturn(managerTransaction);
          when(mockParametrosService.loadBajaGasto(parametrosGastosForm.getCodigo(), usuario.getIdUser())).thenReturn(managerTransaction);
          when(httpServletResponse.getWriter()).thenReturn(printWriter);
        })) {
      //then
      ActionForward actionForwardToAssert = parametrosGastosDetalleLoadAction.executeAction(actionMapping, parametrosGastosForm, samApplication, samClient,
          request, httpServletResponse);
      assertNotNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("gastoToFormSource")
  @DisplayName("Should assign Gastos to Form")
  void shouldAssignGastosToForm(ParametrosGastosForm parametrosGastosForm, HttpServletRequest request, ManagerTransaction managerTransaction,
                                List<String> combosList) throws Exception {
    //given
    Method gastoToFormMocked = ParametrosGastosDetalleLoadAction.class.getDeclaredMethod("gastoToForm", ParametrosGastosForm.class, HttpServletRequest.class, ManagerTransaction.class);
    gastoToFormMocked.setAccessible(true);
    gastoToFormMocked.invoke(parametrosGastosDetalleLoadAction, parametrosGastosForm, request, managerTransaction);
    //then
    assertAll(() -> assertNotNull(parametrosGastosForm),
        () -> assertNotNull(request),
        () -> assertNotNull(managerTransaction),
        () -> assertNotNull(combosList));
  }

  @ParameterizedTest
  @MethodSource("cargarCombosSource")
  @DisplayName("Should save combos in their respective List")
  void shouldSaveCombosInTheirRespectiveList(HttpServletRequest request, List<String> combosList) throws Exception {
    //given
    Method cargarCombosMocked = ParametrosGastosDetalleLoadAction.class.getDeclaredMethod("cargarCombos", HttpServletRequest.class, List.class);
    cargarCombosMocked.setAccessible(true);
    cargarCombosMocked.invoke(parametrosGastosDetalleLoadAction, request, combosList);
    //then
    assertAll(() -> assertNotNull(request),
        () -> assertNotNull(combosList));
  }

  @ParameterizedTest
  @MethodSource("borrarCentroCostoSource")
  @DisplayName("Should remove an element from CentroCostoList")
  void shouldRemoveAnElementFromCentroCostoList(ParametrosGastosForm parametrosGastosForm, int index) throws Exception {
    //given
    Method borrarCentroCostoMocked = ParametrosGastosDetalleLoadAction.class.getDeclaredMethod("borrarCentroCosto", ParametrosGastosForm.class, int.class);
    borrarCentroCostoMocked.setAccessible(true);
    //then
    ActionForward actionForwardToAssert = (ActionForward) borrarCentroCostoMocked.invoke(parametrosGastosDetalleLoadAction, parametrosGastosForm , index);
    assertNull(actionForwardToAssert);
  }

  @ParameterizedTest
  @MethodSource("agregarCentroCostoSource")
  @DisplayName("Should add CentroCostos to Form")
  void shouldAddCentroCostosToForm(ParametrosGastosForm parametrosGastosForm, PrintWriter printWriter) throws Exception {
    //when
    when(httpServletResponse.getWriter()).thenReturn(printWriter);
    //then
    Method agregarCentroCostoMocked = ParametrosGastosDetalleLoadAction.class.getDeclaredMethod("agregarCentroCosto", ParametrosGastosForm.class, HttpServletResponse.class);
    agregarCentroCostoMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) agregarCentroCostoMocked.invoke(parametrosGastosDetalleLoadAction, parametrosGastosForm, httpServletResponse);
    assertNull(actionForwardToAssert);
  }
}
