package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrdenPagoTest {

    private List<Rendicion> rendicion = new ArrayList<Rendicion>();
    private List<OrdenPago> opList = new ArrayList<OrdenPago>();
    private OrdenPago entity;

    @BeforeEach
    void setup(){
        entity = new OrdenPago(1,rendicion,1,opList);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y getRendicion")
    void getRendicion() {
        entity.setRendicion(rendicion);
        List<Rendicion> resultTest = entity.getRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(rendicion,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getId")
    void getId() {
        entity.setId(1);
        Integer resultTest = entity.getId();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(1,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getEstado")
    void getEstado() {
        entity.setEstado(1);
        Integer resultTest = entity.getEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(1,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getOrdenPago")
    void getOrdenPago() {
        entity.setOrdenPago(opList);
        List<OrdenPago> resultTest = entity.getOrdenPago();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(opList,resultTest)
        );
    }
}