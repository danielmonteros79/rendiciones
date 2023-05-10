package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.form.CuponesForm;
import com.sa.services.PagosService;
import com.sa.services.trxs.SU56;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CuponesSaveAction extends RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PagosService service = new PagosService(samClient);
        Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");

        log.info("Entra al action CuponesSaveAction. Usuario (" + user.getIdUser() + ")");
        CuponesForm cupForm = (CuponesForm) form;
        cupForm.reset(mapping, request);
        if (request.getParameter("tipoSubmit").equalsIgnoreCase("1")) {

            try {
                request.setAttribute("tipoConsulta", "f");
                log.info("se llama al service para asignar el cupon");
                service.asignarCupon(SU56.OPCION_MODIFICAR, cupForm
                        .getIdRendicion(), cupForm.getIdGastoRend(), u
                        .getIdUser(), cupForm.getImporteCupon().replace("^.*",
                                ","), cupForm.getNroTarjeta(), cupForm.getCupon(),
                        cupForm.getCupDeb(), cupForm.getCupCred(), cupForm
                        .getDescCupon(), cupForm.getMoneda(), cupForm.getFechaPresentacion());

                request.setAttribute("messageModifTCJP",
                        "OK: SE GUARDÓ CORRECTAMENTE EL CUPÓN "
                );
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e);
                request.setAttribute("messageModifTCJP",
                        "ERROR AL GUARDAR CUPONES: " + e.getMessage());
            }
            request.setAttribute("codigo", cupForm.getIdRendicion());

            return mapping.findForward("success");

        } else {
            request.setAttribute("formCupones", cupForm);
            cupForm = null;
            return mapping.findForward("nuevoGasto");

        }
    }
}
