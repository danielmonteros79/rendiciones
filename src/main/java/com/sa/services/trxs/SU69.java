package com.sa.services.trxs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.ComboOpcion;
import com.sa.entities.parametros.Resumen;
import com.sa.services.Transaction;

public class SU69 extends Transaction  {
	private static final Log log = LogFactory.getLog(SU69.class);
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	public SU69() {
		this.PARAMETER_TRX = "SUM_CONS_RESUMENES_DET";
		this.CURRENT_TRX = "SU69";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			try {
				mapData(parametersExecute);
			} catch (Exception e) {
				log.error(e);
				throw new TransactionException("Error de mapeo " + this.CURRENT_TRX);
			}
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
	
	@Override
	protected void mapData(Map parametersExecute) throws Exception {
		if (parametersExecute.get("lista") != null) {
			for (Object object : (List) parametersExecute.get("lista")) {
				String str = (String) ((BasicDynaBean) object).get("lista");
				
				if (((String) parametersExecute.get("subtran")).equals("FEC"))
					this.dataReturnList.add(new ComboOpcion(str.substring(0, 10)));
				else {
					Resumen resumenParams = new Resumen();
					int i = 0;
					
					resumenParams.setFecha(sdfYMD.parse(str.substring(i, i += 10)));
					resumenParams.setCupon(str.substring(i, i += 12));
					resumenParams.setEstablecimiento(str.substring(i, i += 50));
					resumenParams.setMonto(str.substring(i, i += 15));
					resumenParams.setMoneda(str.substring(i, i += 3));
					if((str.substring(i, str.length()).equals("P"))){
					resumenParams.setEstado("PENDIENTE");
					}else if ((str.substring(i, str.length()).equals("D"))){
						resumenParams.setEstado("DEBITADO");
						}else if ((str.substring(i, str.length()).equals("R"))){
							resumenParams.setEstado("RENDIDO");
						}else if ((str.substring(i, str.length()).equals("E"))){
							resumenParams.setEstado("EXCEPTUADO");
						}else{
							resumenParams.setEstado("ESTADO MAL INFORMADO");
						}
					
					this.dataReturnList.add(resumenParams);
				}
			}
		}
	}
}