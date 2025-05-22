package com.sa.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class DelegadosAsignadosFormTest {

    private DelegadosAsignadosForm service;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        service = new DelegadosAsignadosForm();
    }

    @Test
    @DisplayName("Testeando sety get NomDel")
    void setNomDel() {
        service.setNomDel("");
        String resultTest = service.getNomDel();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getNomDel())
        );
    }

    @Test
    @DisplayName("Testeando sety get Delegado")
    void setDelegado() {
        service.setDelegado("");
        String resultTest = service.getDelegado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,service.getDelegado())
        );
    }

}