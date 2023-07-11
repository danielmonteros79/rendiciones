package com.sa.action.alertas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Rendicion;
import com.sa.form.AlertaForm;
import com.sa.services.RendicionesService;
import com.sa.util.DateUtil;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class ListadoAlertasAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");
			String id = request.getParameter("codigo");
			AlertaForm frm = (AlertaForm) form;
			RendicionesService service = new RendicionesService(samClient);
			List<Rendicion> rendiciones = service.obtenerListadoRendiciones(this.sessionUserWorking.getIdUser(), id, null, "", "");
			for (Rendicion rendicion : rendiciones) {
				frm.setNombreUsuario(this.sessionUserWorking.getIdUser());
				frm.setIdRendicion(rendicion.getId());;
			}
			
			if (action.equals("filtrar")) {
				return this.filtrar(mapping, frm, samClient, request, response);
			}
			else if (action.equals("eliminar"))
				return this.eliminar(samClient, request, response);
			return mapping.findForward("success");
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward filtrar(ActionMapping mapping, ActionForm form, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RendicionesService service = new RendicionesService(samClient);
		String id = request.getParameter("codigo");
		String fechaDesde = request.getParameter("fechaDesde");
		String fechaHasta = request.getParameter("fechaHasta");
		
		
		fechaDesde = fechaDesde == null || fechaDesde.equalsIgnoreCase("") ? "" : DateUtil.dfYYYYMMDD.format(DateUtil.dfDDMMYYYY.parse(fechaDesde));
		fechaHasta = fechaHasta == null || fechaHasta.equalsIgnoreCase("") ? "" : DateUtil.dfYYYYMMDD.format(DateUtil.dfDDMMYYYY.parse(fechaHasta));

		List<Rendicion> rendiciones = service.obtenerListadoRendiciones(this.sessionUserWorking.getIdUser(), id, null, fechaDesde, fechaHasta);
		
		request.setAttribute("rendiciones", rendiciones);
		this.message = service.getMsg();

		return mapping.findForward("rendiciones");
	}
	
	public ActionForward eliminar(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		RendicionesService service = new RendicionesService(samClient);
	
		String idRendicion = request.getParameter("idRendicion");
		service.bajaRendicion(this.sessionUser.getIdUser(), idRendicion);
		resp.put("message", service.getMsg());
		
		return writeJson(response, resp);
	}
}