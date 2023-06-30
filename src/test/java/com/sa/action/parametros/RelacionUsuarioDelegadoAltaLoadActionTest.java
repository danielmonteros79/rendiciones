package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.OSCAR;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosMotivoForm;
import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RelacionUsuarioDelegadoAltaLoadActionTest {

  @Mock
  ActionMapping actionMappingMocked;

  @Mock
  HttpServletResponse httpServletResponse;

  @InjectMocks
  RelacionUsuarioDelegadoAltaLoadAction relacionUsuarioDelegadoAltaLoadAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    HttpSession httpSessionFormNull = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest requestM = new MockHttpServletRequest();
    MockHttpServletRequest requestMNull = new MockHttpServletRequest();
    MockHttpServletRequest requestA = new MockHttpServletRequest();
    MockHttpServletRequest requestAFormNull = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();
    RelacionUsuarioDelegadoForm relacionUsuarioDelegadoForm = new RelacionUsuarioDelegadoForm();
    List<ParametriaUsuarioDelegado> parametriaUsuarioDelegadoList = new ArrayList<>();

    relacionUsuarioDelegadoForm.setFeDesde("30/06/2023");
    relacionUsuarioDelegadoForm.setFeHasta("30/06/2023");

    ParametriaUsuarioDelegado parametriaUsuarioDelegado = new ParametriaUsuarioDelegado();
    parametriaUsuarioDelegado.setId(0);
    parametriaUsuarioDelegado.setDelegadoUser("");
    parametriaUsuarioDelegado.setDelegadoNombre("");
    parametriaUsuarioDelegado.setDelegadoCentroCostos("");
    parametriaUsuarioDelegado.setDelegadoSector("");
    parametriaUsuarioDelegado.setDelegadoInforme("");
    parametriaUsuarioDelegado.setDelegadoAccion("");
    parametriaUsuarioDelegado.setDelegadoEstado("");
    parametriaUsuarioDelegado.setFeDesde(new Date());
    parametriaUsuarioDelegado.setFeHasta(new Date());
    parametriaUsuarioDelegado.setFechaAlta("30/06/2023");
    parametriaUsuarioDelegado.setUsuarioAlta("");

    parametriaUsuarioDelegadoList.add(parametriaUsuarioDelegado);

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    httpSession.setAttribute("usuario", usuario);
    httpSession.setAttribute("delegacionesActivas", parametriaUsuarioDelegadoList);
    httpSession.setAttribute("frmDelegacion", relacionUsuarioDelegadoForm);

    httpSessionFormNull.setAttribute("usuario", usuario);
    httpSessionFormNull.setAttribute("delegacionesActivas", parametriaUsuarioDelegadoList);

    requestM.setHttpSession(httpSession);
    requestM.getSession().setAttribute("message", "This is a message");
    requestM.addParameter("optn", "M");
    requestM.addParameter("callParam", "0");

    requestMNull.setHttpSession(httpSession);
    requestMNull.getSession().setAttribute("message", "This is a message");
    requestMNull.setAttribute("frmDelegacion", relacionUsuarioDelegadoForm);
    requestMNull.addParameter("optn", "M");
    requestMNull.addParameter("callParam", null);

    requestA.setHttpSession(httpSession);
    requestA.getSession().setAttribute("message", "This is a message");
    requestA.setAttribute("frmDelegacion", relacionUsuarioDelegadoForm);
    requestA.addParameter("optn", "A");

    requestAFormNull.setHttpSession(httpSession);
    requestAFormNull.getSession().setAttribute("message", "This is a message");
    requestAFormNull.setAttribute("frmDelegacion", null);
    requestAFormNull.addParameter("optn", "A");

    actionMapping.addForwardConfig(new ActionForward("success", "path1", false));

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
        Arguments.of(actionMapping, samWebApplication, samWebClient, requestM, relacionUsuarioDelegadoForm),
        Arguments.of(actionMapping, samWebApplication, samWebClient, requestMNull, relacionUsuarioDelegadoForm),
        Arguments.of(actionMapping, samWebApplication, samWebClient, requestA, relacionUsuarioDelegadoForm),
        Arguments.of(actionMapping, samWebApplication, samWebClient, requestAFormNull, relacionUsuarioDelegadoForm)
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
                                        RelacionUsuarioDelegadoForm relacionUsuarioDelegadoForm) throws Exception {
    //then
    ActionForward actionForwardToAssert = relacionUsuarioDelegadoAltaLoadAction.executeAction(actionMapping, relacionUsuarioDelegadoForm, samApplication,
        samClient, request, httpServletResponse);
    assertNotNull(actionForwardToAssert);
  }
}
