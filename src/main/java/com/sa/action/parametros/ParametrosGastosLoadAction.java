package com.sa.action.parametros;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.parametros.ParametroGasto;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.services.ParametrosService;

public class ParametrosGastosLoadAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosGastosLoadAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {

			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("filtrar"))
				return this.filtrar(mapping, samClient, request, response);
			

		} catch (Exception e) {
			String errorMessage = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
			request.setAttribute("message", "ERROR: " + errorMessage);
		}
		
		
		return mapping.findForward("success");
	}
	
	
	private ActionForward filtrar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosService service = new ParametrosService(samClient);
		System.out.println(request.getParameter("gasto") + " Codigo aaah");
		String codGasto = "";
		String codMotivo = request.getParameter("motivo");

		//String codMotivo = "";
		if (request.getParameter("gasto") != null && !request.getParameter("gasto").trim().equals(""))
			codGasto = String.format("%04d", Integer.parseInt(request.getParameter("gasto")));
		
		List<ParametroGasto> gastos = new ArrayList<ParametroGasto>();
		try {
			gastos = service.getGastos(this.getSessionUserWorking().getIdUser(),codGasto, codMotivo);
		}catch(Exception e) {
			System.out.println("PASOPORACA: " + e.getMessage());
		}
		
		//System.out.println("Gastos vacìo: " + gastos.isEmpty());
		//System.out.println("Gasos: " + gastos.toString());
		
		if(!gastos.isEmpty()) {
			for (ParametroGasto parametroGasto : gastos) {
				if (parametroGasto.getEstado().equalsIgnoreCase("A")) {
					parametroGasto.setEstado("ACTIVO");
				} if (parametroGasto.getEstado().equalsIgnoreCase("I")){
					parametroGasto.setEstado("INACTIVO");
				}
				
			}
		
			request.setAttribute("gastos", gastos);
		}
		this.setMessage(service.getMsgAviso(), request);
		
		return mapping.findForward("parametrosGastoFiltro");
	}
	
	
	
}
