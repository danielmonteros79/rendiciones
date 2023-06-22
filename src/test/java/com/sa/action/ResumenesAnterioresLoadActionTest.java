package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboOpcion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.Resumen;
import com.sa.form.ResumenForm;
import com.sa.services.ResumenService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResumenesAnterioresLoadActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    ActionMapping actionMapping;
    @Mock
    SAMWebApplication samWebApplication;
    @Mock
    SAMWebClient samWebClient;
    @Mock
    HttpServletResponse httpServletResponse;
    @InjectMocks
    ResumenesAnterioresLoadAction resumenesAnterioresLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request, List list, ActionMapping mapping,ResumenForm actionForm) throws Exception {
        try(MockedConstruction<ResumenService> mock = Mockito.mockConstruction(ResumenService.class, (mockM, context) -> {
            when(mockM.getFechasResumenes(any())).thenReturn(list);
            when(mockM.getMsg()).thenReturn("message");
        })) {
            ActionForward result = resumenesAnterioresLoadAction.executeAction(mapping, actionForm, samWebApplication,samWebClient,request, httpServletResponse);
            assertAll(
                    () -> assertEquals("message",request.getAttribute("message")),
                    () -> assertNotNull(actionForm.getResumen()),
                    () -> assertNotNull(actionForm.getCmbResumen()),
                    () -> assertEquals(request.getAttribute("cmbResumen"), actionForm.getCmbResumen()),
                    () -> assertNotNull(request.getAttribute("resumenes")),
                    () -> assertNotNull(result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request, List list, ActionMapping mapping,ResumenForm actionForm) throws Exception {
        try(MockedConstruction<ResumenService> mock = Mockito.mockConstruction(ResumenService.class, (mockM, context) -> {
            when(mockM.getFechasResumenes("id")).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {
            ActionForward result = resumenesAnterioresLoadAction.executeAction(mapping, actionForm, samWebApplication,samWebClient,request, httpServletResponse);
            assertAll(
                    () -> assertEquals("ERROR: TransactionException",request.getAttribute("message")),
                    () -> assertNotNull(actionForm.getResumen()),
                    () -> assertNotNull(actionForm.getCmbResumen()),
                    () -> assertEquals(request.getAttribute("cmbResumen"), actionForm.getCmbResumen()),
                    () -> assertNotNull(request.getAttribute("resumenes")),
                    () -> assertNotNull(result)
            );
        }
    }

    // ------ Sources ------

    public static Stream<Arguments> executeActionSource() {
        MockHttpSession session = new MockHttpSession();
        Usuario user = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        session.setAttribute("userWorking", user);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setHttpSession(session);

        List<ComboOpcion> list = new ArrayList<>();

        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("success", "path", false));

        ResumenForm actionForm = new ResumenForm();

        return Stream.of(
                Arguments.of(request,list,mapping,actionForm)
        );
    }
}

