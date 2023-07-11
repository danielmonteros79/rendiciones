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
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.form.AlertaForm;
import com.sa.services.RendicionesService;
import com.sa.util.DateUtil;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class DetalleAlertaAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		AlertaForm frm = (AlertaForm) form;
		
		
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");
			if (action.equals("filtrar"))
				return this.filtrar(mapping, frm, samClient, request, response);
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
		String motivo = request.getParameter("codMotivo");
		String codUsuario = request.getParameter("codUsuario");
	
		List<Rendicion> rendiciones = service.obtenerListadoRendiciones(this.sessionUserWorking.getIdUser(), id, null, "", "");
	
		List<Gastos> gastos = service.getGastos(id, "", codUsuario,motivo);
		
		for (Gastos g : gastos) {
			if(g.getIdRendicion().equals("1287")) {
				if(g.getIdGasto().equals("2")) {
					g.setAlerta("EL GASTO NRO: " +  g.getNroGasto() + " SUPERA EL MONTO MENSUAL (10) SE INFORMARON 34");
				}
			}else {
				g.setAlerta("EL GASTO NRO: " + g.getNroGasto() + " SUPERA LA CANTIDAD MENSUAL (1) SE INFORMARON 2");
				
			}
		}

		request.setAttribute("rendiciones", gastos);
		request.setAttribute("rendicionesData", rendiciones);

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