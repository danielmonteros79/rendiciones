package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.Usuario;
import com.sa.services.Transaction;

public class SU81 extends Transaction {
	private static final Log log = LogFactory.getLog(SU81.class);
	private Usuario usuarioCheck = null;
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	public SU81() {
		this.PARAMETER_TRX = "SUM_ABM_PARAMS_DELEGACIONES";
		this.CURRENT_TRX = "SU81";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute)
			throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			if (parametersExecute.get("opcion").equals("CONS")) {
				mapData(parametersExecute);
			}

		} catch (Exception e) {
			throw new TransactionException(e);
		}
	}

	@Override
	public void executeTrx(IWebClient client, String... parameters)
			throws TransactionException {
	}

	@Override
	protected Map mapInputParams(String... parameters) {
		return null;
	}

	@Override
	protected void mapData(Map parametersExecute) {
		List<String> lista = new ArrayList<String>();

		// lista
		// .add("GABRIEL MIRANDA                                                            66660606");

		// parametersExecute.put("lista", lista);
		int index = 0;
		Usuario u = new Usuario((String) parametersExecute.get("id_reemplazo"), "",
				(String) parametersExecute.get("nombre"), Integer
						.valueOf((String) parametersExecute.get("centro_costo")),
				(String) parametersExecute.get("sector"), null);
//		for (Object object : (List) parametersExecute.get("lista")) {
//			try {
//				String str = (String) object;
//				int i = 0;
//
//				u = new Usuario(String.valueOf(parametersExecute
//						.get("cod_user")), "",
//						str.substring(i, i += 75).trim(), Integer.valueOf(str
//								.substring(i, i += 4).trim()), str.substring(i,
//								i += 4).trim(), null);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		this.usuarioCheck = u;
	}

	@Override
	public Object getDataReturn() {
		// TODO Auto-generated method stub
		return this.usuarioCheck;
	}

}