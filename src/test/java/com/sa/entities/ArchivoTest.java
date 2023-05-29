package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ArchivoTest {

    private Archivo entity;
    private InputStream inputStream = new InputStream() {
        @Override
        public int read() throws IOException {
            return 0;
        }
    };

    @BeforeEach
    void setup(){
        entity = new Archivo();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y get NomArchivo")
    void getNomArchivo() {
        entity.setNomArchivo("");
        String resultTest = entity.getNomArchivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );

    }

    @Test
    @DisplayName("Testeando set y get Idu")
    void getIdu() {
        entity.setIdu("");
        String resultTest = entity.getIdu();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get InputStream")
    void getInputStream() {
        entity.setInputStream(inputStream);
        InputStream resultTest = entity.getInputStream();
        assertNotNull(resultTest);
    }
}