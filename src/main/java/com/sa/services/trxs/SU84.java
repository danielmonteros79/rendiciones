package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.parametros.ParametroGasto;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU84 extends Transaction {
	private static final Log log = LogFactory.getLog(SU84.class);
	public List<ParametroGasto> gastos = new ArrayList<ParametroGasto>();

	public SU84() {
		this.PARAMETER_TRX = "SUM_CONS_PARAMS_GASTOS";
		this.CURRENT_TRX = "SU84";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
	    try {
	        execute(client, this.PARAMETER_TRX, parametersExecute);
	        
	        mapData(parametersExecute);
	    } catch (Exception e) {
	        log.error("Error al ejecutar la transacción", e);
	        throw new TransactionException(e);
	    }
	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {

	    if (parametersExecute.get("cod_gasto").equals("") || parametersExecute.get("cod_gasto").equals(" ")) {
	        Object listaObj = parametersExecute.get("lista");
	        System.out.println("LISTAOBJ: " + parametersExecute.get("lista"));

	        if (listaObj == null || !(listaObj instanceof List<?>)) {
	            throw new IllegalArgumentException("El parámetro 'lista' no es una lista válida o es null");
	        }

	        List<?> lista = (List<?>) listaObj;

	        for (Object obj : lista) {
	            String str = getStrLista(obj);
	            System.out.println("STR: " + str);

	            if (str == null || str.length() < 189) {
	                throw new IllegalArgumentException("Cadena 'str' no tiene la longitud mínima requerida");
	            }

	            ParametroGasto gasto = new ParametroGasto();
	            int i = 0;

	            gasto.setGasto(str.substring(i, i += 4));
	            gasto.setDescripcionGasto(str.substring(i, i += 50));
	            gasto.setMotivo(str.substring(i, i += 4));
	            gasto.setDescripcionMotivo(str.substring(i, i += 50));
	            gasto.setRistra(str.substring(i, i += 69));
	            gasto.setBimon(str.substring(i, i += 1));
	            gasto.setComprob(str.substring(i, i += 4));
	            gasto.setAutoriz(str.substring(i, i += 2));
	            gasto.setObserv(str.substring(i, i += 4));
	            gasto.setEstado(str.substring(i, i += 1));

	            this.gastos.add(gasto);
	        }
	    } else {
	        String codMotivo = (String) parametersExecute.get("cod_motivo");

	        if (codMotivo == null || codMotivo.isEmpty()) {
	            throw new IllegalArgumentException("El parámetro 'cod_motivo' no puede ser nulo o vacío");
	        }

	        ParametroGasto gasto = new ParametroGasto();
	        gasto.setMotivo(codMotivo);

	        String descripcionMotivo = (String) parametersExecute.get("desc_motivo");
	        if (descripcionMotivo != null && !descripcionMotivo.isEmpty()) {
	            gasto.setDescripcionMotivo(descripcionMotivo);
	        } else {
	            throw new IllegalArgumentException("El parámetro 'desc_motivo' no puede ser nulo o vacío");
	        }

	        String codGasto = (String) parametersExecute.get("cod_gasto");
	        if (codGasto != null && !codGasto.isEmpty()) {
	            gasto.setGasto(codGasto);
	        }

	        String descripcionGasto = (String) parametersExecute.get("desc_gto");
	        if (descripcionGasto != null) {
	            gasto.setDescripcionGasto(descripcionGasto);
	        }

	        String estado = (String) parametersExecute.get("estado");
	        if (estado != null) {
	            gasto.setEstado(estado);
	        }
	        
	        String bimon = (String) parametersExecute.get("bimon");
	        if(bimon != null) {
	        	gasto.setBimon(bimon);
	        }
	        
	        String ristra = (String) parametersExecute.get("ristra");
	        if(ristra != null) {
	        	gasto.setRistra(ristra);
	        }

	        this.gastos.add(gasto);
	    }
	}


	@Override
	public List getDataReturnList() {
		return gastos;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		//metodo no utilizado
	}
}