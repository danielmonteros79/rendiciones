package com.sa.services.trxs;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;

import com.sa.services.Transaction;

import ar.com.bbva.soa.conectores.BbvaSoaMensaje;
import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU62 extends Transaction {

	private final String[] FIELDS_INPUT = new String[]{};  
	
	public SU62() {
		this.PARAMETER_TRX = "SUM_APROB_RENDICIONES";
		this.CURRENT_TRX = "SU62";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			mapData(parametersExecute);
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
		log.info("Mapeo SU62");
		String aviso = getAviso();
		System.out.println(aviso);
		this.dataReturn = aviso;
	}
}