package com.sa.manager.parametros;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.parametros.ParametroMotivo;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU82;
import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("unchecked")
public class ParametrosMotivoManager extends ManagerTransaction {

    private static final Log log = LogFactory.getLog(ParametrosMotivoManager.class);
    private SU82 trx;

    public ParametrosMotivoManager(SU82 trx) {
        super(trx);
        this.trx = trx;
    }

    public ParametroMotivo getMotivoPorCodigo(IWebClient client, String codMotivo, String user)
            throws TransactionException {

        log.info("Consultando motivo " + codMotivo + " para usuario " + user);
        Map<String, Object> params = new HashMap<>();
        params.put("cod_motivo", codMotivo);
        params.put("cod_user", user);
        params.put("nro_pagina", "1");

        try {
            this.executeTrx(client, params);
            List<ParametroMotivo> motivos = this.trx.getDataReturnList();

            if (motivos == null || motivos.isEmpty()) {
                throw new TransactionException("No se encontró información para el motivo seleccionado.");
            }

            return motivos.get(0);

        } catch (TransactionException e) {
            log.error("Error en la transacción SU82 al consultar motivo: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al consultar motivo: " + e.getMessage(), e);
            throw new TransactionException("Error inesperado al consultar motivo: " + e.getMessage(), e);
        }
    }
}
