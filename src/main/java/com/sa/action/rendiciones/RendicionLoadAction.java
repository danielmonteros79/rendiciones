package com.sa.action.rendiciones;

import java.io.PrintWriter;
import java.text.Format;
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
import org.json.simple.JSONArray;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.RendicionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import net.sf.json.JSONObject;

public class RendicionLoadAction extends RestriccionTransaccionAction {
	Map<String, String> mapMotivoCostos = new HashMap<String, String>();

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RendicionesService service = new RendicionesService(samClient);
		RendicionForm renForm = (RendicionForm) form;
		renForm.reset();

		// Completa los campos con los datos del Usuario
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action RendicionLoadAction. Usuario (" + user.getIdUser() + ")");
		String accion = request.getParameter("accion");

		if ("checkCDestino".equals(accion)) {
			this.selectMotivo(response.getWriter(), request);
			response.setContentType("application/json");
			response.getWriter().flush();
			response.getWriter().close();
			return null;
		}

		renForm.setUser(u.getIdUser());
		renForm.setNombreUsuario(u.getNombre());
		renForm.setCostos(u.getCcostos());
		renForm.setSector(u.getSector());
		Date fechaHoy = new Date();
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		renForm.setFechaHoy(formatter.format(fechaHoy));

		List<ComboMotivo> motivo = new ArrayList<>();
		
		try {
			motivo = service.getMotivoRendiciones("4", u.getIdUser());
		} catch (Exception e) {
			request.setAttribute("messageModifTCJP", "ERROR: " + e.getCause().getMessage());
		}

		mapMotivoCostos = new HashMap<String, String>();

		for (ComboMotivo fila : motivo) {
			String idMotivo = fila.getId();
			String costosDestino = fila.getCostosDestino();

			mapMotivoCostos.put(idMotivo, costosDestino);
		}

		request.setAttribute("ComboMotivo", motivo);

		String desc = null;
		request.setAttribute("descripcion", desc);

		return mapping.findForward("success");
	}

	@SuppressWarnings("unchecked")
	private void selectMotivo(PrintWriter writer, HttpServletRequest request) {
		JSONArray jArray = new JSONArray();
		if (mapMotivoCostos.get(request.getParameter("codMotivo")) != null) {
			JSONObject jGroup = new JSONObject();
			jGroup.put("costosDestino", mapMotivoCostos.get(request.getParameter("codMotivo")));

			jArray.add(jGroup);
		}
		writer.print(jArray);
	}
}
