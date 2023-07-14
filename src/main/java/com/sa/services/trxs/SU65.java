package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.CierreTarjeta;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import ar.org.bbva.util.DateUtils;

public class SU65 extends Transaction {
	private static final Log log = LogFactory.getLog(SU65.class);
	private static final String LISTA = "lista";
	
	public SU65() {
		this.PARAMETER_TRX = "SUM_ACTUALIZA_RESUMEN";
		this.CURRENT_TRX = "SU65";
	}
 
	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute)
			throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void mapData(Map<String, Object> parametersExecute) throws Exception {
		if (parametersExecute.get(LISTA) != null) {
			for (Object obj : (List) parametersExecute.get(LISTA)) {
				String str = getStrLista(obj);
				CierreTarjeta cd = new CierreTarjeta();
				int i = 0;
				cd.setUsuario(str.substring(i, i += 8));
				cd.setIdSecResumen(Integer.parseInt(str.substring(i, i += 16)));
				cd.setNroTarjeta(maskCardNumber(str.substring(i, i += 16),"XXXX-XXXX-XXXX-####"));
				cd.setFechaCupon(DateUtils.dfYYYYMMDD.parse(str.substring(i, i += 10)));
				cd.setCuponTarjeta(str.substring(i, i += 12));
				cd.setCuponAdmDev(str.substring(i, i += 12));
				cd.setCuponAdmCred(str.substring(i, i += 12));
				cd.setEstablecimiento(str.substring(i, i += 50));
				cd.setMontoCupon(Integer.parseInt(str.substring(i, i += 13))/100);
				cd.setMoneda(str.substring(i, i += 3));
				cd.setImporteGtosRend(str.substring(i, i += 13));
				cd.setFechaResumen(DateUtils.dfYYYYMMDD.parse(str.substring(i, i += 10)));
				cd.setEstadoResumen(str.substring(i, i += 1));
				cd.setFechaCierre(DateUtils.dfYYYYMMDD.parse(str.substring(i, i += 10)));
				cd.setFechaAlta(str.substring(i, i += 26));
				cd.setUserAlta(str.substring(i, i += 8));
				cd.setFechaUltModif(str.substring(i, i += 26));

				dataReturnList.add(cd);
			}
		}
	}
	
	/**
	* Enmascara tarjetas de credito
	* @param cardNumber
	* @param mask
	* @return
	* Ejemplo
	* (maskCardNumber("1234123412341234", "****-****-****-####"));
	*/
	public static String maskCardNumber(String cardNumber, String mask) {

	   // format the number
	   int index = 0;
	   StringBuilder maskedNumber = new StringBuilder();
	   for (int i = 0; i < mask.length(); i++) {
	       char c = mask.charAt(i);
	       if (c == '#') {
	           maskedNumber.append(cardNumber.charAt(index));
	           index++;
	       } else if (c == 'X') {
	           maskedNumber.append(c);
	           index++;
	       } else {
	           maskedNumber.append(c);
	       }
	   }

	   // return the masked number
	   return maskedNumber.toString();
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		List<String> list = new ArrayList<String>();
		
		list.add("A2345678000000000012345612345678901234562019-02-21123456789012123456789012123456789012ESTABLECIMIENTO DE 50 CARACTERES                  0000012345678ARS12345678901232019-03-16P2019-03-142018-12-26-15.10.28.785850A23456782019-12-26-15.10.28.785850");
		list.add("A2345678000000000012345712345678901234562019-02-21123456789012123456789012123456789012ESTABLECIMIENTO DE 50 CARACTERES                  0000012345678ARS12345678901232019-03-16P2019-03-142018-12-26-15.10.28.785850A23456782019-12-26-15.10.28.785850");
		
		parametersExecute.put(LISTA, list);
	}

}
