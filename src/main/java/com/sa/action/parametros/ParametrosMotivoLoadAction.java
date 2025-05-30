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
	        
	        for (ParametroMotivo parametroMotivo : motivos) {
	            String codigo = parametroMotivo.getCodigo() != null ? parametroMotivo.getCodigo().toLowerCase() : "";
	            String descripcion = parametroMotivo.getDescripcion() != null ? parametroMotivo.getDescripcion().toLowerCase() : "";
	            String buscado = codigoParam.trim().toLowerCase();
	            
	            // Si es búsqueda por texto, normalizar texto removiendo tildes para la comparación
	            if (isTextSearch) {
	                String codigoNormalizado = removerTildes(codigo);
	                String descripcionNormalizada = removerTildes(descripcion);
	                String buscadoNormalizado = removerTildes(buscado);
	                
	                if (!(codigoNormalizado.contains(buscadoNormalizado) || descripcionNormalizada.contains(buscadoNormalizado))) {
	                    continue;
	                }
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
	    }
	    
	    request.setAttribute("motivos", motivosTotales);
	    this.message = service.getMsgAviso();
	    return mapping.findForward("parametrosMotivoFiltro");
	}

	/**
	 * Método auxiliar para remover tildes y caracteres especiales
	 * @param texto El texto a normalizar
	 * @return El texto sin tildes ni caracteres especiales
	 */
	private String removerTildes(String texto) {
	    if (texto == null || texto.isEmpty()) {
	        return texto;
	    }
	    
	    String textoNormalizado = texto;
	    
	    // Reemplazar vocales con tilde
	    textoNormalizado = textoNormalizado.replace("á", "a");
	    textoNormalizado = textoNormalizado.replace("é", "e");
	    textoNormalizado = textoNormalizado.replace("í", "i");
	    textoNormalizado = textoNormalizado.replace("ó", "o");
	    textoNormalizado = textoNormalizado.replace("ú", "u");
	    textoNormalizado = textoNormalizado.replace("ü", "u");
	    
	    // Reemplazar vocales con tilde mayúsculas
	    textoNormalizado = textoNormalizado.replace("Á", "A");
	    textoNormalizado = textoNormalizado.replace("É", "E");
	    textoNormalizado = textoNormalizado.replace("Í", "I");
	    textoNormalizado = textoNormalizado.replace("Ó", "O");
	    textoNormalizado = textoNormalizado.replace("Ú", "U");
	    textoNormalizado = textoNormalizado.replace("Ü", "U");
	    
	    // Reemplazar ñ
	    textoNormalizado = textoNormalizado.replace("ñ", "n");
	    textoNormalizado = textoNormalizado.replace("Ñ", "N");
	    
	    return textoNormalizado;
	}
	
}