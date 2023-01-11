package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.FiltrarAprobacionForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AprobacionAction extends RestriccionTransaccionAction {

    private static final Log log = LogFactory.getLog(AprobacionAction.class);

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Usuario user = ((Usuario) request.getSession().getAttribute("userWorking"));
        log.info("Entra al action AprobacionAction. Usuario (" + user.getIdUser() + ")");

        FiltrarAprobacionForm formFiltro = (FiltrarAprobacionForm) form;
        formFiltro.reset(mapping, request);
        AprobacionesService aprobacionesservice = new AprobacionesService(samClient);
        RendicionesService motivoservice = new RendicionesService(samClient);

        try {
            List<ComboMotivo> motivo = new ArrayList<ComboMotivo>();
            if (request.getParameter("glg").equals("1") || request.getParameter("glg").equals("2")) {
                motivo = motivoservice.getMotivoRendiciones("8", user.getIdUser());
            } else {
                motivo = motivoservice.getMotivoRendiciones("9", user.getIdUser());
            }
            request.setAttribute("ComboMotivo", motivo);

            List<Rendicion> rendicion = aprobacionesservice.getAprobacionesPendientes("", "", "", request.getParameter("glg"),
                    user.getIdUser());

            request.setAttribute("Rendicion", rendicion);
            request.setAttribute("messageModifTCJP", aprobacionesservice.getMsg());

            String cantRendiciones = aprobacionesservice.getCantRendiciones();
            if (!(cantRendiciones.equals("") || cantRendiciones.equals("0"))) {
                request.setAttribute("cantRendiciones", cantRendiciones);
            }

            formFiltro.setEstado(request.getParameter("glg"));
            formFiltro.setMotivo("");
            formFiltro.setUser("");
            formFiltro.setIdRendicion("");

            String opcionEstado = formFiltro.getEstado();
            request.setAttribute("opcionEstado", opcionEstado);
        } catch (Exception e) {
            request.setAttribute("messageConsulta", "ERROR:" + e.getCause().getMessage());
            request.setAttribute("opcionEstado", request.getParameter("glg"));
        }

        return mapping.findForward("success");
    }
}
