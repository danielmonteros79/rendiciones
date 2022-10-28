package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.CuadroDetallado;
import com.sa.services.Transaction;

public class SU72 extends Transaction {
	private static final Log log = LogFactory.getLog(SU72.class);
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	public SU72() {
		this.PARAMETER_TRX = "SUM_REPORTERIA_CUADRO_DETALLE";
		this.CURRENT_TRX = "SU72";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
		try {
 			execute(client, this.PARAMETER_TRX, parametersExecute);
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error(e);
			throw new TransactionException(e);
		}
	}

	@Override
	public void executeTrx(IWebClient client, String... parameters) throws TransactionException {

	}

	@Override
	protected Map mapInputParams(String... parameters) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void mapData(Map parametersExecute) {
		// List <String> list = new ArrayList<String>();
		// list.add("000000000000000100020000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000400005000000000000000000000000000006000000072017-02-1200000000000000009");
		
		if (parametersExecute.get("lista") != null) {
			for (Object object : (List) parametersExecute.get("lista")) {
				try {
					CuadroDetallado cd = new CuadroDetallado();
					String str = (String) ((BasicDynaBean) object).get("lista");
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
					e.printStackTrace();
				}
			}
		}
	}
}