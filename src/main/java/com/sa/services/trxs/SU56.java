package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.services.Transaction;
import java.util.HashMap;
import java.util.Map;

public class SU56 extends Transaction {

    public final static String OPCION_MODIFICAR = "MODI";

    private final String[] FIELDS_INPUT = new String[]{};
    Integer idGasto = null;

    public SU56() {
        // TODO Auto-generated constructor stub
        this.PARAMETER_TRX = "SUM_ABM_DET_GASTOS_REND";
        this.CURRENT_TRX = "SU56";
    }

    @Override
    public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
        // TODO Auto-generated method stub

        try {
            // parametersExecute.put("id_gasto", 4);
            // parametersExecute.get("id_gasto");
            execute(client, this.PARAMETER_TRX, parametersExecute);
            mapData(parametersExecute);
            // throw new TransactionException();
            // Mapear los datos
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new TransactionException(e);
        }

    }

    @Override
    public void executeTrx(IWebClient client, String... parameters) throws TransactionException {
        // TODO Auto-generated method stub
        try {

            execute(client, this.PARAMETER_TRX, this.mapInputParams(parameters));

            // Mapear los datos
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new TransactionException(e);
        }
    }

    @Override
    protected Map mapInputParams(String... parameters) {
        // TODO Auto-generated method stub
        Map parametersExecute = new HashMap<Object, Object>();

        for (int i = 0; i < parameters.length; i++) {
            parametersExecute.put(FIELDS_INPUT[i], parameters[i]);
        }
        return parametersExecute;
    }

    @Override
    protected void mapData(Map parametersExecute) {
        // TODO Auto-generated method stub
        log.info("Mapeo SU56");

        this.idGasto = Integer.valueOf((String) parametersExecute.get("id_gasto"));

    }

    public Object getDataReturn() {
        return this.idGasto;
    }
}
