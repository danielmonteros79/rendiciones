package com.sa.action.tarjetaCorporativa;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.parametros.Resumen;
import com.sa.services.ResumenService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class ResumenesAnterioresAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("filtrar"))
				return this.filtrar(mapping, samClient, request);
			
			return mapping.findForward("success");
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward filtrar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request)
			throws Exception {
		ResumenService service = new ResumenService(samClient);

		List<Resumen> resumen = service.getResumenes(this.sessionUserWorking.getIdUser(), request.getParameter("fecha"));
		request.setAttribute("resumen", resumen);
		this.setMessage(service.getMsg(), request);

		return mapping.findForward("resumen");
	}
}