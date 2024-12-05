package com.sa.action.parametros;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosMotivoForm;
import com.sa.services.ParametrosService;

public class ParametrosMotivoSaveAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosMotivoSaveAction.class);
	private SimpleDateFormat sdfYMD = new SimpleDateFormat("dd/MM/yyyy");

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ParametrosMotivoForm frm = (ParametrosMotivoForm) form;
		ParametrosService service = new ParametrosService(samClient);
		Usuario user = (Usuario) request.getSession().getAttribute("usuario");
		log.info("Entra al action ParametrosMotivoSaveAction. Usuario (" + user.getIdUser() + ")");
		
		System.out.println("FORM SAVE ACTION: " + form.toString());
		
		String forward = "failure";
		
		if (frm.getAccion().equals("alta"))
			forward = this.alta(request, frm, service);
		else if (frm.getAccion().equals("baja"))
			forward = this.baja(request, frm, service);
		else if (frm.getAccion().equals("modificacion"))
			forward = this.modificacion(request, frm, service);
		
		return mapping.findForward(forward);
	}

	private String alta(HttpServletRequest request, ParametrosMotivoForm frm, ParametrosService service) throws ParseException {
		String ret = "errorAlta";
		
		try {
			ParametroMotivo motivo = formToMotivo(frm);
			String msg = service.altaMotivo(motivo);
			request.setAttribute("message", "OK: " + msg);
			ret = "success";
		} catch (TransactionException e) {
			e.printStackTrace();
			log.error(e);
			request.setAttribute("message", e.getCause().getMessage());
		} catch (UnsupportedEncodingException e) {
		    log.error("Error de codificación al procesar los datos: ", e);
		    request.setAttribute("message", "Error de codificación al procesar los datos. Intente nuevamente.");
		} catch (Exception e) {
		    log.error("Error inesperado: ", e);
		    request.setAttribute("message", "Ocurrió un error inesperado.");
		}
		
		return ret;
	}

	private String baja(HttpServletRequest request, ParametrosMotivoForm frm, ParametrosService service) throws ParseException {
		String ret = "errorBaja";
		
		try {
			String msg = service.bajaMotivo(frm.getCodigo());
			request.setAttribute("message", "OK: " + msg);
			ret = "success";
		} catch (TransactionException e) {
			e.printStackTrace();
			log.error(e);
			request.setAttribute("message", e.getCause().getMessage());
		}
		
		return ret;
	}

	private String modificacion(HttpServletRequest request, ParametrosMotivoForm frm, ParametrosService service) throws ParseException {
		String ret = "errorModificacion";
		ParametroMotivo motivo = formToMotivo(frm);
		
		try {
			String msg = service.modificacionMotivo(motivo);
			request.setAttribute("message", "OK: " + msg);
			ret = "success";
		} catch (TransactionException e) {
			e.printStackTrace();
			log.error(e);
			request.setAttribute("message", e.getCause().getMessage());
		}
		
		return ret;
	}
	
	private ParametroMotivo formToMotivo(ParametrosMotivoForm frm) throws ParseException {
		ParametroMotivo motivo = new ParametroMotivo();
		
		motivo.setCodigo(frm.getCodigo());
		motivo.setDescripcion(frm.getDescripcion());
		motivo.setEstado(frm.getEstado());
		motivo.setIdGlg(frm.getIdGlg());
		motivo.setCodAprobacionGlg(frm.getCodAprobacionGlg());
		motivo.setIdCentroCostos(frm.getIdCentroCostos());
		motivo.setMaInclExcl(frm.getMaInclExcl());
		motivo.setCodSup(frm.getCodSup());
		motivo.setCodFirma(frm.getCodFirma());
//		motivo.setMeAviso(frm.getMeAviso());
		motivo.setOscar(frm.getOscar());
		motivo.setIdNivCarga(frm.getIdNivCarga());
		motivo.setIdNivAutoriz(frm.getIdNivAutoriz());
		motivo.setTxAviso(frm.getTxAviso());
		motivo.setIdOperEspe(frm.getIdOperEspe());
		motivo.setMeDiasInterv(frm.getMeDiasInterv());
		motivo.setCentrosCosto(frm.getCentrosCosto());
		motivo.setIdCentroCostos(frm.getIdCentroCostos());
		
		if (frm.getFechaDesde() != null && !frm.getFechaDesde().trim().equals(""))
       		motivo.setFechaDesde(sdfYMD.parse(frm.getFechaDesde()));
		
		if (frm.getFechaHasta() != null && !frm.getFechaHasta().trim().equals(""))
			motivo.setFechaHasta(sdfYMD.parse(frm.getFechaHasta()));
		
		return motivo;
	}
}