package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Journal;
import com.sa.services.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaBean;

public class SU70 extends Transaction {

    public List<Journal> journal = new ArrayList<Journal>();

    public SU70() {
        this.PARAMETER_TRX = "SUM_CONS_JOURNAL";
        this.CURRENT_TRX = "SU70";
    }

    @Override
    public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
        try {
            execute(client, this.PARAMETER_TRX, parametersExecute);
            try {
                mapData(parametersExecute);
            } catch (Exception e) {
                log.error(e);
                throw new TransactionException("Error de mapeo " + this.CURRENT_TRX);
            }
        } catch (Exception e) {
            log.error(e);
            throw new TransactionException(e);
        }
    }

    @Override
    public void executeTrx(IWebClient client, String... parameters) throws TransactionException {
    }

    @Override
    protected Map mapInputParams(String... parameters) {
        return null;
    }

    @Override
    protected void mapData(Map parametersExecute) {
        if (parametersExecute.get("lista") != null) {
            for (Object object : (List) parametersExecute.get("lista")) {
                String str = (String) ((BasicDynaBean) object).get("lista");
                if (str.length() < 131) {
                    str = str + ("                                                                                                       ");
                }
                Journal jour = new Journal();
                int i = 0;
                jour.setNumeroAprob(str.substring(i, i += 3));
                jour.setEstado(str.substring(i, i += 50));
                jour.setUsuarioProx(str.substring(i, i += 8));
                jour.setNombreUsuarioProx(str.substring(i, i += 30));
                jour.setNivel(str.substring(i, i += 2));
                jour.setUsuarioAprob(str.substring(i, i += 8));
                jour.setNombreUsuarioAprob(str.substring(i, i += 30));
                jour.setFechaApr(str.substring(i, i += 10));

                this.journal.add(jour);
            }
        }
    }

    @Override
    public List getDataReturnList() {
        return journal;
    }
}
