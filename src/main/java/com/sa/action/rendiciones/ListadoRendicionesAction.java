package com.sa.action.rendiciones;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.sa.services.RendicionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.org.bbva.util.DateUtils;

import java.util.stream.Collectors;

public class ListadoRendicionesAction extends RestriccionTransaccionAction {
	
	public final SimpleDateFormat dfYYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("filtrar"))
				return this.filtrar(mapping, form, samClient, request, response);
			else if (action.equals("eliminar"))
				return this.eliminar(samClient, request, response);

			return mapping.findForward("ok");
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward filtrar(ActionMapping mapping, ActionForm form, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RendicionesService service = new RendicionesService(samClient);
		String id = request.getParameter("id");
		String fechaDesdeStr = request.getParameter("fechaDesde");
		String fechaHastaStr = request.getParameter("fechaHasta");

		String fechaDesdeFormatted = (fechaDesdeStr == null || fechaDesdeStr.trim().isEmpty()) ? "" :
				dfYYYY_MM_DD.format(DateUtils.dfDDMMYYYY.parse(fechaDesdeStr));
		String fechaHastaFormatted = (fechaHastaStr == null || fechaHastaStr.trim().isEmpty()) ? "" :
				dfYYYY_MM_DD.format(DateUtils.dfDDMMYYYY.parse(fechaHastaStr));

		List<Rendicion> rendiciones = service.obtenerListadoRendiciones(
				this.sessionUserWorking.getIdUser(), id, null, fechaDesdeFormatted, fechaHastaFormatted);

		Date filtroDesde = fechaDesdeFormatted.isEmpty() ? null : dfYYYY_MM_DD.parse(fechaDesdeFormatted);
		Date filtroHasta = fechaHastaFormatted.isEmpty() ? null : dfYYYY_MM_DD.parse(fechaHastaFormatted);

		List<Rendicion> rendicionesFiltradas = rendiciones.stream()
			.filter(r -> {
				Date rendicionDesde = r.getFechaDesde();
				Date rendicionHasta = r.getFechaHasta();

				boolean empiezaDespuesDeFiltro = (filtroHasta == null || !rendicionDesde.after(filtroHasta));
				boolean terminaAntesDeFiltro = (filtroDesde == null || !rendicionHasta.before(filtroDesde));

				return empiezaDespuesDeFiltro && terminaAntesDeFiltro;
			})
			.collect(Collectors.toList());

		request.setAttribute("rendiciones", rendicionesFiltradas);
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