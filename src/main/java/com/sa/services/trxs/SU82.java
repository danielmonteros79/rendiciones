package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.OSCAR;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU82 extends Transaction {
	private static final Log log = LogFactory.getLog(SU82.class);
	private List<ParametroMotivo> parametroMotivos = new ArrayList<ParametroMotivo>();
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	public SU82() {
		this.PARAMETER_TRX = "SUM_CONS_PARAMS_MOTIVOS";
		this.CURRENT_TRX = "SU82";
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
		boolean tieneCodMotivo = !(parametersExecute.get("cod_motivo") == null || ((String)parametersExecute.get("cod_motivo")).trim().equals(""));
		
		if (!tieneCodMotivo) {
			for (Object obj : (List) parametersExecute.get("lista")) {
				String str = getStrLista(obj);
				int i = 0;
				ParametroMotivo motivo = new ParametroMotivo();
				//if(!str.substring(str.length()-1).equalsIgnoreCase("S")){
				motivo.setCodigo(str.substring(i, i += 4));
				motivo.setDescripcion(str.substring(i, i += 50));
				motivo.setIdGlg(str.substring(i, i += 2));
				motivo.setIdCentroCostos(str.substring(i, i += 4));
				motivo.setCodSup(str.substring(i, i += 5));
				motivo.setCodFirma(str.substring(i, i += 5));
				motivo.setCodAprobacionGlg(str.substring(i, i += 5));
				motivo.setEstado(str.substring(i, i += 1));
				motivo.setLastElement(str.substring(str.length()-1));
//				}
//				else{
//					motivo.setCodigo("");
//					motivo.setDescripcion("");
//					motivo.setIdGlg("");
//					motivo.setIdCentroCostos("");
//					motivo.setCodSup("");
//					motivo.setCodFirma("");
//					motivo.setCodAprobacionGlg("");
//					motivo.setEstado("");
//					motivo.setLastElement(str.substring(str.length()-1));
//				}
				this.parametroMotivos.add(motivo);
				
			}
		} else {
			ParametroMotivo motivo = new ParametroMotivo();
			motivo.setCodigo((String) parametersExecute.get("cod_motivo"));
			motivo.setEstado((String) parametersExecute.get("estado"));
			motivo.setDescripcion((String) parametersExecute.get("desc_motivo"));
			motivo.setIdGlg((String) parametersExecute.get("idglg"));
			motivo.setCodAprobacionGlg((String) parametersExecute.get("controlglg"));
			motivo.setIdCentroCostos((String) parametersExecute.get("centro_costo"));
			motivo.setMaInclExcl((String) parametersExecute.get("incluir_costos"));
			motivo.setCodSup((String) parametersExecute.get("superior"));
			motivo.setCodFirma((String) parametersExecute.get("firma"));
			motivo.setMeAviso((String) parametersExecute.get("monto_aviso"));
			motivo.setOscar(new OSCAR((String) parametersExecute.get("regla_centros")));
			motivo.setIdNivCarga((String) parametersExecute.get("nivel_carga"));
			motivo.setIdNivAutoriz((String) parametersExecute.get("nivel_autorizar"));
			motivo.setTxAviso((String) parametersExecute.get("aviso_motivo"));
			motivo.setIdOperEspe((String) parametersExecute.get("codigo_operacion"));
			String dinterv = (String) parametersExecute.get("dias_intervalo");
			motivo.setMeDiasInterv("000000000".equals(dinterv) ? "" : dinterv);
			
			String fechaDesde = (String) parametersExecute.get("fechaDesde");
			if (!"".equals(fechaDesde))
				motivo.setFechaDesde(sdfYMD.parse(fechaDesde));
			
			String fechaHasta = (String) parametersExecute.get("fechaHasta");
			if (!"".equals(fechaHasta))
				motivo.setFechaHasta(sdfYMD.parse(fechaHasta));
			
			List<String> centrosCosto = new ArrayList<String>();
			String vcccost = (String) parametersExecute.get("vector_centro_costos");
			if (vcccost != null) {
				int index = 0;
				while (index < vcccost.length()) {
					centrosCosto.add(vcccost.substring(index, Math.min(index + 4, vcccost.length())));
				    index += 4;
				}
			}
			motivo.setCentrosCosto(centrosCosto);

			this.parametroMotivos.add(motivo);
		}
	}

	@Override
	public List getDataReturnList() {
		return parametroMotivos;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
	}
}