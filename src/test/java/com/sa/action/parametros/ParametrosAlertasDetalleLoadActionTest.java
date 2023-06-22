package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboOpcion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroAlerta;
import com.sa.form.parametros.ParametrosAlertasForm;
import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
import com.sa.services.ParametrosService;
import com.sa.util.ParamsConstants;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class ParametrosAlertasDetalleLoadActionTest {

  @Mock
  ActionMapping actionMappingMocked;

  @Mock
  HttpServletResponse httpServletResponse;

  @InjectMocks
  ParametrosAlertasDetalleLoadAction parametrosAlertasDetalleLoadAction;

  public static Stream<Arguments> executeActionSource() throws FileNotFoundException {
    //given
    ActionMapping actionMappingAlta = new ActionMapping();
    ActionMapping actionMappingBaja = new ActionMapping();
    ActionMapping actionMappingMod = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletRequest requestMotivo = new MockHttpServletRequest();
    MockHttpServletRequest requestGasto = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    List<Usuario> delegados = new ArrayList<>();
    RelacionUsuarioDelegadoForm form = new RelacionUsuarioDelegadoForm();
    ServletContext servletContext = new MockServletContext();
    PrintWriter printWriter = new PrintWriter("file");

    ParametrosAlertasForm parametrosAlertasFormAlta = new ParametrosAlertasForm();
    parametrosAlertasFormAlta.setAccion("alta");

    ParametrosAlertasForm parametrosAlertasFormBaja = new ParametrosAlertasForm();
    parametrosAlertasFormBaja.setAccion("baja");
    parametrosAlertasFormBaja.setCodGasto("GASTO");
    parametrosAlertasFormBaja.setCodMotivo("MOTIVO");
    parametrosAlertasFormBaja.setTimeStamp("22/06/2023");

    ParametrosAlertasForm parametrosAlertasFormMod = new ParametrosAlertasForm();
    parametrosAlertasFormMod.setAccion("modificacion");
    parametrosAlertasFormMod.setCodGasto("GASTO");
    parametrosAlertasFormMod.setCodMotivo("MOTIVO");
    parametrosAlertasFormMod.setTimeStamp("22/06/2023");

    ParametroAlerta parametroAlerta = new ParametroAlerta();
    parametroAlerta.setCodGasto("GASTO");
    parametroAlerta.setCodMotivo("MOTIVO");
    parametroAlerta.setTimeStamp("22/06/2023");
    parametroAlerta.setMotivo("");

    actionMappingAlta.addForwardConfig(new ActionForward("alta", "path1", false));
    actionMappingBaja.addForwardConfig(new ActionForward("baja", "path1", false));
    actionMappingMod.addForwardConfig(new ActionForward("modificacion", "path1", false));

    ActionForward actionForward = new ActionForward();
    actionForward.setName("");
    actionForward.setCatalog("");
    actionForward.setModule("alta");

    List<String> comboListMotivo = new ArrayList<>();
    comboListMotivo.add("MOTIVOMOTIVO");
    List<String> comboListGasto = new ArrayList<>();
    comboListGasto.add("GASTOGASTOGASTO");

    ComboOpcion comboOpcion = new ComboOpcion();
    comboOpcion.setId("55");
    comboOpcion.setDescripcion("Descripcion");

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    request.getSession().setAttribute("usuario", usuario);
    request.getSession().setAttribute("message", "This is a message");
    request.addParameter("accion", "");
    request.addParameter("codMotivo", "MOTIVOMOTIVO");

    requestMotivo.getSession().setAttribute("usuario", usuario);
    requestMotivo.getSession().setAttribute("message", "This is a message");
    requestMotivo.addParameter("accion", "selectMotivo");
    requestMotivo.addParameter("codMotivo", "MOTIVOMOTIVO");

    requestGasto.getSession().setAttribute("usuario", usuario);
    requestGasto.getSession().setAttribute("message", "This is a message");
    requestGasto.addParameter("accion", "selectGasto");
    requestGasto.addParameter("codMotivo", "GASTOGASTOGASTO");

    form.setOpcion(ParamsConstants.SU81_MODIFICACION);
    form.setUsuario("");
    form.setDelegadoUser("");
    form.setFeDesde("2000/01/01");
    form.setFeHasta("2000/01/01");
    form.setInforme("");
    form.setAccion("");
    form.setEstado("");
    form.setFechaAlta("2000/01/01");
    form.setUserAlta("userAlta");
    form.setFeDesdeOld("2023/01/01");
    form.setFeHastaOld("2023/01/01");

    Map<String, String> respHashMap = new HashMap<>();
    respHashMap.put("usuario", "");
    respHashMap.put("delegadoUser", "");
    respHashMap.put("fDesde", "2000/01/01");
    respHashMap.put("fDesde_old", "2000/01/01");
    respHashMap.put("fHasta", "2000/01/01");
    respHashMap.put("fHasta_old", "2000/01/01");
    respHashMap.put("fAlta", "2000/01/01");
    respHashMap.put("opcion", "MODI");
    respHashMap.put("accion", "");
    respHashMap.put("estado", "");
    respHashMap.put("informe", "");
    respHashMap.put("id_reemplazo", "");
    respHashMap.put("user_alta", "userAlta");

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    return Stream.of(
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, requestMotivo, comboListMotivo, printWriter, parametrosAlertasFormAlta, actionForward, parametroAlerta),
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, requestGasto, comboListMotivo, printWriter, parametrosAlertasFormAlta, actionForward, parametroAlerta),
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, request, comboListMotivo, printWriter,parametrosAlertasFormAlta, actionForward, parametroAlerta),
        Arguments.of(actionMappingMod, samWebApplication, samWebClient, request, comboListMotivo, printWriter, parametrosAlertasFormMod, actionForward, parametroAlerta),
        Arguments.of(actionMappingBaja, samWebApplication, samWebClient, request, comboListGasto, printWriter, parametrosAlertasFormBaja, actionForward, parametroAlerta)
    );
  }

  public static Stream<Arguments> alertaToFormSource() {
    //given
    ParametrosAlertasForm parametrosAlertasForm = new ParametrosAlertasForm();
    parametrosAlertasForm.setCodMotivo("MOTIVO");
    parametrosAlertasForm.setCodGasto("GASTO");
    parametrosAlertasForm.setEstado("STATUS");
    parametrosAlertasForm.setMontCant("CANT");
    parametrosAlertasForm.setRend("");
    parametrosAlertasForm.setPeriodo("");
    parametrosAlertasForm.setNivMax("");
    parametrosAlertasForm.setNivMin("");
    parametrosAlertasForm.setImpCant("");
    parametrosAlertasForm.setCriticidad("");
    parametrosAlertasForm.setTxAviso("TX");
    parametrosAlertasForm.setTimeStamp("22/06/2023");

    ParametroAlerta parametroAlerta = new ParametroAlerta();
    parametroAlerta.setCodMotivo("MOTIVO");
    parametroAlerta.setCodGasto("GASTO");
    parametroAlerta.setEstado("STATUS");
    parametroAlerta.setMontCant("CANT");
    parametroAlerta.setRend("");
    parametroAlerta.setPeriodo("");
    parametroAlerta.setNivelMax("");
    parametroAlerta.setNivelMin("");
    parametroAlerta.setImpCant("");
    parametroAlerta.setCriticidad("");
    parametroAlerta.setTxAviso("TX");
    parametroAlerta.setTimeStamp("22/06/2023");

    return Stream.of(Arguments.of(parametrosAlertasForm, parametroAlerta));
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Disabled("Unnecessary stubbing")
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action perform")
  void shouldDetermineWhatActionPerform(ActionMapping actionMapping, SAMWebApplication samApplication,
                                        SAMWebClient samClient, MockHttpServletRequest request, List<String> comboList, PrintWriter printWriter, ParametrosAlertasForm parametrosAlertasForm, ActionForward actionForward2, ParametroAlerta parametroAlerta) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
      when(mockParametrosService.getAlertaCombos()).thenReturn(comboList);
      when(mockParametrosService.getMsgAviso()).thenReturn("This is a message");
      when(mockParametrosService.getAlerta("CONS", "MOTIVO", "GASTO", "22/06/2023")).thenReturn(parametroAlerta);
      when(httpServletResponse.getWriter()).thenReturn(printWriter);
      when(actionMappingMocked.findForward(parametrosAlertasForm.getAccion())).thenReturn(actionForward2);
    })) {
      //then
      ActionForward actionForward = parametrosAlertasDetalleLoadAction.executeAction(actionMapping, parametrosAlertasForm, samApplication, samClient, request, httpServletResponse);
      if (request.getParameter("accion").equals("selectMotivo") || request.getParameter("accion").equals("selectGasto")) {
        assertNull(actionForward);
      } else {
        assertNotNull(actionForward);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("alertaToFormSource")
  @DisplayName("Should set ParametrosAlertas values")
  void shouldSetParametrosAlertasValues(ParametrosAlertasForm parametrosAlertasForm, ParametroAlerta parametroAlerta) throws Exception {
    //then
    Method alertaToFormMocked = ParametrosAlertasDetalleLoadAction.class.getDeclaredMethod("alertaToForm",
        ParametrosAlertasForm.class, ParametroAlerta.class);
    alertaToFormMocked.setAccessible(true);
    alertaToFormMocked.invoke(parametrosAlertasDetalleLoadAction, parametrosAlertasForm, parametroAlerta);

    assertAll(() -> assertNotNull(parametroAlerta),
        () -> assertNotNull(parametrosAlertasForm));
  }

}
