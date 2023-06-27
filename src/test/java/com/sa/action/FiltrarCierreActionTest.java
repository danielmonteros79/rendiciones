package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.CierreFiltroForm;
import com.sa.services.CierreService;
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

class FiltrarCierreActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    FiltrarCierreAction filtrarCierreAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(MockHttpServletRequest request,CierreFiltroForm renForm,ActionMapping mapping ,List<Rendicion> rendiciones,List<ComboMotivo> motivo) throws Exception {
        try(MockedConstruction<CierreService> mock = Mockito.mockConstruction(CierreService.class, (mockCierreService, context) -> {
            when(mockCierreService.getMsg()).thenReturn("msg");
            when(mockCierreService.getDatosRendicion(any(),any(),any(),any(),any(),any())).thenReturn(rendiciones);
        })) {
            try(MockedConstruction<RendicionesService> mock2 = mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
                when(mockRendicionesService.getMsg()).thenReturn("msg");
                when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenReturn(motivo);
            })) {
                ActionForward result = filtrarCierreAction.executeAction(mapping, renForm, null, null, request, null);
                assertAll(
                        () -> assertEquals("success", result.getName()),
                        () -> assertEquals("msg<br>msg",request.getAttribute("message")),
                        () -> assertEquals(rendiciones,request.getAttribute("rendiciones")),
                        () -> assertEquals(motivo,request.getAttribute("comboMotivo"))
                );
            }
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(MockHttpServletRequest request,CierreFiltroForm renForm,ActionMapping mapping ,List<Rendicion> rendiciones,List<ComboMotivo> motivo) throws Exception {
        try(MockedConstruction<CierreService> mock = Mockito.mockConstruction(CierreService.class, (mockCierreService, context) -> {
            when(mockCierreService.getMsg()).thenReturn("msg");
            when(mockCierreService.getDatosRendicion(any(),any(),any(),any(),any(),any())).thenReturn(rendiciones);
        })) {
            try(MockedConstruction<RendicionesService> mock2 = mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
                when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
            })) {
                ActionForward result = filtrarCierreAction.executeAction(mapping, renForm, null, null, request, null);
                assertAll(
                        () -> assertEquals("success", result.getName()),
                        () -> assertEquals("ERROR: TransactionException",request.getAttribute("message")),
                        () -> assertEquals(rendiciones,request.getAttribute("rendiciones")),
                        () -> assertEquals(motivo,request.getAttribute("comboMotivo"))
                );
            }
        }
    }
    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        CierreFiltroForm renForm = new CierreFiltroForm();
        List<Rendicion> rendiciones = new ArrayList<Rendicion>();
        List<ComboMotivo> motivo = new ArrayList<ComboMotivo>();
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);
        request.setHttpSession(session);

        renForm.setIdRendicion("idRendicion");
        renForm.setMotivo("motivo");
        renForm.setUser("user");
        renForm.setFechaDesde("fechaDesde");
        renForm.setFechaHasta("fechaHasta");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
                Arguments.of(request, renForm,mapping, rendiciones, motivo)
        );
    }
}