package com.sa.action.parametros;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
import com.sa.services.ParametrosService;
import com.sa.util.ParamsConstants;

public class AbmDelegacionesAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory
			.getLog(RelacionUsuarioDelegadoLoadAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ParametrosService service = new ParametrosService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action AbmDelegacionesAction. Usuario ("
				+ user.getIdUser() + ")");
		RelacionUsuarioDelegadoForm formulario = (RelacionUsuarioDelegadoForm) form;
		String optn = formulario.getOpcion().equals(ParamsConstants.SU81_ALTA) ? "A"
				: "M";
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
		try {
			// SimpleDateFormat sdfYMD = new SimpleDateFormat("dd-MM-yyyy");
			String msg = service.abmDelegaciones(formulario, user);
			if (msg.equalsIgnoreCase(""))
				request.setAttribute("msgModOk", ParamsConstants.MJE_MODIF_OK);
			else
				request.setAttribute("msgAviso", msg);

		} catch (Exception e) {
			// TODO: handle exception
			log.info("Error en el AbmDelegacionesAction", e);
			request.setAttribute("msgError", e.getMessage().substring(
					e.getMessage().indexOf(":") + 1));
			// optn = "A";
		}
		request.setAttribute("frmDelegacion", formulario);
		ActionForward ret;
		if (formulario.getOpcion().trim().equals("BAJA")) {
			ret = new ActionForward("/relacionUsuarioDelegado.do");
		} else {
			ret = new ActionForward("/relacionUsuarioDelegadoAlta.do?optn="
					+ optn);
		}

		return ret;
		// return mapping.findForward("success");
	}
}