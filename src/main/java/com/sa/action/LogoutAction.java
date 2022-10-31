package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends RestriccionTransaccionAction {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
            SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        log.info("El usuario: " + ((Usuario) request.getSession().getAttribute("usuario")).getIdUser()
                + " cierra session");

        request.getSession().invalidate();

        return mapping.findForward("success");
    }
}
