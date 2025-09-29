package com.sa.action.parametros;

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
import com.sa.form.parametros.ParametrosAlertasForm;
import com.sa.services.AprobacionesService;
import com.sa.services.ParametrosService;

public class ParametrosAlertasSaveAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosAlertasSaveAction.class);
	
	private ParametrosService parametrosService;
	
	public ParametrosAlertasSaveAction() {}
	
	public ParametrosAlertasSaveAction(ParametrosService parametrosService) {
		this.parametrosService = parametrosService;
	}

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosAlertasForm frm = (ParametrosAlertasForm) form;
		ParametrosService service = this.parametrosService != null ? this.parametrosService : new ParametrosService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosAlertaSaveAction. Usuario (" + user.getIdUser() + ")");
		String resultado = "";

		try {
			if (frm.getAccion().equals("alta"))
				resultado = service.altaParamAlerta(frm);
			else if (frm.getAccion().equals("baja"))
				resultado = service.bajaParamAlerta(frm);
			else if (frm.getAccion().equals("modificacion"))
				resultado = service.modificacionParamAlerta(frm);
		} catch (TransactionException e) {
			log.error(e);
			request.setAttribute("message", e.getCause().getMessage());
			return mapping.findForward("fail");
		}

		request.setAttribute("message", "OK: " + resultado);
		return mapping.findForward("success");
	}
}