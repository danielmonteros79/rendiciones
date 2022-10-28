package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import ar.org.bbva.util.DumpUtils;

public class SU54 extends Transaction {
	HashMap parametersExecute2 = new HashMap();
	String idRendicion = null;
	private final String[] FIELDS_INPUT = new String[] {};

	public SU54() {
		// TODO Auto-generated constructor stub
		this.PARAMETER_TRX = "SUM_ABM_RENDICIONES";
		this.CURRENT_TRX = "SU54";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
		// TODO Auto-generated method stub

//		parametersExecute.put("id_rendicion", 5);
		 try {

	 execute(client, this.PARAMETER_TRX, parametersExecute);

		parametersExecute.get("id_rendicion");
		mapData(parametersExecute);

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
		// TODO Auto-generated method stub
		log.info("Mapeo SU54");
		 this.idRendicion = (String) parametersExecute.get("id_rendicion");


	}
	public Object getDataReturn() {
		return this.idRendicion;
	}
}
