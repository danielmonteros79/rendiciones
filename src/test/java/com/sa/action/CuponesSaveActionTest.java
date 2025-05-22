package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.CuponesForm;
import com.sa.form.FiltrarAprobacionForm;
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
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CuponesSaveActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    CuponesSaveAction cuponesSaveAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request, CuponesForm form, ActionMapping mapping,String name) throws Exception {
        try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
            doNothing().when(mockPagosService).asignarCupon(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
        })) {
            ActionForward result = cuponesSaveAction.executeAction(mapping, form, null, null,request, null);
            assertAll(
                    () -> assertEquals(name,result.getName())
            );
        }
    }


    @ParameterizedTest
    @MethodSource("executeActionSourceException")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request, CuponesForm form, ActionMapping mapping,String name) throws Exception {
        try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
            doThrow(new TransactionException("TransactionException",new Throwable("TransactionException"))).when(mockPagosService).asignarCupon(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
        })) {
            ActionForward result = cuponesSaveAction.executeAction(mapping, form, null, null,request, null);
            assertAll(
                    () -> assertEquals(name,result.getName()),
                    () -> assertEquals("ERROR AL GUARDAR CUPONES: TransactionException",request.getAttribute("messageModifTCJP"))
            );
        }
    }
    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        CuponesForm form = new CuponesForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        ActionMapping mapping = new ActionMapping();
        String ret = "success";
        String ret2 = "nuevoGasto";

        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);

        request.setHttpSession(session);
        request.addParameter("tipoSubmit","1");

        request2.setHttpSession(session);
        request2.addParameter("tipoSubmit","2");

        form.setIdRendicion("idRendicion");
        form.setIdGastoRend("idGastoRend");
        form.setImporteCupon("importeCupon");
        form.setNroTarjeta("nroTarjeta");
        form.setCupon("cupon");
        form.setCupDeb("cupDeb");
        form.setCupCred("cupCred");
        form.setDescCupon("descCupon");
        form.setMoneda("moneda");
        form.setFechaPresentacion("fechaPresentacion");


        mapping.addForwardConfig(new ActionForward("success", "path1", false));
        mapping.addForwardConfig(new ActionForward("nuevoGasto", "path2", false));


        return Stream.of(
                Arguments.of(request,form,mapping,ret),
                Arguments.of(request2,form,mapping,ret2)
        );
    }

    private static Stream<Arguments> executeActionSourceException() {
        CuponesForm form = new CuponesForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        ActionMapping mapping = new ActionMapping();
        String ret = "success";

        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);

        request.setHttpSession(session);
        request.addParameter("tipoSubmit","1");


        form.setIdRendicion("idRendicion");
        form.setIdGastoRend("idGastoRend");
        form.setImporteCupon("importeCupon");
        form.setNroTarjeta("nroTarjeta");
        form.setCupon("cupon");
        form.setCupDeb("cupDeb");
        form.setCupCred("cupCred");
        form.setDescCupon("descCupon");
        form.setMoneda("moneda");
        form.setFechaPresentacion("fechaPresentacion");


        mapping.addForwardConfig(new ActionForward("success", "path1", false));
        mapping.addForwardConfig(new ActionForward("nuevoGasto", "path2", false));


        return Stream.of(
                Arguments.of(request,form,mapping,ret)
        );
    }
}

