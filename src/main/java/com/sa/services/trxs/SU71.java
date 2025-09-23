package com.sa.services.trxs;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.CuadroGeneral;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU71 extends Transaction {
	private static final Log log = LogFactory.getLog(SU71.class);

	public SU71() {
		this.PARAMETER_TRX = "SUM_REPORTERIA_CUADRO_GENERAL";
		this.CURRENT_TRX = "SU71";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		ejecutarTransaccion(client, this.PARAMETER_TRX, parametersExecute);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		for (Object obj : (List) parametersExecute.get("lista")) {
			try {
				String str = getStrLista(obj);
				CuadroGeneral cg = new CuadroGeneral();
				cg.setCodEstado(str.substring(0, 5));
				cg.setEstado(str.substring(5, 35));
				cg.setCantRend(str.substring(35, 45));
				cg.setMontoTotal(str.substring(45, 60));
				cg.setCons(str.substring(60, 61));
				cg.setCodMotivo((String)parametersExecute.get("cod_mot_sel"));
				cg.setCodGlg((String)parametersExecute.get("cod_glg_sel"));
				
				dataReturnList.add(cg);
			} catch (Exception e) {
				log.error("Error en mapData SU71", e);
			}
		}
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		//metodo no utilizado
	}
}