package com.sa.services.trxs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sa.entities.Cupones;
import com.sa.entities.Gastos;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU55 extends Transaction {
	public List<Gastos> listaGastos = new ArrayList<>();
	public List<Gastos> listaGastosRedistribuidos = new ArrayList<>();
	public List<Cupones> listaCupones = new ArrayList<>();
	private static final String DERRAME = "DERRAME";

	public SU55() {
		this.PARAMETER_TRX = "SUM_CONS_DET_GASTOS_REND";
		this.CURRENT_TRX = "SU55";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			if (!e.getMessage().contains("INEX"))
				throw new TransactionException(e);
		}

	}
	
	void setInfoGasto(String str, Gastos gasto, Map<String, Object> parametersExecute) throws ParseException {
		
		gasto.setIdRendicion((String)parametersExecute.get("id_rendicion"));
		gasto.setCodMotivo((String)parametersExecute.get("cod_motivo"));
		gasto.setCostosDestino((String)parametersExecute.get("centro_costo"));
		gasto.setIdGasto(str.substring(0, 9).replaceFirst("^0*", ""));

		gasto.setNroGasto(str.substring(9, 13));
		gasto.setDescGasto(str.substring(13, 63).trim());
		gasto.setMonto(str.substring(63, 80));// .replaceFirst("^0*",
		// ""));
		gasto.setMoneda(str.substring(80, 83));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String fechaG = (str.substring(83, 93));
		Date date = null;
		if (fechaG != null && !fechaG.equalsIgnoreCase("")) {
			date = formatter.parse(fechaG);
		}
		if (date != null) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");

			fechaG = df.format(date).trim();
		}
		gasto.setFechagastos(fechaG);
		gasto.setTipoComprobante(str.substring(93, 97));
		// gasto.setObs("00003");//
		gasto.setObs(str.substring(112, 117).trim());

		// gasto.setObsObligatoria("S");
		gasto.setObsObligatoria(str.substring(117, 118).trim());// "S");
		// gasto.setTarjeta("N");
		gasto.setComprobante(str.substring(97, 112).trim());

		gasto.setTarjeta(str.substring(118, 119));
		gasto.setCuponGasto(str.substring(119, 131).replaceFirst("^0*", ""));
		// gasto.set(str.substring(132,135)
		// .replaceFirst("^0*", ""));
		gasto.setObservacionGasto(str.substring(131, 251).trim());
		gasto.setCuit(str.substring(251, 262));
		gasto.setTipoFactura(str.substring(264, 265));
		gasto.setFactura(str.substring(265, 277).equals("000000000000") ? "" : str.substring(265, 277));
		gasto.setCentroCostoGasto(str.substring(277, 281).trim());
		
		if (parametersExecute.containsKey(DERRAME)) {
			if (str.length() > 281) {
				gasto.setIdGastoOriginal(str.substring(281, str.length()).trim().replaceFirst("^0*", ""));
				listaGastosRedistribuidos.add(gasto);
			}
		} else {
			if (!(str.length() > 281)) {
				listaGastos.add(gasto);
			}
		}

	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		log.info("Mapeo SU55");
		List list = (List) parametersExecute.get("lista");
		List lista2 = (List) parametersExecute.get("lista2");
		
		try {
			if (lista2 == null || lista2.isEmpty()) {
				for (Object obj : list) {
					String str = getStrLista(obj);
					Gastos gasto = new Gastos();
					
					setInfoGasto(str, gasto, parametersExecute);
					
				}
			} else {
				for (Object obj : lista2) {
					String str = getStrLista(obj, "lista2");
					Cupones cupones = new Cupones();
					cupones.setFechaPresentacion(str.substring(0, 10));
					cupones.setNroCupon(str.substring(10, 22));
					cupones.setEstablecimiento(str.substring(22, 52));
					cupones.setLiquidacionNeto(str.substring(52, 69));
					cupones.setMoneda(str.substring(69, 72));
					listaCupones.add(cupones);
				}

			}
			if (parametersExecute.containsKey(DERRAME)) {
				parametersExecute.remove(DERRAME);
			}
		} catch (Exception e) {
			log.error("Error mapeo de datos SU55", e);
			e.printStackTrace();
		}
	}

	@Override
	public List getDataReturnList() {
		log.info("Se da return al listado de rendiciones");
		if (listaCupones == null || listaCupones.isEmpty()) {
			if (listaGastos == null || listaGastos.isEmpty()) {
				return listaGastosRedistribuidos;
			}
			return listaGastos;
		} else {
			return listaCupones;
		}
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
//		metodo no utilizado
	}
}
