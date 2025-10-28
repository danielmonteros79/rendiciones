package com.sa.util;

import com.sa.entities.parametros.ParametroMotivo;
import ar.com.itrsa.sam.TransactionException;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class ValidadorMotivo {

    private static final SimpleDateFormat DF = new SimpleDateFormat("dd/MM/yyyy");

    private ValidadorMotivo() {}

    public static void validarVigencia(ParametroMotivo motivo, Date fechaDesde, Date fechaHasta)
            throws TransactionException {

        if (motivo == null) {
            throw new TransactionException("No se encontró información del motivo seleccionado.");
        }

        if (motivo.getEstado() != null && !"A".equalsIgnoreCase(motivo.getEstado())) {
            throw new TransactionException("El motivo " + motivo.getCodigo() + " no se encuentra activo.");
        }

        Date vigDesde = motivo.getFechaDesde();
        Date vigHasta = motivo.getFechaHasta();

        if (fechaDesde != null && fechaHasta != null && fechaDesde.after(fechaHasta)) {
            throw new TransactionException("La fecha Desde no puede ser posterior a la fecha Hasta.");
        }

        if (vigDesde != null && fechaDesde != null && fechaDesde.before(vigDesde)) {
            throw new TransactionException("La fecha Desde (" + DF.format(fechaDesde)
                    + ") es anterior al inicio de vigencia del motivo (" + DF.format(vigDesde) + ").");
        }

        if (vigHasta != null && fechaHasta != null && fechaHasta.after(vigHasta)) {
            throw new TransactionException("La fecha Hasta (" + DF.format(fechaHasta)
                    + ") supera la fecha fin de vigencia del motivo (" + DF.format(vigHasta) + ").");
        }
    }
}
