package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.parametros.ParametroExceptuado;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

public class SU86 extends Transaction {
	private static final Log log = LogFactory.getLog(SU86.class);
	private List<ParametroExceptuado> parametroExceptuado = new ArrayList<ParametroExceptuado>();
	private List<String> validacionesPorExcepcion = new ArrayList<String>();
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	public SU86() {
		this.PARAMETER_TRX = "SUM_CONS_PARAMS_EXCEPCIONES";
		this.CURRENT_TRX = "SU86";
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

//	@Override
//	protected void mapData(Map<String, Object> parametersExecute) throws Exception {
//		if (parametersExecute.get("opcion").equals("CONS")) {
//			for (Object obj : (List) parametersExecute.get("lista")) {
//				String str = getStrLista(obj);
//				ParametroExceptuado exceptuado = new ParametroExceptuado();
//				int i = 0;
//				exceptuado.setMotivoUsuario(str.substring(i, i += 8));
//				exceptuado.setDescripcionNombre(str.substring(i, i += 75));
//				exceptuado.setDesde(sdfYMD.parse(str.substring(i, i += 10)));
//				exceptuado.setHasta(sdfYMD.parse(str.substring(i, i += 10)));
//				exceptuado.setEstado(str.substring(i, i += 1));
//				exceptuado.setTipo(str.substring(i += 8, i += 1));
//
//				this.parametroExceptuado.add(exceptuado);
//			}
//		} else {
//			ParametroExceptuado exceptuado = new ParametroExceptuado();
//			exceptuado.setMotivoUsuario((String) parametersExecute.get("cod_mot_usu"));
//			exceptuado.setDescripcionNombre((String) parametersExecute.get("des_mot_usu"));
//			exceptuado.setHasta(sdfYMD.parse((String) parametersExecute.get("fe_hasta")));
//			exceptuado.setDesde(sdfYMD.parse((String) parametersExecute.get("fe_desde")));
//			exceptuado.setEstado((String) parametersExecute.get("estado"));
//			exceptuado.setTipo((String) parametersExecute.get("ma_mot_usu"));
//			
//			this.parametroExceptuado.add(exceptuado);
//		}
//	}
	
	@Override
	protected void mapData(Map<String, Object> parametersExecute) throws Exception {
		if (parametersExecute.get("lista") != null){
			for (Object obj : (List) parametersExecute.get("lista")) {
				String str = getStrLista(obj);
				this.validacionesPorExcepcion.add(str);
			}
		} else {
			this.validacionesPorExcepcion.add("OK");
		}
	}

//	@Override
//	public List getDataReturnList() {
//		return parametroExceptuado;
//	}
	
	@Override
	public List getDataReturnList() {
		return validacionesPorExcepcion;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
	}
}