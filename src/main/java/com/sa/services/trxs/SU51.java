package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.ComboComprobante;
import com.sa.entities.ComboGasto;
import com.sa.entities.ComboMoneda;
import com.sa.entities.ComboMotivo;
import com.sa.entities.ComboOpcion2;
import com.sa.services.Transaction;
import com.sa.util.ParamsConstants;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU51 extends Transaction {
	private static final Log log = LogFactory.getLog(SU51.class);
	private List<ComboOpcion2> listaOpcion2 = new ArrayList<>();
	private List<ComboMotivo> listaMotivo = new ArrayList<>();
	private List<ComboMoneda> listaMoneda = new ArrayList<>();
	private List<ComboComprobante> listaComprobante = new ArrayList<>();
	private List<ComboGasto> listaTipoGasto = new ArrayList<>();

	public SU51() {
		this.PARAMETER_TRX = "SUM_CONS_PARAMETROS";
		this.CURRENT_TRX = "SU51";
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

	private void addComboOpcion2(String str, ComboOpcion2 comboOpcion2  ) {
		comboOpcion2.setId(str.substring(10, 14));
		comboOpcion2.setDescripcion(str.substring(14, str.length()));
		if (str.substring(10, 14).trim().equals("ARS") || str.substring(10, 14).trim().equals("EUR")
				|| str.substring(10, 14).trim().equals("USD")) {
			comboOpcion2.setDescripcion(str.substring(10, 14));
		}
		listaOpcion2.add(comboOpcion2);
	}
	
	private void setListaTipoGasto(String str, ComboGasto comboTipoGasto ) {
		if (str.length() <= 54) {
			comboTipoGasto.setId(str.substring(0, str.length()));
			comboTipoGasto.setDescripcion(str.substring(4, str.length()));
		} else {
			if (str.substring(54, 59).equalsIgnoreCase("") || str.substring(54, 59).equalsIgnoreCase("00000")) {
				comboTipoGasto.setId(str.substring(0, str.length()) + "N");

			} else {
				comboTipoGasto.setId(str.substring(0, str.length()) + "S");
			}
			comboTipoGasto.setDescripcion(str.substring(4, 54));
			comboTipoGasto.setDetalle(str.substring(54, 59));
			comboTipoGasto.setDescOblig("S");
		}
		listaTipoGasto.add(comboTipoGasto);
	}
	
	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		List lista = (List) parametersExecute.get("lista");

		Integer opcion = Integer.valueOf((String) parametersExecute.get("opcion"));
		switch (opcion) {
		case 1:
			break;
		case 2:
			for (Object obj : lista) {
				String str = getStrLista(obj);
				ComboOpcion2 comboOpcion2 = new ComboOpcion2();
				addComboOpcion2(str,comboOpcion2);
			}
			this.dataReturnList = listaOpcion2;
			break;
		case 3:
			for (Object obj : lista) {
				String str = getStrLista(obj);
				ComboGasto comboTipoGasto = new ComboGasto();
				setListaTipoGasto(str, comboTipoGasto);
				
			}
			this.dataReturnList = listaTipoGasto;

			break;
		case 4:
		case 8:			
		case 9:
			if (lista != null) {
				for (Object obj : lista) {
					String str = getStrLista(obj);
					ComboMotivo comboMotivo = new ComboMotivo();
					comboMotivo.setId(str.substring(0, 4));
					comboMotivo.setDescripcion(comboMotivo.getId() + "-" + (str.substring(4, 54)));
					comboMotivo.setCostosDestino(str.substring(54, str.length()));
					listaMotivo.add(comboMotivo);
				}
			}
			this.dataReturnList = listaMotivo;

			break;
		case 5:
		case 6:
		case 7:
			for (Object obj : lista) {
				String str = getStrLista(obj);
				ComboMotivo comboMotivo = new ComboMotivo();
				comboMotivo.setId(str.substring(0, 4));
				comboMotivo.setDescripcion(comboMotivo.getId() + "-" + (str.substring(4, str.length())));
				listaMotivo.add(comboMotivo);
			}
			this.dataReturnList = listaMotivo;
			
			break;
		default :
		break;
		}
	}
	
	protected void hardcodear(Map<String, Object> parametersExecute) {
	// metodo no utilizado
	}
}
