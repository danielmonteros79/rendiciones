package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.parametros.ParametroExceptuado;
import com.sa.services.Transaction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SU87 extends Transaction {

    private static final Log log = LogFactory.getLog(SU87.class);
    private List<ParametroExceptuado> parametroExceptuado = new ArrayList<ParametroExceptuado>();
    private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
    private String descripcion = null;

    public SU87() {
        this.PARAMETER_TRX = "SUM_ABM_EXCEPCIONES";
        this.CURRENT_TRX = "SU87";
    }

    @Override
    public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
        try {
            execute(client, this.PARAMETER_TRX, parametersExecute);
            mapData(parametersExecute);
        } catch (Exception e) {
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
        if ("CONS".equals(parametersExecute.get("opcion"))) {
            this.descripcion = (String) parametersExecute.get("descrip");
        }

    }

    public Object getDataReturn() {
        return this.descripcion;
    }

    @Override
    public List getDataReturnList() {
        return parametroExceptuado;
    }
}
