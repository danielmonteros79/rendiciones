package com.sa.action;

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

import com.sa.entities.ComboMotivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.CuadroFiltroForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;

public class CuadroGeneralAction extends RestriccionTransaccionAction{
	private static final Log log = LogFactory.getLog(CuadroGeneralAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario user = ((Usuario) request.getSession().getAttribute("userWorking"));
//		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action AprobacionAction. Usuario ("+user.getIdUser()+")");

		CuadroFiltroForm formFiltro = (CuadroFiltroForm) form;
		formFiltro.reset(mapping, request);
		RendicionesService motivoservice = new RendicionesService(samClient);

		// Service carga Combo de Motivos
		List<ComboMotivo> motivo = motivoservice.getMotivoRendiciones("4",user.getIdUser());
		request.setAttribute("ComboMotivo", motivo);
		// Service carga Listado de Rendiciones pendientes de aprobacion
		try{
//		List<Rendicion> rendicion = aprobacionesservice.getAprobacionesPendientes("", "", "",request.getParameter("glg"), user.getIdUser());
		
		}
		catch (Exception e) {
			request.setAttribute("messageConsulta","ERROR:"+ e.getCause().getMessage());	
		}
		return mapping.findForward("success");
	}
}