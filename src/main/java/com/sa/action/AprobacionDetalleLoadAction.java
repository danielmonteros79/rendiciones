package com.sa.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.ComboMotivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.RendicionesService;
import com.sa.services.UsuarioService;

public class AprobacionDetalleLoadAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(AprobacionDetalleLoadAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RendicionForm renForm = (RendicionForm) form;
		renForm.reset(mapping, request);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		log.info("Entra al action AprobacionDetalleLoadAction. Usuario (" + user.getIdUser() + ")");

		UsuarioService usuarioService = new UsuarioService(samClient);
		RendicionesService service = new RendicionesService(samClient);
		String usuarioRend = (String) request.getParameter("usuarioRendicion");

		if (request.getParameter("hide") != null) {
			request.setAttribute("displayButtons", 1);
		} else {
			request.setAttribute("displayButtons", 2);
		}
		Integer idRendicion = null;
		// Get codigo current row
		idRendicion = Integer.valueOf(request.getParameter("codigo"));
		log.info("Se realiza la consulta a la trx que traera el detalle de la rendicion " + idRendicion);

		Rendicion rendicion = null;
		try {
			List<Rendicion> rendiciones = service.obtenerListadoRendiciones(usuarioRend, idRendicion.toString(), "", "", "");
			if (rendiciones.size() == 0) {
				request.setAttribute("message", "ERROR: RENDICION INEXISTENTE");
				request.setAttribute("error", 1);
			} else {
				String message = "";
				rendicion = rendiciones.get(0);
				if (service.getMsg() != null)
					message += service.getMsg() + "<br>";
				
				log.info("Se llama al service para obtener los gastos de la rendicion que se selecciono anteriormente");
				List<Gastos> gastos = service.getGastos(idRendicion.toString(), "", user.getIdUser(), rendicion.getCodMotivo());
				if (service.getMsg() != null)
					message += service.getMsg() + "<br>";
				
				rendicion.setGastosRendicion(gastos);
				
				Usuario usuarioRendicion = usuarioService.obtenerDelegadosUsuario((String) request.getParameter("usuarioRendicion"));
				if (usuarioService.getMsg() != null)
					message += service.getMsg();
		
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String dateD = df.format(rendicion.getFechaDesde());
				String dateH = df.format(rendicion.getFechaHasta());
				request.setAttribute("Gastos", rendicion.getGastosRendicion());
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
				renForm.setUsuarioRend((String) request.getParameter("usuarioRendicion"));
				renForm.setGlg(request.getParameter("glg"));
				renForm.setCodMotivo(rendicion.getCodMotivo());
				renForm.setMotivoRechazo(rendicion.getMotivoRechazo());
				if (renForm.getMotivoRechazo() != null && !renForm.getMotivoRechazo().equalsIgnoreCase("")) {
					request.setAttribute("motivoRechazo", "Si");
				} else {
					request.setAttribute("motivoRechazo", "No");
				}
				List<ComboMotivo> motivo = service.getMotivoRendiciones("5", u.getIdUser());
				request.setAttribute("ComboMotivo", motivo);
		
				renForm.setLinkThuban((String) request.getSession().getServletContext().getAttribute("rendicion.link.thuban") + idRendicion);
				
				if (!message.equals(""))
					request.setAttribute("message", message);
			}
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
			request.setAttribute("error", 1);
		}

		return mapping.findForward("success");
	}
}
