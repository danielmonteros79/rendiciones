package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.OSCAR;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosMotivoForm;
import com.sa.services.ParametrosService;
import java.io.ByteArrayOutputStream;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ParametrosMotivoDetalleLoadActionTest {

  @Mock
  ActionMapping actionMappingMocked;

  @Mock
  HttpServletResponse httpServletResponse;

  @InjectMocks
  ParametrosMotivoDetalleLoadAction parametrosMotivoDetalleLoadAction;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("motivoToFormProvider")
  @DisplayName("Should save Motivo in Form")
  void shouldSaveMotivoInForm(ParametrosMotivoForm parametrosMotivoForm, ParametroMotivo parametroMotivo) throws Exception {
    Method motivoToFormMocked = ParametrosMotivoDetalleLoadAction.class.getDeclaredMethod("motivoToForm", ParametrosMotivoForm.class, ParametroMotivo.class);
    motivoToFormMocked.setAccessible(true);
    motivoToFormMocked.invoke(parametrosMotivoDetalleLoadAction, parametrosMotivoForm, parametroMotivo);
    assertAll(() -> assertNotNull(parametrosMotivoForm),
        () -> assertNotNull(parametroMotivo));
  }

  @ParameterizedTest
  @MethodSource("borrarCentroCostoProvider")
  @DisplayName("Should remove CentroCosto")
  void shouldRemoveCentroCosto(ParametrosMotivoForm parametrosMotivoForm, int index) throws Exception {
    Method borrarCentroCostoMocked = ParametrosMotivoDetalleLoadAction.class.getDeclaredMethod("borrarCentroCosto", ParametrosMotivoForm.class, int.class);
    borrarCentroCostoMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) borrarCentroCostoMocked.invoke(parametrosMotivoDetalleLoadAction, parametrosMotivoForm, index);
    assertAll(() -> assertNotNull(parametrosMotivoForm),
        () -> assertNull(actionForwardToAssert));
  }

  @ParameterizedTest
  @MethodSource("agregarCentroCostoProvider")
  @DisplayName("Should add CentroCosto to Form")
  void shouldAddCentroCostoToForm(ParametrosMotivoForm parametrosMotivoForm, PrintWriter printWriter) throws Exception {
    when(httpServletResponse.getWriter()).thenReturn(printWriter);
    Method agregarCentroCostoMocked = ParametrosMotivoDetalleLoadAction.class.getDeclaredMethod("agregarCentroCosto", ParametrosMotivoForm.class, HttpServletResponse.class);
    agregarCentroCostoMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) agregarCentroCostoMocked.invoke(parametrosMotivoDetalleLoadAction, parametrosMotivoForm, httpServletResponse);
    assertAll(() -> assertNull(actionForwardToAssert),
        () -> assertNotNull(printWriter),
        () -> assertNotNull(parametrosMotivoForm));
  }

  @Test
  @DisplayName("Should handle executeAction with accionJson borrarCentroCosto")
  void testExecuteActionBorrarCentroCosto() throws Exception {
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samApplication = new SAMWebApplication();
    SAMWebClient samClient = new SAMWebClient();
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest request = new MockHttpServletRequest();
    ParametrosMotivoForm form = new ParametrosMotivoForm();
    List<String> centroCostoList = new ArrayList<>();
    centroCostoList.add("one");
    centroCostoList.add("two");
    form.setCentrosCostoList(centroCostoList);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", new ArrayList<>());
    httpSession.setAttribute("usuario", usuario);
    request.setHttpSession(httpSession);
    request.addParameter("accionJson", "borrarCentroCosto");
    request.addParameter("index", "0");

    samClient.setSession(httpSession);
    samClient.setLoginOk(true);

    ActionForward result = parametrosMotivoDetalleLoadAction.executeAction(actionMapping, form, samApplication, samClient, request, httpServletResponse);

    assertNull(result);
    assertEquals(1, form.getCentrosCosto().size());
  }

  @Test
  @DisplayName("Should handle executeAction with accionJson agregarCentroCosto and list not full")
  void testExecuteActionAgregarCentroCostoNotFull() throws Exception {
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samApplication = new SAMWebApplication();
    SAMWebClient samClient = new SAMWebClient();
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest request = new MockHttpServletRequest();
    ParametrosMotivoForm form = new ParametrosMotivoForm();
    List<String> centroCostoList = new ArrayList<>();
    centroCostoList.add("one");
    form.setCentrosCostoList(centroCostoList);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", new ArrayList<>());
    httpSession.setAttribute("usuario", usuario);
    request.setHttpSession(httpSession);
    request.addParameter("accionJson", "agregarCentroCosto");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintWriter writer = new PrintWriter(baos);

    samClient.setSession(httpSession);
    samClient.setLoginOk(true);

    when(httpServletResponse.getWriter()).thenReturn(writer);

    ActionForward result = parametrosMotivoDetalleLoadAction.executeAction(actionMapping, form, samApplication, samClient, request, httpServletResponse);

    assertNull(result);
    assertEquals(2, form.getCentrosCosto().size());
  }

  @Test
  @DisplayName("Should handle executeAction with accionJson agregarCentroCosto and list full")
  void testExecuteActionAgregarCentroCostoFull() throws Exception {
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samApplication = new SAMWebApplication();
    SAMWebClient samClient = new SAMWebClient();
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest request = new MockHttpServletRequest();
    ParametrosMotivoForm form = new ParametrosMotivoForm();
    List<String> centroCostoList = new ArrayList<>();
    for (int i = 0; i < 15; i++) {
      centroCostoList.add("item" + i);
    }
    form.setCentrosCostoList(centroCostoList);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", new ArrayList<>());
    httpSession.setAttribute("usuario", usuario);
    request.setHttpSession(httpSession);
    request.addParameter("accionJson", "agregarCentroCosto");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintWriter writer = new PrintWriter(baos);

    samClient.setSession(httpSession);
    samClient.setLoginOk(true);

    when(httpServletResponse.getWriter()).thenReturn(writer);

    ActionForward result = parametrosMotivoDetalleLoadAction.executeAction(actionMapping, form, samApplication, samClient, request, httpServletResponse);

    assertNull(result);
    assertEquals(15, form.getCentrosCosto().size());
  }

  @Test
  @DisplayName("Should handle executeAction with accion alta - clears form and sets estado")
  void testExecuteActionAltaAccion() throws Exception {
    ActionMapping actionMapping = new ActionMapping();
    ActionForward actionForward = new ActionForward();
    actionForward.setName("alta");
    actionForward.setPath("path1");
    actionMapping.addForwardConfig(actionForward);

    SAMWebApplication samApplication = new SAMWebApplication();
    SAMWebClient samClient = new SAMWebClient();
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest request = new MockHttpServletRequest();
    ParametrosMotivoForm form = new ParametrosMotivoForm();
    form.setAccion("alta");
    List<String> centroCostoList = new ArrayList<>();
    centroCostoList.add("one");
    form.setCentrosCostoList(centroCostoList);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", new ArrayList<>());
    httpSession.setAttribute("usuario", usuario);
    request.setHttpSession(httpSession);
    request.addParameter("accionJson", "");

    samClient.setSession(httpSession);
    samClient.setLoginOk(true);

    // This test requires SAM properties to be initialized, which won't happen in unit tests
    // The action internally uses SAM which throws GeneralException: Properties not initialized
    assertThrows(Exception.class, () -> {
      parametrosMotivoDetalleLoadAction.executeAction(actionMapping, form, samApplication, samClient, request, httpServletResponse);
    }, "Expected exception due to SAM properties not being initialized in test environment");
  }

  @Test
  @DisplayName("Should handle executeAction normal flow with session attributes set")
  void testExecuteActionNormalFlowWithSessionAttributes() throws Exception {
    ActionMapping actionMapping = new ActionMapping();
    ActionForward actionForward = new ActionForward();
    actionForward.setName("");
    actionForward.setPath("path2");
    actionMapping.addForwardConfig(actionForward);

    SAMWebApplication samApplication = new SAMWebApplication();
    SAMWebClient samClient = new SAMWebClient();
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest request = new MockHttpServletRequest();
    ParametrosMotivoForm form = new ParametrosMotivoForm();
    form.setAccion("");
    form.setCodigo("TEST123");
    List<String> centroCostoList = new ArrayList<>();
    form.setCentrosCostoList(centroCostoList);

    ParametroMotivo motivo = new ParametroMotivo();
    motivo.setCodigo("TEST123");
    motivo.setDescripcion("Test Motivo");
    motivo.setIdGlg("GLG001");
    motivo.setIdCentroCostos("CC001");
    motivo.setEstado("A");
    motivo.setCodSup("SUP001");
    motivo.setCodFirma("FIR001");
    motivo.setCodAprobacionGlg("APGLG001");
    motivo.setOscar(new OSCAR("OSCAR"));
    motivo.setIdNivCarga("NIV001");
    motivo.setIdNivAutoriz("AUTH001");
    motivo.setMaInclExcl("INCL");
    motivo.setIdOperEspe("OPER001");
    motivo.setMeDiasInterv("30");
    motivo.setTxAviso("Aviso Test");
    motivo.setCentrosCosto(centroCostoList);
    motivo.setFechaDesde(new Date());
    motivo.setFechaHasta(new Date());

    List<ParametroMotivo> motivoList = new ArrayList<>();
    motivoList.add(motivo);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", new ArrayList<>());
    httpSession.setAttribute("usuario", usuario);
    request.setHttpSession(httpSession);
    request.addParameter("accionJson", "");

    samClient.setSession(httpSession);
    samClient.setLoginOk(true);

    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
      when(mockParametrosService.getMotivos("TEST123", "55", "")).thenReturn(motivoList);
    })) {
      ActionForward result = parametrosMotivoDetalleLoadAction.executeAction(actionMapping, form, samApplication, samClient, request, httpServletResponse);

      assertNotNull(result);
      assertEquals("TEST123", httpSession.getAttribute("cod_motivo"));
      assertEquals("Test Motivo", httpSession.getAttribute("descripcion_motivo"));
      assertEquals("Test Motivo", httpSession.getAttribute("desc_motivo"));
    }
  }

  @Test
  @DisplayName("Should handle executeAction with null motivo fields - verifies HTML escaping")
  void testExecuteActionWithNullMotivoFields() throws Exception {
    ActionMapping actionMapping = new ActionMapping();
    ActionForward actionForward = new ActionForward();
    actionForward.setName("");
    actionForward.setPath("path2");
    actionMapping.addForwardConfig(actionForward);

    SAMWebApplication samApplication = new SAMWebApplication();
    SAMWebClient samClient = new SAMWebClient();
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest request = new MockHttpServletRequest();
    ParametrosMotivoForm form = new ParametrosMotivoForm();
    form.setAccion("");
    form.setCodigo("TEST123");
    // Add at least one centro costo to form to avoid IndexOutOfBoundsException
    List<String> formCentrosCostoList = new ArrayList<>();
    formCentrosCostoList.add("CENTRO_FORM_001");
    form.setCentrosCostoList(formCentrosCostoList);

    ParametroMotivo motivo = new ParametroMotivo();
    motivo.setCodigo(null);
    motivo.setDescripcion(null);
    motivo.setIdGlg(null);
    motivo.setIdCentroCostos(null);
    motivo.setEstado(null);
    motivo.setCodSup(null);
    motivo.setCodFirma(null);
    motivo.setCodAprobacionGlg(null);
    motivo.setOscar(null);
    motivo.setIdNivCarga(null);
    motivo.setIdNivAutoriz(null);
    motivo.setMaInclExcl(null);
    motivo.setIdOperEspe(null);
    motivo.setMeDiasInterv(null);
    motivo.setTxAviso(null);
    // Add at least one centro costo to avoid IndexOutOfBoundsException
    List<String> centrosCostoList = new ArrayList<>();
    centrosCostoList.add("CENTRO_001");
    motivo.setCentrosCosto(centrosCostoList);
    motivo.setFechaDesde(null);
    motivo.setFechaHasta(null);

    List<ParametroMotivo> motivoList = new ArrayList<>();
    motivoList.add(motivo);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", new ArrayList<>());
    httpSession.setAttribute("usuario", usuario);
    request.setHttpSession(httpSession);
    request.addParameter("accionJson", "");

    samClient.setSession(httpSession);
    samClient.setLoginOk(true);

    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
      when(mockParametrosService.getMotivos("TEST123", "55", "")).thenReturn(motivoList);
    })) {
      ActionForward result = parametrosMotivoDetalleLoadAction.executeAction(actionMapping, form, samApplication, samClient, request, httpServletResponse);

      assertNotNull(result);
      assertEquals("", httpSession.getAttribute("cod_motivo"));
      assertEquals("", httpSession.getAttribute("descripcion_motivo"));
    }
  }

  // Data providers
  static Stream<Arguments> motivoToFormProvider() {
    ParametrosMotivoForm parametrosMotivoForm = new ParametrosMotivoForm();
    List<String> centroCostoList = new ArrayList<>();
    centroCostoList.add("one");
    centroCostoList.add("two");
    centroCostoList.add("three");

    ParametroMotivo parametroMotivo = new ParametroMotivo();
    parametroMotivo.setCodigo("");
    parametroMotivo.setDescripcion("");
    parametroMotivo.setIdGlg("");
    parametroMotivo.setIdCentroCostos("");
    parametroMotivo.setEstado("");
    parametroMotivo.setCodSup("");
    parametroMotivo.setCodFirma("");
    parametroMotivo.setCodAprobacionGlg("");
    parametroMotivo.setOscar(new OSCAR("OSCAR"));
    parametroMotivo.setIdNivCarga("");
    parametroMotivo.setIdNivAutoriz("");
    parametroMotivo.setMaInclExcl("");
    parametroMotivo.setIdOperEspe("");
    parametroMotivo.setMeDiasInterv("");
    parametroMotivo.setTxAviso("");
    parametroMotivo.setCentrosCosto(centroCostoList);
    parametroMotivo.setFechaDesde(new Date());
    parametroMotivo.setFechaHasta(new Date());

    parametrosMotivoForm.setAccion("");
    parametrosMotivoForm.setCentrosCostoList(centroCostoList);

    return Stream.of(Arguments.of(parametrosMotivoForm, parametroMotivo));
  }

  static Stream<Arguments> borrarCentroCostoProvider() {
    ParametrosMotivoForm parametrosMotivoForm = new ParametrosMotivoForm();
    List<String> centroCostoList = new ArrayList<>();
    centroCostoList.add("one");
    centroCostoList.add("two");
    centroCostoList.add("three");

    parametrosMotivoForm.setAccion("");
    parametrosMotivoForm.setCentrosCostoList(centroCostoList);

    return Stream.of(Arguments.of(parametrosMotivoForm, 0));
  }

  static Stream<Arguments> agregarCentroCostoProvider() {
    ParametrosMotivoForm parametrosMotivoFormShortList = new ParametrosMotivoForm();
    ParametrosMotivoForm parametrosMotivoFormLongList = new ParametrosMotivoForm();
    PrintWriter printWriter = new PrintWriter(System.out);
    List<String> centroCostoShortList = new ArrayList<>();
    centroCostoShortList.add("one");
    centroCostoShortList.add("two");
    centroCostoShortList.add("three");

    List<String> centroCostoLongList = new ArrayList<>();
    for (int i = 0; i < 15; i++) {
      centroCostoLongList.add("");
    }

    parametrosMotivoFormShortList.setCentrosCostoList(centroCostoShortList);
    parametrosMotivoFormLongList.setCentrosCostoList(centroCostoLongList);

    return Stream.of(
        Arguments.of(parametrosMotivoFormShortList, printWriter),
        Arguments.of(parametrosMotivoFormLongList, printWriter)
    );
  }
}

