package com.sa.services.trxs;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU58 extends Transaction {

	private final String[] FIELDS_INPUT = new String[]{};  
	
	public SU58() {
		// TODO Auto-generated constructor stub
		this.PARAMETER_TRX = "SUM_ABM_DET_OBLIGATORIOS";
		this.CURRENT_TRX = "SU58";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
		// TODO Auto-generated method stub

		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);

			// Mapear los datos

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TransactionException(e);
		}

	}

	@Override
	public void executeTrx(IWebClient client, String... parameters) throws TransactionException {
		// TODO Auto-generated method stub
		try {
			
			execute(client, this.PARAMETER_TRX, this.mapInputParams(parameters));

			// Mapear los datos

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TransactionException(e);
		}
	}
	
	@Override
	protected Map mapInputParams(String... parameters) {
		// TODO Auto-generated method stub
		Map parametersExecute = new HashMap<Object, Object>();
		
		for (int i = 0; i < parameters.length; i++) {
			parametersExecute.put(FIELDS_INPUT[i], parameters[i]);
		}
		return parametersExecute;
	}
	@Override
	protected void mapData(Map parametersExecute) {
		log.info("Mapeo SU58");

		// TODO Auto-generated method stub

	}
}
