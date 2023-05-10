package com.sa.action.redistribucion;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.PagosService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DistribucionGastosConfirmAction extends
        RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RendicionForm renForm = (RendicionForm) form;
        String accion = request.getParameter("accion");
        String gastoOriginal = request.getParameter("gastoRedistribucion");
        String idRendicion = request.getParameter("idRendicion");
        String montoItems = request.getParameter("montoGastoItems");
        String ccostoItems = request.getParameter("centroCostoItems");
        String gastoItems = request.getParameter("codGastoRedistribucion");
        Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
        log.info("El Usuario (" + user.getIdUser()
                + "). Confirma " + accion + " del Gasto: " + gastoOriginal
                + " para la rendicion: " + idRendicion);
        Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
        // RendicionesService service = new RendicionesService(samClient);
        PagosService service = new PagosService(samClient);
        String error = "";
        String msg = "";
        try {

            msg = service.redistribuirGastos(accion, idRendicion, gastoOriginal, montoItems,
                    ccostoItems, gastoItems, user);

        } catch (Exception e) {
            // TODO: handle exception
            log.error(e);
            error = e.getMessage().substring(e.getMessage().indexOf(":") + 1);
        }
        // String usuarioRend = u.getIdUser();
        JSONObject jsonObject = null;
        Map<String, Object> resp = new HashMap<String, Object>();
        resp.put("error", error);
        resp.put("msg", msg);

        jsonObject = JSONObject.fromObject(resp);
        response.getWriter().print(jsonObject);

        response.setContentType("application/json");
        response.getWriter().flush();
        response.getWriter().close();
        return null;

    }

}
