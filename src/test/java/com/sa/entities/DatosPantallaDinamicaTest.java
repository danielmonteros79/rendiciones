package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatosPantallaDinamicaTest {

    private DatosPantallaDinamica entity;

    @BeforeEach
    void setup(){
        entity = new DatosPantallaDinamica();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y get TipoCampo")
    void getTipoCampo() {
        entity.setTipoCampo("");
        String resultTest = entity.getTipoCampo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Mostrar")
    void getMostrar() {
        entity.setMostrar("");
        String resultTest = entity.getMostrar();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get CampoObligatorio")
    void getCampoObligatorio() {
        entity.setCampoObligatorio("");
        String resultTest = entity.getCampoObligatorio();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get TituloCampo")
    void getTituloCampo() {
        entity.setTituloCampo("");
        String resultTest = entity.getTituloCampo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando add y get OpcionesCombo")
    void getOpcionesCombo() {
        ComboGenerico combo = new ComboGenerico();
        entity.addOpcionCombo(combo);
        List<ComboGenerico> resultTest = entity.getOpcionesCombo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertTrue(resultTest.equals(entity.getOpcionesCombo()))
        );
    }
}