package com.sa.entities.parametros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.sa.entities.OSCAR;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class ParametroGastoTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link ParametroGasto}
     *   <li>{@link ParametroGasto#setAntiguedad(String)}
     *   <li>{@link ParametroGasto#setAutoriz(String)}
     *   <li>{@link ParametroGasto#setBimon(String)}
     *   <li>{@link ParametroGasto#setCcostos(String)}
     *   <li>{@link ParametroGasto#setCentrosCosto(List)}
     *   <li>{@link ParametroGasto#setComboCCostos(String)}
     *   <li>{@link ParametroGasto#setComprob(String)}
     *   <li>{@link ParametroGasto#setDescripcionGasto(String)}
     *   <li>{@link ParametroGasto#setDescripcionMotivo(String)}
     *   <li>{@link ParametroGasto#setEstado(String)}
     *   <li>{@link ParametroGasto#setFeAlta(Date)}
     *   <li>{@link ParametroGasto#setFeBaja(Date)}
     *   <li>{@link ParametroGasto#setFeUltMod(Date)}
     *   <li>{@link ParametroGasto#setGasto(String)}
     *   <li>{@link ParametroGasto#setIdAntg(String)}
     *   <li>{@link ParametroGasto#setImpAviso(String)}
     *   <li>{@link ParametroGasto#setMaCtrlImp(String)}
     *   <li>{@link ParametroGasto#setMaInclExcl(String)}
     *   <li>{@link ParametroGasto#setMaMonto(String)}
     *   <li>{@link ParametroGasto#setMotivo(String)}
     *   <li>{@link ParametroGasto#setNivelIngreso(String)}
     *   <li>{@link ParametroGasto#setNroTerm(String)}
     *   <li>{@link ParametroGasto#setObserv(String)}
     *   <li>{@link ParametroGasto#setOscar(OSCAR)}
     *   <li>{@link ParametroGasto#setPlazoAprob(String)}
     *   <li>{@link ParametroGasto#setRistra(String)}
     *   <li>{@link ParametroGasto#setUsrAlta(String)}
     *   <li>{@link ParametroGasto#setUsrBaja(String)}
     *   <li>{@link ParametroGasto#setUsrUltMod(String)}
     *   <li>{@link ParametroGasto#getAntiguedad()}
     *   <li>{@link ParametroGasto#getAutoriz()}
     *   <li>{@link ParametroGasto#getBimon()}
     *   <li>{@link ParametroGasto#getCcostos()}
     *   <li>{@link ParametroGasto#getCentrosCosto()}
     *   <li>{@link ParametroGasto#getComboCCostos()}
     *   <li>{@link ParametroGasto#getComprob()}
     *   <li>{@link ParametroGasto#getDescripcionGasto()}
     *   <li>{@link ParametroGasto#getDescripcionMotivo()}
     *   <li>{@link ParametroGasto#getEstado()}
     *   <li>{@link ParametroGasto#getFeAlta()}
     *   <li>{@link ParametroGasto#getFeBaja()}
     *   <li>{@link ParametroGasto#getFeUltMod()}
     *   <li>{@link ParametroGasto#getGasto()}
     *   <li>{@link ParametroGasto#getIdAntg()}
     *   <li>{@link ParametroGasto#getImpAviso()}
     *   <li>{@link ParametroGasto#getMaCtrlImp()}
     *   <li>{@link ParametroGasto#getMaInclExcl()}
     *   <li>{@link ParametroGasto#getMaMonto()}
     *   <li>{@link ParametroGasto#getMotivo()}
     *   <li>{@link ParametroGasto#getNivelIngreso()}
     *   <li>{@link ParametroGasto#getNroTerm()}
     *   <li>{@link ParametroGasto#getObserv()}
     *   <li>{@link ParametroGasto#getOscar()}
     *   <li>{@link ParametroGasto#getPlazoAprob()}
     *   <li>{@link ParametroGasto#getRistra()}
     *   <li>{@link ParametroGasto#getUsrAlta()}
     *   <li>{@link ParametroGasto#getUsrBaja()}
     *   <li>{@link ParametroGasto#getUsrUltMod()}
     * </ul>
     */
    @Test
    void testConstructor() {
        ParametroGasto actualParametroGasto = new ParametroGasto();
        actualParametroGasto.setAntiguedad("Antiguedad");
        actualParametroGasto.setAutoriz("Autoriz");
        actualParametroGasto.setBimon("Bimon");
        actualParametroGasto.setCcostos("Ccostos");
        ArrayList<String> centrosCosto = new ArrayList<>();
        actualParametroGasto.setCentrosCosto(centrosCosto);
        actualParametroGasto.setComboCCostos("Combo CCostos");
        actualParametroGasto.setComprob("Comprob");
        actualParametroGasto.setDescripcionGasto("alice.liddell@example.org");
        actualParametroGasto.setDescripcionMotivo("Descripcion Motivo");
        actualParametroGasto.setEstado("Estado");
        Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualParametroGasto.setFeAlta(date);
        Date feBaja = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualParametroGasto.setFeBaja(feBaja);
        Date feUltMod = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualParametroGasto.setFeUltMod(feUltMod);
        actualParametroGasto.setGasto("alice.liddell@example.org");
        actualParametroGasto.setIdAntg("Id Antg");
        actualParametroGasto.setImpAviso("Imp Aviso");
        actualParametroGasto.setMaCtrlImp("Ma Ctrl Imp");
        actualParametroGasto.setMaInclExcl("Ma Incl Excl");
        actualParametroGasto.setMaMonto("alice.liddell@example.org");
        actualParametroGasto.setMotivo("Motivo");
        actualParametroGasto.setNivelIngreso("Nivel Ingreso");
        actualParametroGasto.setNroTerm("Nro Term");
        actualParametroGasto.setObserv("Observ");
        OSCAR oscar = new OSCAR("Oscar");
        actualParametroGasto.setOscar(oscar);
        actualParametroGasto.setPlazoAprob("Plazo Aprob");
        actualParametroGasto.setRistra("Ristra");
        actualParametroGasto.setUsrAlta("Usr Alta");
        actualParametroGasto.setUsrBaja("Usr Baja");
        actualParametroGasto.setUsrUltMod("Usr Ult Mod");
        assertEquals("Antiguedad", actualParametroGasto.getAntiguedad());
        assertEquals("Autoriz", actualParametroGasto.getAutoriz());
        assertEquals("Bimon", actualParametroGasto.getBimon());
        assertEquals("Ccostos", actualParametroGasto.getCcostos());
        assertSame(centrosCosto, actualParametroGasto.getCentrosCosto());
        assertEquals("Combo CCostos", actualParametroGasto.getComboCCostos());
        assertEquals("Comprob", actualParametroGasto.getComprob());
        assertEquals("alice.liddell@example.org", actualParametroGasto.getDescripcionGasto());
        assertEquals("Descripcion Motivo", actualParametroGasto.getDescripcionMotivo());
        assertEquals("Estado", actualParametroGasto.getEstado());
        assertSame(date, actualParametroGasto.getFeAlta());
        assertSame(feBaja, actualParametroGasto.getFeBaja());
        assertSame(feUltMod, actualParametroGasto.getFeUltMod());
        assertEquals("alice.liddell@example.org", actualParametroGasto.getGasto());
        assertEquals("Id Antg", actualParametroGasto.getIdAntg());
        assertEquals("Imp Aviso", actualParametroGasto.getImpAviso());
        assertEquals("Ma Ctrl Imp", actualParametroGasto.getMaCtrlImp());
        assertEquals("Ma Incl Excl", actualParametroGasto.getMaInclExcl());
        assertEquals("alice.liddell@example.org", actualParametroGasto.getMaMonto());
        assertEquals("Motivo", actualParametroGasto.getMotivo());
        assertEquals("Nivel Ingreso", actualParametroGasto.getNivelIngreso());
        assertEquals("Nro Term", actualParametroGasto.getNroTerm());
        assertEquals("Observ", actualParametroGasto.getObserv());
        assertSame(oscar, actualParametroGasto.getOscar());
        assertEquals("Plazo Aprob", actualParametroGasto.getPlazoAprob());
        assertEquals("Ristra", actualParametroGasto.getRistra());
        assertEquals("Usr Alta", actualParametroGasto.getUsrAlta());
        assertEquals("Usr Baja", actualParametroGasto.getUsrBaja());
        assertEquals("Usr Ult Mod", actualParametroGasto.getUsrUltMod());
    }
}

