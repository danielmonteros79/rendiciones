package com.sa.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.sa.entities.ComboDelegado;
import com.sa.entities.ComboGasto;
import com.sa.entities.ComboMotivo;
import com.sa.entities.ComboOpcion;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.Usuario;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import com.sa.services.ResumenService;
import com.sa.util.ParamsConstants;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class CombosAction extends RestriccionTransaccionAction {
	
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
					
		
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");
			if (action.equals("getTiposGasto"))
				this.getTiposGasto(samClient, response, request);
			else if (action.equals("getMonedas"))
				this.getMonedas(samClient, response, request);
			else if (action.equals("getTiposComprobante"))
				this.getTiposComprobante(samClient, response, request);
			else if (action.equals("getMotivos"))
				this.getMotivos(samClient, response, request);
			else if (action.equals("getDelegados"))
				this.getDelegados(samClient, response, request);
			else if (action.equals("getFechasResumenes"))
				this.getFechasResumenes(samClient, response, request);
		
			
		} catch (Exception e) {
			log.error("", e);
			writeError(response, e);
		}
		
		return null;
	}

	private void getTiposGasto(SAMWebClient samClient, HttpServletResponse response, HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		request.setCharacterEncoding("UTF-8");
		List<String> jsonCombo = new ArrayList<String>();
		PagosService service = new PagosService(samClient);

		List<ComboGasto> tiposGasto = service.getComboGasto(ParamsConstants.TIPO_GASTO_OPCION, sessionUserWorking.getIdUser(), request.getParameter("codMotivo"));
		for (ComboGasto tipoGasto : tiposGasto) {
			jsonCombo.add(gson.toJson(tipoGasto));
		}
		
		Map<String, Object> resp = new HashMap<String, Object>();

		
		resp.put("combo", jsonCombo);
		//response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		writeJson(response, resp);
	}
	
	private void getMonedas(SAMWebClient samClient, HttpServletResponse response, HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		Usuario user = ((Usuario) request.getSession().getAttribute("userWorking"));
		List<String> jsonCombo = new ArrayList<String>();
		RendicionesService service = new RendicionesService(samClient);
		
		List<ComboOpcion2> monedas = service.getComboOpcion2(
				ParamsConstants.MONEDA_OPCION,
				ParamsConstants.MONEDA_TABLA + ParamsConstants.MONEDA_SUBTABLA + ParamsConstants.MONEDA_CODIGO,
				ParamsConstants.MONEDA_CANTIDAD,
				user.getIdUser());
		
		for (ComboOpcion2 moneda : monedas) {
			jsonCombo.add(gson.toJson(moneda));
		}
		
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("combo", jsonCombo);
		
		writeJson(response, resp);
	}
	
	private void getTiposComprobante(SAMWebClient samClient, HttpServletResponse response, HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		Usuario user = ((Usuario) request.getSession().getAttribute("userWorking"));
		List<String> jsonCombo = new ArrayList<String>();
		RendicionesService service = new RendicionesService(samClient);
		
		List<ComboOpcion2> monedas = service.getComboOpcion2(
				ParamsConstants.COMPROBANTE_OPCION,
				ParamsConstants.COMPROBANTE_TABLA + ParamsConstants.COMPROBANTE_SUBTABLA + ParamsConstants.COMPROBANTE_CODIGO,
				ParamsConstants.COMPROBANTE_CANTIDAD,
				user.getIdUser(),
				request.getParameter("tipoGasto"));
		
		for (ComboOpcion2 moneda : monedas) {
			jsonCombo.add(gson.toJson(moneda));
		}
		
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("combo", jsonCombo);
		
		writeJson(response, resp);
	}
	
	private void getMotivos(SAMWebClient samClient, HttpServletResponse response, HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		List<String> jsonCombo = new ArrayList<String>();
		RendicionesService service = new RendicionesService(samClient);
		List<ComboMotivo> motivos = service.getMotivoRendiciones(request.getParameter("opcion"), this.sessionUserWorking.getIdUser());
		
		for (ComboMotivo motivo : motivos) {
			jsonCombo.add(gson.toJson(motivo));
		}
		
		Map<String, Object> resp = new HashMap<String, Object>();
		
		resp.put("combo", jsonCombo);


		
		//response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		writeJson(response, resp);
	}
	
	private void getDelegados(SAMWebClient samClient, HttpServletResponse response, HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		List<String> jsonCombo = new ArrayList<String>();
		
		for (Usuario u : this.sessionUser.getDelegadosAsignados()) {
			if (u.getIdUser().equalsIgnoreCase(this.sessionUser.getIdUser()))
				jsonCombo.add(gson.toJson(new ComboDelegado(this.sessionUser.getIdUser(), "Yo mismo")));
			else
				jsonCombo.add(gson.toJson(new ComboDelegado(u.getIdUser(), u.getIdUser() + " - " + u.getNombre())));
		}
		
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("combo", jsonCombo);
		resp.put("selected", this.sessionUserWorking.getIdUser());
		
		writeJson(response, resp);
	}
	
	private void getFechasResumenes(SAMWebClient samClient, HttpServletResponse response, HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		List<String> jsonCombo = new ArrayList<String>();
		ResumenService service = new ResumenService(samClient);
		
		List<ComboOpcion> fechas = (List<ComboOpcion>) service.getFechasResumenes(this.sessionUserWorking.getIdUser());
		
		for (ComboOpcion fecha : fechas) {
			jsonCombo.add(gson.toJson(fecha));
		}
		
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("combo", jsonCombo);
		
		writeJson(response, resp);
	}
}