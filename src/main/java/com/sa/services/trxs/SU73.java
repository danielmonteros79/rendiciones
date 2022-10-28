package com.sa.services.trxs;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.ComboOpcion;
import com.sa.services.Transaction;

@SuppressWarnings("rawtypes")
public class SU73 extends Transaction {
	private static final Log log = LogFactory.getLog(SU73.class);

	public SU73() {
		this.PARAMETER_TRX = "SUM_ARMADO_COMBO_GLG";
		this.CURRENT_TRX = "SU73";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			try {
				mapData(parametersExecute);
			} catch (Exception e) {
				log.error(e);
				throw new TransactionException("Error de mapeo " + this.CURRENT_TRX);
			}
 		} catch (Exception e) {
			log.error(e);
			throw new TransactionException(e);
		}
	}

	@Override
	public void executeTrx(IWebClient client, String... parameters) throws TransactionException {
	}

	@Override
	protected Map mapInputParams(String... parameters) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void mapData(Map parametersExecute) {
		for (Object object : (List) parametersExecute.get("lista")) {
			String str = (String) ((BasicDynaBean) object).get("lista");
			this.dataReturnList.add(new ComboOpcion(str));
		}
	}
}