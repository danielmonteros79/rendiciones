package com.sa.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.ThObtenerDocs;
import com.sa.services.trxs.ThPublicarDoc;

import ar.com.bbva.web.impl.SAMWebClient;

@SuppressWarnings("unchecked")
public class ThubanService {
	private static final Log log = LogFactory.getLog(ThubanService.class);

	private String msg;
	private SAMWebClient samClient;

	public ThubanService(SAMWebClient samClient) {
		this.samClient = samClient;
	}

	public List<String> publicarDocumentos(String claseDoc, String usuarioThuban, String claveThuban, Rendicion rendicion, List<Archivo> files) {
		List<String> errores = new ArrayList<String>();

		for (Archivo file : files) {
			try {
				Map<String, Object> parametersExecute = new HashMap<String, Object>();
				parametersExecute.put("Usuario", usuarioThuban);
				parametersExecute.put("Clave", claveThuban);
				parametersExecute.put("ClaseDocumental", claseDoc);
				parametersExecute.put("NombreArchivo", file.getNomArchivo());
				parametersExecute.put("ListaCampos", "N_DOC=" + rendicion.getId());
				//parametersExecute.put("Documento", file.getBase64());
				this.publicarDoc(parametersExecute);
			} catch (Exception e) {
				log.error("", e);
				errores.add("<b>" + file.getNomArchivo() + ":</b><br>" + e.toString());
			}
		}

		return errores;
	}

	public String publicarDoc(Map<String, Object> parametersExecute) throws Exception {
		ManagerTransaction manager = new ManagerTransaction(new ThPublicarDoc());
		manager.executeTrx(this.samClient, parametersExecute);

		String idThuban = (String) manager.getDataReturn();
		msg = (String) manager.getMensajeAviso();

		return idThuban;
	}
	
	public List<Archivo> obtenerArchivos(String claseDoc, String usuarioThuban, String claveThuban, String idRendicion) throws Exception {
		ManagerTransaction manager = new ManagerTransaction(new ThObtenerDocs());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		parametersExecute.put("Usuario", usuarioThuban);
		parametersExecute.put("Clave", claveThuban);
		parametersExecute.put("ClaseDocumental", claseDoc);
		parametersExecute.put("idRendicion", String.format("%016d", Integer.parseInt(idRendicion)));
		manager.executeTrx(this.samClient, parametersExecute);

		List<Archivo> archivos = (List<Archivo>) manager.getDataReturnList();
		msg = (String) manager.getMensajeAviso();
		
		return archivos;
	}

	public String getMsg() {
		return msg;
	}
}
