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

	    String codUser = StringEscapeUtils.escapeHtml4((String) parametersExecute.get("cod_user"));
	    String facultad = StringEscapeUtils.escapeHtml4((String) parametersExecute.get("facultad"));
	    String nombreApellido = StringEscapeUtils.escapeHtml4((String) parametersExecute.get("nombre_apellido"));
	    String ctroCostos = StringEscapeUtils.escapeHtml4((String) parametersExecute.get("ctro_costos"));
	    String sector = StringEscapeUtils.escapeHtml4((String) parametersExecute.get("sector"));

	    List<Usuario> delegados = new ArrayList<>();
	    List retorno = (List) parametersExecute.get("lista");

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