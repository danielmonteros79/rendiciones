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
	
	private PagosService pagosService;
	private RendicionesService rendicionesService;
	private AprobacionesService aprobacionesService;
	
    public AvanzarRendicionAction() {
    }

    public AvanzarRendicionAction(PagosService pagosService) {
        this.pagosService = pagosService;
    }
    
    public void setRendicionesService(RendicionesService service) {
    	this.rendicionesService = service;
    }
    
    public void setAprobacionesService(AprobacionesService service) {
    	this.aprobacionesService = service;
    }

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

		rendicionesService = this.rendicionesService != null ? this.rendicionesService : new RendicionesService(samClient);
		aprobacionesService = this.aprobacionesService != null ? this.aprobacionesService : new AprobacionesService(samClient);

		Rendicion rendicion = null;
		if (esAprobacion)
			rendicion = aprobacionesService
					.getAprobacionesPendientes(idRendicion, null, null, glg, this.getSessionUserWorking().getIdUser())
					.get(0);
		else
			rendicion = rendicionesService
					.obtenerListadoRendiciones(this.getSessionUserWorking().getIdUser(), idRendicion, null, null, null)
					.get(0);

		rendicion.setUsuarioRendicion(this.getSessionUserWorking().getIdUser());
		rendicion.setId(Integer.parseInt(idRendicion));
		rendicion.setCostosDestino(String.valueOf(this.getSessionUserWorking().getCcostos()));


		List<Gastos> gastos = rendicionesService.getGastos(idRendicion, "", rendicion.getUsuarioRendicion() != null
				? rendicion.getUsuarioRendicion() : this.getSessionUserWorking().getIdUser(), rendicion.getCodMotivo());
		if (gastos.size() == 0)
			return writeError(response, "La rendici&oacute;n no tiene gastos cargados.");

		else if (rendicion.getEstado().equals("PENDI") || rendicion.getEstado().equals("OBSER")) {
			String idu = aprobacionesService.obtenerIDU(rendicion, this.getSessionUserWorking().getIdUser(),
					WM95.DELIM_04_SIN_ADEA);
			aprobacionesService.cambiarEscanRendicion(String.valueOf(rendicion.getId()),
					this.getSessionUserWorking().getIdUser(), idu);
			if (aprobacionesService.getMsg() != null)
				message += "<br>" + aprobacionesService.getMsg();
		}

		resp.put("message", message);

		return writeJson(response, resp);
	}
	
	private ActionForward validarRend(HttpServletResponse response, SAMWebClient samClient, String idRendicion) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		 
		PagosService pagosService = this.pagosService != null ? this.pagosService : new PagosService(samClient);
		
		String validacionExc = pagosService.getValidacionRendicion("0001", "000000000000" + idRendicion, "000000001");
		//String validacionHardcodeada = "NO OKA";
		
		resp.put("textoValidacion", validacionExc);//MODIFICAR CUANDO SE TERMINE EL SERVICIO

		response.setContentType("text/html; charset=UTF-8");
		return writeJson(response, resp);
	}

}
