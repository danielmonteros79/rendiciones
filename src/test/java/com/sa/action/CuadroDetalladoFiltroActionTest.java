package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.*;
import com.sa.form.CuadroFiltroForm;
import com.sa.services.RendicionesService;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockHttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuadroDetalladoFiltroActionTest {
    
    @Mock
    private Log log;
    
    @Mock
    private SAMWebApplication samWebApplication;
    
    @Mock
    private SAMWebClient samWebClient;
    
    @InjectMocks
    private CuadroDetalladoFiltroAction cuadroDetalladoFiltroAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Test executeAction - successful execution")
    void executeActionSuccess(HttpServletRequest request, HttpServletResponse response, 
                             CuadroFiltroForm form, ActionMapping mapping, 
                             List<ComboOpcion2> estado, List<CuadroDetallado> rendicion) throws Exception {
        
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = 
                Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getComboOpcion2(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(estado);
            when(mockRendicionesService.getMsg()).thenReturn("Test message");
            when(mockRendicionesService.getCuadroDetallado(anyString(), anyString(), anyString(), 
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(rendicion);
        })) {

            ActionForward result = cuadroDetalladoFiltroAction.executeAction(
                mapping, form, samWebApplication, samWebClient, request, response);
            
            assertAll("Verify successful execution",
                    () -> assertNotNull(result, "ActionForward should not be null"),
                    () -> assertEquals("ok", result.getName(), "Forward name should be 'ok'"),
                    () -> assertEquals(estado, request.getAttribute("ComboEstado"), "ComboEstado should match"),
                    () -> assertEquals(rendicion, request.getAttribute("CuadroDetallado"), "CuadroDetallado should match"),
                    () -> assertEquals("Test message<br>Test message", request.getAttribute("message"), "Message should be concatenated"),
                    () -> assertEquals("t", request.getAttribute("Tabla"), "Tabla attribute should be 't'"),
                    () -> assertEquals(form.getComboGlg(), request.getAttribute("ComboGlg"), "ComboGlg should match"),
                    () -> assertEquals(form.getComboMotivo(), request.getAttribute("ComboMotivo"), "ComboMotivo should match")
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Test executeAction - exception handling")
    void executeActionException(HttpServletRequest request, HttpServletResponse response, 
                               CuadroFiltroForm form, ActionMapping mapping,
                               List<ComboOpcion2> estado, List<CuadroDetallado> rendicion) throws Exception {
        
        TransactionException testException = new TransactionException("Test transaction error", 
            new RuntimeException("Root cause error"));
        
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = 
                Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getComboOpcion2(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(estado);
            when(mockRendicionesService.getMsg()).thenReturn("Test message");
            when(mockRendicionesService.getCuadroDetallado(anyString(), anyString(), anyString(), 
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenThrow(testException);
        })) {

            ActionForward result = cuadroDetalladoFiltroAction.executeAction(
                mapping, form, samWebApplication, samWebClient, request, response);
            
            assertAll("Verify exception handling",
                    () -> assertNotNull(result, "ActionForward should not be null"),
                    () -> assertEquals("ok", result.getName(), "Forward name should be 'ok' even with exception"),
                    () -> assertEquals(estado, request.getAttribute("ComboEstado"), "ComboEstado should still be set"),
                    () -> assertEquals(rendicion, request.getAttribute("CuadroDetallado"), "CuadroDetallado should be empty list"),
                    () -> assertTrue(((String) request.getAttribute("message")).startsWith("ERROR: "), 
                        "Message should start with 'ERROR: '"),
                    () -> assertEquals(form.getComboGlg(), request.getAttribute("ComboGlg"), "ComboGlg should match"),
                    () -> assertEquals(form.getComboMotivo(), request.getAttribute("ComboMotivo"), "ComboMotivo should match")
            );
        }
    }

    @Test
    @DisplayName("Test executeAction - null form handling")
    void executeActionNullForm() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("ok", "test-path", false));
        
        // Test should handle null form gracefully or throw appropriate exception
        assertThrows(Exception.class, () -> {
            cuadroDetalladoFiltroAction.executeAction(mapping, null, samWebApplication, samWebClient, request, response);
        }, "Should throw exception for null form");
    }

    @Test
    @DisplayName("Test executeAction - null user in session")
    void executeActionNullUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        CuadroFiltroForm form = createTestForm();
        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("ok", "test-path", false));
        
        // Set session without user
        request.setSession(session);
        
        assertThrows(Exception.class, () -> {
            cuadroDetalladoFiltroAction.executeAction(mapping, form, samWebApplication, samWebClient, request, response);
        }, "Should throw exception when user is not in session");
    }

    // ------ Helper Methods ------

    private CuadroFiltroForm createTestForm() {
        CuadroFiltroForm form = new CuadroFiltroForm();
        form.setComboGlg(new ArrayList<>());
        form.setComboMotivo(new ArrayList<>());
        form.setFechaDesde("01/01/2023");
        form.setFechaHasta("31/12/2023");
        form.setOpcion("test-option");
        form.setMontoDesde("100");
        form.setMontoHasta("1000");
        form.setCodEstado("ACTIVE");
        form.setCodMotivo("TEST");
        form.setCodGlg("GLG001");
        form.setUsuario("testuser");
        return form;
    }

    // ------ Data Sources ------

    private static Stream<Arguments> executeActionSource() {
        CuadroFiltroForm form = new CuadroFiltroForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpSession session = new MockHttpSession();
        
        // Create a proper Usuario object
        Usuario usuario = new Usuario("testuser", "admin", "Test User", 1, "IT", new ArrayList<>());
        
        // Setup test data
        List<ComboOpcion2> estado = new ArrayList<>();
        estado.add(new ComboOpcion2("1", "Active"));
        estado.add(new ComboOpcion2("2", "Inactive"));
        
        List<CuadroDetallado> rendicion = new ArrayList<>();
        CuadroDetallado detalle = new CuadroDetallado();
        rendicion.add(detalle);
        
        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("ok", "/success.jsp", false));

        // Setup session and request
        session.setAttribute("usuario", usuario);
        request.setSession(session);

        // Setup form with valid test data
        form.setComboGlg(new ArrayList<>());
        form.setComboMotivo(new ArrayList<>());
        form.setFechaDesde("01/01/2023");
        form.setFechaHasta("31/12/2023");
        form.setOpcion("test-option");
        form.setMontoDesde("100");
        form.setMontoHasta("1000");
        form.setCodEstado("ACTIVE");
        form.setCodMotivo("TEST");
        form.setCodGlg("GLG001");
        form.setUsuario("testuser");

        return Stream.of(
                Arguments.of(request, response, form, mapping, estado, rendicion)
        );
    }
}

