package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Usuario;
import com.sa.form.AprobacionForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MotivoObservarSaveAction extends RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        AprobacionForm frm = (AprobacionForm) form;
        Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
        RendicionesService rendicionesService = new RendicionesService(samClient);
        AprobacionesService aprobacionesService = new AprobacionesService(samClient);

        log.info("Entra al action MotivoObservarSaveAction. Usuario (" + u.getIdUser() + ")");

        List<ComboMotivo> motivo = rendicionesService.getMotivoRendiciones("7", u.getIdUser());
        request.setAttribute("ComboMotivo", motivo);

        try {
            aprobacionesService.cambiarEstadoDeUnaRendicion(u.getIdUser(), frm.getId(), "OBSER", frm.getCmboMotivo() + " - " + frm.getMotivoRechazo(), frm.getGlg());
            request.setAttribute("messageModifTCJP", "OK: OBSERVACION DADA DE ALTA CORRECTAMENTE");
        } catch (Exception e) {
            log.error(e);
            request.setAttribute("messageModifTCJP", "ERROR AL DAR DE ALTA OBSERVACION: " + e.getCause().getMessage());
        }

        return mapping.findForward("success");
    }
}
