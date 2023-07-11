package com.sa.action.parametros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import net.sf.json.JSONArray;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.action.aprobacion.ListadoAprobacionesAction;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosMotivoFiltroForm;
import com.sa.services.AprobacionesService;
import com.sa.services.ParametrosService;

public class ParametrosMotivoFiltroAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosMotivoFiltroAction.class);
	
//	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
//			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ParametrosMotivoFiltroForm frm = (ParametrosMotivoFiltroForm) form;
//		ParametrosService service = new ParametrosService(samClient);
//		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
//		log.info("Entra al action ParametrosMotivoFiltroAction. Usuario ("+user.getIdUser()+")");
//		
//		String codMotivo = "";
//		if (frm.getCodigo() != null && !frm.getCodigo().trim().equals(""))
//			codMotivo = String.format("%04d", Integer.parseInt(frm.getCodigo()));
//		
//		List<ParametroMotivo> motivos = new ArrayList<ParametroMotivo>();
//		try {
//			motivos = service.getMotivos(codMotivo, user.getIdUser());
//			
//			if (request.getAttribute("message") == null)
//				request.setAttribute("message", service.getMsgAviso());
//		} catch (Exception e) {
//			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
//		}
//		
//		request.setAttribute("motivos", motivos);
//		
//		return mapping.findForward("success");
//	}
	

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String action = request.getParameter("action") == null ? "" : request.getParameter("action");

			if (action.equals("filtrar"))
				return this.filtrar(mapping, samClient, request, response);
			

			//ParametrosService service = new ParametrosService(samClient);
			//service.getMotivos("", this.sessionUserWorking.getIdUser());
			//String cantRendiciones = service.getCantRendiciones();
			//request.setAttribute("cantRendiciones", cantRendiciones.equals("") ? 0 : cantRendiciones);
			//request.setAttribute("glg", request.getParameter("glg"));
			
			return mapping.findForward("success");
		} catch (Exception e) {
			log.error("", e);
			return writeError(response, e);
		}
	}

	private ActionForward filtrar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosService service = new ParametrosService(samClient);

		List<ParametroMotivo> motivos = service.getMotivos(request.getParameter("codigo"), this.sessionUserWorking.getIdUser());
		
		request.setAttribute("motivos", motivos);
		this.message = service.getMsgAviso();
		
		return mapping.findForward("motivos");
	}
	
	
	
	
	
}