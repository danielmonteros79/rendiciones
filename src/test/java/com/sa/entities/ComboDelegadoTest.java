package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ComboDelegadoTest {

    private ComboDelegado entity;

    @BeforeEach
    void setup(){
        entity = new ComboDelegado("1","Desc");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y get Id")
    void setId() {
        entity.setId("");
        String resultTest = entity.getId();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Descripcion")
    void setDescripcion() {
        entity.setDescripcion("");
        String resultTest = entity.getDescripcion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }
}