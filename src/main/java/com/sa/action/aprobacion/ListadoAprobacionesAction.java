package com.sa.action.aprobacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Rendicion;
import com.sa.services.AprobacionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import net.sf.json.JSONArray;


public class ListadoAprobacionesAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ListadoAprobacionesAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("filtrar"))
				return this.filtrar(mapping, samClient, request, response);
			else if (action.equals("aprobar"))
				return this.aprobar(mapping, samClient, request, response);

			AprobacionesService service = new AprobacionesService(samClient);
			service.getAprobacionesPendientes("", "", "", request.getParameter("glg"), this.sessionUserWorking.getIdUser());
			String cantRendiciones = service.getCantRendiciones();
			request.setAttribute("cantRendiciones", cantRendiciones.equals("") ? 0 : cantRendiciones);
			request.setAttribute("glg", request.getParameter("glg"));
			
			return mapping.findForward("success");
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward filtrar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AprobacionesService service = new AprobacionesService(samClient);

		String alerta = request.getParameter("nroAlerta");
		List<Rendicion> rendicion = service.getAprobacionesPendientes(request.getParameter("idRendicion"), request.getParameter("usuario").trim().toUpperCase(),
				request.getParameter("motivo"), request.getParameter("glg"), this.sessionUserWorking.getIdUser());
	
		List<Rendicion> rendicionAlerta = new ArrayList<Rendicion>();
		
		List<Rendicion> rendicionesAlerta = new ArrayList<Rendicion>();
		if(alerta.equals("1")) {
			for (Rendicion r : rendicion) {
				if(r.getAdea().substring(0,1).equals("1")) {
					rendicionesAlerta.add(r);
			}
		}
			rendicion = rendicionesAlerta;
		}else if(alerta.equals("0") ) {
			for (Rendicion r : rendicion) {
				System.out.println(r); 
				System.out.println("Adea" + r.getAdea());
			if(r.getAdea().equals("0000000000") || r.getIdu() ==null ) {
				rendicionesAlerta.add(r);
			}
			}	
			rendicion = rendicionesAlerta;
		}	
		
		
		

		request.setAttribute("Rendicion", rendicion);


	
		this.message = service.getMsg();
		
		return mapping.findForward("aprobaciones");
	}

	@SuppressWarnings("deprecation")
	private ActionForward aprobar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		AprobacionesService service = new AprobacionesService(samClient);
		List<Integer> idRendiciones = new ArrayList<Integer>();
		JSONArray idRendicionesJSON = JSONArray.fromObject(request.getParameter("idRendiciones"));

		for (Object object : idRendicionesJSON) {
			idRendiciones.add(Integer.parseInt((String) object));
		}

		this.message = service.cambiarEstadoRendiciones(this.sessionUserWorking.getIdUser(), idRendiciones, "APROB", null, request.getParameter("glg"));

		if (this.message != null && this.message.equalsIgnoreCase("OPERACION EFECTUADA"))
			this.message = "OK: APROBO CORRECTAMENTE " + (idRendiciones.size() == 1 ? "LA RENDICION" : "LAS RENDICIONES");

		resp.put("message", this.message);
		return writeJson(response, resp);
	}
}
