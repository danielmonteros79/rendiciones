package com.sa.services;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.ComboMotivo;
import com.sa.entities.ComboOpcion;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.CuadroDetallado;
import com.sa.entities.CuadroGeneral;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.form.RendicionForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU51;
import com.sa.services.trxs.SU53;
import com.sa.services.trxs.SU54;
import com.sa.services.trxs.SU55;
import com.sa.services.trxs.SU66;
import com.sa.services.trxs.SU71;
import com.sa.services.trxs.SU72;
import com.sa.services.trxs.SU73;

@SuppressWarnings("unchecked")
public class RendicionesService {
	private static final Log log = LogFactory.getLog(RendicionesService.class);
	private SAMWebClient client;
	private String msg;

	public RendicionesService(SAMWebClient samClient) {
		this.client = samClient;
	}

	public List<Rendicion> obtenerListadoRendiciones(String idUser, String idRendicion, String estado, String feDesde, String feHasta)
			throws TransactionException, ParseException {
		log.info("Comienza llamado a trx para traer el listado de todas las rendiciones (con los parametros de entrada indicados)");

		ManagerTransaction manager = new ManagerTransaction(new SU53());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		if (idRendicion != null && !idRendicion.equalsIgnoreCase(""))
			idRendicion = String.format("%016d", Long.parseLong(idRendicion));
		
		parametersExecute.put("codUsuario", idUser);
		parametersExecute.put("idRendicion", idRendicion != null ? idRendicion : "");
		parametersExecute.put("estadoRendicion", estado != null ? estado : "");
		parametersExecute.put("fDesde", feDesde != null ? feDesde : "");
		parametersExecute.put("fHasta", feHasta != null ? feHasta : "");
		manager.executeTrx(this.client, parametersExecute);
		List<Rendicion> rendiciones = (List<Rendicion>) manager.getDataReturnList();
		msg = (String) manager.getMensajeAviso();
	
		
		return rendiciones;
	}

	public List<ComboOpcion2> getComboOpcion2(String opcion, String tabla, String subTabla, String user) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de estados para el combo");
		ManagerTransaction manager = new ManagerTransaction(new SU51());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", opcion);
		parametersExecute.put("claves_cons", tabla);
		parametersExecute.put("cant_tablas", subTabla);
		parametersExecute.put("cod_usr", user);

		manager.executeTrx(this.client, parametersExecute);

		List<ComboOpcion2> comboEstados = (List<ComboOpcion2>) manager.getDataReturnList();
		msg = (String) manager.getMensajeAviso();

		return comboEstados;
	}
	
	public List<ComboOpcion2> getComboOpcion2(String opcion, String tabla, String subTabla, String user, String codGasto) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de estados para el combo");
		ManagerTransaction manager = new ManagerTransaction(new SU51());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", opcion);
		parametersExecute.put("claves_cons", tabla);
		parametersExecute.put("cant_tablas", subTabla);
		parametersExecute.put("cod_usr", user);
		parametersExecute.put("cod_gasto", codGasto);

		manager.executeTrx(this.client, parametersExecute);

		List<ComboOpcion2> comboEstados = (List<ComboOpcion2>) manager.getDataReturnList();
		msg = (String) manager.getMensajeAviso();

		return comboEstados;
	}

	public List<ComboMotivo> getMotivoRendiciones(String opcion, String user, String glg) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de motivos para el combo");
		ManagerTransaction manager = new ManagerTransaction(new SU51());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		String glgActual = glg.equals("") ? "00" : "0" + glg;
		parametersExecute.put("cant_tablas", glgActual);
		parametersExecute.put("opcion", opcion);
		parametersExecute.put("cod_usr", user);
		manager.executeTrx(this.client, parametersExecute);

		List<ComboMotivo> comboMotivo = (List<ComboMotivo>) manager.getDataReturnList();
		msg = (String) manager.getMensajeAviso();
		
		return comboMotivo;
	}

	public void cambiarEstadoScann(String idUser, RendicionForm rf, String string2, String string3, String string4) {

		rf.setEstado(2);

	}

	public String altaRendicion(String idusr, String motivo, String feDesde, String feHasta, String descripcion, Boolean excepcion)
			throws TransactionException {
		log.info("Comienza llamado a trx para crear nueva rendicion)");

		ManagerTransaction manager = new ManagerTransaction(new SU54());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		String codEstadoDoc = excepcion ? "EXCP" : "";
		
		parametersExecute.put("opcion", "ALTA");
		parametersExecute.put("id_user", idusr);
		parametersExecute.put("fecha_desde", feDesde);
		parametersExecute.put("fecha_hasta", feHasta);
		parametersExecute.put("cod_motivo", motivo);
		parametersExecute.put("desc_rendicion", descripcion);
		parametersExecute.put("importe_rend_pesos", "000000000000000");
		parametersExecute.put("id_gestor_gastos", "000");
		parametersExecute.put("cod_estado_doc", codEstadoDoc);
		
		manager.executeTrx(this.client, parametersExecute);
		String idRendicion = (String) manager.getDataReturn();
		this.msg = (String) manager.getMensajeAviso();
		
		return idRendicion;
	}

	public String modificarRendicion(String idRendicion, String idUser, String codMotivo, String fechaDesde, String fechaHasta, String descRendicion, String estadoRend, String excepcion)
			throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU54());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("opcion", "MODI");
		parametersExecute.put("id_rendicion", String.format("%016d", Integer.parseInt(idRendicion)));
		parametersExecute.put("id_user", idUser);
		parametersExecute.put("fecha_desde", fechaDesde);
		parametersExecute.put("fecha_hasta", fechaHasta);
		parametersExecute.put("cod_motivo", codMotivo);
		parametersExecute.put("desc_rendicion", descRendicion);
		parametersExecute.put("est_rend", estadoRend);
		parametersExecute.put("cod_estado_doc", excepcion);
		
		manager.executeTrx(this.client, parametersExecute);
		idRendicion = (String) manager.getDataReturn();
		this.msg = (String) manager.getMensajeAviso();
		
		return idRendicion;
	}
	
	public String exceptuarRendicion(String opcion, String idRendicion, String excepcion)
			throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU54());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("opcion", opcion);
		parametersExecute.put("id_rendicion", String.format("%016d", Integer.parseInt(idRendicion)));
		parametersExecute.put("cod_estado_doc", excepcion);
		
		manager.executeTrx(this.client, parametersExecute);
		idRendicion = (String) manager.getDataReturn();
		this.msg = (String) manager.getMensajeAviso();
		
		return idRendicion;
	}

	public List<Gastos> getGastos(String idRendicion, String idGasto,
			String idUser, String codMotivo) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de gastos ");
		ManagerTransaction manager = new ManagerTransaction(new SU55());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		if (idRendicion != null && !idRendicion.equalsIgnoreCase("")) {
			idRendicion = String.format("%016d", Integer.parseInt(idRendicion));
		}
		if (idGasto != null && !idGasto.equalsIgnoreCase("")) {
			idGasto = String.format("%09d", Integer.parseInt(idGasto));
		}
		parametersExecute.put("id_rendicion", idRendicion);
		parametersExecute.put("id_gasto", idGasto);
		parametersExecute.put("id_user", idUser);
		parametersExecute.put("cod_motivo", codMotivo);
//		parametersExecute.put("desc_gasto", "test desde eclipse");
		
		manager.executeTrx(this.client, parametersExecute);

		List<Gastos> gastosRendicion = (List<Gastos>) manager
				.getDataReturnList();
		return gastosRendicion;
	}

	public List<Gastos> getGastosDistribuidos(String idRendicion, String idGasto, String idUser, String codMotivo) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de gastos ");
		ManagerTransaction manager = new ManagerTransaction(new SU55());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		if (idRendicion != null && !idRendicion.equalsIgnoreCase("")) {
			idRendicion = String.format("%016d", Integer.parseInt(idRendicion));
		}
		if (idGasto != null && !idGasto.equalsIgnoreCase("")) {
			idGasto = String.format("%09d", Integer.parseInt(idGasto));
		}
		parametersExecute.put("id_rendicion", idRendicion);
		parametersExecute.put("id_gasto", idGasto);
		parametersExecute.put("id_user", idUser);
		parametersExecute.put("cod_motivo", codMotivo);
		 parametersExecute.put("DERRAME", "S");
		// parametersExecute.put("desc_gasto", "test desde eclipse");
		
		manager.executeTrx(this.client, parametersExecute);

		List<Gastos> gastosRendicion = (List<Gastos>) manager.getDataReturnList();
		return gastosRendicion;
	}
	
	public String bajaRendicion(String user, String idRendicion) throws TransactionException {
		log.info("Comienza llamado a trx para eliminar una rendicion");
		ManagerTransaction manager = new ManagerTransaction(new SU54());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("opcion", "BAJA");
		parametersExecute.put("id_user", user);
		parametersExecute.put("id_rendicion", String.format("%016d", Integer.parseInt(idRendicion)));
		manager.executeTrx(this.client, parametersExecute);
		
		String idRendicionBorrada = (String) manager.getDataReturn();
		
		if (manager.getMensajeAviso() != null && manager.getMensajeAviso().equals("BAJA EFECTUADA"))
			msg = "OK: " + manager.getMensajeAviso();
		else
			msg = (String) manager.getMensajeAviso();
		
		return idRendicionBorrada;
	}
	
	public List<CuadroGeneral> getCuadroGeneral(String opcion, String idUser, String fechaDesde, String fechaHasta,
			String monDesde, String monHasta, String codMotivo, String codGlg, String userSel) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de rendiciones para cuadro general");
		ManagerTransaction manager = new ManagerTransaction(new SU71());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		String monD = monDesde;
		String monH = monHasta;

		DecimalFormat decimalFormat = new DecimalFormat("000000000000000");
		if (!monD.equals("")) {
		    double valueD = Double.parseDouble(monD.replace(",", "."));
		    String imp = decimalFormat.format(valueD * 100).replace(",", "");
		    monD = String.format("%015d", Long.parseLong(imp));
		}
		
		if (!monH.equals("")) {
		    double valueH = Double.parseDouble(monH.replace(",", "."));
		    String imp2 = decimalFormat.format(valueH * 100).replace(",", "");
		    monH = String.format("%015d", Long.parseLong(imp2));
		}




		parametersExecute.put("id_usr_log", idUser);
		parametersExecute.put("perf_usr_log", codGlg);
		parametersExecute.put("opcion_cons", opcion);
		parametersExecute.put("fdesde", fechaDesde);
		parametersExecute.put("fhasta", fechaHasta);
		parametersExecute.put("monto_desde", monD);
		parametersExecute.put("monto_hasta", monH);
		parametersExecute.put("cod_glg_sel", codGlg);
		parametersExecute.put("cod_mot_sel", codMotivo);
		parametersExecute.put("usr_sel", userSel);

		manager.executeTrx(this.client, parametersExecute);
		msg = (String) manager.getMensajeAviso();

		List<CuadroGeneral> cuadroGeneral = (List<CuadroGeneral>) manager.getDataReturnList();

		return cuadroGeneral;
	}
	
	public List<CuadroDetallado> getCuadroDetallado(String opcion, String fechaDesde, String fechaHasta, String monDesde, String monHasta,
			String codEstado, String codMotivo, String idUser, String codGlg, String userSel) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU72());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		String monD = monDesde;
		String monH = monHasta;
		DecimalFormat decimalFormat = new DecimalFormat("#.00");

		if (!monD.equals("")) {
		    double valueD = Double.parseDouble(monD.replace(",", "."));
		    monD = String.valueOf(valueD);
		}
		
		if (!monH.equals("")) {
		    double valueH = Double.parseDouble(monH.replace(",", "."));
		    monH = String.valueOf(valueH);
		}


		
		String proxUsuario = "";
		if (codEstado.equals("PGLGE")) {
			codEstado = "PGLG";
			proxUsuario = "E";
		}
		
		parametersExecute.put("monto_desde", monD);
		parametersExecute.put("monto_hasta", monH);
		parametersExecute.put("cod_mot_sel", codMotivo);
		parametersExecute.put("cod_est_sel", codEstado);
		parametersExecute.put("id_usr_log", idUser);
		parametersExecute.put("perf_usr_log", codGlg);
		parametersExecute.put("cod_glg_sel", codGlg);
		parametersExecute.put("opcion_cons", opcion);
		parametersExecute.put("fdesde", fechaDesde);
		parametersExecute.put("fhasta", fechaHasta);
		parametersExecute.put("usr_sel", userSel);
		parametersExecute.put("prox_usu", proxUsuario);

		manager.executeTrx(this.client, parametersExecute);
		msg = (String) manager.getMensajeAviso();

		List<CuadroDetallado> cuadroDetallado = (List<CuadroDetallado>) manager.getDataReturnList();

		return cuadroDetallado;
	}
	
	public List<ComboOpcion> getGlgsUsuario(String usuario, String perfil) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU73());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("id_usr_log", usuario);
		parametersExecute.put("perf_usr_log", perfil);

		manager.executeTrx(this.client, parametersExecute);

		List<ComboOpcion> glgs = (List<ComboOpcion>) manager.getDataReturnList();
		
		this.msg = (String) manager.getMensajeAviso();
		
		return glgs;
	}
	
	public String getMsg (){
		return msg;
	}

	public void activaRechazaRendicion(String estado, String user, String idRendicion) throws TransactionException {
		log.info("Comienza llamado a trx para activar o rechazar una rendicion");
		ManagerTransaction manager = new ManagerTransaction(new SU66());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("id_user", user);
		parametersExecute.put("rendicion", String.format("%016d", Integer.parseInt(idRendicion)));
		parametersExecute.put("estado", String.format("%1$-" + 5 + "s", estado));
		manager.executeTrx(this.client, parametersExecute);
		
			msg = (String) manager.getMensajeAviso();
	}
}