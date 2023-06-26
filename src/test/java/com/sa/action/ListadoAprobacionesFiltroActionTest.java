package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.FiltrarAprobacionForm;
import com.sa.services.AprobacionesService;
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

class ListadoAprobacionesFiltroActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    ListadoAprobacionesFiltroAction listadoAprobacionesFiltroAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(MockHttpServletRequest request,FiltrarAprobacionForm form,ActionMapping mapping,List<Rendicion> rendicion,List<ComboMotivo> motivo) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenReturn(motivo);
        })) {
            try(MockedConstruction<AprobacionesService> mock2 = Mockito.mockConstruction(AprobacionesService.class, (mockAprobacionesService, context) -> {
                when(mockAprobacionesService.getAprobacionesPendientes(any(),any(),any(),any(),any())).thenReturn(rendicion);
                when(mockAprobacionesService.getMsg()).thenReturn("msg");
            })) {
                ActionForward result = listadoAprobacionesFiltroAction.executeAction(mapping, form, null, null, request, null);
                assertAll(
                        () -> assertEquals("glg",request.getAttribute("opcionEstado")),
                        () -> assertEquals(rendicion, request.getAttribute("Rendicion")),
                        () -> assertEquals(motivo, request.getAttribute("ComboMotivo")),
                        () -> assertEquals("messageModifTCJP<br>msg", request.getAttribute("messageModifTCJP")),
                        () -> assertEquals("success", result.getName())
                );
            }
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(MockHttpServletRequest request,FiltrarAprobacionForm form,ActionMapping mapping,List<Rendicion> rendicion,List<ComboMotivo> motivo) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {
            try(MockedConstruction<AprobacionesService> mock2 = Mockito.mockConstruction(AprobacionesService.class, (mockAprobacionesService, context) -> {
                when(mockAprobacionesService.getAprobacionesPendientes(any(),any(),any(),any(),any())).thenReturn(rendicion);
                when(mockAprobacionesService.getMsg()).thenReturn("msg");
            })) {
                ActionForward result = listadoAprobacionesFiltroAction.executeAction(mapping, form, null, null, request, null);
                assertAll(
                        () -> assertEquals("glg",request.getAttribute("opcionEstado")),
                        () -> assertEquals(rendicion, request.getAttribute("Rendicion")),
                        () -> assertEquals("success", result.getName()),
                        () -> assertEquals("ERROR:TransactionException", request.getAttribute("messageConsulta")),
                        () -> assertEquals("glg", request.getAttribute("opcionEstado"))
                );
            }
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        FiltrarAprobacionForm form = new FiltrarAprobacionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        List<Rendicion> rendicion = new ArrayList<>();
        List<ComboMotivo> motivo = new ArrayList<>();
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("userWorking", usuario);

        request.setHttpSession(session);
        request.addParameter("glg","glg");
        request.setAttribute("messageModifTCJP","messageModifTCJP");

        form.setIdRendicion("idRendicion");
        form.setMotivo("motivo");
        form.setEstado(null);
        form.setUser("user");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));


        return Stream.of(
                Arguments.of(request,form,mapping,rendicion,motivo)
        );
    }

}

