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
import com.sa.entities.parametros.ParametroExceptuado;
import com.sa.form.parametros.ParametrosExceptuadosFiltroForm;
import com.sa.services.ParametrosService;

public class ParametrosExceptuadosLoadAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosExceptuadosLoadAction.class);
	private static final String MSG = "message";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosExceptuadosFiltroForm frm = (ParametrosExceptuadosFiltroForm) form;
		ParametrosService service = new ParametrosService (samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosExceptuadosLoadAction. Usuario ("+user.getIdUser()+")");
		
		frm.clear();
		
		List<ParametroExceptuado> exceptuado = new ArrayList<>();
		try {
			exceptuado = service.getExceptuados(user.getIdUser());
			
			if (request.getAttribute(MSG) == null)
				request.setAttribute(MSG, service.getMsgAviso());
		} catch (Exception e) {
			request.setAttribute(MSG, "ERROR: " + e.getCause().getMessage());
		}
		
		request.setAttribute("exceptuados", exceptuado);
		
		return mapping.findForward("success");
	}
}