package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Usuario;
import com.sa.form.AprobacionForm;
import com.sa.services.AprobacionesService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MotivoObservarSaveActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    MotivoObservarSaveAction motivoObservarSaveAction;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request, List<ComboMotivo> motivo, ActionMapping mapping, ActionForm form) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenReturn(motivo);
        })) {
            try(MockedConstruction<AprobacionesService> mock2 = mockConstruction(AprobacionesService.class, (mockAprobacionesService, context) -> {
                when(mockAprobacionesService.cambiarEstadoDeUnaRendicion(any(),any(),any(),any(),any())).thenReturn("");
            })) {
                ActionForward result = motivoObservarSaveAction.executeAction(mapping, form, null, null, request, null);

                assertAll(
                        () -> assertEquals("OK: OBSERVACION DADA DE ALTA CORRECTAMENTE", request.getAttribute("messageModifTCJP")),
                        () -> assertEquals("success",result.getName())
                );
            }
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request, List<ComboMotivo> motivo, ActionMapping mapping, ActionForm form) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenReturn(motivo);
        })) {
            try(MockedConstruction<AprobacionesService> mock2 = mockConstruction(AprobacionesService.class, (mockAprobacionesService, context) -> {
                when(mockAprobacionesService.cambiarEstadoDeUnaRendicion(any(),any(),any(),any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
            })) {
                ActionForward result = motivoObservarSaveAction.executeAction(mapping, form, null, null, request, null);

                assertAll(
                        () -> assertEquals("ERROR AL DAR DE ALTA OBSERVACION: TransactionException", request.getAttribute("messageModifTCJP")),
                        () -> assertEquals("success",result.getName())
                );
            }
        }
    }
    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        AprobacionForm aprobacionForm = new AprobacionForm();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpSession mockHttpSession = new MockHttpSession();
        List<ComboMotivo> motivo = new ArrayList<>();
        ActionMapping mapping = new ActionMapping();
        Usuario usuario = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());

        mockHttpSession.setAttribute("userWorking",usuario);
        mockHttpServletRequest.setHttpSession(mockHttpSession);

        aprobacionForm.setId(1);
        aprobacionForm.setCmboMotivo("comboMotivo");
        aprobacionForm.setMotivoRechazo("motivoRechazo");
        aprobacionForm.setGlg("glg");


        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
                Arguments.of(mockHttpServletRequest,motivo,mapping,aprobacionForm)
        );
    }
}