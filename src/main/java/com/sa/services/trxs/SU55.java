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
	private static final String LISTA2 = "lista2";
	private static final String ID_REND = "id_rendicion";
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

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
	    log.info("Mapeo SU55");
	    List<?> list = (List<?>) parametersExecute.get("lista");
	    List<?> lista2 = (List<?>) parametersExecute.get(LISTA2);

	    try {
	        if (lista2 == null || lista2.isEmpty()) {
	            mapGastos(list, parametersExecute);
	        } else {
	            mapCupones(lista2);
	        }

	        if (parametersExecute.containsKey(DERRAME)) {
	            parametersExecute.remove(DERRAME);
	        }
	    } catch (Exception e) {
	        log.error("Error mapeo de datos SU55", e);
	        e.printStackTrace();
	    }
	}

	private void mapGastos(List<?> list, Map<String, Object> parametersExecute) throws ParseException {
	    for (Object obj : list) {
	        String str = getStrLista(obj);
	        Gastos gasto = new Gastos();
	        gasto.setIdRendicion((String) parametersExecute.get(ID_REND));
	        gasto.setCodMotivo((String) parametersExecute.get("cod_motivo"));
	        gasto.setCostosDestino((String) parametersExecute.get("centro_costo"));
	        gasto.setIdGasto(parseIdGasto(str));
	        gasto.setNroGasto(parseNroGasto(str));
	        gasto.setDescGasto(parseDescGasto(str));
	        gasto.setMonto(parseMonto(str));
	        gasto.setMoneda(parseMoneda(str));
	        gasto.setFechagastos(parseFechaGastos(str));
	        gasto.setTipoComprobante(parseTipoComprobante(str));
	        gasto.setObs(parseObs(str));
	        gasto.setObsObligatoria(parseObsObligatoria(str));
	        gasto.setComprobante(parseComprobante(str));
	        gasto.setTarjeta(parseTarjeta(str));
	        gasto.setCuponGasto(parseCuponGasto(str));
	        gasto.setObservacionGasto(parseObservacionGasto(str));
	        gasto.setCuit(parseCuit(str));
	        gasto.setTipoFactura(parseTipoFactura(str));
	        gasto.setFactura(parseFactura(str));
	        gasto.setCentroCostoGasto(parseCentroCostoGasto(str));

	        if (parametersExecute.containsKey(DERRAME)) {
	            if (str.length() > 281) {
	                gasto.setIdGastoOriginal(parseIdGastoOriginal(str));
	                listaGastosRedistribuidos.add(gasto);
	            }
	        } else {
	            if (!(str.length() > 281)) {
	                listaGastos.add(gasto);
	            }
	        }
	    }
	}

	private void mapCupones(List<?> lista2) {
	    for (Object obj : lista2) {
	        String str = getStrLista(obj, LISTA2);
	        Cupones cupones = new Cupones();
	        cupones.setFechaPresentacion(parseFechaPresentacion(str));
	        cupones.setNroCupon(parseNroCupon(str));
	        cupones.setEstablecimiento(parseEstablecimiento(str));
	        cupones.setLiquidacionNeto(parseLiquidacionNeto(str));
	        cupones.setMoneda(parseMoneda(str));
	        listaCupones.add(cupones);
	    }
	}

	private String parseIdGasto(String str) {
	    return str.substring(0, 9).replaceFirst("^0*", "");
	}

	private String parseNroGasto(String str) {
	    return str.substring(9, 13);
	}

	private String parseDescGasto(String str) {
	    return str.substring(13, 63).trim();
	}

	private String parseMonto(String str) {
	    return str.substring(63, 80);
	}

	private String parseMoneda(String str) {
	    return str.substring(80, 83);
	}

	private String parseFechaGastos(String str) throws ParseException {
	    String fechaG = str.substring(83, 93);
	    if (fechaG != null && !fechaG.equalsIgnoreCase("")) {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = formatter.parse(fechaG);
	        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	        fechaG = df.format(date).trim();
	    }
	    return fechaG;
	}

	private String parseTipoComprobante(String str) {
	    return str.substring(93, 97);
	}

	private String parseObs(String str) {
	    return str.substring(112, 117).trim();
	}

	private String parseObsObligatoria(String str) {
	    return str.substring(117, 118).trim();
	}

	private String parseComprobante(String str) {
	    return str.substring(97, 112).trim();
	}

	private String parseTarjeta(String str) {
	    return str.substring(118, 119);
	}

	private String parseCuponGasto(String str) {
	    return str.substring(119, 131).replaceFirst("^0*", "");
	}

	private String parseObservacionGasto(String str) {
	    return str.substring(131, 251).trim();
	}

	private String parseCuit(String str) {
	    return str.substring(251, 262);
	}

	private String parseTipoFactura(String str) {
	    return str.substring(264, 265);
	}

	private String parseFactura(String str) {
	    String factura = str.substring(265, 277);
	    return factura.equals("000000000000") ? "" : factura;
	}

	private String parseCentroCostoGasto(String str) {
	    return str.substring(277, 281).trim();
	}

	private String parseIdGastoOriginal(String str) {
	    return str.substring(281, str.length()).trim().replaceFirst("^0*", "");
	}

	private String parseFechaPresentacion(String str) {
	    return str.substring(0, 10);
	}

	private String parseNroCupon(String str) {
	    return str.substring(10, 22);
	}

	private String parseEstablecimiento(String str) {
	    return str.substring(22, 52);
	}

	private String parseLiquidacionNeto(String str) {
	    return str.substring(52, 69);
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
//		List<String> retList = new ArrayList<String>();
//		
//		Map<String, List<String>> mapRendGastos = new HashMap<String, List<String>>();
//		List<String> gastos = new ArrayList<String>();
//		gastos.add("0000000010214COMIDAS                                                        4,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
//		gastos.add("0000000020215ALQUILER SALON                                                 3,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
//		gastos.add("0000000030216ALQUILER EQUIPOS                                               3,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
//		gastos.add("0000000040217SERVICIO DE CATERING                                           3,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
//		gastos.add("0000000050218VARIOS                                                         3,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
//		gastos.add("0000000070214COMIDAS                                                      500,00ARS2018-04-160001FACTU          00204NS0000S1GFN931                                                                                                                                      0000000000008570");
//		mapRendGastos.put("0000000000001106", gastos);
//		
//		gastos = new ArrayList<String>();
//		gastos.add("0000000010219COMBUSTIBLE                                                    3,00ARS2018-11-260001FACTU          00205SN000000000000x                                                                                                                                     0000000000008570");
//		gastos.add("0000000040222LAVADO                                                         3,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
//		gastos.add("0000000050223SERVICE CONCESIONARIO                                          4,00ARS2018-11-260001FACTU          00000NN000000000000                                                                                                                                      0000000000008570");
//		gastos.add("0000000060219COMBUSTIBLE                                                    6,00ARS2018-11-260001FACTU          00205NN000000000000df                                                                                                                                    0000000000008570");
//		gastos.add("0000000080219COMBUSTIBLE                                                    5,00ARS2018-11-260001FACTU          00205NN000000000000gfhfgh                                                                                                                                0000000000008570");
//		gastos.add("0000000090219COMBUSTIBLE                                                   54,00ARS2018-11-260001FACTU          00205NN000000000000x                                                                                                                                     0000000000008570");
//		gastos.add("0000000100219COMBUSTIBLE                                                    5,00ARS2018-11-260001FACTU          00205NN000000000000fg                                                                                                                                    0000000000008570");
//		gastos.add("0000000110219COMBUSTIBLE                                                  553,00ARS2018-11-260001FACTU          00205SN000000000000f                                                                                                                                     0000000000008570");
//		mapRendGastos.put("0000000000001107", gastos);
//		
//		gastos = new ArrayList<String>();
//		gastos.add("0000000010225BIEN DE USO                                                    5,65ARS2018-11-260001FACTU          00207SN000000000000                                                                                                                                      0000000000008570");
//		gastos.add("0000000020225BIEN DE USO                                                 2000,15ARS2018-04-160001FACTU          00207NS0000S1GFN930asd                                                                                                                                   0000000000008570");
//		mapRendGastos.put("0000000000001108", gastos);
//		
//		if (parametersExecute.get("tipo_consult") != null && parametersExecute.get("tipo_consult").equals("CUPT")) {
//			retList.add("2018-04-160000S1GFN930ADELANTO ATM                            2000,00ARS8570");
//			parametersExecute.put(LISTA2, retList);
//		} else {
//			if (parametersExecute.get("id_gasto").equals(""))
//				retList = mapRendGastos.get(parametersExecute.get(ID_REND)) != null ? mapRendGastos.get(parametersExecute.get(ID_REND)) :
//					new ArrayList<String>();
//			else {
//				for (String row : mapRendGastos.get(parametersExecute.get(ID_REND))) {
//					if (Integer.parseInt(row.substring(0, 9)) == Integer.parseInt((String) parametersExecute.get("id_gasto"))) {
//						retList = new ArrayList<String>();
//						retList.add(row);
//						break;
//					}
//				}
//			}
//			
//			parametersExecute.put("lista", retList);
//		}
//		
//		parametersExecute.put("centro_costo", "5432");
	}
}
