package com.sa.action.parametros;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.ComboOpcion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroGasto;
import com.sa.exceptions.ActionExecutionException;
import com.sa.form.parametros.ParametrosGastosForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.AprobacionesService;
import com.sa.services.ParametrosService;
import org.apache.commons.text.StringEscapeUtils;

public class ParametrosGastosDetalleLoadAction extends RestriccionTransaccionAction {
	
	// Service field made final to comply with SonarQube S2226
	private final ParametrosService parametrosService;
	
	// Thread-local storage for mutable list fields to avoid instance field issues
	private static final ThreadLocal<List<ComboOpcion>> threadLocalCmbObservacion = new ThreadLocal<>();
	private static final ThreadLocal<List<ComboOpcion>> threadLocalCmbMotivo = new ThreadLocal<>();
	private static final ThreadLocal<List<ComboOpcion>> threadLocalCmbComprobante = new ThreadLocal<>();
	
	public ParametrosGastosDetalleLoadAction() {
		// Default constructor for framework usage - service will be null and created on demand
		this.parametrosService = null;
	}
	
	public ParametrosGastosDetalleLoadAction(ParametrosService parametrosService) {
		this.parametrosService = parametrosService;
	}
	
	private static final Log log = LogFactory.getLog(ParametrosGastosDetalleLoadAction.class);

	// Getters for ThreadLocal list fields
	
	private List<ComboOpcion> getCmbObservacion() {
		List<ComboOpcion> list = threadLocalCmbObservacion.get();
		if (list == null) {
			list = new ArrayList<>();
			threadLocalCmbObservacion.set(list);
		}
		return list;
	}
	
	private List<ComboOpcion> getCmbMotivo() {
		List<ComboOpcion> list = threadLocalCmbMotivo.get();
		if (list == null) {
			list = new ArrayList<>();
			threadLocalCmbMotivo.set(list);
		}
		return list;
	}
	
	private List<ComboOpcion> getCmbComprobante() {
		List<ComboOpcion> list = threadLocalCmbComprobante.get();
		if (list == null) {
			list = new ArrayList<>();
			threadLocalCmbComprobante.set(list);
		}
		return list;
	}

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws ActionExecutionException {
		ParametrosGastosForm frm = (ParametrosGastosForm) form;
		String descripcionMotivo = frm.getDescripcionMotivo();
		String sanitizedDescripcionMotivo = (descripcionMotivo != null) ? StringEscapeUtils.escapeHtml4(descripcionMotivo) : "";
		request.getSession().setAttribute("desc_motivo", sanitizedDescripcionMotivo);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosNuevoGastoLoadAction. Usuario (" + user.getIdUser() + ")");

		String accionJson = request.getParameter("accionJson");
		if ("borrarCentroCosto".equals(accionJson))
			try {
				return this.borrarCentroCosto(frm, Integer.parseInt(request.getParameter("index")));
			} catch (Exception e) {
				throw new ActionExecutionException("Error removing centro costo", e);
			}
		else if ("agregarCentroCosto".equals(accionJson))
			try {
				return this.agregarCentroCosto(frm, response);
			} catch (Exception e) {
				throw new ActionExecutionException("Error adding centro costo", e);
			}
		
		if (!frm.isBack()) {
			// Initialize ThreadLocal lists
			this.getCmbObservacion().clear();
			this.getCmbMotivo().clear();
			this.getCmbComprobante().clear();
			
			
			try {
				// Get or create service instance  
				ParametrosService service = this.parametrosService != null ? this.parametrosService : new ParametrosService(samClient);
				
				if (frm.getAccion().equals("alta")) {
					frm.clear();
					frm.setEstado("A");
				} else if (frm.getAccion().equals("modificacion"))
					this.gastoToFormGaston(frm, request, service.loadModificacionGastoGaston(frm.getCodigo(), user.getIdUser()));
				else if (frm.getAccion().equals("baja")) {
					this.gastoToFormGaston(frm, request, service.loadBajaGastoGaston(frm.getCodigo(), user.getIdUser()));
				}
				request.setAttribute("message", service.getMsgAviso());
			} catch (Exception e) {
				String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
			    request.setAttribute("message", "ERROR: " + errorMessage);
			}
		}
		
		request.setAttribute("cmbMotivo", this.getCmbMotivo());
		request.setAttribute("cmbComprobante", this.getCmbComprobante());
		request.setAttribute("cmbObservacion", this.getCmbObservacion());

		return mapping.findForward(frm.getAccion());
	}

	@SuppressWarnings("unchecked")
	private void gastoToForm(ParametrosGastosForm frm, HttpServletRequest request, ManagerTransaction manager) {
		ParametroGasto gasto = (ParametroGasto) manager.getDataReturn();
		
		frm.setEstado(gasto.getEstado());
		//frm.setIdCentroCostos(gasto.getCcostos());
		frm.setRistra(gasto.getRistra());
		//frm.setOscar((gasto.getOscar()));
		//frm.setMaInclExcl(gasto.getMaInclExcl());
		//frm.setComprob(gasto.getComprob());
		//frm.setAntiguedad(gasto.getAntiguedad());
		frm.setBimon(gasto.getBimon());
		//frm.setAutoriz(gasto.getAutoriz());
		frm.setObserv(gasto.getObserv());
		frm.setIdNivAutoriz(gasto.getNivelIngreso());
		//frm.setPlazoAprob(gasto.getPlazoAprob());
		//frm.setCentrosCostoList(gasto.getCentrosCosto());
		
		//this.cargarCombos(request, (List<String>) manager.getDataReturnList());
	}
	
	protected void gastoToFormGaston(ParametrosGastosForm frm, HttpServletRequest request, ParametroGasto gasto) {
		
		frm.setEstado(gasto.getEstado());
		//frm.setIdCentroCostos(gasto.getCcostos());
		//frm.setRistra(gasto.getRistra());
		//frm.setOscar((gasto.getOscar()));
		//frm.setMaInclExcl(gasto.getMaInclExcl());
		//frm.setComprob(gasto.getComprob());
		//frm.setAntiguedad(gasto.getAntiguedad());
		frm.setBimon(gasto.getBimon());
		//frm.setAutoriz(gasto.getAutoriz());
		frm.setObserv(gasto.getObserv());
		frm.setIdNivAutoriz(gasto.getNivelIngreso());
		frm.setDetalleRistra(gasto.getRistra());
		frm.setDescripcionMotivo(gasto.getDescripcionMotivo());
		frm.setDescripcionGasto(gasto.getDescripcionGasto());
		//frm.setPlazoAprob(gasto.getPlazoAprob());
		//frm.setCentrosCostoList(gasto.getCentrosCosto());
		
		//this.cargarCombos(request, (List<String>) manager.getDataReturnList());
	}
	
	private void cargarCombos(HttpServletRequest request, List<String> combos) {
		for (String fila : combos) {
			if (fila.substring(0, 2).equals("MD")) {
				String codigo = fila.substring(2, 6).trim();
				this.getCmbMotivo().add(new ComboOpcion(codigo, codigo + " - " + fila.substring(6)));
			}
			if (fila.substring(0, 2).equals("TC")) {
				String codigo = fila.substring(52).trim();
				this.getCmbComprobante().add(new ComboOpcion(codigo, fila.substring(2, 52).trim()));
			}
			if (fila.substring(0, 2).equals("OB")) {
				this.getCmbObservacion().add(new ComboOpcion(fila.substring(2).trim()));
			}
		}
	}
	
	private ActionForward borrarCentroCosto(ParametrosGastosForm frm, int index) {
		frm.getCentrosCosto().remove(index);
		
		return null;
	}

	private ActionForward agregarCentroCosto(ParametrosGastosForm frm, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		if (frm.getCentrosCosto().size() < 15) {
			frm.getCentrosCosto().add("");
			out.print(frm.getCentrosCosto().size() - 1);
		} else
			out.print(-1);
		
		out.close();
		return null;
	}

	/**
	 * Clean up ThreadLocal variables to prevent memory leaks.
	 * Should be called at the end of request processing.
	 */
	@Override
	protected void cleanupThreadLocals() {
		super.cleanupThreadLocals(); // Clean up inherited ThreadLocals
		threadLocalCmbObservacion.remove();
		threadLocalCmbMotivo.remove();
		threadLocalCmbComprobante.remove();
	}
}