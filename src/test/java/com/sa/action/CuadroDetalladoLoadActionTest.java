package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.*;
import com.sa.form.CuadroFiltroForm;
import com.sa.form.FiltrarAprobacionForm;
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

class CuadroDetalladoLoadActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    CuadroDetalladoLoadAction cuadroDetalladoLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(MockHttpServletRequest request, CuadroFiltroForm form, ActionMapping mapping,List<ComboOpcion2> estado,List<ComboMotivo> motivo,List<ComboOpcion> opcion) throws Exception {
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getComboOpcion2(anyString(),anyString(),anyString(),anyString())).thenReturn(estado);
            when(mockRendicionesService.getMsg()).thenReturn("msg");
            when(mockRendicionesService.getGlgsUsuario(any(),any())).thenReturn(opcion);
            when(mockRendicionesService.getMotivoRendiciones(any(),any(), any())).thenReturn(motivo);
        })) {
            ActionForward result = cuadroDetalladoLoadAction.executeAction(mapping, form, null, null, request, null);
            assertAll(
                    () -> assertEquals("success", result.getName()),
                    () -> assertEquals(motivo, request.getAttribute("ComboMotivo")),
                    () -> assertEquals(estado, request.getAttribute("ComboEstado")),
                    () -> assertEquals("f", request.getAttribute("Tabla")),
                    () -> assertEquals("msg<br>msg<br>msg", request.getAttribute("message"))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(MockHttpServletRequest request, CuadroFiltroForm form, ActionMapping mapping,List<ComboOpcion2> estado,List<ComboMotivo> motivo,List<ComboOpcion> opcion) throws Exception {
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getComboOpcion2(anyString(),anyString(),anyString(),anyString())).thenReturn(estado);
            when(mockRendicionesService.getMsg()).thenReturn("msg");
            when(mockRendicionesService.getGlgsUsuario(any(),any())).thenReturn(opcion);
            when(mockRendicionesService.getMotivoRendiciones(any(),any(), any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {
            ActionForward result = cuadroDetalladoLoadAction.executeAction(mapping, form, null, null, request, null);
            assertAll(
                    () -> assertEquals("success", result.getName()),
                    () -> assertEquals(motivo, request.getAttribute("ComboMotivo")),
                    () -> assertEquals(estado, request.getAttribute("ComboEstado")),
                    () -> assertEquals("f", request.getAttribute("Tabla")),
                    () -> assertEquals("ERROR: TransactionException", request.getAttribute("message"))
            );
        }
    }

    // ------ Source ------

    private static Stream<Arguments> executeActionSource() {
        CuadroFiltroForm form = new CuadroFiltroForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        List<ComboOpcion2> estado = new ArrayList<>();
        List<ComboMotivo> motivo = new ArrayList<>();
        List<ComboOpcion> opcion = new ArrayList<>();
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);

        request.setHttpSession(session);
        request.addParameter("codEstado","codEstado");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
                Arguments.of(request,form,mapping,estado,motivo,opcion)
        );
    }



}