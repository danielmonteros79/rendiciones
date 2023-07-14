package com.sa.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.CierreForm;
import com.sa.services.CierreService;
import com.sa.services.RendicionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class CierreSaveAction extends RestriccionTransaccionAction{
	private static final Log log = LogFactory.getLog(CierreSaveAction.class);
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CierreForm cierreForm = (CierreForm) form;
		Usuario u = ((Usuario) request.getSession().getAttribute("userWorking"));
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action CierreLoadAction. Usuario ("+user.getIdUser()+")");

		RendicionesService motivoservice = new RendicionesService(samClient);
		CierreService gestionarOrdenService = new CierreService(samClient);
		@SuppressWarnings("unchecked")
		Enumeration<String> params = request.getParameterNames();
		List<Rendicion> rendicionesSeleccionadas = new ArrayList<>();
		cierreForm.setEstado(null);
		while (params.hasMoreElements()) {

			int i = 0;
			String param = params.nextElement();
			// Setea el estado al que se cambiara
			if (cierreForm.getEstado() == null) {
				cierreForm.setEstado(request.getParameter("estado"));
			}
			// Adquiere el id de las rendiciones seleccionadas
			//se verifica que el parametro no sea de "estado"
			if (!param.substring(0, 6).equalsIgnoreCase("estado")){
			if (param.substring(0, 11).equalsIgnoreCase("idRendicion")) {

				Rendicion selectR = new Rendicion();
				Boolean selecciono = Boolean
						.valueOf(request.getParameter("seleccionado" + param.substring(11, param.length())));
				if (selecciono) {
					selectR.setId(Integer.valueOf(request.getParameter(param)));
					rendicionesSeleccionadas.add(selectR);
					
				}
			}
			i++;
		
		}}
		cierreForm.setId(45);
		// Llama al service enviando los parametros de entrada (id de la orden
		// generada, estado de la orden de pago que se genera y listado de
		// rendiciones seleccionadas
		String gestionarOrden = gestionarOrdenService.crearOrdenDePago(cierreForm.getId().toString(),cierreForm.getEstado(),
				rendicionesSeleccionadas,u.getIdUser());
		// Recarga el combo de motivo
		List<ComboMotivo> motivo = motivoservice.getMotivoRendiciones("3",u.getIdUser());
		request.setAttribute("ComboMotivo", motivo);
	
		if(cierreForm.getEstado().equals("ORDPG")){
		request.setAttribute("displaySuccess", 1);
		}
		if (cierreForm.getEstado().equals("SUSPE")){
			request.setAttribute("displaySuccess", 2);}
		return mapping.findForward("success");

	}
}
