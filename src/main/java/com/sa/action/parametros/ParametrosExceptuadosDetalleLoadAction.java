package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroExceptuado;
import com.sa.form.parametros.ParametrosExceptuadosForm;
import com.sa.services.ParametrosService;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ParametrosExceptuadosDetalleLoadAction extends RestriccionTransaccionAction {

    private static final Log log = LogFactory.getLog(ParametrosExceptuadosDetalleLoadAction.class);
    private SimpleDateFormat sdfYMD = new SimpleDateFormat("dd/MM/yyyy");

    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
            SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ParametrosExceptuadosForm frm = (ParametrosExceptuadosForm) form;
        ParametrosService service = new ParametrosService(samClient);
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        log.info("Entra al action ParametrosNuevaExceptuadoLoadAction. Usuario (" + user.getIdUser() + ")");

        if (frm.getAccion().equals("alta")) {
            frm.clear();
            frm.setMotivoUsuario("");
            frm.setEstado("A");
        } else if (frm.getAccion().equals("modificacion")) {
            this.exceptuadoToForm(frm, service.getExceptuado(frm.getMarca(), frm.getMotivoUsuario(), user.getIdUser()).get(0));
        } else if (frm.getAccion().equals("baja")) {
            this.exceptuadoToForm(frm, service.getExceptuado(frm.getMarca(), frm.getMotivoUsuario(), user.getIdUser()).get(0));
        }

        return mapping.findForward(frm.getAccion());
    }

    private void exceptuadoToForm(ParametrosExceptuadosForm frm, ParametroExceptuado exceptuado) {
        String marca = "";
        if ("M".equals(frm.getMarca())) {
            marca = "motivo";
        }
        if ("U".equals(frm.getMarca())) {
            marca = "usuario";
        }
        frm.setMotivoUsuario(marca);
        frm.setDescripcionNombre(exceptuado.getDescripcionNombre());
        frm.setHasta(sdfYMD.format(exceptuado.getHasta()));
        frm.setDesde(sdfYMD.format(exceptuado.getDesde()));
        frm.setEstado(exceptuado.getEstado());
        frm.setDesMotivo(exceptuado.getMotivoUsuario());
        frm.setDescripcionCodigo(exceptuado.getDescripcionNombre());
    }
}
