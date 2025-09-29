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

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.form.DelegadosAsignadosForm;
import com.sa.services.ParametrosService;

public class DelegadoSeleccionadoAction extends RestriccionTransaccionAction {

	protected static final Log log = LogFactory.getLog(DelegadoSeleccionadoAction.class);
	List<Usuario> usuarios = new ArrayList<>();
	private static final String USER_WORKING = "userWorking";

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("El usuario: " + user.getIdUser() + " procede a reemplazar delegado.");

		DelegadosAsignadosForm delegadosform = (DelegadosAsignadosForm) form;
		log.info("Delegado seleccionado: " + delegadosform.getDelegado());

		String forward = "rendiciones";
		ParametrosService service = new ParametrosService(samClient);
		try {
			List<ParametriaUsuarioDelegado> usuarioDelegado = service.getRelacionUsuarioDelegado(delegadosform.getDelegado());

			Usuario userWork = null;
			for (Usuario u : user.getDelegadosAsignados()) {
				if (u.getIdUser().equalsIgnoreCase(delegadosform.getDelegado())) {
					userWork = u;
					if (u.getIdUser().equalsIgnoreCase(user.getIdUser())) {
						userWork.setNombre(user.getNombre());
						request.getSession().setAttribute(USER_WORKING, user);
						break;
					}
				}
				if (userWork != null) {
					for (ParametriaUsuarioDelegado delegado : usuarioDelegado) {
						if (user.getIdUser().equals(delegado.getDelegadoUser())) {
							if (delegado.getDelegadoAccion().equals("A")) {
								userWork.setTipoPerfil("DELEG_APROB");
								forward = "aprobaciones";
							} else if (delegado.getDelegadoAccion().equals("I")) {
								userWork.setTipoPerfil("DELEG_REND");}
							else if (delegado.getDelegadoAccion().equals("T")) {
								userWork.setTipoPerfil("DELEG_REND_APROB");}
						}
					}

					request.getSession().setAttribute(USER_WORKING, userWork);
				} else {
					request.getSession().setAttribute(USER_WORKING, user);
				}
			}

			return mapping.findForward(forward);
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		
		return mapping.findForward("failure");
	}
}