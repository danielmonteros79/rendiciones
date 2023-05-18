package com.sa.decorator;

import com.sa.entities.Cupones;
import com.sa.entities.Gastos;
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

class CuponesDecoratorTest {

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
    CuponesDecorator cuponesDecorator;

    Cupones cupones;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando get ver link")
    void getVerLink() {
        String result = cuponesDecorator.getVerLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get editar link")
    void testGetEditarLink() {
        String result = cuponesDecorator.getEditarLink();
        Assertions.assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("getBorrarLinkSource")
    @DisplayName("Testeando get borrar link")
    void getBorrarLink(String nroCupon, String liquidacionNeto, String disponible, String nroTarjeta, String nroCuponDebito, String fechaPresentacion, String moneda, String nroCuponCredito, String establecimiento, boolean adelanto, String cuponGastoSelect,String resultado) {
        cupones = new Cupones();
        cupones.setNroCupon(nroCupon);
        cupones.setLiquidacionNeto(liquidacionNeto);
        cupones.setDisponible(disponible);
        cupones.setNroTarjeta(nroTarjeta);
        cupones.setNroCupon(nroCupon);
        cupones.setNroCuponDebito(nroCuponDebito);
        cupones.setFechaPresentacion(fechaPresentacion);
        cupones.setMoneda(moneda);
        cupones.setNroCuponCredito(nroCuponCredito);
        cupones.setEstablecimiento(establecimiento);
        cupones.setAdelanto(adelanto);
        currentRowObject = cupones;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getAttribute("cuponGastoSelect")).thenReturn(cuponGastoSelect);


        String result = cuponesDecorator.getBorrarLink();
        Assertions.assertEquals(resultado, result);
    }

    @Test
    @DisplayName("Testeando get destinatarios link")
    void getDestinatariosLink() {
        String result = cuponesDecorator.getDestinatariosLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get cupones link")
    void getCuponesLink() {
        String result = cuponesDecorator.getCuponesLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get scan link")
    void getScanLink() {
        String result = cuponesDecorator.getScanLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get caratula link")
    void getCaratulaLink() {
        String result = cuponesDecorator.getCaratulaLink();
        Assertions.assertEquals(null, result);
    }

    // ------ Sources ------


    private static Stream<Arguments> getBorrarLinkSource() {
        String nroCupon = "nroCupon";
        String nroCupon2 = "cuponGastoSelect";
        String liquidacionNeto = "100,0";
        String disponible = "100,0";
        String nroTarjeta = "nroTarjeta";
        String nroCuponDebito = "nroCuponDebito";
        String fechaPresentacion = "fechaPresentacion";
        String moneda = "moneda";
        String nroCuponCredito = "nroCuponCredito";
        String establecimiento = "establecimiento";
        boolean adelanto = true;
        boolean adelanto2 = false;
        String cuponGastoSelect = "cuponGastoSelect";
        String resultado1 = "<input type=\"radio\" name=\"radioCupon\" id=\"cuponRadio\" onclick=\"checkCupon('nroCupon',100.0,nroTarjeta,'nroCupon','nroCuponDebito','nroCuponCredito','fechaPresentacion','establecimiento','moneda','1')\" />";
        String resultado2 = "<input type=\"radio\" name=\"radioCupon\" id=\"cuponRadio\" onclick=\"checkCupon('nroCupon',100.0,nroTarjeta,'nroCupon','nroCuponDebito','nroCuponCredito','fechaPresentacion','establecimiento','moneda','0')\" />";
        String resultado3 = "<input type=\"radio\" name=\"radioCupon\"  id=\"cuponRadio\" onclick=\"checkCupon('cuponGastoSelect',100.0,nroTarjeta,'cuponGastoSelect','nroCuponDebito','nroCuponCredito','','moneda')\" checked/>";

        return Stream.of(
                Arguments.of(nroCupon, liquidacionNeto, disponible, nroTarjeta, nroCuponDebito, fechaPresentacion, moneda, nroCuponCredito, establecimiento, adelanto, cuponGastoSelect, resultado1),
                Arguments.of(nroCupon, liquidacionNeto, disponible, nroTarjeta, nroCuponDebito, fechaPresentacion, moneda, nroCuponCredito, establecimiento, adelanto2, cuponGastoSelect, resultado2),
                Arguments.of(nroCupon2, liquidacionNeto, disponible, nroTarjeta, nroCuponDebito, fechaPresentacion, moneda, nroCuponCredito, establecimiento, adelanto, cuponGastoSelect, resultado3),
                Arguments.of(nroCupon2, liquidacionNeto, disponible, nroTarjeta, nroCuponDebito, fechaPresentacion, moneda, nroCuponCredito, establecimiento, adelanto2, cuponGastoSelect, resultado3)
        );
    }
}