package com.sa.action;

import com.sa.entities.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ParametrosAction extends RestriccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response
    // ParametrosSUM paramsSUM
    ) throws Exception {
        Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
        log.info("Entra al action ParametrosAction. Usuario (" + user.getIdUser() + ")");
        return mapping.findForward("ok");

    }
}
