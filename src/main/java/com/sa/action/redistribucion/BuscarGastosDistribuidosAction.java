package com.sa.action.redistribucion;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.google.gson.Gson;
import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.RendicionesService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BuscarGastosDistribuidosAction extends
        RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RendicionForm renForm = (RendicionForm) form;
        Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
        String gastoSeleccionado = request.getParameter("gastoSeleccionado");
        RendicionesService service = new RendicionesService(samClient);
        log.info("Entra al action BuscarGastosDistribuidosAction. Usuario ("
                + user.getIdUser() + ")");
        Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));

        Rendicion rendicion = (Rendicion) request.getSession().getAttribute(
                "rendicionSelectDerrame");

        List<Gastos> gastos = service.getGastosDistribuidos(
                String.valueOf(rendicion.getId()), "", u.getIdUser(),
                rendicion.getCodMotivo());

        Iterator it = gastos.iterator();
        while (it.hasNext()) {
            Gastos g = (Gastos) it.next();
            if (!(g.getIdGastoOriginal().equals(gastoSeleccionado))) {
                it.remove();
            }
            //
        }

        JSONObject jsonObject = null;
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        int i = 0;
        map.put("gastos", gastos);

        // resp.put("sizeCombo", i);
        ArrayList<String> arrayJsons = new ArrayList<String>();

        arrayJsons.add(gson.toJson(map));

        response.getWriter().print(arrayJsons);

        response.setContentType("application/json");
        response.getWriter().flush();
        response.getWriter().close();

        return null;

    }

}
