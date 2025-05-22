package com.sa.action;

import com.sa.entities.Usuario;
import com.sa.form.LoginForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.UsuarioService;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.mock.MockHttpServletRequest;
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

class LoginActionTest {
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    LoginAction loginAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeSource")
    @DisplayName("Testeando execute")
    void execute(HttpServletRequest request, ActionMapping mapping, LoginForm loginForm, Usuario usuario) throws Exception {
        try(MockedConstruction<UsuarioService> mock = Mockito.mockConstruction(UsuarioService.class, (mockUsuarioService, context) -> {
            when(mockUsuarioService.obtenerDelegadosUsuario(anyString())).thenReturn(usuario);
        })) {
            try(MockedConstruction<ManagerTransaction> mock2 = mockConstruction(ManagerTransaction.class, (mockManagerTransaction, context) -> {
                doNothing().when(mockManagerTransaction).executeTrx(any(),anyMap());
            })) {
                ActionForward result = loginAction.execute(mapping, loginForm, null, null, request, null);
                if(request.getSession().getAttribute("userWorking") == null) {
                    Assertions.assertEquals("failure", result.getName());
                } else {
                    Assertions.assertEquals("success", result.getName());
                }
            }
        }
    }

    // ------ Sources ------
    private static Stream<Arguments> executeSource() {
        LoginForm loginForm = new LoginForm();
        LoginForm loginForm2 = new LoginForm();
        LoginForm loginForm3 = new LoginForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        ActionMapping mapping = new ActionMapping();
        ActionMapping mapping2 = new ActionMapping();
        Usuario usuario  = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        Usuario usuarioNull = null;

        loginForm.setUsername("username");
        loginForm.setPassword("password");

        loginForm2.setUsername(null);

        loginForm3.setUsername("username");
        loginForm3.setPassword(null);

        mapping.addForwardConfig(new ActionForward("failure", "path1", false));
        mapping2.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
                Arguments.of(request, mapping, loginForm2, usuario),
                Arguments.of(request, mapping, loginForm3, usuario),
                Arguments.of(request, mapping, loginForm, usuarioNull),
                Arguments.of(request, mapping2, loginForm, usuario)
        );
    }

}