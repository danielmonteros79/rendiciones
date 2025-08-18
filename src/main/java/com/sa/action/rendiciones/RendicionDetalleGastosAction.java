package com.sa.action.rendiciones;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.Resumen;
import com.sa.form.RendicionForm;
import com.sa.services.AprobacionesService;
import com.sa.services.ParametrosService;
import com.sa.services.RendicionesService;
import com.sa.services.ResumenService;
import com.sa.services.UsuarioService;
import com.sa.util.DateUtil;
import com.sa.util.ParamsConstants;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class RendicionDetalleGastosAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuilder messageBuilder = new StringBuilder();
		Usuario u = this.getSessionUserWorking();
		String action = request.getParameter("action") == null ? "" : request.getParameter("action");

		if (action.equals("getRendicionGastos"))
			return this.getRendicionGastos(samClient, mapping, request);
		else if (action.equals("getConsumosPendientes"))
			return this.getConsumosPendientes(samClient, mapping, request);
		else if (action.equals("activarRechazar"))
			return this.activarRechazar(samClient, mapping, request, response);
		else if (action.equals("modificarRendicion"))
			return this.modificarRendicion(samClient, mapping, request, response);
		else if (action.equals("exceptuarRendicion"))
			return this.exceptuarRendicion(samClient, mapping, request, response);
		else if (action.equals("finalizarObservacion"))
			return this.finalizarObservacion(samClient, mapping, request, response);
	

		RendicionForm renForm = (RendicionForm) form;
		renForm.reset();
		RendicionesService service = new RendicionesService(samClient);
		Integer idRendicion = null;
		String usuarioRend = u.getIdUser();

		// Chequea si viene de Cuadro detallado
		if (request.getParameter("usuario") != null) {
			UsuarioService usuarioService = new UsuarioService(samClient);
			usuarioRend = request.getParameter("usuario").trim().toUpperCase();
			u = usuarioService.obtenerDelegadosUsuario(usuarioRend);
			request.setAttribute("readonly", "true");
		}

		if (request.getParameter("codigo") == null)
			idRendicion = (Integer.parseInt((String) request.getAttribute("codigo")));
		else
			idRendicion = Integer.valueOf(request.getParameter("codigo"));

		if (request.getParameter("usuarioRendicion") != null && !request.getParameter("usuarioRendicion").equals(""))
			usuarioRend = request.getParameter("usuarioRendicion").toString();

		try {
			List<Rendicion> rendiciones = service.obtenerListadoRendiciones(usuarioRend, idRendicion.toString(), "", "", "");
			if (rendiciones.size() == 0) {
				request.setAttribute("Rendicion", new Rendicion());
				request.getSession().setAttribute("lastErrorMessage", "ERROR: RENDICION INEXISTENTE");
			} else {
				Rendicion rendicion = rendiciones.get(0);
				String serviceMessage = service.getMsg();
				if (serviceMessage != null) {
					messageBuilder.append(serviceMessage);
				}

				List<ComboMotivo> motivo = service.getMotivoRendiciones("4", usuarioRend,"");
				for (ComboMotivo fila : motivo) {
					if (fila.getId().equals(rendicion.getCodMotivo()))
						renForm.setCostosDestino(fila.getCostosDestino());
				}
				request.setAttribute("ComboMotivo", motivo);

				List<Gastos> gastos = service.getGastos(idRendicion.toString(), "", usuarioRend, rendicion.getCodMotivo());

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String dateD = df.format(rendicion.getFechaDesde());
				String dateH = df.format(rendicion.getFechaHasta());
				rendicion.setGastosRendicion(gastos);
				request.setAttribute("Rendicion", rendicion);
				renForm.setUser(usuarioRend);
				renForm.setNombreUsuario(u.getNombre());
				renForm.setCostos(u.getCcostos());
				renForm.setSector(u.getSector());
				renForm.setIdRendicion(rendicion.getId());
				renForm.setCodMotivo(rendicion.getCodMotivo());
				renForm.setMotivo(rendicion.getMotivo());
				
				//Inicio bloque para validar preformato de fechas
				request.setAttribute("motivoRend", rendicion.getMotivo());
				
//				if(rendicion.getMotivo().trim().equals("GYMPASS")){
//					String mesGasto = dateD.substring(dateD.indexOf('/') + 1);
//					renForm.setFechaDesde(mesGasto);
//				}
//				else{
					renForm.setFechaDesde(dateD);
//				}
				//Fin bloque para validar preformato de fechas
				
				//INICIO BLOQUE DATO EXEP
				boolean rendicionExceptuada = rendicion.getExceptuado().equals("EXEP") ? true : false;//equals.("EXEP")
				request.setAttribute("rendExceptuada", rendicionExceptuada);
				//FIN BLOQUE DATO EXEP
				
				renForm.setFechaHasta(dateH);
				renForm.setFechaUltimaModificacion(rendicion.getFechaUltimaModificacion());
				renForm.setAviso(rendicion.getAviso());
				
				for (Gastos gasto : gastos) {
					if (renForm.getGastoFechaMin() == null || 
							DateUtil.dfDDMMYYYY.parse(gasto.getFechagastos()).before(DateUtil.dfDDMMYYYY.parse(renForm.getGastoFechaMin())))
						renForm.setGastoFechaMin(gasto.getFechagastos());
					
					if (renForm.getGastoFechaMax() == null || 
							DateUtil.dfDDMMYYYY.parse(gasto.getFechagastos()).after(DateUtil.dfDDMMYYYY.parse(renForm.getGastoFechaMax())))
					renForm.setGastoFechaMax(gasto.getFechagastos());
				}

				if (!renForm.getAviso().equalsIgnoreCase("")) {
					request.setAttribute("avisoRendicion", "si");
					request.setAttribute("avisoMostrar", rendicion.getAviso());
				}

				if (!(rendicion.getFechaUltimaModificacion() != null) || !rendicion.getFechaUltimaModificacion().equalsIgnoreCase("")) {
					request.setAttribute("ultimaModif", "si");
				}
				// se calculan dias entre fecha desde y hasta de la rendicion
				Calendar cal1 = new GregorianCalendar();
				Calendar cal2 = new GregorianCalendar();

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				Date date = sdf.parse(dateD);
				cal1.setTime(date);
				date = sdf.parse(dateH);
				cal2.setTime(date);
				renForm.setCantDias(DateUtil.daysBetween(cal1.getTime(), cal2.getTime()) + 1);
				renForm.setMotivoRechazo(rendicion.getMotivoRechazo());
				if (renForm.getMotivoRechazo() != null && !renForm.getMotivoRechazo().equalsIgnoreCase("")) {
					if (rendicion.getEstado().equalsIgnoreCase("APROB"))
						request.setAttribute("motivoRechAprob", "APROB");
					else
						request.setAttribute("motivoRechAprob", "RECHA");

				} else {
					request.setAttribute("motivoRechAprob", "No");
				}
				
				
				renForm.setDescripcion(rendicion.getDescripcion());
				renForm.setEstadoRend(rendicion.getEstado());
				renForm.setDescripcionEstado(rendicion.getDescripcionEstado());
				renForm.setUsuarioAprobador(rendicion.getUsuarioAprobador());

				if (renForm.getUsuarioAprobador() != null && !renForm.getUsuarioAprobador().equalsIgnoreCase("")) {
					request.setAttribute("usuarioAprobador", "SI");
				}

				if (renForm.getEstadoRend() == null)
					renForm.setEstadoRend(request.getParameter("estadoRendicion"));

				request.setAttribute("estadoRend", rendicion.getEstado());

				if (rendicion.getEstado() != null && !gastos.isEmpty()) { // Siempre puede adjuntar imagenes sin importar el estado de la rend
					request.setAttribute("showAviso", "true");
				} else {
					if (!rendicion.getIdu().equals("") && !rendicion.getAdea().equalsIgnoreCase("")) {
						request.setAttribute("showCaratula", "true");
					}
				}
				if (idRendicion != null)
					renForm.setLinkThuban((String) request.getSession().getServletContext().getAttribute("rendicion.link.thuban") + idRendicion);
			}
		} catch (Exception e) {
			log.error("", e);
			this.setErrorMessage(e, request);
		}

		return mapping.findForward("rendicionDetalleGastos");
	}

	private ActionForward getRendicionGastos(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request) throws Exception {
		RendicionesService service = new RendicionesService(samClient);
		String estadoRend = (String) request.getParameter("estadoRend");
		List<Gastos> gastos = service.getGastos(request.getParameter("idRendicion"), "", request.getParameter("usuarioRend"),
				request.getParameter("codMotivo"));
		String serviceMessage = service.getMsg();
		if (serviceMessage != null) {
			request.getSession().setAttribute("lastErrorMessage", serviceMessage);
		}
		request.setAttribute("gastos", gastos);
		request.setAttribute("showOpciones", true);
		request.setAttribute("showEditar", estadoRend.equals("PENDI"));
		request.setAttribute("showBorrar", estadoRend.equals("PENDI"));
		request.setAttribute("readOnlyDatosAdicionales", !(estadoRend.equals("PENDI") || estadoRend.equals("OBSER")));
		request.setAttribute("readOnlyCupones", !(estadoRend.equals("PENDI") || estadoRend.equals("OBSER")));

		return mapping.findForward("gastos");
	}
	
	private ActionForward getConsumosPendientes(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request) throws Exception {
		List<Resumen> consumosPendientes = new ArrayList<Resumen>();
		ResumenService service = new ResumenService(samClient);
		
		String fechaDesde = DateUtil.dfYYYYMMDD.format(DateUtil.dfDDMMYYYY.parse(request.getParameter("fechaDesde")));
		String fechaHasta = DateUtil.dfYYYYMMDD.format(DateUtil.dfDDMMYYYY.parse(request.getParameter("fechaHasta")));
		String codMotivo = request.getParameter("codMotivo");
		
		
		if(this.getSessionUserWorking().isManejaFacultades()) consumosPendientes = service.getConsumos(this.getSessionUserWorking().getIdUser(), fechaDesde, fechaHasta, codMotivo);
		String serviceMessage = service.getMsg();
		if (serviceMessage != null) {
			request.getSession().setAttribute("lastErrorMessage", serviceMessage);
		}

		request.setAttribute("consumosPendientes", consumosPendientes);

		return mapping.findForward("consumosPendientes");
	}

	private ActionForward activarRechazar(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Map<String, Object> resp = new HashMap<String, Object>();
			RendicionesService service = new RendicionesService(samClient);

			String idRendicion = request.getParameter("idRendicion");
			String estado = request.getParameter("estado");
			service.activaRechazaRendicion(estado, this.getSessionUserWorking().getIdUser(), idRendicion);

			if (service.getMsg() != null)
				resp.put("message", "OK: " + service.getMsg());

			return writeJson(response, resp);
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward modificarRendicion(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Map<String, Object> resp = new HashMap<String, Object>();
			RendicionesService service = new RendicionesService(samClient);

			String idRendicion = request.getParameter("idRendicion");
			String codMotivo = request.getParameter("codMotivo");
			String estadoRend = request.getParameter("estadoRend");
			String fechaDesde = DateUtil.dfYYYYMMDD.format(DateUtil.dfDDMMYYYY.parse(request.getParameter("fechaDesde")));
			String fechaHasta = DateUtil.dfYYYYMMDD.format(DateUtil.dfDDMMYYYY.parse(request.getParameter("fechaHasta")));
			String descRendicion = request.getParameter("desc_rendicion");
			String excepcion = request.getParameter("cod_estado_doc") != null ?request.getParameter("cod_estado_doc") : "" ;
			
			service.modificarRendicion(idRendicion, this.getSessionUserWorking().getIdUser(), codMotivo, fechaDesde, fechaHasta, descRendicion, estadoRend,excepcion);

			if (service.getMsg() != null)
				resp.put("message", "OK: " + service.getMsg());

			return writeJson(response, resp);
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}
	
	private ActionForward exceptuarRendicion(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Map<String, Object> resp = new HashMap<String, Object>();
			RendicionesService service = new RendicionesService(samClient);

			String opcion = request.getParameter("opcion");
			String idRendicion = request.getParameter("idRendicion");
			String excepcion = request.getParameter("cod_estado_doc") != null ? request.getParameter("cod_estado_doc") : "";
			
			service.exceptuarRendicion(opcion, idRendicion, excepcion);

			if (service.getMsg() != null)
				resp.put("message", "OK: " + service.getMsg());

			return writeJson(response, resp);
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward finalizarObservacion(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Map<String, Object> resp = new HashMap<String, Object>();
			AprobacionesService service = new AprobacionesService(samClient);

			String idRendicion = request.getParameter("idRendicion");
			String idu = request.getParameter("idu");
			
			service.cambiarEscanRendicion(idRendicion, this.getSessionUserWorking().getIdUser(), idu);

			if (service.getMsg() != null)
				resp.put("message", "OK: " + service.getMsg());

			return writeJson(response, resp);
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}
	
	
	
	
	
}
