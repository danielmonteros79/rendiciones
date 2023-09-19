package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboOpcion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroAlerta;
import com.sa.form.parametros.ParametrosAlertasFiltroForm;
import com.sa.form.parametros.ParametrosAlertasForm;
import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
import com.sa.services.ParametrosService;
import com.sa.util.ParamsConstants;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParametrosAlertasFiltroActionTest {

  @Mock
  HttpServletResponse httpServletResponse;


  @InjectMocks
  ParametrosAlertasFiltroAction parametrosAlertasFiltroAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    HttpServletResponse response = new MockHttpServletResponse();
    ParametrosAlertasFiltroForm parametrosAlertasForm = new ParametrosAlertasFiltroForm();
    ParametroAlerta parametroAlerta = new ParametroAlerta();
    List<Usuario> delegados = new ArrayList<>();
    List<ParametroAlerta> parametroAlertaList = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    parametrosAlertasForm.setCodGasto("GASTO");
    parametrosAlertasForm.setCodMotivo("MOTIVO");

    httpSession.setAttribute("usuario", usuario);
    request.setHttpSession(httpSession);
    request.setAttribute("message", "This is a message");

    parametroAlerta.setCodGasto("GASTO");
    parametroAlerta.setCodMotivo("MOTIVO");
    parametroAlerta.setTimeStamp("22/06/2023");
    parametroAlerta.setMotivo("");

    parametroAlertaList.add(parametroAlerta);

    actionMapping.addForwardConfig(new ActionForward("success", "path1", false));

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    return Stream.of(Arguments.of(actionMapping, parametrosAlertasForm, samWebApplication, samWebClient, request,
        parametroAlertaList));
  }

  public static Stream<Arguments> executeActionMsgNullSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    HttpServletResponse response = new MockHttpServletResponse();
    ParametrosAlertasFiltroForm parametrosAlertasForm = new ParametrosAlertasFiltroForm();
    ParametroAlerta parametroAlerta = new ParametroAlerta();
    List<Usuario> delegados = new ArrayList<>();
    List<ParametroAlerta> parametroAlertaList = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    parametrosAlertasForm.setCodGasto("");
    parametrosAlertasForm.setCodMotivo("MOTIVO");

    httpSession.setAttribute("usuario", usuario);
    request.setHttpSession(httpSession);
    request.setAttribute("message", null);

    parametroAlerta.setCodGasto("GASTO");
    parametroAlerta.setCodMotivo("MOTIVO");
    parametroAlerta.setTimeStamp("22/06/2023");
    parametroAlerta.setMotivo("");

    parametroAlertaList.add(parametroAlerta);

    actionMapping.addForwardConfig(new ActionForward("fail", "path1", false));

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    return Stream.of(Arguments.of(actionMapping, parametrosAlertasForm, samWebApplication, samWebClient, request,
        parametroAlertaList));
  }

  public static Stream<Arguments> executeActionExceptionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    HttpServletResponse response = new MockHttpServletResponse();
    ParametrosAlertasFiltroForm parametrosAlertasForm = new ParametrosAlertasFiltroForm();
    ParametroAlerta parametroAlerta = new ParametroAlerta();
    List<Usuario> delegados = new ArrayList<>();
    List<ParametroAlerta> parametroAlertaList = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    parametrosAlertasForm.setCodGasto("GASTO");
    parametrosAlertasForm.setCodMotivo("MOTIVO");

    httpSession.setAttribute("usuario", usuario);
    request.setHttpSession(httpSession);
    request.setAttribute("message", "This is a message");

    parametroAlerta.setCodGasto("GASTO");
    parametroAlerta.setCodMotivo("MOTIVO");
    parametroAlerta.setTimeStamp("22/06/2023");
    parametroAlerta.setMotivo("");

    parametroAlertaList.add(parametroAlerta);

    actionMapping.addForwardConfig(new ActionForward("success", "path1", false));

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    return Stream.of(Arguments.of(actionMapping, parametrosAlertasForm, samWebApplication, samWebClient, request,
        parametroAlertaList));
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action execute")
  void shouldDetermineWhatActionExecute(ActionMapping actionMapping, ParametrosAlertasFiltroForm parametrosAlertasForm,
                                        SAMWebApplication samApplication, SAMWebClient samClient,
                                        MockHttpServletRequest request, List<ParametroAlerta> parametroAlertaList) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
      when(mockParametrosService.getAlertas("CONS", parametrosAlertasForm.getCodMotivo(),
          parametrosAlertasForm.getCodGasto())).thenReturn(parametroAlertaList);
    })) {
      //then
      ActionForward actionForward = parametrosAlertasFiltroAction.executeAction(actionMapping, parametrosAlertasForm, samApplication, samClient,
          request, httpServletResponse);
      assertNotNull(actionForward);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionMsgNullSource")
  @DisplayName("Should determine what action execute with msg null")
  void shouldDetermineWhatActionExecuteWithMsgNull(ActionMapping actionMapping,
                                                   ParametrosAlertasFiltroForm parametrosAlertasForm,
                                                   SAMWebApplication samApplication, SAMWebClient samClient,
                                                   MockHttpServletRequest request, List<ParametroAlerta> parametroAlertaList) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
      when(mockParametrosService.getAlertas("CONS", parametrosAlertasForm.getCodMotivo(),
          parametrosAlertasForm.getCodGasto())).thenReturn(parametroAlertaList);
      when(mockParametrosService.getMsgAviso()).thenReturn("");
    })) {
      //then
      ActionForward actionForward = parametrosAlertasFiltroAction.executeAction(actionMapping, parametrosAlertasForm, samApplication, samClient,
          request, httpServletResponse);
      assertNotNull(actionForward);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should catch an exception")
  void shouldCatchAnException(ActionMapping actionMapping, ParametrosAlertasFiltroForm parametrosAlertasForm,
                              SAMWebApplication samApplication, SAMWebClient samClient,
                              MockHttpServletRequest request, List<ParametroAlerta> parametroAlertaList) throws Exception {
    //then
    ActionForward actionForward = parametrosAlertasFiltroAction.executeAction(actionMapping, parametrosAlertasForm, samApplication, samClient,
        request, httpServletResponse);
    assertNotNull(actionForward);
  }
}
