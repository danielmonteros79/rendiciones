package com.sa.services.trxs;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sa.entities.Rendicion;
import com.sa.services.Transaction;

import ar.com.bbva.web.IWebClient;
import ar.com.itrsa.sam.TransactionException;

@SuppressWarnings("rawtypes")
public class SU61 extends Transaction {
	private static final Log log = LogFactory.getLog(SU61.class);
	public List<Rendicion> listaRendiciones = new ArrayList<Rendicion>();
	public String cantRendiciones = "";

	public SU61() {
		this.PARAMETER_TRX = "SUM_CONS_REND_PEND_APROB";
		this.CURRENT_TRX = "SU61";
	}

	@Override
	public void executeTrx(IWebClient client, Map<String, Object> parametersExecute) throws TransactionException {
		try {
			execute(client, this.PARAMETER_TRX, parametersExecute);			
			mapData(parametersExecute);
		} catch (Exception e) {
			log.error("", e);
			throw new TransactionException(e);
		}
	}

	@Override
	protected void mapData(Map<String, Object> parametersExecute) {
		List list = (List) parametersExecute.get("lista");

		if (list != null) {
			for (Object obj : list) {
				String str = getStrLista(obj);

				String importe = (str.substring(210, 227).replaceFirst("^0*", ""));
				Rendicion rendicion = new Rendicion();
				SimpleDateFormat toDate = new SimpleDateFormat("yyyy-MM-dd");
				rendicion.setGlg((String) parametersExecute.get("glg"));

				rendicion.setId(Integer.parseInt(str.substring(0, 16).replaceFirst("^0*", "")));
				rendicion.setIdMotivo(Integer.parseInt(str.substring(16, 20)));
				rendicion.setCodMotivo(str.substring(16, 20));
				rendicion.setDescripcionMotivo(str.substring(20, 70).trim());

				rendicion.setDescripcion(str.substring(70, 190).trim());
				rendicion.setImporte(importe);
				try {
					rendicion.setFechaDesde(toDate.parse(str.substring(190, 200)));
					rendicion.setFechaHasta(toDate.parse(str.substring(200, 210)));

				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				rendicion.setUsuarioRendicion(str.substring(227, 235));
				rendicion.setNombreUsuarioRendicion(str.substring(235, 310).trim());
				rendicion.setEstado(str.substring(310, 315).trim());
				rendicion.setDescripcionEstado(str.substring(315, str.length()).trim());
				rendicion.setAdea("00000000000");
				if (str.length() > 365) {
					rendicion.setDescripcionEstado(str.substring(315, 365).trim());
					rendicion.setIdu(str.substring(365, 375));
					rendicion.setAdea(str.substring(375, 385));
				}
				
				listaRendiciones.add(rendicion);
			}
		}
		
		if (parametersExecute.get("cantidad") != null && !((String)parametersExecute.get("cantidad")).trim().equals("")) {
			this.cantRendiciones = (String) parametersExecute.get("cantidad");
			DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(new Locale("es_AR"));
			DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
			symbols.setGroupingSeparator('.');
			formatter.setDecimalFormatSymbols(symbols);
			this.cantRendiciones = formatter.format(Long.parseLong(this.cantRendiciones.replaceFirst("^0+(?!$)", "")));
		}
	}

	@Override
	public List getDataReturnList() {
		return listaRendiciones;
	}
	
	@Override
	public Object getDataReturn() {
		return this.cantRendiciones;
	}

	@Override
	protected void hardcodear(Map<String, Object> parametersExecute) throws Exception {
		List<String> retList = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		
		list.add("00000000000011080205COMPRA DE BIENES DE USO                           DESCRIPCION 1                                                                                                           2018-11-262018-11-26          2005,00A103555 NOMBRE USUARIO REND 1                                                      PSUP DESC ESTADO 1");
		list.add("00000000000011070204USO DE VEHICULOS DE GESTION COMERCIAL             DESCRIPCION 2                                                                                                           2019-01-062019-02-12           252,63A103556 NOMBRE USUARIO REND 2                                                      OBSERDESC ESTADO 2");
		
		if (!parametersExecute.get("id_rend").equals("")) {
			for (String row : list) {
				if (Integer.parseInt(row.substring(0, 16)) == Integer.parseInt((String) parametersExecute.get("id_rend"))) {
					retList.add(row);
					break;
				}
			}
		} else {
			retList = list;}
		
		parametersExecute.put("lista", retList);
		parametersExecute.put("cantidad", String.valueOf(list.size()));
	}
}