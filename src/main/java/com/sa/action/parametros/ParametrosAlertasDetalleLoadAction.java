package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.ComboOpcion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroAlerta;
import com.sa.form.parametros.ParametrosAlertasForm;
import com.sa.services.ParametrosService;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;

public class ParametrosAlertasDetalleLoadAction extends RestriccionTransaccionAction {

    private static final Log log = LogFactory.getLog(ParametrosAlertasDetalleLoadAction.class);
    Map<String, String> mapGastoMotivo = new HashMap<String, String>();
    Map<String, List<ComboOpcion>> mapMotivoGastos = new HashMap<String, List<ComboOpcion>>();
    List<ComboOpcion> cmbGasto = new ArrayList<ComboOpcion>();
    private List<ComboOpcion> cmbMotivo = new ArrayList<ComboOpcion>();

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        ParametrosAlertasForm frm = (ParametrosAlertasForm) form;
        ParametrosService service = new ParametrosService(samClient);
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        log.info("Entra al action ParametrosNuevaAlertaLoadAction. Usuario (" + user.getIdUser() + ")");
        String accion = request.getParameter("accion");

        if ("selectMotivo".equals(accion)) {
            this.selectMotivo(response.getWriter(), request);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().flush();
            response.getWriter().close();
            return null;
        } else if ("selectGasto".equals(accion)) {
            this.selectGasto(response.getWriter(), request);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().flush();
            response.getWriter().close();
            return null;
        }

        try {
            String message = "";

            if (frm.getAccion().equals("alta")) {
                frm.clear();
                frm.setEstado("A");
            } else if (frm.getAccion().equals("modificacion")) {
                this.alertaToForm(frm, service.getAlerta("CONS", frm.getCodMotivo(), frm.getCodGasto(), frm.getTimeStamp()));
                if (service.getMsgAviso() != null) {
                    message = service.getMsgAviso() + "<br>";
                }
            } else if (frm.getAccion().equals("baja")) {
                this.alertaToForm(frm, service.getAlerta("CONS", frm.getCodMotivo(), frm.getCodGasto(), frm.getTimeStamp()));
                if (service.getMsgAviso() != null) {
                    message = service.getMsgAviso() + "<br>";
                }
            }

            List<String> combos = service.getAlertaCombos();
            if (service.getMsgAviso() != null) {
                message += service.getMsgAviso();
            }

            mapGastoMotivo = new HashMap<String, String>();
            mapMotivoGastos = new HashMap<String, List<ComboOpcion>>();
            cmbGasto = new ArrayList<ComboOpcion>();
            cmbMotivo = new ArrayList<ComboOpcion>();

            for (String fila : combos) {
                String combo = fila.substring(0, 2);
                String codMotivo = fila.substring(2, 6);
                if (combo.equals("MO")) {
                    cmbMotivo.add(new ComboOpcion(codMotivo, codMotivo + " - " + fila.substring(7).trim()));
                }

                if (combo.equals("GA")) {
                    String codGasto = fila.substring(6, 10);
                    ComboOpcion opcionGasto = new ComboOpcion(codGasto, codGasto + " - " + fila.substring(10).trim());
                    cmbGasto.add(opcionGasto);
                    mapGastoMotivo.put(codGasto, codMotivo);

                    if (mapMotivoGastos.get(codMotivo) == null) {
                        List<ComboOpcion> gastos = new ArrayList<ComboOpcion>();
                        gastos.add(opcionGasto);
                        mapMotivoGastos.put(codMotivo, gastos);
                    } else {
                        mapMotivoGastos.get(codMotivo).add(opcionGasto);
                    }
                }
            }

            if (!message.equals("")) {
                request.setAttribute("message", message);
            }
        } catch (Exception e) {
            request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
        }

        request.setAttribute("cmbMotivo", cmbMotivo);
        request.setAttribute("cmbGasto", cmbGasto);

        frm.setMapGastoMotivo(mapGastoMotivo);
        frm.setMapMotivoGastos(mapMotivoGastos);
        frm.setCmbGasto(cmbGasto);
        frm.setCmbMotivo(cmbMotivo);

        return mapping.findForward(frm.getAccion());
    }

    private void alertaToForm(ParametrosAlertasForm frm, ParametroAlerta alerta) {
        frm.setCodMotivo(alerta.getCodMotivo());
        frm.setCodGasto(alerta.getCodGasto());
        frm.setEstado(alerta.getEstado());
        frm.setMontCant(alerta.getMontCant());
        frm.setRend(alerta.getRend());
        frm.setPeriodo(alerta.getPeriodo());
        frm.setNivMax(alerta.getNivelMax());
        frm.setNivMin(alerta.getNivelMin());
        frm.setImpCant(alerta.getImpCant());
        frm.setCriticidad(alerta.getCriticidad());
        frm.setTxAviso(alerta.getTxAviso());
    }

    @SuppressWarnings("unchecked")
    private void selectMotivo(PrintWriter writer, HttpServletRequest request) {
        JSONArray jArray = new JSONArray();
        String codMotivo = request.getParameter("codMotivo");
        if (codMotivo == null || codMotivo.trim().equals("")) {
            for (ComboOpcion opcion : cmbGasto) {
                JSONObject jGroup = new JSONObject();
                jGroup.put("codigo", opcion.getId());
                jGroup.put("descripcion", opcion.getDescripcion());

                jArray.add(jGroup);
            }
        } else {
            if (mapMotivoGastos.get(request.getParameter("codMotivo")) != null) {
                for (ComboOpcion opcion : mapMotivoGastos.get(request.getParameter("codMotivo"))) {
                    JSONObject jGroup = new JSONObject();
                    jGroup.put("codigo", opcion.getId());
                    jGroup.put("descripcion", opcion.getDescripcion());

                    jArray.add(jGroup);
                }
            }
        }

        writer.print(jArray);
    }

    private void selectGasto(PrintWriter writer, HttpServletRequest request) {
        JSONObject jsonObject = null;
        Map<String, Object> resp = new HashMap<String, Object>();
        resp.put("codGasto", mapGastoMotivo.get(request.getParameter("codGasto")));
        jsonObject = JSONObject.fromObject(resp);
        writer.print(jsonObject);
    }
}
