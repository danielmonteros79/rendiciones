package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ComboMonedaTest {

    private ComboMoneda entity = new ComboMoneda();

    @BeforeEach
    void setup(){
        entity = new ComboMoneda("","");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando get y set Id")
    void getId() {
        entity.setId("");
        String resultTest = entity.getId();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando get y set Descripcion")
    void getDescripcion() {
        entity.setDescripcion("");
        String resultTest = entity.getDescripcion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    void getMoneda() {
        assertNull(entity.getMoneda());
    }
}