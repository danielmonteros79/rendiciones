package com.sa.action;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.ComboGasto;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.Usuario;
import com.sa.form.CuponesForm;
import com.sa.form.RendicionDetalleForm;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import com.sa.util.ParamsConstants;

public class DetallePopUpAction extends RestriccionTransaccionAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		RendicionDetalleForm renForm = (RendicionDetalleForm) form;
		CuponesForm cu = (CuponesForm) request.getAttribute("formCupones");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateCupon = null;
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");

		log.info("Entra al action DetallePopUpAction. Usuario (" + user.getIdUser() + ")");

		RendicionesService serviceCombos = new RendicionesService(samClient);
		
		String accion = request.getParameter("accion");
		if ("selectTipoGasto".equals(accion)) {
			this.selectTipoGasto(response.getWriter(), request, serviceCombos, u.getIdUser());
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().flush();
			response.getWriter().close();
			return null;
		}
		
		PagosService service = new PagosService(samClient);

		Integer idRendicion = null;
		if (request.getParameter("codigo") == null) {
			idRendicion = (Integer.parseInt((String) request.getAttribute("codigo")));
		} else {
			idRendicion = Integer.valueOf(request.getParameter("codigo"));
		}
		
		String costosDestino = null;
		if (request.getParameter("costosDestino").equals("0000")) {
			costosDestino = Integer.toString(u.getCcostos());
			renForm.setCostosDestino(costosDestino);
		} else {
			request.setAttribute("costosDestino", request.getParameter("costosDestino"));
		}
		
		request.setAttribute("estadoRend", request.getParameter("estadoRendicion"));
		request.setAttribute("idRendicion", idRendicion);
		request.setAttribute("nombreUsuario", u.getNombre());
		request.setAttribute("costos", u.getCcostos());
		String fechaD = request.getParameter("fechaD");
		String fechaH = request.getParameter("fechaH");
		request.setAttribute("fechaD", fechaD);
		request.setAttribute("fechaH", fechaH);

		log.info("Se llama al service que realiza la carga de los combos para el alta de gastos");
		
		String message = "";
		
		List<ComboOpcion2> moneda = new ArrayList<ComboOpcion2>();
		List<ComboGasto> tipoGastos = new ArrayList<ComboGasto>();
		List<ComboOpcion2> comprobante = new ArrayList<ComboOpcion2>();
		
		try {
			List<ComboOpcion2> tmpMoneda = serviceCombos.getComboOpcion2(ParamsConstants.MONEDA_OPCION, ParamsConstants.MONEDA_TABLA
					+ ParamsConstants.MONEDA_SUBTABLA + ParamsConstants.MONEDA_CODIGO, ParamsConstants.MONEDA_CANTIDAD, u.getIdUser());
			if (serviceCombos.getMsg() != null)
				message += serviceCombos.getMsg() + "<br>";

			tipoGastos = service.getComboGasto(ParamsConstants.TIPO_GASTO_OPCION, u.getIdUser(), request.getParameter("codMotivo"));
			if (service.getMsg() != null)
				message += service.getMsg() + "<br>";
			
			comprobante = serviceCombos.getComboOpcion2(ParamsConstants.COMPROBANTE_OPCION,
					ParamsConstants.COMPROBANTE_TABLA + ParamsConstants.COMPROBANTE_SUBTABLA + ParamsConstants.COMPROBANTE_CODIGO,
					ParamsConstants.COMPROBANTE_CANTIDAD, u.getIdUser(), tipoGastos.get(0).getId());
			if (serviceCombos.getMsg() != null)
				message += serviceCombos.getMsg() + "<br>";

			renForm.setIdRendicion(request.getParameter("codigo"));
			renForm.setEstadoRendicion(request.getParameter("estadoRendicion"));
			renForm.setCodMotivo(request.getParameter("codMotivo"));
			renForm.setNombreUsuario(u.getNombre());
			renForm.setCostos(u.getCcostos());
			renForm.setCodMotivo(request.getParameter("codMotivo"));
			renForm.setCentroCostos(request.getParameter("cCosto"));
			renForm.setMoneda("");
			renForm.setCupon("");
			renForm.setCupCred("");
			renForm.setCupDeb("");
			renForm.setDescCupon("");
			renForm.setImporteCupon("");
			renForm.setNroTarjeta("");
			renForm.setObservacionGasto("");
			renForm.setCuit1("");
			renForm.setCuit2("");
			renForm.setCuit3("");
			renForm.setCmbComprobante("A");
			renForm.setComprobante1("");
			renForm.setComprobante2("");

			if (cu != null) {
				request.setAttribute("conCupon", "1");
				renForm.setCupon(cu.getCupon());
				renForm.setCupCred(cu.getCupCred());
				renForm.setCupDeb(cu.getCupDeb());
				renForm.setDescCupon(cu.getDescCupon());
				renForm.setImporteCupon(cu.getImporteCupon().replace("^.*", ","));
				renForm.setNroTarjeta(cu.getNroTarjeta());
				renForm.setMonto(cu.getImporteCupon());
				renForm.setMoneda(cu.getMoneda() + " ");

				for (ComboOpcion2 mon : tmpMoneda) {
					if (mon.getId().equals(renForm.getMoneda()))
						moneda.add(mon);
				}

				if (cu.getFechaPresentacion() != null && !cu.getFechaPresentacion().equalsIgnoreCase("")) {
					dateCupon = formatter.parse(cu.getFechaPresentacion());
				}
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy ");
				String fechaCupon = "";
				if (dateCupon != null) {
					fechaCupon = df.format(dateCupon).trim();
				}
				if (!fechaCupon.equals("")) {
				}
				renForm.setFechagastos(fechaCupon);
				renForm.setEsAdelanto(cu.getEsAdelanto());
			} else {
				moneda = tmpMoneda;
			
				renForm.setMonto("");
				renForm.setFechagastos("");
				renForm.setEsAdelanto("0");
			}
			renForm.setGastos("");
			renForm.setComprobante("");
			cu = null;
		} catch (Exception e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		
		request.setAttribute("ComboMoneda", moneda);
		request.setAttribute("ComboGastos", tipoGastos);
		request.setAttribute("ComboComprobante", comprobante);
		
		if (!message.equals(""))
			request.setAttribute("message", message);

		return mapping.findForward("success");
	}
	
	@SuppressWarnings("unchecked")
	private void selectTipoGasto(PrintWriter writer, HttpServletRequest request, RendicionesService serviceCombos, String user) throws TransactionException {
		JSONArray jArray = new JSONArray();
		String codTipoGasto = request.getParameter("codTipoGasto");
		List<ComboOpcion2> comprobantes = serviceCombos.getComboOpcion2(ParamsConstants.COMPROBANTE_OPCION,
				ParamsConstants.COMPROBANTE_TABLA + ParamsConstants.COMPROBANTE_SUBTABLA + ParamsConstants.COMPROBANTE_CODIGO,
				ParamsConstants.COMPROBANTE_CANTIDAD, user, codTipoGasto);
		
		if (comprobantes != null)
			for (ComboOpcion2 opcion : comprobantes) {
				JSONObject jGroup = new JSONObject();
				jGroup.put("codigo", opcion.getId());
				jGroup.put("descripcion", opcion.getDescripcion());
	
				jArray.add(jGroup);
			}
		
		writer.print(jArray);
	}
}