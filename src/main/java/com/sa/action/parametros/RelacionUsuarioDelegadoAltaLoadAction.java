package com.sa.action.parametros;

import java.text.SimpleDateFormat;
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
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
import com.sa.util.ParamsConstants;

public class RelacionUsuarioDelegadoAltaLoadAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(RelacionUsuarioDelegadoLoadAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		SimpleDateFormat sdfYMD = new SimpleDateFormat("dd-MM-yyyy");
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		RelacionUsuarioDelegadoForm formulario = (RelacionUsuarioDelegadoForm) form;
		String opcion = request.getParameter("optn");
		log.info("Entra al action RelacionUsuarioDelegadoLoadAction. Usuario (" + user.getIdUser() + ")");
		log.info("Ingreso a pantalla para " + opcion + " de delegaci\u00f3n");
		
		if (opcion.equalsIgnoreCase("M")) {
			formulario.setOpcion(ParamsConstants.SU81_MODIFICACION);

			String idDelegacion = request.getParameter("callParam");
			if (idDelegacion != null) {
				List<ParametriaUsuarioDelegado> delegaciones = 
						(List<ParametriaUsuarioDelegado>) request.getSession().getAttribute("delegacionesActivas");

				for (ParametriaUsuarioDelegado del : delegaciones) {
					if (Integer.valueOf(idDelegacion).compareTo(del.getId()) == 0) {
						formulario.setDelegadoUser(del.getDelegadoUser());
						formulario.setDelegadoNombre(del.getDelegadoNombre());
						formulario.setDelegadoCentroCostos(del.getDelegadoCentroCostos());
						formulario.setDelegadoSector(del.getDelegadoSector());
						formulario.setInforme(del.getDelegadoInforme());
						formulario.setAccion(del.getDelegadoAccion());
						formulario.setEstado(del.getDelegadoEstado());
						formulario.setFeDesde(sdfYMD.format(del.getFeDesde()).replace("-", "/"));
						formulario.setFeDesdeOld(sdfYMD.format(del.getFeDesde()));
						formulario.setFeHasta(sdfYMD.format(del.getFeHasta()).replace("-", "/"));
						formulario.setFeHastaOld(sdfYMD.format(del.getFeHasta()));
						formulario.setFechaAlta(del.getFechaAlta());
						formulario.setUserAlta(del.getUsuarioAlta());
						break;
					}
				}
			} else {
				formulario = (RelacionUsuarioDelegadoForm) request.getAttribute("frmDelegacion");

				formulario.setFeDesdeOld(formulario.getFeDesde().replace("/", "-"));
				formulario.setFeHastaOld(formulario.getFeHasta().replace("/", "-"));
			}
		} else if (opcion.equalsIgnoreCase("A")) {
			RelacionUsuarioDelegadoForm f = (RelacionUsuarioDelegadoForm) request.getAttribute("frmDelegacion");
			if (f != null) {
				formulario = f;
				formulario.setFeDesdeOld(formulario.getFeDesde().replace("/", "-"));
				formulario.setFeHastaOld(formulario.getFeHasta().replace("/", "-"));
			} else {
				formulario.clearData();
			}
			formulario.setOpcion(ParamsConstants.SU81_ALTA);
		}

		request.setAttribute("comboAcciones", ParamsConstants.getAccionesDelegaciones());
		
		return mapping.findForward("success");
	}
}