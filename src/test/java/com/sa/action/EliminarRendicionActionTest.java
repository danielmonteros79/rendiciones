package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.FiltrarAprobacionForm;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EliminarRendicionActionTest {
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
    EliminarRendicionAction eliminarRendicionAction;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @ParameterizedTest
//    @MethodSource("executeActionSource")
//    @DisplayName("Testeando execute action")
//    void executeAction(HttpServletRequest request, ActionMapping mapping) throws Exception {
//        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
//            when(mockRendicionesService.bajaRendicion(anyString(),anyString(),anyString())).thenReturn("bajaRendicion");
//            when(mockRendicionesService.getMsg()).thenReturn("msg");
//        })) {
//            ActionForward result = eliminarRendicionAction.executeAction(mapping, actionForm, samWebApplication, samWebClient, request, httpServletResponse);
//            assertAll(
//                    () -> assertNotNull(result),
//                    () -> assertEquals("success",result.getName()),
//                    () -> assertEquals("msg",request.getAttribute("message"))
//            );
//        }
//    }
//
//    @ParameterizedTest
//    @MethodSource("executeActionSource")
//    @DisplayName("Testeando execute action exception")
//    void executeActionException(HttpServletRequest request, ActionMapping mapping) throws Exception {
//
//        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
//            when(mockRendicionesService.bajaRendicion(anyString(),anyString(),anyString())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
//        })) {
//            ActionForward result = eliminarRendicionAction.executeAction(mapping, actionForm, samWebApplication, samWebClient, request, httpServletResponse);
//            assertAll(
//                    () -> assertNotNull(result),
//                    () -> assertEquals("success",result.getName()),
//                    () -> assertEquals("ERROR: TransactionException",request.getAttribute("message"))
//            );
//        }
//    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());

        ActionMapping mapping = new ActionMapping();

        session.setAttribute("usuario", usuario);

        request.setHttpSession(session);
        request.addParameter("codigo","codigo");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));


        return Stream.of(
                Arguments.of(request,mapping)
        );
    }
}