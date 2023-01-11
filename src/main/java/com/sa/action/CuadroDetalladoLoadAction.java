package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboMotivo;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.Usuario;
import com.sa.form.CuadroFiltroForm;
import com.sa.services.RendicionesService;
import com.sa.util.ParamsConstants;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CuadroDetalladoLoadAction extends RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
            SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
        Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
        log.info("Entra al action CuadroDetallado. Usuario (" + user.getIdUser() + ")");

        String message = "";

        CuadroFiltroForm frm = (CuadroFiltroForm) form;
        frm.clear();
        frm.setCodEstado(request.getParameter("codEstado"));
        frm.setNombreUsuario(u.getNombre());
        frm.setCostos(u.getCcostos());

        RendicionesService service = new RendicionesService(samClient);
        List<ComboOpcion2> estado = new ArrayList<ComboOpcion2>();
        List<ComboMotivo> motivo = new ArrayList<ComboMotivo>();

        try {
            estado = service.getComboOpcion2(ParamsConstants.EST_REND_OPCION, ParamsConstants.EST_REND_TABLA
                    + ParamsConstants.EST_REND_SUBTABLA + ParamsConstants.EST_REND_CODIGO, ParamsConstants.EST_REND_CANTIDAD, user.getIdUser());
            if (service.getMsg() != null) {
                message += service.getMsg() + "<br>";
            }

            frm.setComboGlg(service.getGlgsUsuario(user.getIdUser(), user.getFacultades()));
            if (service.getMsg() != null) {
                message += service.getMsg() + "<br>";
            }

            motivo = service.getMotivoRendiciones("9", u.getIdUser());
            if (service.getMsg() != null) {
                message += service.getMsg();
            }

            if (!message.equals("")) {
                request.setAttribute("message", message);
            }
        } catch (Exception e) {
            request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
        }

        frm.setComboEstado(estado);
        frm.setComboMotivo(motivo);
        request.setAttribute("ComboMotivo", motivo);
        request.setAttribute("ComboEstado", estado);
        request.setAttribute("ComboGlg", frm.getComboGlg());
        request.setAttribute("Tabla", "f");

        return mapping.findForward("success");
    }
}
