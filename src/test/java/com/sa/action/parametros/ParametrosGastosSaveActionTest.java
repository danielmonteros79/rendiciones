package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.OSCAR;
import com.sa.entities.Usuario;
import com.sa.form.parametros.ParametrosGastosForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.ParametrosService;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ParametrosGastosSaveActionTest {

  @Mock
  ActionMapping actionMappingMocked;

  @Mock
  HttpServletResponse httpServletResponse;

  @Mock
  ParametrosService parametrosServiceMocked;

  @InjectMocks
  ParametrosGastosSaveAction parametrosGastosSaveAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();

    StringBuilder ristra= new StringBuilder();
    for (int i = 0; i < 18; i++) {
      ristra.append("iiii");
    }

    ParametrosGastosForm parametrosGastosFormAlta = new ParametrosGastosForm();
    parametrosGastosFormAlta.setAccion("alta");
    parametrosGastosFormAlta.setCodigo("445");
    parametrosGastosFormAlta.setDescripcionGasto("");
    parametrosGastosFormAlta.setMotivo("");
    parametrosGastosFormAlta.setBimon("");
    parametrosGastosFormAlta.setCentrosCosto("445");
    parametrosGastosFormAlta.setEstado("");
    parametrosGastosFormAlta.setRistra(ristra.toString());
    parametrosGastosFormAlta.setOscar(new OSCAR("OSCAR"));
    parametrosGastosFormAlta.setMaInclExcl("");
    parametrosGastosFormAlta.setComprob("");
    parametrosGastosFormAlta.setAntiguedad("");
    parametrosGastosFormAlta.setObserv("");
    parametrosGastosFormAlta.setAutoriz("");
    parametrosGastosFormAlta.setPlazoAprob("");

    ParametrosGastosForm parametrosGastosFormBaja = new ParametrosGastosForm();
    parametrosGastosFormBaja.setAccion("baja");
    parametrosGastosFormBaja.setCodigo("445");
    parametrosGastosFormBaja.setDescripcionGasto("");
    parametrosGastosFormBaja.setMotivo("");
    parametrosGastosFormBaja.setBimon("");
    parametrosGastosFormBaja.setCentrosCosto("445");
    parametrosGastosFormBaja.setEstado("");
    parametrosGastosFormBaja.setRistra(ristra.toString());
    parametrosGastosFormBaja.setOscar(new OSCAR("OSCAR"));
    parametrosGastosFormBaja.setMaInclExcl("");
    parametrosGastosFormBaja.setComprob("");
    parametrosGastosFormBaja.setAntiguedad("");
    parametrosGastosFormBaja.setObserv("");
    parametrosGastosFormBaja.setAutoriz("");
    parametrosGastosFormBaja.setPlazoAprob("");

    ParametrosGastosForm parametrosGastosFormMod = new ParametrosGastosForm();
    parametrosGastosFormMod.setAccion("modificacion");
    parametrosGastosFormMod.setCodigo("445");
    parametrosGastosFormMod.setDescripcionGasto("");
    parametrosGastosFormMod.setMotivo("");
    parametrosGastosFormMod.setBimon("");
    parametrosGastosFormMod.setCentrosCosto("445");
    parametrosGastosFormMod.setEstado("");
    parametrosGastosFormMod.setRistra(ristra.toString());
    parametrosGastosFormMod.setOscar(new OSCAR("OSCAR"));
    parametrosGastosFormMod.setMaInclExcl("");
    parametrosGastosFormMod.setComprob("");
    parametrosGastosFormMod.setAntiguedad("");
    parametrosGastosFormMod.setObserv("");
    parametrosGastosFormMod.setAutoriz("");
    parametrosGastosFormMod.setPlazoAprob("");

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    httpSession.setAttribute("usuario", usuario);

    request.setHttpSession(httpSession);
    request.addParameter("accion", "");
    request.addParameter("codMotivo", "MOTIVOMOTIVO");

    actionMapping.addForwardConfig(new ActionForward("success", "path1", false));

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    ActionForward actionForward = new ActionForward();
    actionForward.setName("");
    actionForward.setCatalog("");
    actionForward.setModule("alta");

    ParametrosService parametrosService = new ParametrosService(samWebClient);

    return Stream.of(
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosGastosFormAlta, actionForward, parametrosService),
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosGastosFormBaja, actionForward, parametrosService),
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosGastosFormMod, actionForward, parametrosService)
                    );
  }

  public static Stream<Arguments> altaSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();

    StringBuilder ristra= new StringBuilder();
    for (int i = 0; i < 18; i++) {
      ristra.append("iiii");
    }

    List<String> centroCostoList = new ArrayList<>();
    centroCostoList.add("1");
    centroCostoList.add("2");

    ParametrosGastosForm parametrosGastosForm = new ParametrosGastosForm();
    parametrosGastosForm.setAccion("alta");
    parametrosGastosForm.setCodigo("445");
    parametrosGastosForm.setDescripcionGasto("");
    parametrosGastosForm.setMotivo("");
    parametrosGastosForm.setBimon("");
    parametrosGastosForm.setCentrosCosto("445");
    parametrosGastosForm.setEstado("");
    parametrosGastosForm.setRistra(ristra.toString());
    parametrosGastosForm.setOscar(new OSCAR("OSCAR"));
    parametrosGastosForm.setMaInclExcl("");
    parametrosGastosForm.setComprob("");
    parametrosGastosForm.setAntiguedad("");
    parametrosGastosForm.setObserv("");
    parametrosGastosForm.setAutoriz("");
    parametrosGastosForm.setPlazoAprob("");
    parametrosGastosForm.setCentrosCostoList(centroCostoList);
    parametrosGastosForm.setIdCentroCostos("554");

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    httpSession.setAttribute("usuario", usuario);

    request.setHttpSession(httpSession);
    request.addParameter("accion", "");
    request.addParameter("codMotivo", "MOTIVOMOTIVO");

    actionMapping.addForwardConfig(new ActionForward("success", "path1", false));

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    ParametrosService parametrosService = new ParametrosService(samWebClient);

    String message = "fail";

    return Stream.of(
        Arguments.of(request, parametrosGastosForm, parametrosService, null),
        Arguments.of(request, parametrosGastosForm, parametrosService, message)
                    );
  }

  public static Stream<Arguments> bajaSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();

    StringBuilder ristra= new StringBuilder();
    for (int i = 0; i < 18; i++) {
      ristra.append("iiii");
    }

    List<String> centroCostoList = new ArrayList<>();
    centroCostoList.add("1");
    centroCostoList.add("2");

    ParametrosGastosForm parametrosGastosForm = new ParametrosGastosForm();
    parametrosGastosForm.setAccion("alta");
    parametrosGastosForm.setCodigo("445");
    parametrosGastosForm.setDescripcionGasto("");
    parametrosGastosForm.setMotivo("");
    parametrosGastosForm.setBimon("");
    parametrosGastosForm.setCentrosCosto("445");
    parametrosGastosForm.setEstado("");
    parametrosGastosForm.setRistra(ristra.toString());
    parametrosGastosForm.setOscar(new OSCAR("OSCAR"));
    parametrosGastosForm.setMaInclExcl("");
    parametrosGastosForm.setComprob("");
    parametrosGastosForm.setAntiguedad("");
    parametrosGastosForm.setObserv("");
    parametrosGastosForm.setAutoriz("");
    parametrosGastosForm.setPlazoAprob("");
    parametrosGastosForm.setCentrosCostoList(centroCostoList);
    parametrosGastosForm.setIdCentroCostos("554");

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    httpSession.setAttribute("usuario", usuario);

    request.setHttpSession(httpSession);
    request.addParameter("accion", "");
    request.addParameter("codMotivo", "MOTIVOMOTIVO");

    actionMapping.addForwardConfig(new ActionForward("success", "path1", false));

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    ParametrosService parametrosService = new ParametrosService(samWebClient);

    String message = "fail";

    return Stream.of(
        Arguments.of(request, parametrosGastosForm, parametrosService, null)
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
                                        ParametrosGastosForm parametrosGastosForm, ActionForward actionForward, ParametrosService parametrosService) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(mockParametrosService.getMsgAviso()).thenReturn("");
          when(actionMappingMocked.findForward(parametrosGastosForm.getAccion())).thenReturn(actionForward);
          doNothing().when(mockParametrosService).altaGasto(parametrosGastosForm);
          doNothing().when(mockParametrosService).bajaGasto(parametrosGastosForm.getCodigo(), parametrosGastosForm.getMotivo());
        })) {
      //then
      Method altaMocked = ParametrosGastosSaveAction.class.getDeclaredMethod("alta", HttpServletRequest.class, ParametrosGastosForm.class,
          ParametrosService.class);
      altaMocked.setAccessible(true);
      String statusAlta = (String) altaMocked.invoke(parametrosGastosSaveAction, request, parametrosGastosForm, parametrosServiceMocked);

      Method bajaMocked = ParametrosGastosSaveAction.class.getDeclaredMethod("baja", HttpServletRequest.class, ParametrosGastosForm.class, ParametrosService.class);
      bajaMocked.setAccessible(true);
      String statusBaja = (String) bajaMocked.invoke(parametrosGastosSaveAction, request, parametrosGastosForm, parametrosServiceMocked);

      Method modificacionMocked = ParametrosGastosSaveAction.class.getDeclaredMethod("modificacion", HttpServletRequest.class, ParametrosGastosForm.class, ParametrosService.class);
      modificacionMocked.setAccessible(true);
      String statusModificacion = (String) modificacionMocked.invoke(parametrosGastosSaveAction, request, parametrosGastosForm, parametrosServiceMocked);

      ActionForward actionForwardToAssert = parametrosGastosSaveAction.executeAction(actionMapping, parametrosGastosForm, samApplication, samClient, request,
          httpServletResponse);
      assertAll(() -> assertNotNull(actionForwardToAssert),
          () -> assertEquals("success", statusAlta),
          () -> assertEquals("success", statusBaja),
          () -> assertEquals("success", statusModificacion));
    }
  }

  @ParameterizedTest
  @MethodSource("altaSource")
  @DisplayName("Should save Gastos")
  void shouldSaveGastos(HttpServletRequest request, ParametrosGastosForm parametrosGastosForm, ParametrosService parametrosService,
                                        String message) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          doNothing().when(mockParametrosService).altaGasto(parametrosGastosForm);
          doNothing().when(mockParametrosService).getMsgAviso();
        })) {
      try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
          (mockManagerTransaction, context) -> {
            doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
            when(mockManagerTransaction.getMensajeAviso()).thenReturn(message);
          })) {
        //then
        Method altaMocked = ParametrosGastosSaveAction.class.getDeclaredMethod("alta", HttpServletRequest.class, ParametrosGastosForm.class,
            ParametrosService.class);
        altaMocked.setAccessible(true);
        String statusAlta = (String) altaMocked.invoke(parametrosGastosSaveAction, request, parametrosGastosForm, parametrosService);
        if (message == null) {
          assertEquals("success", statusAlta);
        } else {
          assertEquals("fail", message);
        }
      }
    }
  }

  @ParameterizedTest
  @MethodSource("altaSource")
  @DisplayName("Should save Modificacion")
  void shouldSaveModificacion(HttpServletRequest request, ParametrosGastosForm parametrosGastosForm, ParametrosService parametrosService, String message) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          doNothing().when(mockParametrosService).modificacionGasto(parametrosGastosForm);
          doNothing().when(mockParametrosService).getMsgAviso();
        })) {
      try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
          (mockManagerTransaction, context) -> {
            doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
            when(mockManagerTransaction.getMensajeAviso()).thenReturn(message);
          })) {
        //then
        Method modificacionMocked = ParametrosGastosSaveAction.class.getDeclaredMethod("modificacion", HttpServletRequest.class, ParametrosGastosForm.class, ParametrosService.class);
        modificacionMocked.setAccessible(true);
        String statusAlta = (String) modificacionMocked.invoke(parametrosGastosSaveAction, request, parametrosGastosForm, parametrosService);
        if (message == null) {
          assertEquals("success", statusAlta);
        } else {
          assertEquals("fail", message);
        }
      }
    }
  }

  @ParameterizedTest
  @MethodSource("altaSource")
  @DisplayName("Should save Baja")
  void shouldSaveBaja(HttpServletRequest request, ParametrosGastosForm parametrosGastosForm, ParametrosService parametrosService, String message) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          doNothing().when(mockParametrosService).bajaGasto(parametrosGastosForm.getCodigo(), parametrosGastosForm.getMotivo());
          doNothing().when(mockParametrosService).getMsgAviso();
        })) {
      try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
          (mockManagerTransaction, context) -> {
            doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
            when(mockManagerTransaction.getMensajeAviso()).thenReturn(message);
          })) {
        //then
        Method bajaMocked = ParametrosGastosSaveAction.class.getDeclaredMethod("baja", HttpServletRequest.class, ParametrosGastosForm.class, ParametrosService.class);
        bajaMocked.setAccessible(true);
        String statusAlta = (String) bajaMocked.invoke(parametrosGastosSaveAction, request, parametrosGastosForm, parametrosService);
        if (message == null) {
          assertEquals("success", statusAlta);
        } else {
          assertEquals("fail", message);
        }
      }
    }
  }
}
