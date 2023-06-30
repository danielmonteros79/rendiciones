package com.sa.action;

import com.sa.entities.Usuario;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.util.TokenProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LogoutActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    HttpSession session;
    @InjectMocks
    LogoutAction logoutAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(ActionMapping mapping, MockHttpServletRequest request,Usuario usuario) throws Exception {
        request.setHttpSession(session);
        when(session.getAttribute("usuario")).thenReturn(usuario);
        doNothing().when(session).invalidate();

        ActionForward result = logoutAction.executeAction(mapping, null, null, null, request, null);
        assertEquals("success", result.getName());
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Usuario usuario = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        ActionMapping mapping = new ActionMapping();

        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return
            Stream.of(
                Arguments.of(mapping, request,usuario)
            );
    }


}

