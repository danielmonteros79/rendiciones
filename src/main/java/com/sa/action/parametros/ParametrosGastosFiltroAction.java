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
import com.sa.entities.parametros.ParametroGasto;
import com.sa.form.parametros.ParametrosGastosFiltroForm;
import com.sa.services.ParametrosService;

public class ParametrosGastosFiltroAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosGastosFiltroAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosGastosFiltroForm frm = (ParametrosGastosFiltroForm) form;
		ParametrosService service = new ParametrosService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosMotivoFiltroAction. Usuario (" + user.getIdUser() + ")");
		
		String codGasto= "";
		if (frm.getGasto() != null && !frm.getGasto().equals(""))
			codGasto = String.format("%04d", Integer.parseInt(frm.getGasto()));
		
		List<ParametroGasto> gastos = new ArrayList<ParametroGasto>();
		try {
			gastos = service.getGastos(user.getIdUser(), codGasto);
			
			if (request.getAttribute("message") == null)
				request.setAttribute("message", service.getMsgAviso());
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		
		request.setAttribute("gastos", gastos);

		return mapping.findForward("success");
	}
}