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
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosMotivoFiltroForm;
import com.sa.services.ParametrosService;

public class ParametrosMotivoFiltroAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosMotivoFiltroAction.class);
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosMotivoFiltroForm frm = (ParametrosMotivoFiltroForm) form;
		ParametrosService service = new ParametrosService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosMotivoFiltroAction. Usuario ("+user.getIdUser()+")");
		
		String codMotivo = "";
		if (frm.getCodigo() != null && !frm.getCodigo().trim().equals(""))
			codMotivo = String.format("%04d", Integer.parseInt(frm.getCodigo()));
		
		List<ParametroMotivo> motivos = new ArrayList<ParametroMotivo>();
		try {
			motivos = service.getMotivos(codMotivo, user.getIdUser());
			
			if (request.getAttribute("message") == null)
				request.setAttribute("message", service.getMsgAviso());
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		
		request.setAttribute("motivos", motivos);
		
		return mapping.findForward("success");
	}
}