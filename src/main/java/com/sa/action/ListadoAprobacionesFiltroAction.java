package com.sa.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.FiltrarAprobacionForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class ListadoAprobacionesFiltroAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {		
		try {
			FiltrarAprobacionForm renForm = (FiltrarAprobacionForm) form;
			renForm.reset(mapping, request);
			Usuario user = ((Usuario) request.getSession().getAttribute("userWorking"));
			log.info("Entra al action ListadoAprobacionesFiltroAction. Usuario (" + user.getIdUser() + ")");
			RendicionesService motivoservice = new RendicionesService(samClient);

			AprobacionesService rendicionesservice = new AprobacionesService(samClient);
			String usuarioFiltro = renForm.getUser().toUpperCase();

			String estado = renForm.getEstado();

			if (renForm.getEstado() == null) {
				renForm.setEstado(request.getParameter("glg"));
			}

			if (request.getParameter("glg") != null) {
				estado = request.getParameter("glg");
			}
			String opcionEstado = renForm.getEstado();
			request.setAttribute("opcionEstado", opcionEstado);

			String messageModifTCJP = "";
			if (request.getAttribute("messageModifTCJP") != null)
				messageModifTCJP += (String) request.getAttribute("messageModifTCJP") + "<br>";
			
			log.info("Se llama al service con los parametros de filtrado para traer el listado de rendiciones pendientes de aprobacion");
			List<Rendicion> rendicion = rendicionesservice.getAprobacionesPendientes(renForm.getIdRendicion().toString(), usuarioFiltro,
					renForm.getMotivo(), estado, user.getIdUser().toUpperCase());
			request.setAttribute("Rendicion", rendicion);
			if (rendicionesservice.getMsg() != null)
				messageModifTCJP += rendicionesservice.getMsg();

			renForm.reset(mapping, request);

			List<ComboMotivo> motivo = motivoservice.getMotivoRendiciones("4", user.getIdUser());
			request.setAttribute("ComboMotivo", motivo);
			
			if (!messageModifTCJP.equals(""))
				request.setAttribute("messageModifTCJP", messageModifTCJP);
		} catch (Exception e) {
			request.setAttribute("messageConsulta", "ERROR:" + e.getCause().getMessage());
			request.setAttribute("opcionEstado", request.getParameter("glg"));

		}
		
		return mapping.findForward("success");
	}
}