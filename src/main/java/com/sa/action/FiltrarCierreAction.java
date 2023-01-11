package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.CierreFiltroForm;
import com.sa.services.CierreService;
import com.sa.services.RendicionesService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarCierreAction extends RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
            SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
        Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
        log.info("Entra al action FiltrarCierreAction. Usuario (" + user.getIdUser() + ")");

        CierreFiltroForm renForm = (CierreFiltroForm) form;
        renForm.reset(mapping, request);

        CierreService cierreService = new CierreService(samClient);
        RendicionesService motivoService = new RendicionesService(samClient);

        List<Rendicion> rendiciones = new ArrayList<Rendicion>();
        List<ComboMotivo> motivo = new ArrayList<ComboMotivo>();

        try {
            String message = "";

            rendiciones = cierreService.getDatosRendicion(u.getIdUser(),
                    renForm.getIdRendicion(), renForm.getMotivo(), renForm.getUser(), renForm.getFechaDesde(), renForm.getFechaHasta());
            if (cierreService.getMsg() != null) {
                message += cierreService.getMsg() + "<br>";
            }

            motivo = motivoService.getMotivoRendiciones("4", u.getIdUser());
            if (motivoService.getMsg() != null) {
                message += motivoService.getMsg();
            }

            if (!message.equals("")) {
                request.setAttribute("message", message);
            }
        } catch (Exception e) {
            request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
        }

        request.setAttribute("rendiciones", rendiciones);
        request.setAttribute("comboMotivo", motivo);

        return mapping.findForward("success");
    }
}
