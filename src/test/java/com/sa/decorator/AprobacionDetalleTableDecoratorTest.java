package com.sa.decorator;

import com.sa.entities.CuadroDetallado;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
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

    @ParameterizedTest
    @MethodSource("getEditarLinkSource")
    @DisplayName("Testeando get editar link")
    void getEditarLink(Gastos gastos, String resultado) {
        currentRowObject = gastos;
        MockitoAnnotations.openMocks(this);

        String result = aprobacionDetalleTableDecorator.getEditarLink();
        Assertions.assertEquals(resultado, result);
    }

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

}

