package com.sa.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.CuadroGeneral;
import com.sa.entities.Usuario;
import com.sa.form.CuadroFiltroForm;
import com.sa.services.RendicionesService;

public class CuadroGeneralFiltroAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(CuadroGeneralFiltroAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CuadroFiltroForm frm = (CuadroFiltroForm) form;
		RendicionesService service = new RendicionesService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action CuadroGeneralFiltroAction. Usuario (" + user.getIdUser() + ")");
		
		request.setAttribute("ComboGlg", frm.getComboGlg());
		request.setAttribute("ComboMotivo", frm.getComboMotivo());
		request.setAttribute("Tabla", "t");

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
		String feD = frm.getFechaDesde().equals("") ? "" : df.format(formatter.parse(frm.getFechaDesde()));
		String feH = frm.getFechaHasta().equals("") ? "" : df.format(formatter.parse(frm.getFechaHasta()));
		
		List<CuadroGeneral> rendicion = new ArrayList<CuadroGeneral>();
		try {
			rendicion = service.getCuadroGeneral(frm.getOpcion(), user.getIdUser(), feD, feH, frm.getMontoDesde(),
					frm.getMontoHasta(), frm.getCodMotivo(), frm.getCodGlg(), frm.getUsuario().toUpperCase());
			request.setAttribute("Tabla", "t");
			request.setAttribute("message", service.getMsg());
		} catch (TransactionException e) {
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}
		
		request.setAttribute("Rendicion", rendicion);

		return mapping.findForward("success");
	}
}