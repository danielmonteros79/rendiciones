package com.sa.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.Usuario;
import com.sa.entities.parametros.Resumen;
import com.sa.services.ResumenService;

public class ResumenLoadAction extends RestriccionTransaccionAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResumenService service = new ResumenService(samClient);
		Usuario user = ((Usuario) request.getSession().getAttribute("userWorking"));

		List<Resumen> resumen = new ArrayList<Resumen>();
		try {
			resumen = service.getConsumos(user.getIdUser());
			request.setAttribute("message", service.getMsg());
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		
		request.setAttribute("resumen", resumen);
		
		return mapping.findForward("success");
	}
}