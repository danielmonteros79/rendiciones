package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.services.CierreService;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CierreMensualSimuladoActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
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
    @InjectMocks
    CierreMensualSimuladoAction cierreMensualSimuladoAction;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(Usuario user, String res, ActionForward ret) throws Exception {

        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("usuario")).thenReturn(user);
        when(actionMapping.findForward("success")).thenReturn(ret);

        try(MockedConstruction<CierreService> mock = Mockito.mockConstruction(CierreService.class, (mockCierreService, context) -> {
            when(mockCierreService.GenerarPagoMarca(anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString(),anyString())).thenReturn(res);
        })) {
            ActionForward result = cierreMensualSimuladoAction.executeAction(actionMapping, actionForm, samWebApplication, samWebClient, httpServletRequest, httpServletResponse);
            assertAll(
                    () -> assertNotNull(result),
                    () -> Assertions.assertEquals(ret, result)
            );
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        Usuario user = new Usuario("idUser", "perfil", "nombre", 1, "sector",new ArrayList<>());
        String ret = "generarPagoMarca";
        ActionForward res = new ActionForward("success","path",true);
        return Stream.of(
                Arguments.of(user, ret,res)
        );
    }
}

