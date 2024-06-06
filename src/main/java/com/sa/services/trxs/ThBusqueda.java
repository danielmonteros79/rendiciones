package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.Archivo;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class ThBusqueda extends Transaction {
	private static final Log log = LogFactory.getLog(ThObtenerDocs.class);
	public List<Archivo> archivos = new ArrayList<Archivo>();

	public ThBusqueda() {
		this.PARAMETER_TRX = "TH_BUSQUEDA";
		this.CURRENT_TRX = "TH_BUSQUEDA";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute, this.CONECTOR_SOA_THUBAN);
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}
	
	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		if (parametersExecute.get("resultado") != null) {
			for (Object obj : (List) parametersExecute.get("resultado")) {
				
				// Object object;
				BasicDynaBean bean = (BasicDynaBean) obj;
				String strId = (String) bean.get("INDEX_ITEM_ID");
				String strNombre = (String) bean.get("D_ARCHIVO");
				Archivo archivo = new Archivo();
				
				archivo.setId(strId);
				archivo.setNomArchivo(strNombre);
				this.archivos.add(archivo);
			}
		}
	}

	public List getDataReturnList() {
		return this.archivos;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		List<String> retList = new ArrayList<String>();
		
		retList.add("NOMBRE ARCHIVO 1                                  A123456789");
		
		parametersExecute.put("lista", retList);
	}
}
