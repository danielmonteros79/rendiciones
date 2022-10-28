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

import com.sa.entities.OSCAR;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.services.Transaction;

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
		boolean tieneCodMotivo = !(parametersExecute.get("cod_motivo") == null || ((String)parametersExecute.get("cod_motivo")).trim().equals(""));
		
		if (!tieneCodMotivo) {
			for (Object object : (List) parametersExecute.get("lista")) {
				String str = (String) ((BasicDynaBean) object).get("lista");
				int i = 0;
				ParametroMotivo motivo = new ParametroMotivo();
				
				motivo.setCodigo(str.substring(i, i += 4));
				motivo.setDescripcion(str.substring(i, i += 50));
				motivo.setIdGlg(str.substring(i, i += 2));
				motivo.setIdCentroCostos(str.substring(i, i += 4));
				motivo.setCodSup(str.substring(i, i += 5));
				motivo.setCodFirma(str.substring(i, i += 5));
				motivo.setCodAprobacionGlg(str.substring(i, i += 5));
				motivo.setEstado(str.substring(i, i += 1));
				
				this.parametroMotivos.add(motivo);
			}
		} else {
			ParametroMotivo motivo = new ParametroMotivo();
			motivo.setCodigo((String) parametersExecute.get("cod_motivo"));
			motivo.setEstado((String) parametersExecute.get("estado"));
			motivo.setDescripcion((String) parametersExecute.get("desc_motivo"));
			motivo.setIdGlg((String) parametersExecute.get("idglg"));
			motivo.setCodAprobacionGlg((String) parametersExecute.get("aprglg"));
			motivo.setIdCentroCostos((String) parametersExecute.get("ccosto"));
			motivo.setMaInclExcl((String) parametersExecute.get("inclexc"));
			motivo.setCodSup((String) parametersExecute.get("codsup"));
			motivo.setCodFirma((String) parametersExecute.get("firma"));
			motivo.setMeAviso((String) parametersExecute.get("maviso"));
			motivo.setOscar(new OSCAR((String) parametersExecute.get("oscar")));
			motivo.setIdNivCarga((String) parametersExecute.get("ncarga"));
			motivo.setIdNivAutoriz((String) parametersExecute.get("nautor"));
			motivo.setTxAviso((String) parametersExecute.get("txaviso"));
			motivo.setIdOperEspe((String) parametersExecute.get("oespe"));
			String dinterv = (String) parametersExecute.get("dinterv");
			motivo.setMeDiasInterv("000000000".equals(dinterv) ? "" : dinterv);
			
			String fechaDesde = (String) parametersExecute.get("fdesde");
			if (!"".equals(fechaDesde))
				motivo.setFechaDesde(sdfYMD.parse(fechaDesde));
			
			String fechaHasta = (String) parametersExecute.get("fhasta");
			if (!"".equals(fechaHasta))
				motivo.setFechaHasta(sdfYMD.parse(fechaHasta));
			
			List<String> centrosCosto = new ArrayList<String>();
			String vcccost = (String) parametersExecute.get("vcccost");
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
}