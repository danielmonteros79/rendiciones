package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ComboMotivoTest {

    private ComboMotivo entity = new ComboMotivo();

    @BeforeEach
    void setup(){
        entity = new ComboMotivo("","");
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
    @DisplayName("Testeando set y get CostosDestino")
    void getCostosDestino() {
        entity.setCostosDestino("");
        String resultTest = entity.getCostosDestino();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",entity.getCostosDestino())
        );
    }

    @Test
    @DisplayName("Testeando getMotivoRendiciones")
    void getMotivoRendiciones() {
        entity = new ComboMotivo();
        assertNotNull(entity.getMotivoRendiciones());
    }
}