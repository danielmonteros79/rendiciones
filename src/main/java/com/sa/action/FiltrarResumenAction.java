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
import com.sa.form.ResumenForm;
import com.sa.services.ResumenService;

public class FiltrarResumenAction extends RestriccionTransaccionAction{
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario user = ((Usuario) request.getSession().getAttribute("userWorking"));
		ResumenForm frm = (ResumenForm) form;
		ResumenService service = new ResumenService(samClient);
		
		List<Resumen> resumenes = new ArrayList<Resumen>();
				
		try {
			resumenes = service.getResumenes(user.getIdUser(), frm.getResumen());
			request.setAttribute("message", service.getMsg());
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}

		request.setAttribute("cmbResumen", frm.getCmbResumen());
		request.setAttribute("resumenes", resumenes);

		return mapping.findForward("success");
	}
}