package com.sa.action.cierre;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.CierreTarjeta;
import com.sa.entities.Rendicion;
import com.sa.services.CierreService;
import com.sa.services.RendicionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class AprobacionesPendientesAction extends RestriccionTransaccionAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("filtrar")) {
				return this.filtrar(mapping, form, samClient, request, response);
			}

			return mapping.findForward("success");
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward filtrar(ActionMapping mapping, ActionForm form, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CierreService service = new CierreService(samClient);
		String usuario = request.getParameter("usuario");
		String montoMin = request.getParameter("montoMin");
		String montoMax = request.getParameter("montoMax");
		String moneda = request.getParameter("moneda");
		String glg = request.getParameter("glg");
		String fechaCierre = request.getParameter("fechaCierre");

		List<CierreTarjeta> rendiciones = service.obtenerAprobacionesPendientes(fechaCierre, usuario, montoMin, montoMax, moneda, "04");
		request.setAttribute("aprobacionesPendientes", rendiciones);
		this.setMessage(service.getMsg(), request);

		return mapping.findForward("aprobacionesPendientes");
	}
	

	

}