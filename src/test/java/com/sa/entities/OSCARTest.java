package com.sa.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class OSCARTest {

    private OSCAR entity = new OSCAR();

    @BeforeEach
    void setup(){
        entity = new OSCAR("OSCAR");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y getO")
    void getO() {
        entity.setO("O");
        String resultTest = entity.getO();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("O",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getS")
    void getS() {
        entity.setS("S");
        String resultTest = entity.getS();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("S",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getC")
    void getC() {
        entity.setC("C");
        String resultTest = entity.getC();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("C",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getA")
    void getA() {
        entity.setA("A");
        String resultTest = entity.getA();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("A",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getR")
    void getR() {
        entity.setR("R");
        String resultTest = entity.getR();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("R",resultTest)
        );
    }

    @Test
    void testToString() {
        entity = new OSCAR("o","s","c","a","r");
        assertEquals("oscar",entity.toString());
    }
}