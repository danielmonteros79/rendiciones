package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CuadroGeneralTest {

    private CuadroGeneral entity;

    @BeforeEach
    void setup(){
        entity = new CuadroGeneral();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y get CodEstado")
    void getCodEstado() {
        entity.setCodEstado("");
        String resultTest = entity.getCodEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get CodMotivo")
    void getCodMotivo() {
        entity.setCodMotivo("");
        String resultTest = entity.getCodMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get CodGlg")
    void getCodGlg() {
        entity.setCodGlg("");
        String resultTest = entity.getCodGlg();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Estado")
    void getEstado() {
        entity.setEstado("");
        String resultTest = entity.getEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get CantRend")
    void getCantRend() {
        entity.setCantRend("");
        String resultTest = entity.getCantRend();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get MontoTotal")
    void getMontoTotal() {
        entity.setMontoTotal("");
        String resultTest = entity.getMontoTotal();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Cons")
    void getCons() {
        entity.setCons("");
        String resultTest = entity.getCons();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Glg")
    void getGlg() {
        List<String> list = new ArrayList<>();
        list.add("");
        entity.setGlg(list);
        List<String> resultTest = entity.getGlg();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(list,resultTest)
        );
    }

}