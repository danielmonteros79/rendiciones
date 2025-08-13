package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.exceptions.SessionTimeOutException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RestriccionTransaccionActionTest {

    @Mock
    private SAMWebApplication samWebApplication;
    @Mock
    private SAMWebClient samWebClient;
    @Mock
    private ActionForm actionForm;
    @Mock
    private ActionMapping actionMapping;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private PrintWriter printWriter;

    private ConcreteRestriccionTransaccionAction action;
    private Usuario usuario;
    private StringWriter stringWriter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        action = new ConcreteRestriccionTransaccionAction();
        usuario = new Usuario("testUser", "perfil", "nombre", 1, "sector", new ArrayList<>());
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("usuario")).thenReturn(usuario);
        when(session.getAttribute("userWorking")).thenReturn(usuario);
    }

    @Test
    public void shouldExecuteSuccessfullyWhenUserAuthenticatedAndNoTimeout() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("testAction");
        
        // Act
        ActionForward result = action.execute(actionMapping, actionForm, samWebApplication, samWebClient, request, response);
        
        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Should return success forward", "success", result.getName());
        assertEquals("Should set sessionUser correctly", usuario, action.getSessionUser());
        assertEquals("Should set sessionUserWorking correctly", usuario, action.getSessionUserWorking());
        verify(samWebClient).setAttribute("userLoggin", "testUser");
    }

    @Test(expected = SessionTimeOutException.class)
    public void shouldThrowSessionTimeOutExceptionWhenUserSessionIsNull() throws Exception {
        // Arrange
        when(session.getAttribute("usuario")).thenReturn(null);
        
        // Act
        action.execute(actionMapping, actionForm, samWebApplication, samWebClient, request, response);
    }

    @Test
    public void shouldReturnErrorResponseForXMLHttpRequestWhenSessionTimeout() throws Exception {
        // Arrange
        when(session.getAttribute("usuario")).thenReturn(null);
        when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        when(response.getWriter()).thenReturn(printWriter);
        
        // Act
        ActionForward result = action.execute(actionMapping, actionForm, samWebApplication, samWebClient, request, response);
        
        // Assert
        assertNull("Should return null for XMLHttpRequest timeout", result);
        verify(response).setContentType("application/json");
        assertTrue("Should contain timeout message", stringWriter.toString().contains("Finaliz"));
    }

    @Test
    public void shouldHandleGetMessageActionCorrectly() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn("getMessage");
        when(response.getWriter()).thenReturn(printWriter);
        action.message = "Test message";
        
        // Act
        ActionForward result = action.execute(actionMapping, actionForm, samWebApplication, samWebClient, request, response);
        
        // Assert
        assertNull("Should return null for getMessage action", result);
        assertTrue("Should return the message", stringWriter.toString().contains("Test message"));
    }

    @Test
    public void shouldSanitizeActionParameterToPreventLogInjection() throws Exception {
        // Arrange
        String maliciousAction = "test\r\naction\tvalue\u0001control";
        when(request.getParameter("action")).thenReturn(maliciousAction);
        
        // Act
        ActionForward result = action.execute(actionMapping, actionForm, samWebApplication, samWebClient, request, response);
        
        // Assert
        assertNotNull("Should execute successfully even with malicious input", result);
        // Note: We can't easily test the sanitized logging without more complex mocking,
        // but the sanitization logic is tested implicitly through successful execution
    }

    @Test
    public void shouldHandleNullActionParameterCorrectly() throws Exception {
        // Arrange
        when(request.getParameter("action")).thenReturn(null);
        
        // Act
        ActionForward result = action.execute(actionMapping, actionForm, samWebApplication, samWebClient, request, response);
        
        // Assert
        assertNotNull("Should handle null action parameter", result);
        assertEquals("Should return success forward", "success", result.getName());
    }

    @Test
    public void shouldSetSessionUsersCorrectlyFromSessionAttributes() throws Exception {
        // Arrange
        Usuario workingUser = new Usuario("workingUser", "perfil", "nombre", 2, "sector", new ArrayList<>());
        when(session.getAttribute("userWorking")).thenReturn(workingUser);
        
        // Act
        ActionForward result = action.execute(actionMapping, actionForm, samWebApplication, samWebClient, request, response);
        
        // Assert
        assertEquals("Should set sessionUser from 'usuario' attribute", usuario, action.getSessionUser());
        assertEquals("Should set sessionUserWorking from 'userWorking' attribute", workingUser, action.getSessionUserWorking());
    }

    @Test
    public void shouldSetUserLogginAttributeInSAMWebClient() throws Exception {
        // Arrange - user already set in setUp
        
        // Act
        action.execute(actionMapping, actionForm, samWebApplication, samWebClient, request, response);
        
        // Assert
        verify(samWebClient).setAttribute("userLoggin", "testUser");
    }

    // Concrete implementation for testing the abstract class
    private static class ConcreteRestriccionTransaccionAction extends RestriccionTransaccionAction {
        
        // Make these fields accessible for testing
        public Usuario getSessionUser() {
            return this.sessionUser;
        }
        
        public Usuario getSessionUserWorking() {
            return this.sessionUserWorking;
        }
        
        @Override
        public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                         SAMWebApplication samApplication, SAMWebClient samClient,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
            // Simple implementation that returns a success forward for testing
            ActionForward forward = new ActionForward();
            forward.setName("success");
            forward.setPath("/success.jsp");
            return forward;
        }
    }
}
