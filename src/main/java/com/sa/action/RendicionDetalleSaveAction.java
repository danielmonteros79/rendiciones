package com.sa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.entities.Usuario;
import com.sa.form.RendicionDetalleForm;
import com.sa.services.PagosService;
import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class RendicionDetalleSaveAction extends RestriccionTransaccionAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action RendicionDetalleSaveAction. Usuario ("+user.getIdUser()+")");
		RendicionDetalleForm renForm = (RendicionDetalleForm) form;
		renForm.reset(mapping, request);
		PagosService serviceGastos = new PagosService(samClient);
		Integer idGasto = null;
		String idRendicion = renForm.getIdRendicion();

		log
				.info("Se llama al servicio que guarda los datos del gasto que fue creado");
		String opcion = request.getParameter("opcion");
		String idG = "";
		if (opcion.equalsIgnoreCase("MODI")) {

			idG = renForm.getGlg();
		} else {
			idG = "";

		}
		try{
//			String nroCuit=renForm.getCuit1()+renForm.getCuit2()+renForm.getCuit3();
//			System.out.println(nroCuit);
		idGasto = serviceGastos.altaModifGasto(opcion, idG, renForm
				.getIdRendicion(), renForm.getMoneda(), renForm
				.getTipoComprobante(), renForm.getTipoFactura(), renForm.getFactura(), renForm.getCuit(), renForm.getGasto(), renForm.getMonto(),
				renForm.getFechaGasto(), renForm.getCodMotivo(), renForm
						.getCentroCostos(), renForm.getCupCred(), renForm
						.getCupDeb(), renForm.getCupon(), renForm
						.getDescCupon(), renForm.getImporteCupon(), renForm
						.getNroTarjeta(), renForm.getObservacionGasto());
		if (idRendicion != null && !idRendicion.equalsIgnoreCase("")) {
			idRendicion = String.format("%016d", Integer.parseInt(idRendicion));
		}
		request.setAttribute("messageModifTCJP",
				"OK: SE GUARDO CORRECTAMENTE EL GASTO "

		);
	} catch (Exception e) {
		// TODO: handle exception
		log.error(e);
		request.setAttribute("messageModifTCJP",
				"ERROR AL GUARDAR EL GASTO: " + e.getMessage());
	}
		String cod_det_oblig = (renForm.getGasto().substring(59, renForm
				.getGasto().length()));

		if (cod_det_oblig.equalsIgnoreCase("S")) {
			// request.setAttribute("formNuevoGasto", renForm);
			String attr = "rnd="
					+ idRendicion
					+ "&rndg="
					+ idGasto
					+ "&rndm="
					+ renForm.getCodMotivo()
					+ "&tipoEntrada=2&codObserv="
					+ renForm.getGasto().substring(55,
							(renForm.getGasto().length() - 1)) + "&codGasto="
					+ renForm.getGasto().substring(0, 4)+"&estadoRend="+renForm.getEstadoRendicion();
			// request.setAttribute("tipoEntrada", "1");
			// codObserv
			ActionForward filtroForward = new ActionForward(
					"/descripcionObligatoriaPopup.do?" + attr);
			return filtroForward;
			// return mapping.findForward("success");
		}
		if (idGasto != null) {
			request.setAttribute("trxOk", "1");
			request.setAttribute("codigo", idRendicion);
			return mapping.findForward("success");
		} else {
			request.setAttribute("trxFail", 1);
			return null;

			// throw new
			// TransactionException("El idGasto retorno NULL. Gasto no generado, error en trx");
		}
	}

}

