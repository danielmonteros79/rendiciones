package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.parametros.ParametroGasto;
import com.sa.services.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BasicDynaBean;

@SuppressWarnings("rawtypes")
public class SU84 extends Transaction {

    public List<ParametroGasto> gastos = new ArrayList<ParametroGasto>();

    public SU84() {
        this.PARAMETER_TRX = "SUM_CONS_PARAMS_GASTOS";
        this.CURRENT_TRX = "SU84";
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
        for (Object object : (List) parametersExecute.get("lista")) {
            String str = (String) ((BasicDynaBean) object).get("lista");
            ParametroGasto gasto = new ParametroGasto();
            int i = 0;
            gasto.setGasto(str.substring(i, i += 4));
            gasto.setDescripcionGasto(str.substring(i, i += 50));
            gasto.setMotivo(str.substring(i, i += 4));
            gasto.setDescripcionMotivo(str.substring(i, i += 50));
            gasto.setRistra(str.substring(i, i += 69));
            gasto.setBimon(str.substring(i, i += 1));
            gasto.setComprob(str.substring(i, i += 4));
            gasto.setAutoriz(str.substring(i, i += 2));
            gasto.setObserv(str.substring(i, i += 4));
            gasto.setEstado(str.substring(i, i += 1));

            this.gastos.add(gasto);
        }
    }

    @Override
    public List getDataReturnList() {
        return gastos;
    }
}
