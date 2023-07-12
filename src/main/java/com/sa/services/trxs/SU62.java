package com.sa.services.trxs;

import java.util.Map;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU62 extends Transaction {
	
	public SU62() {
		this.PARAMETER_TRX = "SUM_APROB_RENDICIONES";
		this.CURRENT_TRX = "SU62";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			this.setAviso("OPERACION EFECTUADA");
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}
	
	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		String aviso = getAviso();
		this.dataReturn = aviso;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
	}
}