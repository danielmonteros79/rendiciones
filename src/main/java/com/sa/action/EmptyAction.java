package com.sa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.core.ParametrosSUM;
import com.sa.entities.Usuario;


public class EmptyAction extends RestriccionAction{
	


	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response
			//ParametrosSUM paramsSIA
			) throws Exception {
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action EmptyAction. Usuario ("+user.getIdUser()+")");
		return mapping.findForward("success");
	}
}