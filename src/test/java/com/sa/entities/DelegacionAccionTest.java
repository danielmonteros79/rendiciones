package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class DelegacionAccionTest {

    private DelegacionAccion entity;

    @BeforeEach
    void setup(){
        entity = new DelegacionAccion("","");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y get Accion")
    void getAccion() {
        entity.setAccion("");
        String resultTest = entity.getAccion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get AccionDesc")
    void getAccionDesc() {
        entity.setAccionDesc("");
        String resultTest = entity.getAccionDesc();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }
}