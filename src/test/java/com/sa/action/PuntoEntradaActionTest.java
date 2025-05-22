package com.sa.action;

import com.sa.entities.Usuario;
import com.sa.form.LoginForm;
import com.sa.services.UsuarioService;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
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
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class PuntoEntradaActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    PuntoEntradaAction puntoEntradaAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(ActionForm loginForm, HttpServletRequest request, Usuario usuario, ActionMapping mapping) throws Exception {
        try(MockedConstruction<UsuarioService> mock = Mockito.mockConstruction(UsuarioService.class, (mockM, context) -> {
            when(mockM.obtenerDelegadosUsuario(any())).thenReturn(usuario);
        })) {
            ActionForward result = puntoEntradaAction.executeAction(mapping, loginForm, null, null, request, null);

            if(request.getSession().getAttribute("userWorking") == null) {
                Assertions.assertEquals("failure", result.getName());
            } else {
                Assertions.assertEquals("success", result.getName());
            }
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        LoginForm loginForm = new LoginForm();
        LoginForm loginForm2 = new LoginForm();
        LoginForm loginForm3 = new LoginForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ActionMapping mapping = new ActionMapping();
        ActionMapping mapping2 = new ActionMapping();
        Usuario usuario = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        Usuario usuarioNull = null;
        MockHttpSession session = new MockHttpSession();

        request.setHttpSession(session);

        loginForm.setUsername("username");
        loginForm.setPassword("password");

        loginForm2.setUsername(null);

        loginForm3.setUsername("username");
        loginForm3.setPassword(null);

        mapping.addForwardConfig(new ActionForward("failure", "path1", false));
        mapping2.addForwardConfig(new ActionForward("success", "path2", false));

        return Stream.of(
            Arguments.of(loginForm2, request, usuario,mapping),
            Arguments.of(loginForm, request, usuarioNull,mapping),
            Arguments.of(loginForm3, request, usuario,mapping),
            Arguments.of(loginForm, request, usuario,mapping2)
        );
    }


}

