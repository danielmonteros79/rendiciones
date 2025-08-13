package com.sa.action.parametros;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosAlertasFiltroForm;
import com.sa.services.ParametrosService;

public class ParametrosAlertasLoadAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosAlertasLoadAction.class);
	private static final Map<String, String> mapGastoMotivo = Collections.emptyMap();
	private static final Map<String, List<ComboOpcion>> mapMotivoGastos = Collections.emptyMap();
	private static final List<ComboOpcion> cmbGasto = Collections.emptyList();
	private static final List<ComboOpcion> cmbMotivo = Collections.emptyList();
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosAlertasFiltroForm frm = (ParametrosAlertasFiltroForm) form;
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosAlertaLoadAction. Usuario ("+user.getIdUser()+")");
		frm.setCodGasto("");
		frm.setCodMotivo((String)request.getSession().getAttribute("cod_motivo"));
		String accion = request.getParameter("accion");
		if ("selectMotivo".equals(accion)) {
			this.selectMotivo(response.getWriter(), request);
			//response.setHeader("Content-Type", "text/html; charset=UTF-8");
			response.setContentType("application/json");
			response.getWriter().flush();
			response.getWriter().close();
			return null;
		} else if ("selectGasto".equals(accion)) {
			this.selectGasto(response.getWriter(), request);
			//response.setHeader("Content-Type", "text/html; charset=UTF-8");
			response.setContentType("application/json");
			response.getWriter().flush();
			response.getWriter().close();
			return null;
		}
		else if ("filtrar".equals(accion)) {
			return this.filtrar(mapping, samClient, request, response, frm);
		}
		return mapping.findForward("success");	
	}
	
	private ActionForward filtrar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response, ParametrosAlertasFiltroForm frm) throws Exception {
		String message = "";
		ParametrosService service = new ParametrosService (samClient);
		List<ParametroAlerta> alerta = new ArrayList<ParametroAlerta>();
		int paginado = 0;
		List<ParametroAlerta> alertasTotales = new ArrayList<ParametroAlerta>();
		boolean pagina = true;
		try {
			while(pagina){
			alerta = service.getAlertas("LIST", frm.getCodMotivo(), frm.getCodGasto(), "0" + paginado);
			for (ParametroAlerta parametroAlerta : alerta) {
				if(alerta.get(alerta.size() - 1) == parametroAlerta){
					if (parametroAlerta.getLastElement().equalsIgnoreCase("N")){
						paginado++;
						alertasTotales.add(parametroAlerta);
					}
					else{
						pagina = false;
					}
				}
				else {
					alertasTotales.add(parametroAlerta);
				}
			}
			}
			if (service.getMsgAviso() != null)
				message = service.getMsgAviso() + "<br>";
			
			//List<String> combos = service.getAlertaCombos();
			if (service.getMsgAviso() != null)
				message += service.getMsgAviso();
			
//			mapGastoMotivo = new HashMap<String, String>();
//			mapMotivoGastos = new HashMap<String, List<ComboOpcion>>();
//			cmbGasto = new ArrayList<ComboOpcion>();
//			cmbMotivo = new ArrayList<ComboOpcion>();
//			
//			for (String fila : combos) {
//				String combo = fila.substring(0, 2);
//				String codMotivo = fila.substring(2, 6);
//				if (combo.equals("MO"))
//					cmbMotivo.add(new ComboOpcion(codMotivo, codMotivo + " - " + fila.substring(7).trim()));
//				
//				if (combo.equals("GA")) {
//					String codGasto = fila.substring(6, 10);
//					ComboOpcion opcionGasto = new ComboOpcion(codGasto, codGasto + " - " + fila.substring(10).trim());
//					cmbGasto.add(opcionGasto);
//					mapGastoMotivo.put(codGasto, codMotivo);
//					
//					if (mapMotivoGastos.get(codMotivo) == null) {
//						List<ComboOpcion> gastos = new ArrayList<ComboOpcion>();
//						gastos.add(opcionGasto);
//						mapMotivoGastos.put(codMotivo, gastos);
//					} else
//						mapMotivoGastos.get(codMotivo).add(opcionGasto);
//				}
//			}

//			frm.setMapGastoMotivo(mapGastoMotivo);
//			frm.setMapMotivoGastos(mapMotivoGastos);
//			frm.setCmbGasto(cmbGasto);
//			frm.setCmbMotivo(cmbMotivo);
		} catch (Exception e) {
			message = "ERROR: " + e.getCause().getMessage();
		}
		request.setAttribute("alerta", alertasTotales);
//		request.setAttribute("cmbMotivo", cmbMotivo);
//		request.setAttribute("cmbGasto", cmbGasto);
		if (!message.equals("")){
			request.setAttribute("message", message);
		}
		return mapping.findForward("parametrosAlertaFiltro");
	}

	@SuppressWarnings("unchecked")
	private void selectMotivo(PrintWriter writer, HttpServletRequest request) {
		JSONArray jArray = new JSONArray();
		String codMotivo = request.getParameter("codMotivo");
		if (codMotivo == null || codMotivo.trim().equals("")) {
			for (ComboOpcion opcion : cmbGasto) {
				JSONObject jGroup = new JSONObject();
				jGroup.put("codigo", opcion.getId());
				jGroup.put("descripcion", opcion.getDescripcion());

				jArray.add(jGroup);
			}
		} else {
			if (mapMotivoGastos.get(request.getParameter("codMotivo")) != null)
				for (ComboOpcion opcion : mapMotivoGastos.get(request.getParameter("codMotivo"))) {
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