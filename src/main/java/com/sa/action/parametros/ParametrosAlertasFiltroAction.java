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
import com.sa.entities.parametros.ParametroAlerta;
import com.sa.form.parametros.ParametrosAlertasFiltroForm;
import com.sa.services.ParametrosService;

public class ParametrosAlertasFiltroAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosAlertasFiltroAction.class);
	private static final String MSG = "message";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosAlertasFiltroForm frm = (ParametrosAlertasFiltroForm) form;
		ParametrosService service = new ParametrosService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosAlertaFiltroAction. Usuario (" + user.getIdUser() + ")");
		
		String message = (String) request.getAttribute(MSG);
		if(message == null && ((frm.getCodMotivo().equals("") && !frm.getCodGasto().equals("")) || (!frm.getCodMotivo().equals("") && frm.getCodGasto().equals(""))) ){
			request.setAttribute(MSG,"Debe completar ambos filtros o ninguno");
			return mapping.findForward("fail");
		}

		List<ParametroAlerta> alerta = new ArrayList<>();
		try {
			alerta = service.getAlertas("CONS", frm.getCodMotivo(), frm.getCodGasto());
			request.setAttribute(MSG, service.getMsgAviso());
		} catch (Exception e) {
			request.setAttribute(MSG, "ERROR: " + e.getCause().getMessage());
		}
		request.setAttribute("alerta", alerta);

		request.setAttribute("cmbMotivo", frm.getCmbMotivo());
		request.setAttribute("cmbGasto", frm.getCmbGasto());

		return mapping.findForward("success");
	}
}