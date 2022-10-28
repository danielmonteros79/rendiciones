package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.Cupones;
import com.sa.entities.parametros.Resumen;
import com.sa.services.Transaction;

public class SU68 extends Transaction {
	private static final Log log = LogFactory.getLog(SU68.class);
	private List<Resumen> resumen = new ArrayList<Resumen>();
	public List<Cupones> listaCupones = new ArrayList<Cupones>();
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");

	public SU68() {
		this.PARAMETER_TRX = "SUM_CONS_CONSUMOS_GRALES";
		this.CURRENT_TRX = "SU68";
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
		if (parametersExecute.get("pantalla").equals("resumen")) {
			if (parametersExecute.get("lista") != null) {
				for (Object object : (List) parametersExecute.get("lista")) {
					String str = (String) ((BasicDynaBean) object).get("lista");
					Resumen resumenParams = new Resumen();

					resumenParams.setMonto((str.substring(86, 87).equals("-") ? "-" : "")
							+ str.substring(87, 102).replaceFirst("^0*", "") + "," + str.substring(102, 104));
					resumenParams.setFecha(sdfYMD.parse(str.substring(104, 114)));
					resumenParams.setMoneda(str.substring(124, 127));
					resumenParams.setEstablecimiento(str.substring(127, 157));
					resumenParams.setCupon(str.substring(14, 26).replaceFirst("^0*", ""));
					resumenParams.setEstado(str.substring(195, 207));
					resumenParams.setFechaDebito(sdfDMY.parse(str.substring(241, 251)));
					resumenParams.setIdRendicion(str.substring(251, 267).replaceFirst("^0*", ""));

					this.resumen.add(resumenParams);
				}
			}
			this.dataReturnList = resumen;
		} else {
			if (parametersExecute.get("lista") != null) {
				for (Object obj : (List) parametersExecute.get("lista")) {
					BasicDynaBean bean = (BasicDynaBean) obj;
					String str = (String) bean.get("lista");
					
					Cupones cupones = new Cupones();
					cupones.setTipo(str.substring(0, 1));
					cupones.setCodAdmin(str.substring(1, 4));
					cupones.setCuentaCredito(str.substring(4, 14));
					cupones.setNroCupon(str.substring(14, 26).replaceFirst("^0*", ""));
					cupones.setNroCliente(str.substring(26, 34));
					cupones.setNroTarjeta(str.substring(34, 50));
					cupones.setLiquidacionDebito(str.substring(53, 70));
					cupones.setLiquidacionCredito(str.substring(71, 87));
					cupones.setLiquidacionNeto(str.substring(87, 102).replaceFirst("^0*", "") + "," + str.substring(102, 104));
					cupones.setFechaPresentacion(str.substring(104, 114));
					cupones.setFechaCierre(str.substring(114, 124));
					cupones.setMoneda(str.substring(124, 127));
					cupones.setEstablecimiento(str.substring(127, 154).trim());
					cupones.setCodigoAutorizacion(str.substring(154, 162));
					cupones.setTipoMovimiento(str.substring(162, 164));
					cupones.setTipoConsumo(str.substring(164, 165));
					cupones.setMarcaFacturado(str.substring(165, 166));
					cupones.setAdelanto(str.substring(165, 167).equals("AD"));
					cupones.setNroCuponDebito(str.substring(169, 181).replaceFirst("^0*", ""));
	
					String cupDeb = str.substring(181, 193);
	
					cupones.setNroCuponCredito(cupDeb.trim().equalsIgnoreCase("") ? "0" : cupDeb.replaceFirst("^0*", ""));
					cupones.setMontoUtilizado(str.substring(207, 221).replaceFirst("^0*", "") +  str.substring(221, 222) + "," + str.substring(222, 224));
					cupones.setDisponible(str.substring(224, 238).replaceFirst("^0*", "") + str.substring(238, 239) + "," + str.substring(239, 241));
					
					if(!str.substring(86, 87).contains("-") && !str.substring(87, 104).equals("00000000000000000"))	
						listaCupones.add(cupones);
				}
			}
			this.dataReturnList = listaCupones;
		}
	}
}