package com.sa.services.trxs;

import java.io.InputStream;
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
public class ThDescargaDoc extends Transaction {
	private static final Log log = LogFactory.getLog(ThObtenerDocs.class);
	public List<Archivo> archivos = new ArrayList<Archivo>();

	public ThDescargaDoc() {
		this.PARAMETER_TRX = "TH_DESCARGARDOC";
		this.CURRENT_TRX = "TH_DESCARGARDOC";
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
		
				Archivo archivo = new Archivo();
				archivo.setTipo((String) parametersExecute.get("ArchTipo"));
				archivo.setNomArchivo((String) parametersExecute.get("Nombre"));
				archivo.setBase64File((String) parametersExecute.get("Documento"));
				this.archivos.add(archivo);
	
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
