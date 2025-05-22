package com.sa.delegados.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.ComboDelegado;
import com.sa.entities.Usuario;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class ReplaceDelegadoAction extends RestriccionTransaccionAction {

	protected static final Log log = LogFactory.getLog(ReplaceDelegadoAction.class);

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("El usuario: " + user.getIdUser() + " ingresa al Panel de Reemplazo de delegados.");
				
		List<ComboDelegado> comboDelegado = new ArrayList<>();

		for (Usuario u : user.getDelegadosAsignados()) {
			if(u.getIdUser().equalsIgnoreCase(user.getIdUser()))
				comboDelegado.add(new ComboDelegado(user.getIdUser(), "< Yo mismo >"));
			else
				comboDelegado.add(new ComboDelegado(u.getIdUser(), "<" + u.getIdUser() + " - " + u.getNombre() + ">"));
			
		}
		request.setAttribute("ComboUsuarios", comboDelegado);

		return mapping.findForward("success");
	}

}
