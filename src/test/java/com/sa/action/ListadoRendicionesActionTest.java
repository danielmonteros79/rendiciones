package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.FiltroRendicionForm;
import com.sa.services.RendicionesService;
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
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ListadoRendicionesActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    ListadoRendicionesAction listadoRendicionesAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(MockHttpServletRequest request,FiltroRendicionForm form,ActionMapping mapping,List<Rendicion> rendicion) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
            when(mockM.obtenerListadoRendiciones(any(),any(),any(),any(),any())).thenReturn(rendicion);
            when(mockM.getMsg()).thenReturn("message");
        })) {
            ActionForward result = listadoRendicionesAction.executeAction(mapping, form, null, null, request, null);
            assertAll(
                    () -> assertEquals("ok",result.getName()),
                    () -> assertEquals("message",request.getAttribute("message")),
                    () -> assertEquals(rendicion,request.getAttribute("Rendicion"))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(MockHttpServletRequest request,FiltroRendicionForm form,ActionMapping mapping,List<Rendicion> rendicion) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
            when(mockM.obtenerListadoRendiciones(any(),any(),any(),any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
            when(mockM.getMsg()).thenReturn("message");
        })) {
            ActionForward result = listadoRendicionesAction.executeAction(mapping, form, null, null, request, null);
            assertAll(
                    () -> assertEquals("ok",result.getName()),
                    () -> assertEquals("ERROR: TransactionException",request.getAttribute("message")),
                    () -> assertEquals(rendicion,request.getAttribute("Rendicion"))
            );
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        FiltroRendicionForm form = new FiltroRendicionForm();
        ActionMapping mapping = new ActionMapping();
        List<Rendicion> rendicion = new ArrayList<Rendicion>();

        session.setAttribute("usuario", usuario);
        session.setAttribute("userWorking", usuario);
        request.setAttribute("message", null);
        session.setAttribute("msg", null);
        request.setHttpSession(session);

        mapping.addForwardConfig(new ActionForward("ok", "path1", false));

        return Stream.of(
                Arguments.of(request,form,mapping,rendicion)
        );
    }

}