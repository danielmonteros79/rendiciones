package com.sa.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.ThBusqueda;
import com.sa.services.trxs.ThDescargaDoc;
import com.sa.services.trxs.ThObtenerDocs;
import com.sa.services.trxs.ThPublicarDoc;
import org.apache.commons.text.StringEscapeUtils;

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
				String nombreArchivo = file.getNomArchivo().length() > 20 ? file.getNomArchivo().substring(0,20) : file.getNomArchivo();
				String fechaActual = obtenerFechaActual();
				Map<String, Object> parametersExecute = new HashMap<String, Object>();
				parametersExecute.put("Usuario", usuarioThuban);
				parametersExecute.put("Clave", claveThuban);
				parametersExecute.put("ClaseDocumental", claseDoc);
				parametersExecute.put("NombreArchivo", file.getNomArchivo());
				parametersExecute.put("Documento", file.getBase64File());
				parametersExecute.put("ListaCampos",
					"D_ARCHIVO=" + nombreArchivo + "|" 
					+"D_PREPARADOR=" + rendicion.getUsuarioRendicion() + "|" 
					+"D_DIGITALIZADOR=" + rendicion.getUsuarioRendicion() + "|"  
					+"N_SUCURSAL_DIG=" + rendicion.getCostosDestino() + "|"  
					+"N_FACTURA=" + rendicion.getId() + "|"  
					+"T_ORIGEN=X|" 
					+"E_ESTADO=VIGENTE|"
					+"F_EMISION=" + fechaActual + "|"
					+"F_FACTURA=" + fechaActual + "|"
					+"F_PAGO=" + fechaActual);
				 
				//System.out.println("BASE 64 FILE: " + file.getBase64File());
				//parametersExecute.put("Documento", file.getBase64());
				this.publicarDoc(parametersExecute);
			} catch (Exception e) {
				log.error("", e);
				errores.add("<b>" + StringEscapeUtils.escapeHtml4(file.getNomArchivo()) + ":</b><br>" + StringEscapeUtils.escapeHtml4(e.toString()));
			}
		}

		return errores;
	}
	
    private static String obtenerFechaActual() {
        // Obtener fecha actual
        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();
        // Formato de fecha
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        // Formatear la fecha actual en el formato esperado
        return dateFormatter.format(fechaActual);

    }



	public String publicarDoc(Map<String, Object> parametersExecute) throws Exception {
		ManagerTransaction manager = new ManagerTransaction(new ThPublicarDoc());
		manager.executeTrx(this.samClient, parametersExecute);

		String idThuban = (String) manager.getDataReturn();
		msg = (String) manager.getMensajeAviso();

		return idThuban;
	}
	
	
	public List<Archivo> descargarArchivo(String claseDoc, String usuarioThuban, String claveThuban, String idImagen) throws Exception {
		ManagerTransaction manager = new ManagerTransaction(new ThDescargaDoc());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		parametersExecute.put("Usuario", usuarioThuban);
		parametersExecute.put("Clave", claveThuban);
		parametersExecute.put("IdThuban", idImagen);
		parametersExecute.put("CopiaFiel", "N");
		manager.executeTrx(this.samClient, parametersExecute);

		List<Archivo> archivo = (List<Archivo>) manager.getDataReturnList();
	
		msg = (String) manager.getMensajeAviso();
	
		return archivo;
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
	
	public List<Archivo> buscarArchivos(String claseDoc, String usuarioThuban, String claveThuban, String idRendicion) throws Exception {
		ManagerTransaction manager = new ManagerTransaction(new ThBusqueda());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("Usuario", usuarioThuban);
		parametersExecute.put("Clave", claveThuban);
		parametersExecute.put("ClaseDocumental", claseDoc);
		parametersExecute.put("DatosSelect", "INDEX_ITEM_ID|D_ARCHIVO");
		parametersExecute.put("DatosWhere", "N_FACTURA=" + idRendicion);
		manager.executeTrx(this.samClient, parametersExecute);

		List<Archivo> archivos = (List<Archivo>) manager.getDataReturnList();
		
		msg = (String) manager.getMensajeAviso();
		

		return archivos;
	}

	public String getMsg() {
		return msg;
	}
}
