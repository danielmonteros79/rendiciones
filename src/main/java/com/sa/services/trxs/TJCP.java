package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;

import com.sa.entities.ComboOpcion2;
import com.sa.entities.ComboGasto;
import com.sa.entities.Cupones;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class TJCP extends Transaction {
	
	public List<Cupones> listaCupones = new ArrayList<Cupones>();
	public final static String OPCION_USU = "USU";
	public final static String SUB_TRAN = "MOV";
	public final static String COD_APLI = "SU";

	private final String[] FIELDS_INPUT = new String[] {};

	public TJCP() {
		// TODO Auto-generated constructor stub
		this.PARAMETER_TRX = "SUM_GET_CUPONES_TJ";
		this.CURRENT_TRX = "TJCP";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute)
			throws TransactionException {
		// TODO Auto-generated method stub
		try {

			execute(client, this.PARAMETER_TRX, parametersExecute);
			mapData(parametersExecute);

			// Mapear los datos

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			throw new TransactionException(e);
		}

	}




	@Override
	protected void mapData(Map parametersExecute) {
		log.info("Mapeo TJCP");

		// TODO Auto-generated method stub
		List listCupones = parametersExecute.get("lista") != null ? (List) parametersExecute
				.get("lista")
				: new ArrayList();

		for (Object obj : listCupones) {

			// Object object;
			BasicDynaBean bean = (BasicDynaBean) obj;
			String str = (String) bean.get("lista");
			// parametersExecute = new HashMap();

			Cupones cupones = new Cupones();
			cupones.setTipo(((String) str).substring(0, 1));
			cupones.setCodAdmin(((String) str).substring(1, 4));
			cupones.setCuentaCredito(((String) str).substring(4, 14));
			cupones.setNroCupon(((String) str).substring(14, 26).replaceFirst(
					"^0*", ""));
			cupones.setNroCliente(((String) str).substring(26, 34));
			cupones.setNroTarjeta(((String) str).substring(34, 50));
			cupones.setLiquidacionDebito(((String) str).substring(53, 70));
			cupones.setLiquidacionCredito(((String) str).substring(71, 87));
			cupones.setLiquidacionNeto(((String) str).substring(87, 102)
					.replaceFirst("^0*", "")+ ","+((String) str).substring(102, 104));
			cupones.setFechaPresentacion(((String) str).substring(104, 114));
			cupones.setFechaCierre(((String) str).substring(114, 124));
			cupones.setMoneda(((String) str).substring(124, 127));
			cupones.setEstablecimiento(((String) str).substring(127, 154)
					.trim());
			cupones.setCodigoAutorizacion(((String) str).substring(154, 162));
			cupones.setTipoMovimiento(((String) str).substring(162, 164));
			cupones.setTipoConsumo(((String) str).substring(164, 165));
			cupones.setMarcaFacturado(((String) str).substring(165, 166));
			cupones.setNroCuponDebito(((String) str).substring(169, 181).replaceFirst("^0*", ""));
			
			String cupDeb = ((String) str).substring(181, str
					.length());
			
			cupones.setNroCuponCredito( cupDeb.equalsIgnoreCase("") ? "0" : cupDeb.replaceFirst("^0*", ""));
			// cupones.setDisponible(((String) object).substring(190, 240));
			listaCupones.add(cupones);
		}
		this.dataReturnList = listaCupones;
	}

	

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
