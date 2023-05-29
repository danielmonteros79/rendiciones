package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.form.CierreFiltroForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CierreLoadActionTest {

    @Mock
    ActionMapping mapping;
    @Mock
    ActionForm actionForm;
    @Mock
    SAMWebApplication samWebApplication;
    @Mock
    SAMWebClient samWebClient;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    CierreFiltroForm form;

    @InjectMocks
    CierreLoadAction action;

    public static Stream<Arguments> executeActionSource() {
        List<Usuario> delegados = new ArrayList<Usuario>();
        Usuario user1 = new Usuario("","","",1,"",delegados);
        Usuario user2 = new Usuario("","","",1,"",delegados);;
        String message ="Mensaje";
        ActionForward ret =new ActionForward("success","path",true);
        return Stream.of(
                Arguments.of(user1,user2,"",ret),
                Arguments.of(user1,user2,null,ret),
                Arguments.of(user1,user2,"ERROR",ret),
                Arguments.of(user1,user2,message,ret)
        );
    }

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando executeAction")
    void executeAction(Usuario user1, Usuario user2, String message, ActionForward ret) throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userWorking")).thenReturn(user1);
        when(session.getAttribute("usuario")).thenReturn(user2);
        doNothing().when(form).reset(mapping,request);
        when(request.getAttribute("message")).thenReturn(message);
        when(mapping.findForward("success")).thenReturn(ret);
        ActionForward result = action.executeAction(mapping,form,samWebApplication,samWebClient,request,response);
        assertNotNull(result);
    }
}