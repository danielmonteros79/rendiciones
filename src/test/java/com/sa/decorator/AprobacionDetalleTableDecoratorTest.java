package com.sa.decorator;

import com.sa.entities.CuadroDetallado;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import org.apache.struts.mock.MockHttpServletRequest;
import org.displaytag.model.TableModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class AprobacionDetalleTableDecoratorTest {
    @Spy
    Object currentRowObject;
    @Mock
    Map propertyMap;
    @Mock
    PageContext pageContext;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    Object decoratedObject;
    @Mock
    TableModel tableModel;
    @InjectMocks
    AprobacionDetalleTableDecorator aprobacionDetalleTableDecorator;

    Gastos gastos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando get ver link")
    void getVerLink() {
        String result = aprobacionDetalleTableDecorator.getVerLink();
        Assertions.assertEquals("", result);
    }

//    @ParameterizedTest
//    @MethodSource("getEditarLinkSource")
//    @DisplayName("Testeando get editar link")
//    void getEditarLink(Gastos gastos, String resultado) {
//        currentRowObject = gastos;
//        MockitoAnnotations.openMocks(this);
//
//        String result = aprobacionDetalleTableDecorator.getEditarLink();
//        Assertions.assertEquals(resultado, result);
//    }

    @Test
    @DisplayName("Testeando get borrar link")
    void getBorrarLink() {
        String result = aprobacionDetalleTableDecorator.getBorrarLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get destinatarios link")
    void getDestinatariosLink() {
        String result = aprobacionDetalleTableDecorator.getDestinatariosLink();
        assertNull(result);
    }

    @Test
    @DisplayName("Testeando get cupones link")
    void getCuponesLink() {
        String result = aprobacionDetalleTableDecorator.getCuponesLink();
        assertNull(result);
    }

    @Test
    @DisplayName("Testeando get scan link")
    void getScanLink() {
        String result = aprobacionDetalleTableDecorator.getScanLink();
        assertNull(result);
    }

    @Test
    @DisplayName("Testeando get caratula link")
    void getCaratulaLink() {
        String result = aprobacionDetalleTableDecorator.getCaratulaLink();
        Assertions.assertEquals( null, result);
    }


    @ParameterizedTest
    @MethodSource("getCuponesSource")
    @DisplayName("Testeando getCupones")
    void getCupones(Gastos gasto, HttpServletRequest request, String resultado) {
        aprobacionDetalleTableDecorator.initRow(gasto,0,0);
        aprobacionDetalleTableDecorator.init(pageContext,null,null);

        when(pageContext.getRequest()).thenReturn(request);

        String result = aprobacionDetalleTableDecorator.getCupones();
        Assertions.assertEquals(resultado, result);
    }

    @ParameterizedTest
    @MethodSource("getComentariosSource")
    @DisplayName("Testeando getComentarios")
    void getComentarios(Gastos gasto,HttpServletRequest request, String resultado) {
        aprobacionDetalleTableDecorator.initRow(gasto,0,0);
        aprobacionDetalleTableDecorator.init(pageContext,null,null);

        when(pageContext.getRequest()).thenReturn(request);

        String result = aprobacionDetalleTableDecorator.getComentarios();
        Assertions.assertEquals(resultado, result);
    }

    @ParameterizedTest
    @MethodSource("getComprobanteSource")
    @DisplayName("Testeando getComprobante")
    void getComprobante(Gastos gasto, String res){
        aprobacionDetalleTableDecorator.initRow(gasto,0,0);

        String result = aprobacionDetalleTableDecorator.getComprobante();
        assertEquals(res, result);
    }

    // ------ Sources ------

    private static Stream<Arguments> getEditarLinkSource() {
        Gastos gastos = new Gastos();
        Gastos gastos2 = new Gastos();

        gastos.setIdGasto("1");
        gastos.setCodMotivo("1");
        gastos.setIdRendicion("1");
        gastos.setCuponGasto("1");

        gastos2.setIdGasto("1");
        gastos2.setCodMotivo("1");
        gastos2.setIdRendicion("1");
        gastos2.setCuponGasto("");


        String resultado = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalGastoShow('1', $('#estadoRend').val() ,'1', '1', 1)\"><i class=\"bbva-icon icon-coronita_contract fa-lg\" data-toggle=\"tooltip\" title=\"Editar\"></i></a>";
        String resultado2 = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalGastoShow('1', $('#estadoRend').val() ,'1', '1', 0)\"><i class=\"bbva-icon icon-coronita_contract fa-lg\" data-toggle=\"tooltip\" title=\"Editar\"></i></a>";

        return Stream.of(
                Arguments.of(gastos,resultado),
                Arguments.of(gastos2,resultado2)
        );
    }

    private static Stream<Arguments> getCuponesSource(){
        Gastos gasto = new Gastos();
        Gastos gasto2 = new Gastos();
        MockHttpServletRequest request = new MockHttpServletRequest();
        String res = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalCuponesShow('1', $('#estadoRend').val(), 'codMotivo', null, null, '1', null, null, true)\"><i class=\"bbva-icon icon-coronita_credit-card fa-lg\" data-toggle=\"tooltip\" title=\"Cupones\"></i></a>";
        String res2 = "";

        gasto.setTarjeta("S");
        gasto.setIdRendicion("1");
        gasto.setIdGasto("1");

        request.addParameter("codMotivo","codMotivo");

        gasto2.setTarjeta("A");

        return Stream.of(
                Arguments.of(gasto,request,res),
                Arguments.of(gasto2,request,res2)
        );
    }

    private static Stream<Arguments> getComentariosSource(){
        Gastos gasto = new Gastos();
        Gastos gasto2 = new Gastos();
        MockHttpServletRequest request = new MockHttpServletRequest();
        String res = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalDatosAdicionalesShow('1', 'codMotivoRend', 'idGasto', 'nroGasto', 'obs', true)\"><i class=\"bbva-icon icon-uniE0D2 fa-lg\" data-toggle=\"tooltip\" title=\"Datos adicionales\"></i></a>";
        String res2 = "";
        Rendicion rendicion = new Rendicion();

        rendicion.setId(1);
        rendicion.setCodMotivo("codMotivoRend");

        gasto.setObsObligatoria("S");
        gasto.setIdGasto("idGasto");
        gasto.setNroGasto("nroGasto");
        gasto.setObs("obs");

        request.addParameter("codMotivo","codMotivo");
        request.setAttribute("Rendicion",rendicion);

        gasto2.setObsObligatoria("");

        return Stream.of(
                Arguments.of(gasto,request,res),
                Arguments.of(gasto2,request,res2)
        );
    }

    private static Stream<Arguments> getComprobanteSource(){
        Gastos gasto = new Gastos();
        Gastos gasto2 = new Gastos();
        Gastos gasto3 = new Gastos();
        Gastos gasto4 = new Gastos();
        Gastos gasto5 = new Gastos();
        Gastos gasto6 = new Gastos();
        String res = "FACTURA";
        String res2 = "MAIL";
        String res3 = "SIN COMPROBANTE";
        String res4 = "TICKET";
        String res5 = "FACTURA OBLIGATORIA";
        String res6 = "COMPROBANTE";

        gasto.setComprobante("FACTU");
        gasto2.setComprobante("MAIL-");
        gasto3.setComprobante("SCOMP");
        gasto4.setComprobante("TICK-");
        gasto5.setComprobante("FOBL");
        gasto6.setComprobante("COMPROBANTE");

        return Stream.of(
                Arguments.of(gasto,res),
                Arguments.of(gasto2,res2),
                Arguments.of(gasto3,res3),
                Arguments.of(gasto4,res4),
                Arguments.of(gasto5,res5),
                Arguments.of(gasto6,res6)
        );
    }

}

