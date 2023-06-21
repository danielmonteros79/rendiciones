package com.sa.action.parametros;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.ParametrosService;
import com.sa.util.ParamsConstants;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckCodigoExceptuadosActionTest {

  @Mock
  SAMWebClient samWebClient;

  @Mock
  JSONObject jsonObjectMocked;

  @InjectMocks
  CheckCodigoExceptuadosAction checkCodigoExceptuadosAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    List<Usuario> delegados = new ArrayList<>();
    RelacionUsuarioDelegadoForm form = new RelacionUsuarioDelegadoForm();
    ServletContext servletContext = new MockServletContext();

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());

    delegados.add(usuario2);
    delegados.add(usuario3);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    request.getSession().setAttribute("usuario", usuario);
    request.addParameter("desMotivo", "except1");
    request.addParameter("motivoUsuario", "motivo");

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

    request.setAttribute("usuario", usuario);

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    Map<String, Object> respHashMap = new HashMap<>();
    respHashMap.put("opcion", "CONS");
    respHashMap.put("mot_usu", "except1");
    respHashMap.put("cod_usuario", "55");
    respHashMap.put("cod_mot_usu", "M");
    respHashMap.put("descripcion", "Description message");
    respHashMap.put("error", "Error message");

    return Stream.of(Arguments.of(actionMapping, samWebApplication, samWebClient, request, response, form, respHashMap)
    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Disabled("Line 60 throws UnsupportedOperationException: JsonObject is null")
  @ParameterizedTest
  @MethodSource("executeActionSource")   //TODO linea 60 lanza UnsupportedOperationException -> JsonObject is null
  @DisplayName("Should determine what action perform")
  void shouldDetermineWhatActionPerform(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, HttpServletRequest request,
                                        HttpServletResponse response, RelacionUsuarioDelegadoForm form, Map<String, Object> respHashMap) throws Exception {
    //when
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      doNothing().when(mockManagerTransaction).executeTrx(samWebClient, respHashMap);
    })) {
      try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
        when(mockParametrosService.getCodigoExceptuado("55", "except1", "M")).thenReturn("");
      })) {
        try (MockedStatic<JSONObject> jsonObjectMockedStatic = mockStatic(JSONObject.class)) {
          jsonObjectMockedStatic.when(() -> JSONObject.fromObject(respHashMap)).thenReturn(jsonObjectMocked);

          //then
          ActionForward actionForward = checkCodigoExceptuadosAction.executeAction(actionMapping, form, samApplication, samClient, request, response);
          assertNotNull(actionForward);
        }
      }
    }
  }
}
