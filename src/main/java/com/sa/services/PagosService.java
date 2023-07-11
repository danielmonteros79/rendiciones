package com.sa.services;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.ComboGasto;
import com.sa.entities.Cupones;
import com.sa.entities.DatosPantallaDinamica;
import com.sa.entities.Gastos;
import com.sa.entities.Usuario;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU51;
import com.sa.services.trxs.SU55;
import com.sa.services.trxs.SU56;
import com.sa.services.trxs.SU57;
import com.sa.services.trxs.SU58;
import com.sa.services.trxs.SU59;
import com.sa.services.trxs.SU67;
import com.sa.services.trxs.SU68;

@SuppressWarnings("unchecked")
public class PagosService {
	private static final Log log = LogFactory.getLog(PagosService.class);

	private String msg;
	private IWebClient samClient;

	public PagosService(IWebClient samClient) {
		this.samClient = samClient;
	}

	public List<ComboGasto> getComboGasto(String opcion, String user, String codMotivo) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de estados para el combo");
		ManagerTransaction manager = new ManagerTransaction(new SU51());
		Map parametersExecute = new HashMap();
		parametersExecute.put("opcion", opcion);
		parametersExecute.put("cod_usr", user);
		parametersExecute.put("cod_motivo", codMotivo);
		manager.executeTrx(this.samClient, parametersExecute);
		
		List<ComboGasto> comboTipoGastos = (List<ComboGasto>) manager.getDataReturnList();
		msg = (String) manager.getMensajeAviso();
		
		return comboTipoGastos;
	}
	
	public void addDescripcionObligatoria(String idRendicion, String idGasto,
			String codGasto, String codDetOblig, String campoTexto1,
			String campoTexto2, String campoNumerico1, String campoNumerico2,
			String campoCodigo1, String campoCodigo2, String campoTexto250,
			String campoFecha1, String campoFecha2) throws TransactionException {
		log.info("Comienza llamado a trx para crear el detalle obligtorio.");
		ManagerTransaction manager = new ManagerTransaction(new SU58());
		Map parametersExecute = new HashMap();

		parametersExecute.put("opcion", "ALTA");
		parametersExecute.put("id_rendicion", StringUtils.leftPad(idRendicion,
				16, "0"));
		parametersExecute.put("id_gasto", StringUtils.leftPad(idGasto, 9, "0"));
		parametersExecute.put("id_observacion", "000000000");
		parametersExecute.put("cod_gasto", StringUtils
				.leftPad(codGasto, 4, "0"));
		parametersExecute.put("cod_det_oblig", StringUtils.leftPad(codDetOblig,
				5, "0"));
		parametersExecute.put("campo_texto1", campoTexto1);
		parametersExecute.put("campo_texto2", campoTexto2);
		parametersExecute.put("campo_numerico1", StringUtils.leftPad(
				campoNumerico1, 9, "0"));
		parametersExecute.put("campo_numerico2", StringUtils.leftPad(
				campoNumerico2, 9, "0"));
		parametersExecute.put("campo_codigo1", campoCodigo1);
		parametersExecute.put("campo_codigo2", campoCodigo2);
		parametersExecute.put("campo_texto250", campoTexto250);
		parametersExecute.put("campo_fecha1", campoFecha1);
		parametersExecute.put("campo_fecha2", campoFecha2);

		manager.executeTrx(this.samClient, parametersExecute);
	}
	

	public Integer altaModifGasto(String opcion, String idGasto, String idRendicion, String moneda, String tipoComprobante, String tipoFactura,
			String factura, String cuit, String tipoGasto, String importe, String fechaGasto, String codMotivo, String centroCosto,
			String cupCred, String cupDeb, String cupon, String descCupon, String importeCupon, String nroTarjeta, String observacionGasto)
					throws TransactionException {
		log.info("Comienza llamado a trx para crear o modificar nuevo gasto)");
		if (idRendicion != null && !idRendicion.equalsIgnoreCase("")) {
			idRendicion = String.format("%016d", Integer.parseInt(idRendicion));
		}
		centroCosto = String.format("%04d", Integer.parseInt(centroCosto));
		
		
		double value = Double.parseDouble(importe.replace(",", "."));
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		String imp = decimalFormat.format(value).replace(".", "");
		importe = String.format("%015.0f", Double.parseDouble(imp.replace(",", "")));

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;

		if (fechaGasto != null && !fechaGasto.equalsIgnoreCase("")) {
			try {
				date = formatter.parse(fechaGasto);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
		if (date != null) {
			fechaGasto = df.format(date).trim();
		}
		String codGasto = (tipoGasto.substring(0, 4));
		String descGasto = (tipoGasto.substring(4, 54));
		String cod_det_oblig = (tipoGasto.substring(54, 59));
		ManagerTransaction manager = new ManagerTransaction(new SU56());
		Map parametersExecute = new HashMap();
		parametersExecute.put("opcion", opcion);
		parametersExecute.put("id_rendicion", idRendicion);
		if (!idGasto.equalsIgnoreCase("")) {
			idGasto = String.format("%09d", Integer.parseInt(idGasto));
			parametersExecute.put("id_gasto", idGasto);
		}
		// cod motivo corresponde al codigo de gasto del combo.
		parametersExecute.put("cod_motivo", codGasto);
		parametersExecute.put("tipo_comprobante", tipoComprobante);
		if (tipoComprobante.equals("0006")) {
			parametersExecute.put("clave_ident", cuit);
			parametersExecute.put("cod_sit_imp", tipoFactura); 
			parametersExecute.put("cod_pvnro_p", String.format("%04d", Integer.parseInt(factura.substring(0, 4))));
			parametersExecute.put("nro_comprobante", String.format("%08d", Integer.parseInt(factura.substring(4))));
			
		}
		parametersExecute.put("cod_moneda", moneda);
		log.info("Setea importe_gasto (" + importe + ")");
		parametersExecute.put("importe_gasto", importe);
		parametersExecute.put("fecha_gasto", fechaGasto);
		// parametersExecute.put("desc_gasto", "");
		parametersExecute.put("centro_costo", centroCosto);
		parametersExecute.put("cod_det_oblig", cod_det_oblig);
		if (!observacionGasto.equalsIgnoreCase(""))
			parametersExecute.put("desc_gasto", observacionGasto);

		if (cupon != null && !cupon.equalsIgnoreCase("")) {
			log.info("va con cupon");
			double valueCupon = Double.parseDouble(importeCupon.replace(",", "."));

			String impCupon = decimalFormat.format(valueCupon).replace(",", "");
			log.info(impCupon);

			importeCupon = String.format("%015d", Integer.parseInt(impCupon.replace(".", "")));
			parametersExecute.put("cupon_deb", String.format("%012d", Integer.parseInt(cupDeb.trim().equals("") ? "0" : cupDeb)));
			parametersExecute.put("cupon_cred", String.format("%012d", Integer.parseInt(cupCred)));
			try {
				parametersExecute.put("cupon_tarjeta", String.format("%012d", Integer.parseInt(cupon)));
			} catch (Exception e) {
				parametersExecute.put("cupon_tarjeta", cupon);
			}
			parametersExecute.put("descrip_cupon", descCupon);
			parametersExecute.put("importe_cupon", String.format("%015d", Integer.parseInt(importeCupon)));
			parametersExecute.put("nro_tarjeta", nroTarjeta);
		}
		manager.executeTrx(this.samClient, parametersExecute);
		msg = (String) manager.getMensajeAviso();
		Integer idGastoTrx = (Integer) manager.getDataReturn();
		return idGastoTrx;
	}

	public List<Cupones> getCupones(String opcion, String subTrx, String codapli, String user,
			String fechaDesde, String fechaHasta, String idRendicion, String codMotivo, String montoMin, String moneda) throws TransactionException {
		log.info("Comienza llamado a trx para traer el listado de cupones");
		ManagerTransaction manager = new ManagerTransaction(new SU68());
		Map parametersExecute = new HashMap();
		parametersExecute.put("pantalla", "cupones");
		parametersExecute.put("opcion", opcion);
		parametersExecute.put("subtran", subTrx);
		parametersExecute.put("codapli", codapli);
		parametersExecute.put("usuario", user);
		parametersExecute.put("fepresd", fechaDesde);
		parametersExecute.put("fecpreh", fechaHasta);
		parametersExecute.put("idrend", idRendicion);
		parametersExecute.put("motivo", codMotivo);
		parametersExecute.put("montoMin", montoMin);
		parametersExecute.put("moneda", moneda);

		manager.executeTrx(this.samClient, parametersExecute);
		List<Cupones> cupones = (List<Cupones>) manager.getDataReturnList();
		msg = (String) manager.getMensajeAviso();

		return cupones;
	}

	public void asignarCupon(String opcion, String idRendicion, String idGasto, String user, String impCuponTj, String nroTarjeta,
			String cuponTarjeta, String cuponDeb, String cuponCred, String descCupon, String monedaCupon, String fechaPresentacion)
			throws TransactionException {
		log.info("Comienza llamado a trx para asignar cupon");
		ManagerTransaction manager = new ManagerTransaction(new SU56());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		double value = Double.parseDouble(impCuponTj.replace(",", "."));
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		String imp = decimalFormat.format(value).replace(",", "");
		impCuponTj = String.format("%015d", Integer.parseInt(imp.replace(".", "")));

		parametersExecute.put("opcion", opcion);
		parametersExecute.put("id_rendicion", String.format("%016d", Integer.parseInt(idRendicion)));
		parametersExecute.put("id_gasto", String.format("%09d", Integer.parseInt(idGasto)));
		parametersExecute.put("id_user", user);
		parametersExecute.put("importe_cupon", String.format("%015d", Integer.parseInt(impCuponTj)));
		parametersExecute.put("nro_tarjeta", nroTarjeta);
		parametersExecute.put("cupon_deb", String.format("%012d", Integer.parseInt(cuponDeb)));
		parametersExecute.put("cupon_cred", String.format("%012d", Integer.parseInt(cuponCred)));
		parametersExecute.put("descrip_cupon", descCupon);
		parametersExecute.put("importe_gasto", String.format("%015d", Integer.parseInt(impCuponTj)));
		parametersExecute.put("cod_moneda", monedaCupon);
		parametersExecute.put("fecha_gasto", fechaPresentacion);

		try {
			parametersExecute.put("cupon_tarjeta", String.format("%012d", Integer.parseInt(cuponTarjeta)));
		} catch (Exception e) {
			parametersExecute.put("cupon_tarjeta", cuponTarjeta);
		}
		
		manager.executeTrx(this.samClient, parametersExecute);

		msg = (String) manager.getMensajeAviso();
	}

	public List<DatosPantallaDinamica> consultaDatosAdicionales(String idRendicion, String idGasto, String codMotivo, String codObserv)
			throws TransactionException {
		log.info("Comienza llamado a trx para consultar datos adicionales");
		ManagerTransaction manager = new ManagerTransaction(new SU57());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		parametersExecute.put("id_rendicion", idRendicion);
		parametersExecute.put("id_gasto", idGasto);
		parametersExecute.put("cod_motivo", codMotivo);
		parametersExecute.put("cod_observ", codObserv);

		manager.executeTrx(this.samClient, parametersExecute);
		msg = (String) manager.getMensajeAviso();
		
		return (List<DatosPantallaDinamica>) manager.getDataReturnList();
	}

	public void altaModifDatoAdicional(String idRendicion, String idGasto, String codGasto, String codDetOblig, String idObservacion, String campoTexto1,
			String campoTexto2, String campoNumerico1, String campoNumerico2, String campoCodigo1, String campoCodigo2,
			String campoTexto250, String campoFecha1, String campoFecha2) throws TransactionException {
		log.info("Comienza llamado a trx para crear o editar dato adicional");
		ManagerTransaction manager = new ManagerTransaction(new SU58());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		parametersExecute.put("opcion", idObservacion.equals("") ? "ALTA" : "MODI");
		parametersExecute.put("id_rendicion", StringUtils.leftPad(idRendicion, 16, "0"));
		parametersExecute.put("id_gasto", StringUtils.leftPad(idGasto, 9, "0"));
		parametersExecute.put("id_observacion", StringUtils.leftPad(idObservacion, 9, "0"));
		parametersExecute.put("cod_gasto", StringUtils.leftPad(codGasto, 4, "0"));
		parametersExecute.put("cod_det_oblig", StringUtils.leftPad(codDetOblig, 5, "0"));
		parametersExecute.put("campo_texto1", campoTexto1);
		parametersExecute.put("campo_texto2", campoTexto2);
		parametersExecute.put("campo_numerico1", StringUtils.leftPad(campoNumerico1, 9, "0"));
		parametersExecute.put("campo_numerico2", StringUtils.leftPad(campoNumerico2, 9, "0"));
		parametersExecute.put("campo_codigo1", campoCodigo1);
		parametersExecute.put("campo_codigo2", campoCodigo2);
		parametersExecute.put("campo_texto250", campoTexto250);
		parametersExecute.put("campo_fecha1", campoFecha1);
		parametersExecute.put("campo_fecha2", campoFecha2);

		manager.executeTrx(this.samClient, parametersExecute);
	}

	public void bajaDatoAdicional(String idRendicion, String idGasto, String codGasto, String codDetOblig, String idObservacion) throws TransactionException {
		log.info("Comienza llamado a trx para eliminar dato adicional");
		ManagerTransaction manager = new ManagerTransaction(new SU58());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();

		parametersExecute.put("opcion", "BAJA");
		parametersExecute.put("id_rendicion", StringUtils.leftPad(idRendicion, 16, "0"));
		parametersExecute.put("id_gasto", StringUtils.leftPad(idGasto, 9, "0"));
		parametersExecute.put("id_observacion", StringUtils.leftPad(idObservacion, 9, "0"));
		parametersExecute.put("cod_gasto", StringUtils.leftPad(codGasto, 4, "0"));
		parametersExecute.put("cod_det_oblig", StringUtils.leftPad(codDetOblig, 5, "0"));

		manager.executeTrx(this.samClient, parametersExecute);
	}

	public List<List<String>> consultaDetallesGastos(String idRendicion, String idGasto, String idObserv,
			List<DatosPantallaDinamica> fieldsScreen) throws TransactionException {
		log.info("Comienza llamado a trx para consultar el detalle obl ");
		ManagerTransaction manager = new ManagerTransaction(new SU59(fieldsScreen));
		Map parametersExecute = new HashMap();
		parametersExecute.put("id_rendicion", StringUtils.leftPad(idRendicion, 16, "0"));
		parametersExecute.put("id_gasto", StringUtils.leftPad(idGasto, 9, "0"));
		parametersExecute.put("id_observacion", "000000000");

		manager.executeTrx(this.samClient, parametersExecute);
		msg = (String) manager.getMensajeAviso();
		
		return (List<List<String>>) manager.getDataReturnList();
	}
	
	public List<DatosPantallaDinamica> consultaDetObligatorio(
			String idRendicion, String idGasto, String codMotivo,
			String codObserv) throws TransactionException {
		log.info("Comienza llamado a trx para consultar el detalle obl ");
		ManagerTransaction manager = new ManagerTransaction(new SU57());
		Map parametersExecute = new HashMap();
		parametersExecute.put("id_rendicion", idRendicion);
		parametersExecute.put("id_gasto", idGasto);
		parametersExecute.put("cod_motivo", codMotivo);
		parametersExecute.put("cod_observ", codObserv);

		manager.executeTrx(this.samClient, parametersExecute);
		return (List<DatosPantallaDinamica>) manager.getDataReturnList();

	}
	
	

	public Integer bajaGasto(String idGasto, String user, String idRendicion) throws TransactionException {
		log.info("Comienza llamado a trx para eliminar un gasto");
		ManagerTransaction manager = new ManagerTransaction(new SU56());
		Map<String, Object> parametersExecute = new HashMap<String, Object>();
		
		parametersExecute.put("opcion", "BAJA");
		parametersExecute.put("id_rendicion", String.format("%016d", Integer.parseInt(idRendicion)));
		parametersExecute.put("id_gasto", String.format("%09d", Integer.parseInt(idGasto)));
		
		manager.executeTrx(this.samClient, parametersExecute);
		Integer idGastoBorrado = (Integer) manager.getDataReturn();
		msg = (String) manager.getMensajeAviso();
		
		return idGastoBorrado;
	}

	public List<Cupones> getCuponUnico(String idRendicion, String idGasto, String idUser, String codMotivo) throws TransactionException {
		log.info("Comienza llamado a trx para traer el cupon");
		ManagerTransaction manager = new ManagerTransaction(new SU55());
		Map parametersExecute = new HashMap();
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
		parametersExecute.put("tipo_consult", "CUPT");

		manager.executeTrx(this.samClient, parametersExecute);
		List<Cupones> cupones = (List<Cupones>) manager.getDataReturnList();
		msg = (String) manager.getMensajeAviso();
		
		return cupones;
	}

	public String redistribuirGastos(String accion, String idRendicion,
			String gastoOriginal, String montoItems, String ccostoItems,
			String gastoItems, Usuario user) throws TransactionException {
		// TODO Auto-generated method stub
		log.info("Comienza llamado a trx para anular o redistribuir gastos ");
		ManagerTransaction manager = new ManagerTransaction(new SU67());
		Map parametersExecute = new HashMap();

		idRendicion = String.format("%016d", Integer.parseInt(idRendicion));
		parametersExecute.put("opcion", "DER");
		parametersExecute.put("subtran", accion);
		parametersExecute.put("idrend", idRendicion);
		parametersExecute.put("idgasto", String.format("%09d", Integer.parseInt(gastoOriginal)));
		parametersExecute.put("idusr", user.getIdUser());

		if (!accion.equalsIgnoreCase("ANU")) {

			List<String> items = this.formatearItems(montoItems, ccostoItems,
					gastoItems);

			for (int i = 0; i < items.size(); i++) {
				parametersExecute.put("linea" + (i + 1), items.get(i));
			}
		}

		manager.executeTrx(this.samClient, parametersExecute);
		return (String) manager.getMensajeAviso();

	}

	private List<String> formatearItems(String montoItems, String ccostoItems,
			String gastoItems) {
		// TODO Auto-generated method stub
		List<String> items = new ArrayList<String>();
		log.info("Se formatean los montos y centro costos para la distribucion");
		String[] montos = montoItems.split(";");
		String[] ccosto = ccostoItems.split(";");
		String[] codGasto = gastoItems.split(";");
		for (int i = 0; i < montos.length; i++) {

			String monto = montos[i];
			String decimal = "";
			String item = "";
			if (monto.contains(".")) {
				String format = monto.substring(monto.indexOf("."));
				if (format.length() < 3)
					decimal = monto.substring(monto.indexOf(".") + 1) + "0";
				else
					decimal = monto.substring(monto.indexOf(".") + 1);

				monto = monto.substring(0, monto.indexOf(".")) + decimal;
			} else {
				monto = monto + "00";
			}

			monto = String.format("%015d", Integer.parseInt(monto));
			item = String.format("%04d", Integer.parseInt(ccosto[i])) + monto
					+ codGasto[i];
			items.add(item);
		}
		return items;
	}
	
	public String getMsg (){
		return msg;
	}
}

