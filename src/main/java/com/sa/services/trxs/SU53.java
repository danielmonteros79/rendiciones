package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sa.entities.Rendicion;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU53 extends Transaction {
	public List<Rendicion> listaRendiciones = new ArrayList<>();
	private static final String AVISO = "aviso";
	private static final String DES_EST_REND = "desc_est_rend";
	private static final String FEC_ULT_MOD = "fec_ult_mod";
	
	public SU53() {
		this.PARAMETER_TRX = "SUM_CONS_RENDICIONES";
		this.CURRENT_TRX = "SU53";
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
	    List<?> lista = (List<?>) parametersExecute.get("lista");

	    if (lista != null) {
	        SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd");

	        for (Object obj : lista) {
	            String str = getStrLista(obj);
	            Rendicion rendicion = new Rendicion();

	            String aviso = (String) parametersExecute.getOrDefault(AVISO, "");
	            rendicion.setAviso(aviso);

	            rendicion.setId(parseId(str));
	            rendicion.setCodMotivo(parseCodMotivo(str));
	            rendicion.setMotivo(parseMotivo(str));
	            rendicion.setCostosDestino(parseCostosDestino(str));
	            rendicion.setDescripcion(parseDescripcion(str));
	            rendicion.setEstado(parseEstado(str));
	            rendicion.setFechaDesde(toDate.parse(parseFechaDesde(str)));
	            rendicion.setFechaHasta(toDate.parse(parseFechaHasta(str)));
	            rendicion.setImporte(parseImporte(str));
	            rendicion.setIdu(parseIdu(str));
	            rendicion.setAdea(parseAdea(str));
	            rendicion.setDescripcionEstado(parseDescripcionEstado(parametersExecute));
	            rendicion.setUsuarioAprobador(parseUsuarioAprobador(parametersExecute));
	            rendicion.setCodUsuarioAprobador(parseCodUsuarioAprobador(parametersExecute));
	            rendicion.setMotivoRechazo(parseMotivoRechazo(parametersExecute));
	            rendicion.setFechaUltimaModificacion(parseFechaUltimaModificacion(parametersExecute));
	            rendicion.setAlerta(getAlerta(rendicion.getId()));

	            listaRendiciones.add(rendicion);
	        }
	    }
	}

	private int parseId(String str) {
	    String idString = str.substring(0, 16).replaceFirst("^0*", "");
	    return Integer.parseInt(idString);
	}

	private String parseCodMotivo(String str) {
	    return str.substring(16, 20);
	}

	private String parseMotivo(String str) {
	    return str.substring(20, 66);
	}

	private String parseCostosDestino(String str) {
	    return str.substring(66, 70);
	}

	private String parseDescripcion(String str) {
	    return str.substring(70, 190).trim();
	}

	private String parseEstado(String str) {
	    return str.substring(190, 195);
	}

	private String parseFechaDesde(String str) {
	    return str.substring(195, 205);
	}

	private String parseFechaHasta(String str) {
	    return str.substring(205, 215);
	}

	private String parseImporte(String str) {
	    return str.substring(215, 232).replaceFirst("^0*", "");
	}

	private String parseIdu(String str) {
	    if (str.length() > 232) {
	        return str.substring(232, 242).trim();
	    } else {
	        return "";
	    }
	}

	private String parseAdea(String str) {
	    if (str.length() > 242) {
	        return str.substring(248, 259).trim();
	    } else {
	        return "";
	    }
	}

	private String parseDescripcionEstado(Map<String, Object> parametersExecute) {
	    return ((String) parametersExecute.getOrDefault(DES_EST_REND, "")).trim();
	}

	private String parseUsuarioAprobador(Map<String, Object> parametersExecute) {
	    return ((String) parametersExecute.getOrDefault("nomUsrAprob", "")).trim();
	}

	private String parseCodUsuarioAprobador(Map<String, Object> parametersExecute) {
	    return ((String) parametersExecute.getOrDefault("codUsrAprob", "")).trim();
	}

	private String parseMotivoRechazo(Map<String, Object> parametersExecute) {
	    return ((String) parametersExecute.getOrDefault("desc_rechazo", "")).trim();
	}

	private String parseFechaUltimaModificacion(Map<String, Object> parametersExecute) {
	    return (String) parametersExecute.getOrDefault(FEC_ULT_MOD, "");
	}

	private String getAlerta(int id) {
	    if (id == 1287 || id == 1249) {
	        return "1";
	    } else if (id == 1282) {
	        return "2";
	    } else {
	        return "0";
	    }
	}


	@Override
	public List getDataReturnList() {
		return listaRendiciones;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
//		parametersExecute.put(DES_EST_REND, "");
//		parametersExecute.put("nomUsrAprob", "");
//		parametersExecute.put("codUsrAprob", "");
//		parametersExecute.put("desc_rechazo", "");
//		parametersExecute.put(FEC_ULT_MOD, "01/02/2018");
//		parametersExecute.put(AVISO, "SE ACTUALIZO LA POLITICA DE GASTOS - NUEVOS TOPES VIGENTES");
//	
//		List<String> retList = new ArrayList<String>();
//		List<String> list = new ArrayList<String>();
//		
//		list.add("00000000000011080205COMPRA DE BIENES DE USO                           a                                                                                                                       PSUP 2017-11-262018-11-26          2005,00A000174494");
//		list.add("00000000000011070204USO DE VEHICULOS DE GESTION COMERCIAL             a                                                                                                                       OBSER2018-11-262018-11-26           633,00");
//		list.add("00000000000011060203COMITE Y REUNIONES DE TRABAJO                     a                                                                                                                       PENDI2018-11-262018-11-26           516,00");
//		list.add("00000000000011050202VIAJES DE GESTION LOCAL                           a                                                                                                                       PENDI2018-11-262018-11-26             0,00");
//		list.add("00000000000011040723PAGO DE MATERIAS                                  x                                                                                                                       PFIRM2018-11-262018-11-26            11,00");
//		list.add("00000000000011030717ENFERMEDAD                                        a                                                                                                                       PENDI2018-11-262018-11-26           100,00");
//		list.add("00000000000011010201VIAJE AL EXTERIOR                                 x                                                                                                                       PENDI2017-01-012017-04-01             0,00");
//		list.add("00000000000011000200REPRESENTACION AACC - COMIDAS                     x                                                                                                                       ESCAN2018-11-082018-11-19             0,00");
//		list.add("00000000000010990200REPRESENTACION AACC - COMIDAS                     a                                                                                                                       RECHA2018-11-012018-11-02             0,00");
//		list.add("00000000000010984102AYUDAS GRACIABLES - DTO MEDICO                    PRUEBA DE CARGA INICIAL DE AYUDAS GRACIABLES                                                                            PENDI2018-10-022018-10-02             0,00");
//		list.add("00000000000010970201VIAJE AL EXTERIOR                                 d                                                                                                                       ESCAN2018-09-032018-10-01             1,00A000174469");
//		list.add("00000000000010960200REPRESENTACION AACC - COMIDAS                     as                                                                                                                      PENDI2018-09-032018-09-03             0,00");
//		list.add("00000000000010950725DESARROLLO Y SELECCION INTERNA                    sdsd                                                                                                                    PENDI2018-09-032018-09-04             2,00");
//		list.add("00000000000010940200REPRESENTACION AACC - COMIDAS                     Test2                                                                                                                   ESCAN2018-04-022018-06-14         45634,00A000174457");
//		
//		if (!parametersExecute.get("idRendicion").equals("")) {
//			for (String row : list) {
//				if (Integer.parseInt(row.substring(0, 16)) == Integer.parseInt((String) parametersExecute.get("idRendicion"))) {
//					parametersExecute.put(DES_EST_REND, row.substring(190, 195));
//					retList.add(row);
//					break;
//				}
//			}
//		} else {
//			retList = list;}
//		
//		parametersExecute.put("lista", retList);
	}
}