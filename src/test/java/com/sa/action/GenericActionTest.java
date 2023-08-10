package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.form.RendicionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GenericActionTest {

  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  ActionForm actionFormMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  HttpSession httpSessionMocked;
  @Mock
  ServletContext servletContextMocked;
  @InjectMocks
  GenericAction genericAction;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should execute action")
  void shouldExecuteAction() throws Exception {
    //given
    MockHttpServletRequest request = new MockHttpServletRequest();
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("modal.inicial")).thenReturn("");
    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);
    //then
    ActionForward actionForwardToAssert = genericAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,samWebClientMocked,
       httpServletRequestMocked, httpServletResponseMocked);
    assertNotNull(actionForwardToAssert);
  }
}
