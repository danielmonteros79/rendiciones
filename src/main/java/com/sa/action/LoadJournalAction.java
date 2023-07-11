package com.sa.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.Journal;
import com.sa.entities.Usuario;
import com.sa.form.JournalForm;
import com.sa.services.AprobacionesService;
import com.sa.services.ParametrosService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class LoadJournalAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(AprobacionDetalleLoadAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JournalForm frm = (JournalForm) form;
		AprobacionesService service = new AprobacionesService(samClient);
		
		frm.setIdRendicion((String) request.getParameter("codigo"));
		String codigo = request.getParameter("codigo");
		codigo = StringUtils.leftPad(codigo, 16, "0");
		try {
			request.setAttribute("journal", service.getJournal(codigo));
			request.setAttribute("message", service.getMsg());
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		return mapping.findForward("success");
	}
}
