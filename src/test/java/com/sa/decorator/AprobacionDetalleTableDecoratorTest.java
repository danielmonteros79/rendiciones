package com.sa.decorator;

import com.sa.entities.CuadroDetallado;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import org.displaytag.model.TableModel;
import org.junit.jupiter.api.Assertions;
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
import java.util.Map;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("getEditarLinkSource")
    @DisplayName("Testeando get editar link")
    void getEditarLink(String idGasto, String cuponGasto, String contextPath, String codigo, String codMotivo, String estadoRend, String usuarioRendicion, String glg, String resultado) {
        gastos = new Gastos();
        gastos.setIdGasto(idGasto);
        gastos.setCuponGasto(cuponGasto);
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);
        when(httpServletRequest.getParameter("codigo")).thenReturn(codigo);
        when(httpServletRequest.getParameter("codMotivo")).thenReturn(codMotivo);
        when(httpServletRequest.getParameter("estadoRend")).thenReturn(estadoRend);
        when(httpServletRequest.getParameter("usuarioRendicion")).thenReturn(usuarioRendicion);
        when(httpServletRequest.getParameter("glg")).thenReturn(glg);

        String result = aprobacionDetalleTableDecorator.getEditarLink();
        Assertions.assertEquals(resultado, result);
    }

    @Test
    @DisplayName("Testeando get borrar link")
    void getBorrarLink() {
        String result = aprobacionDetalleTableDecorator.getBorrarLink();
        Assertions.assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("getDestinatariosLinkSource")
    @DisplayName("Testeando get destinatarios link")
    void getDestinatariosLink(String obsObligatoria, String idGasto, String nroGasto, String obs, String contextPath, Rendicion rendicion, String resultado) {
        gastos = new Gastos();
        gastos.setObsObligatoria(obsObligatoria);
        gastos.setIdGasto(idGasto);
        gastos.setNroGasto(nroGasto);
        gastos.setObs(obs);
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);
        when(httpServletRequest.getAttribute("Rendicion")).thenReturn(rendicion);

        String result = aprobacionDetalleTableDecorator.getDestinatariosLink();
        Assertions.assertEquals(resultado, result);
    }

    @ParameterizedTest
    @MethodSource("getCuponesLinkSource")
    @DisplayName("Testeando get cupones link")
    void getCuponesLink(String tarjeta, String idGasto, String cuponGasto, String nroGasto, String contextPath, String resultado) {
        gastos = new Gastos();
        gastos.setTarjeta(tarjeta);
        gastos.setIdGasto(idGasto);
        gastos.setCuponGasto(cuponGasto);
        gastos.setNroGasto(nroGasto);
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);

        String result = aprobacionDetalleTableDecorator.getCuponesLink();
        Assertions.assertEquals(resultado, result);
    }

    @Test
    @DisplayName("Testeando get scan link")
    void getScanLink() {
        String result = aprobacionDetalleTableDecorator.getScanLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get caratula link")
    void getCaratulaLink() {
        String result = aprobacionDetalleTableDecorator.getCaratulaLink();
        Assertions.assertEquals( null, result);
    }

    // ------ Sources ------

    private static Stream<Arguments> getEditarLinkSource() {
        String idGasto = "idGasto";
        String cuponGasto = "cuponGasto";
        String cuponGasto2 = "";
        String contextPath = "contextPath";
        String codigo = "codigo";
        String codMotivo = "codMotivo";
        String estadoRend = "estadoRend";
        String usuarioRendicion = "usuarioRendicion";
        String glg = "glg";
        String resultado = "<a href=\"contextPath/editarGasto.do?action=editarGasto.do&idGasto=idGasto&codigo=codigo&codMotivo=codMotivo&estadoRend=estadoRend\"><a href=\"#\" onclick=\"showEditarGastoPopup(idGasto,'estadoRend','1','1','usuarioRendicion','glg')\"><img src=\"contextPath/images/iconos/editar.png\" alt=\"Editar\" title=\"Editar\" border=\"0\" /> </a></a>";
        String resultado2 = "<a href=\"contextPath/editarGasto.do?action=editarGasto.do&idGasto=idGasto&codigo=codigo&codMotivo=codMotivo&estadoRend=estadoRend\"><a href=\"#\" onclick=\"showEditarGastoPopup(idGasto,'estadoRend','','1','usuarioRendicion','glg')\"><img src=\"contextPath/images/iconos/editar.png\" alt=\"Editar\" title=\"Editar\" border=\"0\" /> </a></a>";

        return Stream.of(
                Arguments.of(idGasto, cuponGasto, contextPath, codigo, codMotivo, estadoRend, usuarioRendicion, glg, resultado),
                Arguments.of(idGasto, cuponGasto2, contextPath, codigo, codMotivo, estadoRend, usuarioRendicion, glg, resultado2)
        );
    }

    private static Stream<Arguments> getDestinatariosLinkSource() {
        String obsObligatoria = "S";
        String obsObligatoria2 = "";
        String obsObligatoria3 = "OTRO";
        String idGasto = "idGasto";
        String nroGasto = "nroGasto";
        String obs = "obs";
        String contextPath = "contextPath";
        Rendicion rendicion = new Rendicion();
        rendicion.setCodMotivo("codMotivo");
        String resultado = "";
        String resultado2 = "<a href=\"#\" onclick=\"showDescripcionObligatoriaPopup(idGasto,nroGasto,'obs',1,'codMotivo','null')\"><img src=\"contextPath/images/iconos/message.png\" alt=\"Descripcion\" title=\"Descripcion\" border=\"0\" /></a>";

        return Stream.of(
                Arguments.of(obsObligatoria, idGasto, nroGasto, obs, contextPath, rendicion, resultado2),
                Arguments.of(obsObligatoria2, idGasto, nroGasto, obs, contextPath, rendicion, resultado),
                Arguments.of(obsObligatoria3, idGasto, nroGasto, obs, contextPath, rendicion, resultado)
        );
    }

    private static Stream<Arguments> getCuponesLinkSource(){
        String tarjeta = "tarjeta";
        String tarjeta2 = "S";
        String idGasto = "idGasto";
        String cuponGasto = "cuponGasto";
        String nroGasto = "nroGasto";
        String contextPath = "contextPath";
        String resultado = "";
        String resultado2 = "<a href=\"contextPath/cuponesPopup.do?action=cuponesPopup&view=f&gasto=nroGasto\"><a href=\"#\" onclick=\"showCuponesTarjetasPopup(idGasto,'cuponGasto','null','t')\"><img src=\"contextPath/images/iconos/creditcards.png\" alt=\"Cupones\" title=\"Cupones\" border=\"0\" /> </a></a></td>";

        return Stream.of(
                Arguments.of(tarjeta, idGasto, cuponGasto, nroGasto, contextPath, resultado),
                Arguments.of(tarjeta2, idGasto, cuponGasto, nroGasto, contextPath, resultado2)
        );
    }

}

