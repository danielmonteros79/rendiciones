package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.Resumen;
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

class ResumenLoadActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    ActionMapping actionMapping;
    @Mock
    ActionForm actionForm;
    @Mock
    SAMWebApplication samWebApplication;
    @Mock
    SAMWebClient samWebClient;
    @Mock
    HttpServletResponse httpServletResponse;
    @InjectMocks
    ResumenLoadAction resumenLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @ParameterizedTest
//    @MethodSource("executeActionSource")
//    @DisplayName("Testeando execute action")
//    void executeAction(HttpServletRequest request, List list,ActionMapping mapping) throws Exception {
//        try(MockedConstruction<ResumenService> mock = Mockito.mockConstruction(ResumenService.class, (mockM, context) -> {
//            when(mockM.getConsumos(any())).thenReturn(list);
//            when(mockM.getMsg()).thenReturn("message");
//        })) {
//            ActionForward result = resumenLoadAction.executeAction(mapping, actionForm, samWebApplication,samWebClient,request, httpServletResponse);
//            assertAll(
//                    () -> assertEquals(request.getAttribute("message"),"message"),
//                    () -> assertEquals(request.getAttribute("resumen"),list),
//                    () -> assertNotNull(result)
//            );
//
//        }
//    }

//    @ParameterizedTest
//    @MethodSource("executeActionSource")
//    @DisplayName("Testeando execute action exception")
//    void executeActionException(HttpServletRequest request, List list,ActionMapping mapping) throws Exception {
//        try(MockedConstruction<ResumenService> mock = Mockito.mockConstruction(ResumenService.class, (mockM, context) -> {
//            when(mockM.getConsumos(any())).thenThrow(new TransactionException("ExceptionMessage",new Throwable("ExceptionMessage")));
//        })) {
//            ActionForward result = resumenLoadAction.executeAction(mapping, actionForm, samWebApplication,samWebClient,request, httpServletResponse);
//            assertAll(
//                    () -> assertEquals(request.getAttribute("message"),"ERROR: ExceptionMessage"),
//                    () -> assertNotNull(request.getAttribute("message")),
//                    () -> assertNotNull(result)
//            );
//        }
//    }

    // ------ Sources ------

    public static Stream<Arguments> executeActionSource() {
        MockHttpSession session = new MockHttpSession();
        Usuario user = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        session.setAttribute("userWorking", user);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setHttpSession(session);

        List<Resumen> list = new ArrayList<>();

        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("success", "path", false));

        return Stream.of(
            Arguments.of(request,list,mapping)
        );
    }

}

