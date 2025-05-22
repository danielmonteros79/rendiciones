package com.sa.services.trxs;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.parametros.ParametroAlerta;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU88 extends Transaction {
	private static final Log log = LogFactory.getLog(SU88.class);

	public SU88() {
		this.PARAMETER_TRX = "SUM_CONS_ALERTAS";
		this.CURRENT_TRX = "SU88";
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
		if (parametersExecute.get("tmstp") != null) {
			ParametroAlerta aviso = new ParametroAlerta();
			aviso.setCodMotivo((String) parametersExecute.get("cod_mot"));
			aviso.setCodGasto((String) parametersExecute.get("cod_gto"));
			aviso.setMontCant((String) parametersExecute.get("cod_mont_cant"));
			aviso.setRend((String) parametersExecute.get("cod_rendicion"));
			
			String impCant = (String) parametersExecute.get("valor");
//			if ("M".equals(aviso.getMontCant()))
//				impCant = impCant.substring(0, impCant.length() - 2) + "." + impCant.substring(impCant.length() - 2);
			aviso.setImpCant(impCant);
			
			aviso.setPeriodo((String) parametersExecute.get("cod_periodo"));
			aviso.setCriticidad((String) parametersExecute.get("cod_critico"));
			aviso.setNivelMin((String) parametersExecute.get("cod_nivel_min"));
			aviso.setNivelMax((String) parametersExecute.get("cod_nivel_max"));
			aviso.setTxAviso((String) parametersExecute.get("tx_aviso"));
			aviso.setEstado((String) parametersExecute.get("est_aviso"));
			aviso.setTimeStamp((String) parametersExecute.get("tmstp"));
			
			dataReturnList.add(aviso);
		} else {
			if (parametersExecute.get("lista") != null) {
				for (Object obj : (List) parametersExecute.get("lista")) {
					String str = getStrLista(obj);
					if (parametersExecute.get("opcion").equals("INDI")) {
						dataReturnList.add(str);
					} else if (parametersExecute.get("opcion").equals("LIST")) {
						ParametroAlerta aviso = new ParametroAlerta();
						int i = 0;
						aviso.setId(str.substring(i, i += 4));
						aviso.setCodMotivo(str.substring(i, i += 4));
						aviso.setCodGasto(str.substring(i, i += 4));
						aviso.setMontCant(str.substring(i, i += 1));
						aviso.setRend(str.substring(i, i += 4));
						aviso.setImpCant(str.substring(i, i += 16));
						aviso.setPeriodo(str.substring(i, i += 2));
						aviso.setCriticidad(str.substring(i, i += 1));
						aviso.setNivelMin(str.substring(i, i += 2));
						aviso.setNivelMax(str.substring(i, i += 2));
						aviso.setTxAviso(str.substring(i, i += 50));
						aviso.setEstado(str.substring(i, i += 1));
						aviso.setDesMotivo(str.substring(i, i += 50));
						aviso.setDesGasto(str.substring(i, i += 50));
						aviso.setLastElement(str.substring(str.length()-1));
						aviso.setTimeStamp("");
						
						dataReturnList.add(aviso);
					}
				}
			}
		}
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
	}
}