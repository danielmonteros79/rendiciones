package com.sa.action.aprobacion;

import com.sa.action.cierre.AprobacionesPendientesAction;
import com.sa.entities.CierreTarjeta;
import com.sa.services.CierreService;
import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AprobacionesPendientesActionTest {

    @Mock
    ActionMapping actionMapping;
    
    @Mock
    ActionForm actionForm;
    
    @Mock
    SAMWebApplication samWebApplication;
    
    @Mock
    SAMWebClient samWebClient;
    
    @Mock
    HttpServletRequest httpServletRequest;
    
    @Mock
    HttpServletResponse httpServletResponse;
    
    @Mock
    HttpSession httpSession;
    
    @Mock
    CierreService cierreService;
    
    @InjectMocks
    AprobacionesPendientesAction aprobacionesPendientesAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Debería devolver success cuando no hay action")
    void executeAction_Success() throws Exception {
        when(httpServletRequest.getParameter("action")).thenReturn(null);
        when(actionMapping.findForward("success")).thenReturn(new ActionForward("success", "/successPath", false));

        ActionForward result = aprobacionesPendientesAction.executeAction(actionMapping, actionForm, samWebApplication, samWebClient, httpServletRequest, httpServletResponse);

        assertNotNull(result, "El resultado de executeAction no debería ser null");
        assertEquals("success", result.getName(), "El nombre del forward debería ser 'success'");
    }

    @Test
    @DisplayName("Debería llamar a filtrar cuando action es 'filtrar'")
    void executeAction_Filtrar() throws Exception {
        when(httpServletRequest.getParameter("action")).thenReturn("filtrar");
        when(actionMapping.findForward("aprobacionesPendientes")).thenReturn(new ActionForward("aprobacionesPendientes", "/aprobacionesPath", false));  // Asegurar que el mock no devuelve null

        try (MockedConstruction<CierreService> mock = mockConstruction(CierreService.class, (mockCierreService, context) -> {
            when(mockCierreService.obtenerAprobacionesPendientes(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                    .thenReturn(Collections.emptyList());
        })) {
            ActionForward result = aprobacionesPendientesAction.executeAction(actionMapping, actionForm, samWebApplication, samWebClient, httpServletRequest, httpServletResponse);

            assertNotNull(result, "El resultado de executeAction no debería ser null"); // Validar antes de assertEquals
            assertEquals("aprobacionesPendientes", result.getName(), "El nombre del forward debería ser 'aprobacionesPendientes'");
        }
    }
    
    @Test
    @DisplayName("Testeando filtrar con parámetros válidos")
    void filtrar() throws Exception {
        // Configura los mocks necesarios
        when(httpServletRequest.getParameter("action")).thenReturn("filtrar");
        when(httpServletRequest.getParameter("usuario")).thenReturn("user1");
        when(httpServletRequest.getParameter("montoMin")).thenReturn("100");
        when(httpServletRequest.getParameter("montoMax")).thenReturn("500");
        when(httpServletRequest.getParameter("moneda")).thenReturn("USD");
        when(httpServletRequest.getParameter("fechaCierre")).thenReturn("2024-03-10");
        when(actionMapping.findForward("aprobacionesPendientes")).thenReturn(new ActionForward("aprobacionesPendientes", "/aprobacionesPath", false));  // Mock del forward

        // Mock de CierreService
        try (MockedConstruction<CierreService> mock = mockConstruction(CierreService.class, (mockCierreService, context) -> {
            when(mockCierreService.obtenerAprobacionesPendientes(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                    .thenReturn(Arrays.asList(new CierreTarjeta()));  // Devuelve una lista con un elemento
        })) {
            ActionForward result = aprobacionesPendientesAction.executeAction(actionMapping, actionForm, samWebApplication, samWebClient, httpServletRequest, httpServletResponse);

            assertNotNull(result, "El resultado no debe ser null");  // Verifica que result no sea null
            assertEquals("aprobacionesPendientes", result.getName(), "El nombre del forward debe ser 'aprobacionesPendientes'");  // Verifica que sea el forward correcto
        }
    }
    
    private static Stream<Arguments> filtrarDataProvider() {
        return Stream.of(
            Arguments.of("user1", "100", "500", "USD", "2024-03-10", Collections.emptyList()),
            Arguments.of("user2", "200", "1000", "EUR", "2024-03-11", Arrays.asList(new CierreTarjeta()))
        );
    }
}
