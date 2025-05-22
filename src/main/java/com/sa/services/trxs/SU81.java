package com.sa.services.trxs;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.Usuario;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU81 extends Transaction {
	private static final Log log = LogFactory.getLog(SU81.class);
	private Usuario usuarioCheck = null;

	public SU81() {
		this.PARAMETER_TRX = "SUM_ABM_PARAMS_DELEGACIONES";
		this.CURRENT_TRX = "SU81";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute)
			throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			
			if (parametersExecute.get("opcion").equals("CONS"))
				mapData(parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		this.usuarioCheck = new Usuario((String) parametersExecute.get("id_reemplazo"), "",
			(String) parametersExecute.get("nombre"), Integer.valueOf((String) parametersExecute.get("centro_costo")),
			(String) parametersExecute.get("sector"), null);
	}

	@Override
	public Object getDataReturn() {
		return this.usuarioCheck;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		if (parametersExecute.get("opcion").equals("CONS")) {
			if (parametersExecute.get("id_reemplazo").equals("A126661")) {
				parametersExecute.put("sector", "8568");
				parametersExecute.put("centro_costo", "0099");
				parametersExecute.put("nombre", "RED DEVIL, ANALIA LAURA");
			} else {
				throw new Exception("ERROR: USUARIO INEXISTENTE:SUE0011");}
		}
	}

}