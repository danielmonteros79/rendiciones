package com.sa.action.parametros;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosMotivoForm;
import com.sa.services.ParametrosService;

public class ParametrosMotivoDetalleLoadAction extends RestriccionTransaccionAction{
	private static final Log log = LogFactory.getLog(ParametrosMotivoDetalleLoadAction.class);
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("dd/MM/yyyy");
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosMotivoForm frm = (ParametrosMotivoForm) form;
		ParametrosService service = new ParametrosService (samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosNuevoMotivoLoadAction. Usuario ("+user.getIdUser()+")");
		
		String accionJson = request.getParameter("accionJson");
		if ("borrarCentroCosto".equals(accionJson))
			return this.borrarCentroCosto(frm, Integer.parseInt(request.getParameter("index")));
		else if ("agregarCentroCosto".equals(accionJson))
			return this.agregarCentroCosto(frm, response);
		
		if ("alta".equals(frm.getAccion())) {
			frm.clear();
			frm.setEstado("A");
		} else
			this.motivoToForm(frm, service.getMotivos(frm.getCodigo(), user.getIdUser(), "").get(0));
		
		ParametroMotivo motivo = service.getMotivos(frm.getCodigo(), user.getIdUser(), "").get(0);
		String codigoMotivo = StringEscapeUtils.escapeHtml4(motivo.getCodigo() != null ? motivo.getCodigo() : "");
		String descripcionMotivo = StringEscapeUtils.escapeHtml4(motivo.getDescripcion() != null ? motivo.getDescripcion() : "");

		request.getSession().setAttribute("cod_motivo", codigoMotivo);
		request.getSession().setAttribute("descripcion_motivo", descripcionMotivo);
		request.getSession().setAttribute("desc_motivo", descripcionMotivo);

		return mapping.findForward(frm.getAccion());
	}
	
	private void motivoToForm(ParametrosMotivoForm frm, ParametroMotivo motivo) {
		frm.setCodigo(motivo.getCodigo());
		frm.setDescripcion(motivo.getDescripcion());
		frm.setIdGlg(motivo.getIdGlg());
		frm.setIdCentroCostos(motivo.getIdCentroCostos());
		frm.setEstado(motivo.getEstado());
		frm.setCodSup(motivo.getCodSup());
		frm.setCodFirma(motivo.getCodFirma());
		frm.setCodAprobacionGlg(motivo.getCodAprobacionGlg());
		frm.setOscar(motivo.getOscar());
		frm.setIdNivCarga(motivo.getIdNivCarga());
		frm.setIdNivAutoriz(motivo.getIdNivAutoriz());
		frm.setMaInclExcl(motivo.getMaInclExcl());
		frm.setIdOperEspe(motivo.getIdOperEspe());
		frm.setMeDiasInterv(motivo.getMeDiasInterv());
		frm.setTxAviso(motivo.getTxAviso());
		frm.setCentrosCostoList(motivo.getCentrosCosto());
		frm.setIdCentroCostos(motivo.getIdCentroCostos());
		
		if (motivo.getFechaDesde() != null)
			frm.setFechaDesde(sdfYMD.format(motivo.getFechaDesde()));
		
		if (motivo.getFechaHasta() != null)
			frm.setFechaHasta(sdfYMD.format(motivo.getFechaHasta()));
	}

	private ActionForward borrarCentroCosto(ParametrosMotivoForm frm, int index) {
		frm.getCentrosCosto().remove(index);
		
		return null;
	}

	private ActionForward agregarCentroCosto(ParametrosMotivoForm frm, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		if (frm.getCentrosCosto().size() < 15) {
			frm.getCentrosCosto().add("");
			out.print(frm.getCentrosCosto().size() - 1);
		} else
			out.print(-1);
		
		out.close();
		return null;
	}
}