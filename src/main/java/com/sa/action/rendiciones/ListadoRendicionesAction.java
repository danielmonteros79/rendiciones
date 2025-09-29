package com.sa.action.rendiciones;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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
import java.time.ZoneId;

public class ListadoRendicionesAction extends RestriccionTransaccionAction {
	
	public final SimpleDateFormat dfYYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// Verificar que sessionUserWorking no sea null
			if (this.getSessionUserWorking() == null) {
				log.error("sessionUserWorking es null en ListadoRendicionesAction");
				return writeError(response, new Exception("Sesión de usuario no válida"));
			}
			
			// Verificar que el ID de usuario no sea null o vacío
			String userId = this.getSessionUserWorking().getIdUser();
			if (userId == null || userId.trim().isEmpty()) {
				log.error("ID de usuario es null o vacío en ListadoRendicionesAction");
				return writeError(response, new Exception("ID de usuario no válido"));
			}
			
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
				this.getSessionUserWorking().getIdUser(), id, null, "", "");

		LocalDate filtroDesde = fechaDesdeFormatted.isEmpty() ? null : toLocalDate(dfYYYY_MM_DD.parse(fechaDesdeFormatted));
		LocalDate filtroHasta = fechaHastaFormatted.isEmpty() ? null : toLocalDate(dfYYYY_MM_DD.parse(fechaHastaFormatted));

		List<Rendicion> rendicionesFiltradas = rendiciones.stream()
			.filter(r -> {
				LocalDate rendicionDesde = toLocalDate(r.getFechaDesde());
				LocalDate rendicionHasta = toLocalDate(r.getFechaHasta());

				boolean cumpleDesde = filtroDesde == null || !rendicionHasta.isBefore(filtroDesde);
				boolean cumpleHasta = filtroHasta == null || !rendicionDesde.isAfter(filtroHasta);

				return cumpleDesde && cumpleHasta;
			})
			.collect(Collectors.toList());

		request.setAttribute("rendiciones", rendicionesFiltradas);
		String serviceMessage = service.getMsg();
		if (serviceMessage != null) {
			request.getSession().setAttribute("lastErrorMessage", serviceMessage);
		}

		return mapping.findForward("rendiciones");
	}

	
	public ActionForward eliminar(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		RendicionesService service = new RendicionesService(samClient);
	
		String idRendicion = request.getParameter("idRendicion");
		service.bajaRendicion(this.getSessionUser().getIdUser(), idRendicion);
		resp.put("message", service.getMsg());
		
		return writeJson(response, resp);
	}
	
	private LocalDate toLocalDate(Date date) {
		if (date == null) return null;
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	@Override
    public ActionForward writeError(HttpServletResponse response, Exception e) throws com.sa.exceptions.JsonResponseException {
        return super.writeError(response, e);
    }


}
