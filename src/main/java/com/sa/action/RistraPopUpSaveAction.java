package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.form.parametros.ParametrosGastosForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RistraPopUpSaveAction extends RestriccionTransaccionAction {

    private static final Log log = LogFactory.getLog(RistraPopUpSaveAction.class);

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ParametrosGastosForm frm = (ParametrosGastosForm) form;
        frm.setBack(true);

        request.setAttribute("message", "OK");

        return mapping.findForward("success");
    }
}
