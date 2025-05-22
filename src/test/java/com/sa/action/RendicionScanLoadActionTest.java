package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.AprobacionesService;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.stream.Stream;

import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

class RendicionScanLoadActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    SAMWebApplication samWebApplication;
    @Mock
    SAMWebClient samWebClient;
    @Mock
    HttpServletResponse response;
    @InjectMocks
    RendicionScanLoadAction rendicionScanLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(RendicionForm rf, MockHttpServletRequest request, ActionMapping mapping) throws Exception {
        try(MockedConstruction<AprobacionesService> mock = Mockito.mockConstruction(AprobacionesService.class, (mockM, context) -> {
            doNothing().when(mockM).scanRendicion(any(),any());
        })) {
            ActionForward result = rendicionScanLoadAction.executeAction(mapping, rf,samWebApplication,samWebClient,request,response);
            assertNotNull(result);
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        RendicionForm renForm = new RendicionForm();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();

        Usuario user = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        session.setAttribute("usuario", user);

        Usuario user1 = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        session.setAttribute("userWorking", user1);

        request.addParameter("codigo", "1");

        request.setHttpSession(session);

        MockHttpServletRequest request2 = new MockHttpServletRequest();
        MockHttpSession session2 = new MockHttpSession();
        session2.setAttribute("usuario", user);
        session2.setAttribute("userWorking", user1);
        request2.setHttpSession(session2);
        request2.addParameter("codigo",null);


        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("success", "path", false));

        return Stream.of(
                Arguments.of(renForm,request,mapping),
                Arguments.of(renForm,request2,mapping)
        );
    }
}