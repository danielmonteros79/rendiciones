package com.sa.services.trxs;

import java.util.Map;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU56 extends Transaction {
	public final static String OPCION_MODIFICAR = "MODI";
	Integer idGasto = null;

	public SU56() {
		this.PARAMETER_TRX = "SUM_ABM_DET_GASTOS_REND";
		this.CURRENT_TRX = "SU56";
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
		log.info("Mapeo SU56");

		this.idGasto = Integer.valueOf((String) parametersExecute.get("id_gasto"));
	}

	public Object getDataReturn() {
		return this.idGasto;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		parametersExecute.put("id_gasto", "1");
	}
}
