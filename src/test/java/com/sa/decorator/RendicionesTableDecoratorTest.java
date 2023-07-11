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
        String resultTest1 = "<img width='25px' src='contextPath/images/iconos/alerta_riesgo_grave.png' alt='Riesgo grave' title='Riesgo grave'/>";
        String resultTest2 = "<img width='25px' src='contextPath/images/iconos/alerta_riesgo.png' alt='Riesgo' title='Riesgo'/>";
        String resultTest3 = "<img width='25px' src='contextPath/images/iconos/alerta_incidencia_grave.png' alt='Incidencia grave' title='Incidencia grave'/>";
        String resultTest4 ="<img width='25px' src='contextPath/images/iconos/alerta_incidente.png' alt='Incidente' title='Incidente'/>";
        String resultTest5 ="<img width='25px' src='contextPath/images/iconos/alerta_anomalia.png' alt='Anomal&iacute;a' title='Anomal&iacute;a'/>";

        return Stream.of(
                Arguments.of(rendicion, 1, "1", resultTest1),
                Arguments.of(rendicion, 2, "2", resultTest2),
                Arguments.of(rendicion, 3, "3", resultTest3),
                Arguments.of(rendicion, 4,"4", resultTest4),
                Arguments.of(rendicion, 5,"5", resultTest5)
        );
    }

    public static Stream<Arguments> getStatusColorSource() {

        Rendicion rendicion = new Rendicion();

        String resultTest1 = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-zero-in-a-circle.png); background-color: yellow;margin-left:7px\"> </div>";
        String resultTest2 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-one-in-a-circle.png); background-color: lightblue;margin-left:7px\"> </div>";
        String resultTest3 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-two-in-a-circle.png); background-color: lightblue;margin-left:7px\"> </div>";
        String resultTest4 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-three-in-a-circle.png); background-color: lightblue;margin-left:7px\"> </div>";
        String resultTest5 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-four-in-circular-button.png); background-color: lightblue;margin-left:7px\"> </div>";
        String resultTest6 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-four-in-circular-button.png); background-color: yellow;margin-left:7px\"> </div>";
        String resultTest7 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: lightblue;margin-left:7px\"> </div>";
        String resultTest8 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: #5cb85c;margin-left:7px\"> </div>";
        String resultTest9 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: yellow;margin-left:7px\"> </div>";
        String resultTest10 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/circular-button.png); background-color: red;margin-left:7px\"> </div>";
        String resultTest11 ="<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: red;margin-left:7px\"> </div>";

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

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @Test
    @DisplayName("Testeando getVerLink")
    void getVerLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getVerLink();

        String resultTest = "<a href=\"contextPath/mostrarDetalleGastos.do?action=mostrarDetalleGastos&codigo=1\"><img src=\"contextPath/images/iconos/ver.png\" alt=\"Ver\" title=\"Ver\" border=\"0\" /></a>";

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

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @Test
    @DisplayName("Testeando getVerLink")
    void getBorrarLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getBorrarLink();

        String resultTest = "<a href=\"contextPath/listaRendiciones.do?action=deleteSector&codigo=1\"><a href=\"#\" onclick=\"eliminarRendicion(1)\"><img src=\"contextPath/images/iconos/borrar.png\" alt=\"Eliminar\" title=\"Eliminar\" border=\"0\" /> </a></a></td>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @Test
    @DisplayName("Testeando getScanLink")
    void getScanLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getScanLink();

        String resultTest = "<a href=\"contextPath/mostrarDetalleScan.do?action=mostrarDetalleScan&codigo=1\"><img src=\"contextPath/images/iconos/scanner.png\" alt=\"Escan\" title=\"Escan\" border=\"0\"width=\"24\" height=\"24\" /></a>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @Test
    @DisplayName("Testeando getScanLink")
    void getDestinatariosLink() {
        String resultTest = decorator.getDestinatariosLink();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
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

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
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

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @ParameterizedTest
    @MethodSource("getCaratulaLinkSource")
    @DisplayName("Testeando getCaratulaLink")
    void getCaratulaLink(Rendicion rendicion, Integer id, String estado, String idu, String adea, String resultTest) {

        rendicion.setId(id);
        rendicion.setEstado(estado);
        rendicion.setIdu(idu);
        rendicion.setAdea(adea);
        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getCaratulaLink();

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }
}