package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ComboOpcion2Test {

    private ComboOpcion2 entity = new ComboOpcion2();

    @BeforeEach
    void setup(){
        entity = new ComboOpcion2("","");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y get Id")
    void getId() {
        entity.setId("");
        String resultTest = entity.getId();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Descripcion")
    void getDescripcion() {
        entity.setDescripcion("");
        String resultTest = entity.getDescripcion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("testeando getEstadoRendiciones")
    void getEstadoRendiciones() {
        assertNull(entity.getEstadoRendiciones());
    }
}