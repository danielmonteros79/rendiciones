package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;
import com.sa.services.RendicionesService;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.util.TokenProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivarRechazarRendicionActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpSession httpSession;
    @Mock
    ActionMapping actionMapping;
    @Mock
    ActionForm actionForm;
    @Mock
    SAMWebApplication samWebApplication;
    @Mock
    SAMWebClient samWebClient;
    @Mock
    HttpServletResponse httpServletResponse;
    @InjectMocks
    ActivarRechazarRendicionAction activarRechazarRendicionAction;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(Object usuario,String codigo, String estado, String msg,String forwardName, ActionForward resultado,String message) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
            when(httpServletRequest.getSession()).thenReturn(httpSession);
            when(httpSession.getAttribute("usuario")).thenReturn(usuario);
            when(httpServletRequest.getParameter("codigo")).thenReturn(codigo);
            when(httpServletRequest.getParameter("estado")).thenReturn(estado);
            doNothing().when(mockM).activaRechazaRendicion(any(),any(),any());
            when(mockM.getMsg()).thenReturn(msg);
            when(actionMapping.findForward(forwardName)).thenReturn(resultado);
        })) {
            ActionForward result = activarRechazarRendicionAction.executeAction(actionMapping, actionForm, samWebApplication, samWebClient, httpServletRequest, httpServletResponse);
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals(resultado, result)
                    //() -> assertEquals(message,httpServletRequest.getAttribute("message"))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionExceptionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(Object usuario,String codigo, String estado, String msg,String forwardName, ActionForward resultado) throws Exception {
        AtomicReference<ActionForward> result = null;
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
                    when(httpServletRequest.getSession()).thenReturn(httpSession);
            when(httpSession.getAttribute("usuario")).thenReturn(usuario);
            when(httpServletRequest.getParameter("codigo")).thenReturn(codigo);
            when(httpServletRequest.getParameter("estado")).thenReturn(estado);
            doThrow(new TransactionException("error")).when(mockM).activaRechazaRendicion(any(),any(),any());
            when(mockM.getMsg()).thenReturn(msg);
            when(actionMapping.findForward(forwardName)).thenReturn(resultado);
        })) {
            Exception exception = assertThrows(Exception.class, () -> {
                result.set(activarRechazarRendicionAction.executeAction(actionMapping, actionForm, samWebApplication, samWebClient, httpServletRequest, httpServletResponse));
                assertAll(
                        () -> assertEquals(resultado, result)
                       // () -> assertEquals("error", exception.getMessage())
                        //() -> assertEquals("ERROR: error", httpServletRequest.getAttribute("message"))
                );

            });


        }
    }


    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        Usuario usuario = new Usuario( "idUser", "perfil", "nombre", 1, "sector", new ArrayList<>());
        String codigo = "codigo";
        String estado = "estado";
        String msg = "msg";
        String msg2 = null;
        Object usuarioObject = usuario;
        String forwardName = "success";
        ActionForward resultado = new ActionForward(forwardName,"path",true);
        String message = "OK: msg";
        String message2 = null;

        return Stream.of(
                Arguments.of(usuarioObject, codigo, estado, msg, forwardName, resultado,message),
                Arguments.of(usuarioObject, codigo, estado, msg2, forwardName, resultado,message2)
        );
    }

    private static Stream<Arguments> executeActionExceptionSource() {
        Usuario usuario = new Usuario( "idUser", "perfil", "nombre", 1, "sector", new ArrayList<>());
        String codigo = "codigo";
        String estado = "estado";
        String msg = "msg";
        String msg2 = null;
        Object usuarioObject = usuario;
        String forwardName = "error";
        ActionForward resultado = new ActionForward(forwardName,"path",true);

        return Stream.of(
                Arguments.of(usuarioObject, codigo, estado, msg, forwardName, resultado),
                Arguments.of(usuarioObject, codigo, estado, msg2, forwardName, resultado)
        );
    }
}

