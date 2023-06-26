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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FiltroRendicionesActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    FiltroRendicionesAction filtroRendicionesAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request, FiltroRendicionForm form, ActionMapping mapping, List<Rendicion> rendicion) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
            when(mockM.obtenerListadoRendiciones(any(),any(),any(),any(),any())).thenReturn(rendicion);
            when(mockM.getMsg()).thenReturn("msg");
        })) {
            ActionForward result = filtroRendicionesAction.executeAction(mapping, form, null, null, request, null);
            assertAll(
                    () -> assertEquals("ok",result.getName()),
                    () -> assertEquals("msg",request.getAttribute("message")),
                    () -> assertEquals(rendicion,request.getAttribute("Rendicion"))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request, FiltroRendicionForm form, ActionMapping mapping, List<Rendicion> rendicion) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
            when(mockM.obtenerListadoRendiciones(any(),any(),any(),any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {
            ActionForward result = filtroRendicionesAction.executeAction(mapping, form, null, null, request, null);
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
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        FiltroRendicionForm formFiltro = new FiltroRendicionForm();
        List<Rendicion> rendicion = new ArrayList<>();
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("usuario", usuario);
        session.setAttribute("userWorking", usuario);
        request.setHttpSession(session);

        formFiltro.setFechaDesde("01/01/2023");
        formFiltro.setFechaHasta("01/01/2023");

        mapping.addForwardConfig(new ActionForward("ok", "path1", false));

        return Stream.of(
                Arguments.of(request, formFiltro,mapping, rendicion)
        );
    }

}