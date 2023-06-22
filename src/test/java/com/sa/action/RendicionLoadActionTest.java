package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.RendicionesService;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.util.TokenProcessor;
import org.displaytag.filter.BufferedResponseWrapper13Impl;
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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RendicionLoadActionTest {
    @Mock
    Map<String, String> mapMotivoCostos;
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    RendicionForm rendicionForm;
    @Mock
    SAMWebApplication samApplication;
    @Mock
    SAMWebClient samClient;
    @Mock
    HttpServletResponse response;
    @InjectMocks
    RendicionLoadAction rendicionLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(ActionMapping actionMapping, HttpServletRequest request, List list,PrintWriter printWriter) throws Exception {
        Map<String, String> mapMotivoCostos = new HashMap<String, String>();
        mapMotivoCostos.put("codMotivo", "codMotivo");
        rendicionLoadAction.mapMotivoCostos = mapMotivoCostos;

        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
            when(mockM.getMotivoRendiciones(any(),any())).thenReturn(list);
            when(response.getWriter()).thenReturn(printWriter);

        })) {

            ActionForward result = rendicionLoadAction.executeAction(actionMapping, rendicionForm, samApplication, samClient, request, response);
            if(request.getParameter("accion").equals("checkCDestino")){
                Assertions.assertNull(result);
            } else {
                assertNotNull(result);
            }
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(ActionMapping actionMapping, HttpServletRequest request, List list,PrintWriter printWriter) throws Exception {
        Map<String, String> mapMotivoCostos = new HashMap<String, String>();
        mapMotivoCostos.put("codMotivo", "codMotivo");
        rendicionLoadAction.mapMotivoCostos = mapMotivoCostos;

        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
            when(mockM.getMotivoRendiciones(any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
            when(response.getWriter()).thenReturn(printWriter);

        })) {

            ActionForward result = rendicionLoadAction.executeAction(actionMapping, rendicionForm, samApplication, samClient, request, response);
            if(request.getParameter("accion").equals("checkCDestino")){
                assertNull(result);
            } else {
                assertAll(
                        () -> assertNotNull(result),
                        () -> assertEquals("ERROR: TransactionException", request.getAttribute("messageModifTCJP"))
                );
            }
        }
    }


    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() throws FileNotFoundException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();

        Usuario user = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());

        session.setAttribute("userWorking", user);
        session.setAttribute("usuario", user);

        request.setHttpSession(session);
        request.addParameter("accion","checkCDestino");
        request.addParameter("codMotivo","codMotivo");

        MockHttpServletRequest request2 = new MockHttpServletRequest();
        request2.setHttpSession(session);
        request2.addParameter("accion","accion");
        request2.addParameter("codMotivo","codMotivo");

        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        List<ComboMotivo> list = new ArrayList();
        ComboMotivo combo = new ComboMotivo("codMotivo", "descMotivo");
        combo.setCostosDestino("costosDestino");
        list.add(combo);

        PrintWriter printWriter = new PrintWriter("string");

        return Stream.of(
                Arguments.of(mapping,request,list,printWriter ),
                Arguments.of(mapping,request2,list,printWriter)
        );

    }
}