package com.sa.form.parametros;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ParametrosExceptuadosFiltroFormTest {

    private ParametrosExceptuadosFiltroForm form;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        form = new ParametrosExceptuadosFiltroForm();
    }

    @Test
    @DisplayName("Testeando getters, setters y metodo clear")
    void clear() {
        form.setMotivoUsuario("");
        form.setExceptuadoFiltro("");
        form.clear();
        assertAll(
                ()->assertEquals(form.getMotivoUsuario(),null),
                ()->assertEquals(form.getExceptuadoFiltro(),null)
        );
    }
}