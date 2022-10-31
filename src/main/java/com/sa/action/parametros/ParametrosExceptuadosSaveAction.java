package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Usuario;
import com.sa.form.parametros.ParametrosExceptuadosForm;
import com.sa.services.ParametrosService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ParametrosExceptuadosSaveAction extends RestriccionTransaccionAction {

    private static final Log log = LogFactory.getLog(ParametrosExceptuadosSaveAction.class);

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ParametrosExceptuadosForm frm = (ParametrosExceptuadosForm) form;
        ParametrosService service = new ParametrosService(samClient);
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        log.info("Entra al action ParametrosExceptuadoSaveAction. Usuario (" + user.getIdUser() + ")");
        String respuesta = "";
        if (frm.getAccion().equals("alta")) {

            try {
                respuesta = service.altaExceptuado(frm);
                frm.clear();
                request.setAttribute("message", "OK: " + respuesta);
            } catch (Exception e) {
                request.setAttribute("message", e.getCause().getMessage());
                return mapping.findForward("fail");
            }

        } else if (frm.getAccion().equals("baja")) {
            respuesta = service.deleteExceptuado(frm);
            request.setAttribute("message", "OK: " + respuesta);
        } else if (frm.getAccion().equals("modificacion")) {
            respuesta = service.saveModExceptuado(frm);
            request.setAttribute("message", "OK: " + respuesta);
        }
//		List<ParametroExceptuado> exceptuado = service.getExceptuados(user.getIdUser());
//		request.setAttribute("exceptuados", exceptuado);
        return mapping.findForward("success");
    }
}
