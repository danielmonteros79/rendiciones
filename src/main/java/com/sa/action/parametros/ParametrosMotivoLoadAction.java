package com.sa.action.parametros;

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

import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.services.ParametrosService;

public class ParametrosMotivoLoadAction extends RestriccionTransaccionAction {
	private static final Log log = LogFactory.getLog(ParametrosMotivoLoadAction.class);
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
			SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
			try {
				String action = request.getParameter("action") == null ? "" : request.getParameter("action");

				if (action.equals("filtrar"))
					return this.filtrar(mapping, samClient, request, response);
				
				
				return mapping.findForward("success");
			} catch (Exception e) {
				log.error("", e);
				return writeError(response, e);
			}
	}
	
	private ActionForward filtrar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    ParametrosService service = new ParametrosService(samClient);
	    String codigoParam = request.getParameter("codigo");
	    String codMotivo = "";
	    int paginado = 0;
	    List<ParametroMotivo> motivosTotales = new ArrayList<>();
	    boolean pagina = true;
	    boolean isTextSearch = false;

	    if (codigoParam != null && !codigoParam.trim().isEmpty()) {
	        try {
	            int codInt = Integer.parseInt(codigoParam.trim());
	            codMotivo = String.format("%04d", codInt);
	        } catch (NumberFormatException e) {
	            isTextSearch = true;
	            codMotivo = "";
	        }
	    }

	    while (pagina) {
	        List<ParametroMotivo> motivos = service.getMotivos(codMotivo, this.sessionUserWorking.getIdUser(), "0" + paginado);
	        boolean seAgregoAlgunMotivo = false;

	        for (ParametroMotivo parametroMotivo : motivos) {
	            String codigo = parametroMotivo.getCodigo() != null ? parametroMotivo.getCodigo().toLowerCase() : "";
	            String descripcion = parametroMotivo.getDescripcion() != null ? parametroMotivo.getDescripcion().toLowerCase() : "";
	            String buscado = codigoParam.trim().toLowerCase();

	            if (isTextSearch && !(codigo.contains(buscado) || descripcion.contains(buscado))) {
	                continue;
	            }

	            if (parametroMotivo.getCodSup().trim().equalsIgnoreCase("PSUP") || parametroMotivo.getCodSup().equalsIgnoreCase("SUPER")) {
	                parametroMotivo.setCodSup("SI");
	            }
	            if (parametroMotivo.getCodAprobacionGlg().equalsIgnoreCase("MONTO") || parametroMotivo.getCodAprobacionGlg().trim().equalsIgnoreCase("PGLG")) {
	                parametroMotivo.setCodAprobacionGlg("SI");
	            }
	            if (parametroMotivo.getCodFirma().equalsIgnoreCase("MONTO") || parametroMotivo.getCodFirma().equalsIgnoreCase("PFIRM")) {
	                parametroMotivo.setCodFirma("SI");
	            }
	            if (parametroMotivo.getEstado().equalsIgnoreCase("A")) {
	                parametroMotivo.setEstado("ACTIVO");
	            } else if (parametroMotivo.getEstado().equalsIgnoreCase("I")) {
	                parametroMotivo.setEstado("INACTIVO");
	            }

	            motivosTotales.add(parametroMotivo);
	            seAgregoAlgunMotivo = true;
	        }

	        if (!motivos.isEmpty()) {
	            ParametroMotivo ultimo = motivos.get(motivos.size() - 1);
	            if ("N".equalsIgnoreCase(ultimo.getLastElement())) {
	                paginado++;
	            } else {
	                pagina = false;
	            }
	        } else {
	            pagina = false;
	        }

	        if (isTextSearch && !seAgregoAlgunMotivo) {
	            pagina = false;
	        }
	    }

	    request.setAttribute("motivos", motivosTotales);
	    this.message = service.getMsgAviso();

	    return mapping.findForward("parametrosMotivoFiltro");
	}

	
	
}