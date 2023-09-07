package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
import com.sa.manager.ManagerTransaction;
import com.sa.util.ParamsConstants;
import net.sf.json.JSONObject;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckCodigoExceptuadosActionTest {
  
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Spy
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
    List<Usuario> delegados = new ArrayList<>();
    RelacionUsuarioDelegadoForm form = new RelacionUsuarioDelegadoForm();
    ServletContext servletContext = new MockServletContext();
    PrintWriter printWriter = new PrintWriter(new ByteArrayOutputStream());
    
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
    
    return Stream.of(
      Arguments.of(actionMapping, samWebApplication, samWebClient, request, form, respHashMap, printWriter),
      Arguments.of(actionMapping, samWebApplication, samWebClient, request, form, respHashMap, printWriter)
                    );
  }
  
  public static Stream<Arguments> executeActionExceptionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    RelacionUsuarioDelegadoForm form = new RelacionUsuarioDelegadoForm();
    ServletContext servletContext = new MockServletContext();
    PrintWriter printWriter = new PrintWriter(new ByteArrayOutputStream());
    
    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    
    delegados.add(usuario2);
    delegados.add(usuario3);
    
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);
    
    request.getSession().setAttribute("usuario", usuario);
    request.addParameter("desMotivo", "except1");
    request.addParameter("motivoUsuario", "usuario");
    
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
    respHashMap.put("cod_mot_usu", "U");
    respHashMap.put("descripcion", "Description message");
    respHashMap.put("error", "Error message");
    
    return Stream.of(Arguments.of(actionMapping, samWebApplication, samWebClient, request, form, respHashMap, printWriter));
  }
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }
  
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action perform")
  void shouldDetermineWhatActionPerform(ActionMapping actionMapping, SAMWebApplication samApplication,
                                        SAMWebClient samClient, MockHttpServletRequest request,
                                        RelacionUsuarioDelegadoForm relacionUsuarioDelegadoForm,
                                        Map<String, Object> respHashMap, PrintWriter printWriter) throws Exception {
    //when
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      doNothing().when(mockManagerTransaction).executeTrx(samWebClientMocked, respHashMap);
      when(mockManagerTransaction.getDataReturn()).thenReturn("data");
    })) {
      
      try (MockedStatic<JSONObject> jsonObjectMockedStatic = mockStatic(JSONObject.class)) {
        jsonObjectMockedStatic.when(() -> JSONObject.fromObject(any())).thenReturn(jsonObjectMocked);
        jsonObjectMockedStatic.when(() -> JSONObject.fromObject(any(), any())).thenReturn(jsonObjectMocked);
        
        when(httpServletResponseMocked.getWriter()).thenReturn(printWriter);
        
        //then
        ActionForward actionForward = checkCodigoExceptuadosAction.executeAction(actionMapping, relacionUsuarioDelegadoForm,
          samApplication, samClient, request, httpServletResponseMocked);
        assertNull(actionForward);
      }
    }
  }
  
  @ParameterizedTest
  @MethodSource("executeActionExceptionSource")
  @DisplayName("Should throw an Exception")
  void shouldThrowAnException(ActionMapping actionMapping, SAMWebApplication samApplication,
                              SAMWebClient samClient, MockHttpServletRequest request,
                              RelacionUsuarioDelegadoForm relacionUsuarioDelegadoForm,
                              Map<String, Object> respHashMap, PrintWriter printWriter) throws Exception {
    //when
    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
      doNothing().when(mockManagerTransaction).executeTrx(samWebClientMocked, respHashMap);
    })) {
      
      try (MockedStatic<JSONObject> jsonObjectMockedStatic = mockStatic(JSONObject.class)) {
        jsonObjectMockedStatic.when(() -> JSONObject.fromObject(any())).thenReturn(jsonObjectMocked);
        jsonObjectMockedStatic.when(() -> JSONObject.fromObject(any(), any())).thenReturn(jsonObjectMocked);
        //then
        assertThrows(Exception.class, () -> {
          checkCodigoExceptuadosAction.executeAction(actionMapping, relacionUsuarioDelegadoForm,
            samApplication, samClient, request, httpServletResponseMocked);
        }, "Did not throw an exception");
      }
    }
  }
}
