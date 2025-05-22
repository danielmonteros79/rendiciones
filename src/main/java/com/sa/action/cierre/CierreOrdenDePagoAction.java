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
import com.sa.entities.Rendicion;
import com.sa.services.CierreService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.org.bbva.util.DateUtils;
import net.sf.json.JSONArray;

public class CierreOrdenDePagoAction extends RestriccionTransaccionAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("filtrar"))
				return this.filtrar(mapping, form, samClient, request, response);
			else if (action.equals("generar"))
				return this.generar(mapping, samClient, request, response);
			else if (action.equals("suspender"))
				return this.suspender(mapping, samClient, request, response);

			return mapping.findForward("success");
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward filtrar(ActionMapping mapping, ActionForm form, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CierreService service = new CierreService(samClient);
		String idRendicion = request.getParameter("idRendicion");
		String motivo = request.getParameter("motivo");
		String user = request.getParameter("usuario");
		String fechaDesde = request.getParameter("fechaDesde");
		String fechaHasta = request.getParameter("fechaHasta");

		fechaDesde = fechaDesde == null || fechaDesde.equalsIgnoreCase("") ? "" : DateUtils.dfYYYYMMDD.format(DateUtils.dfDDMMYYYY.parse(fechaDesde));
		fechaHasta = fechaHasta == null || fechaHasta.equalsIgnoreCase("") ? "" : DateUtils.dfYYYYMMDD.format(DateUtils.dfDDMMYYYY.parse(fechaHasta));

		List<Rendicion> rendiciones = service.getDatosRendicion(this.sessionUserWorking.getIdUser(), idRendicion, motivo, user, fechaDesde, fechaHasta);
		request.setAttribute("rendiciones", rendiciones);
		this.message = service.getMsg();

		return mapping.findForward("cierreOrdenDePago");
	}

	@SuppressWarnings("deprecation")
	private ActionForward generar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		CierreService service = new CierreService(samClient);
		List<Integer> idRendiciones = new ArrayList<Integer>();
		JSONArray idRendicionesJSON = JSONArray.fromObject(request.getParameter("idRendiciones"));

		for (Object object : idRendicionesJSON) {
			idRendiciones.add(Integer.parseInt((String) object));
		}

		service.crearOrdenDePago("ORDPG", idRendiciones, this.sessionUserWorking.getIdUser(), null, null);
		this.message = service.getMsg();

		resp.put("message", this.message);
		return writeJson(response, resp);
	}

	@SuppressWarnings("deprecation")
	private ActionForward suspender(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		CierreService service = new CierreService(samClient);
		List<Integer> idRendiciones = new ArrayList<Integer>();
		JSONArray idRendicionesJSON = JSONArray.fromObject(request.getParameter("idRendiciones"));

		for (Object object : idRendicionesJSON) {
			idRendiciones.add(Integer.parseInt((String) object));
		}

		service.crearOrdenDePago("SUSPE",idRendiciones, this.sessionUserWorking.getIdUser(), request.getParameter("descripcion"), request.getParameter("codMotivo"));
		this.message = service.getMsg();

		resp.put("message", this.message);
		return writeJson(response, resp);
	}
}