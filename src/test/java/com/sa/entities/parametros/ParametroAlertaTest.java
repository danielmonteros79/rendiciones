package com.sa.entities.parametros;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParametroAlertaTest {

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link ParametroAlerta}
     *   <li>{@link ParametroAlerta#setCodGasto(String)}
     *   <li>{@link ParametroAlerta#setCodMotivo(String)}
     *   <li>{@link ParametroAlerta#setCriticidad(String)}
     *   <li>{@link ParametroAlerta#setDesGasto(String)}
     *   <li>{@link ParametroAlerta#setDesMotivo(String)}
     *   <li>{@link ParametroAlerta#setEstado(String)}
     *   <li>{@link ParametroAlerta#setImpCant(String)}
     *   <li>{@link ParametroAlerta#setMontCant(String)}
     *   <li>{@link ParametroAlerta#setMotivo(String)}
     *   <li>{@link ParametroAlerta#setNivelMax(String)}
     *   <li>{@link ParametroAlerta#setNivelMin(String)}
     *   <li>{@link ParametroAlerta#setPeriodo(String)}
     *   <li>{@link ParametroAlerta#setRend(String)}
     *   <li>{@link ParametroAlerta#setTimeStamp(String)}
     *   <li>{@link ParametroAlerta#setTxAviso(String)}
     *   <li>{@link ParametroAlerta#getCodGasto()}
     *   <li>{@link ParametroAlerta#getCodMotivo()}
     *   <li>{@link ParametroAlerta#getCriticidad()}
     *   <li>{@link ParametroAlerta#getDesGasto()}
     *   <li>{@link ParametroAlerta#getDesMotivo()}
     *   <li>{@link ParametroAlerta#getEstado()}
     *   <li>{@link ParametroAlerta#getImpCant()}
     *   <li>{@link ParametroAlerta#getMontCant()}
     *   <li>{@link ParametroAlerta#getMotivo()}
     *   <li>{@link ParametroAlerta#getNivelMax()}
     *   <li>{@link ParametroAlerta#getNivelMin()}
     *   <li>{@link ParametroAlerta#getPeriodo()}
     *   <li>{@link ParametroAlerta#getRend()}
     *   <li>{@link ParametroAlerta#getTimeStamp()}
     *   <li>{@link ParametroAlerta#getTxAviso()}
     * </ul>
     */
    @Test
    void testConstructor() {
        ParametroAlerta actualParametroAlerta = new ParametroAlerta();
        actualParametroAlerta.setCodGasto("alice.liddell@example.org");
        actualParametroAlerta.setCodMotivo("Cod Motivo");
        actualParametroAlerta.setCriticidad("Criticidad");
        actualParametroAlerta.setDesGasto("alice.liddell@example.org");
        actualParametroAlerta.setDesMotivo("Des Motivo");
        actualParametroAlerta.setEstado("Estado");
        actualParametroAlerta.setImpCant("Imp Cant");
        actualParametroAlerta.setMontCant("Mont Cant");
        actualParametroAlerta.setMotivo("Motivo");
        actualParametroAlerta.setNivelMax("Nivel Max");
        actualParametroAlerta.setNivelMin("Nivel Min");
        actualParametroAlerta.setPeriodo("Periodo");
        actualParametroAlerta.setRend("Rend");
        actualParametroAlerta.setTimeStamp("Time Stamp");
        actualParametroAlerta.setTxAviso("Tx Aviso");
        assertEquals("alice.liddell@example.org", actualParametroAlerta.getCodGasto());
        assertEquals("Cod Motivo", actualParametroAlerta.getCodMotivo());
        assertEquals("Criticidad", actualParametroAlerta.getCriticidad());
        assertEquals("alice.liddell@example.org", actualParametroAlerta.getDesGasto());
        assertEquals("Des Motivo", actualParametroAlerta.getDesMotivo());
        assertEquals("Estado", actualParametroAlerta.getEstado());
        assertEquals("Imp Cant", actualParametroAlerta.getImpCant());
        assertEquals("Mont Cant", actualParametroAlerta.getMontCant());
        assertEquals("Motivo", actualParametroAlerta.getMotivo());
        assertEquals("Nivel Max", actualParametroAlerta.getNivelMax());
        assertEquals("Nivel Min", actualParametroAlerta.getNivelMin());
        assertEquals("Periodo", actualParametroAlerta.getPeriodo());
        assertEquals("Rend", actualParametroAlerta.getRend());
        assertEquals("Time Stamp", actualParametroAlerta.getTimeStamp());
        assertEquals("Tx Aviso", actualParametroAlerta.getTxAviso());
    }

}