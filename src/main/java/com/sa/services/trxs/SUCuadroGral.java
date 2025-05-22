package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.CuadroGeneral;
import com.sa.entities.parametros.ParametroAlerta;
import com.sa.services.Transaction;

@SuppressWarnings("rawtypes")
public class SUCuadroGral extends Transaction {

	public SUCuadroGral() {
		this.PARAMETER_TRX = "SUM_CONS_ALERTAS";
		this.CURRENT_TRX = "SU88";
	}

	

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
//			execute(client, this.PARAMETER_TRX, parametersExecute);
			mapData(parametersExecute);
 		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) throws Exception {
		if (parametersExecute.get("tmstp") != null) {
//			ParametroAlerta aviso = new ParametroAlerta();
//			aviso.setCodMotivo((String) parametersExecute.get("cod_mot"));
//			aviso.setCodGasto((String) parametersExecute.get("cod_gto"));
//			aviso.setMontCant((String) parametersExecute.get("mont_can"));
//			aviso.setRend((String) parametersExecute.get("cod_rend"));
//			
//			String impCant = (String) parametersExecute.get("me_valor");
//			if ("M".equals(aviso.getMontCant()))
//				impCant = impCant.substring(0, impCant.length() - 2) + "." + impCant.substring(impCant.length() - 2);
//			aviso.setImpCant(impCant);
//			
//			aviso.setPeriodo((String) parametersExecute.get("periodo"));
//			aviso.setCriticidad((String) parametersExecute.get("critico"));
//			aviso.setNivelMin((String) parametersExecute.get("ni_min"));
//			aviso.setNivelMax((String) parametersExecute.get("ni_max"));
//			aviso.setTxAviso((String) parametersExecute.get("tx_aviso"));
//			aviso.setEstado((String) parametersExecute.get("est_aviso"));
//			aviso.setTimeStamp((String) parametersExecute.get("tmstp"));
//			dataReturnList.add(aviso);
//		} else {
//			for (Object object : (List) parametersExecute.get("lista")) {
				try {
//					String str = (String) ((BasicDynaBean) object).get("lista");
					if (parametersExecute.get("opcion").equals("CONS")) {
						CuadroGeneral datos = new CuadroGeneral();
						datos.setEstado("NO INGRESADO");
						datos.setCantRend("99999");
						datos.setMontoTotal("$99999");
						
						List<String> centrosCosto = new ArrayList<String>();
//						String vcccost = (String) parametersExecute.get("vcccost");
						String vcccost = "123456789";
						if (vcccost != null) {
							int index = 0;
							while (index < vcccost.length()) {
								centrosCosto.add(vcccost.substring(index, Math.min(index + 1, vcccost.length())));
							    index += 1;
							}
						}
						datos.setGlg(centrosCosto);
						
						dataReturnList.add(datos);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
		
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		// TODO Auto-generated method stub
		
	}
}