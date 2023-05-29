package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ComboOpcionTest {

    private ComboOpcion entity = new ComboOpcion();

    @BeforeEach
    void setup(){
        entity = new ComboOpcion("");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y get Id")
    void getId() {
        entity.setId("");
        String resultTest = entity.getId();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",entity.getId())
        );
    }

    @Test
    @DisplayName("Testeando set y get Descripcion")
    void getDescripcion() {
        entity.setDescripcion("");
        String resultTest = entity.getDescripcion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",entity.getDescripcion())
        );
    }

    @Test
    void testToString() {
        entity = new ComboOpcion("1","desc");
        assertEquals("1 = desc",entity.toString());
    }
}