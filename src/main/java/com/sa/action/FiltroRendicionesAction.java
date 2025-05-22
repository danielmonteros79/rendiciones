package com.sa.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sa.core.ParametrosSUM;
import com.sa.entities.ComboDelegado;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.FiltroRendicionForm;
import com.sa.services.RendicionesService;
import com.sa.util.ParamsConstants;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class FiltroRendicionesAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ParametrosSUM paramsSUM

		SimpleDateFormat toDate = new SimpleDateFormat("dd/MM/yyyy");
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action FiltroRendicionesAction. Usuario ("+user.getIdUser()+")");
		FiltroRendicionForm formFiltro = (FiltroRendicionForm) form;

		RendicionesService service = new RendicionesService(samClient);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateD = null;
		Date dateH = null;

		if (formFiltro.getFechaDesde() != null
				&& !formFiltro.getFechaDesde().equalsIgnoreCase("")) {
			dateD = formatter.parse(formFiltro.getFechaDesde());
		}
		if (formFiltro.getFechaHasta() != null
				&& !formFiltro.getFechaHasta().equalsIgnoreCase("")) {
			dateH = formatter.parse(formFiltro.getFechaHasta());
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
		String feD = "";
		String feH = "";
		if (dateD != null) {
			feD = df.format(dateD).trim();
		}
		if (dateH != null) {
			feH = df.format(dateH).trim();

		}
		// Service carga Listado de Rendiciones
		List<Rendicion> rendicion = new ArrayList<Rendicion>();
		try {
			rendicion = service.obtenerListadoRendiciones(u.getIdUser(), formFiltro.getId(), formFiltro.getEstado(), feD, feH);
			request.setAttribute("message", service.getMsg());
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		request.setAttribute("Rendicion", rendicion);
		return mapping.findForward("ok");
	}
}
