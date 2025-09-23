package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.CuadroDetallado;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU72 extends Transaction {
	private static final Log log = LogFactory.getLog(SU72.class);
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	public SU72() {
		this.PARAMETER_TRX = "SUM_REPORTERIA_CUADRO_DETALLE";
		this.CURRENT_TRX = "SU72";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		ejecutarTransaccion(client, this.PARAMETER_TRX, parametersExecute);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void mapData(Map<String, Object> parametersExecute) {

		if (parametersExecute.get("lista") != null) {
			for (Object obj : (List) parametersExecute.get("lista")) {
				try {
					String str = getStrLista(obj);
					CuadroDetallado cd = new CuadroDetallado();
					int i = 0;
					cd.setId(Integer.parseInt(str.substring(i, i += 16)));
					cd.setCodMotivo(str.substring(i, i += 4));
					cd.setMotivo(str.substring(i, i += 50));
					cd.setDescripcion(str.substring(i, i += 120));
					cd.setCodEstado(str.substring(i, i += 5));
					cd.setEstado(str.substring(i, i += 30));
					cd.setProxUsuario(str.substring(i, i += 8));
					cd.setFechaUltModif(sdfYMD.parse(str.substring(i, i += 10)));
					cd.setImporte(str.substring(i, i += 17));
					cd.setUsuario(str.substring(i));
	
					dataReturnList.add(cd);
				} catch (Exception e) {
					if (log.isDebugEnabled()) {
						log.debug("Error en mapData al procesar un elemento de lista", e);
					}
					log.error("Error en mapData: " + e.getMessage());
				}
			}
		}
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		//metodo no utilizado
	}
}