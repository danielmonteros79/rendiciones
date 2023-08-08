package com.sa.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class CierreOrdenDePagoTableDecoratorTest {

    @Test
    @Disabled("Revisar NullPointerException linea20")
    void testGetEditarLink() {

        (new CierreOrdenDePagoTableDecorator()).getEditarLink();
    }


    @Test
    @Disabled("Revisar NullPointerException linea33")
    void testGetThubanLink() {

        (new CierreOrdenDePagoTableDecorator()).getThubanLink();
    }

    @Test
    @Disabled("Revisar NullPointerException linea 47")
    void testGetJournalLink() {

        (new CierreOrdenDePagoTableDecorator()).getJournalLink();
    }

    @Test
    @Disabled("Revisar NullPointerException linea 20 y 54")
    void testGetOpciones() {


        (new CierreOrdenDePagoTableDecorator()).getOpciones();
    }

    @Test
    @Disabled("Revisar NullPointerException linea 62")
    void testGetCheck() {


        (new CierreOrdenDePagoTableDecorator()).getCheck();
    }

    @Test
    void testConstructor() {
        CierreOrdenDePagoTableDecorator actualCierreOrdenDePagoTableDecorator = new CierreOrdenDePagoTableDecorator();
        assertEquals("", actualCierreOrdenDePagoTableDecorator.getBorrarLink());
        assertNull(actualCierreOrdenDePagoTableDecorator.getCaratulaLink());
        assertNull(actualCierreOrdenDePagoTableDecorator.getCuponesLink());
        assertNull(actualCierreOrdenDePagoTableDecorator.getDestinatariosLink());
        assertNull(actualCierreOrdenDePagoTableDecorator.getScanLink());
        assertEquals("", actualCierreOrdenDePagoTableDecorator.getVerLink());
    }
}

