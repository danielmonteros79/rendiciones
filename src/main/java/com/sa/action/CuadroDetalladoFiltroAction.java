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

import com.sa.entities.ComboOpcion2;
import com.sa.entities.CuadroDetallado;
import com.sa.entities.Usuario;
import com.sa.form.CuadroFiltroForm;
import com.sa.services.RendicionesService;
import com.sa.util.ParamsConstants;


public class CuadroDetalladoFiltroAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(CuadroDetalladoFiltroAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CuadroFiltroForm frm = (CuadroFiltroForm) form;
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		RendicionesService service = new RendicionesService(samClient);
		
		request.setAttribute("ComboGlg", frm.getComboGlg());
		request.setAttribute("ComboMotivo", frm.getComboMotivo());

		List<ComboOpcion2> estado = new ArrayList<>();
		List<CuadroDetallado> rendicion = new ArrayList<>();
		try {
			String message = "";
			
			estado = service.getComboOpcion2(ParamsConstants.EST_REND_OPCION, ParamsConstants.EST_REND_TABLA
					+ ParamsConstants.EST_REND_SUBTABLA + ParamsConstants.EST_REND_CODIGO, ParamsConstants.EST_REND_CANTIDAD, user.getIdUser());
			if (service.getMsg() != null)
				message += service.getMsg() + "<br>";
			
			frm.setComboEstado(estado);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			String feD = frm.getFechaDesde().equals("") ? "" : df.format(formatter.parse(frm.getFechaDesde()));
			String feH = frm.getFechaHasta().equals("") ? "" : df.format(formatter.parse(frm.getFechaHasta()));
			
			rendicion = service.getCuadroDetallado(frm.getOpcion(), feD, feH, frm.getMontoDesde(), frm.getMontoHasta(),
					frm.getCodEstado(), frm.getCodMotivo(), user.getIdUser(), frm.getCodGlg(), frm.getUsuario().toUpperCase());
			if (service.getMsg() != null)
				message += service.getMsg();
			request.setAttribute("Tabla", "t");
			
			if (!message.equals(""))
				request.setAttribute("message", message);
		} catch (TransactionException e) {
			log.error(e);
			request.setAttribute("message", "ERROR: " + e.getCause().getMessage());
		}

		request.setAttribute("ComboEstado", estado);
		request.setAttribute("CuadroDetallado", rendicion);

		return mapping.findForward("ok");
	}
}
