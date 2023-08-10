package com.sa.entities.parametros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;

class ParametriaUsuarioDelegadoTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link ParametriaUsuarioDelegado}
     *   <li>{@link ParametriaUsuarioDelegado#setDelegadoAccionDesc(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setDelegadoCentroCostos(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setDelegadoEstado(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setDelegadoInforme(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setDelegadoNombre(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setDelegadoSector(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setDelegadoUser(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setFeDesde(Date)}
     *   <li>{@link ParametriaUsuarioDelegado#setFeHasta(Date)}
     *   <li>{@link ParametriaUsuarioDelegado#setFechaAlta(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setFechaModif(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setId(Integer)}
     *   <li>{@link ParametriaUsuarioDelegado#setOpciones(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setUsuario(String)}
     *   <li>{@link ParametriaUsuarioDelegado#setUsuarioAlta(String)}
     *   <li>{@link ParametriaUsuarioDelegado#getDelegadoAccion()}
     *   <li>{@link ParametriaUsuarioDelegado#getDelegadoAccionDesc()}
     *   <li>{@link ParametriaUsuarioDelegado#getDelegadoCentroCostos()}
     *   <li>{@link ParametriaUsuarioDelegado#getDelegadoEstado()}
     *   <li>{@link ParametriaUsuarioDelegado#getDelegadoInforme()}
     *   <li>{@link ParametriaUsuarioDelegado#getDelegadoNombre()}
     *   <li>{@link ParametriaUsuarioDelegado#getDelegadoSector()}
     *   <li>{@link ParametriaUsuarioDelegado#getDelegadoUser()}
     *   <li>{@link ParametriaUsuarioDelegado#getFeDesde()}
     *   <li>{@link ParametriaUsuarioDelegado#getFeHasta()}
     *   <li>{@link ParametriaUsuarioDelegado#getFechaAlta()}
     *   <li>{@link ParametriaUsuarioDelegado#getFechaModif()}
     *   <li>{@link ParametriaUsuarioDelegado#getId()}
     *   <li>{@link ParametriaUsuarioDelegado#getOpciones()}
     *   <li>{@link ParametriaUsuarioDelegado#getUsuario()}
     *   <li>{@link ParametriaUsuarioDelegado#getUsuarioAlta()}
     * </ul>
     */
    @Test
    void testConstructor() {
        ParametriaUsuarioDelegado actualParametriaUsuarioDelegado = new ParametriaUsuarioDelegado();
        actualParametriaUsuarioDelegado.setDelegadoAccionDesc("Delegado Accion Desc");
        actualParametriaUsuarioDelegado.setDelegadoCentroCostos("Delegado Centro Costos");
        actualParametriaUsuarioDelegado.setDelegadoEstado("Delegado Estado");
        actualParametriaUsuarioDelegado.setDelegadoInforme("Delegado Informe");
        actualParametriaUsuarioDelegado.setDelegadoNombre("Delegado Nombre");
        actualParametriaUsuarioDelegado.setDelegadoSector("Delegado Sector");
        actualParametriaUsuarioDelegado.setDelegadoUser("Delegado User");
        Date feDesde = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualParametriaUsuarioDelegado.setFeDesde(feDesde);
        Date feHasta = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualParametriaUsuarioDelegado.setFeHasta(feHasta);
        actualParametriaUsuarioDelegado.setFechaAlta("Fecha Alta");
        actualParametriaUsuarioDelegado.setFechaModif("Fecha Modif");
        actualParametriaUsuarioDelegado.setId(1);
        actualParametriaUsuarioDelegado.setOpciones("Opciones");
        actualParametriaUsuarioDelegado.setUsuario("Usuario");
        actualParametriaUsuarioDelegado.setUsuarioAlta("Usuario Alta");
        assertNull(actualParametriaUsuarioDelegado.getDelegadoAccion());
        assertEquals("Delegado Accion Desc", actualParametriaUsuarioDelegado.getDelegadoAccionDesc());
        assertEquals("Delegado Centro Costos", actualParametriaUsuarioDelegado.getDelegadoCentroCostos());
        assertEquals("Delegado Estado", actualParametriaUsuarioDelegado.getDelegadoEstado());
        assertEquals("Delegado Informe", actualParametriaUsuarioDelegado.getDelegadoInforme());
        assertEquals("Delegado Nombre", actualParametriaUsuarioDelegado.getDelegadoNombre());
        assertEquals("Delegado Sector", actualParametriaUsuarioDelegado.getDelegadoSector());
        assertEquals("Delegado User", actualParametriaUsuarioDelegado.getDelegadoUser());
        assertSame(feDesde, actualParametriaUsuarioDelegado.getFeDesde());
        assertSame(feHasta, actualParametriaUsuarioDelegado.getFeHasta());
        assertEquals("Fecha Alta", actualParametriaUsuarioDelegado.getFechaAlta());
        assertEquals("Fecha Modif", actualParametriaUsuarioDelegado.getFechaModif());
        assertEquals(1, actualParametriaUsuarioDelegado.getId().intValue());
        assertEquals("Opciones", actualParametriaUsuarioDelegado.getOpciones());
        assertEquals("Usuario", actualParametriaUsuarioDelegado.getUsuario());
        assertEquals("Usuario Alta", actualParametriaUsuarioDelegado.getUsuarioAlta());
    }
}

