package com.sa.action.parametros;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.services.ParametrosService;

public class RelacionUsuarioDelegadoLoadAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(RelacionUsuarioDelegadoLoadAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosService service = new ParametrosService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action RelacionUsuarioDelegadoLoadAction. Usuario (" + user.getIdUser() + ")");
		
		request.getSession().removeAttribute("delegacionesActivas");
		List<ParametriaUsuarioDelegado> usuarioDelegado = new ArrayList<ParametriaUsuarioDelegado>();
				
		try {
			usuarioDelegado = service.getRelacionUsuarioDelegado(user.getIdUser());
			request.setAttribute("message", service.getMsgAviso());
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		
		request.setAttribute("ParametriaUsuarioDelegado", usuarioDelegado);
		request.getSession().setAttribute("delegacionesActivas", usuarioDelegado);
		
		return mapping.findForward("success");
	}
}