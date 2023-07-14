package com.sa.services.trxs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
		List list = (List) parametersExecute.get("lista");
		List lista2 = (List) parametersExecute.get(LISTA2);
		
		try {
			if (lista2 == null || lista2.isEmpty()) {
				for (Object obj : list) {
					String str = getStrLista(obj);
					Gastos gasto = new Gastos();
					gasto.setIdRendicion((String)parametersExecute.get(ID_REND));
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

					if (parametersExecute.containsKey("DERRAME")) {
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
			} else {
				for (Object obj : lista2) {
					String str = getStrLista(obj, LISTA2);
					Cupones cupones = new Cupones();
					cupones.setFechaPresentacion(str.substring(0, 10));
					cupones.setNroCupon(str.substring(10, 22));
					cupones.setEstablecimiento(str.substring(22, 52));
					cupones.setLiquidacionNeto(str.substring(52, 69));
					cupones.setMoneda(str.substring(69, 72));
					listaCupones.add(cupones);
				}

			}
			if (parametersExecute.containsKey("DERRAME")) {
				parametersExecute.remove("DERRAME");
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
		List<String> retList = new ArrayList<String>();
		
		Map<String, List<String>> mapRendGastos = new HashMap<String, List<String>>();
		List<String> gastos = new ArrayList<String>();
		gastos.add("0000000010214COMIDAS                                                        4,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
		gastos.add("0000000020215ALQUILER SALON                                                 3,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
		gastos.add("0000000030216ALQUILER EQUIPOS                                               3,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
		gastos.add("0000000040217SERVICIO DE CATERING                                           3,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
		gastos.add("0000000050218VARIOS                                                         3,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
		gastos.add("0000000070214COMIDAS                                                      500,00ARS2018-04-160001FACTU          00204NS0000S1GFN931                                                                                                                                      0000000000008570");
		mapRendGastos.put("0000000000001106", gastos);
		
		gastos = new ArrayList<String>();
		gastos.add("0000000010219COMBUSTIBLE                                                    3,00ARS2018-11-260001FACTU          00205SN000000000000x                                                                                                                                     0000000000008570");
		gastos.add("0000000040222LAVADO                                                         3,00ARS2018-11-260001FACTU          00204NN000000000000                                                                                                                                      0000000000008570");
		gastos.add("0000000050223SERVICE CONCESIONARIO                                          4,00ARS2018-11-260001FACTU          00000NN000000000000                                                                                                                                      0000000000008570");
		gastos.add("0000000060219COMBUSTIBLE                                                    6,00ARS2018-11-260001FACTU          00205NN000000000000df                                                                                                                                    0000000000008570");
		gastos.add("0000000080219COMBUSTIBLE                                                    5,00ARS2018-11-260001FACTU          00205NN000000000000gfhfgh                                                                                                                                0000000000008570");
		gastos.add("0000000090219COMBUSTIBLE                                                   54,00ARS2018-11-260001FACTU          00205NN000000000000x                                                                                                                                     0000000000008570");
		gastos.add("0000000100219COMBUSTIBLE                                                    5,00ARS2018-11-260001FACTU          00205NN000000000000fg                                                                                                                                    0000000000008570");
		gastos.add("0000000110219COMBUSTIBLE                                                  553,00ARS2018-11-260001FACTU          00205SN000000000000f                                                                                                                                     0000000000008570");
		mapRendGastos.put("0000000000001107", gastos);
		
		gastos = new ArrayList<String>();
		gastos.add("0000000010225BIEN DE USO                                                    5,65ARS2018-11-260001FACTU          00207SN000000000000                                                                                                                                      0000000000008570");
		gastos.add("0000000020225BIEN DE USO                                                 2000,15ARS2018-04-160001FACTU          00207NS0000S1GFN930asd                                                                                                                                   0000000000008570");
		mapRendGastos.put("0000000000001108", gastos);
		
		if (parametersExecute.get("tipo_consult") != null && parametersExecute.get("tipo_consult").equals("CUPT")) {
			retList.add("2018-04-160000S1GFN930ADELANTO ATM                            2000,00ARS8570");
			parametersExecute.put(LISTA2, retList);
		} else {
			if (parametersExecute.get("id_gasto").equals(""))
				retList = mapRendGastos.get(parametersExecute.get(ID_REND)) != null ? mapRendGastos.get(parametersExecute.get(ID_REND)) :
					new ArrayList<String>();
			else {
				for (String row : mapRendGastos.get(parametersExecute.get(ID_REND))) {
					if (Integer.parseInt(row.substring(0, 9)) == Integer.parseInt((String) parametersExecute.get("id_gasto"))) {
						retList = new ArrayList<String>();
						retList.add(row);
						break;
					}
				}
			}
			
			parametersExecute.put("lista", retList);
		}
		
		parametersExecute.put("centro_costo", "5432");
	}
}
