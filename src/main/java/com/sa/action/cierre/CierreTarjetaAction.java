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

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.CierreTarjeta;
import com.sa.services.CierreService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import net.sf.json.JSONArray;

public class CierreTarjetaAction extends RestriccionTransaccionAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("filtrar"))
				return this.filtrar(mapping, form, samClient, request, response);
			else if (action.equals("generar"))
				return this.generar(mapping, samClient, request, response);

			return mapping.findForward("success");
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward filtrar(ActionMapping mapping, ActionForm form, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CierreService service = new CierreService(samClient);
		
		List<CierreTarjeta> cierreTarjeta = service.getCierreTarjeta("F", request.getParameter("usuario"), this.sessionUserWorking.getIdUser());
		request.setAttribute("cierreTarjeta", cierreTarjeta);
		this.message = service.getMsg();

		return mapping.findForward("cierreTarjeta");
	}

	@SuppressWarnings("deprecation")
	private ActionForward generar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		CierreService service = new CierreService(samClient);
		List<Integer> idConsumos = new ArrayList<Integer>();
		JSONArray idConsumosJSON = JSONArray.fromObject(request.getParameter("idConsumos"));

		for (Object object : idConsumosJSON) {
			idConsumos.add(Integer.parseInt((String) object));
		}
		service.generarCierreTarjeta(request.getParameter("usuario"), this.sessionUserWorking.getIdUser(), idConsumos, 
				Double.parseDouble(request.getParameter("totalPesos")), Double.parseDouble(request.getParameter("totalDolares")),
				request.getParameter("motivo"),request.getParameter("tipoGasto"));
		this.message = service.getMsg();

		resp.put("message", this.message);
		return writeJson(response, resp);
	}
}