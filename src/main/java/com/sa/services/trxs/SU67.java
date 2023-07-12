package com.sa.services.trxs;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU67 extends Transaction {
	private static final Log log = LogFactory.getLog(SU67.class);

	public SU67() {
		this.PARAMETER_TRX = "SUM_REDISTRIBUCION_GASTO";
		this.CURRENT_TRX = "SU67";
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