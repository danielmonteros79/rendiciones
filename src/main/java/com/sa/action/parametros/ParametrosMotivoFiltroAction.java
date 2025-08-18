package com.sa.action.parametros;

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
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.services.ParametrosService;

public class ParametrosMotivoFiltroAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosMotivoFiltroAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("filtrar"))
				return this.filtrar(mapping, samClient, request, response);
			
			
			return mapping.findForward("success");
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward filtrar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosService service = new ParametrosService(samClient);

		String codMotivo = "";
		if (request.getParameter("codigo") != null && !request.getParameter("codigo").trim().equals(""))
			codMotivo = String.format("%04d", Integer.parseInt(request.getParameter("codigo")));
		
		List<ParametroMotivo> motivos = service.getMotivos(codMotivo, this.getSessionUserWorking().getIdUser(), "");
		
		request.setAttribute("motivos", motivos);
		this.setMessage(service.getMsgAviso(), request);
		
		return mapping.findForward("parametrosMotivoFiltro");
	}
	
	
	
	
	
}
