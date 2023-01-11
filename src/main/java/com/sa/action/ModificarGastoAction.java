package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboGasto;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.Gastos;
import com.sa.entities.Usuario;
import com.sa.form.RendicionDetalleForm;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import com.sa.util.ParamsConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;

public class ModificarGastoAction extends RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        RendicionDetalleForm renForm = (RendicionDetalleForm) form;
        Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
        Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
        log.info("Entra al action ModificarGastoAction. Usuario (" + user.getIdUser() + ")");
        renForm.setEstadoRendicion(request.getParameter("estadoRendicion"));
        request.setAttribute("estadoRend", request.getParameter("estadoRendicion"));
        PagosService service = new PagosService(samClient);
        RendicionesService serviceCombos = new RendicionesService(samClient);
        Integer idRendicion = null;
        if (request.getParameter("codigo") == null) {

            idRendicion = (Integer.parseInt((String) request.getAttribute("codigo")));
            // int idr = Integer.parseInt(idRendicion);
        } else {
            idRendicion = Integer.valueOf(request.getParameter("codigo"));
        }
        request.setAttribute("idRendicion", idRendicion);

        String accion = request.getParameter("accion");
        if ("selectTipoGasto".equals(accion)) {
            this.selectTipoGasto(response.getWriter(), request, serviceCombos, u.getIdUser());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().flush();
            response.getWriter().close();
            return null;
        }

        String fechaD = request.getParameter("fechaD");
        String fechaH = request.getParameter("fechaH");
        request.setAttribute("fechaD", fechaD);
        request.setAttribute("fechaH", fechaH);
        request.setAttribute("costos", u.getCcostos());
        log.info("Se llama al service que realiza la carga de los combos para la modificacion de gastos");

        String message = "";

        List<ComboOpcion2> moneda = new ArrayList<ComboOpcion2>();
        List<ComboGasto> tipoGastos = new ArrayList<ComboGasto>();
        List<ComboOpcion2> comprobante = new ArrayList<ComboOpcion2>();

        try {
            moneda = serviceCombos.getComboOpcion2(ParamsConstants.MONEDA_OPCION, ParamsConstants.MONEDA_TABLA
                    + ParamsConstants.MONEDA_SUBTABLA + ParamsConstants.MONEDA_CODIGO, ParamsConstants.MONEDA_CANTIDAD, u.getIdUser());
            if (serviceCombos.getMsg() != null) {
                message += serviceCombos.getMsg() + "<br>";
            }

            tipoGastos = service.getComboGasto(ParamsConstants.TIPO_GASTO_OPCION, u.getIdUser(), request.getParameter("codMotivo"));
            if (service.getMsg() != null) {
                message += service.getMsg() + "<br>";
            }

            String idGasto = request.getParameter("idGasto");
            List<Gastos> gastos = serviceCombos.getGastos(idRendicion.toString(), idGasto, u.getIdUser(), request.getParameter("codMotivo"));
            if (gastos.size() == 0) {
                request.setAttribute("message", "ERROR: GASTO INEXISTENTE");
            } else {
                if (serviceCombos.getMsg() != null) {
                    message += service.getMsg() + "<br>";
                }

                Gastos gasto = gastos.get(0);

                ComboGasto comboGastos = null;
                for (ComboGasto cg : tipoGastos) {
                    if (cg.getDescripcion().trim().equalsIgnoreCase(gasto.getDescGasto())) {
                        comboGastos = cg;
                        break;
                    }
                }

                ComboOpcion2 comboMoneda = null;
                for (ComboOpcion2 cm : moneda) {
                    if (cm.getId().trim().equalsIgnoreCase(gasto.getMoneda())) {
                        comboMoneda = cm;
                        break;
                    }
                }

                comprobante = serviceCombos.getComboOpcion2(ParamsConstants.COMPROBANTE_OPCION,
                        ParamsConstants.COMPROBANTE_TABLA + ParamsConstants.COMPROBANTE_SUBTABLA + ParamsConstants.COMPROBANTE_CODIGO,
                        ParamsConstants.COMPROBANTE_CANTIDAD, u.getIdUser(), comboGastos.getId());
                if (serviceCombos.getMsg() != null) {
                    message += serviceCombos.getMsg() + "<br>";
                }

                ComboOpcion2 comboComprobante = null;
                for (ComboOpcion2 cf : comprobante) {
                    if (cf.getId().equalsIgnoreCase(gasto.getTipoComprobante())) {
                        comboComprobante = cf;
                        break;
                    }
                }

                renForm.setIdRendicion(request.getParameter("codigo"));
                renForm.setCodMotivo(request.getParameter("codMotivo"));
                renForm.setCentroCostos(request.getParameter("cCosto"));
                renForm.setNombreUsuario(u.getNombre());
                renForm.setCostos(u.getCcostos());
                renForm.setGastos(comboGastos.getId());
                renForm.setFechagastos(gasto.getFechagastos());
                renForm.setMoneda(comboMoneda.getId());
                renForm.setComprobante(comboComprobante.getId());
                renForm.setCostosDestino(gasto.getCostosDestino());
                renForm.setIdG(gasto.getIdGasto());
                renForm.setMonto(gasto.getMonto().trim());
                renForm.setMontoMaximo(gasto.getMonto().trim());
                renForm.setObservacionGasto(gasto.getObservacionGasto());
                renForm.setCmbComprobante(gasto.getCmbComprobante());
                renForm.setComprobante1(gasto.getComprobante1());
                renForm.setComprobante2(gasto.getComprobante2());
                renForm.setCuit1(gasto.getCuit1());
                renForm.setCuit2(gasto.getCuit2());
                renForm.setCuit3(gasto.getCuit3());
                renForm.setUsuarioRend(request.getParameter("user"));
                renForm.setGlg(request.getParameter("glg"));

                if (request.getParameter("tieneCupon").equalsIgnoreCase("1")) {
                    renForm.setImporteCupon(gasto.getMonto().trim());
                    request.setAttribute("conCupon", "1");
                } else {
                    renForm.setCupCred("");
                    renForm.setCupDeb("");
                    renForm.setCupon("");
                    renForm.setCuponesCheck("");
                    renForm.setDescCupon("");
                    renForm.setImporteCupon("");
                    renForm.setNroTarjeta("");
                }

                if (request.getParameter("listadoAprob").equalsIgnoreCase("1")) {
                    request.setAttribute("listadoAprob", "1");
                }

                if (request.getParameter("opcion").equalsIgnoreCase("MODI")) {
                    request.setAttribute("opcionTitulo", "1");
                }
            }
        } catch (Exception e) {
            request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
        }

        request.setAttribute("ComboMoneda", moneda);
        request.setAttribute("ComboGastos", tipoGastos);
        request.setAttribute("ComboComprobante", comprobante);

        if (!message.equals("")) {
            request.setAttribute("message", message);
        }

        return mapping.findForward("success");
    }

    @SuppressWarnings("unchecked")
    private void selectTipoGasto(PrintWriter writer, HttpServletRequest request, RendicionesService serviceCombos, String user) throws TransactionException {
        JSONArray jArray = new JSONArray();
        String codTipoGasto = request.getParameter("codTipoGasto");
        List<ComboOpcion2> comprobantes = serviceCombos.getComboOpcion2(ParamsConstants.COMPROBANTE_OPCION,
                ParamsConstants.COMPROBANTE_TABLA + ParamsConstants.COMPROBANTE_SUBTABLA + ParamsConstants.COMPROBANTE_CODIGO,
                ParamsConstants.COMPROBANTE_CANTIDAD, user, codTipoGasto);

        if (comprobantes != null) {
            for (ComboOpcion2 opcion : comprobantes) {
                JSONObject jGroup = new JSONObject();
                jGroup.put("codigo", opcion.getId());
                jGroup.put("descripcion", opcion.getDescripcion());

                jArray.add(jGroup);
            }
        }

        writer.print(jArray);
    }
}
