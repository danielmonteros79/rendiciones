package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.AprobacionForm;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListadoAprobacionesSaveActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    ListadoAprobacionesSaveAction listadoAprobacionesSaveAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request,AprobacionForm form,ActionMapping mapping,String aviso, List<ComboMotivo> motivo,String forwardName) throws Exception {
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenReturn(motivo);
        })) {
            try (MockedConstruction<AprobacionesService> aprobacionesServiceMC = Mockito.mockConstruction(AprobacionesService.class, (mockAprobacionesService, context) -> {
                when(mockAprobacionesService.cambiarEstadoRendiciones(any(),any(),any(),any(),any())).thenReturn(aviso);
                when(mockAprobacionesService.cambiarEstadoDeUnaRendicion(any(),any(),any(),any(),any())).thenReturn(aviso);
            })) {
                ActionForward result = listadoAprobacionesSaveAction.executeAction(mapping,form, null, null, request, null);
                assertAll(
                        () -> assertEquals(motivo,request.getAttribute("ComboMotivo")),
                        () -> assertEquals("ok",request.getAttribute("trxOk")),
                        () -> assertEquals(forwardName,result.getName()),
                        () -> assertNotNull(request.getAttribute("messageModifTCJP"))
                );
            }
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request,AprobacionForm form,ActionMapping mapping,String aviso, List<ComboMotivo> motivo,String forwardName) throws Exception {
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenReturn(motivo);
        })) {
            try (MockedConstruction<AprobacionesService> aprobacionesServiceMC = Mockito.mockConstruction(AprobacionesService.class, (mockAprobacionesService, context) -> {
                when(mockAprobacionesService.cambiarEstadoRendiciones(any(),any(),any(),any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
                when(mockAprobacionesService.cambiarEstadoDeUnaRendicion(any(),any(),any(),any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
            })) {
                ActionForward result = listadoAprobacionesSaveAction.executeAction(mapping,form, null, null, request, null);
                assertAll(
                        () -> assertEquals(motivo,request.getAttribute("ComboMotivo")),
                        () -> assertEquals("ok",request.getAttribute("trxOk")),
                        () -> assertEquals(forwardName,result.getName()),
                        () -> assertNotNull(request.getAttribute("messageModifTCJP"))
                );
            }
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        AprobacionForm form = new AprobacionForm();
        AprobacionForm form2 = new AprobacionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        MockHttpServletRequest request3 = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        Usuario usuario2 = new Usuario("id2","perfil", "nombre", 1, "sector", new ArrayList<>());
        ActionMapping mapping = new ActionMapping();
        String aviso = "aviso";
        String avisoNull = null;
        List<ComboMotivo> motivo = new ArrayList<>();
        String forwardName = "success";
        String forwardName2 = "aprobacionPopUp";
        String forwardName3 = "rechazoPopUp";

        usuario2.setManejaFacultades(true);

        session.setAttribute("usuario", usuario);
        session.setAttribute("userWorking", usuario2);

        request.setHttpSession(session);
        request.addParameter("idRendicion","1");
        request.addParameter("seleccionado","true");

        request2.setHttpSession(session);
        request2.addParameter("seleccionado","false");

        request3.setHttpSession(session);
        request3.addParameter("codigo","1");

        form.setGlg("glg");
        form.setId(1);
        form.setEstado("1");

        form2.setGlg("glg");
        form2.setId(1);
        form2.setEstado("2");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));
        mapping.addForwardConfig(new ActionForward("aprobacionPopUp", "path2", false));
        mapping.addForwardConfig(new ActionForward("rechazoPopUp", "path3", false));


        return Stream.of(
                Arguments.of(request,form,mapping,aviso,motivo,forwardName),
                Arguments.of(request,form,mapping,avisoNull,motivo,forwardName),
                Arguments.of(request2,form,mapping,aviso,motivo,forwardName),
                Arguments.of(request2,form,mapping,avisoNull,motivo,forwardName),
                Arguments.of(request3,form,mapping,aviso,motivo,forwardName2),
                Arguments.of(request3,form,mapping,avisoNull,motivo,forwardName2),
                Arguments.of(request3,form2,mapping,aviso,motivo,forwardName3),
                Arguments.of(request3,form2,mapping,avisoNull,motivo,forwardName3)
        );
    }
}