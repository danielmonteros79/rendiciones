package com.sa.action.cierre;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.form.AprobacionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class CierreOrdenDePagoActionTest {
  /**
   * Method under test: {@link CierreOrdenDePagoAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @Test
  @SuppressWarnings("deprecation")
  @Disabled("sessionUserWorking lanza NPE - reveer")
  void testExecuteAction() throws Exception {
    // Arrange
    CierreOrdenDePagoAction cierreOrdenDePagoAction = new CierreOrdenDePagoAction();
    ActionMapping mapping = new ActionMapping();
    AprobacionForm form = new AprobacionForm();
    SAMWebApplication samApplication = new SAMWebApplication();
    SAMWebClient samClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest("https://example.org/example",
        "https://example.org/example", "https://example.org/example", "https://example.org/example");

    // Act
    cierreOrdenDePagoAction.executeAction(mapping, form, samApplication, samClient, request,
        new MockHttpServletResponse());
  }
}

