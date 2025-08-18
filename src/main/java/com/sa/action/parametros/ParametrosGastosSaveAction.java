package com.sa.action.parametros;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Usuario;
import com.sa.form.parametros.ParametrosGastosForm;
import com.sa.services.ParametrosService;

public class ParametrosGastosSaveAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosGastosSaveAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosGastosForm frm = (ParametrosGastosForm) form;
		ParametrosService service = new ParametrosService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosGastosSaveAction. Usuario (" + user.getIdUser() + ")");
		String forward = "failure";

		if (frm.getAccion().equals("alta"))
			forward = this.alta(request, frm, service);
		else if (frm.getAccion().equals("baja"))
			forward = this.baja(request, frm, service);
		else if (frm.getAccion().equals("modificacion"))
			forward = this.modificacion(request, frm, service);

		return mapping.findForward(forward);
	}

	private String alta(HttpServletRequest request, ParametrosGastosForm frm, ParametrosService service) throws ParseException {
		String ret = "fail";

		try {
			service.altaGasto(frm);

			if (service.getMsgAviso() == null || service.getMsgAviso().equals("")) {
				ret = "success";
				request.setAttribute("message", "OK: ALTA EFECTUADA");
			} else
				request.setAttribute("message", service.getMsgAviso());
		} catch (TransactionException e) {
			log.error(e);
			request.setAttribute("message", e.getCause().getMessage());
		}

		return ret;
	}

	private String baja(HttpServletRequest request, ParametrosGastosForm frm, ParametrosService service) throws ParseException {
		String ret = "fail";

		try {
			service.bajaGasto(frm.getCodigo(), frm.getMotivo());

			if (service.getMsgAviso() == null || service.getMsgAviso().equals("")) {
				ret = "success";
				request.setAttribute("message", "OK: BAJA EFECTUADA");
			} else
				request.setAttribute("message", service.getMsgAviso());
		} catch (TransactionException e) {
			log.error(e);
			request.setAttribute("message", e.getCause().getMessage());
		}

		return ret;
	}

	private String modificacion(HttpServletRequest request, ParametrosGastosForm frm, ParametrosService service) throws ParseException {
		String ret = "fail";

		try {
			service.modificacionGasto(frm);

			if (service.getMsgAviso() == null || service.getMsgAviso().equals("")) {
				ret = "success";
				request.setAttribute("message", "OK: MODIFICACION EFECTUADA");
			} else
				request.setAttribute("message", service.getMsgAviso());
		} catch (TransactionException e) {
			log.error(e);
			request.setAttribute("message", e.getCause().getMessage());
		}

		return ret;
	}
}