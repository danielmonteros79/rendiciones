package com.sa.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
		String tipoClick = request.getParameter("tipoSubmit");
		RendicionesService service = new RendicionesService(samClient);

		// Date dateD = formatter.parse(renForm.getFechaDesde());
		// Date dateH = formatter.parse(renForm.getFechaHasta());
		// ComboMotivo mot = new ComboMotivo();
		//
		// for (ComboMotivo m : mot.getMotivoRendiciones()) {
		// if (m.getId().equals(renForm.getMotivo())) {
		// renForm.setMotivo(m.getDescripcion());
		// break;
		// }
		// }
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
			String idRend = service.altaRendicion(renForm.getUser(), renForm.getMotivo(), feD, feH,
					renForm.getDescripcion());
			request.setAttribute("codigo", idRend);
			if (idRend.equalsIgnoreCase("") || idRend.equalsIgnoreCase(null)) {
				request.setAttribute("validarTrx", 1);
				return mapping.findForward("failure");
			}
			if (Integer.valueOf(tipoClick) == 1) {
				request.setAttribute("tipoSubmit", 1);
				return mapping.findForward("success");
			} else {
				request.setAttribute("codigo", idRend);
				request.setAttribute("tipoSubmit", 2);
				request.setAttribute("message", service.getMsg());

				return mapping.findForward("detalleGastos");
			}
		} catch (TransactionException e) {
			List<ComboMotivo> motivo = service.getMotivoRendiciones("4", user.getIdUser(), "");
			request.setAttribute("ComboMotivo", motivo);
			
			log.error(e);
			request.setAttribute("messageModifTCJP", e.getCause().getMessage());
			return mapping.findForward("failure");
		}
	}

	public ActionForward mostrarDetalleGastos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("mostrarDetalleGastos");

	}
}
