package com.sa.action.redistribucion;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class DistribucionGastosLoadAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RendicionForm renForm = (RendicionForm) form;
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action DistribucionGastosLoadAction. Usuario ("
				+ user.getIdUser() + ")");
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		RendicionesService service = new RendicionesService(samClient);
		request.getSession().removeAttribute("rendicionSelectDerrame");

		Integer idRendicion = null;
		String usuarioRend = u.getIdUser();
		

		idRendicion = Integer.valueOf(request.getParameter("idRendicion"));

		usuarioRend = request.getParameter("usuarioRendicion").toString();

		log.info("Se llama al service para obtener los datos de la rendicion seleccionada y luego mapear los gastos");
		List<Rendicion> rendiciones = service.obtenerListadoRendiciones(
				usuarioRend, idRendicion.toString(), "", "", "");
		PagosService serv = new PagosService(samClient);
		Rendicion rendicion = null;

		for (Rendicion r : rendiciones) {
			Integer idR = r.getId();
			if (idR.compareTo(idRendicion) == 0) {
				rendicion = r;
				break;
			}

		}

		// Obtiene costosDestino
		List<ComboMotivo> motivo = service.getMotivoRendiciones("4",
				u.getIdUser());
		String desMotivo = "";
		for (ComboMotivo fila : motivo) {
			if (fila.getId().equals(rendicion.getCodMotivo())) {
				desMotivo = fila.getDescripcion();
				break;
			}

		}

		log.info("Se llama al service para obtener los gastos de la rendicion que se selecciono anteriormente");
		List<Gastos> gastos = service.getGastos(idRendicion.toString(), "",
				u.getIdUser(), rendicion.getCodMotivo());


		request.setAttribute("Gastos", gastos);
		request.setAttribute("idRendicion", rendicion.getId());
		request.setAttribute("descripcionMotivo", desMotivo);
		request.setAttribute("codMotivo", rendicion.getCodMotivo());
		request.setAttribute("usuarioRendicion", usuarioRend);
		
		request.getSession().setAttribute("rendicionSelectDerrame", rendicion);
		
		return mapping.findForward("success");

	}

	public int daysBetween(Date d1, Date d2) {

		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

}