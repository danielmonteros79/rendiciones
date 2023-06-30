package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RendicionSaveActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    SAMWebApplication samApplication;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpServletRequest request;
    @Mock
    SAMWebClient samWebClient;
    @Mock
    ActionForm form;
    @InjectMocks
    RendicionSaveAction rendicionSaveAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(MockHttpServletRequest request,RendicionForm form,String idRend, ActionMapping mapping) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
            when(mockM.altaRendicion(any(), any(), any(), any(), any(), any())).thenReturn(idRend);

        })) {
            ActionForward result = rendicionSaveAction.executeAction(mapping, form, samApplication,samWebClient, request, response);
            assertAll(
                    () -> assertEquals(request.getAttribute("codigo"),idRend)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionTransactionExceptionSource")
    @DisplayName("Testeando execute action transaction exception")
    void executeActionTransactionException(MockHttpServletRequest request, RendicionForm form, String idRend, ActionMapping mapping, List list) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
            when(mockM.altaRendicion(any(), any(), any(), any(), any(), any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
            when(mockM.getMotivoRendiciones(any(), any())).thenReturn(list);

        })) {
            ActionForward result = rendicionSaveAction.executeAction(mapping, form, samApplication,samWebClient, request, response);

            assertAll(
                    () -> assertEquals("TransactionException", request.getAttribute("messageModifTCJP")),
                    () -> assertNotNull(request.getAttribute("ComboMotivo"))
            );
        }
    }


    @ParameterizedTest
    @MethodSource("mostrarDetalleGastosSource")
    @DisplayName("Testeando mostrar detalles gastos")
    void mostrarDetalleGastos(ActionMapping mapping) {
        ActionForward result = rendicionSaveAction.mostrarDetalleGastos(mapping,form,request,response);
        assertNotNull(result);
    }

    // ------ Sources ------
    private static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        MockHttpSession session = new MockHttpSession();

        Usuario user = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        session.setAttribute("usuario", user);

        request.setHttpSession(session);
        request.addParameter("tipoSubmit", "1");

        MockHttpServletRequest request2 = new MockHttpServletRequest();
        request2.setHttpSession(session);
        request2.addParameter("tipoSubmit", "2");

        RendicionForm form = new RendicionForm();
        form.setFechaDesde("01/01/2000");
        form.setFechaHasta("01/01/2000");

        String idRend = "";
        String idRend2 = "idRend";

        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("success", "path1", false));
        mapping.addForwardConfig(new ActionForward("detalleGastos", "path2", false));
        mapping.addForwardConfig(new ActionForward("failure", "path3", false));

        return Stream.of(
                Arguments.of(request, form, idRend, mapping),
                Arguments.of(request2, form, idRend2, mapping),
                Arguments.of(request, form, idRend2, mapping)
        );
    }

    private static Stream<Arguments> executeActionTransactionExceptionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        MockHttpSession session = new MockHttpSession();

        Usuario user = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        session.setAttribute("usuario", user);

        request.setHttpSession(session);
        request.addParameter("tipoSubmit", "1");

        MockHttpServletRequest request2 = new MockHttpServletRequest();
        request2.setHttpSession(session);
        request2.addParameter("tipoSubmit", "2");

        RendicionForm form = new RendicionForm();
        form.setFechaDesde("01/01/2000");
        form.setFechaHasta("01/01/2000");

        String idRend = "";
        String idRend2 = "idRend";

        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("success", "path1", false));
        mapping.addForwardConfig(new ActionForward("detalleGastos", "path2", false));
        mapping.addForwardConfig(new ActionForward("failure", "path3", false));

        List<ComboMotivo> motivoList = new ArrayList<>();

        return Stream.of(
                Arguments.of(request, form, idRend, mapping,motivoList)
        );
    }


    private static Stream<Arguments> mostrarDetalleGastosSource() {
        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("mostrarDetalleGastos", "path1", false));

        return Stream.of(
                Arguments.of(mapping)
        );
    }

}