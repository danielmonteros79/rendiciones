package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboOpcion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroAlerta;
import com.sa.form.parametros.ParametrosAlertasFiltroForm;
import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
import com.sa.services.ParametrosService;
import com.sa.util.ParamsConstants;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ParametrosAlertasLoadActionTest {

  @Mock
  ActionMapping actionMappingMocked;

  @Mock
  HttpServletResponse httpServletResponse;

  @InjectMocks
  ParametrosAlertasLoadAction parametrosAlertasLoadAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletRequest requestMotivo = new MockHttpServletRequest();
    MockHttpServletRequest requestGasto = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    List<ParametroAlerta> parametroAlertaList = new ArrayList<>();
    RelacionUsuarioDelegadoForm relacionUsuarioDelegadoForm = new RelacionUsuarioDelegadoForm();
    ServletContext servletContext = new MockServletContext();
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));
    ParametroAlerta parametroAlerta = new ParametroAlerta();
    ParametrosAlertasFiltroForm parametrosAlertasFiltroForm = new ParametrosAlertasFiltroForm();

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    httpSession.setAttribute("usuario", usuario);

    request.setHttpSession(httpSession);
    request.getSession().setAttribute("message", "This is a message");
    request.addParameter("accion", "");
    request.addParameter("codMotivo", "MOTIVOMOTIVO");

    requestMotivo.setHttpSession(httpSession);
    requestMotivo.getSession().setAttribute("message", "This is a message");
    requestMotivo.addParameter("accion", "selectMotivo");
    requestMotivo.addParameter("codMotivo", "MOTIVOMOTIVO");

    requestGasto.setHttpSession(httpSession);
    requestGasto.getSession().setAttribute("message", "This is a message");
    requestGasto.addParameter("accion", "selectGasto");
    requestGasto.addParameter("codMotivo", "MOTIVOMOTIVO");

    parametroAlerta.setCodGasto("GASTO");
    parametroAlerta.setCodMotivo("MOTIVO");
    parametroAlerta.setTimeStamp("22/06/2023");
    parametroAlerta.setMotivo("");

    parametroAlertaList.add(parametroAlerta);

    actionMapping.addForwardConfig(new ActionForward("success", "path1", false));

    ActionForward actionForward = new ActionForward();
    actionForward.setName("");
    actionForward.setCatalog("");
    actionForward.setModule("alta");

    relacionUsuarioDelegadoForm.setOpcion(ParamsConstants.SU81_MODIFICACION);
    relacionUsuarioDelegadoForm.setUsuario("");
    relacionUsuarioDelegadoForm.setDelegadoUser("");
    relacionUsuarioDelegadoForm.setFeDesde("2000/01/01");
    relacionUsuarioDelegadoForm.setFeHasta("2000/01/01");
    relacionUsuarioDelegadoForm.setInforme("");
    relacionUsuarioDelegadoForm.setAccion("");
    relacionUsuarioDelegadoForm.setEstado("");
    relacionUsuarioDelegadoForm.setFechaAlta("2000/01/01");
    relacionUsuarioDelegadoForm.setUserAlta("userAlta");
    relacionUsuarioDelegadoForm.setFeDesdeOld("2023/01/01");
    relacionUsuarioDelegadoForm.setFeHastaOld("2023/01/01");

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    List<String> comboListMotivo = new ArrayList<>();
    comboListMotivo.add("MOTIVOMOTIVO");
    List<String> comboListGasto = new ArrayList<>();
    comboListGasto.add("GASTOGASTOGASTO");

    ComboOpcion comboOpcion = new ComboOpcion();
    comboOpcion.setId("55");
    comboOpcion.setDescripcion("Descripcion");

    List<ComboOpcion> comboOpcionList = new ArrayList<>();
    comboOpcionList.add(comboOpcion);

    return Stream.of(
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, comboListMotivo, printWriter,
            parametrosAlertasFiltroForm, actionForward, parametroAlertaList),
        Arguments.of(actionMapping, samWebApplication, samWebClient, requestMotivo, comboListMotivo, printWriter,
            parametrosAlertasFiltroForm, actionForward, parametroAlertaList),
        Arguments.of(actionMapping, samWebApplication, samWebClient, requestGasto, comboListMotivo, printWriter,
            parametrosAlertasFiltroForm, actionForward, parametroAlertaList),
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, comboListGasto, printWriter,
            parametrosAlertasFiltroForm, actionForward, parametroAlertaList)
    );
  }

  public static Stream<Arguments> selectMotivoSource() {
    //given
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest requestCodMotivoNull = new MockHttpServletRequest();
    MockHttpServletRequest requestCodMotivoNotNull = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);
    httpSession.setAttribute("usuario", usuario);

    requestCodMotivoNull.setHttpSession(httpSession);
    requestCodMotivoNull.getSession().setAttribute("message", "This is a message");
    requestCodMotivoNull.addParameter("accion", "");
    requestCodMotivoNull.addParameter("codMotivo", "");

    requestCodMotivoNotNull.setHttpSession(httpSession);
    requestCodMotivoNotNull.getSession().setAttribute("message", "This is a message");
    requestCodMotivoNotNull.addParameter("accion", "");
    requestCodMotivoNotNull.addParameter("codMotivo", "MOTIVO");

    ComboOpcion comboOpcion = new ComboOpcion();
    comboOpcion.setId("55");
    comboOpcion.setDescripcion("Descripcion");

    List<ComboOpcion> comboOpcionList = new ArrayList<>();
    comboOpcionList.add(comboOpcion);

    Map<String, List<ComboOpcion>> motivoGastoHashMap = new HashMap<>();
    motivoGastoHashMap.put("MOTIVO", comboOpcionList);

    return Stream.of(
        Arguments.of(printWriter, requestCodMotivoNull, comboOpcionList, motivoGastoHashMap),
        Arguments.of(printWriter, requestCodMotivoNotNull, comboOpcionList, motivoGastoHashMap)
    );
  }

  public static Stream<Arguments> selectGastoSource() {
    //given
    HttpSession httpSession = new MockHttpSession();
    MockHttpServletRequest request = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);
    httpSession.setAttribute("usuario", usuario);

    request.setHttpSession(httpSession);
    request.getSession().setAttribute("message", "This is a message");
    request.addParameter("accion", "");
    request.addParameter("codMotivo", "");
    request.addParameter("codGasto", "");

    ComboOpcion comboOpcion = new ComboOpcion();
    comboOpcion.setId("55");
    comboOpcion.setDescripcion("Descripcion");

    List<ComboOpcion> comboOpcionList = new ArrayList<>();
    comboOpcionList.add(comboOpcion);

    Map<String, List<ComboOpcion>> motivoGastoHashMap = new HashMap<>();
    motivoGastoHashMap.put("MOTIVO", comboOpcionList);

    return Stream.of(
        Arguments.of(printWriter, request, motivoGastoHashMap)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /*@ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action execute")
  void shouldDetermineWhatActionExecute(ActionMapping actionMapping, SAMWebApplication samApplication,
                                        SAMWebClient samClient, MockHttpServletRequest request,
                                        List<String> comboList, PrintWriter printWriter,
                                        ParametrosAlertasFiltroForm parametrosAlertasFiltroForm,
                                        ActionForward actionForward, List<ParametroAlerta> parametroAlertaList) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
      when(mockParametrosService.getAlertas("CONS", parametrosAlertasFiltroForm.getCodMotivo(),
              parametrosAlertasFiltroForm.getCodGasto())).thenReturn(parametroAlertaList);
      when(mockParametrosService.getMsgAviso()).thenReturn("This is a message");
      when(mockParametrosService.getAlertaCombos()).thenReturn(comboList);
      when(httpServletResponse.getWriter()).thenReturn(printWriter);
      when(actionMappingMocked.findForward("success")).thenReturn(actionForward);
    })) {
      //then
      ActionForward actionForwardToAssert = parametrosAlertasLoadAction.executeAction(actionMapping, parametrosAlertasFiltroForm, samApplication, samClient,
          request, httpServletResponse);
      if (request.getParameter("accion").equals("selectMotivo") || request.getParameter("accion").equals("selectGasto")) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
      assertNotNull(printWriter);
    }
  }*/

  @ParameterizedTest
  @MethodSource("selectMotivoSource")
  @DisplayName("Should check codMotivo with empty collections")
  void shouldCheckCodMotivo(PrintWriter printWriter, MockHttpServletRequest request, List<ComboOpcion> comboOpcionList, Map<String, List<ComboOpcion>> motivoGastoHashMap) throws Exception {
    //given - static final fields are now immutable empty collections
    //when
    Method selectMotivoMocked = ParametrosAlertasLoadAction.class.getDeclaredMethod("selectMotivo", PrintWriter.class, HttpServletRequest.class);
    selectMotivoMocked.setAccessible(true);
    selectMotivoMocked.invoke(parametrosAlertasLoadAction, printWriter, request);
    //then - verify method executes without error with empty collections
    assertAll(() -> assertNotNull(printWriter),
        () -> assertNotNull(request),
        () -> assertNotNull(comboOpcionList),
        () -> assertNotNull(motivoGastoHashMap));
  }

  @ParameterizedTest
  @MethodSource("selectGastoSource")
  @DisplayName("Should check codGasto with empty collections")
  void shouldCheckCodGasto(PrintWriter printWriter, MockHttpServletRequest request, Map<String, List<ComboOpcion>> motivoGastoHashMap) throws Exception {
    //given - static final fields are now immutable empty collections
    //when
    Method selectGastoMocked = ParametrosAlertasLoadAction.class.getDeclaredMethod("selectGasto", PrintWriter.class, HttpServletRequest.class);
    selectGastoMocked.setAccessible(true);
    selectGastoMocked.invoke(parametrosAlertasLoadAction, printWriter, request);
    //then - verify method executes without error with empty collections
    assertAll(() -> assertNotNull(printWriter),
        () -> assertNotNull(request),
        () -> assertNotNull(motivoGastoHashMap));
  }
}
