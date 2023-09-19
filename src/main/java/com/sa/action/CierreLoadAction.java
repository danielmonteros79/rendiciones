package com.sa.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.CierreFiltroForm;
import com.sa.services.CierreService;
import com.sa.services.RendicionesService;

public class CierreLoadAction extends RestriccionTransaccionAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");

		log.info("Entra al action CierreLoadAction. Usuario ("+user.getIdUser()+")");

		CierreFiltroForm formFiltro = (CierreFiltroForm) form;
		formFiltro.reset(mapping, request);
		
		CierreService cierreService = new CierreService(samClient);
		RendicionesService motivoService = new RendicionesService(samClient);

		// Service carga Listado de Rendiciones pendientes de aprobacion
		List<Rendicion> rendiciones = cierreService.getDatosRendicion(u.getIdUser(), null, null, null, null, null);
		request.setAttribute("rendiciones", rendiciones);
		
		// Service carga Combo de Motivos
		List<ComboMotivo> motivo = motivoService.getMotivoRendiciones("4", u.getIdUser(), "");
		request.setAttribute("comboMotivo", motivo);
		formFiltro.setFechaDesde("");
		formFiltro.setFechaHasta("");
		formFiltro.setIdRendicion("");
		formFiltro.setMotivo("");
		formFiltro.setUser("");
		return mapping.findForward("success");
	}
}