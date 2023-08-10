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
	private static final String FORMAT16 = "%016d";
	private static final String CAMPO1 = "campo1";
	private static final String COD_USER = "cod_user";
	private static final String ID_REND = "id_rend";
	private static final String ESTADO_REND = "estado_rendicion";

	public AprobacionesService(SAMWebClient samClient) {
		this.client = samClient;
	}

	public List<Rendicion> getAprobacionesPendientes(String id, String usuarioFiltro, String motivo, String estado, String usuario)
			throws TransactionException {
		log.info("Comienza llamado a trx SU61");
		if (id != null && !id.equalsIgnoreCase("")) {
			id = String.format(FORMAT16,Integer.parseInt(id));
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
		log.info("Se llama a la trx que realiza el  cambio de estado de rendiciones (aprob). ");
		ManagerTransaction manager = new ManagerTransaction(new SU62());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		String rendiciones = "";
		String rendiciones2 = "";
		String rendiciones3 = "";
		String rendiciones4 = "";
		
		for (Integer idRend : rendicionesSeleccionadas) {
			if (rendiciones.length() <= 480)
				rendiciones += String.format(FORMAT16, idRend);
			else if (rendiciones2.length() <= 480)
				rendiciones2 += String.format(FORMAT16, idRend);
			else if (rendiciones3.length() <= 480)
				rendiciones3 += String.format(FORMAT16, idRend);
			else
				rendiciones4 += String.format(FORMAT16, idRend);
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
		parametersExecute.put(CAMPO1, String.format(FORMAT16, i));
		parametersExecute.put("glg", glg);
		manager.executeTrx(this.client, parametersExecute);
		String aviso = (String) manager.getDataReturn();
		return aviso;
	}

	public String obtenerIDU(RendicionAvisoForm form, String tipoAdea) {
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
	
	

	
	private Map<String, Object> mapDataIdu(RendicionAvisoForm form, String tipoAdea) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		log.info("Se mapean los datos de entrada");

		// String nroTramite =
		// FormatosCampos.formatString(String.valueOf(form.getRendicion().getId()),
		// WM95.NRO_TRAMITE);
		String nroTramite = String.format(FORMAT16,form.getRendicion().getId());
		String usuario = FormatosCampos.formatString(form.getUsuario()
				.getIdUser(), WM95.USUARIO);
		String fechaGen = sdfYMD.format(new Date());
//		String ctroCosto = FormatosCampos.formatString(String.valueOf(form
//				.getUsuario().getCcostos()), WM95.CENTRO_COSTOS);
//		String codMotivo = FormatosCampos.formatString(form.getRendicion()
//				.getCodMotivo(), WM95.CODIGO_MOTIVO);
//		String descMotivo = FormatosCampos.formatString(form.getRendicion()
//				.getMotivo(), WM95.DESC_MOTIVO);
//
//		String nombreEmpleado = FormatosCampos.formatString(form.getUsuario()
//				.getNombre(), WM95.NOMBRE_EMPLEADO);
//		String glg = FormatosCampos.formatString("gl", WM95.GLG); // FALTA VER

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
		// parameters.put("datos", FormatosCampos.formatString(
		// nroTramite + usuario + fechaGen + ctroCosto + codMotivo + descMotivo
		// +
		// nombreEmpleado + glg + rendicionFechaDesde + rendicionFechaHasta,
		// WM95.DELIM_06_DATOS));

		return parameters;
	}
	
	

//	private Map<String, Object> mapDataIdu(Rendicion rendicion, String tipoAdea) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		
//		String nroTramite = String.format(FORMAT16, rendicion.getId());
//		String usuario = FormatosCampos.formatString(rendicion.getUsuarioRendicion(), WM95.USUARIO);
//		String fechaGen = DateUtil.dfYYYYMMDD.format(new Date());
//		
//		String rendicionFechaDesde = sdfYMD.format(rendicion.getFechaDesde());
//		String rendicionFechaHasta = sdfYMD.format(rendicion.getFechaHasta());
//
//		parameters.put("modo", WM95.DELIM_03_MODO);
//		parameters.put("adea", tipoAdea);
//		parameters.put("clase", WM95.DELIM_05_CLASE);
//		String datos = nroTramite + rendicionFechaDesde + rendicionFechaHasta + usuario + fechaGen;
//		log.info("Datos para Thuban: " + datos);
//		parameters.put("datos", FormatosCampos.formatString(datos, WM95.DELIM_06_DATOS));
//
//		return parameters;
//	}

	public void scanRendicion(String idRendicion, String user)
			throws TransactionException {
		// TODO Auto-generated method stub
		String iduAdea = null;
		// try {
		ManagerTransaction manager = new ManagerTransaction(new SU60());
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put(ID_REND, String.format(FORMAT16, Integer
				.parseInt(String.valueOf(idRendicion))));
		parameters.put(COD_USER, user);
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

		parametersExecute.put(ID_REND, String.format(FORMAT16, Integer.parseInt(String.valueOf(idRendicion))));
		parametersExecute.put(COD_USER, user);
		parametersExecute.put("idu_thuban", idu);
		
		manager.executeTrx(client, parametersExecute);
		msg = (String) manager.getMensajeAviso();
	}
	
	
	@SuppressWarnings("unchecked")
	public void cambiarEscanRendicion(String idRendicion, String user,
			String idu, String adea) throws TransactionException {
		// TODO Auto-generated method stub
		String iduAdea = null;
		// try {
		ManagerTransaction manager = new ManagerTransaction(new SU60());
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put(ID_REND, String.format(FORMAT16, Integer
				.parseInt(String.valueOf(idRendicion))));
		parameters.put(COD_USER, user);
		// parameters.put("id_thuban", "R"+ String.format("%015d",
		// Integer.parseInt(String.valueOf(idRendicion))));
		// parameters.put("cod_adea", "A"+String.format("%010d",
		// Integer.parseInt(String.valueOf(idRendicion))));
		parameters.put("idu_thuban", idu);
		if (adea != null)
			parameters.put("cod_adea", adea);
		manager.executeTrx(client, parameters);

		// iduAdea = (String) manager.getDataReturn();

		// } catch (Exception e) {
		// // TODO: handle exception
		// log.error(e);
		// }
		// return iduAdea;
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
		.info("Se llama a la trx que realiza el cambio de estado de rendiciones (aprob). ");
ManagerTransaction manager = new ManagerTransaction(new SU62());
Map parametersExecute = new HashMap();

String rendiciones = "";
String rendiciones2 = "";
String rendiciones3 = "";
String rendiciones4 = "";
// Recorre la lista de rendiciones
for (Rendicion r : rendicionesSeleccionadas) {
	if (rendiciones.length() <= 480) {
		rendiciones += String.format(FORMAT16, Integer.parseInt(String
				.valueOf(r.getId())));
	} else {
		if (rendiciones2.length() <= 480) {
			rendiciones2 += String.format(FORMAT16, Integer
					.parseInt(String.valueOf(r.getId())));

		} else {
			if (rendiciones3.length() <= 480) {
				rendiciones3 += String.format(FORMAT16, Integer
						.parseInt(String.valueOf(r.getId())));

					} else {
						rendiciones4 += String.format(FORMAT16, Integer
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