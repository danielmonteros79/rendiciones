package com.sa.form.delegacion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AbmDelegadoFormTest {


    @Test
    void testClearData() {
        AbmDelegadoForm abmDelegadoForm = new AbmDelegadoForm();
        abmDelegadoForm.clearData();
        assertEquals("I", abmDelegadoForm.getAccion());
        assertEquals("", abmDelegadoForm.getFeHasta());
        assertEquals("", abmDelegadoForm.getFeDesde());
        assertEquals("", abmDelegadoForm.getEstado());
        assertEquals("", abmDelegadoForm.getDelegadoUser());
        assertEquals("", abmDelegadoForm.getDelegadoSector());
        assertEquals("", abmDelegadoForm.getDelegadoNombre());
        assertEquals("", abmDelegadoForm.getDelegadoCentroCostos());
    }

    @Test
    void testConstructor() {
        AbmDelegadoForm actualAbmDelegadoForm = new AbmDelegadoForm();
        actualAbmDelegadoForm.setAccion("Accion");
        actualAbmDelegadoForm.setDelegadoCentroCostos("Delegado Centro Costos");
        actualAbmDelegadoForm.setDelegadoNombre("Delegado Nombre");
        actualAbmDelegadoForm.setDelegadoSector("Delegado Sector");
        actualAbmDelegadoForm.setDelegadoUser("Delegado User");
        actualAbmDelegadoForm.setEstado("Estado");
        actualAbmDelegadoForm.setFeDesde("Fe Desde");
        actualAbmDelegadoForm.setFeDesdeOld("Fe Desde Old");
        actualAbmDelegadoForm.setFeHasta("Fe Hasta");
        actualAbmDelegadoForm.setFeHastaOld("Fe Hasta Old");
        actualAbmDelegadoForm.setFechaAlta("Fecha Alta");
        actualAbmDelegadoForm.setInforme("Informe");
        actualAbmDelegadoForm.setOpcion("Opcion");
        actualAbmDelegadoForm.setUserAlta("User Alta");
        actualAbmDelegadoForm.setUsuario("Usuario");
        assertEquals("Accion", actualAbmDelegadoForm.getAccion());
        assertEquals("Delegado Centro Costos", actualAbmDelegadoForm.getDelegadoCentroCostos());
        assertEquals("Delegado Nombre", actualAbmDelegadoForm.getDelegadoNombre());
        assertEquals("Delegado Sector", actualAbmDelegadoForm.getDelegadoSector());
        assertEquals("Delegado User", actualAbmDelegadoForm.getDelegadoUser());
        assertEquals("Estado", actualAbmDelegadoForm.getEstado());
        assertEquals("Fe Desde", actualAbmDelegadoForm.getFeDesde());
        assertEquals("Fe Desde Old", actualAbmDelegadoForm.getFeDesdeOld());
        assertEquals("Fe Hasta", actualAbmDelegadoForm.getFeHasta());
        assertEquals("Fe Hasta Old", actualAbmDelegadoForm.getFeHastaOld());
        assertEquals("Fecha Alta", actualAbmDelegadoForm.getFechaAlta());
        assertEquals("Informe", actualAbmDelegadoForm.getInforme());
        assertEquals("Opcion", actualAbmDelegadoForm.getOpcion());
        assertEquals("User Alta", actualAbmDelegadoForm.getUserAlta());
        assertEquals("Usuario", actualAbmDelegadoForm.getUsuario());
    }
}

