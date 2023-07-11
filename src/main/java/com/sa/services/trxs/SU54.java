package com.sa.services.trxs;

import java.util.Map;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU54 extends Transaction {
	String idRendicion = null;

	public SU54() {
		this.PARAMETER_TRX = "SUM_ABM_RENDICIONES";
		this.CURRENT_TRX = "SU54";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		 this.idRendicion = (String) parametersExecute.get("id_rendicion");
	}
	
	public Object getDataReturn() {
		return this.idRendicion;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		parametersExecute.put("id_rendicion", "1");
	}
}
