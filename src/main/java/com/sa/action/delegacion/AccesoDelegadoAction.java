package com.sa.action.delegacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.services.AprobacionesService;
import com.sa.services.ParametrosService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class AccesoDelegadoAction extends RestriccionTransaccionAction {
	protected static final Log log = LogFactory.getLog(AccesoDelegadoAction.class);
	private static final String USER_WORKING = "userWorking";
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action") == null ? "" : request.getParameter("action");
		
		if (action.equals("reemplazar"))
			return reemplazar(request, response, samClient);

		return mapping.findForward("success");
	}

	private ActionForward reemplazar(HttpServletRequest request, HttpServletResponse response, SAMWebClient samClient) throws Exception {
		try {
			Map<String, Object> resp = new HashMap<>();
			ParametrosService service = new ParametrosService(samClient);
			String delegadoSel = request.getParameter("delegado");
			List<ParametriaUsuarioDelegado> usuarioDelegado = service.getDelegaciones(delegadoSel);
			Usuario userWork = obtenerUsuarioTrabajo(delegadoSel);
			if (userWork != null) {
				actualizarTipoPerfilDelegado(usuarioDelegado, userWork);
				verificarAprobaciones(userWork, samClient);
				request.getSession().setAttribute(USER_WORKING, userWork);
			} else {
				request.getSession().setAttribute(USER_WORKING, this.getSessionUser());
			}
			return writeJson(response, resp);
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private Usuario obtenerUsuarioTrabajo(String delegadoSel) {
		for (Usuario u : this.getSessionUser().getDelegadosAsignados()) {
			if (u.getIdUser().equalsIgnoreCase(delegadoSel)) {
				if (u.getIdUser().equalsIgnoreCase(this.getSessionUser().getIdUser())) {
					u.setNombre(this.getSessionUser().getNombre());
					return u;
				} else {
					return u;
				}
			}
		}
		return null;
	}

	private void actualizarTipoPerfilDelegado(List<ParametriaUsuarioDelegado> usuarioDelegado, Usuario userWork) {
		for (ParametriaUsuarioDelegado delegado : usuarioDelegado) {
			if (this.getSessionUser().getIdUser().equals(delegado.getDelegadoUser())) {
				if (delegado.getDelegadoAccion().equals("A"))
					userWork.setTipoPerfil("DELEG_APROB");
				else if (delegado.getDelegadoAccion().equals("I"))
					userWork.setTipoPerfil("DELEG_REND");
				else if (delegado.getDelegadoAccion().equals("T"))
					userWork.setTipoPerfil("DELEG_REND_APROB");
			}
		}
	}

	private void verificarAprobaciones(Usuario userWork, SAMWebClient samClient) {
		if (userWork.getTipoPerfil().getPantalla().contains("Aprobacion")) {
			AprobacionesService aprobacionesService = new AprobacionesService(samClient);
			for (int i = 1; i < 5; i++) {
				try {
					aprobacionesService.getAprobacionesPendientes("", "", "", Integer.toString(i), userWork.getIdUser());
					userWork.getGlgAprobacion().add(i);
				} catch (Exception e) {}
			}
		}
	}



}
