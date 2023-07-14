package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.ComboComprobante;
import com.sa.entities.ComboGasto;
import com.sa.entities.ComboMoneda;
import com.sa.entities.ComboMotivo;
import com.sa.entities.ComboOpcion2;
import com.sa.services.Transaction;
import com.sa.util.ParamsConstants;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU51 extends Transaction {
	private static final Log log = LogFactory.getLog(SU51.class);
	public List<ComboOpcion2> listaOpcion2 = new ArrayList<>();
	public List<ComboMotivo> listaMotivo = new ArrayList<>();
	public List<ComboMoneda> listaMoneda = new ArrayList<>();
	public List<ComboComprobante> listaComprobante = new ArrayList<>();
	public List<ComboGasto> listaTipoGasto = new ArrayList<>();
	
	private static final String OPCION = "opcion";
	private static final String COD_MOTIVO = "cod_motivo";

	public SU51() {
		this.PARAMETER_TRX = "SUM_CONS_PARAMETROS";
		this.CURRENT_TRX = "SU51";
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
	protected void mapData(Map<String, Object> parametersExecute) {
		List lista = (List) parametersExecute.get("lista");

		Integer opcion = Integer.valueOf((String) parametersExecute.get(OPCION));
		switch (opcion) {
		case 1:
			break;
		case 2:
			for (Object obj : lista) {
				String str = getStrLista(obj);
				ComboOpcion2 comboOpcion2 = new ComboOpcion2();
				comboOpcion2.setId(str.substring(10, 14));
				comboOpcion2.setDescripcion(str.substring(14, str.length()));
				if (str.substring(10, 14).trim().equals("ARS") || str.substring(10, 14).trim().equals("EUR")
						|| str.substring(10, 14).trim().equals("USD")) {
					comboOpcion2.setDescripcion(str.substring(10, 14));
				}
				listaOpcion2.add(comboOpcion2);
			}
			this.dataReturnList = listaOpcion2;
			break;
		case 3:
			for (Object obj : lista) {
				String str = getStrLista(obj);
				ComboGasto comboTipoGasto = new ComboGasto();
				if (str.length() <= 54) {
					comboTipoGasto.setId(str.substring(0, str.length()));
					comboTipoGasto.setDescripcion(str.substring(4, str.length()));
				} else {
					if (str.substring(54, 59).equalsIgnoreCase("") || str.substring(54, 59).equalsIgnoreCase("00000")) {
						comboTipoGasto.setId(str.substring(0, str.length()) + "N");

					} else {
						comboTipoGasto.setId(str.substring(0, str.length()) + "S");
					}
					comboTipoGasto.setDescripcion(str.substring(4, 54));
					comboTipoGasto.setDetalle(str.substring(54, 59));
					comboTipoGasto.setDescOblig("S");
				}
				listaTipoGasto.add(comboTipoGasto);
			}
			this.dataReturnList = listaTipoGasto;

			break;
		case 4:
		case 8:			
		case 9:
			if (lista != null) {
				for (Object obj : lista) {
					String str = getStrLista(obj);
					ComboMotivo comboMotivo = new ComboMotivo();
					comboMotivo.setId(str.substring(0, 4));
					comboMotivo.setDescripcion(comboMotivo.getId() + "-" + (str.substring(4, 54)));
					comboMotivo.setCostosDestino(str.substring(54, str.length()));
					listaMotivo.add(comboMotivo);
				}
			}
			this.dataReturnList = listaMotivo;

			break;
		case 5:
		case 6:
		case 7:
			for (Object obj : lista) {
				String str = getStrLista(obj);
				ComboMotivo comboMotivo = new ComboMotivo();
				comboMotivo.setId(str.substring(0, 4));
				comboMotivo.setDescripcion(comboMotivo.getId() + "-" + (str.substring(4, str.length())));
				listaMotivo.add(comboMotivo);
			}
			this.dataReturnList = listaMotivo;
			
			break;
		}
	}
	
	protected void hardcodear(Map<String, Object> parametersExecute) {
		List<String> retList = new ArrayList<String>();
		
		if (parametersExecute.get(OPCION).equals("2") && 
			parametersExecute.get("claves_cons").equals(ParamsConstants.MONEDA_TABLA + ParamsConstants.MONEDA_SUBTABLA + ParamsConstants.MONEDA_CODIGO)) {
			retList.add("0000200004ARS PESOS ARGENTINOS");
			retList.add("0000200004USD DOLARES");
			retList.add("0000200004EUR EUROS");
		} else if (parametersExecute.get(OPCION).equals("2") && 
				parametersExecute.get("claves_cons").equals(ParamsConstants.COMPROBANTE_TABLA + ParamsConstants.COMPROBANTE_SUBTABLA + ParamsConstants.COMPROBANTE_CODIGO)) {
			retList.add("00002000060001FACTURA");
			retList.add("00002000060002MAIL");
			retList.add("00002000060003SIN COMPROBANTE");
			retList.add("00002000060004TICKET");
			retList.add("00002000060006FACTURA OBLIGATORIA");
		} else if (parametersExecute.get(OPCION).equals("3")) {
			if (parametersExecute.get(COD_MOTIVO).equals("0202")) {
				retList.add("0200ALMUERZOS                                         00202N");
				retList.add("0201CENAS                                             00202N");
			} else if (parametersExecute.get(COD_MOTIVO).equals("0203")) {
				retList.add("0214COMIDAS                                           00204N");
				retList.add("0215ALQUILER SALON                                    00204N");
				retList.add("0216ALQUILER EQUIPOS                                  00204N");
				retList.add("0217SERVICIO DE CATERING                              00204N");
				retList.add("0218VARIOS                                            00204N");
			} else if (parametersExecute.get(COD_MOTIVO).equals("0204")) {
				retList.add("0219COMBUSTIBLE                                       00205N");
				retList.add("0220PEAJES                                            00000N");
				retList.add("0221ESTACIONAMIENTO                                   00000N");
				retList.add("0222LAVADO                                            00204N");
				retList.add("0223SERVICE CONCESIONARIO                             00000N");
				retList.add("0224VARIOS                                            00206N");
				retList.add("45345345345                                           00001N");
			} else if (parametersExecute.get(COD_MOTIVO).equals("0205")) {
				retList.add("0225BIEN DE USO                                       00207N");}
		} else if (parametersExecute.get(OPCION).equals("4")) {
			retList.add("0200GASTOS DE REPRESENTACION                          0000");
			retList.add("0201VIAJE AL EXTERIOR                                 0000");
			retList.add("0202VIAJES DE GESTION LOCAL                           0000");
			retList.add("0203COMITE Y REUNIONES DE TRABAJO                     0000");
			retList.add("0204USO DE VEHICULOS DE GESTION COMERCIAL             0000");
			retList.add("0205COMPRA DE BIENES DE USO                           0000");
			retList.add("0206COMPRA DE UTILES DE OFICINA                       0000");
			retList.add("0207VIAJES GRUPO BBVA                                 0000");
			retList.add("0208PUBLICACIONES/SUSCRIPCIONES                       0000");
			retList.add("0707MOVILIDAD CON CAMBIO DE RADICAION                 0000");
			retList.add("0711HORARIO EXTENDIDO                                 0000");
			retList.add("0712TRASLADOS DIA DE PARO                             0000");
			retList.add("0715GASTOS NAVIDAD                                    0000");
			retList.add("0716EMERGENCIAS Y CATASTROFES                         0000");
			retList.add("0717ENFERMEDAD                                        0000");
			retList.add("0718ENFERMEDAD ATENCION CLIENTE                       0000");
			retList.add("0719REPRESENTACION AREAS GLOBALES                     9999");
			retList.add("0720prueba nivel ingreso                              0000");
			retList.add("0721prueba 2 nivel de ingreso                         0000");
			retList.add("0723PAGO DE MATERIAS                                  0000");
			retList.add("0725DESARROLLO Y SELECCION INTERNA                    0000");
			retList.add("0799prueba                                            0000");
			retList.add("4102AYUDAS GRACIABLES - DTO MEDICO                    1812");
			retList.add("6903FORMACION CAPACITACION INTERNA                    1830");
			retList.add("6904FORMACION CAPACITACION EXTERNA                    1830");
			retList.add("6905FORMACION VIAJES AL INTERIOR                      9999");
			retList.add("6906FORMACION VIAJES AL EXTERIOR                      1830");
		} else if (parametersExecute.get(OPCION).equals("5")) {
			retList.add("MOT1MOTIVO RECH1");
			retList.add("MOT2MOTIVO RECH2");
			retList.add("MOT3MOTIVO RECH3");
			retList.add("MOT4MOTIVO RECH4");
		} else if (parametersExecute.get(OPCION).equals("6")) {
			retList.add("MOP0motivo de prueba");
			retList.add("MOP1mot prue 1");
			retList.add("MOP2prueba de suspension");
			retList.add("MOP3mot susp");
			retList.add("MOP4mot susp");
			retList.add("MOP5motivo de prueba susp 5");
			retList.add("MOP6motivo de prueba susp 6");
			retList.add("MOP7motivo de prueba susp 7");
			retList.add("MOP8motivo de prueba susp 8");
			retList.add("MOP9mot prue 9");
			retList.add("MOPAprueba de suspension");
			retList.add("MOPBprueba de suspension");
			retList.add("MOPCmotivo de prueba susp 12");
		} else if (parametersExecute.get(OPCION).equals("7")) {
			retList.add("MOP1prueba motivo observacion 1");
			retList.add("MOP2prueba motivo observacion 2");
			retList.add("MOP3prueba motivo observacion 3");
			retList.add("MOP4prueba motivo observacion 4");
			retList.add("MOP5prueba motivo observacion 5");
		} else if (parametersExecute.get(OPCION).equals("8") || parametersExecute.get(OPCION).equals("9")) {
			retList.add("0123123                                               0123");
			retList.add("0200REPRESENTACION AACC - COMIDAS                     0000");
			retList.add("0201VIAJE AL EXTERIOR                                 0000");
			retList.add("0202VIAJES DE GESTION LOCAL                           0000");
			retList.add("0203COMITE Y REUNIONES DE TRABAJO                     0000");
			retList.add("0204USO DE VEHICULOS DE GESTION COMERCIAL             0000");
			retList.add("0205COMPRA DE BIENES DE USO                           0000");
			retList.add("0206COMPRA DE UTILES DE OFICINA                       0000");
			retList.add("0207VIAJES GRUPO BBVA                                 0000");
			retList.add("0208PUBLICACIONES/SUSCRIPCIONES                       0000");
			retList.add("0213prueba de motivos                                 1234");
			retList.add("0500GASTOS DE SW - LOCAL                              0000");
			retList.add("0666motivo 666                                        1234");
			retList.add("0700BENEFICIO EQUIPO DIRECTIVO                        0000");
			retList.add("0701BENEFICIO DIRECTOR ADJUNTO                        0000");
			retList.add("0702BENEFICIO EQUIPO DIRECTIVO                        0111");
			retList.add("0703SERVICIOS AL PERSONAL                             0000");
			retList.add("0704BENEFICIOS GERENTES                               0000");
			retList.add("0705BENEFICIO ALMUERZO                                0000");
			retList.add("0706ALMUERZO OPERADORES                               0000");
			retList.add("0707MOVILIDAD CON CAMBIO DE RADICAION                 0000");
			retList.add("0708MOV CAMBIO DE RADIC-EXCEP                         0000");
			retList.add("0709VIAJE ANUAL EXPATRIADOS                           0000");
			retList.add("0710TRASLADOS POR ROTACIONES O SUPLENCIAS             0000");
			retList.add("0711HORARIO EXTENDIDO                                 0000");
			retList.add("0712TRASLADOS DIA DE PARO                             0000");
			retList.add("0713REFRIGERIO (SUCURSALES Y FFVV)                    0000");
			retList.add("0714INCENTIVACION COMERCIAL                           0000");
			retList.add("0715GASTOS NAVIDAD                                    0000");
			retList.add("0716EMERGENCIAS Y CATASTROFES                         0000");
			retList.add("0717ENFERMEDAD                                        0000");
			retList.add("0718ENFERMEDAD ATENCION CLIENTE                       0000");
			retList.add("0719REPRESENTACION AREAS GLOBALES                     9999");
			retList.add("0720prueba nivel ingreso                              0000");
			retList.add("0721prueba 2 nivel de ingreso                         0000");
			retList.add("0722VGC - VIAJE AL EXTERIOR                           0000");
			retList.add("0723PAGO DE MATERIAS                                  0000");
			retList.add("0725DESARROLLO Y SELECCION INTERNA                    0000");
			retList.add("0799prueba                                            0000");
			retList.add("4102AYUDAS GRACIABLES - DTO MEDICO                    1812");
			retList.add("6900SELECCION DE PERSONAL                             0000");
			retList.add("6901COMUNICACIONES INTERNAS                           0000");
			retList.add("6902FORMACION IDIOMA DIRECC                           1830");
			retList.add("6903FORMACION CAPACITACION INTERNA                    1830");
			retList.add("6904FORMACION CAPACITACION EXTERNA                    1830");
			retList.add("6905FORMACION VIAJES AL INTERIOR                      9999");
			retList.add("6906FORMACION VIAJES AL EXTERIOR                      1830");
			retList.add("9888asd                                               0123");
			retList.add("9999asd                                               1234");
		} 
		
		parametersExecute.put("lista", retList);
	}
}