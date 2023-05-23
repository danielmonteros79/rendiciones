package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.form.CuponesForm;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CuponesLoadActionTest {

    @Mock
    ActionServlet servlet;
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
    CuponesForm form;

    @InjectMocks
    CuponesLoadAction action;

    public static Stream<Arguments> executeActionSource() {
        List<Usuario> delegados = new ArrayList<Usuario>();
        Usuario user1 = new Usuario("1","","",1,"",delegados);
        Usuario user2 = new Usuario("2","","",1,"",delegados);
        String cuponGasto = "";
        String view = "";
        String idRendicion="";
        String idGasto ="";
        String codMotivo="";
        String feD = "22/05/2023";
        String feH = "22/05/2023";

        ActionForward ret = new ActionForward("success","path",true);
        return Stream.of(
                Arguments.of(user1,user2,cuponGasto,view,idRendicion,idGasto,codMotivo,feD,feH,ret)
        );
    }

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando executeAction")
    void executeAction(Usuario user1, Usuario user2, String cuponGasto, String view, String idRendicion, String idGasto , String codMotivo, String feD, String feH, ActionForward ret) throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userWorking")).thenReturn(user1);
        when(session.getAttribute("usuario")).thenReturn(user2);
        when(request.getParameter("cg")).thenReturn(cuponGasto);
        when(request.getParameter("view")).thenReturn(view);
        when(request.getParameter("codigo")).thenReturn(idRendicion);
        when(request.getParameter("idGasto")).thenReturn(idGasto);
        when(request.getParameter("codMotivo")).thenReturn(codMotivo);
        when(request.getParameter("feD")).thenReturn(feD);
        when(request.getParameter("feH")).thenReturn(feH);
        when(mapping.findForward("success")).thenReturn(ret);

        ActionForward result = action.executeAction(mapping,form,samWebApplication,samWebClient,request,response);
        assertNotNull(result);
    }
}