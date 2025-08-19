package com.sa.action.cierre;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.services.CierreService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReasignarBandejaActionTest {

    @Mock
    ActionMapping actionMappingMocked;
    @Mock
    ActionForward actionForwardMocked;
    @Mock
    ActionForm actionFormMocked;
    @Mock
    SAMWebClient samWebClientMocked;
    @Mock
    SAMWebApplication samWebApplicationMocked;
    @Mock
    HttpServletRequest httpServletRequestMocked;
    @Mock
    HttpServletResponse httpServletResponseMocked;
    @Mock
    HttpSession httpSessionMocked;
    @Mock
    PrintWriter printWriterMocked;
    
    @InjectMocks
    ReasignarBandejaAction reasignarBandejaAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "sector", new ArrayList<>());
        reasignarBandejaAction.setSessionUserWorking(usuario);
    }

    @Test
    @DisplayName("Should return success when action parameter is null")
    void shouldReturnSuccessWhenActionParameterIsNull() throws Exception {
        // Arrange
        when(httpServletRequestMocked.getParameter("action")).thenReturn(null);
        when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);

        // Act
        ActionForward result = reasignarBandejaAction.executeAction(actionMappingMocked, actionFormMocked, 
            samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

        // Assert
        assertNotNull(result);
        assertEquals(actionForwardMocked, result);
        verify(actionMappingMocked).findForward("success");
    }

    @Test
    @DisplayName("Should return success when action parameter is empty string")
    void shouldReturnSuccessWhenActionParameterIsEmptyString() throws Exception {
        // Arrange
        when(httpServletRequestMocked.getParameter("action")).thenReturn("");
        when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);

        // Act
        ActionForward result = reasignarBandejaAction.executeAction(actionMappingMocked, actionFormMocked, 
            samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

        // Assert
        assertNotNull(result);
        assertEquals(actionForwardMocked, result);
        verify(actionMappingMocked).findForward("success");
    }

    @Test
    @DisplayName("Should return success when action parameter is unknown value")
    void shouldReturnSuccessWhenActionParameterIsUnknownValue() throws Exception {
        // Arrange
        when(httpServletRequestMocked.getParameter("action")).thenReturn("unknown");
        when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);

        // Act
        ActionForward result = reasignarBandejaAction.executeAction(actionMappingMocked, actionFormMocked, 
            samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

        // Assert
        assertNotNull(result);
        assertEquals(actionForwardMocked, result);
        verify(actionMappingMocked).findForward("success");
    }

    @Test
    @DisplayName("Should call reasignar when action parameter is 'reasignarBandeja'")
    void shouldCallReasignarWhenActionParameterIsReasignarBandeja() throws Exception {
        // Arrange
        when(httpServletRequestMocked.getParameter("action")).thenReturn("reasignarBandeja");
        when(httpServletRequestMocked.getParameter("userOrigen")).thenReturn("user1");
        when(httpServletRequestMocked.getParameter("userDestino")).thenReturn("user2");
        when(httpServletRequestMocked.getParameter("tipoBandeja")).thenReturn("APROBACION");
        when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

        try (MockedConstruction<CierreService> cierreServiceMC = Mockito.mockConstruction(CierreService.class,
            (mockCierreService, context) -> {
                when(mockCierreService.reasignarBandeja("user1", "user2", "APROBACION")).thenReturn("Reasignación exitosa");
            })) {

            // Act
            ActionForward result = reasignarBandejaAction.executeAction(actionMappingMocked, actionFormMocked, 
                samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

            // Assert
            assertNull(result); // writeJson returns null
            verify(httpServletResponseMocked).setContentType("application/json");
            verify(httpServletResponseMocked).setCharacterEncoding("UTF-8");
            verify(printWriterMocked).print(anyString());
        }
    }

    @Test
    @DisplayName("Should handle null parameters in reasignar")
    void shouldHandleNullParametersInReasignar() throws Exception {
        // Arrange
        when(httpServletRequestMocked.getParameter("action")).thenReturn("reasignarBandeja");
        when(httpServletRequestMocked.getParameter("userOrigen")).thenReturn(null);
        when(httpServletRequestMocked.getParameter("userDestino")).thenReturn(null);
        when(httpServletRequestMocked.getParameter("tipoBandeja")).thenReturn(null);
        when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

        try (MockedConstruction<CierreService> cierreServiceMC = Mockito.mockConstruction(CierreService.class,
            (mockCierreService, context) -> {
                when(mockCierreService.reasignarBandeja(null, null, null)).thenReturn("Operación completada");
            })) {

            // Act
            ActionForward result = reasignarBandejaAction.executeAction(actionMappingMocked, actionFormMocked, 
                samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

            // Assert
            assertNull(result); // writeJson returns null
            verify(httpServletResponseMocked).setContentType("application/json");
            verify(httpServletResponseMocked).setCharacterEncoding("UTF-8");
        }
    }

    @Test
    @DisplayName("Should handle exception in reasignar method")
    void shouldHandleExceptionInReasignarMethod() throws Exception {
        // Arrange
        when(httpServletRequestMocked.getParameter("action")).thenReturn("reasignarBandeja");
        when(httpServletRequestMocked.getParameter("userOrigen")).thenReturn("user1");
        when(httpServletRequestMocked.getParameter("userDestino")).thenReturn("user2");
        when(httpServletRequestMocked.getParameter("tipoBandeja")).thenReturn("APROBACION");
        when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

        try (MockedConstruction<CierreService> cierreServiceMC = Mockito.mockConstruction(CierreService.class,
            (mockCierreService, context) -> {
                when(mockCierreService.reasignarBandeja("user1", "user2", "APROBACION"))
                    .thenThrow(new RuntimeException("Service error"));
            })) {

            // Act
            ActionForward result = reasignarBandejaAction.executeAction(actionMappingMocked, actionFormMocked, 
                samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

            // Assert
            assertNull(result); // writeJson returns null (error response)
            verify(httpServletResponseMocked).setContentType("application/json");
            verify(httpServletResponseMocked).setCharacterEncoding("UTF-8");
            verify(printWriterMocked).print(ArgumentMatchers.<String>argThat(arg -> arg.contains("error")));
        }
    }

    @Test
    @DisplayName("Should handle exception with cause in executeAction")
    void shouldHandleExceptionWithCauseInExecuteAction() throws Exception {
        // Arrange
        RuntimeException cause = new RuntimeException("Database connection failed");
        Exception mainException = new Exception("Service unavailable", cause);
        
        when(httpServletRequestMocked.getParameter("action")).thenReturn("reasignarBandeja");
        when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

        try (MockedConstruction<CierreService> cierreServiceMC = Mockito.mockConstruction(CierreService.class,
            (mockCierreService, context) -> {
                when(mockCierreService.reasignarBandeja(anyString(), anyString(), anyString())).thenThrow(mainException);
            })) {

            // Act
            ActionForward result = reasignarBandejaAction.executeAction(actionMappingMocked, actionFormMocked, 
                samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

            // Assert
            assertNull(result);
            verify(printWriterMocked).print(ArgumentMatchers.<String>argThat(arg -> 
                arg.contains("\"error\"") && arg.contains("java.lang.Exception: Service unavailable") && arg.contains("\"status\":\"OK\"")));
        }
    }

    @Test
    @DisplayName("Should process error message with colon and remove error codes")
    void shouldProcessErrorMessageWithColonAndRemoveErrorCodes() throws Exception {
        // Arrange
        RuntimeException exception = new RuntimeException("Error: Connection timeout ABC1234");
        
        when(httpServletRequestMocked.getParameter("action")).thenReturn("reasignarBandeja");
        when(httpServletRequestMocked.getParameter("userOrigen")).thenReturn("user1");
        when(httpServletRequestMocked.getParameter("userDestino")).thenReturn("user2");  
        when(httpServletRequestMocked.getParameter("tipoBandeja")).thenReturn("APROBACION");
        when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

        try (MockedConstruction<CierreService> cierreServiceMC = Mockito.mockConstruction(CierreService.class,
            (mockCierreService, context) -> {
                when(mockCierreService.reasignarBandeja(anyString(), anyString(), anyString())).thenThrow(exception);
            })) {

            // Act
            ActionForward result = reasignarBandejaAction.executeAction(actionMappingMocked, actionFormMocked, 
                samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

            // Assert
            assertNull(result);
            // Should contain "Connection timeout" (after processing) in JSON format
            verify(printWriterMocked).print(ArgumentMatchers.<String>argThat(arg -> 
                arg.contains("\"error\"") && arg.contains("Connection timeout") && arg.contains("\"status\":\"OK\"")));
            // Should not contain the error code "ABC1234"
            verify(printWriterMocked).print(ArgumentMatchers.<String>argThat(arg -> !arg.contains("ABC1234")));
        }
    }

    @Test
    @DisplayName("Should handle exception without cause and no colon in message")
    void shouldHandleExceptionWithoutCauseAndNoColonInMessage() throws Exception {
        // Arrange
        RuntimeException exception = new RuntimeException("Simple error message");
        
        when(httpServletRequestMocked.getParameter("action")).thenReturn("reasignarBandeja");
        when(httpServletRequestMocked.getParameter("userOrigen")).thenReturn("user1");
        when(httpServletRequestMocked.getParameter("userDestino")).thenReturn("user2");
        when(httpServletRequestMocked.getParameter("tipoBandeja")).thenReturn("APROBACION");
        when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

        try (MockedConstruction<CierreService> cierreServiceMC = Mockito.mockConstruction(CierreService.class,
            (mockCierreService, context) -> {
                when(mockCierreService.reasignarBandeja(anyString(), anyString(), anyString())).thenThrow(exception);
            })) {

            // Act
            ActionForward result = reasignarBandejaAction.executeAction(actionMappingMocked, actionFormMocked, 
                samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

            // Assert
            assertNull(result);
            verify(printWriterMocked).print(ArgumentMatchers.<String>argThat(arg -> 
                arg.contains("\"error\"") && arg.contains("Simple error message") && arg.contains("\"status\":\"OK\"")));
        }
    }
}
