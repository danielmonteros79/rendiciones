package com.sa.manager;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.services.Transaction;
import java.util.List;
import java.util.Map;

public class ManagerTransaction {

    private Transaction trx;

    public ManagerTransaction(Object trx) {
        // TODO Auto-generated constructor stub
        this.trx = (Transaction) trx;

    }

    public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
        this.trx.executeTrx(client, parametersExecute);
    }

    public void executeTrx(IWebClient client, String... parameters) throws TransactionException {
        this.trx.executeTrx(client, parameters);
    }

    public Object getDataReturn() {
        return this.trx.getDataReturn();
    }

    public List getDataReturnList() {
        return this.trx.getDataReturnList();
    }

    public Object getMensajeAviso() {
        return this.trx.getAviso();
    }
}
