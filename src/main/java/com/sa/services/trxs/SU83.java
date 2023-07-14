package com.sa.services.trxs;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU83 extends Transaction {
	private static final Log log = LogFactory.getLog(SU83.class);

	public SU83() {
		this.PARAMETER_TRX = "SUM_ABM_PARAMS_MOTIVOS";
		this.CURRENT_TRX = "SU83";
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
		//metodo no utilizado
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		//metodo no utilizado
	}
}