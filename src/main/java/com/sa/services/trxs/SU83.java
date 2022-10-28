package com.sa.services.trxs;

import java.util.HashMap;
import java.util.Map;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.services.Transaction;

public class SU83 extends Transaction {
	private final String[] FIELDS_INPUT = new String[] {};

	public SU83() {
		this.PARAMETER_TRX = "SUM_ABM_PARAMS_MOTIVOS";
		this.CURRENT_TRX = "SU83";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
		} catch (Exception e) {
			throw new TransactionException(e);
		}
	}

	@Override
	public void executeTrx(IWebClient client, String... parameters) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, this.mapInputParams(parameters));
		} catch (Exception e) {
			throw new TransactionException(e);
		}
	}

	@Override
	protected Map mapInputParams(String... parameters) {
		Map parametersExecute = new HashMap<Object, Object>();

		for (int i = 0; i < parameters.length; i++) {
			parametersExecute.put(FIELDS_INPUT[i], parameters[i]);
		}

		return parametersExecute;
	}

	@Override
	protected void mapData(Map parametersExecute) {

	}
}