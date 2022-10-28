package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.ComboComprobante;
import com.sa.entities.ComboGasto;
import com.sa.entities.ComboMoneda;
import com.sa.entities.ComboMotivo;
import com.sa.entities.ComboOpcion2;
import com.sa.services.Transaction;

public class SU51 extends Transaction {
	private static final Log log = LogFactory.getLog(SU51.class);
	public List<ComboOpcion2> listaOpcion2 = new ArrayList<ComboOpcion2>();
	public List<ComboMotivo> listaMotivo = new ArrayList<ComboMotivo>();
	public List<ComboMoneda> listaMoneda = new ArrayList<ComboMoneda>();
	public List<ComboComprobante> listaComprobante = new ArrayList<ComboComprobante>();
	public List<ComboGasto> listaTipoGasto = new ArrayList<ComboGasto>();
	/**
	 * OPCION DELIM 03 - CLAVES DELIM 04 - CANTTAB DELIM 05 - CODMOT DELIM 06 -
	 * CODGAS DELIM 07
	 * 
	 */
	private final String[] FIELDS_INPUT = new String[] { "opcion", "claves_cons", "cant_tablas", "cod_motivo", "cod_gasto" };

	public SU51() {
		this.PARAMETER_TRX = "SUM_CONS_PARAMETROS";
		this.CURRENT_TRX = "SU51";
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
		Map parametersExecute = new HashMap<Object, Object>();

		for (int i = 0; i < parameters.length; i++) {
			parametersExecute.put(FIELDS_INPUT[i], parameters[i]);
		}
		return parametersExecute;
	}

	@Override
	protected void mapData(Map parametersExecute) {
		List lista = (List) parametersExecute.get("lista");

		Integer opcion = Integer.valueOf((String) parametersExecute.get("opcion"));
		switch (opcion) {
		case 1:
			break;
		case 2:
			for (Object object : lista) {
				BasicDynaBean bean = (BasicDynaBean) object;
				String str = (String) bean.get("lista");
				ComboOpcion2 comboOpcion2 = new ComboOpcion2();
				comboOpcion2.setId(str.substring(10, 14));
				comboOpcion2.setDescripcion(str.substring(14, str.length()));
				if (str.substring(10, 14).trim().equals("ARS") || str.substring(10, 14).trim().equals("EUR")
						|| str.substring(10, 14).trim().equals("USD")) {
					comboOpcion2.setDescripcion(str.substring(10, 14));
				}
				listaOpcion2.add(comboOpcion2);
			}
			this.dataReturnList = listaOpcion2;
			break;
		case 3:
			for (Object object : lista) {
				BasicDynaBean bean = (BasicDynaBean) object;
				String str = (String) bean.get("lista");
				ComboGasto comboTipoGasto = new ComboGasto();
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
			this.dataReturnList = listaTipoGasto;

			break;
		case 4:
		case 8:			
		case 9:
			if (lista != null) {
				for (Object object : lista) {
					BasicDynaBean bean = (BasicDynaBean) object;
					String str = (String) bean.get("lista");
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
			for (Object object : lista) {
				BasicDynaBean bean = (BasicDynaBean) object;
				String str = (String) bean.get("lista");
				ComboMotivo comboMotivo = new ComboMotivo();
				comboMotivo.setId(str.substring(0, 4));
				comboMotivo.setDescripcion(comboMotivo.getId() + "-" + (str.substring(4, str.length())));
				listaMotivo.add(comboMotivo);
			}
			this.dataReturnList = listaMotivo;
			
			break;
		}
	}
}