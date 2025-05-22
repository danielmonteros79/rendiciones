package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sa.entities.Usuario;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import org.apache.commons.text.StringEscapeUtils;

public class SU52 extends Transaction {
	
	private final String COD_USER = "cod_user";
	private final String FACULTAD = "facultad";
	private final String NOMBRE_APELLIDO = "nombre_apellido";
	private final String CTRO_COSTOS = "ctro_costos";
	private final String SECTOR = "sector";

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
	    log.info(this.CURRENT_TRX + " --> Mapeando datos de la transacción");
	    
	    String codUser = StringEscapeUtils.escapeHtml4((String) parametersExecute.get(COD_USER));
	    String facultad = StringEscapeUtils.escapeHtml4((String) parametersExecute.get(FACULTAD));
	    String nombreApellido = StringEscapeUtils.escapeHtml4((String) parametersExecute.get(NOMBRE_APELLIDO));
	    String ctroCostos = StringEscapeUtils.escapeHtml4((String) parametersExecute.get(CTRO_COSTOS));
	    String sector = StringEscapeUtils.escapeHtml4((String) parametersExecute.get(SECTOR));
	    

	    List<Usuario> delegados = new ArrayList<>();
	    List retorno = (List) parametersExecute.get("lista");
	    
		for (Object obj : retorno) {
			String str = getStrLista(obj);
			
			if (str.substring(0, 8).trim().equals(parametersExecute.get(COD_USER))) {
				delegados.add(new Usuario(str.substring(0, 8).trim(), (String) parametersExecute.get(FACULTAD), str.substring(8, str.length()).trim(),
						Integer.valueOf((String) parametersExecute.get(CTRO_COSTOS)), (String) parametersExecute.get(SECTOR), null));
			} else {
				delegados.add(new Usuario(str.substring(0, 8).trim(), str.substring((str.length() - 1), str.length()).trim(), str.substring(8, 83).trim(),
						Integer.valueOf(str.substring(83, 87)), str.substring(87, (str.length() - 1)), null));
			}
		}

	    log.debug("Usuario: " + codUser);
	    log.debug("Facultad: " + facultad);
	    log.debug("Nombre y Apellido: " + nombreApellido);
	    log.debug("Centro de Costos: " + ctroCostos);
	    log.debug("Sector: " + sector);

	    this.dataReturn = new Usuario(
	        codUser,
	        facultad.isEmpty() ? "" : facultad,
	        nombreApellido,
	        ctroCostos.isEmpty() ? 0 : Integer.parseInt(ctroCostos),
	        sector.isEmpty() ? "" : sector,
	        delegados
	    );
	}


	protected void hardcodear(Map<String, Object> parametersExecute) {
		List<String> retList = new ArrayList<String>();

		if (parametersExecute.get(COD_USER).equals("A103555")) {
			parametersExecute.put(FACULTAD, "NN");
			parametersExecute.put("cta_monetaria", "040099000561767");
			parametersExecute.put(SECTOR, "1721");
			parametersExecute.put(NOMBRE_APELLIDO, "AUDRUICQ, DIEGO ANDRES");
			parametersExecute.put(CTRO_COSTOS, "8570");

			retList.add("A103555 AUDRUICQ, DIEGO ANDRES                                                     85701721S");
		} else {
			parametersExecute.put(FACULTAD, "SS");
			parametersExecute.put("cta_monetaria", "040141000108834");
			parametersExecute.put(SECTOR, "8568");
			parametersExecute.put(NOMBRE_APELLIDO, "RED DEVIL, ANALIA LAURA");
			parametersExecute.put(CTRO_COSTOS, "0099");

			retList.add("A126661 RED DEVIL, ANALIA LAURA                                                    00998568S");
			retList.add("A103555 AUDRUICQ, DIEGO ANDRES                                                     85701721S");
		}

		parametersExecute.put("lista", retList);
	}
}