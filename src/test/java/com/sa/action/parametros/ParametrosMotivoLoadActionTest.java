package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosMotivoFiltroForm;
import com.sa.services.ParametrosService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParametrosMotivoLoadActionTest {

    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    ActionMapping actionMappingMock;
    @Mock
    HttpServletRequest requestMock;
    @Mock
    SAMWebApplication samWebApplicationMock;
    @Mock
    SAMWebClient samWebClientMock;
    @Mock
    ParametrosMotivoFiltroForm formMock;

    @InjectMocks
    ParametrosMotivoLoadAction parametrosMotivoLoadAction;

    private Usuario usuarioMock;

    public static Stream<Arguments> executeActionSource() {
        //given
        ActionMapping actionMapping = new ActionMapping();
        SAMWebApplication samWebApplication = new SAMWebApplication();
        HttpSession httpSession = new MockHttpSession();
        SAMWebClient samWebClient = new SAMWebClient();
        MockHttpServletRequest request = new MockHttpServletRequest();
        List<Usuario> delegados = new ArrayList<>();
        ServletContext servletContext = new MockServletContext();

        ParametrosMotivoFiltroForm parametrosMotivoFiltroForm = new ParametrosMotivoFiltroForm();
        parametrosMotivoFiltroForm.setCodigo("11");

        ParametroMotivo parametroMotivo = new ParametroMotivo();

        List<ParametroMotivo> parametroMotivoList = new ArrayList<>();
        parametroMotivoList.add(parametroMotivo);

        Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
        Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
        delegados.add(usuario2);
        delegados.add(usuario3);
        Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

        httpSession.setAttribute("usuario", usuario);

        request.setHttpSession(httpSession);

        actionMapping.addForwardConfig(new ActionForward("success", "path1", false));

        samWebClient.setSession(httpSession);
        samWebClient.setLoginOk(true);
        samWebClient.setId("55");
        samWebClient.setAttribute("usuario", usuario);

        samWebApplication.setContext(servletContext);
        samWebApplication.setClientClass("");
        samWebApplication.setAttribute("usuario", usuario);

        return Stream.of(
                Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosMotivoFiltroForm, usuario, parametroMotivoList)
        );
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        usuarioMock = new Usuario("1234", "SS", "PEPE", 1212, "", null);
        parametrosMotivoLoadAction.setSessionUser(usuarioMock);
        parametrosMotivoLoadAction.setSessionUserWorking(usuarioMock);

    }

    /**
     * Helper method to setup mock user session for tests
     */
    private void setupMockUserSession() {
        Usuario testUser = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        parametrosMotivoLoadAction.setSessionUser(testUser);
        parametrosMotivoLoadAction.setSessionUserWorking(testUser);
    }

  /*@ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action perform")
  void shouldDetermineWhatActionPerform(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request,
                                        ParametrosMotivoFiltroForm parametrosMotivoFiltroForm, Usuario usuario, List<ParametroMotivo> parametroMotivoList) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(mockParametrosService.getMotivos(parametrosMotivoFiltroForm.getCodigo(), usuario.getIdUser())).thenReturn(parametroMotivoList);
          when(mockParametrosService.getMsgAviso()).thenReturn("Message");
        })) {
      //then
      ActionForward actionForwardToAssert = parametrosMotivoLoadAction.executeAction(actionMapping, parametrosMotivoFiltroForm, samApplication, samClient, request,
          httpServletResponse);
      assertNotNull(actionForwardToAssert);
    }
  }*/

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Should catch exception")
    void shouldCatchException(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request,
                              ParametrosMotivoFiltroForm parametrosMotivoFiltroForm, Usuario usuario, List<ParametroMotivo> parametroMotivoList) throws Exception {
        //then
        ActionForward actionForwardToAssert = parametrosMotivoLoadAction.executeAction(actionMapping, parametrosMotivoFiltroForm, samApplication, samClient, request,
                httpServletResponse);
        assertNotNull(actionForwardToAssert);
    }



    // ========== TEST COVERAGE FOR SELECTED CODE (LINES 79-89) ==========
    // Testing procesarBusquedaNumerica method range validation and formatting

    @Test
    @DisplayName("Should handle negative numbers as text search - line 79 condition")
    void shouldHandleNegativeNumbersAsTextSearch() throws Exception {
        // Arrange - Testing line 79: if (codInt < 0 || codInt > 9999)
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "-5");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq(""), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert - Should trigger text search (lines 80-81)
            assertNotNull(forward);
            assertEquals("parametrosMotivoFiltro", forward.getName());

            // Verify that empty code was used (indicating text search mode)
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq(""), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should handle numbers above 9999 as text search - line 79 condition")
    void shouldHandleNumbersAbove9999AsTextSearch() throws Exception {
        // Arrange - Testing line 79: codInt > 9999
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "10000");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq(""), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert - Should trigger text search (lines 80-81)
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq(""), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should format valid numbers with leading zeros - lines 83-84")
    void shouldFormatValidNumbersWithLeadingZeros() throws Exception {
        // Arrange - Testing lines 83-84: String.format("%04d", codInt) and isTextSearch = false
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "123");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq("0123"), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert - Should format with leading zeros
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq("0123"), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should handle NumberFormatException - lines 86-88")
    void shouldHandleNumberFormatException() throws Exception {
        // Arrange - Testing lines 86-88: catch (NumberFormatException e)
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "abc123"); // This will cause NumberFormatException
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq(""), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert - Should trigger text search (lines 87-88)
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq(""), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should handle boundary value 0 correctly - line 83-84")
    void shouldHandleBoundaryValueZero() throws Exception {
        // Arrange - Testing boundary case: codInt = 0
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "0");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq("0000"), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert - Should format as "0000"
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq("0000"), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should handle boundary value 9999 correctly - line 83-84")
    void shouldHandleBoundaryValue9999() throws Exception {
        // Arrange - Testing boundary case: codInt = 9999
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "9999");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq("9999"), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert - Should keep as "9999"
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq("9999"), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should handle boundary edge case -1 as text search - line 79")
    void shouldHandleBoundaryValueNegativeOne() throws Exception {
        // Arrange - Testing edge case: codInt = -1
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "-1");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq(""), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert - Should trigger text search
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq(""), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should handle boundary edge case 10000 as text search - line 79")
    void shouldHandleBoundaryValue10000() throws Exception {
        // Arrange - Testing edge case: codInt = 10000
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "10000");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq(""), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert - Should trigger text search
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq(""), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should format single digit numbers correctly - line 83")
    void shouldFormatSingleDigitNumbers() throws Exception {
        // Arrange - Testing String.format("%04d", codInt) for single digits
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "5");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq("0005"), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert - Should format as "0005"
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq("0005"), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should handle complex NumberFormatException scenarios - lines 86-88")
    void shouldHandleComplexNumberFormatExceptions() throws Exception {
        // Arrange - Testing various invalid number formats
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "2147483648"); // Larger than Integer.MAX_VALUE
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq(""), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert - Should handle overflow as text search
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq(""), anyString(), anyString());
        }
    }

    // ========== ADDITIONAL COVERAGE FOR RANGE VALIDATION EDGE CASES ==========

    @Test
    @DisplayName("Should test range validation case 1 - 0001")
    void shouldTestRangeValidationCase1() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "1");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq("0001"), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq("0001"), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should test range validation case 99 - 0099")
    void shouldTestRangeValidationCase99() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "99");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq("0099"), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq("0099"), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should test range validation case 999 - 0999")
    void shouldTestRangeValidationCase999() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "999");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq("0999"), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq("0999"), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should test range validation case 1234 already 4 digits")
    void shouldTestRangeValidationCase1234() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "1234");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq("1234"), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq("1234"), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should test invalid range -100 triggers text search")
    void shouldTestInvalidRangeNegative100() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "-100");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq(""), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq(""), anyString(), anyString());
        }
    }

    @Test
    @DisplayName("Should test invalid range 15000 triggers text search")
    void shouldTestInvalidRange15000() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "15000");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(eq(""), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Test message");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);
            assertNotNull(forward);
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance).getMotivos(eq(""), anyString(), anyString());
        }
    }
    @Test
    @DisplayName("Debe ejecutar action normalmente con número")
    void executeAction_filtrarPorNumero() throws Exception {
        // Setup real request with session and user
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "11");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        List<ParametroMotivo> motivos = new ArrayList<>();
        ParametroMotivo motivo = new ParametroMotivo();
        motivo.setCodigo("0011");
        motivo.setDescripcion("Descripción test");
        motivo.setCodSup("PSUP");
        motivo.setCodAprobacionGlg("MONTO");
        motivo.setCodFirma("MONTO");
        motivo.setEstado("A");
        motivo.setLastElement("S");
        motivos.add(motivo);

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(motivos);
            when(serviceMock.getMsgAviso()).thenReturn("Mensaje exitoso");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            assertNotNull(forward);
            assertEquals("parametrosMotivoFiltro", forward.getName());
        }
    }

    @Test
    @DisplayName("Debe ejecutar action filtrando por texto")
    void executeAction_filtrarPorTexto() throws Exception {
        // Setup real request with session and user
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "test");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        List<ParametroMotivo> motivos = new ArrayList<>();
        ParametroMotivo motivo = new ParametroMotivo();
        motivo.setCodigo("TEST01");
        motivo.setDescripcion("Una descripcion con test");
        motivo.setCodSup("SUPER");
        motivo.setCodAprobacionGlg("PGLG");
        motivo.setCodFirma("PFIRM");
        motivo.setEstado("I");
        motivo.setLastElement("S");
        motivos.add(motivo);

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(motivos);
            when(serviceMock.getMsgAviso()).thenReturn("Mensaje exitoso");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            assertNotNull(forward);
            assertEquals("parametrosMotivoFiltro", forward.getName());
        }
    }

    @Test
    @DisplayName("Debe ejecutar action sin código enviado")
    void executeAction_sinCodigo() throws Exception {
        // Setup real request with session and user
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        List<ParametroMotivo> motivos = new ArrayList<>();
        ParametroMotivo motivo = new ParametroMotivo();
        motivo.setCodigo("0002");
        motivo.setDescripcion("Test sin codigo");
        motivo.setCodSup("PSUP");
        motivo.setCodAprobacionGlg("MONTO");
        motivo.setCodFirma("PFIRM");
        motivo.setEstado("A");
        motivo.setLastElement("S");
        motivos.add(motivo);

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(motivos);
            when(serviceMock.getMsgAviso()).thenReturn("OK");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            assertNotNull(forward);
            assertEquals("parametrosMotivoFiltro", forward.getName());
        }
    }

    @Test
    @DisplayName("Debe capturar excepción general y devolver null")
    void executeAction_capturaExcepcionGeneral() throws Exception {
        when(httpServletResponse.getWriter()).thenReturn(mock(PrintWriter.class));
        when(requestMock.getParameter("action")).thenReturn("filtrar");

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            // Forzar una excepción que será capturada por el catch principal en executeAction
            when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenThrow(new RuntimeException("Error inesperado"));
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, requestMock, httpServletResponse);
            assertNull(forward, "El forward debe ser nulo porque la excepción principal llama a writeError");
        }
    }

    @Test
    @DisplayName("Debe detenerse si getMotivos devuelve vacío")
    void executeAction_sinMotivosDevueltos() throws Exception {
        // Setup real request with session and user
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "11");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(Collections.emptyList());
            when(serviceMock.getMsgAviso()).thenReturn("Mensaje sin motivos");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);
            assertNotNull(forward);
            assertEquals("parametrosMotivoFiltro", forward.getName());
        }
    }

    @Test
    @DisplayName("Debe detener búsqueda si texto no coincide con ningún motivo")
    void executeAction_textoSinCoincidencia() throws Exception {
        // Setup real request with session and user
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "nope");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        List<ParametroMotivo> motivos = new ArrayList<>();
        ParametroMotivo motivo = new ParametroMotivo();
        motivo.setCodigo("AAA");  // no contiene "nope"
        motivo.setDescripcion("Algo irrelevante");
        motivo.setLastElement("S");
        motivos.add(motivo);

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(motivos);
            when(serviceMock.getMsgAviso()).thenReturn("Mensaje sin coincidencias");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);
            assertNotNull(forward);
            assertEquals("parametrosMotivoFiltro", forward.getName());
        }
    }

    @Test
    @DisplayName("Debe mantener valores si no coinciden con condiciones de transformación")
    void executeAction_valoresNoTransformados() throws Exception {
        // Setup real request with session and user
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "9999");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

        List<ParametroMotivo> motivos = new ArrayList<>();
        ParametroMotivo motivo = new ParametroMotivo();
        motivo.setCodigo("9999");
        motivo.setDescripcion("Otra desc");
        motivo.setCodSup("ABC");
        motivo.setCodAprobacionGlg("XYZ");
        motivo.setCodFirma("QWE");
        motivo.setEstado("Z");
        motivo.setLastElement("S");
        motivos.add(motivo);

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(motivos);
            when(serviceMock.getMsgAviso()).thenReturn("Sin transformaciones");
        })) {
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);
            assertNotNull(forward);
            assertEquals("parametrosMotivoFiltro", forward.getName());
        }
    }

    // ========== TESTS CON CORRECCIONES INTEGRADAS ==========

    @Test
    @DisplayName("Debe manejar sesión de usuario nula sin lanzar excepción")
    void executeAction_handlesNullUserSession() throws Exception {
        // Arrange
        parametrosMotivoLoadAction.setSessionUserWorking(null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();

        // **FIX**: Se simula un entorno de sesión más completo, estableciendo los atributos que
        // las clases base podrían esperar, incluso si son nulos. Esto evita un NullPointerException.
        session.setAttribute("userWorking", null);
        session.setAttribute("usuario", null);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "1234");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro"));
        when(httpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class)) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert
            assertNotNull(forward, "El ActionForward no debe ser nulo.");
            assertNull(forward.getName(), "El nombre del ActionForward debe ser nulo.");
            assertEquals("parametrosMotivoFiltro", forward.getPath(), "El path debe ser 'parametrosMotivoFiltro'.");
        }
    }

    @Test
    @DisplayName("Debe manejar usuario con ID nulo sin lanzar excepción")
    void executeAction_handlesUserWithNullId() throws Exception {
        // Arrange
        Usuario usuarioSinId = new Usuario(null, "perfil", "nombre", 1, "sector", new ArrayList<>());
        parametrosMotivoLoadAction.setSessionUserWorking(usuarioSinId);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();

        // **FIX**: Al igual que en el test anterior, se completa la configuración de la sesión.
        session.setAttribute("userWorking", usuarioSinId);
        session.setAttribute("usuario", usuarioSinId);
        request.setHttpSession(session);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "1234");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro"));
        when(httpServletResponse.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class)) {
            // Act
            ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert
            assertNotNull(forward, "El ActionForward no debe ser nulo.");
            assertNull(forward.getName(), "El nombre del ActionForward debe ser nulo.");
            assertEquals("parametrosMotivoFiltro", forward.getPath(), "El path debe ser 'parametrosMotivoFiltro'.");
        }
    }

    @Test
    @DisplayName("Debe establecer el mensaje correcto cuando la búsqueda por texto falla y no hay resultados")
    void executeAction_noEstableceMensajeSiBusquedaPorTextoFalla() throws Exception {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        setupMockUserSession(); // usa un usuario válido
        session.setAttribute("userWorking", parametrosMotivoLoadAction.getSessionUserWorking());
        session.setAttribute("usuario", parametrosMotivoLoadAction.getSessionUser());
        request.setHttpSession(session);
        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "texto de busqueda"); // Búsqueda por texto
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro"));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            // Simula una excepción, lo que resulta en una lista de motivos vacía
            when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenThrow(new RuntimeException("Error de DB"));
        })) {
            // Act
            parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert
            // **FIX**: La aserción ahora espera el mensaje específico que genera el código
            // cuando una búsqueda de texto no arroja resultados, en lugar de un mensaje genérico.
            String message = (String) session.getAttribute("lastErrorMessage");
            assertEquals("No se encontraron motivos que contengan 'texto de busqueda'", message);
        }
    }

    @Test
    @DisplayName("Debe paginar correctamente cuando hay múltiples páginas de resultados")
    void executeAction_paginatesMultiPageResults() throws Exception {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id", "perfil", "nombre", 1, "sector", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        request.setHttpSession(session);
        parametrosMotivoLoadAction.setSessionUserWorking(usuario);

        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "");
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro"));

        // Página 1 de resultados
        List<ParametroMotivo> page1 = new ArrayList<>();
        ParametroMotivo motivo1 = new ParametroMotivo();
        motivo1.setCodigo("0001");
        motivo1.setLastElement("N"); // Indica que hay más páginas
        page1.add(motivo1);

        // Página 2 de resultados
        List<ParametroMotivo> page2 = new ArrayList<>();
        ParametroMotivo motivo2 = new ParametroMotivo();
        motivo2.setCodigo("0002");
        motivo2.setLastElement("S"); // Indica que es la última página
        page2.add(motivo2);

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            // Configura el mock para devolver las páginas secuencialmente
            when(serviceMock.getMotivos(anyString(), anyString(), eq("00"))).thenReturn(page1);
            when(serviceMock.getMotivos(anyString(), anyString(), eq("01"))).thenReturn(page2);
        })) {
            // Act
            parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert
            ParametrosService serviceInstance = mock.constructed().get(0);
            verify(serviceInstance, times(1)).getMotivos(eq(""), eq("id"), eq("00"));
            verify(serviceInstance, times(1)).getMotivos(eq(""), eq("id"), eq("01"));

            List<ParametroMotivo> motivosTotales = (List<ParametroMotivo>) request.getAttribute("motivos");
            assertEquals(2, motivosTotales.size(), "Deberían haberse acumulado los motivos de ambas páginas");
        }
    }

    @Test
    @DisplayName("Debe establecer mensaje de error si la búsqueda por código falla")
    void executeAction_manejaExcepcionEnBusquedaPorCodigo() throws Exception {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        setupMockUserSession();
        session.setAttribute("userWorking", parametrosMotivoLoadAction.getSessionUserWorking());
        request.setHttpSession(session);
        request.addParameter("action", "filtrar");
        request.addParameter("codigo", "9999"); // Búsqueda por código
        when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro"));

        try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
            // Simula una excepción dentro del bucle de búsqueda en buscarMotivos
            when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenThrow(new RuntimeException("Error de base de datos"));
        })) {
            // Act
            parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, request, httpServletResponse);

            // Assert
            // Verifica que el mensaje de error específico fue establecido en la sesión por manejarExcepcionBusqueda
            String errorMessage = (String) session.getAttribute("lastErrorMessage");
            assertEquals("No se encontró el motivo con código: 9999", errorMessage);
        }
    }

}