package com.sa.services.trxs;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class ThPublicarDoc extends Transaction {
	private static final Log log = LogFactory.getLog(ThPublicarDoc.class);
	String idThuban = null;

	public ThPublicarDoc() {
		this.PARAMETER_TRX = "TH_PUBLICARDOC";
		this.CURRENT_TRX = "TH_PUBLICARDOC";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute, this.CONECTOR_SOA_THUBAN);
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		this.idThuban = (String) parametersExecute.get("IdThuban");
	}

	public Object getDataReturn() {
		return this.idThuban;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
	}
}
