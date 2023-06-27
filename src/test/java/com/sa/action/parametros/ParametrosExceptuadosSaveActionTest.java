package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.form.parametros.ParametrosExceptuadosForm;
import com.sa.services.ParametrosService;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParametrosExceptuadosSaveActionTest {

  @Mock
  HttpServletResponse httpServletResponse;

  @InjectMocks
  ParametrosExceptuadosSaveAction parametrosExceptuadosSaveAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();

    ParametrosExceptuadosForm parametrosExceptuadosFormAlta = new ParametrosExceptuadosForm();
    parametrosExceptuadosFormAlta.setAccion("alta");

    ParametrosExceptuadosForm parametrosExceptuadosFormBaja = new ParametrosExceptuadosForm();
    parametrosExceptuadosFormBaja.setAccion("baja");

    ParametrosExceptuadosForm parametrosExceptuadosFormMod = new ParametrosExceptuadosForm();
    parametrosExceptuadosFormMod.setAccion("modificacion");

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

    return Stream.of(
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosExceptuadosFormAlta),
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosExceptuadosFormBaja),
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosExceptuadosFormMod)
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
                                        ParametrosExceptuadosForm parametrosExceptuadosForm) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(mockParametrosService.altaExceptuado(parametrosExceptuadosForm)).thenReturn("");
          when(mockParametrosService.deleteExceptuado(parametrosExceptuadosForm)).thenReturn("");
          when(mockParametrosService.saveModExceptuado(parametrosExceptuadosForm)).thenReturn("");
        })) {
      //then
      ActionForward actionForwardToAssert = parametrosExceptuadosSaveAction.executeAction(actionMapping, parametrosExceptuadosForm, samApplication, samClient, request,
          httpServletResponse);
      assertNotNull(actionForwardToAssert);
    }
  }
}
