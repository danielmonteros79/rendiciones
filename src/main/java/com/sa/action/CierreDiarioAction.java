package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;
import com.sa.services.CierreService;
import com.sa.util.ParamsConstants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CierreDiarioAction extends RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response)
            // ParametrosSUM paramsSUM
            throws Exception {
        CierreService service = new CierreService(samClient);
        Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));

        try {
            request.setAttribute("tipoConsulta", "f");
            log.info("se llama al service para asignar el cupon");
            Date fechaHoy = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
            String feHoy = "";
            if (fechaHoy != null) {
                feHoy = df.format(fechaHoy).trim();
            }
            service.GenerarPagoMarca(ParamsConstants.PAGOS_OPCION,
                    ParamsConstants.PAGOS_IDPROCESO,
                    ParamsConstants.PAGOS_TIPOPROCESOCIERREDIARIO, feHoy,
                    ParamsConstants.PAGOS_ESTADOPROCESO,
                    ParamsConstants.PAGOS_NUMEROREGISTRO,
                    ParamsConstants.PAGOS_DESCRIPCION, user.getIdUser());

            request.setAttribute("messageModifTCJP",
                    "OK: SE COLOCO LA MARCA DE CIERRE DIARIO CORRECTAMENTE "
            );
        } catch (TransactionException e) {
            // TODO: handle exception
            log.error(e);
            request.setAttribute("messageModifTCJP", "ERROR AL COLOCAR MARCA: "
                    + e.getMessage().replace("java.lang.Exception:", ""));
        }

        return mapping.findForward("success");

    }
}
