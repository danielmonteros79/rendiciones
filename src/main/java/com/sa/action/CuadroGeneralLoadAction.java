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
		RendicionesService service = new RendicionesService (samClient);
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action CuadroGeneralLoadAction. Usuario ("+u.getIdUser()+")");
		
		List<ComboMotivo> motivo = new ArrayList<ComboMotivo>();
		String message = "";
		
		CuadroFiltroForm frm = (CuadroFiltroForm) form;
		frm.clear();

		try {
			String accion = request.getParameter("accion");
			if ("selectGlg".equals(accion)) {
				this.selectGlg(response.getWriter(), request, samClient);
				//response.setHeader("Content-Type", "text/html; charset=UTF-8");
				response.setContentType("application/json");
				response.getWriter().flush();
				response.getWriter().close();
				return null;
			}
			motivo = service.getMotivoRendiciones("9", u.getIdUser(), "");
			if (service.getMsg() != null)
				message += service.getMsg() + "<br>";
			
			Map<String, List<ComboMotivo>> mapGlgMotivos = new HashMap<String, List<ComboMotivo>>();
			List<ComboMotivo> cmbMotivo = new ArrayList<ComboMotivo>();
			
			List<String> motivos = new ArrayList<String>(motivo.size());
			for (ComboMotivo motivoItem : motivo) {
				String mot = "3" + motivoItem.getDescripcion();
				motivos.add(mot != null ? mot.toString() : null);
			}
			
			for (String fila : motivos) {
				String codGlg = fila.substring(0, 1);
				String codMotivo = fila.substring(1, 5);

				if (codGlg.equals("2")) {
					ComboMotivo opcionMotivo = new ComboMotivo(codMotivo, codMotivo + " - " + fila.substring(6).trim());
					cmbMotivo.add(opcionMotivo);
					
					if (mapGlgMotivos.get(codGlg) == null) {
						List<ComboMotivo> motivosList = new ArrayList<ComboMotivo>();
						motivosList.add(opcionMotivo);
						mapGlgMotivos.put(codGlg, motivosList);
					} else {
						mapGlgMotivos.get(codGlg).add(opcionMotivo);}
				}
			}
			
			frm.setNombreUsuario(u.getNombre());
			frm.setCostos(u.getCcostos());
			frm.setComboMotivo(motivo);
			
			frm.setComboGlg(service.getGlgsUsuario(user.getIdUser(), user.getFacultades()));
			if (service.getMsg() != null)
				message += service.getMsg();

			if (!message.equals(""))
				request.setAttribute("message", message);
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}

		request.setAttribute("ComboMotivo", motivo);
		request.setAttribute("ComboGlg", frm.getComboGlg());
		request.setAttribute("Tabla", "f");
		
		return mapping.findForward("success");
	}
	
	@SuppressWarnings("unchecked")
	private void selectGlg(PrintWriter writer, HttpServletRequest request, SAMWebClient samClient) {
		try {
			RendicionesService service = new RendicionesService(samClient);
			Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
			
			List<ComboMotivo> motivo = service.getMotivoRendiciones("9", u.getIdUser(), "");
			
			Map<String, List<ComboMotivo>> mapGlgMotivos = new HashMap<String, List<ComboMotivo>>();
			List<ComboMotivo> cmbMotivo = new ArrayList<ComboMotivo>();
			
			List<String> motivos = new ArrayList<String>(motivo.size());
			for (ComboMotivo cmbMotivoItem : motivo) {
				String mot = "3" + cmbMotivoItem.getDescripcion();
				motivos.add(mot != null ? mot.toString() : null);
			}
			
			for (String fila : motivos) {
				String codGlg = fila.substring(0, 1);
				String codMotivo = fila.substring(1, 5);

				if (codGlg.equals("2")) {
					ComboMotivo opcionMotivo = new ComboMotivo(codMotivo, codMotivo + " - " + fila.substring(6).trim());
					cmbMotivo.add(opcionMotivo);
					
					if (mapGlgMotivos.get(codGlg) == null) {
						List<ComboMotivo> motivosList = new ArrayList<ComboMotivo>();
						motivosList.add(opcionMotivo);
						mapGlgMotivos.put(codGlg, motivosList);
					} else {
						mapGlgMotivos.get(codGlg).add(opcionMotivo);
					}
				}
			}
			
			JSONArray jArray = new JSONArray();
			String codGlg = request.getParameter(COD_GLG);
			if (codGlg == null || codGlg.trim().equals("")) {
				for (ComboMotivo motivoItem : cmbMotivo) {
					JSONObject jGroup = new JSONObject();
					jGroup.put("codigo", motivoItem.getId());
					jGroup.put("descripcion", motivoItem.getDescripcion());

					jArray.add(jGroup);
				}
			} else {
				if (mapGlgMotivos.get(request.getParameter(COD_GLG)) != null)
					for (ComboMotivo motivoItem : mapGlgMotivos.get(request.getParameter(COD_GLG))) {
						JSONObject jGroup = new JSONObject();
						jGroup.put("codigo", motivoItem.getId());
						jGroup.put("descripcion", motivoItem.getDescripcion());
		
						jArray.add(jGroup);
					}
			}
			writer.print(jArray);
		} catch (Exception e) {
			writer.print("[]");
			log.error("Error in selectGlg: " + e.getMessage(), e);
		}
	}
}