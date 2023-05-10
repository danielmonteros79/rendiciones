package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.CierreForm;
import com.sa.services.CierreService;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CierreSaveAction extends RestriccionTransaccionAction {

    private static final Log log = LogFactory.getLog(CierreSaveAction.class);

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        CierreForm cierreForm = (CierreForm) form;
        Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        log.info("Entra al action CierreSaveAction. Usuario (" + user.getIdUser() + ")");

        CierreService gestionarOrdenService = new CierreService(samClient);
        @SuppressWarnings("unchecked")
        Enumeration<String> params = request.getParameterNames();
        List<Rendicion> rendicionesSeleccionadas = new ArrayList<Rendicion>();
        // System.out.println(param.length());
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            // Adquiere el id de las rendiciones seleccionadas
            // se verifica que el parametro no sea de "estado"
            if (!param.substring(0, 6).equalsIgnoreCase("estado")) {
                if (!param.substring(0, 10).equalsIgnoreCase("cmboMotivo")) {
                    if (param.substring(0, 11).equalsIgnoreCase("idRendicion")) {
                        Rendicion selectR = new Rendicion();

                        selectR.setId(Integer.valueOf(request.getParameter(param)));
                        rendicionesSeleccionadas.add(selectR);
                    }
                }
            }
        }

        cierreForm.setEstado(request.getParameter("estado"));
        // Llama al service enviando los parametros de entrada (id de la orden
        // generada, estado de la orden de pago que se genera y listado de
        // rendiciones seleccionadas
        try {
            gestionarOrdenService.crearOrdenDePago(cierreForm.getEstado(), rendicionesSeleccionadas, u.getIdUser(),
                    cierreForm.getMotivoRechazo(), cierreForm.getCmboMotivo());
            if (gestionarOrdenService.getMsg() != null) {
                request.setAttribute("message", gestionarOrdenService.getMsg());
            }

            if (cierreForm.getEstado().equals("ORDPG")) {
                request.setAttribute("displaySuccess", 1);
            } else if (cierreForm.getEstado().equals("SUSPE")) {
                request.setAttribute("messageModifTCJP", "OK: SUSPENSION REALIZADA CORRECTAMENTE");
                return mapping.findForward("successSusp");
            }
        } catch (Exception e) {
            log.error(e);
            if (cierreForm.getEstado().equals("ORDPG")) {
                request.setAttribute("message", "ERROR: ERROR AL GENERAR ORDEN: " + e.getCause().getMessage());
                return mapping.findForward("success");
            }
            if (cierreForm.getEstado().equals("SUSPE")) {
                request.setAttribute("messageModifTCJP", "ERROR AL SUSPENDER: " + e.getCause().getMessage());
                return mapping.findForward("successSusp");
            }
        }

        return mapping.findForward("success");
    }
}
