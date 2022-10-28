package com.sa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.Usuario;
import com.sa.services.PagosService;

public class EliminarGastoAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		PagosService service = new PagosService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action EliminarGastoAction. Usuario (" + user.getIdUser() + ")");

		String codMotivo = request.getParameter("codMotivo");
		String idRendicion = request.getParameter("codigo");
		String idGasto = request.getParameter("idGasto");
		
		try {
			service.bajaGasto("BAJA", idGasto, user.getIdUser(), idRendicion, codMotivo);
			if (service.getMsg() != null)
				request.getSession().setAttribute("messageModif", service.getMsg());
		} catch (Exception e) {
			request.getSession().setAttribute("messageModif", "ERROR: " + e.getCause().getMessage());
		}
		
		return null;
	}
}