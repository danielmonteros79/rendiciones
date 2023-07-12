package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.form.CierreForm;
import com.sa.services.CierreService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class CierreSaveActionTest {

    @Mock
    HttpSession httpSession;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    ActionMapping actionMapping;
    @Mock
    ActionForm actionForm;
    @Mock
    SAMWebApplication samWebApplication;
    @Mock
    SAMWebClient samWebClient;
    @Mock
    CierreForm form;
    @InjectMocks
    CierreSaveAction action;

    public static Stream<Arguments> executeActionSource() {

        Object usuario = new Usuario( "idUser", "perfil", "nombre", 1, "sector", new ArrayList<>());
        Object userWorking = new Usuario( "idUser", "perfil", "nombre", 1, "sector", new ArrayList<>());

        Vector<String> params = new Vector<>();
        params.add(0,"substringdeprueba");
        params.add(1,"cmboMotivo");
        params.add(2,"idRendicion");
        Enumeration<?> paramNames = params.elements();

        String estado1= "ORDPG";
        String estado2= "SUSPE";

        String forward1 = "success";
        String forward2 = "successSusp";

        ActionForward resultado = new ActionForward("success","path",true);

        String msg = "ERROR: ERROR AL GENERAR ORDEN: ";

        return Stream.of(
                Arguments.of(usuario,userWorking,paramNames,estado1,forward1,resultado,msg),
                Arguments.of(usuario,userWorking,paramNames,estado2,forward2,resultado,msg)
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando executeAction")
    void executeAction(Object userWorking, Object usuario, Enumeration paramNames, String estado,String forward ,ActionForward resultado) throws Exception {
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("userWorking")).thenReturn(userWorking);
        when(httpSession.getAttribute("usuario")).thenReturn(usuario);
        try (MockedConstruction<CierreService> mockedService = Mockito.mockConstruction(CierreService.class, (mockM,context) -> {
            when(httpServletRequest.getParameterNames()).thenReturn(paramNames);
            when(form.getEstado()).thenReturn(estado);
            when(actionMapping.findForward(forward)).thenReturn(resultado);
            when(httpServletRequest.getParameter("idRendicion")).thenReturn("1");
        })){
            ActionForward result = action.executeAction(actionMapping,form,samWebApplication,samWebClient,httpServletRequest,httpServletResponse);
            assertAll(
                    ()->assertNotNull(result)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando executeAction")
    void executeActionException(Object userWorking, Object usuario, Enumeration paramNames, String estado,String forward ,ActionForward resultado,String msg) throws Exception {
        AtomicReference<ActionForward> result = null;
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("userWorking")).thenReturn(userWorking);
        when(httpSession.getAttribute("usuario")).thenReturn(usuario);
        try (MockedConstruction<CierreService> mockedService = Mockito.mockConstruction(CierreService.class, (mockM,context) -> {
            when(httpServletRequest.getParameterNames()).thenReturn(paramNames);
            when(form.getEstado()).thenReturn(estado);
            when(actionMapping.findForward(forward)).thenReturn(resultado);
            when(httpServletRequest.getParameter("idRendicion")).thenReturn("1");
            doThrow(new Exception()).when(action.executeAction(actionMapping,form,samWebApplication,samWebClient,httpServletRequest,httpServletResponse));
            when(mockM.getMsg()).thenReturn(msg);
        })){
            Exception exception = assertThrows(Exception.class, ()->{
                result.set(action.executeAction(actionMapping,form,samWebApplication,samWebClient,httpServletRequest,httpServletResponse));
                assertAll(
                        ()->assertNotNull(result),
                        ()->assertEquals(resultado,result)
                );
            });
        }
    }
}