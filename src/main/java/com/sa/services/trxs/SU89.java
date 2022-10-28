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
	private List<ParametroAlerta> parametroAviso = new ArrayList<ParametroAlerta>();

	public SU89() {
		this.PARAMETER_TRX = "SUM_ABM_ALERTAS";
		this.CURRENT_TRX = "SU_ALERTA";
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
	}

	@Override
	protected Map mapInputParams(String... parameters) {
		return null;
	}
	
	@Override
	protected void mapData(Map parametersExecute) {
//		List<String> lista = new ArrayList<String>();
//		lista.add("4SPSUPodFidf2345678654654654654564654a65sr33");
//		parametersExecute.put("lista", lista);
//		for (Object object : (List) parametersExecute.get("lista")) {
//			try {
	//			String str = (String) ((BasicDynaBean) object).get("lista");
//				String str = (String) object;
//				ParametroAlerta aviso = new ParametroAlerta();
//				int i = 0;
//				aviso.setCodMotivo(str.substring(i, i += 4));
//				aviso.setDesMotivo(str.substring(i, i += 50));
//				aviso.setCodGasto(str.substring(i, i += 4));
//				aviso.setDesGasto(str.substring(i, i += 50));
//				aviso.setMontCant(str.substring(i, i += 1));
//				aviso.setRend(str.substring(i, i += 4));
//				aviso.setPeriodo(str.substring(i, i += 2));
//				aviso.setNivelMin(str.substring(i, i += 2));
//				aviso.setNivelMax(str.substring(i, i += 2));
//				aviso.setEstado(str.substring(i, i += 1));
//				aviso.setTimeStamp(str.substring(i, i += 26));
//				this.parametroAviso.add(aviso);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	@Override
	public List getDataReturnList() {
		return parametroAviso;
	}
}