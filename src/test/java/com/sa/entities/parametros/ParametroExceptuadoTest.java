package com.sa.entities.parametros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;

class ParametroExceptuadoTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link ParametroExceptuado}
     *   <li>{@link ParametroExceptuado#setDescripcionNombre(String)}
     *   <li>{@link ParametroExceptuado#setDesde(Date)}
     *   <li>{@link ParametroExceptuado#setEstado(String)}
     *   <li>{@link ParametroExceptuado#setHasta(Date)}
     *   <li>{@link ParametroExceptuado#setMotivoUsuario(String)}
     *   <li>{@link ParametroExceptuado#setTipo(String)}
     *   <li>{@link ParametroExceptuado#getDescripcionNombre()}
     *   <li>{@link ParametroExceptuado#getDesde()}
     *   <li>{@link ParametroExceptuado#getEstado()}
     *   <li>{@link ParametroExceptuado#getHasta()}
     *   <li>{@link ParametroExceptuado#getMotivoUsuario()}
     *   <li>{@link ParametroExceptuado#getTipo()}
     * </ul>
     */
    @Test
    void testConstructor() {
        ParametroExceptuado actualParametroExceptuado = new ParametroExceptuado();
        actualParametroExceptuado.setDescripcionNombre("Descripcion Nombre");
        Date desde = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualParametroExceptuado.setDesde(desde);
        actualParametroExceptuado.setEstado("Estado");
        Date hasta = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualParametroExceptuado.setHasta(hasta);
        actualParametroExceptuado.setMotivoUsuario("Motivo Usuario");
        actualParametroExceptuado.setTipo("Tipo");
        assertEquals("Descripcion Nombre", actualParametroExceptuado.getDescripcionNombre());
        assertSame(desde, actualParametroExceptuado.getDesde());
        assertEquals("Estado", actualParametroExceptuado.getEstado());
        assertSame(hasta, actualParametroExceptuado.getHasta());
        assertEquals("Motivo Usuario", actualParametroExceptuado.getMotivoUsuario());
        assertEquals("Tipo", actualParametroExceptuado.getTipo());
    }
}

