package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU80 extends Transaction {
	private static final Log log = LogFactory.getLog(SU80.class);
	private List<ParametriaUsuarioDelegado> parametroDelegaciones = new ArrayList<ParametriaUsuarioDelegado>();
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	public SU80() {
		this.PARAMETER_TRX = "SUM_CONS_PARAMS_DELEGACIONES";
		this.CURRENT_TRX = "SU80";
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
		int index = 0;

		if (parametersExecute.get("lista") != null) {
			for (Object obj : (List) parametersExecute.get("lista")) {
				index++;
				String str = getStrLista(obj);

				ParametriaUsuarioDelegado delegacion = new ParametriaUsuarioDelegado();
				int i = 0;
				delegacion.setId(index);
				delegacion.setDelegadoNombre(str.substring(i, i += 75).trim());
				delegacion.setDelegadoCentroCostos(str.substring(i, i += 4).trim());
				delegacion.setDelegadoSector(str.substring(i, i += 4).trim());
				delegacion.setDelegadoInforme(str.substring(i, i += 1).trim());
				delegacion.setDelegadoAccion(str.substring(i, i += 1).trim());
				delegacion.setDelegadoEstado(str.substring(i, i += 1).trim());
				delegacion.setFeDesde(sdfYMD.parse(str.substring(i, i += 10).trim()));
				delegacion.setFeHasta(sdfYMD.parse(str.substring(i, i += 10).trim()));
				delegacion.setDelegadoUser(str.substring(i, i += 8).trim());
				delegacion.setUsuarioAlta(str.substring(i, i += 8).trim());
				delegacion.setFechaAlta(str.substring(i, i += 26).trim());

				this.parametroDelegaciones.add(delegacion);
			}
		}
	}

	@Override
	public List getDataReturnList() {
		return parametroDelegaciones;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		List<String> retList = new ArrayList<String>();
		
		if (parametersExecute.get("cod_user").equals("A103555"))
			retList.add("RED DEVIL, ANALIA LAURA                                                    00998568SIA2018-12-262019-04-23A126661 A103555 2018-12-26-15.10.28.785850");
		
		parametersExecute.put("lista", retList);
	}
}