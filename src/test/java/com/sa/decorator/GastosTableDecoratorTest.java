package com.sa.decorator;

import com.sa.entities.Gastos;
import com.sa.entities.parametros.Resumen;
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
import javax.servlet.jsp.PageContext;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GastosTableDecoratorTest {
    @Spy
    Object currentRowObject;
    @Mock
    Map propertyMap;
    @Mock
    PageContext pageContext;
    @Mock
    Object decoratedObject;
    @Mock
    TableModel tableModel;
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
    
    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @ParameterizedTest
    @MethodSource("testGetEditarLinkSource")
    @DisplayName("Testeando get editar link")
    void getEditarLink(String contextPath,String codigo, String codMotivo, String estadoRend, String cuponGasto,String respuesta) {
        gastos = new Gastos();
        gastos.setCuponGasto(cuponGasto);
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);
        when(httpServletRequest.getParameter(codigo)).thenReturn(codigo);
        when(httpServletRequest.getParameter(codMotivo)).thenReturn(codMotivo);
        when(httpServletRequest.getAttribute("estadoRend")).thenReturn(estadoRend);


        String result = gastosTableDecorator.getEditarLink();
        assertEquals(respuesta, result);
    }

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @ParameterizedTest
    @MethodSource("testGetBorrarLinkSource")
    @DisplayName("Testeando get borrar link")
    void getBorrarLink(String cuponGasto, String idGasto, String contextPath,String codigo, String codMotivo, String estadoRend,String respuesta) {
        gastos = new Gastos();
        gastos.setCuponGasto(cuponGasto);
        gastos.setIdGasto(idGasto);
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);
        when(httpServletRequest.getParameter(codigo)).thenReturn(codigo);
        when(httpServletRequest.getParameter(codMotivo)).thenReturn(codMotivo);
        when(httpServletRequest.getAttribute("estadoRend")).thenReturn(estadoRend);

        String result = gastosTableDecorator.getBorrarLink();
        assertEquals(respuesta, result);
    }

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @ParameterizedTest
    @MethodSource("testGetDestinatariosLinkSource")
    @DisplayName("Testeando get destinatarios link")
    void getDestinatariosLink(String obsObligatoria, String obs, String idGasto, String nroGasto, String contextPath,String codMotivo, String estadoRend, String readOnly, String respuesta) {
        gastos = new Gastos();
        gastos.setObsObligatoria(obsObligatoria);
        gastos.setObs(obs);
        gastos.setIdGasto(idGasto);
        gastos.setNroGasto(nroGasto);
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);
        when(httpServletRequest.getParameter(codMotivo)).thenReturn(codMotivo);
        when(httpServletRequest.getAttribute("estadoRend")).thenReturn(estadoRend);
        when(httpServletRequest.getAttribute("readOnly")).thenReturn(readOnly);

        String result = gastosTableDecorator.getDestinatariosLink();
        assertEquals(respuesta, result);
    }

    @ParameterizedTest
    @MethodSource("testGetCuponesLinkSource")
    @DisplayName("Testeando get cupones link")
    void getCuponesLink(String tarjeta,String idGasto, String fechaGastos, String cuponGasto, String nroGasto, String contextPath,String codMotivo, String resultado) {
        gastos = new Gastos();
        gastos.setTarjeta(tarjeta);
        gastos.setIdGasto(idGasto);
        gastos.setFechagastos(fechaGastos);
        gastos.setCuponGasto(cuponGasto);
        gastos.setNroGasto(nroGasto);
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);
        when(httpServletRequest.getParameter(codMotivo)).thenReturn(codMotivo);

        String result = gastosTableDecorator.getCuponesLink();
        System.out.println(result);
        //assertEquals(resultado, result);
    }

    @Test
    @DisplayName("Testeando get scan link")
    void getScanLink() {
        String result = gastosTableDecorator.getScanLink();
        assertEquals(null, result);
    }

    @Test
    @DisplayName("Testeando get caratula link")
    void getCaratulaLink() {
        String result = gastosTableDecorator.getCaratulaLink();
        assertEquals(null, result);
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

    // ------ Sources ------

    private static Stream<Arguments> testGetEditarLinkSource() {
        String contextPath = "contextPath";
        String codigo = "codigo";
        String codMotivo = "codMotivo";
        String estadoRend = "APROB";
        String estadoRend2 = "RECHA";
        String estadoRend3 = "ORDPG";
        String estadoRend4 = "SUSPE";
        String estadoRend5 = "PENDI";
        String estadoRend6 = "OTRO";
        String cuponGasto = "cuponGasto";

        String respuesta1 = "";
        String respuesta2 = "<a href=\"contextPath/editarGasto.do?action=editarGasto.do&idGasto=&codigo=codigo&codMotivo=codMotivo&estadoRend=PENDI\"><a href=\"#\" onclick=\"showEditarGastoPopup(,'PENDI','1')\"><img src=\"contextPath/images/iconos/editar.png\" alt=\"Editar\" title=\"Editar\" border=\"0\" /> </a></a>";

        return Stream.of(
                Arguments.of(contextPath,codigo, codMotivo, estadoRend,cuponGasto,respuesta1),
                Arguments.of(contextPath,codigo, codMotivo, estadoRend2,cuponGasto,respuesta1),
                Arguments.of(contextPath,codigo, codMotivo, estadoRend3,cuponGasto,respuesta1),
                Arguments.of(contextPath,codigo, codMotivo, estadoRend4,cuponGasto,respuesta1),
                Arguments.of(contextPath,codigo, codMotivo, estadoRend5,cuponGasto,respuesta2),
                Arguments.of(contextPath,codigo, codMotivo, estadoRend6,cuponGasto,respuesta1)
        );
    }

    private static Stream<Arguments> testGetBorrarLinkSource(){
        String cuponGasto = "cuponGasto";
        String idGasto = "idGasto";
        String contextPath = "contextPath";
        String codigo = "codigo";
        String codMotivo = "codMotivo";
        String estadoRend1 = "PENDI";
        String estadoRend2 = "OTRO";
        String respuesta1 = "<a href=\"contextPath/bajaGasto.do?action=bajaGasto.do&idGasto=idGasto&codigo=codigo&codMotivo=codMotivo\"><a href=\"#\" onclick=\"eliminarGasto(idGasto,'PENDI')\"><img src=\"contextPath/images/iconos/borrar.png\" alt=\"Eliminar\" title=\"Eliminar\" border=\"0\" /> </a></a>";
        String respuesta2 = "";

        return Stream.of(
                Arguments.of(cuponGasto,idGasto,contextPath,codigo,codMotivo,estadoRend1,respuesta1),
                Arguments.of(cuponGasto,idGasto,contextPath,codigo,codMotivo,estadoRend2,respuesta2)
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

