package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.form.parametros.ParametrosGastosForm;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.util.TokenProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RistraPopUpSaveActionTest {
    @Mock
    Log log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    SAMWebApplication samApplication;
    @Mock
    SAMWebClient samClient;
    @Mock
    HttpServletResponse response;
    @InjectMocks
    RistraPopUpSaveAction ristraPopUpSaveAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando execute action")
    void executeAction() throws Exception {
        ParametrosGastosForm form = new ParametrosGastosForm();
        HttpServletRequest request = new MockHttpServletRequest();
        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("success", "path", false));

        ActionForward result = ristraPopUpSaveAction.executeAction(mapping, form, samApplication, samClient, request, response);
        assertAll(
                () -> assertEquals("OK",request.getAttribute("message")),
                () -> assertEquals("success", result.getName())
        );
    }
}