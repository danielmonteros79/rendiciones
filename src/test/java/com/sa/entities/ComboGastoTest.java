package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComboGastoTest {

    private ComboGasto entity = new ComboGasto();

    @BeforeEach
    void setup(){
        entity = new ComboGasto("","");
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
        String resultTest =entity.getDescripcion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",entity.getDescripcion())
        );
    }

    @Test
    @DisplayName("Testeando set y get Detalle")
    void setDetalle() {
        entity.setDetalle("");
        String resultTest =entity.getDetalle();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",entity.getDetalle())
        );
    }

    @Test
    @DisplayName("Testeando set y get DescOblig")
    void setDescOblig() {
        entity.setDescOblig("");
        String resultTest =entity.getDescOblig();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",entity.getDescOblig())
        );
    }

    @Test
    void getGastos() {
        List<ComboGasto> resultTest =entity.getGastos();
        assertAll(
                ()->assertNull(resultTest)
        );
    }
}