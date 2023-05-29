package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.services.CierreService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CierreDiarioActionTest {

    @Mock
    ActionServlet servlet;
    @Mock
    ActionMapping actionMapping;
    @Mock
    ActionForm actionForm;
    @Mock
    SAMWebClient samWebClient;
    @Mock
    SAMWebApplication samWebApplication;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    CierreService service;
    @InjectMocks
    CierreDiarioAction action;

    public static Stream<Arguments> executeActionSource() {
        List<Usuario> delegados = new ArrayList<Usuario>();
        Usuario user = new Usuario("1","","",1,"",delegados);
        ActionForward ret = new ActionForward("success","path",true);
        return Stream.of(
                Arguments.of(user, ret)
        );
    }

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando executeAction")
    void executeAction(Usuario user, ActionForward ret) throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("usuario")).thenReturn(user);
        when(actionMapping.findForward("success")).thenReturn(ret);

        ActionForward result = action.executeAction(actionMapping,actionForm,samWebApplication,samWebClient,request,response);

        assertNotNull(result);
    }
}