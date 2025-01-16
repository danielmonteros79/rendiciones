package com.sa.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.Usuario;
import com.sa.form.LoginForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.UsuarioService;
import com.sa.services.trxs.SU53;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import org.apache.commons.text.StringEscapeUtils;

public class PuntoEntradaAction extends RestriccionTransaccionAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		LoginForm loginForm = (LoginForm) form;
		List errorList = new ArrayList();
		if (loginForm.getUsername() == null || "".equals(loginForm.getUsername().trim())) {

			errorList.add(new String("Por favor, ingresar Usuario"));
			request.setAttribute("errores", errorList);
			return mapping.findForward("failure");

		}

		else if (loginForm.getPassword() == null || "".equals(loginForm.getPassword().trim())) {
			errorList.add(new String("Por favor, ingresar Contrase�a"));
			request.setAttribute("errores", errorList);
			return mapping.findForward("failure");

		}

		UsuarioService serviceUsuario = new UsuarioService();
		String username = StringEscapeUtils.escapeHtml4(loginForm.getUsername());
		Usuario usuario = serviceUsuario.obtenerDelegadosUsuario(username);
		if (usuario == null) {
		    errorList.add("Usuario inexistente");
		    request.setAttribute("errores", errorList);
		    return mapping.findForward("failure");
		}

		request.getSession().setAttribute("userWorking", usuario);
		request.getSession().setAttribute("usuario", usuario);
		
			
		return mapping.findForward("success");
	}

}
