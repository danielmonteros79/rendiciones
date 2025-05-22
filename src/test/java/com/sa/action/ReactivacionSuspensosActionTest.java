package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;
import com.sa.services.CierreService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReactivacionSuspensosActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    ReactivacionSuspensosAction reactivacionSuspensosAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request, String ret,ActionMapping mapping) throws Exception {
        try(MockedConstruction<CierreService> mock = Mockito.mockConstruction(CierreService.class, (mockM, context) -> {
            when(mockM.GenerarPagoMarca(any(),any(),any(),any(),any(),any(),any(),any())).thenReturn(ret);
        })) {
            ActionForward result = reactivacionSuspensosAction.executeAction(mapping, null, null, null, request, null);
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals("f",request.getAttribute("tipoConsulta")),
                    () -> assertEquals("OK: SE COLOCO LA MARCA DE REACTIVACION DE SUSPENSOS CORRECTAMENTE  ",request.getAttribute("messageModifTCJP"))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request, String ret,ActionMapping mapping) throws Exception {
        try(MockedConstruction<CierreService> mock = Mockito.mockConstruction(CierreService.class, (mockM, context) -> {
            when(mockM.GenerarPagoMarca(any(),any(),any(),any(),any(),any(),any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {
            ActionForward result = reactivacionSuspensosAction.executeAction(mapping, null, null, null, request, null);
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals("f",request.getAttribute("tipoConsulta")),
                    () -> assertEquals("ERROR AL COLOCAR MARCA: TransactionException",request.getAttribute("messageModifTCJP"))
            );
        }
    }

    // ------ Sources ------
    private static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario user = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        String ret = "ret";
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("usuario", user);

        request.setHttpSession(session);

        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
                Arguments.of(request,ret,mapping)
        );
    }



}