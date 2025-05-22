package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.Archivo;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class ThObtenerDocs extends Transaction {
	private static final Log log = LogFactory.getLog(ThObtenerDocs.class);
	private List<Archivo> archivos = new ArrayList<>();
	private static final String LISTA = "lista";

	public ThObtenerDocs() {
		this.PARAMETER_TRX = "TH_OBTENERDOCS";
		this.CURRENT_TRX = "TH_OBTENERDOCS";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
//			execute(client, this.PARAMETER_TRX, parametersExecute, this.CONECTOR_SOA_THUBAN);
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}
	
	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		if (parametersExecute.get(LISTA) != null) {
			for (Object obj : (List) parametersExecute.get(LISTA)) {
				String str = getStrLista(obj);
				Archivo archivo = new Archivo();
				
				archivo.setNomArchivo(str.substring(0, 50));
				archivo.setIdu(str.substring(50, 60));
				
				this.archivos.add(archivo);
			}
		}
	}

	@Override
	public List getDataReturnList() {
		return this.archivos;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		List<String> retList = new ArrayList<>();
		
		retList.add("NOMBRE ARCHIVO 1                                  A123456789");
		
		parametersExecute.put(LISTA, retList);
	}
}
