package com.sa.action.cierre;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.CierreTarjeta;
import com.sa.entities.Usuario;
import com.sa.services.CierreService;
import com.sa.services.RendicionesService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ConsumosNoRendidosActionTest {

    @Mock
    private ActionMapping actionMapping;
    @Mock
    private ActionForm actionForm;
    @Mock
    private SAMWebApplication samWebApplication;
    @Mock
    private SAMWebClient samWebClient;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter printWriter;
    @Mock
    private HttpSession session;

    @InjectMocks
    private ConsumosNoRendidosAction action;

    private Usuario usuario;
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario("testUser", "perfil", "nombre", 1, "sector", new ArrayList<>());
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);

        action.setSessionUserWorking(usuario);
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);
    }

    // Tests for executeAction method (lines 34-48)
    @Test
    @DisplayName("Should route to filtrar when action is filtrar")
    void shouldRouteToFiltrarWhenActionIsFiltrar() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("filtrar");
        when(request.getParameter("usuario")).thenReturn("testUser");
        when(request.getParameter("montoMin")).thenReturn("100");
        when(request.getParameter("montoMax")).thenReturn("1000");
        when(request.getParameter("moneda")).thenReturn("USD");
        when(request.getParameter("fechaCierre")).thenReturn("2023-08-15");
        
        ActionForward expectedForward = new ActionForward("consumosSinRendir", "/consumos.jsp", false);
        when(actionMapping.findForward("consumosSinRendir")).thenReturn(expectedForward);
        
        List<CierreTarjeta> mockList = new ArrayList<>();
        try (MockedConstruction<CierreService> cierreServiceMC = mockConstruction(CierreService.class,
            (mockCierreService, context) -> {
                when(mockCierreService.obtenerCuponesPendientes(anyString(), anyString(), anyString(), anyString(), anyString()))
                    .thenReturn(mockList);
                when(mockCierreService.getMsg()).thenReturn("Success message");
            })) {
            
            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication, 
                samWebClient, request, response);
            
            // Assert
            assertNotNull(result);
            assertEquals("consumosSinRendir", result.getName());
            verify(request).setAttribute("consumosSinRendir", mockList);
        }
    }    @Test
    @DisplayName("Should route to crearRendicion when action is altaRend")
    void shouldRouteToCrearRendicionWhenActionIsAltaRend() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("altaRend");
        when(request.getParameter("usuario")).thenReturn("testUser");
        when(request.getParameter("motivo")).thenReturn("business");
        when(request.getParameter("fecha")).thenReturn("2023-08-15");
        when(request.getParameter("descripcion")).thenReturn("Test description");

        try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class,
                (mockRendicionesService, context) -> {
                    when(mockRendicionesService.altaRendicion(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean()))
                            .thenReturn("12345");
                })) {

            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication,
                    samWebClient, request, response);

            // Assert
            assertNull(result); // writeJson returns null
            verify(response).getWriter();
        }
    }

    @Test
    @DisplayName("Should return success forward when action is null")
    void shouldReturnSuccessForwardWhenActionIsNull() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn(null);
        when(actionMapping.findForward("success")).thenReturn(new ActionForward());

        // Act
        ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication,
                samWebClient, request, response);

        // Assert
        assertNotNull(result);
        verify(actionMapping).findForward("success");
    }

    @Test
    @DisplayName("Should return success forward when action is empty")
    void shouldReturnSuccessForwardWhenActionIsEmpty() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("");
        when(actionMapping.findForward("success")).thenReturn(new ActionForward());

        // Act
        ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication,
                samWebClient, request, response);

        // Assert
        assertNotNull(result);
        verify(actionMapping).findForward("success");
    }

    @Test
    @DisplayName("Should return success forward when action is unknown")
    void shouldReturnSuccessForwardWhenActionIsUnknown() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("unknownAction");
        when(actionMapping.findForward("success")).thenReturn(new ActionForward());

        // Act
        ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication,
                samWebClient, request, response);

        // Assert
        assertNotNull(result);
        verify(actionMapping).findForward("success");
    }

    @Test
    @DisplayName("Should handle exception and return error response")
    void shouldHandleExceptionAndReturnErrorResponse() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenThrow(new RuntimeException("Test exception"));

        // Act
        ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication,
                samWebClient, request, response);

        // Assert
        assertNull(result); // writeError returns null
        verify(response).getWriter();
    }

    // Tests for filtrar method (lines 52-63)
    @Test
    @DisplayName("Should execute filtrar successfully with all parameters")
    void shouldExecuteFiltrarSuccessfullyWithAllParameters() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("filtrar");
        when(request.getParameter("usuario")).thenReturn("testUser");
        when(request.getParameter("montoMin")).thenReturn("100");
        when(request.getParameter("montoMax")).thenReturn("1000");
        when(request.getParameter("moneda")).thenReturn("USD");
        when(request.getParameter("fechaCierre")).thenReturn("2023-08-15");
        
        ActionForward expectedForward = new ActionForward("consumosSinRendir", "/consumos.jsp", false);
        when(actionMapping.findForward("consumosSinRendir")).thenReturn(expectedForward);
        
        List<CierreTarjeta> mockList = new ArrayList<>();
        CierreTarjeta cierre = new CierreTarjeta();
        mockList.add(cierre);
        
        try (MockedConstruction<CierreService> cierreServiceMC = mockConstruction(CierreService.class,
            (mockCierreService, context) -> {
                when(mockCierreService.obtenerCuponesPendientes("2023-08-15", "testUser", "100", "1000", "USD"))
                    .thenReturn(mockList);
                when(mockCierreService.getMsg()).thenReturn("Operation successful");
            })) {
            
            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication, 
                samWebClient, request, response);
            
            // Assert
            assertNotNull(result);
            assertEquals("consumosSinRendir", result.getName());
            verify(request).setAttribute("consumosSinRendir", mockList);
        }
    }    @Test
    @DisplayName("Should execute filtrar with null parameters")
    void shouldExecuteFiltrarWithNullParameters() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("filtrar");
        when(request.getParameter("usuario")).thenReturn(null);
        when(request.getParameter("montoMin")).thenReturn(null);
        when(request.getParameter("montoMax")).thenReturn(null);
        when(request.getParameter("moneda")).thenReturn(null);
        when(request.getParameter("fechaCierre")).thenReturn(null);
        when(actionMapping.findForward("consumosSinRendir")).thenReturn(new ActionForward());

        List<CierreTarjeta> mockList = new ArrayList<>();

        try (MockedConstruction<CierreService> cierreServiceMC = mockConstruction(CierreService.class,
                (mockCierreService, context) -> {
                    when(mockCierreService.obtenerCuponesPendientes(isNull(), isNull(), isNull(), isNull(), isNull()))
                            .thenReturn(mockList);
                    when(mockCierreService.getMsg()).thenReturn(null);
                })) {

            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication,
                    samWebClient, request, response);

            // Assert
            assertNotNull(result);
            verify(request).setAttribute("consumosSinRendir", mockList);
        }
    }

    @Test
    @DisplayName("Should set service message when available in filtrar")
    void shouldSetServiceMessageWhenAvailableInFiltrar() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("filtrar");
        when(request.getParameter("usuario")).thenReturn("testUser");
        when(request.getParameter("fechaCierre")).thenReturn("2023-08-15");
        
        ActionForward expectedForward = new ActionForward("consumosSinRendir", "/consumos.jsp", false);
        when(actionMapping.findForward("consumosSinRendir")).thenReturn(expectedForward);
        
        try (MockedConstruction<CierreService> cierreServiceMC = mockConstruction(CierreService.class,
            (mockCierreService, context) -> {
                when(mockCierreService.obtenerCuponesPendientes(anyString(), anyString(), anyString(), anyString(), anyString()))
                    .thenReturn(new ArrayList<>());
                when(mockCierreService.getMsg()).thenReturn("Service operation completed");
            })) {
            
            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication, 
                samWebClient, request, response);
            
            // Assert
            assertNotNull(result);
            assertEquals("consumosSinRendir", result.getName());
            // Note: setMessage is called but we can't verify it directly as it's a protected method
        }
    }    // Tests for crearRendicion method (lines 65-85)
    @Test
    @DisplayName("Should create rendicion successfully")
    void shouldCreateRendicionSuccessfully() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("altaRend");
        when(request.getParameter("usuario")).thenReturn("testUser");
        when(request.getParameter("motivo")).thenReturn("business");
        when(request.getParameter("fecha")).thenReturn("2023-08-15");
        when(request.getParameter("descripcion")).thenReturn("Test description");

        try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class,
                (mockRendicionesService, context) -> {
                    when(mockRendicionesService.altaRendicion("testUser", "business", "2023-08-15", "2023-08-15", "Test description", false))
                            .thenReturn("12345");
                })) {

            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication,
                    samWebClient, request, response);

            // Assert
            assertNull(result); // writeJson returns null
            verify(response).getWriter();
            String output = stringWriter.toString();
            assertTrue(output.contains("status"));
        }
    }

    @Test
    @DisplayName("Should return failure when rendicion creation fails with empty id")
    void shouldReturnFailureWhenRendicionCreationFailsWithEmptyId() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("altaRend");
        when(request.getParameter("usuario")).thenReturn("testUser");
        when(request.getParameter("motivo")).thenReturn("business");
        when(request.getParameter("fecha")).thenReturn("2023-08-15");
        when(request.getParameter("descripcion")).thenReturn("Test description");
        when(actionMapping.findForward("failure")).thenReturn(new ActionForward());

        try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class,
                (mockRendicionesService, context) -> {
                    when(mockRendicionesService.altaRendicion("testUser", "business", "2023-08-15", "2023-08-15", "Test description", false))
                            .thenReturn(""); // Empty string
                })) {

            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication,
                    samWebClient, request, response);

            // Assert
            assertNotNull(result);
            verify(request).setAttribute("validarTrx", 1);
            verify(actionMapping).findForward("failure");
        }
    }

    @Test
    @DisplayName("Should return failure when rendicion creation returns null")
    void shouldReturnFailureWhenRendicionCreationReturnsNull() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("altaRend");
        when(request.getParameter("usuario")).thenReturn("testUser");
        when(request.getParameter("motivo")).thenReturn("business");
        when(request.getParameter("fecha")).thenReturn("2023-08-15");
        when(request.getParameter("descripcion")).thenReturn("Test description");
        
        ActionForward expectedForward = new ActionForward("failure", "/failure.jsp", false);
        when(actionMapping.findForward("failure")).thenReturn(expectedForward);
        
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class,
            (mockRendicionesService, context) -> {
                when(mockRendicionesService.altaRendicion("testUser", "business", "2023-08-15", "2023-08-15", "Test description", false))
                    .thenReturn(null); // Null return
            })) {
            
            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication, 
                samWebClient, request, response);
            
            // Assert
            assertNotNull(result);
            assertEquals("failure", result.getName());
            verify(request).setAttribute("validarTrx", 1);
            verify(actionMapping).findForward("failure");
        }
    }    @Test
    @DisplayName("Should handle crearRendicion with null parameters")
    void shouldHandleCrearRendicionWithNullParameters() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("altaRend");
        when(request.getParameter("usuario")).thenReturn(null);
        when(request.getParameter("motivo")).thenReturn(null);
        when(request.getParameter("fecha")).thenReturn(null);
        when(request.getParameter("descripcion")).thenReturn(null);

        try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class,
                (mockRendicionesService, context) -> {
                    when(mockRendicionesService.altaRendicion(isNull(), isNull(), isNull(), isNull(), isNull(), eq(false)))
                            .thenReturn("12345");
                })) {

            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication,
                    samWebClient, request, response);

            // Assert
            assertNull(result); // writeJson returns null
            verify(response).getWriter();
        }
    }

    @Test
    @DisplayName("Should use same fecha for fechaDesde and fechaHasta in crearRendicion")
    void shouldUseSameFechaForFechaDesdeAndFechaHastaInCrearRendicion() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("altaRend");
        when(request.getParameter("usuario")).thenReturn("testUser");
        when(request.getParameter("motivo")).thenReturn("business");
        when(request.getParameter("fecha")).thenReturn("2023-08-15");
        when(request.getParameter("descripcion")).thenReturn("Test description");

        try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class,
                (mockRendicionesService, context) -> {
                    when(mockRendicionesService.altaRendicion(eq("testUser"), eq("business"), eq("2023-08-15"), eq("2023-08-15"), eq("Test description"), eq(false)))
                            .thenReturn("12345");
                })) {

            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication,
                    samWebClient, request, response);

            // Assert
            assertNull(result); // writeJson returns null

            // Verify that both fechaDesde and fechaHasta receive the same fecha parameter
            List<RendicionesService> services = rendicionesServiceMC.constructed();
            assertEquals(1, services.size());
            verify(services.get(0)).altaRendicion("testUser", "business", "2023-08-15", "2023-08-15", "Test description", false);
        }
    }

    @Test
    @DisplayName("Should return failure when rendicion creation returns whitespace only")
    void shouldReturnFailureWhenRendicionCreationReturnsWhitespace() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("altaRend");
        when(request.getParameter("usuario")).thenReturn("testUser");
        when(request.getParameter("motivo")).thenReturn("business");
        when(request.getParameter("fecha")).thenReturn("2023-08-15");
        when(request.getParameter("descripcion")).thenReturn("Test description");
        
        ActionForward expectedForward = new ActionForward("failure", "/failure.jsp", false);
        when(actionMapping.findForward("failure")).thenReturn(expectedForward);
        
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class,
            (mockRendicionesService, context) -> {
                when(mockRendicionesService.altaRendicion("testUser", "business", "2023-08-15", "2023-08-15", "Test description", false))
                    .thenReturn("   "); // Whitespace only string
            })) {
            
            // Act
            ActionForward result = action.executeAction(actionMapping, actionForm, samWebApplication, 
                samWebClient, request, response);
            
            // Assert
            assertNotNull(result);
            assertEquals("failure", result.getName());
            verify(request).setAttribute("validarTrx", 1);
            verify(actionMapping).findForward("failure");
        }
    }
}
