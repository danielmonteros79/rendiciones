package com.sa.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class DescripcionObligatoriaFormTest {

    private DescripcionObligatoriaForm service;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        service = new DescripcionObligatoriaForm();
    }

    @Test
    @DisplayName("Testeando reset y getter y setters")
    void resetandAllSettersyGetters() {
        service.setIdRendicion("");
        service.setIdGasto("");
        service.setCodGasto("");
        service.setCodDetOblig("");
        service.setCodDetOblig("");
        service.setTXT1("");
        service.setTXT2("");
        service.setNUM1(1);
        service.setNUM2(2);
        service.setCOD1("");
        service.setCOD2("");
        service.setTXT250("");
        service.setFEC1("");
        service.setFEC2("");
        service.setTipoEntrada("");
        service.setCodMotivo("");
        service.setEstadoRend("");

        service.reset();

        assertAll(
                ()->assertEquals(service.getIdRendicion(),null),
                ()->assertEquals(service.getIdGasto(),null),
                ()->assertEquals(service.getCodGasto(),null),
                ()->assertEquals(service.getCodDetOblig(),null),
                ()->assertEquals(service.getTXT1(),null),
                ()->assertEquals(service.getTXT2(),null),
                ()->assertEquals(service.getNUM1(),null),
                ()->assertEquals(service.getNUM2(),null),
                ()->assertEquals(service.getCOD1(),null),
                ()->assertEquals(service.getCOD2(),null),
                ()->assertEquals(service.getTXT250(),null),
                ()->assertEquals(service.getFEC1(),null),
                ()->assertEquals(service.getFEC2(),null),
                ()->assertEquals(service.getTipoEntrada(),null),
                ()->assertEquals(service.getCodMotivo(),null),
                ()->assertEquals(service.getEstadoRend(),null)
        );
    }

    @Test
    @DisplayName("Testeando metodo toString")
    void testToString() {
        String resultTest = service.toString();

        String toString = "DescripcionObligatoriaForm [COD1=" + service.COD1 + ", COD2=" + service.COD2
                + ", FEC1=" + service.FEC1 + ", FEC2=" + service.FEC2 + ", NUM1="
                + service.NUM1 + ", NUM2=" + service.NUM2 + ", TXT1=" + service.TXT1 + ", TXT2=" + service.TXT2
                + ", TXT250=" + service.TXT250 + ", codDetOblig=" + service.codDetOblig
                + ", codGasto=" + service.codGasto + ", idGasto=" + service.idGasto
                + ", idRendicion=" + service.idRendicion + "]";

        assertEquals(resultTest,toString);
    }

}