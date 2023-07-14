package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.OSCAR;
import com.sa.entities.parametros.ParametroGasto;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU85 extends Transaction {
	private static final Log log = LogFactory.getLog(SU85.class);
	public final static String OPCION_MODIFICAR = "MODI";
	public List<ParametroGasto> gastos = new ArrayList<>();
	ParametroGasto gastoReturn = new ParametroGasto();

	public SU85() {
		this.PARAMETER_TRX = "SUM_ABM_PARAMS_GASTOS";
		this.CURRENT_TRX = "SU85";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		if (parametersExecute.get("modo").equals("I")) {
			if (parametersExecute.get("opcion").equals("ALTA")) {
				for (Object obj : (List) parametersExecute.get("lista")) {
					try {
						String str = getStrLista(obj);
						this.getDataReturnList().add(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if(parametersExecute.get("opcion").equals("MODI")) {
				for (Object obj : (List) parametersExecute.get("lista")) {
					try {
						String str = getStrLista(obj);

						this.getDataReturnList().add(str);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				gastoReturn.setOscar(new OSCAR((String) parametersExecute.get("oscar")));
				gastoReturn.setAntiguedad(((String) parametersExecute.get("antig")));
				gastoReturn.setBimon((String) parametersExecute.get("bimon"));
				gastoReturn.setDescripcionGasto((String) parametersExecute.get("desc_gasto"));
				gastoReturn.setObserv((String) parametersExecute.get("observ"));
				gastoReturn.setEstado((String) parametersExecute.get("estado"));
				gastoReturn.setCcostos((String) parametersExecute.get("cent_costo"));
				gastoReturn.setComprob((String) parametersExecute.get("compte"));
				gastoReturn.setPlazoAprob((String) parametersExecute.get("plazo_ap"));
				gastoReturn.setRistra((String) parametersExecute.get("ristra"));
				gastoReturn.setMaInclExcl((String) parametersExecute.get("inc_excl"));
				gastoReturn.setNivelIngreso((String) parametersExecute.get("ni_ing"));
				
				List<String> centrosCosto = new ArrayList<String>();
				String vcccost = (String) parametersExecute.get("vcccost");
				if (vcccost != null) {
					int index = 0;
					while (index < vcccost.length()) {
						centrosCosto.add(vcccost.substring(index, Math.min(index + 4, vcccost.length())));
					    index += 4;
					}
				}
				gastoReturn.setCentrosCosto(centrosCosto);
				
			} else if(parametersExecute.get("opcion").equals("BAJA")) {
				gastoReturn.setOscar(new OSCAR((String) parametersExecute.get("oscar")));
				gastoReturn.setAntiguedad(((String) parametersExecute.get("antig")));
				gastoReturn.setBimon((String) parametersExecute.get("bimon"));
				gastoReturn.setDescripcionGasto((String) parametersExecute.get("desc_gasto"));
				gastoReturn.setObserv((String) parametersExecute.get("observ"));
				gastoReturn.setEstado((String) parametersExecute.get("estado"));
				gastoReturn.setCcostos((String) parametersExecute.get("cent_costo"));
				gastoReturn.setComprob((String) parametersExecute.get("compte"));
				gastoReturn.setPlazoAprob((String) parametersExecute.get("plazo_ap"));
				gastoReturn.setRistra((String) parametersExecute.get("ristra"));
				gastoReturn.setMaInclExcl((String) parametersExecute.get("inc_excl"));
				gastoReturn.setNivelIngreso((String) parametersExecute.get("ni_ing"));
				
				List<String> centrosCosto = new ArrayList<String>();
				String vcccost = (String) parametersExecute.get("vcccost");
				if (vcccost != null) {
					int index = 0;
					while (index < vcccost.length()) {
						centrosCosto.add(vcccost.substring(index, Math.min(index + 4, vcccost.length())));
					    index += 4;
					}
				}
				gastoReturn.setCentrosCosto(centrosCosto);
			}
		}
	}

	@Override
	public List getDataReturnList() {
		return gastos;
	}

	public Object getDataReturn() {
		return this.gastoReturn;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		//metodo no utilizado
	}
}
