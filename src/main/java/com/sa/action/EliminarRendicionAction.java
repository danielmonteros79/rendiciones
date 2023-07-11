package com.sa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.Usuario;
import com.sa.services.RendicionesService;

public class EliminarRendicionAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RendicionesService service = new RendicionesService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action EliminarRendicionAction. Usuario (" + user.getIdUser() + ")");
		
		try {
			String idRendicion = request.getParameter("codigo");
			service.bajaRendicion( user.getIdUser(), idRendicion);
			if (service.getMsg() != null)
				request.setAttribute("message", service.getMsg());
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		
		return mapping.findForward("success");
	}
}
