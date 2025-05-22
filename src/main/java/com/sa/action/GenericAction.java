package com.sa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class GenericAction extends RestriccionTransaccionAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Obtener informacion sobre el modal informativo de inicio
		String modal = (String) request.getSession().getServletContext().getAttribute("modal.inicial");
		request.getSession().setAttribute("modalInicio", modal);
		return mapping.findForward("success");
	}

}
