package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.OSCAR;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroExceptuado;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosExceptuadosForm;
import com.sa.form.parametros.ParametrosMotivoForm;
import com.sa.services.ParametrosService;
import org.apache.axis.utils.ByteArrayOutputStream;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ParametrosMotivoDetalleLoadActionTest {

  @Mock
  ActionMapping actionMappingMocked;

  @Mock
  HttpServletResponse httpServletResponse;

  @InjectMocks
  ParametrosMotivoDetalleLoadAction parametrosMotivoDetalleLoadAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMappingAlta = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletRequest requestBorrar = new MockHttpServletRequest();
    MockHttpServletRequest requestAgregar = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();
    ParametrosMotivoForm parametrosMotivoForm = new ParametrosMotivoForm();
    ParametrosMotivoForm parametrosMotivoFormAlta = new ParametrosMotivoForm();
    PrintWriter printWriter = new PrintWriter(new ByteArrayOutputStream());
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

    List<ParametroMotivo> parametroMotivoList = new ArrayList<>();
    parametroMotivoList.add(parametroMotivo);

    parametrosMotivoForm.setAccion("");
    parametrosMotivoForm.setCentrosCostoList(centroCostoList);
    parametrosMotivoFormAlta.setAccion("alta");
    parametrosMotivoFormAlta.setCentrosCostoList(centroCostoList);

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    httpSession.setAttribute("usuario", usuario);

    request.setHttpSession(httpSession);
    request.getSession().setAttribute("message", "This is a message");
    request.addParameter("accionJson", "");

    requestBorrar.setHttpSession(httpSession);
    requestBorrar.getSession().setAttribute("message", "This is a message");
    requestBorrar.addParameter("accionJson", "borrarCentroCosto");
    requestBorrar.addParameter("index", "0");

    requestAgregar.setHttpSession(httpSession);
    requestAgregar.getSession().setAttribute("message", "This is a message");
    requestAgregar.addParameter("accionJson", "agregarCentroCosto");
    requestAgregar.addParameter("index", "0");

    actionMappingAlta.addForwardConfig(new ActionForward("alta", "path1", false));
    actionMappingAlta.addForwardConfig(new ActionForward("", "path2", false));

    ActionForward actionForward = new ActionForward();
    actionForward.setName("");
    actionForward.setCatalog("");
    actionForward.setModule("alta");
    actionForward.setPath("");
    actionForward.setExtends("");

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    return Stream.of(
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, request, parametrosMotivoFormAlta, usuario, parametroMotivoList, actionForward, printWriter),
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, requestBorrar, parametrosMotivoFormAlta, usuario, parametroMotivoList, actionForward, printWriter),
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, requestAgregar, parametrosMotivoFormAlta, usuario, parametroMotivoList, actionForward, printWriter),
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, request, parametrosMotivoForm, usuario, parametroMotivoList, actionForward, printWriter)
                    );
  }

  public static Stream<Arguments> motivoToForm() {
    //given
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

    return Stream.of(
        Arguments.of(parametrosMotivoForm, parametroMotivo)
                    );
  }

  public static Stream<Arguments> borrarCentroCostoSource() {
    //given
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

    int index = 0;

    return Stream.of(
        Arguments.of(parametrosMotivoForm, index)
                    );
  }

  public static Stream<Arguments> agregarCentroCosto() {
    //given
    ParametrosMotivoForm parametrosMotivoFormShortList = new ParametrosMotivoForm();
    ParametrosMotivoForm parametrosMotivoFormLongList = new ParametrosMotivoForm();
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));
    List<String> centroCostoShortList = new ArrayList<>();
    centroCostoShortList.add("one");
    centroCostoShortList.add("two");
    centroCostoShortList.add("three");

    List<String> centroCostoLongList = new ArrayList<>();
    do {
      centroCostoLongList.add("");
    } while (centroCostoLongList.size() < 15);

    parametrosMotivoFormShortList.setCentrosCostoList(centroCostoShortList);
    parametrosMotivoFormLongList.setCentrosCostoList(centroCostoLongList);

    return Stream.of(
        Arguments.of(parametrosMotivoFormShortList, printWriter),
        Arguments.of(parametrosMotivoFormLongList, printWriter)
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
                                        ParametrosMotivoForm parametrosMotivoForm, Usuario usuario, List<ParametroMotivo> parametroMotivoList,
                                        ActionForward actionForward, PrintWriter printWriter) throws Exception {
    //given
    Method motivoToFormMocked = ParametrosMotivoDetalleLoadAction.class.getDeclaredMethod("motivoToForm", ParametrosMotivoForm.class, ParametroMotivo.class);
    motivoToFormMocked.setAccessible(true);
    motivoToFormMocked.invoke(parametrosMotivoDetalleLoadAction, parametrosMotivoForm, parametroMotivoList.get(0));
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
      when(mockParametrosService.getMotivos(parametrosMotivoForm.getCodigo(), usuario.getIdUser())).thenReturn(parametroMotivoList);
      when(actionMappingMocked.findForward("")).thenReturn(actionForward);
      when(httpServletResponse.getWriter()).thenReturn(printWriter);
    })) {
      //then
      ActionForward actionForwardToAssert = parametrosMotivoDetalleLoadAction.executeAction(actionMapping, parametrosMotivoForm, samApplication, samClient,
          request, httpServletResponse);
      if (request.getParameter("accionJson").equals("borrarCentroCosto") || request.getParameter("accionJson").equals("agregarCentroCosto")) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("motivoToForm")
  @DisplayName("Should save Motivo in Form")
  void shouldSaveMotivoInForm(ParametrosMotivoForm parametrosMotivoForm, ParametroMotivo parametroMotivo) throws Exception {
    //then
    Method motivoToFormMocked = ParametrosMotivoDetalleLoadAction.class.getDeclaredMethod("motivoToForm", ParametrosMotivoForm.class, ParametroMotivo.class);
    motivoToFormMocked.setAccessible(true);
    motivoToFormMocked.invoke(parametrosMotivoDetalleLoadAction, parametrosMotivoForm, parametroMotivo);
    assertAll(() -> assertNotNull(parametrosMotivoForm),
        () -> assertNotNull(parametroMotivo));
  }

  @ParameterizedTest
  @MethodSource("borrarCentroCostoSource")
  @DisplayName("Should remove CentroCosto")
  void shouldRemoveCentroCosto(ParametrosMotivoForm parametrosMotivoForm, int index) throws Exception {
    //then
    Method borrarCentroCostoMocked = ParametrosMotivoDetalleLoadAction.class.getDeclaredMethod("borrarCentroCosto", ParametrosMotivoForm.class, int.class);
    borrarCentroCostoMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) borrarCentroCostoMocked.invoke(parametrosMotivoDetalleLoadAction, parametrosMotivoForm, index);
    assertAll(() -> assertNotNull(parametrosMotivoForm),
        () -> assertNull(actionForwardToAssert));
  }


  @ParameterizedTest
  @MethodSource("agregarCentroCosto")
  @DisplayName("Should add CentroCosto to Form")
  void shouldAddCentroCostoToForm(ParametrosMotivoForm parametrosMotivoForm, PrintWriter printWriter) throws Exception {
    //when
    when(httpServletResponse.getWriter()).thenReturn(printWriter);
    //then
    Method agregarCentroCostoMocked = ParametrosMotivoDetalleLoadAction.class.getDeclaredMethod("agregarCentroCosto", ParametrosMotivoForm.class, HttpServletResponse.class);
    agregarCentroCostoMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) agregarCentroCostoMocked.invoke(parametrosMotivoDetalleLoadAction, parametrosMotivoForm, httpServletResponse);
    assertAll(() -> assertNull(actionForwardToAssert),
        () -> assertNotNull(printWriter),
        () -> assertNotNull(parametrosMotivoForm));
  }
}
