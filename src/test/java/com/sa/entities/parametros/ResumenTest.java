package com.sa.entities.parametros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;

class ResumenTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link Resumen}
     *   <li>{@link Resumen#setCupon(String)}
     *   <li>{@link Resumen#setEstablecimiento(String)}
     *   <li>{@link Resumen#setEstado(String)}
     *   <li>{@link Resumen#setFecha(Date)}
     *   <li>{@link Resumen#setFechaDebito(Date)}
     *   <li>{@link Resumen#setIdRendicion(String)}
     *   <li>{@link Resumen#setMoneda(String)}
     *   <li>{@link Resumen#setMonto(String)}
     *   <li>{@link Resumen#getCupon()}
     *   <li>{@link Resumen#getEstablecimiento()}
     *   <li>{@link Resumen#getEstado()}
     *   <li>{@link Resumen#getFecha()}
     *   <li>{@link Resumen#getFechaDebito()}
     *   <li>{@link Resumen#getIdRendicion()}
     *   <li>{@link Resumen#getMoneda()}
     *   <li>{@link Resumen#getMonto()}
     * </ul>
     */
    @Test
    void testConstructor() {
        Resumen actualResumen = new Resumen();
        actualResumen.setCupon("Cupon");
        actualResumen.setEstablecimiento("alice.liddell@example.org");
        actualResumen.setEstado("Estado");
        Date fecha = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualResumen.setFecha(fecha);
        Date fechaDebito = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualResumen.setFechaDebito(fechaDebito);
        actualResumen.setIdRendicion("Id Rendicion");
        actualResumen.setMoneda("Moneda");
        actualResumen.setMonto("alice.liddell@example.org");
        assertEquals("Cupon", actualResumen.getCupon());
        assertEquals("alice.liddell@example.org", actualResumen.getEstablecimiento());
        assertEquals("Estado", actualResumen.getEstado());
        assertSame(fecha, actualResumen.getFecha());
        assertSame(fechaDebito, actualResumen.getFechaDebito());
        assertEquals("Id Rendicion", actualResumen.getIdRendicion());
        assertEquals("Moneda", actualResumen.getMoneda());
        assertEquals("alice.liddell@example.org", actualResumen.getMonto());
    }
}

