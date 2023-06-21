package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
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

import static org.mockito.Mockito.*;

class RistraPopUpLoadActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;@Mock
    SAMWebApplication samApplication;
    @Mock
    SAMWebClient samClient;
    @Mock
    HttpServletResponse response;
    @Mock
    ActionForm form;
    @Mock
    HttpServletRequest request;
    @InjectMocks
    RistraPopUpLoadAction ristraPopUpLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando execute action")
    void executeAction() throws Exception {
        ActionMapping mapping = new ActionMapping();
        mapping.addForwardConfig(new ActionForward("success", "path", false));

        ActionForward result = ristraPopUpLoadAction.executeAction(mapping, form, samApplication, samClient, request, response);
        Assertions.assertEquals("success", result.getName());
    }
}

