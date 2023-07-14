package com.sa.services.trxs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.Cupones;
import com.sa.entities.parametros.Resumen;
import com.sa.services.Transaction;
import com.sa.util.DateUtil;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU68 extends Transaction {
	private static final Log log = LogFactory.getLog(SU68.class);
	private List<Resumen> resumen = new ArrayList<>();
	public List<Cupones> listaCupones = new ArrayList<>();
	private static final String MONTO_MIN = "montoMin";

	public SU68() {
		this.PARAMETER_TRX = "SUM_CONS_CONSUMOS_GRALES";
		this.CURRENT_TRX = "SU68";
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

	@Override
	protected void mapData(Map<String, Object> parametersExecute) throws Exception {
		if (parametersExecute.get("pantalla").equals("resumen")) {
			if (parametersExecute.get("lista") != null) {
				for (Object obj : (List) parametersExecute.get("lista")) {
					String str = getStrLista(obj);
					Resumen resumenParams = new Resumen();

					resumenParams.setMonto((str.substring(86, 87).equals("-") ? "-" : "")
							+ str.substring(87, 102).replaceFirst("^0*", "") + "," + str.substring(102, 104));
					resumenParams.setFecha(DateUtil.dfYYYYMMDD.parse(str.substring(104, 114)));
					resumenParams.setMoneda(str.substring(124, 127));
					resumenParams.setEstablecimiento(str.substring(127, 157));
					resumenParams.setCupon(str.substring(14, 26).replaceFirst("^0*", ""));
					resumenParams.setEstado(str.substring(195, 207));
					resumenParams.setFechaDebito(DateUtil.dfDDMMYYYY.parse(str.substring(241, 251)));
					resumenParams.setIdRendicion(str.substring(251, 267).replaceFirst("^0*", ""));

					this.resumen.add(resumenParams);
				}
			}
			this.dataReturnList = resumen;
		} else {
			if (parametersExecute.get("lista") != null) {
				for (Object obj : (List) parametersExecute.get("lista")) {
					String str = getStrLista(obj);
					Cupones cupon = new Cupones();
					cupon.setTipo(str.substring(0, 1));
					cupon.setCodAdmin(str.substring(1, 4));
					cupon.setCuentaCredito(str.substring(4, 14));
					cupon.setNroCupon(str.substring(14, 26).replaceFirst("^0*", ""));
					cupon.setNroCliente(str.substring(26, 34));
					cupon.setNroTarjeta(str.substring(34, 50));
					cupon.setLiquidacionDebito(str.substring(53, 70));
					cupon.setLiquidacionCredito(str.substring(71, 87));
					cupon.setLiquidacionNeto(str.substring(87, 102).replaceFirst("^0*", "") + "," + str.substring(102, 104));
					cupon.setFechaPresentacion(str.substring(104, 114));
					cupon.setFechaCierre(str.substring(114, 124));
					cupon.setMoneda(str.substring(124, 127));
					cupon.setEstablecimiento(str.substring(127, 154).trim());
					cupon.setCodigoAutorizacion(str.substring(154, 162));
					cupon.setTipoMovimiento(str.substring(162, 164));
					cupon.setTipoConsumo(str.substring(164, 165));
					cupon.setMarcaFacturado(str.substring(165, 166));
					cupon.setAdelanto(str.substring(165, 167).equals("AD"));
					cupon.setNroCuponDebito(str.substring(169, 181).replaceFirst("^0*", ""));
	
					String cupDeb = str.substring(181, 193);
	
					cupon.setNroCuponCredito(cupDeb.trim().equalsIgnoreCase("") ? "0" : cupDeb.replaceFirst("^0*", ""));
					cupon.setMontoUtilizado(str.substring(207, 221).replaceFirst("^0*", "") +  str.substring(221, 222) + "," + str.substring(222, 224));
					cupon.setDisponible(str.substring(224, 238).replaceFirst("^0*", "") + str.substring(238, 239) + "," + str.substring(239, 241));
					
					BigDecimal disponible = new BigDecimal(cupon.getDisponible().replace(",", ""));
					BigDecimal montoMin = (parametersExecute.get(MONTO_MIN) == null || parametersExecute.get(MONTO_MIN).equals("") ? BigDecimal.ZERO : 
						new BigDecimal(((String) parametersExecute.get(MONTO_MIN)).replace(",", "").trim()));
					String moneda = parametersExecute.get("moneda") == null ? "" : (String) parametersExecute.get("moneda");
					
					if(!str.substring(86, 87).contains("-") && !str.substring(87, 104).equals("00000000000000000") && disponible.compareTo(montoMin) > -1  &&
						(moneda.equals("") || cupon.getMoneda().equals(moneda)))
						listaCupones.add(cupon);
				}
			}
			this.dataReturnList = listaCupones;
		}
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		List<String> retList = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		
		list.add("J11900944188770000S1GFN930008642524857490000159003+00000000000200000+00000000000000000+000000000002000002018-11-262018-11-26ARSADELANTO ATM                     07509ADOS000588410096            P-PENDIENTE   000000000000000000000000000020000029/11/20180000000000000000");
		list.add("J11900944188770000S1GFN931008642524857490000159003+00000000000200000+00000000000000000+000000000002000002018-04-152018-04-26ARSADELANTO ATM                     07229ADOS000597797180            P-PENDIENTE   000000000000000000000000000020000029/11/20180000000000000000");
		list.add("J11900944188770000S1GFN931008642524857490000159003+00000000000180000+00000000000000000+000000000001800002018-04-162018-04-26ARSADELANTO ATM                     10452ADOS000597797181            P-PENDIENTE   000000000000100000000000000017000029/11/20180000000000000000");
		list.add("J11900944188770000S1GFN932008642524857490000159003+00000000000200000+00000000000000000+000000000002000002018-04-162018-04-26ARSADELANTO ATM                     06463ADOS000597418932            P-PENDIENTE   000000000000000000000000000020000029/11/20180000000000000000");
		list.add("J11900944188770000S1GFN932008642524857490000159003+00000000000110000+00000000000000000+000000000001100002018-04-162018-04-26ARSADELANTO ATM                     07933ADOS000597418933            P-PENDIENTE   000000000000000000000000000011000029/11/20180000000000000000");
		list.add("J11900944188770000S1GFN932008642524857490000159003+00000000000100000+00000000000000000+000000000001000002018-04-162018-04-26ARSADELANTO ATM                     08088ADOS000597418934            P-PENDIENTE   000000000000000000000000000010000029/11/20180000000000000000");
		list.add("J1190094418877000000001282008642524857490000159003+00000000000230000+00000000000000000+000000000002300002018-04-162018-04-26ARSTHEORY SA                        06079CNOS000586170268            P-PENDIENTE   000000000000000000000000000023000029/11/20180000000000000000");
		list.add("J1190094418877000000009694008642524857490000159003+00000000000152509+00000000000000000+000000000001525092018-04-162018-04-26ARSSHELL                            06853CNOS000591009886            P-PENDIENTE   000000000000000000000000000015250929/11/20180000000000000000");
		list.add("J1190094418877000017813473008642524857490000159003+00000000000072556+00000000000000000+000000000000725562018-04-162018-04-26USDHILTON CAPITAL                00005783CNIS000587083333            P-PENDIENTE   000000000000000000000000000007255629/11/20180000000000000000");
		list.add("J1190094418877000023533364008642524857490000159003+00000000000017381+00000000000000000+000000000000173812018-04-162018-04-26USDAMEX BARCELO                  00005510CNIS000586170274            P-PENDIENTE   000000000000000000000000000001738129/11/20180000000000000000");
		list.add("J1190094418877000024820131008642524857490000159003+00000000005629480+00000000000000000+000000000056294802018-04-162018-04-26ARSTURKISH AIRLINES BSP             04682CNOS000555243803            P-PENDIENTE   000000000000000000000000000562948029/11/20180000000000000000");
		list.add("J1190094418877000061889881008642524857490000159003+00000000005483820+00000000000000000+000000000054838202018-04-162018-04-26ARSIBERIA LINEAS AEREAS BSP         08962CNOS000568639248            P-PENDIENTE   000000000000000000000000000548382029/11/20180000000000000000");
		list.add("J1190094418877000065621409008642524857490000159003+00000000000154837+00000000000000000+000000000001548372018-04-162018-04-26USDAPPLE STORE R450              00005378CNIS000580108180            P-PENDIENTE   000000000000000000000000000015483729/11/20180000000000000000");
		
		for (String row : list) {
			if (parametersExecute.get("fepresd") == null ||
				(((String)parametersExecute.get("fepresd")).compareTo(row.substring(104, 114)) <= 0 &&
				 ((String)parametersExecute.get("fecpreh")).compareTo(row.substring(104, 114)) >= 0))
				retList.add(row);
		}
		
		parametersExecute.put("lista", retList);
	}
}