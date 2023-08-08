package com.sa.form.delegacion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AbmDelegadoFormTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link AbmDelegadoForm}
     *   <li>{@link AbmDelegadoForm#setAccion(String)}
     *   <li>{@link AbmDelegadoForm#setDelegadoCentroCostos(String)}
     *   <li>{@link AbmDelegadoForm#setDelegadoNombre(String)}
     *   <li>{@link AbmDelegadoForm#setDelegadoSector(String)}
     *   <li>{@link AbmDelegadoForm#setDelegadoUser(String)}
     *   <li>{@link AbmDelegadoForm#setEstado(String)}
     *   <li>{@link AbmDelegadoForm#setFeDesde(String)}
     *   <li>{@link AbmDelegadoForm#setFeDesdeOld(String)}
     *   <li>{@link AbmDelegadoForm#setFeHasta(String)}
     *   <li>{@link AbmDelegadoForm#setFeHastaOld(String)}
     *   <li>{@link AbmDelegadoForm#setFechaAlta(String)}
     *   <li>{@link AbmDelegadoForm#setInforme(String)}
     *   <li>{@link AbmDelegadoForm#setOpcion(String)}
     *   <li>{@link AbmDelegadoForm#setUserAlta(String)}
     *   <li>{@link AbmDelegadoForm#setUsuario(String)}
     *   <li>{@link AbmDelegadoForm#getAccion()}
     *   <li>{@link AbmDelegadoForm#getDelegadoCentroCostos()}
     *   <li>{@link AbmDelegadoForm#getDelegadoNombre()}
     *   <li>{@link AbmDelegadoForm#getDelegadoSector()}
     *   <li>{@link AbmDelegadoForm#getDelegadoUser()}
     *   <li>{@link AbmDelegadoForm#getEstado()}
     *   <li>{@link AbmDelegadoForm#getFeDesde()}
     *   <li>{@link AbmDelegadoForm#getFeDesdeOld()}
     *   <li>{@link AbmDelegadoForm#getFeHasta()}
     *   <li>{@link AbmDelegadoForm#getFeHastaOld()}
     *   <li>{@link AbmDelegadoForm#getFechaAlta()}
     *   <li>{@link AbmDelegadoForm#getInforme()}
     *   <li>{@link AbmDelegadoForm#getOpcion()}
     *   <li>{@link AbmDelegadoForm#getUserAlta()}
     *   <li>{@link AbmDelegadoForm#getUsuario()}
     * </ul>
     */
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

