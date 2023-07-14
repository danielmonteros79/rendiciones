package com.sa.action.parametros;

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

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.ComboOpcion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroAlerta;
import com.sa.form.parametros.ParametrosAlertasFiltroForm;
import com.sa.services.ParametrosService;

public class ParametrosAlertasLoadAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosAlertasLoadAction.class);
	private Map<String, String> mapGastoMotivo = new HashMap<String, String>();
	private Map<String, List<ComboOpcion>> mapMotivoGastos = new HashMap<String, List<ComboOpcion>>();
	private List<ComboOpcion> cmbGasto = new ArrayList<>();
	private List<ComboOpcion> cmbMotivo = new ArrayList<>();
	private static final String COD_MOTIVO = "codMotivo";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosAlertasFiltroForm frm = (ParametrosAlertasFiltroForm) form;
		ParametrosService service = new ParametrosService (samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosAlertaLoadAction. Usuario ("+user.getIdUser()+")");
		frm.setCodGasto("");
		frm.setCodMotivo("");
		String accion = request.getParameter("accion");
		if ("selectMotivo".equals(accion)) {
			this.selectMotivo(response.getWriter(), request);
			response.setContentType("application/json");
			response.getWriter().flush();
			response.getWriter().close();
			return null;
		} else if ("selectGasto".equals(accion)) {
			this.selectGasto(response.getWriter(), request);
			response.setContentType("application/json");
			response.getWriter().flush();
			response.getWriter().close();
			return null;
		}
		
		String message = "";
		List<ParametroAlerta> alerta = new ArrayList<ParametroAlerta>();
		
		try {
			alerta = service.getAlertas("CONS", frm.getCodMotivo(), frm.getCodGasto());
			if (service.getMsgAviso() != null)
				message = service.getMsgAviso() + "<br>";
			
			List<String> combos = service.getAlertaCombos();
			if (service.getMsgAviso() != null)
				message += service.getMsgAviso();
			
			mapGastoMotivo = new HashMap<String, String>();
			mapMotivoGastos = new HashMap<String, List<ComboOpcion>>();
			cmbGasto = new ArrayList<>();
			cmbMotivo = new ArrayList<>();
			
			for (String fila : combos) {
				String combo = fila.substring(0, 2);
				String codMotivo = fila.substring(2, 6);
				if (combo.equals("MO"))
					cmbMotivo.add(new ComboOpcion(codMotivo, codMotivo + " - " + fila.substring(7).trim()));
				
				if (combo.equals("GA")) {
					String codGasto = fila.substring(6, 10);
					ComboOpcion opcionGasto = new ComboOpcion(codGasto, codGasto + " - " + fila.substring(10).trim());
					cmbGasto.add(opcionGasto);
					mapGastoMotivo.put(codGasto, codMotivo);
					
					if (mapMotivoGastos.get(codMotivo) == null) {
						List<ComboOpcion> gastos = new ArrayList<ComboOpcion>();
						gastos.add(opcionGasto);
						mapMotivoGastos.put(codMotivo, gastos);
					} else {
						mapMotivoGastos.get(codMotivo).add(opcionGasto);}
				}
			}
			
			frm.setMapGastoMotivo(mapGastoMotivo);
			frm.setMapMotivoGastos(mapMotivoGastos);
			frm.setCmbGasto(cmbGasto);
			frm.setCmbMotivo(cmbMotivo);
		} catch (Exception e) {
			message = "ERROR: " + e.getCause().getMessage();
		}
		
		request.setAttribute("alerta", alerta);
		request.setAttribute("cmbMotivo", cmbMotivo);
		request.setAttribute("cmbGasto", cmbGasto);
		
		if (!message.equals(""))
			request.setAttribute("message", message);
		
		return mapping.findForward("success");
	}

	@SuppressWarnings("unchecked")
	private void selectMotivo(PrintWriter writer, HttpServletRequest request) {
		JSONArray jArray = new JSONArray();
		String codMotivo = request.getParameter(COD_MOTIVO);
		if (codMotivo == null || codMotivo.trim().equals("")) {
			for (ComboOpcion opcion : cmbGasto) {
				JSONObject jGroup = new JSONObject();
				jGroup.put("codigo", opcion.getId());
				jGroup.put("descripcion", opcion.getDescripcion());

				jArray.add(jGroup);
			}
		} else {
			if (mapMotivoGastos.get(request.getParameter(COD_MOTIVO)) != null)
				for (ComboOpcion opcion : mapMotivoGastos.get(request.getParameter(COD_MOTIVO))) {
					JSONObject jGroup = new JSONObject();
					jGroup.put("codigo", opcion.getId());
					jGroup.put("descripcion", opcion.getDescripcion());
	
					jArray.add(jGroup);
				}
		}
		
		writer.print(jArray);
	}

	private void selectGasto(PrintWriter writer, HttpServletRequest request) {
		JSONObject jsonObject = null;
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("codGasto", mapGastoMotivo.get(request.getParameter("codGasto")));
		jsonObject = JSONObject.fromObject(resp);
		writer.print(jsonObject);
	}
}