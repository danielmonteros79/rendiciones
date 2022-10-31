package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.CuadroGeneral;
import com.sa.services.Transaction;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaBean;

@SuppressWarnings("rawtypes")
public class SU71 extends Transaction {

    public SU71() {
        this.PARAMETER_TRX = "SUM_REPORTERIA_CUADRO_GENERAL";
        this.CURRENT_TRX = "SU71";
    }

    @Override
    public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
        try {
            execute(client, this.PARAMETER_TRX, parametersExecute);
            mapData(parametersExecute);
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

    @SuppressWarnings("unchecked")
    @Override
    protected void mapData(Map parametersExecute) {
        for (Object object : (List) parametersExecute.get("lista")) {
            try {
                String str = (String) ((BasicDynaBean) object).get("lista");
                CuadroGeneral cg = new CuadroGeneral();
                cg.setCodEstado(str.substring(0, 5));
                cg.setEstado(str.substring(5, 35));
                cg.setCantRend(str.substring(35, 45));
                cg.setMontoTotal(str.substring(45, 60));
                cg.setCons(str.substring(60, 61));
                cg.setCodMotivo((String) parametersExecute.get("cod_mot_sel"));
                cg.setCodGlg((String) parametersExecute.get("cod_glg_sel"));

                dataReturnList.add(cg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
