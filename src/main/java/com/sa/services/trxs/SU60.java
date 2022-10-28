package com.sa.services.trxs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.Rendicion;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;
import iaik.asn1.INTEGER;

public class SU60 extends Transaction {

	private static final Log log = LogFactory.getLog(SU60.class);

	private final String[] FIELDS_INPUT = new String[] {};
	public List<Rendicion> listaRendiciones = new ArrayList<Rendicion>();

	public SU60() {
		// TODO Auto-generated constructor stub
		this.PARAMETER_TRX = "SUM_GEN_SCAN";
		this.CURRENT_TRX = "SU60";
	}

	@Override
	public void executeTrx(IWebClient client, Map parametersExecute)
			throws TransactionException {
		// TODO Auto-generated method stub
//		parametersExecute = new HashMap();
		try {

			execute(client, this.PARAMETER_TRX, parametersExecute);

			// Mapear los datos
//			mapData(parametersExecute);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TransactionException(e);
		}

	}

	@Override
	public void executeTrx(IWebClient client, String... parameters)
			throws TransactionException {
		// TODO Auto-generated method stub
		try {

			execute(client, this.PARAMETER_TRX, this.mapInputParams(parameters));

			// Mapear los datos

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TransactionException(e);
		}
	}

	@Override
	protected Map mapInputParams(String... parameters) {
		// TODO Auto-generated method stub
		Map parametersExecute = new HashMap<Object, Object>();

		for (int i = 0; i < parameters.length; i++) {
			parametersExecute.put(FIELDS_INPUT[i], parameters[i]);
		}
		return parametersExecute;
	}

	@Override
	protected void mapData(Map parametersExecute) {
		log.info("Se mapean los datos y se los agrega a la lista Rendicion");

		List list = (List) parametersExecute.get("mensajesRespuesta");
		// BasicDynaBean bean = (BasicDynaBean)
		// parametersExecute.get("mensajesRespuesta");

		for (Object object : list) {
			// BasicDynaBean bean = (BasicDynaBean) object;
			String importe = (((String) object).substring(160, 177)
					.replaceFirst("^0*", ""));
			parametersExecute = new HashMap();
			Rendicion rendicion = new Rendicion();
			SimpleDateFormat toDate = new SimpleDateFormat("dd/MM/yyyy");
			rendicion.setId(Integer.parseInt(((String) object).substring(0, 16)
					.replaceFirst("^0*", "")));
			rendicion.setIdMotivo(Integer.parseInt(((String) object).substring(
					16, 20)));
			rendicion.setCodMotivo(((String) object).substring(16, 20));
			rendicion.setDescripcion(((String) object).substring(20, 140));
			rendicion.setImporte(importe);
			try {
				rendicion.setFechaDesde(toDate.parse(((String) object)
						.substring(140, 150)));
				rendicion.setFechaHasta(toDate.parse(((String) object)
						.substring(150, 160)));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			listaRendiciones.add(rendicion);
		}
		log.info("Se agregan todas las rendiciones a la lista");
	}

	@Override
	public List getDataReturnList() {
		log.info("Se da return al listado de rendiciones");
		return listaRendiciones;
	}
}
