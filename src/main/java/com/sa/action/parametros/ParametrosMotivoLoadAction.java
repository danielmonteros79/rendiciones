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
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosMotivoFiltroForm;
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
		System.out.println(request.getParameter("codigo") + " Codigo aaah");
		String codMotivo = "";
		int paginado = 0;
		List<ParametroMotivo> motivosTotales = new ArrayList<ParametroMotivo>();
		boolean pagina = true;
		if (request.getParameter("codigo") != null && !request.getParameter("codigo").trim().equals(""))
			codMotivo = String.format("%04d", Integer.parseInt(request.getParameter("codigo")));
		
		while(pagina){
			List<ParametroMotivo> motivos = service.getMotivos(codMotivo, this.sessionUserWorking.getIdUser(), "0" + paginado);
			for (ParametroMotivo parametroMotivo : motivos) {
				if(parametroMotivo.getCodSup().trim().equalsIgnoreCase("PSUP") || parametroMotivo.getCodSup().equalsIgnoreCase("SUPER")){
					parametroMotivo.setCodSup("SI");
				} if (parametroMotivo.getCodAprobacionGlg().equalsIgnoreCase("MONTO") || parametroMotivo.getCodAprobacionGlg().trim().equalsIgnoreCase("PGLG")) {
					parametroMotivo.setCodAprobacionGlg("SI");
				} if (parametroMotivo.getCodFirma().equalsIgnoreCase("MONTO") || parametroMotivo.getCodFirma().equalsIgnoreCase("PFIRM")) {
					parametroMotivo.setCodFirma("SI");
				} if (parametroMotivo.getEstado().equalsIgnoreCase("A")) {
					parametroMotivo.setEstado("ACTIVO");
				} if (parametroMotivo.getEstado().equalsIgnoreCase("I")){
					parametroMotivo.setEstado("INACTIVO");
				}
				//Valida si es el último motivo existente
				if(!motivos.isEmpty() && motivos.get(motivos.size() - 1).equals(parametroMotivo)){
					System.out.println("Último motivo encontrado");
					if (parametroMotivo.getLastElement().equalsIgnoreCase("N")){
						paginado++;
					}
					else{
						pagina = false;
					}
				}
				motivosTotales.add(parametroMotivo);
			}
		}
		
		request.setAttribute("motivos", motivosTotales);
		this.message = service.getMsgAviso();
		
		return mapping.findForward("parametrosMotivoFiltro");
	}
	
	
}