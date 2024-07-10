package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sa.entities.Usuario;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU52 extends Transaction {

	public SU52() {
		this.PARAMETER_TRX = "SUM_CONS_USERDATA";
		this.CURRENT_TRX = "SU52";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			mapData(parametersExecute);
		} catch (Exception e) {
			throw new TransactionException(e);
		}
	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		log.info(this.CURRENT_TRX + " --> Se procede a mapear los datos de la transaccion");
		List<Usuario> delegados = new ArrayList<Usuario>();
		List retorno = (List) parametersExecute.get("lista");
		System.out.println(parametersExecute.get("cod_user") + "USUARIOO");
		System.out.println(parametersExecute.get("facultad") + "FACULTAS");
		System.out.println(parametersExecute.get("nombre_apellido") + "NOMBRE");
		System.out.println(parametersExecute.get("ctro_costos") + "COSTOS");
		System.out.println(parametersExecute.get("sector") + "SECTOR");
//			for (Object obj : retorno) {
//				String str = getStrLista(obj);
//				
//				if (str.substring(0, 8).trim().equals(parametersExecute.get("cod_user"))) {
//					delegados.add(new Usuario(str.substring(0, 8).trim(), (String) parametersExecute.get("facultad"), str.substring(8, str.length()).trim(),
//							Integer.valueOf((String) parametersExecute.get("ctro_costos")), (String) parametersExecute.get("sector"), null));
//				} else {
//					delegados.add(new Usuario(str.substring(0, 8).trim(), str.substring((str.length() - 1), str.length()).trim(), str.substring(8, 83).trim(),
//							Integer.valueOf(str.substring(83, 87)), str.substring(87, (str.length() - 1)), null));
//				}
//			}
			


		this.dataReturn = new Usuario(((String) parametersExecute.get("cod_user")).trim(), !parametersExecute.get("facultad").equals("") ? (String) parametersExecute.get("facultad"): "",
				((String) parametersExecute.get("nombre_apellido")).trim(),
				!parametersExecute.get("ctro_costos").equals("") ? Integer.valueOf((String) parametersExecute.get("ctro_costos")) : 0,
				!parametersExecute.get("sector").equals("") ? ((String) parametersExecute.get("sector")).trim() : "", delegados);
		


	}

	protected void hardcodear(Map<String, Object> parametersExecute) {
		List<String> retList = new ArrayList<String>();

		if (parametersExecute.get("cod_user").equals("A103555")) {
			parametersExecute.put("facultad", "NN");
			parametersExecute.put("cta_monetaria", "040099000561767");
			parametersExecute.put("sector", "1721");
			parametersExecute.put("nombre_apellido", "AUDRUICQ, DIEGO ANDRES");
			parametersExecute.put("ctro_costos", "8570");

			retList.add("A103555 AUDRUICQ, DIEGO ANDRES                                                     85701721S");
		} else {
			parametersExecute.put("facultad", "SS");
			parametersExecute.put("cta_monetaria", "040141000108834");
			parametersExecute.put("sector", "8568");
			parametersExecute.put("nombre_apellido", "RED DEVIL, ANALIA LAURA");
			parametersExecute.put("ctro_costos", "0099");

			retList.add("A126661 RED DEVIL, ANALIA LAURA                                                    00998568S");
			retList.add("A103555 AUDRUICQ, DIEGO ANDRES                                                     85701721S");
		}

		parametersExecute.put("lista", retList);
	}
}