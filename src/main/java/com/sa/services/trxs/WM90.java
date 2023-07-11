package com.sa.services.trxs;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;

import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class WM90 extends Transaction {

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
	public static String DELIM_06_DATOS1 = "                                                                                                    ";
	public static String DELIM_06_DATOS2 = "                                                                                                    ";
	public static String DELIM_03_MODO = "C";
	public static String DELIM_04_ADEA = "C";
	public static String DELIM_05_CLASE = "CTA";

	private final String[] FIELDS_INPUT = new String[] {};

	public WM90() {
		// TODO Auto-generated constructor stub
		this.PARAMETER_TRX = "SUM_OBTENER_IDU";
		this.CURRENT_TRX = "WM95";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
		// TODO Auto-generated method stub

		try {

			 execute(client, this.PARAMETER_TRX, parametersExecute);

//			parametersExecute.put("idu", "IDU0012345");
//			parametersExecute.put("etiqueta", "ADEA1234567");
			// Mapear los datos
			mapData(parametersExecute);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			throw new TransactionException(e);
		}

	}





	@Override
	protected void mapData(Map parametersExecute) {
		// TODO Auto-generated method stub
		String retorno = (String) parametersExecute.get("idu");
		this.dataReturn = retorno + ";" + (String) parametersExecute.get("etiqueta");

	}

	

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
