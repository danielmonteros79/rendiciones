package com.sa.services.trxs;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.ComboOpcion;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU73 extends Transaction {
	private static final Log log = LogFactory.getLog(SU73.class);

	public SU73() {
		this.PARAMETER_TRX = "SUM_ARMADO_COMBO_GLG";
		this.CURRENT_TRX = "SU73";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			try {
				mapData(parametersExecute);
			} catch (Exception e) {
				log.error("", e);
				throw new TransactionException("Error de mapeo " + this.CURRENT_TRX);
			}
 		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		for (Object obj : (List) parametersExecute.get("lista")) {
			String str = getStrLista(obj);
			this.dataReturnList.add(new ComboOpcion(str));
		}
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
	}
}