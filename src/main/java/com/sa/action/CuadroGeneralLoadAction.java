package com.sa.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.ComboMotivo;
import com.sa.entities.Usuario;
import com.sa.form.CuadroFiltroForm;
import com.sa.services.RendicionesService;

public class CuadroGeneralLoadAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(CuadroGeneralLoadAction.class);
	private static final String COD_GLG = "codGlg";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		log.info("Entra al action CuadroGeneralLoadAction. Usuario ("+u.getIdUser()+")");
		
		CuadroFiltroForm frm = (CuadroFiltroForm) form;
		frm.clear();

		String accion = request.getParameter("accion");
		if ("selectGlg".equals(accion)) {
			return handleSelectGlgAction(response, request, samClient);
		}

		return handleMainAction(mapping, samClient, request, frm);
	}

	private ActionForward handleSelectGlgAction(HttpServletResponse response, HttpServletRequest request, 
			SAMWebClient samClient) throws Exception {
		this.selectGlg(response.getWriter(), request, samClient);
		response.setContentType("application/json");
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	private ActionForward handleMainAction(ActionMapping mapping, SAMWebClient samClient, 
			HttpServletRequest request, CuadroFiltroForm frm) throws Exception {
		RendicionesService service = new RendicionesService(samClient);
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		
		List<ComboMotivo> motivo = new ArrayList<>();
		String message = "";

		try {
			motivo = service.getMotivoRendiciones("9", u.getIdUser(), "");
			message = buildMessageFromService(service, message);
			
			processMotivos(motivo);
			
			populateForm(frm, u, motivo, service, user);
			message = appendServiceMessage(service, message);

			if (!message.equals("")) {
				request.setAttribute("message", message);
			}
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}

		setRequestAttributes(request, motivo, frm);
		return mapping.findForward("success");
	}

	private String buildMessageFromService(RendicionesService service, String message) {
		if (service.getMsg() != null) {
			message += service.getMsg() + "<br>";
		}
		return message;
	}

	private void processMotivos(List<ComboMotivo> motivo) {
		Map<String, List<ComboMotivo>> mapGlgMotivos = new HashMap<>();
		List<ComboMotivo> cmbMotivo = new ArrayList<>();
		
		List<String> motivos = transformMotivosList(motivo);
		populateGlgMotivos(motivos, mapGlgMotivos, cmbMotivo);
	}

	private List<String> transformMotivosList(List<ComboMotivo> motivo) {
		List<String> motivos = new ArrayList<>(motivo.size());
		for (ComboMotivo motivoItem : motivo) {
			String mot = "3" + motivoItem.getDescripcion();
			motivos.add(mot != null ? mot : null);
		}
		return motivos;
	}

	private void populateGlgMotivos(List<String> motivos, Map<String, List<ComboMotivo>> mapGlgMotivos, 
			List<ComboMotivo> cmbMotivo) {
		for (String fila : motivos) {
			String codGlg = fila.substring(0, 1);
			String codMotivo = fila.substring(1, 5);

			if (codGlg.equals("2")) {
				ComboMotivo opcionMotivo = new ComboMotivo(codMotivo, codMotivo + " - " + fila.substring(6).trim());
				cmbMotivo.add(opcionMotivo);
				addToGlgMotivoMap(mapGlgMotivos, codGlg, opcionMotivo);
			}
		}
	}

	private void addToGlgMotivoMap(Map<String, List<ComboMotivo>> mapGlgMotivos, String codGlg, 
			ComboMotivo opcionMotivo) {
		if (mapGlgMotivos.get(codGlg) == null) {
			List<ComboMotivo> motivosList = new ArrayList<>();
			motivosList.add(opcionMotivo);
			mapGlgMotivos.put(codGlg, motivosList);
		} else {
			mapGlgMotivos.get(codGlg).add(opcionMotivo);
		}
	}

	private void populateForm(CuadroFiltroForm frm, Usuario u, List<ComboMotivo> motivo, 
			RendicionesService service, Usuario user) throws Exception {
		frm.setNombreUsuario(u.getNombre());
		frm.setCostos(u.getCcostos());
		frm.setComboMotivo(motivo);
		frm.setComboGlg(service.getGlgsUsuario(user.getIdUser(), user.getFacultades()));
	}

	private String appendServiceMessage(RendicionesService service, String message) {
		if (service.getMsg() != null) {
			message += service.getMsg();
		}
		return message;
	}

	private void setRequestAttributes(HttpServletRequest request, List<ComboMotivo> motivo, CuadroFiltroForm frm) {
		request.setAttribute("ComboMotivo", motivo);
		request.setAttribute("ComboGlg", frm.getComboGlg());
		request.setAttribute("Tabla", "f");
	}
	
	@SuppressWarnings("unchecked")
	private void selectGlg(PrintWriter writer, HttpServletRequest request, SAMWebClient samClient) {
		try {
			RendicionesService service = new RendicionesService(samClient);
			Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
			
			List<ComboMotivo> motivo = service.getMotivoRendiciones("9", u.getIdUser(), "");
			
			Map<String, List<ComboMotivo>> mapGlgMotivos = new HashMap<>();
			List<ComboMotivo> cmbMotivo = new ArrayList<>();
			
			processMotivosForSelectGlg(motivo, mapGlgMotivos, cmbMotivo);
			
			JSONArray jArray = buildJsonResponse(request, mapGlgMotivos, cmbMotivo);
			writer.print(jArray);
		} catch (Exception e) {
			writer.print("[]");
			log.error("Error in selectGlg: " + e.getMessage(), e);
		}
	}

	private void processMotivosForSelectGlg(List<ComboMotivo> motivo, Map<String, List<ComboMotivo>> mapGlgMotivos, 
			List<ComboMotivo> cmbMotivo) {
		List<String> motivos = transformMotivosList(motivo);
		populateGlgMotivos(motivos, mapGlgMotivos, cmbMotivo);
	}

	private JSONArray buildJsonResponse(HttpServletRequest request, Map<String, List<ComboMotivo>> mapGlgMotivos, 
			List<ComboMotivo> cmbMotivo) {
		JSONArray jArray = new JSONArray();
		String codGlg = request.getParameter(COD_GLG);
		
		if (isEmptyOrNull(codGlg)) {
			addAllMotivosToArray(jArray, cmbMotivo);
		} else {
			addFilteredMotivosToArray(jArray, mapGlgMotivos, request);
		}
		
		return jArray;
	}

	private boolean isEmptyOrNull(String codGlg) {
		return codGlg == null || codGlg.trim().equals("");
	}

	private void addAllMotivosToArray(JSONArray jArray, List<ComboMotivo> cmbMotivo) {
		for (ComboMotivo motivoItem : cmbMotivo) {
			jArray.add(createJsonObject(motivoItem));
		}
	}

	private void addFilteredMotivosToArray(JSONArray jArray, Map<String, List<ComboMotivo>> mapGlgMotivos, 
			HttpServletRequest request) {
		List<ComboMotivo> filteredMotivos = mapGlgMotivos.get(request.getParameter(COD_GLG));
		if (filteredMotivos != null) {
			for (ComboMotivo motivoItem : filteredMotivos) {
				jArray.add(createJsonObject(motivoItem));
			}
		}
	}

	private JSONObject createJsonObject(ComboMotivo motivo) {
		JSONObject jGroup = new JSONObject();
		jGroup.put("codigo", motivo.getId());
		jGroup.put("descripcion", motivo.getDescripcion());
		return jGroup;
	}
}