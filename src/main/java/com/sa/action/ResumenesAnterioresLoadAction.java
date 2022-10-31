package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboOpcion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.Resumen;
import com.sa.form.ResumenForm;
import com.sa.services.ResumenService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ResumenesAnterioresLoadAction extends RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResumenForm frm = (ResumenForm) form;
        frm.setResumen("");
        ResumenService service = new ResumenService(samClient);
        Usuario user = ((Usuario) request.getSession().getAttribute("userWorking"));

        List<ComboOpcion> fechas = new ArrayList<ComboOpcion>();

        try {
            fechas = (List<ComboOpcion>) service.getFechasResumenes(user.getIdUser());
            request.setAttribute("message", service.getMsg());
        } catch (Exception e) {
            request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
        }
        frm.setCmbResumen(fechas);

        request.setAttribute("cmbResumen", frm.getCmbResumen());
        request.setAttribute("resumenes", new ArrayList<Resumen>());

        return mapping.findForward("success");
    }
}
