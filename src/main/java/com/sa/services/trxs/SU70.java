package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sa.entities.Journal;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU70 extends Transaction {
	public List<Journal> journal = new ArrayList<Journal>();

	public SU70() {
		this.PARAMETER_TRX = "SUM_CONS_JOURNAL";
		this.CURRENT_TRX = "SU70";
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

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		if (parametersExecute.get("lista") != null) {
			for (Object obj : (List) parametersExecute.get("lista")) {
				String str = getStrLista(obj);
				
				if (str.length() < 131)
					str = str + ("                                                                                                       ");
				Journal jour = new Journal();
				int i = 0;
				jour.setNumeroAprob(str.substring(i, i += 3));
				jour.setEstado(str.substring(i, i += 50));
				jour.setUsuarioProx(str.substring(i, i += 8));
				jour.setNombreUsuarioProx(str.substring(i, i += 30));
				jour.setNivel(str.substring(i, i += 2));
				jour.setUsuarioAprob(str.substring(i, i += 8));
				jour.setNombreUsuarioAprob(str.substring(i, i += 30));
				jour.setFechaApr(str.substring(i, i += 10));

				this.journal.add(jour);
			}
		}
	}

	@Override
	public List getDataReturnList() {
		return journal;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		List<String> retList = new ArrayList<String>();
		
		retList.add("001ESTADO 1                                          A103557 NOMBRE USUARIO PROXIMO        01A103558 NOMBRE USUARIO APROBADOR      2018-05-27");
		
		parametersExecute.put("lista", retList);
	}
}