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
	private static final String ID_REND = "id_rend";
	private static final String ESTADO_REND = "estado_rendicion";
	private static final String COD_USER = "cod_user";
	private static final String CAMPO1 = "campo1";
	private static final String FORMAT = "%016d";
	
	
	public AprobacionesService(SAMWebClient samClient) {
		this.client = samClient;
	}

	public List<Rendicion> getAprobacionesPendientes(String id, String usuarioFiltro, String motivo, String estado, String usuario)
			throws TransactionException {
		log.info("Comienza llamado a trx SU61");
		if (id != null && !id.equalsIgnoreCase("")) {
			id = String.format(FORMAT,Integer.parseInt(id));
		}
		ManagerTransaction manager = new ManagerTransaction(new SU61());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put(ID_REND, id);
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
	
	
	
	

	public String cambiarEstadoRendiciones(String user, List<Integer> rendicionesSeleccionadas, String estado, String motivoRechazo, String glg) throws TransactionException {
		log.info("Se llama a la trx que realiza el cambio de estado de rendiciones (aprob)");
		ManagerTransaction manager = new ManagerTransaction(new SU62());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		String rendiciones = "";
		String rendiciones2 = "";
		String rendiciones3 = "";
		String rendiciones4 = "";
		
		for (Integer idRend : rendicionesSeleccionadas) {
			if (rendiciones.length() <= 480)
				rendiciones += String.format(FORMAT, idRend);
			else if (rendiciones2.length() <= 480)
				rendiciones2 += String.format(FORMAT, idRend);
			else if (rendiciones3.length() <= 480)
				rendiciones3 += String.format(FORMAT, idRend);
			else
				rendiciones4 += String.format(FORMAT, idRend);
		}
		
	
		parametersExecute.put(COD_USER, user);
		parametersExecute.put(ESTADO_REND, estado);
		parametersExecute.put(CAMPO1, rendiciones);
		parametersExecute.put("campo2", rendiciones2);
		parametersExecute.put("campo3", rendiciones3);
		parametersExecute.put("campo4", rendiciones4);
		parametersExecute.put("glg", glg);

		if (estado.equals("RECHA"))
			parametersExecute.put("", motivoRechazo);
		
		manager.executeTrx(this.client, parametersExecute);
		String aviso = (String) manager.getDataReturn();
		return aviso;
	}

	public String cambiarEstadoDeUnaRendicion(String user, int i, String estado, String motivoRechazo, String glg) throws TransactionException {
		log.info("Se llama a la trx que realiza el cambio de estado de una sola rendicion (aprob-recha)");
		ManagerTransaction manager = new ManagerTransaction(new SU62());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put(COD_USER, user);
		parametersExecute.put(ESTADO_REND, estado);
		parametersExecute.put("desc_rechazo", motivoRechazo);
		parametersExecute.put(CAMPO1, String.format(FORMAT, i));
		parametersExecute.put("glg", glg);
		manager.executeTrx(this.client, parametersExecute);
		String aviso = (String) manager.getDataReturn();
		return aviso;
	}

	public String obtenerIDU(ImagenesForm form, String tipoAdea) {
		String iduAdea = null;

		try {
			ManagerTransaction manager = new ManagerTransaction(new WM95());

			manager.executeTrx(client, this.mapDataIdu(form, tipoAdea));

			iduAdea = (String) manager.getDataReturn();
			msg = (String) manager.getMensajeAviso();
		} catch (Exception e) {
			log.error("", e);
		}

		return iduAdea;
	}
	
	

	
	private Map<String, Object> mapDataIdu(ImagenesForm form, String tipoAdea) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		log.info("Se mapean los datos de entrada");

	
		String nroTramite = String.format(FORMAT,form.getRendicion().getId());
		String usuario = FormatosCampos.formatString(form.getUsuario()
				.getIdUser(), WM95.USUARIO);
		String fechaGen = sdfYMD.format(new Date());


		String rendicionFechaDesde = sdfYMD.format(form.getRendicion()
				.getFechaDesde());
		String rendicionFechaHasta = sdfYMD.format(form.getRendicion()
				.getFechaHasta());

		parameters.put("modo", WM95.DELIM_03_MODO);
		parameters.put("adea", tipoAdea);
		parameters.put("clase", WM95.DELIM_05_CLASE);
		String datos = nroTramite + rendicionFechaDesde + rendicionFechaHasta
				+ usuario + fechaGen;
		log.info("Datos para Thuban: " + datos);
		parameters.put("datos", FormatosCampos.formatString(datos,
				WM95.DELIM_06_DATOS));
	

		return parameters;
	}
	
	



	public void scanRendicion(String idRendicion, String user)
			throws TransactionException {

		String iduAdea = null;
		
		ManagerTransaction manager = new ManagerTransaction(new SU60());
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put(ID_REND, String.format(FORMAT, Integer
				.parseInt(String.valueOf(idRendicion))));
		parameters.put(COD_USER, user);
	
		parameters.put("id_thuban", "");
		parameters.put("cod_adea", "");
		manager.executeTrx(client, parameters);

		
	}

	public void cambiarEscanRendicion(String idRendicion, String user, String idu) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU60());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		parametersExecute.put(ID_REND, String.format(FORMAT, Integer.parseInt(String.valueOf(idRendicion))));
		parametersExecute.put(COD_USER, user);
		parametersExecute.put("idu_thuban", idu);
		
		manager.executeTrx(client, parametersExecute);
		msg = (String) manager.getMensajeAviso();
	}
	
	
	@SuppressWarnings("unchecked")
	public void cambiarEscanRendicion(String idRendicion, String user,
			String idu, String adea) throws TransactionException {
		String iduAdea = null;
	
		ManagerTransaction manager = new ManagerTransaction(new SU60());
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put(ID_REND, String.format(FORMAT, Integer
				.parseInt(String.valueOf(idRendicion))));
		parameters.put(COD_USER, user);
		
		parameters.put("idu_thuban", idu);
		if (adea != null)
			parameters.put("cod_adea", adea);
		manager.executeTrx(client, parameters);


	}
	
	
	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public String cambiarEstadoRendiciones(String user, List<Rendicion> rendicionesSeleccionadas, String estado,
			Object motivoRechazo, String glg) throws TransactionException {
		
		log
		.info("Se llama a la trx que realiza el cambio de estado de rendiciones (aprob)");
ManagerTransaction manager = new ManagerTransaction(new SU62());
Map parametersExecute = new HashMap();

String rendiciones = "";
String rendiciones2 = "";
String rendiciones3 = "";
String rendiciones4 = "";
// Recorre la lista de rendiciones
for (Rendicion r : rendicionesSeleccionadas) {
	if (rendiciones.length() <= 480) {
		rendiciones += String.format(FORMAT, Integer.parseInt(String
				.valueOf(r.getId())));
	} else {
		if (rendiciones2.length() <= 480) {
			rendiciones2 += String.format(FORMAT, Integer
					.parseInt(String.valueOf(r.getId())));

		} else {
			if (rendiciones3.length() <= 480) {
				rendiciones3 += String.format(FORMAT, Integer
						.parseInt(String.valueOf(r.getId())));

					} else {
						rendiciones4 += String.format(FORMAT, Integer
									.parseInt(String.valueOf(r.getId())));
						}
					}
				}
			}
			parametersExecute.put(COD_USER, user);
			parametersExecute.put(ESTADO_REND, estado);
			parametersExecute.put(CAMPO1, rendiciones);
			parametersExecute.put("campo2", rendiciones2);
			parametersExecute.put("campo3", rendiciones3);
			parametersExecute.put("campo4", rendiciones4);
			parametersExecute.put("glg", glg);
			
			if (estado.equals("RECHA")) {
			parametersExecute.put("", motivoRechazo);
			}
			
			manager.executeTrx(this.client, parametersExecute);
			String aviso = (String) manager.getDataReturn();
			return aviso;
		
	}
}