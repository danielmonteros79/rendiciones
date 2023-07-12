package com.sa.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.CierreTarjeta;
import com.sa.entities.Rendicion;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU63;
import com.sa.services.trxs.SU64;
import com.sa.services.trxs.SU65;
import com.sa.services.trxs.SU66;
import com.sa.services.trxs.SU67;

public class CierreService {
	private static final Log log = LogFactory.getLog(CierreService.class);
	private SAMWebClient client;
	private String msg;
	private SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	public CierreService(SAMWebClient samClient) {
		this.client = samClient;
	}

	@SuppressWarnings("unchecked")
	public List<Rendicion> getDatosRendicion(String idUser, String idRend, String codMotivo, String usrSel, String feDesde, String feHasta)
			throws TransactionException, ParseException {
		log.info("Comienza llamado a trx SUM_CONS_RND_APROB (SU63)");

		ManagerTransaction manager = new ManagerTransaction(new SU63());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("id_user", idUser);
		parametersExecute.put("id_rend", idRend != null && !idRend.trim().equals("") ? String.format("%016d", Integer.parseInt(idRend))
				: "");
		parametersExecute.put("cod_motivo", codMotivo);
		parametersExecute.put("usr_sel", usrSel != null && !usrSel.trim().equals("") ? usrSel : "");
		parametersExecute.put("fe_desde", feDesde != null && !feDesde.trim().equals("") ? sdfYMD.format(sdfDMY.parse(feDesde)) : "");
		parametersExecute.put("fe_hasta", feHasta != null && !feHasta.trim().equals("") ? sdfYMD.format(sdfDMY.parse(feHasta)) : "");

		manager.executeTrx(this.client, parametersExecute);
		List<Rendicion> rendiciones = (List<Rendicion>) manager.getDataReturnList();
		msg = (String) manager.getMensajeAviso();

		return rendiciones;
	}

	public void crearOrdenDePago(String estado, List<Integer> idRendiciones, String user, String descripcion, String cmboMotivo) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU64());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		String rendiciones1 = "";
		String rendiciones2 = "";
		String rendiciones3 = "";
		String rendiciones4 = "";
		
		for (Integer idRendicion : idRendiciones) {
			String idRend = String.format("%016d", idRendicion);
			
			if (rendiciones1.length() <= 480)
				rendiciones1 += idRend;
			else if (rendiciones2.length() <= 480)
				rendiciones2 += idRend;
			else if (rendiciones3.length() <= 480)
				rendiciones3 += idRend;
			else
				rendiciones4 += idRend;
		}
		parametersExecute.put("cod_user", user);
		parametersExecute.put("estado_rendicion", estado);
		parametersExecute.put("campo1", rendiciones1);
		parametersExecute.put("campo2", rendiciones2);
		parametersExecute.put("campo3", rendiciones3);
		parametersExecute.put("campo4", rendiciones4);
		
		if (estado.equals("SUSPE"))
			parametersExecute.put("campo5", cmboMotivo + (descripcion == null ? "" : descripcion));

		manager.executeTrx(this.client, parametersExecute);
		msg = (String) manager.getMensajeAviso();
	}

	public String GenerarPagoMarca(String opcion, String idProceso, String tipoProceso, String feHoy, String estProceso,
			String numRegistro, String descripcion, String usuario) throws TransactionException {
		log.info("Comienza llamado a trx su65 para colocar la marca )");

		ManagerTransaction manager = new ManagerTransaction(new SU65());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		if (idProceso != null && !idProceso.equalsIgnoreCase("")) {
			idProceso = String.format("%016d", Integer.parseInt(idProceso));
		}
		if (numRegistro != null && !numRegistro.equalsIgnoreCase("")) {
			numRegistro = String.format("%010d", Integer.parseInt(numRegistro));
		}
		parametersExecute.put("cod_opcion", opcion);
		parametersExecute.put("id_proceso", idProceso);
		parametersExecute.put("tipo_proceso", tipoProceso);
		parametersExecute.put("fe_proceso", feHoy);
		parametersExecute.put("est_proceso", estProceso);
		parametersExecute.put("cant_reg_proc", numRegistro);
		parametersExecute.put("descripcion", descripcion);
		parametersExecute.put("usr_log", usuario);
		// parametersExecute.put("term", temminal);
		manager.executeTrx(this.client, parametersExecute);

		return null;
	}
	
	
	public String crearOrdenDePago(String id, String estado,
			List<Rendicion> rendicionesSeleccionadas, String user)
			throws TransactionException {

		log
				.info("Se llama a la trx que realiza el cambio de estado de rendiciones (aprob)");
		ManagerTransaction manager = new ManagerTransaction(new SU64());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		String rendiciones = "";
		String rendiciones2 = "";
		String rendiciones3 = "";
		String rendiciones4 = "";
		// Recorre la lista de rendiciones
		for (Rendicion r : rendicionesSeleccionadas) {
			if (rendiciones.length() <= 480) {
				rendiciones += String.format("%016d", Integer.parseInt(String
						.valueOf(r.getId())));
			} else {
				if (rendiciones2.length() <= 480) {
					rendiciones2 += String.format("%016d", Integer
							.parseInt(String.valueOf(r.getId())));

				} else {
					if (rendiciones3.length() <= 480) {
						rendiciones3 += String.format("%016d", Integer
								.parseInt(String.valueOf(r.getId())));

					} else {
						rendiciones4 += String.format("%016d", Integer
								.parseInt(String.valueOf(r.getId())));
					}
				}
			}
		}
		parametersExecute.put("cod_user", user);
		parametersExecute.put("estado_rendicion", estado);
		parametersExecute.put("campo1", rendiciones);
		parametersExecute.put("campo2", rendiciones2);
		parametersExecute.put("campo3", rendiciones3);
		parametersExecute.put("campo4", rendiciones4);

		manager.executeTrx(this.client, parametersExecute);
		// String idOrden= (String) manager.getDataReturn();
		return null;
	}
	
	
	public List<CierreTarjeta> getCierreTarjeta(String accion, String codUsuario, String usrConectado) throws TransactionException {
		log.info("Comienza llamado a trx su65 para colocar la marca )");

		ManagerTransaction manager = new ManagerTransaction(new SU65());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("accion", accion.toUpperCase());
		parametersExecute.put("usuario", codUsuario.toUpperCase());
		parametersExecute.put("conectado", usrConectado.toUpperCase());
	
		manager.executeTrx(this.client, parametersExecute);
		
		msg = (String) manager.getMensajeAviso();
		
		@SuppressWarnings("unchecked")
		List<CierreTarjeta> cierreTarjeta = (List<CierreTarjeta>) manager.getDataReturnList();	

		return cierreTarjeta;
	}

	public List<CierreTarjeta> generarCierreTarjeta( String codUsuario, String usrConectado, List<Integer> idConsumos, Double totPes, Double totDol,
			String motivo, String gastos) throws TransactionException {
		log.info("Comienza llamado a trx su65 para generarCierreTarjeta)");

		ManagerTransaction manager = new ManagerTransaction(new SU65());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		String totDolString  = String.valueOf(totDol).replace(".", "");
		String totPesString  = String.valueOf(totPes).replace(".", "") ;
		
		parametersExecute.put("accion", "G");
		parametersExecute.put("usuario", codUsuario.toUpperCase());
		parametersExecute.put("conectado", usrConectado.toUpperCase());
		parametersExecute.put("totdol", String.format("%13s", totDolString).replace(' ', '0'));
		parametersExecute.put("totpes", String.format("%13s", totPesString).replace(' ', '0')); 
		parametersExecute.put("motivo", motivo); 
		parametersExecute.put("gastos", gastos.substring(0, 4)); 
		int i = 1;
		for (Integer id : idConsumos) {
			parametersExecute.put("idres" + i, String.format("%016d", id));
			i++;
		}
	
		manager.executeTrx(this.client, parametersExecute);
		
		msg = (String) manager.getMensajeAviso();
		
		@SuppressWarnings("unchecked")
		List<CierreTarjeta> cierreTarjeta = (List<CierreTarjeta>) manager.getDataReturnList();	

		return cierreTarjeta;
	}
	

	public String SuspensosMarca() throws TransactionException {
		log.info("Comienza llamado a trx para colocar la marca de Pago)");

		ManagerTransaction manager = new ManagerTransaction(new SU66());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("", "");
		manager.executeTrx(this.client, parametersExecute);

		throw new TransactionException("Ya existe una solicitud pendiente para este usuario");
	}

	public String CierreTarjetaMarca() throws TransactionException {
		log.info("Comienza llamado a trx para colocar la marca de Pago)");

		ManagerTransaction manager = new ManagerTransaction(new SU67());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("", "");
		manager.executeTrx(this.client, parametersExecute);

		return null;
	}
	
	public String getMsg (){
		return msg;
	}
}