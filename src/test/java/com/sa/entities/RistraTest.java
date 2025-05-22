package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class RistraTest {

    private Ristra entity = new Ristra();

    @BeforeEach
    void setup(){
        String ristraStr="";
        for (int i = 0; i < 3; i++) {
            ristraStr+="abcdefghijkmnopqrstuvwx";
        }
        entity = new Ristra(ristraStr);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y getProducto")
    void getProducto() {
        entity.setProducto("");
        String resultTest = entity.getProducto();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getSubproducto")
    void getSubproducto() {
        entity.setSubproducto("");
        String resultTest = entity.getSubproducto();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getGarantia")
    void getGarantia() {
        entity.setGarantia("");
        String resultTest = entity.getGarantia();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getTipoPlazo")
    void getTipoPlazo() {
        entity.setTipoPlazo("");
        String resultTest = entity.getTipoPlazo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getPlazo")
    void getPlazo() {
        entity.setPlazo("");
        String resultTest = entity.getPlazo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getSubsector")
    void getSubsector() {
        entity.setSubsector("");
        String resultTest = entity.getSubsector();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getSectorBE")
    void getSectorBE() {
        entity.setSectorBE("");
        String resultTest = entity.getSectorBE();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCnae")
    void getCnae() {
        entity.setCnae("");
        String resultTest = entity.getCnae();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getEmpresaTutelada")
    void getEmpresaTutelada() {
        entity.setEmpresaTutelada("");
        String resultTest = entity.getEmpresaTutelada();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getAmbito")
    void getAmbito() {
        entity.setAmbito("");
        String resultTest = entity.getAmbito();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getMorosidad")
    void getMorosidad() {
        entity.setMorosidad("");
        String resultTest = entity.getMorosidad();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getInversion")
    void getInversion() {
        entity.setInversion("");
        String resultTest = entity.getInversion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getOperacion")
    void getOperacion() {
        entity.setOperacion("");
        String resultTest = entity.getOperacion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCodigoContable")
    void getCodigoContable() {
        entity.setCodigoContable("");
        String resultTest = entity.getCodigoContable();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getDivisa")
    void getDivisa() {
        entity.setDivisa("");
        String resultTest = entity.getDivisa();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getTipoDivisa")
    void getTipoDivisa() {
        entity.setTipoDivisa("");
        String resultTest = entity.getTipoDivisa();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getResto")
    void getResto() {
        entity.setResto("");
        String resultTest = entity.getResto();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getVarios")
    void getVarios() {
        entity.setVarios("");
        String resultTest = entity.getVarios();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    void testToString() {
        assertEquals("abcdefghijkmnopqrstuvwxabcdefghijkmnopqrstuvwxabcdefghijkmnopqrstuvwx",entity.toString());
    }
}