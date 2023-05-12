package com.sa.form;

import com.sa.entities.ComboOpcion;
import com.sa.form.ResumenForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResumenFormTest {

    ResumenForm resumenFormReal;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        resumenFormReal = new ResumenForm();
    }

    @Test
    @DisplayName("Testeando set y get  Resumen")
    void getResumen() {
        String res = "Resumen";
        resumenFormReal.setResumen(res);
        String resumen = resumenFormReal.getResumen();
        assertAll(
                ()->assertNotNull(resumen),
                ()->assertEquals(res,resumen)
        );
    }

    @Test
    @DisplayName("Testeando set y get Estado")
    void getEstado() {
        String est = "estado";
        resumenFormReal.setEstado(est);
        String getEstado = resumenFormReal.getEstado();
        assertAll(
                ()-> assertNotNull(getEstado),
                ()-> assertEquals(est, getEstado)
        );
    }

    @Test
    @DisplayName("Testeando set y get CmbResumen")
    void getCmbResumen() {

        List<ComboOpcion> combList = new ArrayList<>();
        combList.add(new ComboOpcion("id1", "Descripcion"));
        combList.add(new ComboOpcion("id2", "Descripcion"));
        combList.add(new ComboOpcion("id3", "Descripcion"));
        resumenFormReal.setCmbResumen(combList);

        List<ComboOpcion> realComboList = resumenFormReal.getCmbResumen();

        assertAll(
                ()-> assertNotNull(realComboList),
                ()-> assertEquals(realComboList, combList)
        );
    }
}