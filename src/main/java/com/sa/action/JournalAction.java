package com.sa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.Journal;
import com.sa.services.AprobacionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class JournalAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(JournalAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, Object> resp = new HashMap<String, Object>();
			AprobacionesService service = new AprobacionesService(samClient);
			
			List<Journal> journalList = service.getJournal(StringUtils.leftPad(request.getParameter("idRendicion"), 16, "0"));
			
			resp.put("filas", journalList);
			resp.put("message", service.getMsg());

			return writeJson(response, resp);
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}
}
