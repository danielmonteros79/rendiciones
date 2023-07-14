package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.parametros.ParametroExceptuado;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU87 extends Transaction {
	private static final Log log = LogFactory.getLog(SU87.class);
	private List<ParametroExceptuado> parametroExceptuado = new ArrayList<>();
	private String descripcion = null;

	public SU87() {
		this.PARAMETER_TRX = "SUM_ABM_EXCEPCIONES";
		this.CURRENT_TRX = "SU87";
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
		if ("CONS".equals(parametersExecute.get("opcion")))
			this.descripcion = (String) parametersExecute.get("descrip");
	}

	public Object getDataReturn() {
		return this.descripcion;
	}

	@Override
	public List getDataReturnList() {
		return parametroExceptuado;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		//metodo no utilizado
	}
}