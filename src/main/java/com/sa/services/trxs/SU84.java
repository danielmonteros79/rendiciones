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
	    Object listaObj = parametersExecute.get("lista");
	    
	    if (listaObj == null || !(listaObj instanceof List<?>)) {
	        //throw new IllegalArgumentException("El parámetro 'lista' no es una lista válida o es null");
	    	return;
	    }
	    
	    List<?> lista = (List<?>) listaObj;

	    for (Object obj : lista) {
	        String str = getStrLista(obj);
	        
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