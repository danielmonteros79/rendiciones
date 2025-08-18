package com.sa.action.aprobacion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import com.sa.services.UsuarioService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class AprobacionDetalleAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(AprobacionDetalleAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Verificar que sessionUserWorking no sea null
		if (this.getSessionUserWorking() == null) {
			log.error("sessionUserWorking es null en AprobacionDetalleAction");
			return writeError(response, new Exception("Sesión de usuario no válida"));
		}
		
		// Verificar que el ID de usuario no sea null o vacío
		String userId = this.getSessionUserWorking().getIdUser();
		if (userId == null || userId.trim().isEmpty()) {
			log.error("ID de usuario es null o vacío en AprobacionDetalleAction");
			return writeError(response, new Exception("ID de usuario no válido"));
		}
		
		String action = request.getParameter("action") == null ? "" : request.getParameter("action");
		
		if (action.equals("getRendicionGastos"))
			return this.getRendicionGastos(samClient, mapping, request);
		else if (action.equals("aprobar"))
			return this.aprobar(samClient, mapping, request, response);
		else if (action.equals("rechazar"))
			return this.rechazar(samClient, mapping, request, response);
		else if (action.equals("observar"))
			return this.observar(samClient, mapping, request, response);
		
		try {
			RendicionForm renForm = (RendicionForm) form;
			String idRendicion = request.getParameter("codigo");
			String glg = request.getParameter("glg");
			
			RendicionesService service = new RendicionesService(samClient);
			UsuarioService usuarioService = new UsuarioService(samClient);
			StringBuilder messageBuilder = new StringBuilder();
			
			String idUsuarioRendicion = request.getParameter("usuarioRend");
			if (idUsuarioRendicion == null) {
				AprobacionesService aprobacionesService = new AprobacionesService(samClient);
	
				List<Rendicion> aprobaciones = aprobacionesService.getAprobacionesPendientes(idRendicion, "", "", request.getParameter("glg"),
						this.getSessionUserWorking().getIdUser());
				if (aprobaciones.size() == 0) {
					request.setAttribute("Rendicion", new Rendicion());
					request.getSession().setAttribute("lastErrorMessage", "ERROR: APROBACION INEXISTENTE");
					return mapping.findForward("success");
				}
				
				idUsuarioRendicion = aprobaciones.get(0).getUsuarioRendicion();
				if (aprobacionesService.getMsg() != null)
					messageBuilder.append(aprobacionesService.getMsg()).append("<br>");
			}
			
			List<Rendicion> rendiciones = service.obtenerListadoRendiciones(idUsuarioRendicion, idRendicion, "", "", "");
			if (rendiciones.size() == 0) {
				request.setAttribute("Rendicion", new Rendicion());
				request.getSession().setAttribute("lastErrorMessage", "ERROR: RENDICION INEXISTENTE");
				return mapping.findForward("success");
			}
			
			Rendicion rendicion = rendiciones.get(0);
			if (service.getMsg() != null)
				messageBuilder.append(service.getMsg()).append("<br>");
			

			Usuario usuarioRendicion = usuarioService.obtenerDelegadosUsuario(idUsuarioRendicion);
			if (usuarioService.getMsg() != null)
				messageBuilder.append(service.getMsg());
	
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dateD = df.format(rendicion.getFechaDesde());
			String dateH = df.format(rendicion.getFechaHasta());
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
			renForm.setGlg(glg);
			renForm.setCodMotivo(rendicion.getCodMotivo());
			renForm.setMotivoRechazo(rendicion.getMotivoRechazo());
			if (renForm.getMotivoRechazo() != null && !renForm.getMotivoRechazo().equalsIgnoreCase("")) {
				request.setAttribute("motivoRechazo", "Si");
			} else {
				request.setAttribute("motivoRechazo", "No");
			}
			
			renForm.setLinkThuban((String) request.getSession().getServletContext().getAttribute("rendicion.link.thuban") + idRendicion);
			
//			log.info(message + " mensaje recibido");
//			if (!message.equals(""))
//				request.setAttribute("message", message);
		} catch (Exception e) {
			log.error("", e);
			this.setErrorMessage(e, request);
		}

		return mapping.findForward("success");
	}
	
	private ActionForward getRendicionGastos(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request) throws Exception {
		RendicionesService service = new RendicionesService(samClient);
		List<Gastos> gastos = service.getGastos(request.getParameter("idRendicion"), "", request.getParameter("usuarioRend"), request.getParameter("codMotivo"));
		String serviceMessage = service.getMsg();
		if (serviceMessage != null) {
			request.getSession().setAttribute("lastErrorMessage", serviceMessage);
		}
		request.setAttribute("gastos", gastos);
		request.setAttribute("showOpciones", true);
		request.setAttribute("showEditar", true);
		request.setAttribute("showBorrar", false);
		request.setAttribute("readOnlyDatosAdicionales", true);
		request.setAttribute("readOnlyCupones", true);
		
		return mapping.findForward("gastos");
	}
	
	private ActionForward aprobar(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, Object> resp = new HashMap<String, Object>();

			AprobacionesService service = new AprobacionesService(samClient);
			String serviceMessage = service.cambiarEstadoDeUnaRendicion(this.getSessionUserWorking().getIdUser(), request.getParameter("idRendicion"), "APROB", 
					request.getParameter("comentario"), request.getParameter("glg"));
	
			if (serviceMessage != null && serviceMessage.equalsIgnoreCase("OPERACION EFECTUADA"))
				serviceMessage = "OK: APROBO CORRECTAMENTE LA RENDICION";
			
			resp.put("message", serviceMessage);

			return writeJson(response, resp);
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}
	
	private ActionForward rechazar(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, Object> resp = new HashMap<String, Object>();

			AprobacionesService service = new AprobacionesService(samClient);
			String serviceMessage = service.cambiarEstadoDeUnaRendicion(this.getSessionUserWorking().getIdUser(), request.getParameter("idRendicion"), "RECHA", 
					request.getParameter("motivo") + " - " + request.getParameter("descripcion"), request.getParameter("glg"));
			if (serviceMessage != null && serviceMessage.equalsIgnoreCase("OPERACION EFECTUADA"))
				serviceMessage = "OK: RECHAZO CORRECTAMENTE LA RENDICION";
			
			resp.put("message", serviceMessage);

			return writeJson(response, resp);
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}
	
	private ActionForward observar(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Map<String, Object> resp = new HashMap<String, Object>();

			AprobacionesService service = new AprobacionesService(samClient);
			String serviceMessage = service.cambiarEstadoDeUnaRendicion(this.getSessionUserWorking().getIdUser(), request.getParameter("idRendicion"), "OBSER", 
					request.getParameter("motivo") + " - " + request.getParameter("descripcion"), request.getParameter("glg"));
			if (serviceMessage != null && serviceMessage.equalsIgnoreCase("OPERACION EFECTUADA"))
				serviceMessage = "OK: OBSERVACION DADA DE ALTA CORRECTAMENTE";
			
			resp.put("message", serviceMessage);

			return writeJson(response, resp);
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}
}
