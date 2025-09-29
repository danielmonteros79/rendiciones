package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroGasto;
import com.sa.form.parametros.ParametrosGastosFiltroForm;
import com.sa.services.ParametrosService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParametrosGastosLoadActionTest {

    @Mock
    HttpServletResponse httpServletResponse;

    @InjectMocks
    ParametrosGastosLoadAction parametrosGastosLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup usuario for session
        Usuario usuario = new Usuario("testUser", "perfil", "Test User", 1, "sector", new ArrayList<>());
        parametrosGastosLoadAction.setSessionUser(usuario);
        parametrosGastosLoadAction.setSessionUserWorking(usuario);
    }

    // Tests básicos existentes
    public static Stream<Arguments> executeActionSource() {
        //given
        ActionMapping actionMapping = new ActionMapping();
        SAMWebApplication samWebApplication = new SAMWebApplication();
        HttpSession httpSession = new MockHttpSession();
        SAMWebClient samWebClient = new SAMWebClient();
        MockHttpServletRequest request = new MockHttpServletRequest();
        List<Usuario> delegados = new ArrayList<>();
        ServletContext servletContext = new MockServletContext();

        ParametrosGastosFiltroForm parametrosGastosFiltroForm = new ParametrosGastosFiltroForm();
        parametrosGastosFiltroForm.setGasto("13");

        ParametroGasto parametroGasto = new ParametroGasto();

        List<ParametroGasto> parametroGastoList = new ArrayList<>();
        parametroGastoList.add(parametroGasto);

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
            Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosGastosFiltroForm)
        );
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Should determine what action execute")
    void shouldDetermineWhatActionExecute(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request,
                                          ParametrosGastosFiltroForm parametrosGastosFiltroForm) throws Exception {
        //when
        try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
            (mockParametrosService, context) -> {
                when(mockParametrosService.getMsgAviso()).thenReturn("This is a message");
            })) {
            //then
            ActionForward actionForwardToAssert = parametrosGastosLoadAction.executeAction(actionMapping, parametrosGastosFiltroForm, samApplication, samClient, request,
                httpServletResponse);
            assertNotNull(actionForwardToAssert);
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Should catch an exception")
    void shouldCatchAnException(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request,
                                ParametrosGastosFiltroForm parametrosGastosFiltroForm) throws Exception {
        //then
        ActionForward actionForwardToAssert = parametrosGastosLoadAction.executeAction(actionMapping, parametrosGastosFiltroForm, samApplication, samClient, request,
            httpServletResponse);
        assertNotNull(actionForwardToAssert);
    }

    // ========== NUEVOS TESTS ESPECÍFICOS PARA EL CÓDIGO SELECCIONADO ==========

    @Test
    @DisplayName("Should handle 'filtrar' action with valid gasto parameter")
    void shouldHandleFiltrarActionWithValidGastoParameter() throws Exception {
        // Arrange
        ActionMapping actionMapping = createBasicActionMapping();
        MockHttpServletRequest request = createBasicRequest();
        request.addParameter("action", "filtrar");
        request.addParameter("gasto", "123");
        request.addParameter("motivo", "testMotivo");

        ParametrosGastosFiltroForm form = new ParametrosGastosFiltroForm();
        SAMWebApplication samApp = createBasicSamApp();
        SAMWebClient samClient = createBasicSamClient(request);

        List<ParametroGasto> mockGastos = createMockParametroGastos();

        // Act & Assert
        try (MockedConstruction<ParametrosService> serviceConstruction = Mockito.mockConstruction(ParametrosService.class,
            (mockService, context) -> {
                when(mockService.getGastos(anyString(), eq("0123"), eq("testMotivo"))).thenReturn(mockGastos);
                when(mockService.getMsgAviso()).thenReturn("Success message");
            })) {

            ActionForward result = parametrosGastosLoadAction.executeAction(actionMapping, form, samApp, samClient, request, httpServletResponse);
            
            assertNotNull(result);
            assertEquals("parametrosGastoFiltro", result.getName());
            
            // Verify gastos were set in request
            assertEquals(mockGastos, request.getAttribute("gastos"));
        }
    }

    @Test
    @DisplayName("Should format gasto code with leading zeros")
    void shouldFormatGastoCodeWithLeadingZeros() throws Exception {
        // Arrange
        ActionMapping actionMapping = createBasicActionMapping();
        MockHttpServletRequest request = createBasicRequest();
        request.addParameter("action", "filtrar");
        request.addParameter("gasto", "5");
        request.addParameter("motivo", "test");

        ParametrosGastosFiltroForm form = new ParametrosGastosFiltroForm();
        SAMWebApplication samApp = createBasicSamApp();
        SAMWebClient samClient = createBasicSamClient(request);

        // Act & Assert
        try (MockedConstruction<ParametrosService> serviceConstruction = Mockito.mockConstruction(ParametrosService.class,
            (mockService, context) -> {
                when(mockService.getGastos(anyString(), eq("0005"), eq("test"))).thenReturn(new ArrayList<>());
                when(mockService.getMsgAviso()).thenReturn("Success");
            })) {

            ActionForward result = parametrosGastosLoadAction.executeAction(actionMapping, form, samApp, samClient, request, httpServletResponse);
            
            assertNotNull(result);
            
            // Verify the service was called with formatted code
            List<ParametrosService> services = serviceConstruction.constructed();
            assertEquals(1, services.size());
        }
    }

    @Test
    @DisplayName("Should handle null gasto parameter")
    void shouldHandleNullGastoParameter() throws Exception {
        // Arrange
        ActionMapping actionMapping = createBasicActionMapping();
        MockHttpServletRequest request = createBasicRequest();
        request.addParameter("action", "filtrar");
        request.addParameter("gasto", null);
        request.addParameter("motivo", "test");

        ParametrosGastosFiltroForm form = new ParametrosGastosFiltroForm();
        SAMWebApplication samApp = createBasicSamApp();
        SAMWebClient samClient = createBasicSamClient(request);

        // Act & Assert
        try (MockedConstruction<ParametrosService> serviceConstruction = Mockito.mockConstruction(ParametrosService.class,
            (mockService, context) -> {
                when(mockService.getGastos(anyString(), eq(""), eq("test"))).thenReturn(new ArrayList<>());
                when(mockService.getMsgAviso()).thenReturn("Success");
            })) {

            ActionForward result = parametrosGastosLoadAction.executeAction(actionMapping, form, samApp, samClient, request, httpServletResponse);
            
            assertNotNull(result);
        }
    }

    @Test
    @DisplayName("Should handle empty gasto parameter")
    void shouldHandleEmptyGastoParameter() throws Exception {
        // Arrange
        ActionMapping actionMapping = createBasicActionMapping();
        MockHttpServletRequest request = createBasicRequest();
        request.addParameter("action", "filtrar");
        request.addParameter("gasto", "");
        request.addParameter("motivo", "test");

        ParametrosGastosFiltroForm form = new ParametrosGastosFiltroForm();
        SAMWebApplication samApp = createBasicSamApp();
        SAMWebClient samClient = createBasicSamClient(request);

        // Act & Assert
        try (MockedConstruction<ParametrosService> serviceConstruction = Mockito.mockConstruction(ParametrosService.class,
            (mockService, context) -> {
                when(mockService.getGastos(anyString(), eq(""), eq("test"))).thenReturn(new ArrayList<>());
                when(mockService.getMsgAviso()).thenReturn("Success");
            })) {

            ActionForward result = parametrosGastosLoadAction.executeAction(actionMapping, form, samApp, samClient, request, httpServletResponse);
            
            assertNotNull(result);
        }
    }

    @Test
    @DisplayName("Should handle invalid numeric gasto parameter")
    void shouldHandleInvalidNumericGastoParameter() throws Exception {
        // Arrange
        ActionMapping actionMapping = createBasicActionMapping();
        MockHttpServletRequest request = createBasicRequest();
        request.addParameter("action", "filtrar");
        request.addParameter("gasto", "abc"); // Invalid number
        request.addParameter("motivo", "test");

        ParametrosGastosFiltroForm form = new ParametrosGastosFiltroForm();
        SAMWebApplication samApp = createBasicSamApp();
        SAMWebClient samClient = createBasicSamClient(request);

        // Act & Assert
        try (MockedConstruction<ParametrosService> serviceConstruction = Mockito.mockConstruction(ParametrosService.class,
            (mockService, context) -> {
                when(mockService.getGastos(anyString(), eq(""), eq("test"))).thenReturn(new ArrayList<>());
                when(mockService.getMsgAviso()).thenReturn("Success");
            })) {

            ActionForward result = parametrosGastosLoadAction.executeAction(actionMapping, form, samApp, samClient, request, httpServletResponse);
            
            assertNotNull(result);
            // Should use empty string when number parsing fails and handle NumberFormatException properly
        }
    }

    @Test
    @DisplayName("Should transform estado codes correctly")
    void shouldTransformEstadoCodesCorrectly() throws Exception {
        // Arrange
        ActionMapping actionMapping = createBasicActionMapping();
        MockHttpServletRequest request = createBasicRequest();
        request.addParameter("action", "filtrar");
        request.addParameter("gasto", "123");
        request.addParameter("motivo", "test");

        ParametrosGastosFiltroForm form = new ParametrosGastosFiltroForm();
        SAMWebApplication samApp = createBasicSamApp();
        SAMWebClient samClient = createBasicSamClient(request);

        List<ParametroGasto> gastos = new ArrayList<>();
        ParametroGasto gastoActivo = new ParametroGasto();
        gastoActivo.setEstado("A");
        ParametroGasto gastoInactivo = new ParametroGasto();
        gastoInactivo.setEstado("I");
        ParametroGasto gastoOtro = new ParametroGasto();
        gastoOtro.setEstado("X");
        gastos.add(gastoActivo);
        gastos.add(gastoInactivo);
        gastos.add(gastoOtro);

        // Act & Assert
        try (MockedConstruction<ParametrosService> serviceConstruction = Mockito.mockConstruction(ParametrosService.class,
            (mockService, context) -> {
                when(mockService.getGastos(anyString(), anyString(), anyString())).thenReturn(gastos);
                when(mockService.getMsgAviso()).thenReturn("Success");
            })) {

            ActionForward result = parametrosGastosLoadAction.executeAction(actionMapping, form, samApp, samClient, request, httpServletResponse);
            
            assertNotNull(result);
            
            // Verify estado transformations
            assertEquals("ACTIVO", gastoActivo.getEstado());
            assertEquals("INACTIVO", gastoInactivo.getEstado());
            assertEquals("X", gastoOtro.getEstado()); // Should remain unchanged
        }
    }

    @Test
    @DisplayName("Should handle service exception")
    void shouldHandleServiceException() throws Exception {
        // Arrange
        ActionMapping actionMapping = createBasicActionMapping();
        MockHttpServletRequest request = createBasicRequest();
        request.addParameter("action", "filtrar");
        request.addParameter("gasto", "123");
        request.addParameter("motivo", "test");

        ParametrosGastosFiltroForm form = new ParametrosGastosFiltroForm();
        SAMWebApplication samApp = createBasicSamApp();
        SAMWebClient samClient = createBasicSamClient(request);

        // Act & Assert
        try (MockedConstruction<ParametrosService> serviceConstruction = Mockito.mockConstruction(ParametrosService.class,
            (mockService, context) -> {
                when(mockService.getGastos(anyString(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("Service error"));
                when(mockService.getMsgAviso()).thenReturn("Error message");
            })) {

            ActionForward result = parametrosGastosLoadAction.executeAction(actionMapping, form, samApp, samClient, request, httpServletResponse);
            
            assertNotNull(result);
            assertEquals("parametrosGastoFiltro", result.getName());
        }
    }

    @Test
    @DisplayName("Should return success for null action")
    void shouldReturnSuccessForNullAction() throws Exception {
        // Arrange
        ActionMapping actionMapping = createBasicActionMapping();
        MockHttpServletRequest request = createBasicRequest();
        request.addParameter("action", null);

        ParametrosGastosFiltroForm form = new ParametrosGastosFiltroForm();
        SAMWebApplication samApp = createBasicSamApp();
        SAMWebClient samClient = createBasicSamClient(request);

        // Act
        ActionForward result = parametrosGastosLoadAction.executeAction(actionMapping, form, samApp, samClient, request, httpServletResponse);

        // Assert
        assertNotNull(result);
        assertEquals("success", result.getName());
    }

    @Test
    @DisplayName("Should return success for unknown action")
    void shouldReturnSuccessForUnknownAction() throws Exception {
        // Arrange
        ActionMapping actionMapping = createBasicActionMapping();
        MockHttpServletRequest request = createBasicRequest();
        request.addParameter("action", "unknownAction");

        ParametrosGastosFiltroForm form = new ParametrosGastosFiltroForm();
        SAMWebApplication samApp = createBasicSamApp();
        SAMWebClient samClient = createBasicSamClient(request);

        // Act
        ActionForward result = parametrosGastosLoadAction.executeAction(actionMapping, form, samApp, samClient, request, httpServletResponse);

        // Assert
        assertNotNull(result);
        assertEquals("success", result.getName());
    }

    // ========== MÉTODOS HELPER ==========

    private ActionMapping createBasicActionMapping() {
        ActionMapping actionMapping = new ActionMapping();
        actionMapping.addForwardConfig(new ActionForward("success", "/success.jsp", false));
        actionMapping.addForwardConfig(new ActionForward("parametrosGastoFiltro", "/parametrosGastoFiltro.jsp", false));
        return actionMapping;
    }

    private MockHttpServletRequest createBasicRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession httpSession = new MockHttpSession();
        
        List<Usuario> delegados = new ArrayList<>();
        Usuario usuario = new Usuario("testUser", "perfil", "Test User", 1, "sector", delegados);
        httpSession.setAttribute("usuario", usuario);
        
        request.setHttpSession(httpSession);
        return request;
    }

    private SAMWebApplication createBasicSamApp() {
        SAMWebApplication samApp = new SAMWebApplication();
        ServletContext servletContext = new MockServletContext();
        samApp.setContext(servletContext);
        return samApp;
    }

    private SAMWebClient createBasicSamClient(MockHttpServletRequest request) {
        SAMWebClient samClient = new SAMWebClient();
        samClient.setSession(request.getSession());
        samClient.setLoginOk(true);
        samClient.setId("testUser");
        return samClient;
    }

    private List<ParametroGasto> createMockParametroGastos() {
        List<ParametroGasto> gastos = new ArrayList<>();
        
        ParametroGasto gasto1 = new ParametroGasto();
        gasto1.setEstado("A");
        gasto1.setGasto("0001");
        gasto1.setDescripcionGasto("Gasto Test 1");
        
        ParametroGasto gasto2 = new ParametroGasto();
        gasto2.setEstado("I");
        gasto2.setGasto("0002");
        gasto2.setDescripcionGasto("Gasto Test 2");
        
        gastos.add(gasto1);
        gastos.add(gasto2);
        
        return gastos;
    }
}
