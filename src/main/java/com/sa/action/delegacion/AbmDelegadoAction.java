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
import com.sa.form.delegacion.AbmDelegadoForm;
import com.sa.services.ParametrosService;
import com.sa.util.ParamsConstants;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class AbmDelegadoAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(AbmDelegadoAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
									   HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("getDelegados"))
				return this.getDelegados(samClient, mapping, request);
			else if (action.equals("getDelegado"))
				return this.getDelegado(request, response);
			else if (action.equals("buscarUsuario"))
				return this.buscarUsuario(samClient, request, response);
			else if (action.equals("abm"))
				return this.abm(form, samClient, request, response);

			return mapping.findForward("success");
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward getDelegados(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request) throws Exception {
		ParametrosService service = new ParametrosService(samClient);
		List<ParametriaUsuarioDelegado> usuarioDelegados = service.getDelegaciones(this.getSessionUser().getIdUser());
		this.setMessage(service.getMsgAviso(), request);
		request.setAttribute("delegados", usuarioDelegados);
		request.getSession().setAttribute("delegacionesActivas", usuarioDelegados);

		return mapping.findForward("delegados");
	}

	@SuppressWarnings("unchecked")
	private ActionForward getDelegado(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		String id = request.getParameter("id");

		for (ParametriaUsuarioDelegado delegado : (List<ParametriaUsuarioDelegado>) request.getSession().getAttribute("delegacionesActivas")) {
			if (Integer.valueOf(id).equals(delegado.getId()) ) {
				resp.put("delegado", delegado);
				break;
			}
		}

		return writeJson(response, resp);
	}

	private ActionForward buscarUsuario(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		ParametrosService service = new ParametrosService(samClient);
		String legajo = request.getParameter("legajo").trim().toUpperCase();
		try {
			Usuario delegado = service.getUsuarioDelegacion(legajo, ParamsConstants.SU81_CONSULTA);
			resp.put("success", true);
			resp.put("delegado", delegado);
		} catch (Exception e) {
			log.error("Usuario no encontrado", e);

			resp.put("success", false);
			resp.put("error", "Usuario no encontrado");
			resp.put("message", "No se encontró el usuario con legajo: " + legajo);
		}

		return writeJson(response, resp);
	}

	private ActionForward abm(ActionForm form, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		AbmDelegadoForm frm = (AbmDelegadoForm) form;
		ParametrosService service = new ParametrosService(samClient);
		String message = service.abmDelegaciones(frm, this.getSessionUser());
		resp.put("message", message.equals("") ? ParamsConstants.MJE_MODIF_OK : message);

		return writeJson(response, resp);
	}
}
