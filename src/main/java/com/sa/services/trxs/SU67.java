package com.sa.services.trxs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.CierreTarjeta;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import ar.org.bbva.util.DateUtils;

public class SU67 extends Transaction {
	private static final Log log = LogFactory.getLog(SU67.class);

	public SU67() {
		this.PARAMETER_TRX = "SUM_REDISTRIBUCION_GASTO";
		this.CURRENT_TRX = "SU67";
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
	protected void mapData(Map<String, Object> parametersExecute) throws ParseException {
		CierreTarjeta cd = new CierreTarjeta();
		if (parametersExecute.get("lista") != null) {
			for (Object obj : (List) parametersExecute.get("lista")) {
				String str = getStrLista(obj);
				int i = 0;

				SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd");
				cd.setUsuario(str.substring(i, i += 8));
				//cd.setIdRend(str.substring(8,25));
				cd.setFechaCupon(toDate.parse(str.substring(70, 84)));
				cd.setEstablecimiento(str.substring(82, 112));
				cd.setMoneda(str.substring(112, 115));
				cd.setEstadoResumen(str.substring(115, 120));

				//cd.setMontoCupon(Integer.parseInt(str.substring(71, 93)));
				cd.setMontoCupon(Double.valueOf((String) parametersExecute.get("importe")));
				System.out.println(str + " datos de cupon ahh");
		
				dataReturnList.add(cd);
				
			}
			
		}
		
	
		
		
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
	}
}