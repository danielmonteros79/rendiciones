package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.parametros.ParametroAlerta;
import com.sa.services.Transaction;

public class SU89 extends Transaction {
	private static final Log log = LogFactory.getLog(SU89.class);
	private List<ParametroAlerta> parametroAviso = new ArrayList<>();

	public SU89() {
		this.PARAMETER_TRX = "SUM_ABM_ALERTAS";
		this.CURRENT_TRX = "SU_ALERTA";
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
		//metodo no utilizado
	}

	@Override
	public List getDataReturnList() {
		return parametroAviso;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		//metodo no utilizado
	}
}