package com.sa.services.trxs;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class WM95 extends Transaction {
	private static final Log log = LogFactory.getLog(WM95.class);
	public static String NRO_TRAMITE = "              ";
	public static String USUARIO = "          ";
	public static String FECHA_GENERACION = "          ";
	public static String CENTRO_COSTOS = "    ";
	public static String CODIGO_MOTIVO = "    ";
	public static String DESC_MOTIVO = "                                                  ";
	public static String NOMBRE_EMPLEADO = "                                        ";
	public static String GLG = "  ";
	public static String REND_FECHA_DESDE = "          ";
	public static String REND_FECHA_HASTA = "          ";
	public static String DELIM_06_DATOS = "                                                                                                                                                                                                        ";
	public static String DELIM_03_MODO = "C";
	public static String DELIM_04_CON_ADEA = "C";
	public static String DELIM_04_SIN_ADEA = "S";
	public static String DELIM_05_CLASE = "CRE";

	public WM95() {
		this.PARAMETER_TRX = "SUM_OBTENER_IDU";
		this.CURRENT_TRX = "WM95";
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
		String retorno = (String) parametersExecute.get("idu");
		this.dataReturn = retorno + ";" + (String) parametersExecute.get("etiqueta");
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
	}
}
