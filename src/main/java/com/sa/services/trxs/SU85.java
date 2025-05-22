package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.OSCAR;
import com.sa.entities.parametros.ParametroGasto;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU85 extends Transaction {
	private static final Log log = LogFactory.getLog(SU85.class);
	public static final String OPCION_MODIFICAR = "MODI";
  private List<ParametroGasto> gastos = new ArrayList<>();
	ParametroGasto gastoReturn = new ParametroGasto();

	public SU85() {
		this.PARAMETER_TRX = "SUM_ABM_PARAMS_GASTOS";
		this.CURRENT_TRX = "SU85";
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
    String modo = (String) parametersExecute.get("modo");

    if (modo.equals("I")) {
        handleInsertMode(parametersExecute);
    }
}

private void handleInsertMode(Map<String, Object> parametersExecute) {
    String opcion = (String) parametersExecute.get("opcion");

    if (opcion.equals("ALTA") || opcion.equals("MODI")) {
        List<Object> lista = (List<Object>) parametersExecute.get("lista");

        for (Object obj : lista) {
            try {
                String str = getStrLista(obj);
                this.getDataReturnList().add(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    if (opcion.equals("MODI")) {
        setGastoReturnValues(parametersExecute);
    }

    if (opcion.equals("BAJA")) {
        setGastoReturnValues(parametersExecute);
    }
}

private void setGastoReturnValues(Map<String, Object> parametersExecute) {
    gastoReturn.setOscar(new OSCAR((String) parametersExecute.get("oscar")));
    gastoReturn.setAntiguedad(((String) parametersExecute.get("antig")));
    gastoReturn.setBimon((String) parametersExecute.get("bimon"));
    gastoReturn.setDescripcionGasto((String) parametersExecute.get("desc_gasto"));
    gastoReturn.setObserv((String) parametersExecute.get("observ"));
    gastoReturn.setEstado((String) parametersExecute.get("estado"));
    gastoReturn.setCcostos((String) parametersExecute.get("cent_costo"));
    gastoReturn.setComprob((String) parametersExecute.get("compte"));
    gastoReturn.setPlazoAprob((String) parametersExecute.get("plazo_ap"));
    gastoReturn.setRistra((String) parametersExecute.get("ristra"));
    gastoReturn.setMaInclExcl((String) parametersExecute.get("inc_excl"));
    gastoReturn.setNivelIngreso((String) parametersExecute.get("ni_ing"));

    List<String> centrosCosto = extractCentrosCosto(parametersExecute);
    gastoReturn.setCentrosCosto(centrosCosto);
}

private List<String> extractCentrosCosto(Map<String, Object> parametersExecute) {
    List<String> centrosCosto = new ArrayList<>();

    String vcccost = (String) parametersExecute.get("vcccost");
    if (vcccost != null) {
        int index = 0;
        while (index < vcccost.length()) {
            centrosCosto.add(vcccost.substring(index, Math.min(index + 4, vcccost.length())));
            index += 4;
        }
    }

    return centrosCosto;
}



	@Override
	public List getDataReturnList() {
		return gastos;
	}

	@Override
	public Object getDataReturn() {
		return this.gastoReturn;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		//metodo no utilizado
	}
}
