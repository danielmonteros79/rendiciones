package com.sa.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.Journal;
import com.sa.entities.Rendicion;
import com.sa.form.ImagenesForm;
import com.sa.form.RendicionAvisoForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU60;
import com.sa.services.trxs.SU61;
import com.sa.services.trxs.SU62;
import com.sa.services.trxs.SU70;
import com.sa.services.trxs.WM95;
import com.sa.util.DateUtil;
import com.sa.util.FormatosCampos;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

public class AprobacionesService {
	private static final Log log = LogFactory.getLog(AprobacionesService.class);
	private SAMWebClient client;
	private String msg;
	private String cantRendiciones;
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	public AprobacionesService(SAMWebClient samClient) {
		this.client = samClient;
	}

	public List<Rendicion> getAprobacionesPendientes(String id, String usuarioFiltro, String motivo, String estado, String usuario)
			throws TransactionException {
		log.info("Comienza llamado a trx SU61");
		if (id != null && !id.equalsIgnoreCase("")) {
			id = String.format("%016d",Integer.parseInt(id));
		}
		ManagerTransaction manager = new ManagerTransaction(new SU61());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("id_rend", id);
		parametersExecute.put("id_user", usuarioFiltro);
		parametersExecute.put("cod_motivo", motivo);
		parametersExecute.put("glg", estado);
		parametersExecute.put("id_usrdel", usuario);
		manager.executeTrx(this.client, parametersExecute);
		@SuppressWarnings("unchecked")
		List<Rendicion> rendiciones = (List<Rendicion>) manager.getDataReturnList();
		this.cantRendiciones = (String) manager.getDataReturn();
		msg = (String) manager.getMensajeAviso();

		return rendiciones;
	}
	
	
	public String cambiarEstadoRendiciones(String user, List<Integer> idRendiciones, String estado, String motivoRechazo, String glg) throws TransactionException {
		log.info("Se llama a la trx que realiza el cambio de estado de rendiciones (aprob)");
		ManagerTransaction manager = new ManagerTransaction(new SU62());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		String rendiciones = "";
		String rendiciones2 = "";
		String rendiciones3 = "";
		String rendiciones4 = "";
		
		for (Integer idRend : idRendiciones) {
			if (rendiciones.length() <= 480)
				rendiciones += String.format("%016d", idRend);
			else if (rendiciones2.length() <= 480)
				rendiciones2 += String.format("%016d", idRend);
			else if (rendiciones3.length() <= 480)
				rendiciones3 += String.format("%016d", idRend);
			else
				rendiciones4 += String.format("%016d", idRend);
		}
		
		parametersExecute.put("cod_user", user);
		parametersExecute.put("estado_rendicion", estado);
		parametersExecute.put("campo1", rendiciones);
		parametersExecute.put("campo2", rendiciones2);
		parametersExecute.put("campo3", rendiciones3);
		parametersExecute.put("campo4", rendiciones4);
		parametersExecute.put("glg", glg);

		if (estado == "RECHA")
			parametersExecute.put("", motivoRechazo);
		
		manager.executeTrx(this.client, parametersExecute);
		String aviso = (String) manager.getDataReturn();
		return aviso;
	}

	public String cambiarEstadoDeUnaRendicion(String user, String idRendicion, String estado, String motivoRechazo, String glg) throws TransactionException {
		log.info("Se llama a la trx que realiza el cambio de estado de una sola rendicion (aprob-recha)");
		ManagerTransaction manager = new ManagerTransaction(new SU62());
		Map parametersExecute = new HashMap();
		parametersExecute.put("cod_user", user);
		parametersExecute.put("estado_rendicion", estado);
		parametersExecute.put("desc_rechazo", motivoRechazo);
		parametersExecute.put("campo1", String.format("%016d", Integer.parseInt(idRendicion)));
		parametersExecute.put("glg", glg);
		manager.executeTrx(this.client, parametersExecute);
		String aviso = (String) manager.getDataReturn();
		return aviso;
	}

	public String obtenerIDU(Rendicion rendicion, String userId, String tipoAdea) {
		String iduAdea = null;

		try {
			ManagerTransaction manager = new ManagerTransaction(new WM95());

			manager.executeTrx(client, this.mapDataIdu(rendicion, userId, tipoAdea));

			iduAdea = (String) manager.getDataReturn();
			msg = (String) manager.getMensajeAviso();
		} catch (Exception e) {
			log.error("", e);
		}

		return iduAdea;
	}

	private Map<String, Object> mapDataIdu(Rendicion rendicion, String userId, String tipoAdea) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		String nroTramite = String.format("%016d", rendicion.getId());
		String usuario = FormatosCampos.formatString(userId, WM95.USUARIO);
		String fechaGen = DateUtil.dfYYYYMMDD.format(new Date());
		
		String rendicionFechaDesde = sdfYMD.format(rendicion.getFechaDesde());
		String rendicionFechaHasta = sdfYMD.format(rendicion.getFechaHasta());

		parameters.put("modo", WM95.DELIM_03_MODO);
		parameters.put("adea", tipoAdea);
		parameters.put("clase", WM95.DELIM_05_CLASE);
		String datos = nroTramite + rendicionFechaDesde + rendicionFechaHasta + usuario + fechaGen;
		log.info("Datos para Thuban: " + datos);
		parameters.put("datos", FormatosCampos.formatString(datos, WM95.DELIM_06_DATOS));

		return parameters;
	}

	public void scanRendicion(String idRendicion, String user)
			throws TransactionException {
		// TODO Auto-generated method stub
		String iduAdea = null;
		// try {
		ManagerTransaction manager = new ManagerTransaction(new SU60());
		Map parameters = new HashMap();

		parameters.put("id_rend", String.format("%016d", Integer
				.parseInt(String.valueOf(idRendicion))));
		parameters.put("cod_user", user);
		// parameters.put("id_thuban", "R"+ String.format("%015d",
		// Integer.parseInt(String.valueOf(idRendicion))));
		// parameters.put("cod_adea", "A"+String.format("%010d",
		// Integer.parseInt(String.valueOf(idRendicion))));
		parameters.put("id_thuban", "");
		parameters.put("cod_adea", "");
		manager.executeTrx(client, parameters);

		// iduAdea = (String) manager.getDataReturn();

		// } catch (Exception e) {
		// // TODO: handle exception
		// log.error(e);
		// }
		// return iduAdea;
	}

	public void cambiarEscanRendicion(String idRendicion, String user, String idu) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU60());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		parametersExecute.put("id_rend", String.format("%016d", Integer.parseInt(String.valueOf(idRendicion))));
		parametersExecute.put("cod_user", user);
		parametersExecute.put("idu_thuban", idu);
		
		manager.executeTrx(client, parametersExecute);
		msg = (String) manager.getMensajeAviso();
	}
	public List<Journal> getJournal(String idRendicion) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU70());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("id_rendicion", idRendicion);
		
		manager.executeTrx(this.client, parametersExecute);
		msg = (String) manager.getMensajeAviso();
		
		return (List<Journal>) manager.getDataReturnList();
	}
	
	public String getMsg (){
		return msg;
	}

	public String getCantRendiciones() {
		return cantRendiciones;
	}
}