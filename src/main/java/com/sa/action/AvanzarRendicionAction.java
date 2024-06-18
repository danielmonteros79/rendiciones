package com.sa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.form.RendicionDetalleForm;
import com.sa.services.AprobacionesService;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import com.sa.services.trxs.WM95;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class AvanzarRendicionAction extends RestriccionTransaccionAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> resp = new HashMap<String, Object>();

		String message = "";
		String idRendicion = request.getParameter("idRendicion");
		String action = request.getParameter("action");
		
		if (action.equals("validarRend"))
			return this.validarRend(response, samClient, idRendicion);

		String glg = request.getParameter("glg");
		boolean esAprobacion = request.getParameter("esAprobacion").equals("true");

		RendicionesService rendicionesService = new RendicionesService(samClient);
		AprobacionesService aprobacionesService = new AprobacionesService(samClient);

		Rendicion rendicion = null;
		if (esAprobacion)
			rendicion = aprobacionesService
					.getAprobacionesPendientes(idRendicion, null, null, glg, this.sessionUserWorking.getIdUser())
					.get(0);
		else
			rendicion = rendicionesService
					.obtenerListadoRendiciones(this.sessionUserWorking.getIdUser(), idRendicion, null, null, null)
					.get(0);

		rendicion.setUsuarioRendicion(this.sessionUserWorking.getIdUser());
		rendicion.setId(Integer.parseInt(idRendicion));
		rendicion.setCostosDestino(String.valueOf(this.sessionUserWorking.getCcostos()));


		List<Gastos> gastos = rendicionesService.getGastos(idRendicion, "", rendicion.getUsuarioRendicion() != null
				? rendicion.getUsuarioRendicion() : this.sessionUserWorking.getIdUser(), rendicion.getCodMotivo());
		if (gastos.size() == 0)
			return writeError(response, "La rendici&oacute;n no tiene gastos cargados.");

		else if (rendicion.getEstado().equals("PENDI") || rendicion.getEstado().equals("OBSER")) {
			String idu = aprobacionesService.obtenerIDU(rendicion, this.sessionUserWorking.getIdUser(),
					WM95.DELIM_04_SIN_ADEA);
			aprobacionesService.cambiarEscanRendicion(String.valueOf(rendicion.getId()),
					this.sessionUserWorking.getIdUser(), idu);
			if (aprobacionesService.getMsg() != null)
				message += "<br>" + aprobacionesService.getMsg();
		}

		resp.put("message", message);

		return writeJson(response, resp);
	}
	
	private ActionForward validarRend(HttpServletResponse response, SAMWebClient samClient, String idRendicion) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		 
		PagosService pagosService = new PagosService(samClient);
		
		String validacionExc = pagosService.getValidacionRendicion("ALTA", "000000000000" + idRendicion, "000000000");
		//String validacionHardcodeada = "NO OKA";
		
		resp.put("textoValidacion", validacionExc);//MODIFICAR CUANDO SE TERMINE EL SERVICIO

		response.setContentType("text/html; charset=UTF-8");
		return writeJson(response, resp);
	}

}
