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
import com.sa.form.RendicionDetalleForm;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class GastosAction extends RestriccionTransaccionAction {
	
	private static final String UTF8_CHARSET = "text/html; charset=UTF-8";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			RendicionDetalleForm renForm = (RendicionDetalleForm) form;
			String opcion = request.getParameter("opcion");
			
			if (opcion.equals("ALTA") || opcion.equals("MODIF"))
				return altaModif(response, renForm, samClient);
			else if (opcion.equals("BAJA"))
				return baja(response, renForm, samClient);
			else if (opcion.equals("CONS"))
				return consulta(response, renForm, samClient);
			return null;
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}
	
	private ActionForward altaModif(HttpServletResponse response, RendicionDetalleForm renForm, SAMWebClient samClient) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		 
		PagosService pagosService = new PagosService(samClient);
		Integer idGasto = pagosService.altaModifGasto(renForm.getOpcion(), renForm.getIdGasto(), renForm.getIdRendicion(), renForm.getMoneda(),
				renForm.getTipoComprobante(), renForm.getTipoFactura(), renForm.getFactura(), renForm.getCuit(), renForm.getGasto(), renForm.getMonto(),
				renForm.getFechaGasto(), renForm.getCodMotivo(), renForm.getCostosDestino(), renForm.getCupCred(), renForm.getCupDeb(), renForm.getCupon(),
				renForm.getDescCupon(), renForm.getImporteCupon(), renForm.getNroTarjeta(), renForm.getObservacionGasto());
		
		String datosAdicionales = renForm.getGasto().substring(60,61);
		resp.put("message", pagosService.getMsg());
		resp.put("idGasto", idGasto);
		resp.put("showModalDatosAdicionales", datosAdicionales.equalsIgnoreCase("S"));

		response.setContentType(UTF8_CHARSET);
		return writeJson(response, resp);
	}
	
	private ActionForward baja(HttpServletResponse response, RendicionDetalleForm renForm, SAMWebClient samClient) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		PagosService pagosService = new PagosService(samClient);
		pagosService.bajaGasto(renForm.getIdGasto(), this.sessionUserWorking.getIdUser(), renForm.getIdRendicion());
		resp.put("message", pagosService.getMsg());

		response.setContentType(UTF8_CHARSET);
		return writeJson(response, resp);
	}
	
	private ActionForward consulta(HttpServletResponse response, RendicionDetalleForm renForm, SAMWebClient samClient) throws Exception {
		Map<String, Object> resp = new HashMap<String, Object>();
		RendicionesService serviceCombos = new RendicionesService(samClient);
		List<Gastos> gastos = serviceCombos.getGastos(renForm.getIdRendicion(), renForm.getIdGasto(), this.sessionUserWorking.getIdUser(), renForm.getCodMotivo());
		if (gastos.isEmpty())
			return writeError(response, "Gasto inexistente");
		resp.put("gasto", gastos.get(0));
		
		response.setContentType(UTF8_CHARSET);
		
		return writeJson(response, resp);
	}

}

