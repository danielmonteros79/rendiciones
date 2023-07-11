package com.sa.services.trxs;

import java.util.Map;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU58 extends Transaction {

	public SU58() {
		this.PARAMETER_TRX = "SUM_ABM_DET_OBLIGATORIOS";
		this.CURRENT_TRX = "SU58";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			 execute(client, this.PARAMETER_TRX, parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
	}
}
