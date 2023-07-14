package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;

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
		if (parametersExecute.get("lista") != null) {
			for (Object obj : (List) parametersExecute.get("lista")) {
				String str = getStrLista(obj);
				Rendicion rendicion = new Rendicion();
				
				if (parametersExecute.get(AVISO) != null && !((String) parametersExecute.get(AVISO)).trim().equals(""))
					rendicion.setAviso((String) parametersExecute.get(AVISO));
				else
					rendicion.setAviso("");

				SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd");
				rendicion.setId(Integer.parseInt(str.substring(0, 16).replaceFirst("^0*", "")));
				rendicion.setCodMotivo(str.substring(16, 20));
				rendicion.setMotivo(str.substring(20, 66));
				rendicion.setCostosDestino(str.substring(66, 70));
				rendicion.setDescripcion(str.substring(70, 190).trim());
				rendicion.setEstado(str.substring(190, 195));
				rendicion.setFechaDesde(toDate.parse(str.substring(195, 205)));
				rendicion.setFechaHasta(toDate.parse(str.substring(205, 215)));
				rendicion.setImporte(str.substring(215, 232).replaceFirst("^0*", ""));
				
				int codThuban = str.length();
				if (codThuban > 232) {
					rendicion.setIdu(str.substring(232, 242).trim());
					if (codThuban > 242)
						rendicion.setAdea(str.substring(248, 259).trim());
					else
						rendicion.setAdea("");
				} else {
					rendicion.setIdu("");
					rendicion.setAdea("");
				}
				rendicion.setDescripcionEstado(((String) parametersExecute.get(DES_EST_REND)).trim());
				rendicion.setUsuarioAprobador(((String) parametersExecute.get("nomUsrAprob")).trim());
				rendicion.setCodUsuarioAprobador(((String) parametersExecute.get("codUsrAprob")).trim());
				rendicion.setMotivoRechazo(((String) parametersExecute.get("desc_rechazo")).trim());
				
				if (parametersExecute.get(FEC_ULT_MOD) != null) {
					rendicion.setFechaUltimaModificacion(((String) parametersExecute.get(FEC_ULT_MOD)).trim());
				}
				
				
//				String random = String.valueOf(Math.round(Math.random()* (2 - 1) + 1));
//				rendicion.setAlerta(random);
				
				switch(rendicion.getId()){
				case 1287:
					rendicion.setAlerta("1");
					break;
				case 1249:
					rendicion.setAlerta("1");
					break;
				case 1282:
					rendicion.setAlerta("2");
					break;
				default:
					rendicion.setAlerta("0");
				}
				
				//rendicion.setAlerta("1");
				
				listaRendiciones.add(rendicion);
			}
		}
	}

	@Override
	public List getDataReturnList() {
		return listaRendiciones;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		parametersExecute.put(DES_EST_REND, "");
		parametersExecute.put("nomUsrAprob", "");
		parametersExecute.put("codUsrAprob", "");
		parametersExecute.put("desc_rechazo", "");
		parametersExecute.put(FEC_ULT_MOD, "01/02/2018");
		parametersExecute.put(AVISO, "SE ACTUALIZO LA POLITICA DE GASTOS - NUEVOS TOPES VIGENTES");
	
		List<String> retList = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		
		list.add("00000000000011080205COMPRA DE BIENES DE USO                           a                                                                                                                       PSUP 2017-11-262018-11-26          2005,00A000174494");
		list.add("00000000000011070204USO DE VEHICULOS DE GESTION COMERCIAL             a                                                                                                                       OBSER2018-11-262018-11-26           633,00");
		list.add("00000000000011060203COMITE Y REUNIONES DE TRABAJO                     a                                                                                                                       PENDI2018-11-262018-11-26           516,00");
		list.add("00000000000011050202VIAJES DE GESTION LOCAL                           a                                                                                                                       PENDI2018-11-262018-11-26             0,00");
		list.add("00000000000011040723PAGO DE MATERIAS                                  x                                                                                                                       PFIRM2018-11-262018-11-26            11,00");
		list.add("00000000000011030717ENFERMEDAD                                        a                                                                                                                       PENDI2018-11-262018-11-26           100,00");
		list.add("00000000000011010201VIAJE AL EXTERIOR                                 x                                                                                                                       PENDI2017-01-012017-04-01             0,00");
		list.add("00000000000011000200REPRESENTACION AACC - COMIDAS                     x                                                                                                                       ESCAN2018-11-082018-11-19             0,00");
		list.add("00000000000010990200REPRESENTACION AACC - COMIDAS                     a                                                                                                                       RECHA2018-11-012018-11-02             0,00");
		list.add("00000000000010984102AYUDAS GRACIABLES - DTO MEDICO                    PRUEBA DE CARGA INICIAL DE AYUDAS GRACIABLES                                                                            PENDI2018-10-022018-10-02             0,00");
		list.add("00000000000010970201VIAJE AL EXTERIOR                                 d                                                                                                                       ESCAN2018-09-032018-10-01             1,00A000174469");
		list.add("00000000000010960200REPRESENTACION AACC - COMIDAS                     as                                                                                                                      PENDI2018-09-032018-09-03             0,00");
		list.add("00000000000010950725DESARROLLO Y SELECCION INTERNA                    sdsd                                                                                                                    PENDI2018-09-032018-09-04             2,00");
		list.add("00000000000010940200REPRESENTACION AACC - COMIDAS                     Test2                                                                                                                   ESCAN2018-04-022018-06-14         45634,00A000174457");
		
		if (!parametersExecute.get("idRendicion").equals("")) {
			for (String row : list) {
				if (Integer.parseInt(row.substring(0, 16)) == Integer.parseInt((String) parametersExecute.get("idRendicion"))) {
					parametersExecute.put(DES_EST_REND, row.substring(190, 195));
					retList.add(row);
					break;
				}
			}
		} else {
			retList = list;}
		
		parametersExecute.put("lista", retList);
	}
}