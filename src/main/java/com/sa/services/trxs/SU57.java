package com.sa.services.trxs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.ComboGenerico;
import com.sa.entities.DatosPantallaDinamica;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SU57 extends Transaction {
	private static final Log log = LogFactory.getLog(SU57.class);
	
	public SU57() {
		this.PARAMETER_TRX = "SUM_CONS_DET_DINAMIC";
		this.CURRENT_TRX = "SU57";
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
		List list = (List) parametersExecute.get("lista");
		for (Object obj : list) {
			String str = getStrLista(obj, "lista_campo1");
			DatosPantallaDinamica datoPantalla = new DatosPantallaDinamica();
			
			datoPantalla.setTipoCampo(str.substring(0, 15).trim());
			datoPantalla.setMostrar(str.substring(15,16));
			datoPantalla.setCampoObligatorio(str.substring(16, 17));
			
			// VERIFICA SI ES UN COMBO. LOS COMBOS SE DEFINEN CON COD.
			if (datoPantalla.getTipoCampo().contains("COD")) {
				try {
					datoPantalla.setTituloCampo(str.substring(17, 67).trim());
				} catch (Exception e) {
					datoPantalla.setTituloCampo(str.substring(17).trim());
				}
				
				int cantOpciones = (int) Math.ceil((str.length() - 67) / 60.0);
				for (int i = 0; i < cantOpciones; i++) {
					int indexOpcion = 72 + 60 * i;
					ComboGenerico cmb = new ComboGenerico();
					cmb.setId(StringUtils.leftPad(str.substring(indexOpcion, indexOpcion + 5).trim(), 5, "0"));
					
					try {
						cmb.setDescripcion(cmb.getId() + " - " + str.substring(indexOpcion + 5, indexOpcion + 55).trim());
					} catch (Exception e) {
						cmb.setDescripcion(cmb.getId() + " - " + str.substring(indexOpcion + 5).trim());
					}
					
					datoPantalla.addOpcionCombo(cmb);
				}
			} else {
				datoPantalla.setTituloCampo(str.substring(17, str.length()).trim());
			}
			
			dataReturnList.add(datoPantalla);
		}

	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		List<String> retList = new ArrayList<String>();
		
	//	retList.add("COD1           S CARGO                                             002020001 CEO                                               002020002 DIRECTOR                                          002020003 GERENTE                                           002020004 OTROS");
		
		retList.add("TXT1           SSAPELLIDO/NOMBRE");
		retList.add("NUM1           SSDNI");
		retList.add("FEC1           SSFEC.NACIM.");
		retList.add("TXT2           SSINSTITUCION");
	//	retList.add("TXT250         S TXT250");
		
		parametersExecute.put("lista", retList);
	}
}
