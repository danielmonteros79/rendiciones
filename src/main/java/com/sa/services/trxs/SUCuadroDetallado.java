package com.sa.services.trxs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.CuadroDetallado;
import com.sa.entities.parametros.ParametroExceptuado;
import com.sa.services.Transaction;

public class SUCuadroDetallado extends Transaction {
	private static final Log log = LogFactory.getLog(SUCuadroDetallado.class);
	private List<ParametroExceptuado> parametroExceptuado = new ArrayList<ParametroExceptuado>();
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
	private String descripcion = null;
	public SUCuadroDetallado() {
		this.PARAMETER_TRX = "SUM_ABM_EXCEPCIONES";
		this.CURRENT_TRX = "SUCuadroDetallado";
	}

	


	
	public Object getDataReturn() {
		return this.descripcion;
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
//			execute(client, this.PARAMETER_TRX, parametersExecute);
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error("Error ejecutando executeTrx", e); // Se agrega logging para cobertura
			throw new TransactionException(e);
		}
		
	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) throws Exception {
		List <String> list = new ArrayList<String>();
		list.add("231 GASTOS DE REPRESENTACION NI IDEA QUE PONER ACA  APROBADO  USUARIO 1 15000");
		list.add("432 VIAJE AL EXTERIOR        NI IDEA QUE PONER ACA  RECHAZADO USUARIO 2 10000");
		list.add("564 HORARIO EXTENDIDO        NI IDEA QUE PONER ACA  OBSERVADO USUARIO 3 17000");
		list.add("873 TRASLADOS DIA DE PARO    NI IDEA QUE PONER ACA SUSPENDIDO USUARIO 1 20000");
		if("CONS".equals(parametersExecute.get("opcion"))){
			for(String fila : list){
			try {
				CuadroDetallado datos = new CuadroDetallado();
				String str = fila;
				datos.setId(Integer.parseInt(str.substring(0, 3)));
				datos.setMotivo(str.substring(4, 28));
				datos.setDescripcion(str.substring(29, 50));
				datos.setEstado(str.substring(50, 61));
				datos.setProxUsuario(str.substring(61, 71));
				datos.setFechaUltModif(new Date());
				datos.setImporte(str.substring(72, 77));
				
				dataReturnList.add(datos);
			} catch (Exception e) {
				log.error("", e);
			}
			}
			
//		    CuadroDetallado datos = new CuadroDetallado();	
//		    datos.setEstado("NO INGRESADO");
//		    datos.setImporte("99999");
//		    datos.setMotivo("$99999");
//		    		    
//		    dataReturnList.add(datos);

		}

	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		// TODO Auto-generated method stub
		
	}
}