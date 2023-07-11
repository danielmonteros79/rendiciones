package com.sa.manager;

import java.util.List;
import java.util.Map;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class ManagerTransaction {

	private Transaction trx;

	public ManagerTransaction(Object trx) {
		this.trx = (Transaction) trx;

	}

	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		this.trx.executeTrx(client, parametersExecute);
	}
	

	
	public Object getDataReturn() {
		return this.trx.getDataReturn();
	}

	@SuppressWarnings("rawtypes")
	public List getDataReturnList() {
		return this.trx.getDataReturnList();
	}
	
	public Object getMensajeAviso() {
		return this.trx.getAviso();
//		return this.trx.getClass().getSimpleName() + " Mensaje return hardcode " + DateUtils.dfHHMMSS.format(new Date());
	}
}

