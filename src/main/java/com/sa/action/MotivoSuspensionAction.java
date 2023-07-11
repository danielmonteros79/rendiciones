package com.sa.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.ComboMotivo;
import com.sa.entities.Usuario;
import com.sa.services.RendicionesService;

public class MotivoSuspensionAction extends RestriccionTransaccionAction {


	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
//		String estado = (String) request.getParameter("estado");
		String idRendicion = request.getParameter("idRendicion");
//		request.setAttribute("estado",estado);
//		request.setAttribute("idRendicion",idRendicion);
		RendicionesService service = new RendicionesService(samClient);
		
		
		List<ComboMotivo> motivo = service.getMotivoRendiciones("6", u.getIdUser());
		request.setAttribute("ComboMotivo", motivo);
		
		return mapping.findForward("success");
	}

}