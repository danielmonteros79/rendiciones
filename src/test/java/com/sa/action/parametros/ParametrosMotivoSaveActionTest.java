package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.OSCAR;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosGastosForm;
import com.sa.form.parametros.ParametrosMotivoFiltroForm;
import com.sa.form.parametros.ParametrosMotivoForm;
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

class ParametrosMotivoSaveActionTest {

  @Mock
  ActionMapping actionMappingMocked;

  @Mock
  HttpServletResponse httpServletResponse;

  @InjectMocks
  ParametrosMotivoSaveAction parametrosMotivoSaveAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();

    ParametroMotivo parametroMotivo = new ParametroMotivo();

    ParametrosMotivoForm parametrosMotivoFormAlta = new ParametrosMotivoForm();
    parametrosMotivoFormAlta.setAccion("alta");
    parametrosMotivoFormAlta.setCodigo("445");
    parametrosMotivoFormAlta.setDescripcion("");
    parametrosMotivoFormAlta.setEstado("");
    parametrosMotivoFormAlta.setIdGlg("22");
    parametrosMotivoFormAlta.setCodAprobacionGlg("");
    parametrosMotivoFormAlta.setIdCentroCostos("22");
    parametrosMotivoFormAlta.setMaInclExcl("");
    parametrosMotivoFormAlta.setCodSup("");
    parametrosMotivoFormAlta.setCodFirma("");
    parametrosMotivoFormAlta.setDescOscar("OSCAR");
    parametrosMotivoFormAlta.setOscar(new OSCAR("OSCAR"));
    parametrosMotivoFormAlta.setIdNivCarga("");
    parametrosMotivoFormAlta.setTxAviso("");
    parametrosMotivoFormAlta.setIdOperEspe("");
    parametrosMotivoFormAlta.setMeDiasInterv("");
    parametrosMotivoFormAlta.setCentrosCosto("445");
    parametrosMotivoFormAlta.setFechaDesde("29/06/2023");
    parametrosMotivoFormAlta.setFechaHasta("29/06/2023");

    ParametrosMotivoForm parametrosMotivoFormBaja = new ParametrosMotivoForm();
    parametrosMotivoFormBaja.setAccion("baja");
    parametrosMotivoFormBaja.setCodigo("445");
    parametrosMotivoFormBaja.setDescripcion("");
    parametrosMotivoFormBaja.setEstado("");
    parametrosMotivoFormBaja.setIdGlg("33");
    parametrosMotivoFormBaja.setCodAprobacionGlg("");
    parametrosMotivoFormBaja.setIdCentroCostos("33");
    parametrosMotivoFormBaja.setMaInclExcl("");
    parametrosMotivoFormBaja.setCodSup("");
    parametrosMotivoFormBaja.setCodFirma("");
    parametrosMotivoFormBaja.setDescOscar("OSCAR");
    parametrosMotivoFormBaja.setOscar(new OSCAR("OSCAR"));
    parametrosMotivoFormBaja.setIdNivCarga("");
    parametrosMotivoFormBaja.setTxAviso("");
    parametrosMotivoFormBaja.setIdOperEspe("");
    parametrosMotivoFormBaja.setMeDiasInterv("");
    parametrosMotivoFormBaja.setCentrosCosto("445");
    parametrosMotivoFormBaja.setFechaDesde("29/06/2023");
    parametrosMotivoFormBaja.setFechaHasta("29/06/2023");

    ParametrosMotivoForm parametrosMotivoFormMod = new ParametrosMotivoForm();
    parametrosMotivoFormMod.setAccion("modificacion");
    parametrosMotivoFormMod.setCodigo("445");
    parametrosMotivoFormMod.setDescripcion("");
    parametrosMotivoFormMod.setEstado("");
    parametrosMotivoFormMod.setIdGlg("33");
    parametrosMotivoFormMod.setCodAprobacionGlg("");
    parametrosMotivoFormMod.setIdCentroCostos("333");
    parametrosMotivoFormMod.setMaInclExcl("");
    parametrosMotivoFormMod.setCodSup("");
    parametrosMotivoFormMod.setCodFirma("");
    parametrosMotivoFormMod.setDescOscar("OSCAR");
    parametrosMotivoFormMod.setOscar(new OSCAR("OSCAR"));
    parametrosMotivoFormMod.setIdNivCarga("");
    parametrosMotivoFormMod.setTxAviso("");
    parametrosMotivoFormMod.setIdOperEspe("");
    parametrosMotivoFormMod.setMeDiasInterv("");
    parametrosMotivoFormMod.setCentrosCosto("445");
    parametrosMotivoFormMod.setFechaDesde("29/06/2023");
    parametrosMotivoFormMod.setFechaHasta("29/06/2023");

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
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosMotivoFormAlta, actionForward, parametrosService, parametroMotivo),
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosMotivoFormBaja, actionForward, parametrosService, parametroMotivo),
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosMotivoFormMod, actionForward, parametrosService, parametroMotivo)
                    );
  }

  public static Stream<Arguments> altaSource() {
    //given
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();
    List<String> centroCostoList = new ArrayList<>();
    centroCostoList.add("21");
    centroCostoList.add("11");
    centroCostoList.add("78");

    ParametroMotivo parametroMotivo = new ParametroMotivo();
    parametroMotivo.setCentrosCosto(centroCostoList);

    ParametrosMotivoForm parametrosMotivoForm = new ParametrosMotivoForm();
    parametrosMotivoForm.setAccion("alta");
    parametrosMotivoForm.setCodigo("445");
    parametrosMotivoForm.setDescripcion("");
    parametrosMotivoForm.setEstado("");
    parametrosMotivoForm.setIdGlg("3");
    parametrosMotivoForm.setCodAprobacionGlg("");
    parametrosMotivoForm.setIdCentroCostos("55");
    parametrosMotivoForm.setMaInclExcl("");
    parametrosMotivoForm.setCodSup("");
    parametrosMotivoForm.setCodFirma("");
    parametrosMotivoForm.setDescOscar("OSCAR");
    parametrosMotivoForm.setOscar(new OSCAR("OSCAR"));
    parametrosMotivoForm.setIdNivCarga("");
    parametrosMotivoForm.setTxAviso("");
    parametrosMotivoForm.setIdOperEspe("");
    parametrosMotivoForm.setMeDiasInterv("");
    parametrosMotivoForm.setCentrosCosto("445");
    parametrosMotivoForm.setFechaDesde("29/06/2023");
    parametrosMotivoForm.setFechaHasta("29/06/2023");
    parametrosMotivoForm.setCentrosCostoList(centroCostoList);

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    httpSession.setAttribute("usuario", usuario);

    request.setHttpSession(httpSession);
    request.addParameter("accion", "");
    request.addParameter("codMotivo", "MOTIVOMOTIVO");

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    ParametrosService parametrosService = new ParametrosService(samWebClient);

    return Stream.of(
        Arguments.of(request, parametrosMotivoForm, parametrosService, parametroMotivo)
                    );
  }

  public static Stream<Arguments> formToMotivoSource() {
    //given
    List<String> centroCostoList = new ArrayList<>();
    centroCostoList.add("21");
    centroCostoList.add("11");
    centroCostoList.add("78");

    ParametroMotivo parametroMotivo = new ParametroMotivo();
    parametroMotivo.setCentrosCosto(centroCostoList);

    ParametrosMotivoForm parametrosMotivoForm = new ParametrosMotivoForm();
    parametrosMotivoForm.setAccion("alta");
    parametrosMotivoForm.setCodigo("445");
    parametrosMotivoForm.setDescripcion("");
    parametrosMotivoForm.setEstado("");
    parametrosMotivoForm.setIdGlg("3");
    parametrosMotivoForm.setCodAprobacionGlg("");
    parametrosMotivoForm.setIdCentroCostos("55");
    parametrosMotivoForm.setMaInclExcl("");
    parametrosMotivoForm.setCodSup("");
    parametrosMotivoForm.setCodFirma("");
    parametrosMotivoForm.setDescOscar("OSCAR");
    parametrosMotivoForm.setOscar(new OSCAR("OSCAR"));
    parametrosMotivoForm.setIdNivCarga("");
    parametrosMotivoForm.setTxAviso("");
    parametrosMotivoForm.setIdOperEspe("");
    parametrosMotivoForm.setMeDiasInterv("");
    parametrosMotivoForm.setCentrosCosto("445");
    parametrosMotivoForm.setFechaDesde("29/06/2023");
    parametrosMotivoForm.setFechaHasta("29/06/2023");
    parametrosMotivoForm.setCentrosCostoList(centroCostoList);

    return Stream.of(Arguments.of(parametrosMotivoForm));
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action execute")
  void shouldDetermineWhatActionExecute(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request,
                                        ParametrosMotivoForm parametrosMotivoForm, ActionForward actionForward, ParametrosService parametrosService,
                                        ParametroMotivo parametroMotivo) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(mockParametrosService.altaMotivo(parametroMotivo)).thenReturn("");
          when(actionMappingMocked.findForward(parametrosMotivoForm.getAccion())).thenReturn(actionForward);
        })) {
      try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
          (mockManagerTransaction, context) -> {
            doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          })) {
        //then
        ActionForward actionForwardToAssert = parametrosMotivoSaveAction.executeAction(actionMapping, parametrosMotivoForm, samApplication, samClient, request,
            httpServletResponse);
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("altaSource")
  @DisplayName("Should save Alta Motivo to Form")
  void shouldSaveAltaMotivoToForm(HttpServletRequest request, ParametrosMotivoForm parametrosMotivoForm, ParametrosService parametrosService,
                              ParametroMotivo parametroMotivo) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(mockParametrosService.altaMotivo(parametroMotivo)).thenReturn("message");
        })) {
      try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
          (mockManagerTransaction, context) -> {
            doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          })) {
        //then
        Method altaMocked = ParametrosMotivoSaveAction.class.getDeclaredMethod("alta", HttpServletRequest.class, ParametrosMotivoForm.class,
            ParametrosService.class);
        altaMocked.setAccessible(true);
        String status = (String) altaMocked.invoke(parametrosMotivoSaveAction, request, parametrosMotivoForm, parametrosService);
        assertEquals("success", status);
      }
    }
  }


  @ParameterizedTest
  @MethodSource("altaSource")
  @DisplayName("Should save Baja Motivo to Form")
  void shouldSaveBajaMotivoToForm(HttpServletRequest request, ParametrosMotivoForm parametrosMotivoForm, ParametrosService parametrosService,
                                  ParametroMotivo parametroMotivo) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(mockParametrosService.bajaMotivo(parametroMotivo.getCodigo())).thenReturn("message");
        })) {
      try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
          (mockManagerTransaction, context) -> {
            doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          })) {
        //then
        Method bajaMocked = ParametrosMotivoSaveAction.class.getDeclaredMethod("baja", HttpServletRequest.class, ParametrosMotivoForm.class, ParametrosService.class);
        bajaMocked.setAccessible(true);
        String status = (String) bajaMocked.invoke(parametrosMotivoSaveAction, request, parametrosMotivoForm, parametrosService);
        assertEquals("success", status);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("altaSource")
  @DisplayName("Should save Modificacion Motivo to Form")
  void shouldSaveModificacionMotivoToForm(HttpServletRequest request, ParametrosMotivoForm parametrosMotivoForm, ParametrosService parametrosService,
                                          ParametroMotivo parametroMotivo) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(mockParametrosService.modificacionMotivo(parametroMotivo)).thenReturn("message");
        })) {
      try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
          (mockManagerTransaction, context) -> {
            doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          })) {
        //then
        Method modificacionMocked = ParametrosMotivoSaveAction.class.getDeclaredMethod("modificacion", HttpServletRequest.class, ParametrosMotivoForm.class, ParametrosService.class);
        modificacionMocked.setAccessible(true);
        String status = (String) modificacionMocked.invoke(parametrosMotivoSaveAction, request, parametrosMotivoForm, parametrosService);
        assertEquals("success", status);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("formToMotivoSource")
  @DisplayName("Should map Form values into Motivo")
  void shouldMapFormValuesIntoMotivo(ParametrosMotivoForm parametrosMotivoForm) throws Exception {
    //then
    Method formToMotivoMocked = ParametrosMotivoSaveAction.class.getDeclaredMethod("formToMotivo", ParametrosMotivoForm.class);
    formToMotivoMocked.setAccessible(true);
    ParametroMotivo parametroMotivoToAssert = (ParametroMotivo) formToMotivoMocked.invoke(parametrosMotivoSaveAction, parametrosMotivoForm);
    assertAll(() -> assertNotNull(parametrosMotivoForm),
        () -> assertNotNull(parametroMotivoToAssert));
  }
}
