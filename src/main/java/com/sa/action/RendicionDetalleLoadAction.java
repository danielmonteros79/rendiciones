package com.sa.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.ComboMotivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.RendicionesService;
import com.sa.services.UsuarioService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class RendicionDetalleLoadAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		RendicionForm renForm = (RendicionForm) form;
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action RendicionDetalleLoadAction. Usuario (" + user.getIdUser() + ")");
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		RendicionesService service = new RendicionesService(samClient);
		
//		request.setAttribute("estadoRend", request.getParameter("estadoRend"));
		// Get codigo current row
		Integer idRendicion = null;
		String usuarioRend = u.getIdUser();
		
		// Chequea si viene de Cuadro detallado
		if (request.getParameter("usuario") != null) {
			UsuarioService usuarioService = new UsuarioService(samClient);
			usuarioRend = request.getParameter("usuario").trim().toUpperCase();
			u = usuarioService.obtenerDelegadosUsuario(usuarioRend);
			request.setAttribute("readonly", "true");
		}
		
		request.setAttribute("reload", request.getParameter("reload"));
		if (request.getParameter("codigo") == null)
			idRendicion = (Integer.parseInt((String) request.getAttribute("codigo")));
		else
			idRendicion = Integer.valueOf(request.getParameter("codigo"));

		request.setAttribute("idRendicion", idRendicion);
		if (request.getParameter("usuarioRendicion") != null && !request.getParameter("usuarioRendicion").equals("")) {
			usuarioRend = request.getParameter("usuarioRendicion").toString();
		}
		// Service carga Listado de Rendiciones
		log.info("Se llama al service para obtener los datos de la rendicion seleccionada y luego mapear los gastos");
		
		try {
			List<Rendicion> rendiciones = service.obtenerListadoRendiciones(usuarioRend, idRendicion.toString(), "", "", "");
			if (rendiciones.size() == 0)
				request.setAttribute("message", "ERROR: RENDICION INEXISTENTE");
			else {
				String message = "";
				
				Rendicion rendicion = rendiciones.get(0);
				if (service.getMsg() != null)
					message = service.getMsg() + "<br>";

				// Obtiene costosDestino
				List<ComboMotivo> motivo = service.getMotivoRendiciones("4", usuarioRend);
				for (ComboMotivo fila : motivo) {
					if (fila.getId().equals(rendicion.getCodMotivo()))
						renForm.setCostosDestino(fila.getCostosDestino());
				}
	
				log.info("Se llama al service para obtener los gastos de la rendicion que se selecciono anteriormente");
				List<Gastos> gastos = service.getGastos(idRendicion.toString(), "", usuarioRend, rendicion.getCodMotivo());
				if (service.getMsg() != null)
					message += service.getMsg();
				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String dateD = df.format(rendicion.getFechaDesde());
				String dateH = df.format(rendicion.getFechaHasta());
				rendicion.setGastosRendicion(gastos);
				request.setAttribute("Gastos", rendicion.getGastosRendicion());
				request.setAttribute("Rendicion", rendicion);
				renForm.setUser(usuarioRend);
				renForm.setNombreUsuario(u.getNombre());
				renForm.setCostos(u.getCcostos());
				renForm.setSector(u.getSector());
				renForm.setIdRendicion(rendicion.getId());
				renForm.setCodMotivo(rendicion.getCodMotivo());
				renForm.setMotivo(rendicion.getMotivo());
				renForm.setFechaDesde(dateD);
				renForm.setFechaHasta(dateH);
				renForm.setFechaUltimaModificacion(rendicion.getFechaUltimaModificacion());
				renForm.setAviso(rendicion.getAviso());
	
				if (!renForm.getAviso().equalsIgnoreCase("")) {
					request.setAttribute("avisoRendicion", "si");
					request.setAttribute("avisoMostrar", rendicion.getAviso());
				}
	
				if (!(rendicion.getFechaUltimaModificacion() != null) || !rendicion.getFechaUltimaModificacion().equalsIgnoreCase("")) {
					request.setAttribute("ultimaModif", "si");
				}
				// se calculan dias entre fecha desde y hasta de la rendicion
				Calendar cal1 = new GregorianCalendar();
				Calendar cal2 = new GregorianCalendar();
	
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
				Date date = sdf.parse(dateD);
				cal1.setTime(date);
				date = sdf.parse(dateH);
				cal2.setTime(date);
				renForm.setCantDias(daysBetween(cal1.getTime(), cal2.getTime()) + 1);
				renForm.setMotivoRechazo(rendicion.getMotivoRechazo());
				if (renForm.getMotivoRechazo() != null && !renForm.getMotivoRechazo().equalsIgnoreCase("")) {
					if(rendicion.getEstado().equalsIgnoreCase("APROB"))
						request.setAttribute("motivoRechAprob", "APROB");
					else
						request.setAttribute("motivoRechAprob", "RECHA");
						
				} else {
					request.setAttribute("motivoRechAprob", "No");
				}
				renForm.setDescripcion(rendicion.getDescripcion());
				renForm.setEstadoRend(rendicion.getEstado());
				renForm.setDescripcionEstado(rendicion.getDescripcionEstado());
				renForm.setUsuarioAprobador(rendicion.getUsuarioAprobador());
	
				if (renForm.getUsuarioAprobador() != null && !renForm.getUsuarioAprobador().equalsIgnoreCase("")) {
					request.setAttribute("usuarioAprobador", "SI");
				}
	
				if (renForm.getEstadoRend() == null) {
					renForm.setEstadoRend(request.getParameter("estadoRendicion"));
				}
	
				request.setAttribute("estadoRend", rendicion.getEstado());
				
				if (rendicion.getEstado() != null && !gastos.isEmpty()) { // Siempre puede adjuntar imagenes sin importar el estado de la rend
					request.setAttribute("showAviso", "true");
				} else {
					if (!rendicion.getIdu().equals("") && !rendicion.getAdea().equalsIgnoreCase("")) {
						request.setAttribute("showCaratula", "true");
					}
				}
				if (idRendicion != null)
					renForm.setLinkThuban((String) request.getSession().getServletContext().getAttribute("rendicion.link.thuban") + idRendicion);
				
				if (!message.equals(""))
					request.setAttribute("message", message);
			}
		} catch (Exception e) {
			request.setAttribute("message", e.getCause().getMessage());
		}
		
		return mapping.findForward("mostrarDetalleGastos");
	}

	public int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
}