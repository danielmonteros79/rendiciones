package com.sa.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.ComboGasto;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.Gastos;
import com.sa.entities.Usuario;
import com.sa.form.RendicionDetalleForm;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import com.sa.util.ParamsConstants;

public class ModificarGastoAction extends RestriccionTransaccionAction {
	
	private static final String COD_MOTIVO = "codMotivo";
	private static final String CODIGO = "codigo";
	private static final String LISTADO_APRO = "listadoAprob";
	
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		   RendicionDetalleForm renForm = (RendicionDetalleForm) form;
		    Usuario u = (Usuario) request.getSession().getAttribute("userWorking");
		    Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		    log.info("Entra al action ModificarGastoAction. Usuario (" + user.getIdUser() + ")");

		    cargarEstadoRendicion(request, renForm);
		    cargarIdRendicion(request, renForm);
		    cargarFechas(request);
		    cargarCombos(request, samClient, u.getIdUser());
		    obtenerDatosGasto(request, samClient, renForm, u.getIdUser());
		    asignarValores(request, renForm);

		    return mapping.findForward("success");
		}

		private void cargarEstadoRendicion(HttpServletRequest request, RendicionDetalleForm renForm) {
		    renForm.setEstadoRendicion(request.getParameter("estadoRendicion"));
		    request.setAttribute("estadoRend", request.getParameter("estadoRendicion"));
		}

		private void cargarIdRendicion(HttpServletRequest request, RendicionDetalleForm renForm) {
		    Integer idRendicion = (request.getParameter(CODIGO) == null) ? Integer.parseInt((String) request.getAttribute("idRendicion"))
		            : Integer.valueOf(request.getParameter(CODIGO));

		    request.setAttribute("idRendicion", idRendicion);
		    renForm.setIdRendicion(request.getParameter(CODIGO));
		    renForm.setCodMotivo(request.getParameter(COD_MOTIVO));
		    renForm.setCentroCostos(request.getParameter("cCosto"));
		}

		private void cargarFechas(HttpServletRequest request ) {
		    String fechaD = request.getParameter("fechaD");
		    String fechaH = request.getParameter("fechaH");
		    request.setAttribute("fechaD", fechaD);
		    request.setAttribute("fechaH", fechaH);
		}

		private void cargarCombos(HttpServletRequest request, SAMWebClient samClient,
		        String userId) throws TransactionException {
		    RendicionesService serviceCombos = new RendicionesService(samClient);

		    List<ComboOpcion2> moneda = serviceCombos.getComboOpcion2(ParamsConstants.MONEDA_OPCION,
		            ParamsConstants.MONEDA_TABLA + ParamsConstants.MONEDA_SUBTABLA + ParamsConstants.MONEDA_CODIGO,
		            ParamsConstants.MONEDA_CANTIDAD, userId);
		    request.setAttribute("ComboMoneda", moneda);

		    List<ComboOpcion2> comprobante = serviceCombos.getComboOpcion2(ParamsConstants.COMPROBANTE_OPCION,
		            ParamsConstants.COMPROBANTE_TABLA + ParamsConstants.COMPROBANTE_SUBTABLA
		                    + ParamsConstants.COMPROBANTE_CODIGO,
		            ParamsConstants.COMPROBANTE_CANTIDAD, userId);
		    request.setAttribute("ComboComprobante", comprobante);

		    PagosService service = new PagosService(samClient);
		    List<ComboGasto> tipoGastos = service.getComboGasto(ParamsConstants.TIPO_GASTO_OPCION, userId,
		            request.getParameter(COD_MOTIVO));
		    if (tipoGastos != null) {
		        request.setAttribute("ComboGastos", tipoGastos);
		    }
		}

		private void obtenerDatosGasto(HttpServletRequest request, SAMWebClient samClient, RendicionDetalleForm renForm,
		        String userId) throws TransactionException {
		    RendicionesService serviceCombos = new RendicionesService(samClient);
		    Integer idRendicion = Integer.parseInt(request.getParameter(CODIGO));
		    String idGasto = request.getParameter("idGasto");

		    List<Gastos> gastos = serviceCombos.getGastos(idRendicion.toString(), idGasto, userId,
		            request.getParameter(COD_MOTIVO));
		    renForm.setIdRendicion(request.getParameter(CODIGO));
		    renForm.setCodMotivo(request.getParameter(COD_MOTIVO));
		    renForm.setCentroCostos(request.getParameter("cCosto"));
		    Gastos gasto = obtenerGastoSeleccionado(gastos, idGasto);

		    if (gasto != null) {
		        ComboGasto comboGastos = obtenerComboGasto(gasto.getDescGasto(),
		                (List<ComboGasto>) request.getAttribute("ComboGastos"));
		        ComboOpcion2 comboMoneda = obtenerComboMoneda(gasto.getMoneda(),
		                (List<ComboOpcion2>) request.getAttribute("ComboMoneda"));
		        ComboOpcion2 comboComprobante = obtenerComboComprobante(gasto.getComprobante(),
		                (List<ComboOpcion2>) request.getAttribute("ComboComprobante"));

		        renForm.setGasto(comboGastos != null ? comboGastos.getId() : null);
		        renForm.setFechaGasto(gasto.getFechagastos());
		        renForm.setMoneda(comboMoneda != null ? comboMoneda.getId() : null);
		        renForm.setTipoComprobante(comboComprobante != null ? comboComprobante.getId() : null);
		        renForm.setGlg(gasto.getIdGasto());
		        renForm.setMonto(gasto.getMonto());
		        renForm.setMontoMaximo(gasto.getMonto());
		        renForm.setObservacionGasto(gasto.getObservacionGasto());
		    }

		    asignarValoresCupon(request, renForm, gasto);
		    asignarAtributoListadoAprob(request);
		    asignarOpcionTitulo(request);
		}

		private Gastos obtenerGastoSeleccionado(List<Gastos> gastos, String idGasto) {
		    return gastos.stream().filter(r -> r.getIdGasto().equalsIgnoreCase(idGasto)).findFirst().orElse(null);
		}

		private ComboGasto obtenerComboGasto(String descGasto, List<ComboGasto> tipoGastos) {
		    return tipoGastos.stream().filter(cg -> cg.getDescripcion().trim().equalsIgnoreCase(descGasto)).findFirst()
		            .orElse(null);
		}

		private ComboOpcion2 obtenerComboMoneda(String moneda, List<ComboOpcion2> monedaList) {
		    return monedaList.stream().filter(cm -> cm.getId().trim().equalsIgnoreCase(moneda)).findFirst().orElse(null);
		}

		private ComboOpcion2 obtenerComboComprobante(String comprobante, List<ComboOpcion2> comprobanteList) {
		    return comprobanteList.stream()
		            .filter(cf -> cf.getDescripcion().substring(0, 1).equalsIgnoreCase(comprobante.substring(0, 1)))
		            .findFirst().orElse(null);
		}

		private void asignarValores(HttpServletRequest request, RendicionDetalleForm renForm) {
		    String tieneCupon = request.getParameter("tieneCupon");
		    String listadoAprob = request.getParameter(LISTADO_APRO);
		    String opcion = request.getParameter("opcion");

		    renForm.setImporteCupon(tieneCupon.equalsIgnoreCase("1") ? renForm.getMonto().trim() : "");
		    request.setAttribute("conCupon", tieneCupon);

		    if (listadoAprob.equalsIgnoreCase("1")) {
		        request.setAttribute(LISTADO_APRO, "1");
		    }

		    renForm.setUsuarioRend(request.getParameter("user"));
		    renForm.setGlg(request.getParameter("glg"));

		    if (opcion.equalsIgnoreCase("MODI")) {
		        request.setAttribute("opcionTitulo", "1");
		    }
		}

		private void asignarValoresCupon(HttpServletRequest request, RendicionDetalleForm renForm, Gastos gasto) {
		    if (request.getParameter("tieneCupon").equalsIgnoreCase("1")) {
		    	if(gasto != null) {
		    		 renForm.setImporteCupon(gasto.getMonto().trim());
		    	}
		        request.setAttribute("conCupon", "1");
		    } else {
		        renForm.setCupCred("");
		        renForm.setCupDeb("");
		        renForm.setCupon("");
		        renForm.setCuponesCheck("");
		        renForm.setDescCupon("");
		        renForm.setImporteCupon("");
		        renForm.setNroTarjeta("");
		    }
		}

		private void asignarAtributoListadoAprob(HttpServletRequest request) {
		    if (request.getParameter(LISTADO_APRO).equalsIgnoreCase("1")) {
		        request.setAttribute(LISTADO_APRO, "1");
		    }
		}

		private void asignarOpcionTitulo(HttpServletRequest request) {
		    if (request.getParameter("opcion").equalsIgnoreCase("MODI")) {
		        request.setAttribute("opcionTitulo", "1");
		    }
		}







}