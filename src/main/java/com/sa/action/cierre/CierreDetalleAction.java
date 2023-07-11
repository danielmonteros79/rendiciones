package com.sa.action.cierre;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.RendicionesService;
import com.sa.services.UsuarioService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.org.bbva.util.DateUtils;

public class CierreDetalleAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(CierreDetalleAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.message = "";
		String action = request.getParameter("action") == null ? "" : request.getParameter("action");
		
		if (action.equals("getRendicionGastos"))
			return this.getRendicionGastos(samClient, mapping, request);
		
		try {
			RendicionForm renForm = (RendicionForm) form;
			String idRendicion = request.getParameter("codigo");
			String usuarioRend = request.getParameter("usuarioRend");
			
			RendicionesService service = new RendicionesService(samClient);
			UsuarioService usuarioService = new UsuarioService(samClient);
			
			List<Rendicion> rendiciones = service.obtenerListadoRendiciones(usuarioRend, idRendicion, "", "", "");
			if (rendiciones.size() == 0) {
				request.setAttribute("Rendicion", new Rendicion());
				this.message = "ERROR: RENDICION INEXISTENTE";
				return mapping.findForward("success");
			}
			
			Rendicion rendicion = rendiciones.get(0);
			if (service.getMsg() != null)
				this.message = service.getMsg() + "<br>";
			
			Usuario usuarioRendicion = usuarioService.obtenerDelegadosUsuario(usuarioRend);
			if (usuarioService.getMsg() != null)
				this.message += service.getMsg();
	
			String dateD = DateUtils.dfDDMMYYYY.format(rendicion.getFechaDesde());
			String dateH = DateUtils.dfDDMMYYYY.format(rendicion.getFechaHasta());
			request.setAttribute("Rendicion", rendicion);
			renForm.setUser(usuarioRendicion.getIdUser());
			renForm.setNombreUsuario(usuarioRendicion.getNombre());
			renForm.setCostos(usuarioRendicion.getCcostos());
			renForm.setSector(usuarioRendicion.getSector());
			renForm.setIdRendicion(rendicion.getId());
			renForm.setMotivo(String.valueOf(rendicion.getMotivo()));
			renForm.setFechaDesde(dateD);
			renForm.setFechaHasta(dateH);
			renForm.setDescripcion(rendicion.getDescripcion());
			renForm.setCodMotivo(rendicion.getCodMotivo());
			renForm.setMotivoRechazo(rendicion.getMotivoRechazo());
			if (renForm.getMotivoRechazo() != null && !renForm.getMotivoRechazo().equalsIgnoreCase(""))
				request.setAttribute("motivoRechazo", "Si");
			else
				request.setAttribute("motivoRechazo", "No");
			
			renForm.setLinkThuban((String) request.getSession().getServletContext().getAttribute("rendicion.link.thuban") + idRendicion);
			
			if (!message.equals(""))
				request.setAttribute("message", message);
		} catch (Exception e) {
			log.error("", e);
			this.setErrorMessage(e);
		}

		return mapping.findForward("success");
	}
	
	private ActionForward getRendicionGastos(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request) throws Exception {
		RendicionesService service = new RendicionesService(samClient);
		List<Gastos> gastos = service.getGastos(request.getParameter("idRendicion"), "", request.getParameter("usuarioRend"), request.getParameter("codMotivo"));
		this.message = service.getMsg();
		request.setAttribute("gastos", gastos);
		request.setAttribute("showOpciones", false);
		request.setAttribute("readOnlyDatosAdicionales", true);
		request.setAttribute("readOnlyCupones", true);
		
		return mapping.findForward("gastos");
	}
}
