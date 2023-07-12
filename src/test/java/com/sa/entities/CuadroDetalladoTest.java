package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CuadroDetalladoTest {

    private CuadroDetallado entity;

    @BeforeEach
    void setup(){
        entity = new CuadroDetallado();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y get Usuario")
    void setUsuario() {
        entity.setUsuario("");
        String resultTest = entity.getUsuario();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get ProxUsuario")
    void setProxUsuario() {
        entity.setProxUsuario("");
        String resultTest = entity.getProxUsuario();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Id")
    void setId() {
        entity.setId(1);
        Integer resultTest = entity.getId();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(1,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Motivo")
    void setMotivo() {
        entity.setMotivo("");
        String resultTest = entity.getMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
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

    @Test
    @DisplayName("Testeando set y get Importe")
    void setImporte() {
        entity.setImporte("");
        String resultTest = entity.getImporte();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get Estado")
    void setEstado() {
        entity.setEstado("");
        String resultTest = entity.getEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaUltModif")
    void setFechaUltModif() {
        Date newDate= new Date();
        entity.setFechaUltModif(newDate);
        Date resultTest = entity.getFechaUltModif();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(newDate,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get CodMotivo")
    void setCodMotivo() {
        entity.setCodMotivo("");
        String resultTest = entity.getCodMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y get CodEstado")
    void setCodEstado() {
        entity.setCodEstado("");
        String resultTest = entity.getCodEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }
}