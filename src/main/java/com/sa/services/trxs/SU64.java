package com.sa.services.trxs;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.services.Transaction;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class SU64 extends Transaction {

    private static final Log log = LogFactory.getLog(SU64.class);

    public SU64() {
        this.PARAMETER_TRX = "SUM_GEN_ORDENPAGO";
        this.CURRENT_TRX = "SU64";
    }

    @Override
    public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
        try {
            execute(client, this.PARAMETER_TRX, parametersExecute);
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
    }
}
