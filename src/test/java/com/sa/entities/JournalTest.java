package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class JournalTest {

    private Journal entity;

    @BeforeEach
    void setup(){
        entity = new Journal();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y getNumeroAprob")
    void getNumeroAprob() {
        entity.setNumeroAprob("");
        String resultTest = entity.getNumeroAprob();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getEstado")
    void getEstado() {
        entity.setEstado("");
        String resultTest = entity.getEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getUsuarioProx")
    void getUsuarioProx() {
        entity.setUsuarioProx("");
        String resultTest = entity.getUsuarioProx();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getNombreUsuarioProx")
    void getNombreUsuarioProx() {
        entity.setNombreUsuarioProx("");
        String resultTest = entity.getNombreUsuarioProx();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getNivel")
    void getNivel() {
        entity.setNivel("");
        String resultTest = entity.getNivel();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getUsuarioAprob")
    void getUsuarioAprob() {
        entity.setUsuarioAprob("");
        String resultTest = entity.getUsuarioAprob();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getNombreUsuarioAprob")
    void getNombreUsuarioAprob() {
        entity.setNombreUsuarioAprob("");
        String resultTest = entity.getNombreUsuarioAprob();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getFechaApr")
    void getFechaApr() {
        entity.setFechaApr("");
        String resultTest = entity.getFechaApr();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }
}