package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.sa.entities.ComboGenerico;
import com.sa.entities.DatosPantallaDinamica;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import ar.org.bbva.util.DateUtils;

public class SU59 extends Transaction {
	private HashSet<String> headers = new HashSet<String>();
	private Map<String, String> mapCod1 = new HashMap<String, String>();
	private Map<String, String> mapCod2 = new HashMap<String, String>();
	private List<List<String>> filas = new ArrayList<List<String>>();
	
	public SU59(List<DatosPantallaDinamica> fieldsScreen) {
		this.PARAMETER_TRX = "SUM_CONS_DET_OBLIGATORIOS";
		this.CURRENT_TRX = "SU59";
		
		for (DatosPantallaDinamica dato : fieldsScreen) {
			this.headers.add(dato.getTipoCampo());
			
			if(dato.getTipoCampo().equals("COD1"))
				for (ComboGenerico opcion : dato.getOpcionesCombo()) {
					mapCod1.put(opcion.getId(), opcion.getDescripcion());
				}
			
			if(dato.getTipoCampo().equals("COD2"))
				for (ComboGenerico opcion : dato.getOpcionesCombo()) {
					mapCod2.put(opcion.getId(), opcion.getDescripcion());
				}
		}
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
	
	
	void addColumns(List<String> columnas, String str ) {
		
		if (this.headers.contains("COD1")) {
			String cod1 = str.substring(9, 14);
			String descCod1 = mapCod1.get(cod1);
			columnas.add(descCod1 == null ? "COD1=" +  cod1 : "COD1=" +  descCod1);
		}
		
		if (this.headers.contains("COD2")) {
			String cod2 = str.substring(14, 19);
			String descCod2 = mapCod2.get(cod2);
			columnas.add(descCod2 == null ? "COD2=" + cod2 : "COD2=" + descCod2);
		}
		
		if (this.headers.contains("TXT1")) {
			columnas.add("TXT1=" + str.substring(19, 69));}
		
		if (this.headers.contains("TXT2")) {
			columnas.add("TXT2=" + str.substring(69, 119));}
		
		if (this.headers.contains("NUM1")) {
			columnas.add("NUM1=" + str.substring(119, 128));}
		
		if (this.headers.contains("NUM2")) {
			columnas.add("NUM2=" + str.substring(128, 137));}
	}
	
	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		log.info("Mapeo SU59");

		List list = parametersExecute.get("lista") != null ? (List) parametersExecute.get("lista") : new ArrayList(); 

		for (Object obj : list) {
			String str = getStrLista(obj);
			List<String> columnas = new ArrayList<>();

			columnas.add("IDOBS=" + str.substring(0, 9).replaceFirst("^0*", ""));
			
			addColumns(columnas,str);
			
			if (this.headers.contains("FEC1"))
				try {
					columnas.add("FEC1=" + DateUtils.formatearFecha(str.substring(137, 147), DateUtils.dfYYYYMMDD, DateUtils.dfDDMMYYYY));
				} catch (Exception e) {
					columnas.add("FEC1=" + str.substring(137, 147));
					log.error("Error procesando fecha", e);

				}

			if (this.headers.contains("FEC2"))
				try {
					columnas.add("FEC2=" + DateUtils.formatearFecha(str.substring(147, 157), DateUtils.dfYYYYMMDD, DateUtils.dfDDMMYYYY));
				} catch (Exception e) {
					columnas.add("FEC2=" + str.substring(147, 157));
					log.error("Error procesando fecha", e);
				}

			if (this.headers.contains("TXT250"))
				columnas.add("TXT250=" + str.substring(157));
			
			this.filas.add(columnas);
		}
	}

	@Override
	public List getDataReturnList() {
		return filas;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		//metodo eliminado
	}
}
