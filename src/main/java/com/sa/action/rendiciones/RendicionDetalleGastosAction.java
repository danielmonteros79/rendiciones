package com.sa.action.rendiciones;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.sa.services.RendicionesService;
import com.sa.services.ResumenService;
import com.sa.services.UsuarioService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import ar.org.bbva.util.DateUtils;

public class RendicionDetalleGastosAction extends RestriccionTransaccionAction {
	
	private static final String PENDI = "PENDI";
	private static final String COD_MOTIVO = "codMotivo";
	private static final String CODIGO = "codigo";
	private static final String ESTADO_REND = "estadoRend";
	private static final String ID_REND = "idRendicion";
	private static final String MSG = "message";
	private static final String MOT_RECH_APROB = "motivoRechAprob";
	private static final String USUARIO_REND = "usuarioRendicion";
	
	
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
	        HttpServletRequest request, HttpServletResponse response) throws Exception {
	    this.message = "";
	    Usuario u = this.sessionUserWorking;
	    String action = request.getParameter("action") == null ? "" : request.getParameter("action");

	    if (action.equals("getRendicionGastos"))
	        return this.getRendicionGastos(samClient, mapping, request);
	    else if (action.equals("getConsumosPendientes"))
	        return this.getConsumosPendientes(samClient, mapping, request);
	    else if (action.equals("activarRechazar"))
	        return this.activarRechazar(samClient, mapping, request, response);
	    else if (action.equals("modificarRendicion"))
	        return this.modificarRendicion(samClient, mapping, request, response);
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

	    if (request.getParameter(CODIGO) == null)
	        idRendicion = (Integer.parseInt((String) request.getAttribute(CODIGO)));
	    else
	        idRendicion = Integer.valueOf(request.getParameter(CODIGO));

	    if (request.getParameter(USUARIO_REND) != null && !request.getParameter(USUARIO_REND).equals(""))
	        usuarioRend = request.getParameter(USUARIO_REND).toString();

	    try {
	        List<Rendicion> rendiciones = service.obtenerListadoRendiciones(usuarioRend, idRendicion.toString(), "", "", "");
	        if (rendiciones.isEmpty()) {
	            handleRendicionNotFound(request);
	        } else {
	            Rendicion rendicion = rendiciones.get(0);
	            this.message = service.getMsg();

	            populateRendicionForm(rendicion, renForm, usuarioRend, service, request, u);
	        }
	    } catch (Exception e) {
	        log.error("", e);
	        this.setErrorMessage(e);
	    }

	    return mapping.findForward("rendicionDetalleGastos");
	}

	private void handleRendicionNotFound(HttpServletRequest request) {
	    request.setAttribute("Rendicion", new Rendicion());
	    this.message = "ERROR: RENDICION INEXISTENTE";
	}

	private void populateRendicionForm(Rendicion rendicion, RendicionForm renForm, String usuarioRend, RendicionesService service, HttpServletRequest request, Usuario u) throws TransactionException {
	    List<ComboMotivo> motivo = service.getMotivoRendiciones("4", usuarioRend);
	    renForm.setCostosDestino(getCostosDestino(rendicion.getCodMotivo(), motivo));
	    request.setAttribute("ComboMotivo", motivo);

	    List<Gastos> gastos = service.getGastos(rendicion.getId().toString(), "", usuarioRend, rendicion.getCodMotivo());

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
	    renForm.setFechaDesde(dateD);
	    renForm.setFechaHasta(dateH);
	    renForm.setFechaUltimaModificacion(rendicion.getFechaUltimaModificacion());
	    renForm.setAviso(rendicion.getAviso());

	    renForm.setGastoFechaMin(getMinGastoFecha(gastos));
	    renForm.setGastoFechaMax(getMaxGastoFecha(gastos));

	    String avisoRendicion = renForm.getAviso().isEmpty() ? "No" : "Si";
	    request.setAttribute("avisoRendicion", avisoRendicion);
	    request.setAttribute("avisoMostrar", rendicion.getAviso());

	    String ultimaModif = rendicion.getFechaUltimaModificacion().isEmpty() ? "No" : "Si";
	    request.setAttribute("ultimaModif", ultimaModif);
	    renForm.setMotivoRechazo(rendicion.getMotivoRechazo());

	    String ternarioResol = rendicion.getEstado().equalsIgnoreCase("APROB") ? "APROB" : "RECHA";
	    String motRechAprob = renForm.getMotivoRechazo().isEmpty() ? "No" : ternarioResol;
	    request.setAttribute(MOT_RECH_APROB, motRechAprob);

	    renForm.setDescripcion(rendicion.getDescripcion());
	    renForm.setEstadoRend(rendicion.getEstado());
	    renForm.setDescripcionEstado(rendicion.getDescripcionEstado());
	    renForm.setUsuarioAprobador(rendicion.getUsuarioAprobador());
	    String usuarioAprobador = renForm.getUsuarioAprobador().isEmpty() ? "No" : "SI";
	    request.setAttribute("usuarioAprobador", usuarioAprobador);

	    if (renForm.getEstadoRend() == null)
	        renForm.setEstadoRend(request.getParameter("estadoRendicion"));

	    request.setAttribute(ESTADO_REND, rendicion.getEstado());

	    String showAviso = rendicion.getEstado() != null && !gastos.isEmpty() ? "true" : "false";
	    request.setAttribute("showAviso", showAviso);

	    if (rendicion.getIdu() != null && !rendicion.getIdu().equals("") && !rendicion.getAdea().equalsIgnoreCase("")) {
	        String showCaratula = "true";
	        request.setAttribute("showCaratula", showCaratula);
	    }

	    if (rendicion.getId() != null)
	        renForm.setLinkThuban((String) request.getSession().getServletContext().getAttribute("rendicion.link.thuban") + rendicion.getId());
	}

	private String getCostosDestino(String codMotivo, List<ComboMotivo> motivo) {
	    for (ComboMotivo fila : motivo) {
	        if (fila.getId().equals(codMotivo))
	            return fila.getCostosDestino();
	    }
	    return null;
	}

	private String getMinGastoFecha(List<Gastos> gastos) {
	    String minDate = null;
	    for (Gastos gasto : gastos) {
	        if (minDate == null || gasto.getFechagastos().compareTo(minDate) < 0) {
	            minDate = gasto.getFechagastos();
	        }
	    }
	    return minDate;
	}

	private String getMaxGastoFecha(List<Gastos> gastos) {
	    String maxDate = null;
	    for (Gastos gasto : gastos) {
	        if (maxDate == null || gasto.getFechagastos().compareTo(maxDate) > 0) {
	            maxDate = gasto.getFechagastos();
	        }
	    }
	    return maxDate;
	}



	private ActionForward getRendicionGastos(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request) throws Exception {
		RendicionesService service = new RendicionesService(samClient);
		String estadoRend =  request.getParameter(ESTADO_REND);
		List<Gastos> gastos = service.getGastos(request.getParameter(ID_REND), "", request.getParameter("usuarioRend"),
				request.getParameter(COD_MOTIVO));
		this.message = service.getMsg();
		request.setAttribute("gastos", gastos);
		request.setAttribute("showOpciones", true);
		request.setAttribute("showEditar", estadoRend.equals(PENDI));
		request.setAttribute("showBorrar", estadoRend.equals(PENDI));
		request.setAttribute("readOnlyDatosAdicionales", !(estadoRend.equals(PENDI) || estadoRend.equals("OBSER")));
		request.setAttribute("readOnlyCupones", !(estadoRend.equals(PENDI) || estadoRend.equals("OBSER")));

		return mapping.findForward("gastos");
	}
	
	private ActionForward getConsumosPendientes(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request) throws Exception {
		List<Resumen> consumosPendientes = new ArrayList<>();
		ResumenService service = new ResumenService(samClient);
	
		String fechaDesde = DateUtils.dfYYYYMMDD.format(DateUtils.dfDDMMYYYY.parse(request.getParameter("fechaDesde")));
		String fechaHasta = DateUtils.dfYYYYMMDD.format(DateUtils.dfDDMMYYYY.parse(request.getParameter("fechaHasta")));
		String codMotivo = request.getParameter(COD_MOTIVO);
		
		
		if(this.sessionUserWorking.isManejaFacultades()) consumosPendientes = service.getConsumos(this.sessionUserWorking.getIdUser(), fechaDesde, fechaHasta, codMotivo);
		this.message = service.getMsg();

		request.setAttribute("consumosPendientes", consumosPendientes);

		return mapping.findForward("consumosPendientes");
	}

	private ActionForward activarRechazar(SAMWebClient samClient, ActionMapping mapping, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Map<String, Object> resp = new HashMap<String, Object>();
			RendicionesService service = new RendicionesService(samClient);

			String idRendicion = request.getParameter(ID_REND);
			String estado = request.getParameter("estado");
			service.activaRechazaRendicion(estado, this.sessionUserWorking.getIdUser(), idRendicion);

			if (service.getMsg() != null)
				resp.put(MSG, "OK: " + service.getMsg());

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
			String idRendicion = request.getParameter(ID_REND);
			String codMotivo = request.getParameter(COD_MOTIVO);
			String estadoRend = request.getParameter(ESTADO_REND);
			String fechaDesde = DateUtils.dfYYYYMMDD.format(DateUtils.dfDDMMYYYY.parse(request.getParameter("fechaDesde")));
			String fechaHasta = DateUtils.dfYYYYMMDD.format(DateUtils.dfDDMMYYYY.parse(request.getParameter("fechaHasta")));
			String descRendicion = request.getParameter("descripcion");
			
			service.modificarRendicion(idRendicion, this.sessionUserWorking.getIdUser(), codMotivo, fechaDesde, fechaHasta, descRendicion, estadoRend);

			if (service.getMsg() != null)
				resp.put(MSG, "OK: " + service.getMsg());

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

			String idRendicion = request.getParameter(ID_REND);
			String idu = request.getParameter("idu");
			
			service.cambiarEscanRendicion(idRendicion, this.sessionUserWorking.getIdUser(), idu);

			if (service.getMsg() != null)
				resp.put(MSG, "OK: " + service.getMsg());

			return writeJson(response, resp);
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}
}