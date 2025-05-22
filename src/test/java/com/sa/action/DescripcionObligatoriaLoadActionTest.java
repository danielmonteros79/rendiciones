package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.DatosPantallaDinamica;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.DescripcionObligatoriaForm;
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

class DescripcionObligatoriaLoadActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    DescripcionObligatoriaLoadAction descripcionObligatoriaLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request,DescripcionObligatoriaForm form, ActionMapping mapping,List<DatosPantallaDinamica> fieldsScreen,List<List<String>> filas ) throws Exception {
        try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
            when(mockPagosService.consultaDetObligatorio(any(),any(),any(),any())).thenReturn(fieldsScreen);
            when(mockPagosService.getMsg()).thenReturn("msg");
            when(mockPagosService.consultaDetallesGastos(any(),any(),any(),any())).thenReturn(filas);
        })) {
            ActionForward result = descripcionObligatoriaLoadAction.executeAction(mapping,form, null, null, request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals(fieldsScreen,request.getAttribute("listCampos")),
                    () -> assertEquals(filas,request.getAttribute("filas")),
                    () -> assertEquals("messageModifTCJP<br>msg<br>msg<br>",request.getAttribute("messageModifTCJP"))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request,DescripcionObligatoriaForm form, ActionMapping mapping,List<DatosPantallaDinamica> fieldsScreen,List<List<String>> filas ) throws Exception {
        try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
            when(mockPagosService.consultaDetObligatorio(any(),any(),any(),any())).thenReturn(fieldsScreen);
            when(mockPagosService.getMsg()).thenReturn("msg");
            when(mockPagosService.consultaDetallesGastos(any(),any(),any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {
            ActionForward result = descripcionObligatoriaLoadAction.executeAction(mapping,form, null, null, request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals("ERROR AL CARGAR LAS DESCRIPCIONES OBLIGATORIAS: TransactionException",request.getAttribute("messageModifLoad"))
            );
        }
    }
    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        DescripcionObligatoriaForm form = new DescripcionObligatoriaForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        List<DatosPantallaDinamica> fieldsScreen = new ArrayList<>();
        List<List<String>> filas = new ArrayList<>();
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("usuario", usuario);

        request.setHttpSession(session);
        request.addParameter("tipoEntrada","1");
        request.setAttribute("messageModifTCJP","messageModifTCJP");
        request.addParameter("rnd","rnd");
        request.addParameter("rndg","rndg");
        request.addParameter("rndm","rndm");
        request.addParameter("codMotivo","codMotivo");
        request.addParameter("codObserv","1");

        request2.setHttpSession(session);
        request2.addParameter("tipoEntrada","2");
        request2.setAttribute("messageModifTCJP","messageModifTCJP");
        request2.addParameter("rnd","rnd");
        request2.addParameter("rndg","rndg");
        request2.addParameter("rndm","rndm");
        request2.addParameter("codMotivo","codMotivo");
        request2.addParameter("codObserv","1");

        form.setEstadoRend(null);

        DatosPantallaDinamica datosPantallaDinamica1 = new DatosPantallaDinamica();
        datosPantallaDinamica1.setTipoCampo("COD1");

        DatosPantallaDinamica datosPantallaDinamica2 = new DatosPantallaDinamica();
        datosPantallaDinamica2.setTipoCampo("COD2");

        DatosPantallaDinamica datosPantallaDinamica3 = new DatosPantallaDinamica();
        datosPantallaDinamica3.setTipoCampo("TXT1");

        DatosPantallaDinamica datosPantallaDinamica4 = new DatosPantallaDinamica();
        datosPantallaDinamica4.setTipoCampo("TXT2");

        DatosPantallaDinamica datosPantallaDinamica5 = new DatosPantallaDinamica();
        datosPantallaDinamica5.setTipoCampo("NUM1");

        DatosPantallaDinamica datosPantallaDinamica6 = new DatosPantallaDinamica();
        datosPantallaDinamica6.setTipoCampo("NUM2");

        DatosPantallaDinamica datosPantallaDinamica7 = new DatosPantallaDinamica();
        datosPantallaDinamica7.setTipoCampo("FEC1");

        DatosPantallaDinamica datosPantallaDinamica8 = new DatosPantallaDinamica();
        datosPantallaDinamica8.setTipoCampo("FEC2");

        DatosPantallaDinamica datosPantallaDinamica9 = new DatosPantallaDinamica();
        datosPantallaDinamica9.setTipoCampo("TXT250");

        fieldsScreen.add(datosPantallaDinamica1);
        fieldsScreen.add(datosPantallaDinamica2);
        fieldsScreen.add(datosPantallaDinamica3);
        fieldsScreen.add(datosPantallaDinamica4);
        fieldsScreen.add(datosPantallaDinamica5);
        fieldsScreen.add(datosPantallaDinamica6);
        fieldsScreen.add(datosPantallaDinamica7);
        fieldsScreen.add(datosPantallaDinamica8);
        fieldsScreen.add(datosPantallaDinamica9);


        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
                Arguments.of(request,form,mapping,fieldsScreen,filas),
                Arguments.of(request2,form,mapping,fieldsScreen,filas)
        );
    }


}