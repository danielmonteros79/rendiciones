package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;
import com.sa.form.DescripcionObligatoriaForm;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DescripcionObligatoriaSaveActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    DescripcionObligatoriaSaveAction descripcionObligatoriaSaveAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(DescripcionObligatoriaForm descForm, HttpServletRequest request, ActionMapping mapping) throws Exception {
        try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
            doNothing().when(mockPagosService).addDescripcionObligatoria(any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any());
        })) {
            ActionForward result = descripcionObligatoriaSaveAction.executeAction(mapping, descForm, null, null, request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals("LUEGO DE CARGAR TODAS LAS OBSERVACIONES, PRESIONE SALIR",request.getAttribute("messageModifTCJP"))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(DescripcionObligatoriaForm descForm, HttpServletRequest request, ActionMapping mapping) throws Exception {
        try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
            doThrow(new TransactionException("TransactionException",new Throwable("TransactionException"))).when(mockPagosService).addDescripcionObligatoria(any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any(),any());

        })) {
            ActionForward result = descripcionObligatoriaSaveAction.executeAction(mapping, descForm, null, null, request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals("ERROR AL GENERAR LA DESCRIPCION: TransactionException",request.getAttribute("messageModifTCJP"))
            );
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        DescripcionObligatoriaForm descForm = new DescripcionObligatoriaForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("usuario", usuario);
        session.setAttribute("userWorking", usuario);
        request.setHttpSession(session);

        descForm.setTXT1("txt1");
        descForm.setTXT2("txt2");
        descForm.setTXT250("txt250");
        descForm.setNUM1(1);
        descForm.setNUM2(2);
        descForm.setCOD1("cod1");
        descForm.setCOD2("cod2");
        descForm.setFEC1("01/01/2023");
        descForm.setFEC2("01/01/2023");
        descForm.setIdRendicion("idRendicion");
        descForm.setIdGasto("idGasto");
        descForm.setCodGasto("codGasto");
        descForm.setCodDetOblig("codDetOblig");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
                Arguments.of(descForm, request, mapping)
        );
    }
}