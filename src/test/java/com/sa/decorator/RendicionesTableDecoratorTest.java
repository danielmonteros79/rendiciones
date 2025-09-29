package com.sa.decorator;

import com.sa.decorator.RendicionesTableDecorator;
import com.sa.entities.Rendicion;
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
import javax.swing.table.TableModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RendicionesTableDecoratorTest {

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
    RendicionesTableDecorator decorator;

    public static Stream<Arguments> getCuponesLinkSource() {
        Rendicion rendicion = new Rendicion();
        Rendicion rendicion2 = new Rendicion();
        String resultTest1 = "";
        String resultTest2 = "<a href=\"#a\" class=\"text-gray\" onclick=\"obtenerDetalleAlerta(2,123)\"><img width='25px' src='contextPath/images/iconos/alerta_riesgo_grave.png' alt='Riesgo' title='Riesgo' data-toggle='tooltip' title='Riesgo' /></a>";

        rendicion.setAdea("");
        rendicion2.setAdea("123");

        return Stream.of(
                Arguments.of(rendicion, 1, "1", resultTest1),
                Arguments.of(rendicion2, 2, "2", resultTest2)
        );
    }

    public static Stream<Arguments> getStatusColorSource() {

        Rendicion rendicion = new Rendicion();

        String resultTest1 = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-zero-in-a-circle.png); background-color: yellow;\" data-toggle=\"tooltip\" title=\"PENDIENTE\"> </div>";
        String resultTest2 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-one-in-a-circle.png); background-color: lightblue;\" data-toggle=\"tooltip\" title=\"ESCANEADA\"> </div>";
        String resultTest3 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-two-in-a-circle.png); background-color: lightblue;\" data-toggle=\"tooltip\" title=\"PENDIENTE SUPERVISOR\"> </div>";
        String resultTest4 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-three-in-a-circle.png); background-color: lightblue;\" data-toggle=\"tooltip\" title=\"PENDIENTE FIRMA\"> </div>";
        String resultTest5 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-four-in-circular-button.png); background-color: lightblue;\" data-toggle=\"tooltip\" title=\"PENDIENTE GLG\"> </div>";
        String resultTest6 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-four-in-circular-button.png); background-color: yellow;\" data-toggle=\"tooltip\" title=\"OBSERVADA\"> </div>";
        String resultTest7 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: lightblue;\" data-toggle=\"tooltip\" title=\"APROBADA\"> </div>";
        String resultTest8 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: #5cb85c;\" data-toggle=\"tooltip\" title=\"ORDPG\"></div>";
        String resultTest9 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: yellow;\" data-toggle=\"tooltip\" title=\"SUSPENDIDA\"> </div>";
        String resultTest10 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/circular-button.png); background-color: red;\" data-toggle=\"tooltip\" title=\"RECHAZADA\"> </div>";
        String resultTest11 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: red;\" data-toggle=\"tooltip\" title=\"OTRO\"></div>";

        return Stream.of(
                Arguments.of(rendicion, 1, "PENDI", resultTest1),
                Arguments.of(rendicion, 1, "ESCAN", resultTest2),
                Arguments.of(rendicion, 1, "PSUP", resultTest3),
                Arguments.of(rendicion, 1, "PFIRM", resultTest4),
                Arguments.of(rendicion, 1, "PGLG", resultTest5),
                Arguments.of(rendicion, 1, "OBSER", resultTest6),
                Arguments.of(rendicion, 1, "APROB", resultTest7),
                Arguments.of(rendicion, 1, "ORDPG", resultTest8),
                Arguments.of(rendicion, 1, "SUSPE", resultTest9),
                Arguments.of(rendicion, 1, "RECHA", resultTest10),
                Arguments.of(rendicion, 1, "OTRO", resultTest11)
        );
    }

    public static Stream<Arguments> getCaratulaLinkSource() {
        Rendicion rendicion = new Rendicion();

        String resultTest1 = "<a href=\"contextPath/rendicionAviso.do?generate=anymode&rnd=1\"><img src=\"contextPath/images/iconos/pdf.png\" alt=\"Caratula\" title=\"Caratula\" border=\"0\" /></a>";
        String resultTest2 ="";


        return Stream.of(
                Arguments.of(rendicion, 1, "", "idu", "Adea", resultTest1)

        );
    }

    @BeforeEach
    void setup() {
        Rendicion rendicion = new Rendicion();
        rendicion.setId(1);
        rendicion.setEstado("PENDI");
        rendicion.setAlerta("1");
        rendicion.setAdea("");
        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando getVerLink")
    void getVerLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getVerLink();

        String resultTest = "<a href=\"contextPath/rendicionDetalleGastos.do?codigo=1\"><i class=\"bbva-icon icon-coronita_search\" data-toggle=\"tooltip\" title=\"Ver\"></i></a>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Test
    @DisplayName("Testeando getEditarLink")
    void getEditarLink() {
        String resultTest = decorator.getEditarLink();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando getVerLink")
    void getBorrarLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getBorrarLink();

        String resultTest = "<a href=\"#a\" class=\"text-gray\" onclick=\"eliminarRendicion(1)\"><i class=\"bbva-icon icon-coronita_trash\" data-toggle=\"tooltip\" title=\"Eliminar\"></i></a>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Test
    @DisplayName("Testeando getScanLink")
    void getScanLink() {
        String result = decorator.getScanLink();
        assertNull(result);
    }

    @Test
    @DisplayName("Testeando getScanLink")
    void getDestinatariosLink() {
        String result = decorator.getDestinatariosLink();
        assertNull(result);
    }

    @ParameterizedTest
    @MethodSource("getCuponesLinkSource")
    @DisplayName("Testeando getCuponesLink")
    void getCuponesLink(Rendicion rendicion, Integer id, String alerta,String resultTest) {

        rendicion.setId(id);
        rendicion.setAlerta(alerta);
        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result1 = decorator.getCuponesLink();

        assertAll(
                ()->assertNotNull(result1),
                ()->assertEquals(resultTest, result1)
        );
    }

    @ParameterizedTest
    @MethodSource("getStatusColorSource")
    @DisplayName("Testeando getStatusColor")
    void getStatusColor(Rendicion rendicion, Integer id, String estado, String resultTest) {

        rendicion.setId(id);
        rendicion.setEstado(estado);
        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getStatusColor();

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @ParameterizedTest
    @MethodSource("getCaratulaLinkSource")
    @DisplayName("Testeando getCaratulaLink")
    void getCaratulaLink(Rendicion rendicion, Integer id, String estado, String idu, String adea, String resultTest) {
        String result = decorator.getCaratulaLink();
        assertNull(result);
    }
}