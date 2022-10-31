package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.AprobacionesService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RendicionScanLoadAction extends RestriccionTransaccionAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        RendicionForm renForm = (RendicionForm) form;
        Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
        log.info("Entra al action RendicionScanLoadAction. Usuario (" + user.getIdUser() + ")");
        Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
        AprobacionesService service = new AprobacionesService(samClient);
        // Get codigo current row
        Integer idRendicion = null;

        if (request.getParameter("codigo") == null) {
            idRendicion = renForm.getIdRendicion();
            // int idr = Integer.parseInt(idRendicion);
        } else {
            idRendicion = Integer.valueOf(request.getParameter("codigo"));
        }
        service.scanRendicion(String.valueOf(idRendicion), u.getIdUser());

        // Service carga Listado de Rendiciones
        // List<Rendicion> rendiciones =
        // service.obtenerListadoRendiciones(user.getIdUser(), "", "", "", "");
        //		
        // Rendicion rendicion = null;
        // // Recorre la lista de rendiciones
        // for (Rendicion r : rendiciones) {
        // if(r.getId()==(idRendicion)){
        // rendicion = r;
        // break;
        // }
        //				
        // }
        // if (rendicion != null){
        // DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // String dateD = df.format(rendicion.getFechaDesde());
        // String dateH = df.format(rendicion.getFechaHasta());
        // request.setAttribute("Gastos", rendicion.getGastosRendicion());
        // request.setAttribute("Rendicion", rendicion);
        // renForm.setUser(u.getIdUser());
        // renForm.setNombreUsuario(u.getNombre());
        // renForm.setCostos(u.getCcostos());
        // renForm.setSector(u.getSector());
        // renForm.setIdRendicion(rendicion.getId());
        // renForm.setMotivo(rendicion.getMotivo());
        // renForm.setFechaDesde(dateD);
        // renForm.setFechaHasta(dateH);
        // renForm.setDescripcion(rendicion.getDescripcion());
        // }
        // else{
        // ComboMotivo mot = new ComboMotivo();
        //
        // for (ComboMotivo m : mot.getMotivoRendiciones()) {
        // if (m.getId().equals(renForm.getMotivo())) {
        // renForm.setMotivo(m.getDescripcion());
        // break;
        // }
        // }
        // String idRform = request.getParameter("idRendicion");
        // int idr2 = Integer.parseInt(idRform);
        //
        // renForm.setUser(u.getIdUser());
        // renForm.setNombreUsuario(u.getNombre());
        // renForm.setCostos(u.getCcostos());
        // renForm.setSector(u.getSector());
        // renForm.setIdRendicion(idr2);
        // renForm.setMotivo(renForm.getMotivo());
        // renForm.setFechaDesde(renForm.getFechaDesde());
        // renForm.setFechaHasta(renForm.getFechaHasta());
        // renForm.setDescripcion(renForm.getDescripcion());
        // }
        return mapping.findForward("success");
    }
}
