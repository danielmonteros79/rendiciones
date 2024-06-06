package com.sa.action.cierre;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.CierreTarjeta;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.services.CierreService;
import com.sa.services.RendicionesService;
import com.sa.util.DateUtil;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import net.sf.json.JSONArray;

public class ConsumosNoRendidosAction extends RestriccionTransaccionAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
	 
			try {
				String action = request.getParameter("action") == null ? "" : request.getParameter("action");

				if (action.equals("filtrar")) {
					return this.filtrar(mapping, form, samClient, request, response);
				}else if (action.equals("altaRend")) {
					return this.crearRendicion(mapping, form, samClient, request, response);
				}
			
				return mapping.findForward("success");
			} catch (Exception e) {
				log.error("", e);
				return writeError(response, e);
			}
	}
	
	
	private ActionForward filtrar(ActionMapping mapping, ActionForm form, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CierreService service = new CierreService(samClient);
		String usuario = request.getParameter("usuario");
		String montoMin = request.getParameter("montoMin");
		String montoMax = request.getParameter("montoMax");
		String moneda = request.getParameter("moneda");
		String fechaCierre = request.getParameter("fechaCierre");

		List<CierreTarjeta> rendiciones = service.obtenerCuponesPendientes(fechaCierre, usuario, montoMin, montoMax, moneda);
		request.setAttribute("consumosSinRendir", rendiciones);
		this.message = service.getMsg();

		return mapping.findForward("consumosSinRendir");
	}
	
	private ActionForward crearRendicion(ActionMapping mapping, ActionForm form, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		RendicionesService service = new RendicionesService(samClient);
		String usuario = request.getParameter("usuario");
		String motivo = request.getParameter("motivo");
		String fecha = request.getParameter("fecha");
		String descripcion = request.getParameter("descripcion");
		
		String idRend = service.altaRendicion(usuario, motivo, fecha, fecha, descripcion);
		
		if (idRend.equalsIgnoreCase("") || idRend.equalsIgnoreCase(null)) {
			request.setAttribute("validarTrx", 1);
			return mapping.findForward("failure");
		}


		return writeJson(response, resp);

	}
	
	
	
		
	

}