package com.sa.form;

import com.sa.entities.Rendicion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CierreFormTest {

    private CierreForm form;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        form = new CierreForm();
    }

    @Test
    void getRendicion() {
        form.setRendicion(new ArrayList<Rendicion>());
        List<Rendicion> resultTest = form.getRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getRendicion())
        );
    }

    @Test
    void getId() {
        form.setId(1);
        Integer resultTest = form.getId();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getId())
        );
    }

    @Test
    @DisplayName("Testeando set y get Estado")
    void getEstado() {
        form.setEstado("");
        String resultTest = form.getEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getEstado())
        );
    }

    @Test
    @DisplayName("Testeando set y get CmboMotivo")
    void getCmboMotivo() {
        form.setCmboMotivo("");
        String resultTest = form.getCmboMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getCmboMotivo())
        );
    }

    @Test
    @DisplayName("Testeando set y get MotivoRechazo")
    void getMotivoRechazo() {
        form.setMotivoRechazo("");
        String resultTest = form.getMotivoRechazo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getMotivoRechazo())
        );
    }
}