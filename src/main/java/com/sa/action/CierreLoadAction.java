package com.sa.action;

import java.util.ArrayList;
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
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");

		log.info("Entra al action CierreLoadAction. Usuario (" + user.getIdUser() + ")");

		CierreFiltroForm formFiltro = (CierreFiltroForm) form;
		formFiltro.reset(mapping, request);
		formFiltro.setFechaDesde("");
		formFiltro.setFechaHasta("");
		formFiltro.setIdRendicion("");
		formFiltro.setMotivo("");
		formFiltro.setUser("");

		CierreService cierreService = new CierreService(samClient);
		RendicionesService motivoService = new RendicionesService(samClient);

		List<Rendicion> rendiciones = new ArrayList<Rendicion>();
		List<ComboMotivo> motivo = new ArrayList<ComboMotivo>();
		
		try {
			String message = request.getAttribute("message") == null ? "" :(String) request.getAttribute("message") + "<br>";
			
			rendiciones = cierreService.getDatosRendicion(u.getIdUser(), null, null, null, null, null);
			if (cierreService.getMsg() != null && !message.startsWith("ERROR"))
				message += cierreService.getMsg() + "<br>";
			
			motivo = motivoService.getMotivoRendiciones("4", u.getIdUser());
			if (motivoService.getMsg() != null && !message.startsWith("ERROR"))
				message += motivoService.getMsg();
			
			if (!message.equals(""))
				request.setAttribute("message", message);
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		
		request.setAttribute("rendiciones", rendiciones);
		request.setAttribute("comboMotivo", motivo);
		
		return mapping.findForward("success");
	}
}