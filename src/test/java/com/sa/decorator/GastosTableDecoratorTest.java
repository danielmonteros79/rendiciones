package com.sa.decorator;

import com.sa.entities.Gastos;
import org.junit.jupiter.api.BeforeEach;
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
import javax.servlet.jsp.PageContext;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class GastosTableDecoratorTest {
    @Spy
    Object currentRowObject;
    @Mock
    PageContext pageContext;
    @Mock
    HttpServletRequest httpServletRequest;
    @InjectMocks
    GastosTableDecorator gastosTableDecorator;

    Gastos gastos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando get ver link")
    void getVerLink() {
        String result = gastosTableDecorator.getVerLink();
        assertEquals("", result);
    }

//    @ParameterizedTest
//    @MethodSource("testGetEditarLinkSource")
//    @DisplayName("Testeando get editar link")
//    void getEditarLink(String contextPath,String codigo, String codMotivo, boolean showEditar, String cuponGasto,String respuesta) {
//        gastos = new Gastos();
//        gastos.setCuponGasto(cuponGasto);
//        currentRowObject = gastos;
//        MockitoAnnotations.openMocks(this);
//
//        when(pageContext.getRequest()).thenReturn(httpServletRequest);
//        when(httpServletRequest.getContextPath()).thenReturn(contextPath);
//        when(httpServletRequest.getParameter(codigo)).thenReturn(codigo);
//        when(httpServletRequest.getParameter(codMotivo)).thenReturn(codMotivo);
//        when(httpServletRequest.getAttribute("showEditar")).thenReturn(showEditar);
//
//        String result = gastosTableDecorator.getEditarLink();
//        if (showEditar) {
//            assertEquals(respuesta, result);
//        } else {
//            assertEquals("", result);
//        }
//    }

    @ParameterizedTest
    @MethodSource("testGetBorrarLinkSource")
    @DisplayName("Testeando get borrar link")
    void getBorrarLink(String cuponGasto, String idGasto, String contextPath,String codigo, String codMotivo, boolean showBorrar,String respuesta) {
        gastos = new Gastos();
        gastos.setCuponGasto(cuponGasto);
        gastos.setIdGasto(idGasto);
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);
        when(httpServletRequest.getParameter(codigo)).thenReturn(codigo);
        when(httpServletRequest.getParameter(codMotivo)).thenReturn(codMotivo);
        when(httpServletRequest.getAttribute("showBorrar")).thenReturn(showBorrar);

        String result = gastosTableDecorator.getBorrarLink();
        assertEquals(respuesta, result);
    }

    @Test
    @DisplayName("Testeando get destinatarios link")
    void getDestinatariosLink() {
        //then
        String result = gastosTableDecorator.getDestinatariosLink();
        assertNull(result);
    }

    @ParameterizedTest
    @MethodSource("testGetCuponesLinkSource")
    @DisplayName("Testeando get cupones link")
    void getCuponesLink() {
        //then
        String result = gastosTableDecorator.getCuponesLink();
        assertNull(result);
    }

    @Test
    @DisplayName("Testeando get scan link")
    void getScanLink() {
        String result = gastosTableDecorator.getScanLink();
        assertNull(result);
    }

    @Test
    @DisplayName("Testeando get caratula link")
    void getCaratulaLink() {
        String result = gastosTableDecorator.getCaratulaLink();
        assertNull(result);
    }

    @ParameterizedTest
    @MethodSource("getComprobanteSource")
    @DisplayName("Testeando get comprobante")
    void getComprobante(String comprobante, String resultado) {
        gastos = new Gastos();
        gastos.setComprobante(comprobante);
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        String result = gastosTableDecorator.getComprobante();
        assertEquals(resultado, result);
    }

    /*@ParameterizedTest
    @MethodSource("getDatosAdicionalesSource")
    @DisplayName("Should return datoas adicionales")
    void shouldReturnDatoasAdicionales(String obsObligatoria, String result) {
        //given
        gastos = new Gastos();
        gastos.setObsObligatoria(obsObligatoria);
        gastos.setIdRendicion("1");
        gastos.setCodMotivo("1");
        gastos.setIdGasto("1");
        gastos.setNroGasto("1");
        gastos.setObs("1");
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        //when
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getAttribute("readOnlyDatosAdicionales")).thenReturn(true);

        //then
        String datosAdicionalesToAssert = gastosTableDecorator.getDatosAdicionales();
        assertEquals(result, datosAdicionalesToAssert);
    }*/

    @ParameterizedTest
    @MethodSource("getCuponesSource")
    @DisplayName("Should getCupones")
    void shouldGetCupones(String tarjeta, String result) {
        //given
        gastos = new Gastos();
        gastos.setIdRendicion("1");
        gastos.setIdGasto("1");
        gastos.setMonto("1");
        gastos.setMoneda("ARP");
        gastos.setFechagastos("11/08/2023");
        gastos.setTarjeta(tarjeta);
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        //when
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getAttribute("readOnlyCupones")).thenReturn(true);
        when(httpServletRequest.getParameter("codMotivo")).thenReturn("1");

        //then
        String resultToAssert = gastosTableDecorator.getCupones();
        assertEquals(result, resultToAssert);
    }

    // ------ Sources ------

    public static Stream<Arguments> getCuponesSource() {
        //given
        String tarjeta = "S";
        String result= "<a href=\"#a\" class=\"text-gray\" onclick=\"modalCuponesShow('1', $('#estadoRend').val(), '1', null, null, '1', null, null, true)" +
                           "\"><i class=\"bbva-icon icon-coronita_credit-card d-block h-1\" style=\"font-size:25px;\" data-toggle=\"tooltip\" title=\"Ver cup&oacute;n\"></i></a>";

        return Stream.of(
            Arguments.of("", ""),
            Arguments.of(tarjeta, result)
                        );
    }

    public static Stream<Arguments> getDatosAdicionalesSource() {
        //given
        String obsObligatoria = "obsObligatoria";
        String resultEmpty = "";
        String result ="<a href=\"#a\" class=\"text-gray\" onclick=\"modalDatosAdicionalesShow('1', '1', '1', '1', '1', true)\"><i class=\"bbva-icon icon-uniE0D2 fa-lg\" data-toggle=\"tooltip\" title=\"Datos adicionales\"></i></a>";

        return Stream.of(
            Arguments.of("", resultEmpty),
            Arguments.of(obsObligatoria, result)
                        );
    }

    private static Stream<Arguments> testGetEditarLinkSource() {
        //given
        boolean showEditar = true;
        String contextPath = "contextPath";
        String codigo = "codigo";
        String codMotivo = "codMotivo";
        String cuponGasto = "cuponGasto";
        String respuesta = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalGastoShow('', $('#estadoRend').val(), '', 'null', 1)\"><i class=\"bbva-icon icon-coronita_contract fa-lg\" data-toggle=\"tooltip\" title=\"Editar\"></i></a>";

        return Stream.of(
            Arguments.of(contextPath,codigo, codMotivo, showEditar,cuponGasto,respuesta),
            Arguments.of(contextPath,codigo, codMotivo, !showEditar,cuponGasto,respuesta)
                        );
    }

    private static Stream<Arguments> testGetBorrarLinkSource(){
        //given
        boolean showBorrar = true;
        String cuponGasto = "cuponGasto";
        String idGasto = "idGasto";
        String contextPath = "contextPath";
        String codigo = "codigo";
        String codMotivo = "codMotivo";
        String respuesta1 = "<a href=\"#a\" class=\"text-gray\" onclick=\"eliminarGasto('idGasto')\"><i class=\"bbva-icon icon-coronita_trash fa-lg\" data-toggle=\"tooltip\" title=\"Eliminar\"></i></a>";
        String respuesta2 = "";

        return Stream.of(
            Arguments.of(cuponGasto,idGasto,contextPath,codigo,codMotivo,showBorrar,respuesta1),
            Arguments.of(cuponGasto,idGasto,contextPath,codigo,codMotivo,!showBorrar,respuesta2)
                        );
    }

    private static Stream<Arguments> testGetDestinatariosLinkSource(){
        String obsObligatoria = "";
        String obsObligatoria2 = "OTRO";
        String obs = "00000";
        String obs2 = "OTRO";
        String idGasto = "idGasto";
        String nroGasto = "nroGasto";
        String contextPath = "contextPath";
        String codMotivo = "codMotivo";
        String estadoRend1 = "PENDI";
        String estadoRend2 = "OBSER";
        String estadoRend3 = "OTRO";
        String readOnly = null;
        String readOnly2 = "readOnly";
        String respuesta1 = "";
        String respuesta2 = "<a href=\"contextPath/descripcionObligatoriaPopup.do?action=descripcionObligatoriaPopup&gasto=nroGasto\"><a href=\"#\" onclick=\"showDescripcionObligatoriaPopup(idGasto,nroGasto,'OTRO',2,'codMotivo','PENDI')\"><img src=\"contextPath/images/iconos/message.png\" alt=\"Descripcion\" title=\"Descripcion\" border=\"0\" /> </a></a>";
        String respuesta3 = "<a href=\"contextPath/descripcionObligatoriaPopup.do?action=descripcionObligatoriaPopup&gasto=nroGasto\"><a href=\"#\" onclick=\"showDescripcionObligatoriaPopup(idGasto,nroGasto,'OTRO',2,'codMotivo','OBSER')\"><img src=\"contextPath/images/iconos/message.png\" alt=\"Descripcion\" title=\"Descripcion\" border=\"0\" /> </a></a>";
        String respuesta4 = "<a href=\"contextPath/descripcionObligatoriaPopup.do?action=descripcionObligatoriaPopup&gasto=nroGasto\"><a href=\"#\" onclick=\"showDescripcionObligatoriaPopup(idGasto,nroGasto,'OTRO',1,'codMotivo','OTRO')\"><img src=\"contextPath/images/iconos/message.png\" alt=\"Descripcion\" title=\"Descripcion\" border=\"0\" /> </a></a>";

        return Stream.of(
            Arguments.of(obsObligatoria,obs,idGasto,nroGasto,contextPath,codMotivo,estadoRend1,readOnly,respuesta1),
            Arguments.of(obsObligatoria,obs,idGasto,nroGasto,contextPath,codMotivo,estadoRend2,readOnly,respuesta1),
            Arguments.of(obsObligatoria,obs,idGasto,nroGasto,contextPath,codMotivo,estadoRend3,readOnly,respuesta1),
            Arguments.of(obsObligatoria2,obs2,idGasto,nroGasto,contextPath,codMotivo,estadoRend1,readOnly2,respuesta2),
            Arguments.of(obsObligatoria2,obs2,idGasto,nroGasto,contextPath,codMotivo,estadoRend2,readOnly2,respuesta3),
            Arguments.of(obsObligatoria2,obs2,idGasto,nroGasto,contextPath,codMotivo,estadoRend3,readOnly2,respuesta4)
                        );
    }

    private static Stream<Arguments> testGetCuponesLinkSource(){
        String tarjeta = "S";
        String tarjeta2 = "N";
        String idGasto = "idGasto";
        String fechaGastos = "fechaGastos";
        String cuponGasto = "cuponGasto";
        String cuponGasto2 = "";
        String nroGasto = "nroGasto";
        String contextPath = "contextPath";
        String codMotivo = "codMotivo";
        String resultado1 = "<a href=\"contextPath/cuponesPopup.do?action=cuponesPopup&gasto=nroGasto\"><a href=\"#\" onclick=\"showCuponesTarjetasPopup(idGasto,'cuponGasto','fechaGastos','t','codMotivo')\"><img src=\"contextPath/images/iconos/creditcards.png\" alt=\"Cupones\" title=\"Cupones\" border=\"0\" /> </a></a></td>";
        String resultado2 = "";
        String resultado3 = "<a href=\"contextPath/cuponesPopup.do?action=cuponesPopup&gasto=nroGasto\"><a href=\"#\" onclick=\"showCuponesTarjetasPopup(idGasto,'0','fechaGastos','t','codMotivo')\"><img src=\"contextPath/images/iconos/creditcards.png\" alt=\"Cupones\" title=\"Cupones\" border=\"0\" /> </a></a></td>\n";

        return Stream.of(
            Arguments.of(tarjeta,idGasto,fechaGastos,cuponGasto,nroGasto,contextPath,codMotivo,resultado1),
            Arguments.of(tarjeta2,idGasto,fechaGastos,cuponGasto,nroGasto,contextPath,codMotivo,resultado2),
            Arguments.of(tarjeta,idGasto,fechaGastos,cuponGasto2,nroGasto,contextPath,codMotivo,resultado3),
            Arguments.of(tarjeta2,idGasto,fechaGastos,cuponGasto2,nroGasto,contextPath,codMotivo,resultado2)
                        );
    }

    private static Stream<Arguments> getComprobanteSource(){
        String comprobante1 = "FACTU";
        String comprobante2 = "MAIL-";
        String comprobante3 = "SCOMP";
        String comprobante4 = "TICK-";
        String comprobante5 = "FOBL";
        String comprobante6 = "OTRO";

        String resultado1 = "FACTURA";
        String resultado2 = "MAIL";
        String resultado3 = "SIN COMPROBANTE";
        String resultado4 = "TICKET";
        String resultado5 = "FACTURA OBLIGATORIA";
        String resultado6 = "OTRO";

        return Stream.of(
            Arguments.of(comprobante1,resultado1),
            Arguments.of(comprobante2,resultado2),
            Arguments.of(comprobante3,resultado3),
            Arguments.of(comprobante4,resultado4),
            Arguments.of(comprobante5,resultado5),
            Arguments.of(comprobante6,resultado6)
                        );
    }

}

