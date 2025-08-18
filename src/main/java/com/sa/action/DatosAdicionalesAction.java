package com.sa.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.DatosPantallaDinamica;
import com.sa.entities.Usuario;
import com.sa.services.PagosService;
import com.sa.services.UsuarioService;
import com.sa.util.DateUtil;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class DatosAdicionalesAction extends RestriccionTransaccionAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("consulta"))
				return this.consulta(samClient, request, response);
			else if (action.equals("altaModif"))
				return this.altaModif(samClient, request, response);
			else if (action.equals("baja"))
				return this.baja(samClient, request, response);
			else if (action.equals("obtenerCodigosPatagonia"))
				return this.obtenerCodigosPatagonia(samClient, request, response);
			else if(action.equals("buscarInvitado")) 
				return this.buscarInvitado(samClient, request, response);
			else if(action.equals("calcularCombustible"))
				return this.calcularCombustible(samClient, request, response);

			return null;
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}
	
	protected ActionForward calcularCombustible(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		PagosService service = new PagosService(samClient);
		
		String idGasto = request.getParameter("idGasto");
		String idRendicion = request.getParameter("idRendicion");
		String gastoMonto = request.getParameter("gastoMonto");
		String codMotivo = request.getParameter("codMotivo");
		String codGasto = request.getParameter("codGasto");
		String moneda = request.getParameter("moneda");
		String tipoComprobante = request.getParameter("tipoComprobante");
		
		if (idRendicion != null && !idRendicion.isEmpty()) {
		    idRendicion = String.format("%016d", Long.parseLong(idRendicion));
		}

		if (idGasto != null && !idGasto.isEmpty()) {
		    idGasto = String.format("%09d", Integer.parseInt(idGasto));
		}
	    
	    String datos = request.getParameter("datosAdicionesCombustible");
	    String cod1 = null; //00002 vehìculo propio, 00001 vehìculo no propio
	    double num1 = 0; // KM
	    double num2 = 0; // Valor litro

	    if (datos != null && !datos.isEmpty()) {
	        String[] partes = datos.split(",");
	        for (String parte : partes) {
	            parte = parte.trim();
	            if (parte.startsWith("COD1=")) {
	                String valorCrudo = parte.substring("COD1=".length()).trim();
	                cod1 = valorCrudo.split("[\\s\\-]", 2)[0];
	            } else if (parte.startsWith("NUM1=")) {
	                String valor = parte.substring("NUM1=".length()).trim();
	                valor = valor.replace(".", "").replace(",", "."); // En caso de que venga 1.000,00
	                try {
	                    num1 = Double.parseDouble(valor);
	                } catch (NumberFormatException e) {
	                    System.out.println("Error parseando NUM1: " + valor);
	                }
	            } else if (parte.startsWith("NUM2=")) {
	            	String valor = parte.substring("NUM2=".length()).trim();
	            	valor = valor.replaceAll("[^\\d,\\.]", "");
	            	valor = valor.replace(".", "").replace(",", ".");
	                try {
	                    num2 = Double.parseDouble(valor);
	                } catch (NumberFormatException e) {
	                    System.out.println("Error parseando NUM2: " + valor);
	                }
	            }
	        }
	    }
	    
	    double gastoCalculado = num1 * num2;

	    if ("00002".equals(cod1)) {
	        gastoCalculado *= 0.22; // Vehículo propio
	    }
	    
	    gastoMonto = String.valueOf(gastoCalculado);
	    
		service.altaModifGasto("MODI", idGasto, idRendicion, moneda, tipoComprobante, null,
				null, null, codGasto, gastoMonto, null, codMotivo, null,
				null, null, null, null, null, null, null  );
		return writeJson(response, resp);
	}

	private ActionForward consulta(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		String message = "";
		String idRendicion = request.getParameter("idRendicion");
		String idGasto = request.getParameter("idGasto");
		String codMotivo = request.getParameter("codMotivo");
		String codObserv = String.format("%05d", Integer.parseInt(request.getParameter("codObserv")));

		PagosService service = new PagosService(samClient);

		List<DatosPantallaDinamica> fieldsScreen = service.consultaDatosAdicionales(idRendicion, idGasto, codMotivo, codObserv);
		request.setAttribute("listCampos", fieldsScreen);
		if (service.getMsg() != null)
			message += service.getMsg() + "<br>";

		resp.put("listCampos", fieldsScreen);

		Map<Integer, String> headerMap = new TreeMap<Integer, String>();
		for (DatosPantallaDinamica dato : fieldsScreen) {
			if (dato.getTipoCampo().equals("COD1"))
				headerMap.put(1, dato.getTituloCampo());
			else if (dato.getTipoCampo().equals("COD2"))
				headerMap.put(2, dato.getTituloCampo());
			else if (dato.getTipoCampo().equals("TXT1"))
				headerMap.put(3, dato.getTituloCampo());
			else if (dato.getTipoCampo().equals("TXT2"))
				headerMap.put(4, dato.getTituloCampo());
			else if (dato.getTipoCampo().equals("NUM1"))
				headerMap.put(5, dato.getTituloCampo());
			else if (dato.getTipoCampo().equals("NUM2"))
				headerMap.put(6, dato.getTituloCampo());
			else if (dato.getTipoCampo().equals("FEC1"))
				headerMap.put(7, dato.getTituloCampo());
			else if (dato.getTipoCampo().equals("FEC2"))
				headerMap.put(8, dato.getTituloCampo());
			else if (dato.getTipoCampo().equals("TXT250"))
				headerMap.put(9, dato.getTituloCampo());
		}
		resp.put("headers", new ArrayList<String>(headerMap.values()));

		List<List<String>> filas = service.consultaDetallesGastos(idRendicion, idGasto, codObserv, fieldsScreen);
	
		resp.put("filas", filas);
		if (service.getMsg() != null)
			message += service.getMsg() + "<br>";
		
		resp.put("message", message);
		response.setContentType("text/html; charset=UTF-8");

		return writeJson(response, resp);
	}

	private ActionForward altaModif(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		PagosService service = new PagosService(samClient);
		String idRendicion = request.getParameter("idRendicion");
		String idGasto = request.getParameter("idGasto");
		String codGasto = request.getParameter("codGasto");
		String codObserv = request.getParameter("codObserv");
		String idObservacion = request.getParameter("IDOBS");

		String txt1 = request.getParameter("TXT1") == null ? "" : request.getParameter("TXT1");
		String txt2 = request.getParameter("TXT2") == null ? "" : request.getParameter("TXT2");
		String txt250 = request.getParameter("TXT250") == null ? "" : request.getParameter("TXT250");
		String num1 = request.getParameter("NUM1") == null ? "" : request.getParameter("NUM1");
		String num2 = request.getParameter("NUM2") == null ? "" : request.getParameter("NUM2");
		String cod1 = request.getParameter("COD1") == null ? "" : StringUtils.leftPad(request.getParameter("COD1"), 5, "0");
		String cod2 = request.getParameter("COD2") == null ? "" : StringUtils.leftPad(request.getParameter("COD2"), 5, "0");
		String fecha1 = request.getParameter("FEC1") == null || request.getParameter("FEC1").equals("") ? "" :
			DateUtil.formatearFecha(request.getParameter("FEC1"), DateUtil.dfDDMMYYYY, DateUtil.dfYYYYMMDD);
		String fecha2 = request.getParameter("FEC2") == null || request.getParameter("FEC2").equals("") ? "" :
			DateUtil.formatearFecha(request.getParameter("FEC2"), DateUtil.dfDDMMYYYY, DateUtil.dfYYYYMMDD);

		service.altaModifDatoAdicional(idRendicion, idGasto, codGasto, codObserv, idObservacion, txt1, txt2, num1, num2, cod1, cod2, txt250, fecha1, fecha2);
		resp.put("message", "OK: LUEGO DE CARGAR TODAS LAS OBSERVACIONES, PRESIONE SALIR");
		
		return writeJson(response, resp);
	}

	private ActionForward baja(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		PagosService service = new PagosService(samClient);
		String idRendicion = request.getParameter("idRendicion");
		String idGasto = request.getParameter("idGasto");
		String codGasto = request.getParameter("codGasto");
		String codObserv = request.getParameter("codObserv");
		String idObservacion = request.getParameter("idObservacion");
	
		service.bajaDatoAdicional(idRendicion, idGasto, codGasto, codObserv, idObservacion);
		resp.put("message", "OK: DATO ADICIONAL ELIMINADO");
		
		return writeJson(response, resp);
	}
	

	private ActionForward buscarInvitado(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			Map<String, Object> resp = new HashMap<String, Object>();
			UsuarioService service = new UsuarioService(samClient);
			String legajo = request.getParameter("legajo").trim().toUpperCase();
			Usuario invitado = service.obtenerInvitadoUsuario(legajo, "INVI");
			resp.put("invitado", invitado);			
			
			if (service.getMsg() != null)
				resp.put("message", "OK: " + service.getMsg());
			
			return writeJson(response, resp);

		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}		
	}

		private ActionForward obtenerCodigosPatagonia(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			Map<String, Object> resp = new HashMap<String, Object>();
			PagosService service2 = new PagosService(samClient);
			List<String> codigos= service2.getCodigosPatagonia();
			
			resp.put("codigos", codigos);
			
			if (service2.getMsg() != null)
				resp.put("message", "OK: " + service2.getMsg());
			
			return writeJson(response, resp);

		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}	
	}
	
}