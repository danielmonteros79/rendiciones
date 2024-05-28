package com.sa.services;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.entities.parametros.ParametroAlerta;
import com.sa.entities.parametros.ParametroExceptuado;
import com.sa.entities.parametros.ParametroGasto;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.delegacion.AbmDelegadoForm;
import com.sa.form.parametros.ParametrosAlertasForm;
import com.sa.form.parametros.ParametrosExceptuadosForm;
import com.sa.form.parametros.ParametrosGastosForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU67;
import com.sa.services.trxs.SU80;
import com.sa.services.trxs.SU81;
import com.sa.services.trxs.SU82;
import com.sa.services.trxs.SU83;
import com.sa.services.trxs.SU84;
import com.sa.services.trxs.SU85;
import com.sa.services.trxs.SU86;
import com.sa.services.trxs.SU87;
import com.sa.services.trxs.SU88;
import com.sa.services.trxs.SU89;
import com.sa.util.ParamsConstants;

@SuppressWarnings("unchecked")
public class ParametrosService {
	private static final Log log = LogFactory.getLog(ParametrosService.class);
	private SAMWebClient client;
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
	private String msgAviso;

	public ParametrosService(SAMWebClient samClient) {
		this.client = samClient;
	}

	public List<ParametroMotivo> getMotivos(String codMotivo, String user) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de motivos");
		ManagerTransaction manager = new ManagerTransaction(new SU82());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("cod_motivo", codMotivo);
		parametersExecute.put("cod_user", user);
		
		manager.executeTrx(this.client, parametersExecute);
		
		List<ParametroMotivo> parametroMotivos = (List<ParametroMotivo>) manager.getDataReturnList();
		
		return parametroMotivos;
	}
	
	public String altaMotivo(ParametroMotivo motivo) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU83());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		String ccosto = "";
		for (String cc : motivo.getCentrosCosto()) {
			if (!"".equals(cc) && !"0000".equals(String.format("%04d", Integer.parseInt(cc))))
				ccosto += String.format("%04d", Integer.parseInt(cc));
		}
		
		String diasInt = "";
		if (motivo.getMeDiasInterv() != null && !motivo.getMeDiasInterv().equals(""))
			diasInt = String.format("%09d", Integer.parseInt(motivo.getMeDiasInterv()));
		
		parametersExecute.put("opcion", "ALTA");
		parametersExecute.put("cod_motivo", String.format("%04d", Integer.parseInt(motivo.getCodigo())));
		parametersExecute.put("est_motivo", motivo.getEstado());
		parametersExecute.put("desc_motivo", motivo.getDescripcion());
		parametersExecute.put("id_glg", String.format("%02d", Integer.parseInt(motivo.getIdGlg())));
		parametersExecute.put("apro_glg", motivo.getCodAprobacionGlg());
		parametersExecute.put("cent_cos", String.format("%04d", Integer.parseInt(motivo.getIdCentroCostos())));
		parametersExecute.put("inc_excl", motivo.getMaInclExcl());
		parametersExecute.put("cod_sup", motivo.getCodSup());
		parametersExecute.put("cfirma", motivo.getCodFirma());
		parametersExecute.put("maviso", motivo.getMeAviso());
		parametersExecute.put("fdesde", motivo.getFechaDesde() == null ? "" : sdfYMD.format(motivo.getFechaDesde()));
		parametersExecute.put("fhasta", motivo.getFechaHasta() == null ? "" : sdfYMD.format(motivo.getFechaHasta()));
		parametersExecute.put("id_oscar", motivo.getOscar().toString());
		parametersExecute.put("ni_carga", motivo.getIdNivCarga());
		parametersExecute.put("ni_auto", motivo.getIdNivAutoriz());
		parametersExecute.put("t_aviso", motivo.getTxAviso());
		parametersExecute.put("operesp", motivo.getIdOperEspe());
		parametersExecute.put("dias_int", diasInt);
		parametersExecute.put("ccosto", String.format("%1$-60s", ccosto));
				
		manager.executeTrx(this.client, parametersExecute);
		String msg =(String) manager.getMensajeAviso();
		if(msg == null)
			return "";
					
		return msg;
	}
	
	public String modificacionMotivo(ParametroMotivo motivo) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU83());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		String ccosto = "";
		for (String cc : motivo.getCentrosCosto()) {
			if (!"".equals(cc) && !"0000".equals(String.format("%04d", Integer.parseInt(cc))))
				ccosto += String.format("%04d", Integer.parseInt(cc));
		}
		
		String diasInt = "";
		if (motivo.getMeDiasInterv() != null && !motivo.getMeDiasInterv().equals(""))
			diasInt = String.format("%09d", Integer.parseInt(motivo.getMeDiasInterv()));
		
		parametersExecute.put("opcion", "MODI");
		parametersExecute.put("cod_motivo", motivo.getCodigo());
		parametersExecute.put("est_motivo", motivo.getEstado());
		parametersExecute.put("desc_motivo", motivo.getDescripcion());
		parametersExecute.put("id_glg", String.format("%02d", Integer.parseInt(motivo.getIdGlg())));
		parametersExecute.put("apro_glg", motivo.getCodAprobacionGlg());
		parametersExecute.put("cent_cos", String.format("%04d", Integer.parseInt(motivo.getIdCentroCostos())));
		parametersExecute.put("inc_excl", motivo.getMaInclExcl());
		parametersExecute.put("cod_sup", motivo.getCodSup());
		parametersExecute.put("cfirma", motivo.getCodFirma());
		parametersExecute.put("maviso", motivo.getMeAviso());
		parametersExecute.put("fdesde", motivo.getFechaDesde() == null ? "" : sdfYMD.format(motivo.getFechaDesde()));
		parametersExecute.put("fhasta", motivo.getFechaHasta() == null ? "" : sdfYMD.format(motivo.getFechaHasta()));
		parametersExecute.put("id_oscar", motivo.getOscar().toString());
		parametersExecute.put("ni_carga", motivo.getIdNivCarga());
		parametersExecute.put("ni_auto", motivo.getIdNivAutoriz());
		parametersExecute.put("t_aviso", motivo.getTxAviso());
		parametersExecute.put("operesp", motivo.getIdOperEspe());
		parametersExecute.put("dias_int", diasInt);
		parametersExecute.put("ccosto", String.format("%1$-60s", ccosto));
				
		manager.executeTrx(this.client, parametersExecute);
		String msg =(String) manager.getMensajeAviso();
		if(msg == null)
			return "";
					
		return msg;
	}

	public String bajaMotivo(String codMotivo) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU83());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("opcion", "BAJA");
		parametersExecute.put("cod_motivo", codMotivo);
				
		manager.executeTrx(this.client, parametersExecute);
		String msg =(String) manager.getMensajeAviso();
		if(msg == null)
			return "";
					
		return msg;
	}
	
	public List<ParametroGasto> getGastos(String user, String codGasto) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU84());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("cod_usr", user);
		parametersExecute.put("cod_gasto", codGasto);

		manager.executeTrx(this.client, parametersExecute);
		msgAviso = (String) manager.getMensajeAviso();

		return (List<ParametroGasto>) manager.getDataReturnList();
	}

	public List<ParametriaUsuarioDelegado> getDelegaciones(String usuario) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU80());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("cod_user", usuario);
		manager.executeTrx(this.client, parametersExecute);
		
		List<ParametriaUsuarioDelegado> listado = (List<ParametriaUsuarioDelegado>) manager.getDataReturnList();
		msgAviso = (String) manager.getMensajeAviso();
		
		return listado;
	}

	public Usuario getUsuarioDelegacion(String usuario, String opcion) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU81());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", opcion);
		parametersExecute.put("id_reemplazo", usuario.toUpperCase());

		manager.executeTrx(this.client, parametersExecute);
		Usuario usuarioCheck = (Usuario) manager.getDataReturn();

		return usuarioCheck;
	}

	public String abmDelegaciones(AbmDelegadoForm formulario, Usuario user) throws TransactionException {
		log.info("Comienza llamado a trx para " + formulario.getOpcion() + " de DELEGACIONES");

		ManagerTransaction manager = new ManagerTransaction(new SU81());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", formulario.getOpcion().trim());
		parametersExecute.put("id_reemplazo", formulario.getDelegadoUser().toUpperCase().trim());
		parametersExecute.put("fDesde_old", formulario.getFeDesde());
		parametersExecute.put("fHasta_old", formulario.getFeHasta());

		if (!formulario.getOpcion().trim().equals("BAJA")) {
			parametersExecute.put("informe", formulario.getInforme().trim());
			parametersExecute.put("accion", formulario.getAccion().trim());
			parametersExecute.put("estado", formulario.getEstado().trim());

			if (formulario.getOpcion().trim().equalsIgnoreCase(ParamsConstants.SU81_MODIFICACION)) {
				parametersExecute.put("fAlta", formulario.getFechaAlta());
				parametersExecute.put("user_alta", formulario.getUserAlta());
				parametersExecute.put("fDesde", formulario.getFeDesde());
				parametersExecute.put("fHasta", formulario.getFeHasta());
				parametersExecute.put("fDesde_old", formulario.getFeDesdeOld());
				parametersExecute.put("fHasta_old", formulario.getFeHastaOld());
			}
		}
		
		manager.executeTrx(this.client, parametersExecute);

		if (manager.getMensajeAviso() != null)
			return (String) manager.getMensajeAviso();
		
		return "";
	}

	public List<String> getGastosCombos() throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de estados para los combos");
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		ManagerTransaction manager = new ManagerTransaction(new SU85());
//		parametersExecute.put("opcion", value)
		parametersExecute.put("opcion", "ALTA");
//		parametersExecute.put("cod_gasto", "0200");
		parametersExecute.put("modo","I");
		manager.executeTrx(this.client, parametersExecute);
		List<String> combos = (List<String>) manager.getDataReturnList();
		return combos;
	}
	
	public List<ParametriaUsuarioDelegado> getRelacionUsuarioDelegado(String usuario) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU80());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("cod_user", usuario);
		manager.executeTrx(this.client, parametersExecute);
		
		List<ParametriaUsuarioDelegado> listado = (List<ParametriaUsuarioDelegado>) manager.getDataReturnList();
		msgAviso = (String) manager.getMensajeAviso();
		
		return listado;
	}
	
	
	
	
	public List<String> getAlertaCombos() throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de estados para los combos");
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		ManagerTransaction manager = new ManagerTransaction(new SU88());
		parametersExecute.put("opcion", "FILT");
		manager.executeTrx(this.client, parametersExecute);
		List<String> combos = (List<String>) manager.getDataReturnList();
		msgAviso = (String) manager.getMensajeAviso();
		return combos;
	}

	private String getFechaYYYY_MM_DD(String fecha) {
		String ret = "";
		fecha = fecha.replace("-", "/");
		String[] str = fecha.split("/");

		ret = str[2] + "-" + str[1] + "-" + str[0];
		return ret;
	}
	
	public String getCodigoExceptuado(String usuario,String codigo, String marca) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU87());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", "CONS");
		parametersExecute.put("mot_usu", codigo);
		parametersExecute.put("cod_usuario", usuario);
		parametersExecute.put("cod_mot_usu", marca);
		
		manager.executeTrx(this.client, parametersExecute);
		String codigoExceptuado= (String) manager.getDataReturn();

		return codigoExceptuado;
	}


	public List<ParametroAlerta> getAlertas(String opcion, String codMotivo, String codGasto) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de alerta");
		ManagerTransaction manager = new ManagerTransaction(new SU88());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		parametersExecute.put("opcion", opcion);
		parametersExecute.put("cod_mot", codMotivo);
		parametersExecute.put("cod_gto", codGasto);

		manager.executeTrx(this.client, parametersExecute);

		List<ParametroAlerta> parametroAlerta = (List<ParametroAlerta>) manager.getDataReturnList();
		this.msgAviso = (String) manager.getMensajeAviso();

		return parametroAlerta;
	}

	public ParametroAlerta getAlerta(String opcion, String codMotivo, String codGasto, String timeStamp) throws TransactionException {
		log.info("Comienza llamado a trx para traer el alerta");
		ManagerTransaction manager = new ManagerTransaction(new SU88());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		parametersExecute.put("opcion", opcion);
		parametersExecute.put("cod_mot", codMotivo);
		parametersExecute.put("cod_gto", codGasto);
		parametersExecute.put("tmstp", timeStamp);

		manager.executeTrx(this.client, parametersExecute);

		ParametroAlerta parametroAlerta = new ParametroAlerta();
		parametroAlerta = (ParametroAlerta) manager.getDataReturnList().get(0);

		return parametroAlerta;
	}
	
	public String altaParamAlerta(ParametrosAlertasForm frm) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU89());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", "ALTA");
		parametersExecute.put("cod_mot", frm.getCodMotivo());
		parametersExecute.put("cod_gto", frm.getCodGasto());
		parametersExecute.put("est_aler", frm.getEstado());
		parametersExecute.put("mont_cant", frm.getMontCant());
		parametersExecute.put("cod_rend", frm.getRend());
		parametersExecute.put("cod_periodo", frm.getPeriodo());
		parametersExecute.put("cod_crit", frm.getCriticidad());
		parametersExecute.put("niv_max", frm.getNivMax());
		parametersExecute.put("niv_min", frm.getNivMin());
		parametersExecute.put("tx_alerta", frm.getTxAviso());
		
		if (frm.getMontCant().equals("M")) {
			String importeCant = frm.getImpCant();
			double value = Double.parseDouble(importeCant.replace(",", "."));
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			String imp = decimalFormat.format(value).replace(".", "");
			importeCant = String.format("%014.0f", Double.parseDouble(imp.replace(",", "")));
			parametersExecute.put("imp_cant", importeCant);
		} else
			parametersExecute.put("imp_cant", String.format("%016.0f", Double.parseDouble(frm.getImpCant())));

		manager.executeTrx(this.client, parametersExecute);
		String msg = (String) manager.getMensajeAviso();
		if (msg == null)
			return "";

		return msg;
	}

	public String modificacionParamAlerta(ParametrosAlertasForm frm) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU89());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", "MODI");
		parametersExecute.put("cod_mot", frm.getCodMotivo());
		parametersExecute.put("cod_gto", frm.getCodGasto());
		parametersExecute.put("est_aler", frm.getEstado());
		parametersExecute.put("mont_cant", frm.getMontCant());
		parametersExecute.put("cod_rend", frm.getRend());
		parametersExecute.put("cod_periodo", frm.getPeriodo());
		parametersExecute.put("cod_crit", frm.getCriticidad());
		parametersExecute.put("niv_max", frm.getNivMax());
		parametersExecute.put("niv_min", frm.getNivMin());
		parametersExecute.put("tx_alerta", frm.getTxAviso());
		parametersExecute.put("timesta", frm.getTimeStamp());
		
		if (frm.getMontCant().equals("M")) {
			String importeCant = frm.getImpCant();
			double value = Double.parseDouble(importeCant.replace(",", "."));
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			String imp = decimalFormat.format(value).replace(".", "");
			importeCant = String.format("%014.0f", Double.parseDouble(imp.replace(",", "")));
			parametersExecute.put("imp_cant", importeCant);
		} else
			parametersExecute.put("imp_cant", String.format("%016.0f", Double.parseDouble(frm.getImpCant())));

		manager.executeTrx(this.client, parametersExecute);
		String msg = (String) manager.getMensajeAviso();
		if (msg == null)
			return "";

		return msg;
	}

	public String bajaParamAlerta(ParametrosAlertasForm frm) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU89());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", "BAJA");
		parametersExecute.put("cod_mot", frm.getCodMotivo());
		parametersExecute.put("cod_gto", frm.getCodGasto());
		parametersExecute.put("timesta", frm.getTimeStamp());

		manager.executeTrx(this.client, parametersExecute);
		String msg = (String) manager.getMensajeAviso();
		if (msg == null)
			return "";

		return msg;
	}

	public List<ParametroExceptuado> getExceptuados(String user) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de exceptuados");
		ManagerTransaction manager = new ManagerTransaction(new SU86());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", "CONS");
		parametersExecute.put("cod_usr", user);
		List<ParametroExceptuado> parametroExceptuado = null;
		try{
		manager.executeTrx(this.client, parametersExecute);
		msgAviso = (String) manager.getMensajeAviso();
		parametroExceptuado = (List<ParametroExceptuado>) manager.getDataReturnList();
		} catch (Exception e) {
			msgAviso = e.getCause().getMessage();
		}
		return parametroExceptuado;
	}
	
	public List<ParametroExceptuado> getExceptuado(String marca, String codMotUs, String user) throws TransactionException {
	
		log.info("Comienza llamado a trx para traer el listado de exceptuados");
		ManagerTransaction manager = new ManagerTransaction(new SU86());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", "FILT");
		parametersExecute.put("ma_mot_usu", marca);
		parametersExecute.put("cod_mot_usu", codMotUs);
		parametersExecute.put("cod_usr", user);
		List<ParametroExceptuado> parametroExceptuado = null;
		try{
		
		manager.executeTrx(this.client, parametersExecute);

		parametroExceptuado = (List<ParametroExceptuado>) manager.getDataReturnList();
		} catch (Exception e) {
			msgAviso = e.getCause().getMessage();
		}
		return parametroExceptuado;
	}
	public String altaExceptuado(ParametrosExceptuadosForm frm) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU87());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		String mu="";
		if("motivo".equals(frm.getMotivoUsuario()))
			mu="M";
		if("usuario".equals(frm.getMotivoUsuario()))
			mu="U";
		parametersExecute.put("opcion", "ALTA");
		parametersExecute.put("cod_mot_usu",mu );
		parametersExecute.put("mot_usu", frm.getDesMotivo());
		parametersExecute.put("estado", frm.getEstado());
		parametersExecute.put("fe_desde",this.getFechaYYYY_MM_DD(frm.getDesde()));
		parametersExecute.put("fe_hasta",this.getFechaYYYY_MM_DD(frm.getHasta()));
		
		manager.executeTrx(this.client, parametersExecute);
		String msg = (String) manager.getMensajeAviso();
		if (msg == null)
			return "";

		return msg;
	}

	public String saveModExceptuado(ParametrosExceptuadosForm frm) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU87());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		String mu="";
		if("motivo".equals(frm.getMotivoUsuario()))
			mu="M";
		if("usuario".equals(frm.getMotivoUsuario()))
			mu="U";
		parametersExecute.put("opcion", "MODI");
		parametersExecute.put("cod_mot_usu",mu );
		parametersExecute.put("mot_usu", frm.getDesMotivo());
		parametersExecute.put("estado", frm.getEstado());
		parametersExecute.put("fe_desde",this.getFechaYYYY_MM_DD(frm.getDesde()));
		parametersExecute.put("fe_hasta",this.getFechaYYYY_MM_DD(frm.getHasta()));
		
		manager.executeTrx(this.client, parametersExecute);
		String msg = (String) manager.getMensajeAviso();
		if (msg == null)
			return "";

		return msg;
	}
	
	public String deleteExceptuado(ParametrosExceptuadosForm frm) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU87());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		String mu="";
		if("motivo".equals(frm.getMotivoUsuario()))
			mu="M";
		if("usuario".equals(frm.getMotivoUsuario()))
			mu="U";
		parametersExecute.put("opcion", "BAJA");
		parametersExecute.put("cod_mot_usu", mu );
		parametersExecute.put("mot_usu", frm.getDesMotivo());
		
		manager.executeTrx(this.client, parametersExecute);
		String msg = (String) manager.getMensajeAviso();
		if (msg == null)
			return "";

		return msg;
	}
	
	public void altaGasto(ParametrosGastosForm frm) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU85());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		String ccosto = "";
		for (String cc : frm.getCentrosCosto()) {
			if (!"".equals(cc) && !"0000".equals(String.format("%04d", Integer.parseInt(cc))))
				ccosto += String.format("%04d", Integer.parseInt(cc));
		}
		
		parametersExecute.put("opcion", "ALTA");
		parametersExecute.put("modo", "C");
		parametersExecute.put("cod_gasto", String.format("%04d", Integer.parseInt(frm.getCodigo())));
		parametersExecute.put("desc_gasto", frm.getDescripcionGasto());
		parametersExecute.put("cod_motivo",frm.getMotivo());
		parametersExecute.put("bimon", frm.getBimon());
		parametersExecute.put("cent_cos", String.format("%04d", Integer.parseInt(frm.getIdCentroCostos())));
		parametersExecute.put("estado", frm.getEstado());
		parametersExecute.put("ristra", frm.getRistra().toString());
		parametersExecute.put("oscar", frm.getOscar().toString());
		parametersExecute.put("inc_excl", frm.getMaInclExcl());
		parametersExecute.put("compte", frm.getComprob());
		parametersExecute.put("antig", frm.getAntiguedad());
		parametersExecute.put("observ", frm.getObserv());
		parametersExecute.put("ni_ing", frm.getIdNivAutoriz());
		parametersExecute.put("plazo_ap", frm.getPlazoAprob());
		parametersExecute.put("ccosto", String.format("%1$-60s", ccosto));

		manager.executeTrx(this.client, parametersExecute);
		this.msgAviso = (String) manager.getMensajeAviso();
	}

	public void modificacionGasto(ParametrosGastosForm frm) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU85());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		String ccosto = "";
		for (String cc : frm.getCentrosCosto()) {
			if (!"".equals(cc) && !"0000".equals(String.format("%04d", Integer.parseInt(cc))))
				ccosto += String.format("%04d", Integer.parseInt(cc));
		}
		
		parametersExecute.put("opcion", "MODI");
		parametersExecute.put("modo", "C");
		parametersExecute.put("cod_gasto", frm.getCodigo());
		parametersExecute.put("desc_gasto", frm.getDescripcionGasto());
		parametersExecute.put("cod_motivo",frm.getMotivo());
		parametersExecute.put("bimon", frm.getBimon());
		parametersExecute.put("cent_cos", String.format("%04d", Integer.parseInt(frm.getIdCentroCostos())));
		parametersExecute.put("estado", frm.getEstado());
		parametersExecute.put("ristra", frm.getRistra().toString());
		parametersExecute.put("oscar", frm.getOscar().toString());
		parametersExecute.put("inc_excl", frm.getMaInclExcl());
		parametersExecute.put("compte", frm.getComprob());
		parametersExecute.put("antig", frm.getAntiguedad());
		parametersExecute.put("observ", frm.getObserv());
		parametersExecute.put("ni_ing", frm.getIdNivAutoriz());
		parametersExecute.put("plazo_ap", frm.getPlazoAprob());
		parametersExecute.put("ccosto", String.format("%1$-60s", ccosto));
				
		manager.executeTrx(this.client, parametersExecute);
		this.msgAviso = (String) manager.getMensajeAviso();
	}
	
	public void bajaGasto(String codGasto, String codMotivo) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU85());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", "BAJA");
		parametersExecute.put("modo", "C");
		parametersExecute.put("cod_gasto", codGasto);
		parametersExecute.put("cod_motivo", codMotivo);
	
		manager.executeTrx(this.client, parametersExecute);
		this.msgAviso = (String) manager.getMensajeAviso();
	}

	public ManagerTransaction loadModificacionGasto(String codGasto, String idUser) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU85());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", "MODI");
		parametersExecute.put("modo", "I");
		parametersExecute.put("cod_gasto", codGasto);
	
		manager.executeTrx(this.client, parametersExecute);
		
		return manager;
	}

	public ManagerTransaction loadBajaGasto(String codGasto, String idUser) throws TransactionException {
		ManagerTransaction manager = new ManagerTransaction(new SU85());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("opcion", "BAJA");
		parametersExecute.put("modo", "I");
		parametersExecute.put("cod_gasto", codGasto);
	
		manager.executeTrx(this.client, parametersExecute);
		
		return manager;
	}

	public String getMsgAviso() {
		return msgAviso;
	}
}