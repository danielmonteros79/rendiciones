package com.sa.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.entities.ComboGasto;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionDetalleForm;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import com.sa.util.ParamsConstants;

public class ModificarGastoAction extends RestriccionTransaccionAction {
	
	private static final String COD_MOTIVO = "codMotivo";
	private static final String CODIGO = "codigo";
	
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RendicionDetalleForm renForm = (RendicionDetalleForm) form;
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action ModificarGastoAction. Usuario ("+user.getIdUser()+")");
		renForm.setEstadoRendicion(request.getParameter("estadoRendicion"));
		request.setAttribute("estadoRend",request.getParameter("estadoRendicion"));
		PagosService service = new PagosService(samClient);
		RendicionesService serviceCombos = new RendicionesService(samClient);
		Integer idRendicion=null;
		if (request.getParameter(CODIGO) == null) {
			
			idRendicion = (Integer.parseInt((String) request.getAttribute("idRendicion")));
			
		} else {
			idRendicion = Integer.valueOf(request.getParameter(CODIGO));
		}
		 request.setAttribute("idRendicion", idRendicion);
		 String fechaD=request.getParameter("fechaD");
		 String fechaH=request.getParameter("fechaH");
		 request.setAttribute("fechaD", fechaD);
		 request.setAttribute("fechaH", fechaH);
		log.info("Se llama al service que realiza la carga de los combos para el alta de gastos");
		List<ComboOpcion2> moneda = serviceCombos.getComboOpcion2(
				ParamsConstants.MONEDA_OPCION, ParamsConstants.MONEDA_TABLA
						+ ParamsConstants.MONEDA_SUBTABLA
						+ ParamsConstants.MONEDA_CODIGO,
				ParamsConstants.MONEDA_CANTIDAD, u.getIdUser());
		request.setAttribute("ComboMoneda", moneda);

		List<ComboOpcion2> comprobante = serviceCombos.getComboOpcion2(
				ParamsConstants.COMPROBANTE_OPCION,
				ParamsConstants.COMPROBANTE_TABLA
						+ ParamsConstants.COMPROBANTE_SUBTABLA
						+ ParamsConstants.COMPROBANTE_CODIGO,
				ParamsConstants.COMPROBANTE_CANTIDAD, u.getIdUser());
		request.setAttribute("ComboComprobante", comprobante);

		List<ComboGasto> tipoGastos = service
				.getComboGasto(ParamsConstants.TIPO_GASTO_OPCION,u.getIdUser(),request.getParameter(COD_MOTIVO));
		request.setAttribute("ComboGastos", tipoGastos);
		String idGasto = request.getParameter("idGasto");
		List<Gastos> Gastos = serviceCombos.getGastos(idRendicion.toString(), idGasto, u.getIdUser(), request.getParameter(COD_MOTIVO));
		renForm.setIdRendicion(request.getParameter(CODIGO));
		renForm.setCodMotivo(request.getParameter(COD_MOTIVO));
		renForm.setCentroCostos(request.getParameter("cCosto"));
		Gastos gasto = null;

		for (Gastos r : Gastos) {
			if (r.getIdGasto().equalsIgnoreCase(idGasto)) {
				gasto = r;
				break;
			}

		}
		ComboGasto comboGastos = null;
		for (ComboGasto cg : tipoGastos) {
			
			if (cg.getDescripcion().trim().equalsIgnoreCase(gasto.getDescGasto())) {
				comboGastos = cg;
				break;
			}

		}
		ComboOpcion2 comboMoneda = null;
		for (ComboOpcion2 cm : moneda) {
			if (cm.getId().trim().equalsIgnoreCase(gasto.getMoneda())) {
				comboMoneda = cm;
				break;
			}

		}
		ComboOpcion2 comboComprobante = null;
		for (ComboOpcion2 cf : comprobante) {
			if (cf.getDescripcion().substring(0,1).equalsIgnoreCase(gasto.getComprobante().substring(0,1))) {
				comboComprobante = cf;
				break;
			}

		}
		renForm.setGasto(comboGastos.getId());
		renForm.setFechaGasto(gasto.getFechagastos());
		renForm.setMoneda(comboMoneda.getId());
		renForm.setTipoComprobante(comboComprobante.getId());
		renForm.setGlg(gasto.getIdGasto());
		renForm.setMonto(gasto.getMonto());
		renForm.setMontoMaximo(gasto.getMonto());
		renForm.setObservacionGasto(gasto.getObservacionGasto());

		if (request.getParameter("tieneCupon").equalsIgnoreCase("1")){
			renForm.setImporteCupon(gasto.getMonto().trim());
			request.setAttribute("conCupon","1");

		}
		else{
			renForm.setCupCred("");
			renForm.setCupDeb("");
			renForm.setCupon("");
			renForm.setCuponesCheck("");
			renForm.setDescCupon("");
			renForm.setImporteCupon("");
			renForm.setNroTarjeta("");
		}
		if (request.getParameter("listadoAprob").equalsIgnoreCase("1")) {
			request.setAttribute("listadoAprob", "1");
		}
		renForm.setUsuarioRend(request.getParameter("user"));
		renForm.setGlg(request.getParameter("glg"));
		if (request.getParameter("opcion").equalsIgnoreCase("MODI")){
			request.setAttribute("opcionTitulo", "1");
		}
		return mapping.findForward("success");
}
}