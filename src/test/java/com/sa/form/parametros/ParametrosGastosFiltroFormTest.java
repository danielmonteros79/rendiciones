package com.sa.form.parametros;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ParametrosGastosFiltroFormTest {

    private ParametrosGastosFiltroForm form;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        form = new ParametrosGastosFiltroForm();
    }
    @Test
    void reset() {
        form.setGasto("");
        form.reset();
        assertEquals(form.getGasto(),null);
    }
}