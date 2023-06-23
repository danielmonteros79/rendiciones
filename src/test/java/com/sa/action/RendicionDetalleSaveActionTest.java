package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;
import com.sa.form.RendicionDetalleForm;
import com.sa.form.RendicionForm;
import com.sa.services.PagosService;
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
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RendicionDetalleSaveActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    RendicionDetalleSaveAction rendicionDetalleSaveAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request,RendicionDetalleForm renForm,Integer idGasto,ActionMapping mapping) throws Exception {
        try(MockedConstruction<PagosService> mock = Mockito.mockConstruction(PagosService.class, (mockM, context) -> {
            when(mockM.altaModifGasto(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(),any(),any(), any(),any(),any(), any(),any())).thenReturn(idGasto);
        })) {
            ActionForward result = rendicionDetalleSaveAction.executeAction(mapping, renForm, null, null, request, null);

            if(idGasto != null){
                if(renForm.getGastos().substring(60, 61).equalsIgnoreCase("S")) {
                    assertAll(
                            () -> assertNotNull(result),
                            () -> assertEquals("OK: SE GUARDO CORRECTAMENTE EL GASTO", request.getAttribute("messageModifTCJP")),
                            () -> assertEquals("OK: SE GUARDO CORRECTAMENTE EL GASTO", request.getSession().getAttribute("messageModif"))
                    );
                } else {
                    assertAll(
                            () -> assertNotNull(result),
                            () -> assertEquals("OK: SE GUARDO CORRECTAMENTE EL GASTO", request.getAttribute("messageModifTCJP")),
                            () -> assertEquals("OK: SE GUARDO CORRECTAMENTE EL GASTO", request.getSession().getAttribute("messageModif")),
                            () -> assertEquals("1", request.getAttribute("trxOk")),
                            () -> assertEquals("0000000000000001",request.getAttribute("codigo"))
                    );
                }
            } else {
                assertAll(
                        () -> assertNull(result),
                        () -> assertEquals("OK: SE GUARDO CORRECTAMENTE EL GASTO", request.getAttribute("messageModifTCJP")),
                        () -> assertEquals("OK: SE GUARDO CORRECTAMENTE EL GASTO", request.getSession().getAttribute("messageModif")),
                        () -> assertEquals(1, request.getAttribute("trxFail"))
                );
            }
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request,RendicionDetalleForm renForm,Integer idGasto,ActionMapping mapping) throws Exception {
        try(MockedConstruction<PagosService> mock = Mockito.mockConstruction(PagosService.class, (mockM, context) -> {
            when(mockM.altaModifGasto(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(),any(),any(), any(),any(),any(), any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {
            ActionForward result = rendicionDetalleSaveAction.executeAction(mapping, renForm, null, null, request, null);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals("ERROR AL GUARDAR EL GASTO: TransactionException", request.getAttribute("messageModifTCJP"))
            );

        }
    }


    // ------ Source ------

    public static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario user = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        RendicionDetalleForm renForm = new RendicionDetalleForm();
        RendicionDetalleForm renForm2 = new RendicionDetalleForm();
        Integer idGasto = 1;
        Integer idGasto2 = null;
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("usuario", user);

        renForm.setIdRendicion("1");
        renForm.setIdG("idG");
        renForm.setGastos("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
        renForm.setCodMotivo("codMotivo");
        renForm.setEstadoRendicion("estadoRendicion");

        renForm2.setIdRendicion("1");
        renForm2.setIdG("idG");
        renForm2.setGastos("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        renForm2.setCodMotivo("codMotivo");
        renForm2.setEstadoRendicion("estadoRendicion");

        request.setHttpSession(session);
        request.addParameter("opcion", "MODI");

        request2.setHttpSession(session);
        request2.addParameter("opcion", "opcion");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));
        mapping.addForwardConfig(new ActionForward("failure", "path1", false));

        return Stream.of(
            Arguments.of(request,renForm,idGasto,mapping),
            Arguments.of(request2,renForm2,idGasto,mapping),
            Arguments.of(request2,renForm2,idGasto2,mapping)
        );
    }

}