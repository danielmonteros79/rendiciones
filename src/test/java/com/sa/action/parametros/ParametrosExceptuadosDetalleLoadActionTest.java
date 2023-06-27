package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroExceptuado;
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParametrosExceptuadosDetalleLoadActionTest {

  @Mock
  HttpServletResponse httpServletResponse;

  @InjectMocks
  ParametrosExceptuadosDetalleLoadAction parametrosExceptuadosDetalleLoadAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMappingAlta = new ActionMapping();
    ActionMapping actionMappingBaja = new ActionMapping();
    ActionMapping actionMappingMod = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();

    ParametrosExceptuadosForm parametrosExceptuadosFormAlta = new ParametrosExceptuadosForm();
    parametrosExceptuadosFormAlta.setAccion("alta");
    parametrosExceptuadosFormAlta.setMarca("MARCA");
    parametrosExceptuadosFormAlta.setMotivoUsuario("MOTIVO");

    ParametrosExceptuadosForm parametrosExceptuadosFormBaja = new ParametrosExceptuadosForm();
    parametrosExceptuadosFormBaja.setAccion("baja");
    parametrosExceptuadosFormBaja.setMarca("MARCA");
    parametrosExceptuadosFormBaja.setMotivoUsuario("MOTIVO");

    ParametrosExceptuadosForm parametrosExceptuadosFormMod = new ParametrosExceptuadosForm();
    parametrosExceptuadosFormMod.setAccion("modificacion");
    parametrosExceptuadosFormMod.setMarca("MARCA");
    parametrosExceptuadosFormMod.setMotivoUsuario("MOTIVO");

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

    actionMappingAlta.addForwardConfig(new ActionForward("alta", "path1", false));
    actionMappingBaja.addForwardConfig(new ActionForward("baja", "path1", false));
    actionMappingMod.addForwardConfig(new ActionForward("modificacion", "path1", false));

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

    ParametroExceptuado parametroExceptuado = new ParametroExceptuado();
    parametroExceptuado.setEstado("");
    parametroExceptuado.setTipo("");
    parametroExceptuado.setHasta(new Date());
    parametroExceptuado.setDesde(new Date());
    parametroExceptuado.setDescripcionNombre("");
    parametroExceptuado.setMotivoUsuario("");

    List<ParametroExceptuado> parametroExceptuadoList = new ArrayList<>();
    parametroExceptuadoList.add(parametroExceptuado);

    return Stream.of(
        Arguments.of(actionMappingAlta, samWebApplication, samWebClient, request, parametrosExceptuadosFormAlta, parametroExceptuado, usuario,
            parametroExceptuadoList),
        Arguments.of(actionMappingBaja, samWebApplication, samWebClient, request, parametrosExceptuadosFormBaja, parametroExceptuado, usuario,
            parametroExceptuadoList),
        Arguments.of(actionMappingMod, samWebApplication, samWebClient, request, parametrosExceptuadosFormMod, parametroExceptuado, usuario,
            parametroExceptuadoList)
                    );
  }

  public static Stream<Arguments> exceptuadoToFormSource() {
    //given

    ParametrosExceptuadosForm parametrosExceptuadosFormMarcaM = new ParametrosExceptuadosForm();
    parametrosExceptuadosFormMarcaM.setAccion("alta");
    parametrosExceptuadosFormMarcaM.setMarca("M");

    ParametrosExceptuadosForm parametrosExceptuadosFormMarcaU = new ParametrosExceptuadosForm();
    parametrosExceptuadosFormMarcaU.setAccion("alta");
    parametrosExceptuadosFormMarcaU.setMarca("U");

    ParametroExceptuado parametroExceptuado = new ParametroExceptuado();
    parametroExceptuado.setEstado("ESTADO");
    parametroExceptuado.setTipo("TIPO");
    parametroExceptuado.setHasta(new Date());
    parametroExceptuado.setDesde(new Date());
    parametroExceptuado.setDescripcionNombre("DescripcionNombre");
    parametroExceptuado.setMotivoUsuario("MOTIVO");

    return Stream.of(
        Arguments.of(parametrosExceptuadosFormMarcaM, parametroExceptuado),
        Arguments.of(parametrosExceptuadosFormMarcaU, parametroExceptuado)
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
                                        ParametrosExceptuadosForm parametrosExceptuadosForm, ParametroExceptuado parametroExceptuado, Usuario usuario,
                                        List<ParametroExceptuado> parametroExceptuadoList) throws Exception {
    //given
    Method exceptuadoToFormMocked = ParametrosExceptuadosDetalleLoadAction.class.getDeclaredMethod("exceptuadoToForm", ParametrosExceptuadosForm.class, ParametroExceptuado.class);
    exceptuadoToFormMocked.setAccessible(true);
    exceptuadoToFormMocked.invoke(parametrosExceptuadosDetalleLoadAction, parametrosExceptuadosForm, parametroExceptuado);

    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
      when(mockParametrosService.getExceptuado(parametrosExceptuadosForm.getMarca(), parametrosExceptuadosForm.getMotivoUsuario(), usuario.getIdUser())).thenReturn(parametroExceptuadoList);
    })) {
      //then
      ActionForward actionForwardToAssert = parametrosExceptuadosDetalleLoadAction.executeAction(actionMapping, parametrosExceptuadosForm, samApplication, samClient,
          request, httpServletResponse);
      assertNotNull(actionForwardToAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("exceptuadoToFormSource")
  @DisplayName("Should set ParametrosExceptuados values")
  void shouldSetParametrosExceptuadosValues(ParametrosExceptuadosForm parametrosExceptuadosForm, ParametroExceptuado parametroExceptuado) throws Exception {

    //then
    Method exceptuadoToFormMocked = ParametrosExceptuadosDetalleLoadAction.class.getDeclaredMethod("exceptuadoToForm", ParametrosExceptuadosForm.class, ParametroExceptuado.class);
    exceptuadoToFormMocked.setAccessible(true);
    exceptuadoToFormMocked.invoke(parametrosExceptuadosDetalleLoadAction, parametrosExceptuadosForm, parametroExceptuado);

    assertAll(() -> assertNotNull(parametrosExceptuadosForm),
        () -> assertNotNull(parametroExceptuado));

  }


  
}
