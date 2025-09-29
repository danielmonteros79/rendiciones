package com.sa.decorator;

import com.sa.entities.CuadroGeneral;
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

import static org.mockito.Mockito.*;

class CuadroGeneralTableDecoratorTest {
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
    CuadroGeneralTableDecorator cuadroGeneralTableDecorator;

    CuadroGeneral cuadroGeneral;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("getVerLinkSource")
    @DisplayName("Testeando get ver link")
    void getVerLink(String estado, String codEstado, String contextPath, String resultado) {
        cuadroGeneral = new CuadroGeneral();
        cuadroGeneral.setEstado(estado);
        cuadroGeneral.setCodEstado(codEstado);
        currentRowObject = cuadroGeneral;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);

        String result = cuadroGeneralTableDecorator.getVerLink();
        Assertions.assertEquals(resultado, result);
    }

    @Test
    @DisplayName("Testeando get borrar link")
    void getBorrarLink() {
        String result = cuadroGeneralTableDecorator.getBorrarLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get destinatarios link")
    void getDestinatariosLink() {
        String result = cuadroGeneralTableDecorator.getDestinatariosLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get cupones link")
    void getCuponesLink() {
        String result = cuadroGeneralTableDecorator.getCuponesLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get scan link")
    void getScanLink() {
        String result = cuadroGeneralTableDecorator.getScanLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get caratula link")
    void getCaratulaLink() {
        String result = cuadroGeneralTableDecorator.getCaratulaLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get editar link")
    void getEditarLink() {
        String result = cuadroGeneralTableDecorator.getEditarLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get thuban link")
    void getThubanLink() {
        String result = cuadroGeneralTableDecorator.getThubanLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get journal link")
    void getJournalLink() {
        String result = cuadroGeneralTableDecorator.getJournalLink();
        Assertions.assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("getVerLinkSource")
    @DisplayName("Testeando get opciones link")
    void getOpciones(String estado, String codEstado, String contextPath, String resultado) {
        cuadroGeneral = new CuadroGeneral();
        cuadroGeneral.setEstado(estado);
        cuadroGeneral.setCodEstado(codEstado);
        currentRowObject = cuadroGeneral;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);

        String result = cuadroGeneralTableDecorator.getOpciones();
        Assertions.assertEquals(resultado, result);
    }


    // ------ Sources ------

    private static Stream<Arguments> getVerLinkSource() {
        String estado = "estado";
        String estado2 = "GLG (ENTRADA)";
        String codEstado = "codEstado";
        String contextPath = "contextPath";
        String resultado = "<i class=\"bbva-icon icon-coronita_search text-primary\" style=\"cursor:pointer;\"  data-toggle=\"tooltip\" title=\"Ver\" onClick=\"detalle('codEstado')\" alt=\"Ver\" title=\"Ver\" border=\"0\" />";
        String resultado2 = "<i class=\"bbva-icon icon-coronita_search text-primary\" style=\"cursor:pointer;\"  data-toggle=\"tooltip\" title=\"Ver\" onClick=\"detalle('PGLGE')\" alt=\"Ver\" title=\"Ver\" border=\"0\" />";

        return Stream.of(
                Arguments.of(estado, codEstado, contextPath, resultado),
                Arguments.of(estado2, codEstado, contextPath, resultado2)
        );
    }

}
