package com.sa.action.rendiciones;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.RendicionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

public class RendicionSaveAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action RendicionSaveAction. Usuario (" + user.getIdUser() + ")");
		RendicionForm renForm = (RendicionForm) form;
		renForm.reset(mapping, request);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		RendicionesService service = new RendicionesService(samClient);
		String action = request.getParameter("action") == null ? "" : request.getParameter("action");
		
		if (action.equals("formatear")) {
			return this.determinarPreFormato(samClient, request, response);
		}
		
		Date dateD = null;
		Date dateH = null;

		if (renForm.getFechaDesde() != null && !renForm.getFechaDesde().equalsIgnoreCase("")) {
			dateD = formatter.parse(renForm.getFechaDesde());
		}
		if (renForm.getFechaHasta() != null && !renForm.getFechaHasta().equalsIgnoreCase("")) {
			dateH = formatter.parse(renForm.getFechaHasta());
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
		String feD = "";
		String feH = "";
		if (dateD != null) {
			feD = df.format(dateD).trim();
		}
		if (dateH != null) {
			feH = df.format(dateH).trim();

		}
		log.info("Se llama al service que realiza el alta de la rendicion");

		try {
			
			String idRend = service.altaRendicion(renForm.getUser(), renForm.getMotivo(), feD, feH, renForm.getDescripcion(), false);
			request.setAttribute("codigo", idRend);
			if (idRend.equalsIgnoreCase("") || idRend.equalsIgnoreCase(null)) {
				request.setAttribute("validarTrx", 1);
				return mapping.findForward("failure");
			}
			
			request.setAttribute("codigo", idRend);
			request.setAttribute("tipoSubmit", 2);
			request.setAttribute("message", service.getMsg());

			
			return mapping.findForward("detalleGastos");
			
		} catch (TransactionException e) {
			List<ComboMotivo> motivo = service.getMotivoRendiciones("4", user.getIdUser(), "");
			request.setAttribute("ComboMotivo", motivo);
			
			log.error(e);
			request.setAttribute("messageModifTCJP", e.getCause().getMessage());
			return mapping.findForward("failure");
		}
	}

	public ActionForward rendicionDetalleGastos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		return mapping.findForward("rendicionDetalleGastos");

	}
	
	public ActionForward determinarPreFormato(SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		RendicionesService service = new RendicionesService(samClient);
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		String codMotivo = request.getParameter("codigoMotivo");
		List<ComboMotivo> motivos = service.getMotivoRendiciones("4", user.getIdUser(), "");
		ComboMotivo motivoEnviar = buscarMotivo(codMotivo,motivos );
		
		resp.put("motivoActual", motivoEnviar.getPreFormato());
		resp.put("diasExtras", motivoEnviar.getCantDias());
		resp.put("message", service.getMsg());
		
		return writeJson(response, resp);
	}
	
	private ComboMotivo buscarMotivo(String codMotivo, List<ComboMotivo> motivos) {
		int cont = 0;
		ComboMotivo motivo = null;
		while(cont < motivos.size() || motivo == null) {
			ComboMotivo motivoActual = motivos.get(cont);
			if(motivoActual.getId().equals(codMotivo)) {
				motivo = motivoActual;
			}
			cont++;
		}
		return motivo;
	}
}