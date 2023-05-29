package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.FiltrarAprobacionForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
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
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AprobacionActionTest {
    @Mock
    Log log;
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
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    HttpSession httpSession;
    @Mock
    FiltrarAprobacionForm filtrarAprobacionForm;
    @InjectMocks
    AprobacionAction aprobacionAction;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(Usuario user,String glg,List<ComboMotivo> motivo,List<Rendicion> rendicion,String cantRendiciones,String opcionEstado, ActionForward ret) throws Exception {

        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("userWorking")).thenReturn(user);
        doNothing().when(filtrarAprobacionForm).reset(any(),any());
        when(httpServletRequest.getParameter("glg")).thenReturn(glg);
        when(filtrarAprobacionForm.getEstado()).thenReturn(opcionEstado);
        when(actionMapping.findForward("success")).thenReturn(ret);

        try(MockedConstruction<AprobacionesService> mock = Mockito.mockConstruction(AprobacionesService.class, (mockAprobacionesService, context) -> {
            when(mockAprobacionesService.getAprobacionesPendientes(any(),any(),any(),any(),any())).thenReturn(rendicion);
            when(mockAprobacionesService.getCantRendiciones()).thenReturn(cantRendiciones);
        })) {
            try(MockedConstruction<RendicionesService> mock2 = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context2) -> {
                when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenReturn(motivo);
            })) {
                ActionForward result = aprobacionAction.executeAction(actionMapping, filtrarAprobacionForm, samWebApplication, samWebClient, httpServletRequest, httpServletResponse);
                assertAll(
                        () -> assertNotNull(result)
                );
            }
        }





    }

    // ------ Sources ------
    private static Stream<Arguments> executeActionSource() {
        Usuario user = new Usuario("idUser", "perfil", "nombre", 1, "sector",new ArrayList<>());
        Object usuario = user;
        String glg = "1";
        String glg2 = "2";
        String glg3 = "0";
        List<ComboMotivo> motivo = new ArrayList<>();
        List<Rendicion> rendicion = new ArrayList<>();
        String cantRendiciones = "";
        String cantRendiciones2 = "0";
        String cantRendiciones3 = "OTRO";
        String opcionEstado = "opcionEstado";
        ActionForward ret = new ActionForward("success","path",true);

        return Stream.of(
                Arguments.of(usuario,glg,motivo,rendicion,cantRendiciones,opcionEstado,ret),
                Arguments.of(usuario,glg2,motivo,rendicion,cantRendiciones,opcionEstado,ret),
                Arguments.of(usuario,glg3,motivo,rendicion,cantRendiciones,opcionEstado,ret),
                Arguments.of(usuario,glg,motivo,rendicion,cantRendiciones2,opcionEstado,ret),
                Arguments.of(usuario,glg,motivo,rendicion,cantRendiciones3,opcionEstado,ret)
        );
    }

}