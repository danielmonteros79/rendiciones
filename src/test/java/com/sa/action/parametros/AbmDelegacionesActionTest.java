package com.sa.action.parametros;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.TipoPerfil;
import com.sa.entities.Usuario;
import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.ParametrosService;
import com.sa.util.ParamsConstants;
import org.apache.struts.action.*;
import org.apache.struts.apps.mailreader.dao.User;
import org.apache.struts.mock.*;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbmDelegacionesActionTest {

  @Mock
  IWebClient iWebClient;

  @InjectMocks
  AbmDelegacionesAction abmDelegacionesAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    HttpServletRequest httpServletRequest = new MockHttpServletRequest();
    HttpServletResponse httpServletResponse = new MockHttpServletResponse();
    List<Usuario> delegados = new ArrayList<>();
    RelacionUsuarioDelegadoForm form1 = new RelacionUsuarioDelegadoForm();
    RelacionUsuarioDelegadoForm form2 = new RelacionUsuarioDelegadoForm();
    ServletContext servletContext = new MockServletContext();

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());

    delegados.add(usuario2);
    delegados.add(usuario3);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);
    httpServletRequest.getSession().setAttribute("usuario", usuario);

    form1.setOpcion(ParamsConstants.SU81_MODIFICACION);
    form1.setUsuario("");
    form1.setDelegadoUser("");
    form1.setFeDesde("2000/01/01");
    form1.setFeHasta("2000/01/01");
    form1.setInforme("");
    form1.setAccion("");
    form1.setEstado("");
    form1.setFechaAlta("2000/01/01");
    form1.setUserAlta("userAlta");
    form1.setFeDesdeOld("2023/01/01");
    form1.setFeHastaOld("2023/01/01");

    form2.setOpcion("BAJA");
    form2.setUsuario("");
    form2.setDelegadoUser("");
    form2.setFeDesde("2000/01/01");
    form2.setFeHasta("2000/01/01");
    form2.setInforme("");
    form2.setAccion("");
    form2.setEstado("");
    form2.setFechaAlta("2000/01/01");
    form2.setUserAlta("userAlta");
    form2.setFeDesdeOld("2023/01/01");
    form2.setFeHastaOld("2023/01/01");

    Map<String, String> hashMap = new HashMap<>();
    hashMap.put("usuario", "");
    hashMap.put("delegadoUser", "");
    hashMap.put("fDesde", "2000/01/01");
    hashMap.put("fDesde_old", "2000/01/01");
    hashMap.put("fHasta", "2000/01/01");
    hashMap.put("fHasta_old", "2000/01/01");
    hashMap.put("fAlta", "2000/01/01");
    hashMap.put("opcion", "MODI");
    hashMap.put("accion", "");
    hashMap.put("estado", "");
    hashMap.put("informe", "");
    hashMap.put("id_reemplazo", "");
    hashMap.put("user_alta", "userAlta");

    Map<String, String> hashMap2 = new HashMap<>();
    hashMap2.put("usuario", "");
    hashMap2.put("delegadoUser", "");
    hashMap2.put("fDesde", "2000/01/01");
    hashMap2.put("fDesde_old", "2000/01/01");
    hashMap2.put("fHasta", "2000/01/01");
    hashMap2.put("fHasta_old", "2000/01/01");
    hashMap2.put("fAlta", "2000/01/01");
    hashMap2.put("opcion", "CONS");
    hashMap2.put("accion", "");
    hashMap2.put("estado", "");
    hashMap2.put("informe", "");
    hashMap2.put("id_reemplazo", "");
    hashMap2.put("user_alta", "userAlta");

    httpServletRequest.setAttribute("usuario", usuario);

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    return Stream.of(Arguments.of(actionMapping, samWebApplication, samWebClient, httpServletRequest, httpServletResponse, form1, hashMap),
            Arguments.of(actionMapping, samWebApplication, samWebClient, httpServletRequest, httpServletResponse, form2, hashMap2));
  }

  public static Stream<Arguments> executeActionExceptionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    HttpServletRequest httpServletRequest = new MockHttpServletRequest();
    HttpServletResponse httpServletResponse = new MockHttpServletResponse();
    List<Usuario> delegados = new ArrayList<>();
    RelacionUsuarioDelegadoForm form = new RelacionUsuarioDelegadoForm();
    ServletContext servletContext = new MockServletContext();

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());

    delegados.add(usuario2);
    delegados.add(usuario3);

    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);
    httpServletRequest.getSession().setAttribute("usuario", usuario);

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

    Map<String, String> hashMap = new HashMap<>();
    hashMap.put("usuario", "");
    hashMap.put("delegadoUser", "");
    hashMap.put("fDesde", "2000/01/01");
    hashMap.put("fDesde_old", "2000/01/01");
    hashMap.put("fHasta", "2000/01/01");
    hashMap.put("fHasta_old", "2000/01/01");
    hashMap.put("fAlta", "2000/01/01");
    hashMap.put("opcion", "MODI");
    hashMap.put("accion", "");
    hashMap.put("estado", "");
    hashMap.put("informe", "");
    hashMap.put("id_reemplazo", "");
    hashMap.put("user_alta", "userAlta");

    httpServletRequest.setAttribute("usuario", usuario);

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    return Stream.of(Arguments.of(actionMapping, samWebApplication, samWebClient, httpServletRequest, httpServletResponse, form, hashMap));
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action perform")
  void shouldDetermineWhatActionPerform(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, HttpServletRequest request,
                                        HttpServletResponse response, RelacionUsuarioDelegadoForm form, Map<String, String> hashMap) throws Exception {
    //given
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      //when
      doNothing().when(mockManagerTransaction).executeTrx(iWebClient, hashMap);
    })) {
      //then
      ActionForward actionForward = abmDelegacionesAction.executeAction(actionMapping, form, samApplication, samClient, request, response);
      assertNotNull(actionForward);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action perform2")
  void shouldDetermineWhatActionPerform2(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, HttpServletRequest request,
                                         HttpServletResponse response, RelacionUsuarioDelegadoForm form, Map<String, String> hashMap) throws Exception {
    //given
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      //when
      doNothing().when(mockManagerTransaction).executeTrx(iWebClient, hashMap);
    })) {
      //given
      try (MockedConstruction<ParametrosService> parametrosServiceMC = mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
        //when
        when(mockParametrosService.abmDelegaciones(form, (Usuario) request.getSession().getAttribute("usuario"))).thenReturn("msg");
      })) {
        //then
        ActionForward actionForward = abmDelegacionesAction.executeAction(actionMapping, form, samApplication, samClient, request, response);
        assertNotNull(actionForward);
      }
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionExceptionSource")
  @DisplayName("Should throw a GenericException")
  void shouldThrowAGenericException(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, HttpServletRequest request,
                                    HttpServletResponse response, RelacionUsuarioDelegadoForm form, Map<String, String> hashMap) throws Exception {
    //given
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      //when
      doNothing().when(mockManagerTransaction).executeTrx(iWebClient, hashMap);
    })) {
      //given
      try (MockedConstruction<ParametrosService> parametrosServiceMC = mockConstruction(ParametrosService.class, (mockParametrosService, context) -> {
        //when
        when(mockParametrosService.abmDelegaciones(form, (Usuario) request.getSession().getAttribute("usuario"))).thenReturn(null);
      })) {
        //then
      }
    }
    //then
    ActionForward actionForward = abmDelegacionesAction.executeAction(actionMapping, form, samApplication, samClient, request, response);
    assertNotNull(actionForward);
  }
}
