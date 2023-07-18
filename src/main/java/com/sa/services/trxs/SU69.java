package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.ComboOpcion;
import com.sa.entities.parametros.Resumen;
import com.sa.services.Transaction;
import com.sa.util.DateUtil;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import ar.org.bbva.util.DateUtils;

public class SU69 extends Transaction  {
	private static final Log log = LogFactory.getLog(SU69.class);
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	public SU69() {
		this.PARAMETER_TRX = "SUM_CONS_RESUMENES_DET";
		this.CURRENT_TRX = "SU69";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			try {
				mapData(parametersExecute);
			} catch (Exception e) {
				log.error("", e);
				throw new TransactionException("Error de mapeo " + this.CURRENT_TRX);
			}
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void mapData(Map<String, Object> parametersExecute) throws Exception {
		
		DateUtil dateUtil = new DateUtil();
		
		if (parametersExecute.get("lista") != null) {
			for (Object obj : (List) parametersExecute.get("lista")) {
				String str = getStrLista(obj);
				
				if (((String) parametersExecute.get("subtran")).equals("FEC"))
					this.dataReturnList.add(new ComboOpcion(str.substring(0, 10), 
							DateUtils.formatearFecha(str.substring(0, 10), dateUtil.getDfDDMMYYYYGuion(), dateUtil.getDfDDMMYYYY())));
				else {
					Resumen resumenParams = new Resumen();
					int i = 0;
					
					resumenParams.setFecha(sdfYMD.parse(str.substring(i, i += 10)));
					resumenParams.setCupon(str.substring(i, i += 12));
					resumenParams.setEstablecimiento(str.substring(i, i += 50));
					resumenParams.setMonto(str.substring(i, i += 15));
					resumenParams.setMoneda(str.substring(i, i += 3));
					
					if((str.substring(i, str.length()).equals("P")))
						resumenParams.setEstado("PENDIENTE");
					else if ((str.substring(i, str.length()).equals("D")))
						resumenParams.setEstado("DEBITADO");
					else if ((str.substring(i, str.length()).equals("R")))
						resumenParams.setEstado("RENDIDO");
					else if ((str.substring(i, str.length()).equals("E")))
						resumenParams.setEstado("EXCEPTUADO");
					else
						resumenParams.setEstado("ESTADO MAL INFORMADO");
					
					this.dataReturnList.add(resumenParams);
				}
			}
		}
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
	// Metodo que no se utilzia

	}
}