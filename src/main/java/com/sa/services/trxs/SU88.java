package com.sa.services.trxs;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.parametros.ParametroAlerta;
import com.sa.services.Transaction;

@SuppressWarnings("rawtypes")
public class SU88 extends Transaction {

	public SU88() {
		this.PARAMETER_TRX = "SUM_CONS_ALERTAS";
		this.CURRENT_TRX = "SU88";
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
		if (parametersExecute.get("tmstp") != null) {
			ParametroAlerta aviso = new ParametroAlerta();
			aviso.setCodMotivo((String) parametersExecute.get("cod_mot"));
			aviso.setCodGasto((String) parametersExecute.get("cod_gto"));
			aviso.setMontCant((String) parametersExecute.get("mont_can"));
			aviso.setRend((String) parametersExecute.get("cod_rend"));
			
			String impCant = (String) parametersExecute.get("me_valor");
			if ("M".equals(aviso.getMontCant()))
				impCant = impCant.substring(0, impCant.length() - 2) + "." + impCant.substring(impCant.length() - 2);
			aviso.setImpCant(impCant);
			
			aviso.setPeriodo((String) parametersExecute.get("periodo"));
			aviso.setCriticidad((String) parametersExecute.get("critico"));
			aviso.setNivelMin((String) parametersExecute.get("ni_min"));
			aviso.setNivelMax((String) parametersExecute.get("ni_max"));
			aviso.setTxAviso((String) parametersExecute.get("tx_aviso"));
			aviso.setEstado((String) parametersExecute.get("est_aviso"));
			aviso.setTimeStamp((String) parametersExecute.get("tmstp"));
			
			dataReturnList.add(aviso);
		} else {
			if (parametersExecute.get("lista") != null) {
				for (Object object : (List) parametersExecute.get("lista")) {
					String str = (String) ((BasicDynaBean) object).get("lista");
					if (parametersExecute.get("opcion").equals("FILT")) {
						dataReturnList.add(str);
					} else if (parametersExecute.get("opcion").equals("CONS")) {
						ParametroAlerta aviso = new ParametroAlerta();
						int i = 0;
						aviso.setCodMotivo(str.substring(i, i += 4));
						aviso.setDesMotivo(str.substring(i, i += 50));
						aviso.setCodGasto(str.substring(i, i += 4));
						aviso.setDesGasto(str.substring(i, i += 50));
						aviso.setMontCant(str.substring(i, i += 1));
						aviso.setRend(str.substring(i, i += 4));
						aviso.setPeriodo(str.substring(i, i += 2));
						aviso.setNivelMin(str.substring(i, i += 2));
						aviso.setNivelMax(str.substring(i, i += 2));
						aviso.setEstado(str.substring(i, i += 1));
						aviso.setTimeStamp(str.substring(i, i += 26));
						
						dataReturnList.add(aviso);
					}
				}
			}
		}
	}
}