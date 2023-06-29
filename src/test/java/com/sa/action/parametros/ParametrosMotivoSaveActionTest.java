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
    parametrosMotivoFormAlta.setIdGlg("");
    parametrosMotivoFormAlta.setCodAprobacionGlg("");
    parametrosMotivoFormAlta.setIdCentroCostos("");
    parametrosMotivoFormAlta.setMaInclExcl("");
    parametrosMotivoFormAlta.setCodSup("");
    parametrosMotivoFormAlta.setCodFirma("");
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
    parametrosMotivoFormBaja.setIdGlg("");
    parametrosMotivoFormBaja.setCodAprobacionGlg("");
    parametrosMotivoFormBaja.setIdCentroCostos("");
    parametrosMotivoFormBaja.setMaInclExcl("");
    parametrosMotivoFormBaja.setCodSup("");
    parametrosMotivoFormBaja.setCodFirma("");
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
    parametrosMotivoFormMod.setIdGlg("");
    parametrosMotivoFormMod.setCodAprobacionGlg("");
    parametrosMotivoFormMod.setIdCentroCostos("");
    parametrosMotivoFormMod.setMaInclExcl("");
    parametrosMotivoFormMod.setCodSup("");
    parametrosMotivoFormMod.setCodFirma("");
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
    return null;
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
}
