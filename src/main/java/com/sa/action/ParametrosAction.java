package com.sa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.Usuario;
import com.sa.exceptions.ActionExecutionException;

public class ParametrosAction extends RestriccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response
	// ParametrosSUM paramsSUM
	) throws ActionExecutionException {
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action ParametrosAction. Usuario ("+user.getIdUser()+")");
		return mapping.findForward("ok");

	}
}
