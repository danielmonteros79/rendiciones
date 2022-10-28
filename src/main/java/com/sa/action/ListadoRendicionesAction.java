package com.sa.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.FiltroRendicionForm;
import com.sa.services.RendicionesService;

public class ListadoRendicionesAction extends RestriccionTransaccionAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action ListadoRendicionesAction. Usuario ("+user.getIdUser()+")");
		
		FiltroRendicionForm formFiltro = (FiltroRendicionForm) form;
		formFiltro.setEstado("");
		formFiltro.setFechaDesde("");
		formFiltro.setFechaHasta("");
		formFiltro.setId("");
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		RendicionesService service = new RendicionesService(samClient);
		
		List<Rendicion> rendicion = new ArrayList<Rendicion>();
		try {
			String message = request.getAttribute("message") == null ? "" : (String) request.getAttribute("message") + "<br>";
			message += request.getSession().getAttribute("msg") == null ? "" : (String) request.getSession().getAttribute("msg") + "<br>";
			
			rendicion = service.obtenerListadoRendiciones(u.getIdUser(), "", "", "", "");
			if (service.getMsg() != null && message.equals(""))
				message += service.getMsg();
			
			if (!message.equals(""))
				request.setAttribute("message", message);
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		request.setAttribute("Rendicion", rendicion);
		
		return mapping.findForward("ok");
	}
}