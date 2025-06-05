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
	    
	    ParametrosBusqueda parametrosBusqueda = analizarParametrosBusqueda(codigoParam);
	    List<ParametroMotivo> motivosTotales = buscarMotivos(service, parametrosBusqueda);
	    
	    configurarRespuesta(request, parametrosBusqueda, motivosTotales, service);
	    return mapping.findForward("parametrosMotivoFiltro");
	}

	private ParametrosBusqueda analizarParametrosBusqueda(String codigoParam) {
	    ParametrosBusqueda params = new ParametrosBusqueda();
	    
	    if (codigoParam == null || codigoParam.trim().isEmpty()) {
	        params.codMotivo = "";
	        params.isTextSearch = false;
	        params.terminoBuscado = "";
	        return params;
	    }
	    
	    String paramTrimmed = codigoParam.trim();
	    params.terminoBuscado = paramTrimmed;
	    
	    if (paramTrimmed.matches("\\d+")) {
	        return procesarBusquedaNumerica(paramTrimmed, params);
	    } else {
	        params.isTextSearch = true;
	        params.codMotivo = "";
	        return params;
	    }
	}

	private ParametrosBusqueda procesarBusquedaNumerica(String paramTrimmed, ParametrosBusqueda params) {
	    try {
	        int codInt = Integer.parseInt(paramTrimmed);
	        
	        if (codInt < 0 || codInt > 9999) {
	            params.isTextSearch = true;
	            params.codMotivo = "";
	        } else {
	            params.codMotivo = String.format("%04d", codInt);
	            params.isTextSearch = false;
	        }
	    } catch (NumberFormatException e) {
	        params.isTextSearch = true;
	        params.codMotivo = "";
	    }
	    
	    return params;
	}

	private List<ParametroMotivo> buscarMotivos(ParametrosService service, ParametrosBusqueda parametrosBusqueda) {
	    List<ParametroMotivo> motivosTotales = new ArrayList<>();
	    int paginado = 0;
	    boolean pagina = true;
	    
	    while (pagina) {
	        try {
	            List<ParametroMotivo> motivos = service.getMotivos(
	                parametrosBusqueda.codMotivo, 
	                this.sessionUserWorking.getIdUser(), 
	                "0" + paginado
	            );
	            
	            List<ParametroMotivo> motivosFiltrados = filtrarYTransformarMotivos(motivos, parametrosBusqueda);
	            motivosTotales.addAll(motivosFiltrados);
	            
	            pagina = deberContinuarPaginacion(motivos);
	            if (pagina) {
	                paginado++;
	            }
	            
	        } catch (Exception e) {
	            manejarExcepcionBusqueda(parametrosBusqueda);
	            break;
	        }
	    }
	    
	    return motivosTotales;
	}

	private List<ParametroMotivo> filtrarYTransformarMotivos(List<ParametroMotivo> motivos, ParametrosBusqueda parametrosBusqueda) {
	    List<ParametroMotivo> motivosFiltrados = new ArrayList<>();
	    
	    for (ParametroMotivo motivo : motivos) {
	        if (cumpleCriteriosBusqueda(motivo, parametrosBusqueda)) {
	            transformarParametroMotivo(motivo);
	            motivosFiltrados.add(motivo);
	        }
	    }
	    
	    return motivosFiltrados;
	}

	private boolean cumpleCriteriosBusqueda(ParametroMotivo motivo, ParametrosBusqueda parametrosBusqueda) {
	    if (!parametrosBusqueda.isTextSearch) {
	        return true;
	    }
	    
	    String codigo = motivo.getCodigo() != null ? motivo.getCodigo().toLowerCase() : "";
	    String descripcion = motivo.getDescripcion() != null ? motivo.getDescripcion().toLowerCase() : "";
	    String buscado = parametrosBusqueda.terminoBuscado.toLowerCase();
	    
	    String codigoNormalizado = removerTildes(codigo);
	    String descripcionNormalizada = removerTildes(descripcion);
	    String buscadoNormalizado = removerTildes(buscado);
	    
	    return codigoNormalizado.contains(buscadoNormalizado) || 
	           descripcionNormalizada.contains(buscadoNormalizado);
	}

	private void transformarParametroMotivo(ParametroMotivo motivo) {
	    transformarCodSup(motivo);
	    transformarCodAprobacionGlg(motivo);
	    transformarCodFirma(motivo);
	    transformarEstado(motivo);
	}

	private void transformarCodSup(ParametroMotivo motivo) {
	    String codSup = motivo.getCodSup().trim();
	    if ("PSUP".equalsIgnoreCase(codSup) || "SUPER".equalsIgnoreCase(codSup)) {
	        motivo.setCodSup("SI");
	    }
	}

	private void transformarCodAprobacionGlg(ParametroMotivo motivo) {
	    String codAprobacion = motivo.getCodAprobacionGlg().trim();
	    if ("MONTO".equalsIgnoreCase(codAprobacion) || "PGLG".equalsIgnoreCase(codAprobacion)) {
	        motivo.setCodAprobacionGlg("SI");
	    }
	}

	private void transformarCodFirma(ParametroMotivo motivo) {
	    String codFirma = motivo.getCodFirma();
	    if ("MONTO".equalsIgnoreCase(codFirma) || "PFIRM".equalsIgnoreCase(codFirma)) {
	        motivo.setCodFirma("SI");
	    }
	}

	private void transformarEstado(ParametroMotivo motivo) {
	    String estado = motivo.getEstado();
	    if ("A".equalsIgnoreCase(estado)) {
	        motivo.setEstado("ACTIVO");
	    } else if ("I".equalsIgnoreCase(estado)) {
	        motivo.setEstado("INACTIVO");
	    }
	}

	private boolean deberContinuarPaginacion(List<ParametroMotivo> motivos) {
	    if (motivos.isEmpty()) {
	        return false;
	    }
	    
	    ParametroMotivo ultimo = motivos.get(motivos.size() - 1);
	    return "N".equalsIgnoreCase(ultimo.getLastElement());
	}

	private void manejarExcepcionBusqueda(ParametrosBusqueda parametrosBusqueda) {
	    if (!parametrosBusqueda.isTextSearch) {
	        this.message = "No se encontró el motivo con código: " + parametrosBusqueda.codMotivo;
	    }
	}

	private void configurarRespuesta(HttpServletRequest request, ParametrosBusqueda parametrosBusqueda, 
	                                List<ParametroMotivo> motivosTotales, ParametrosService service) {
	    
	    configurarMensajeNoResultados(request, parametrosBusqueda, motivosTotales);
	    request.setAttribute("motivos", motivosTotales);
	    
	    if (this.message == null || this.message.isEmpty()) {
	        this.message = service.getMsgAviso();
	    }
	}

	private void configurarMensajeNoResultados(HttpServletRequest request, ParametrosBusqueda parametrosBusqueda, 
	                                          List<ParametroMotivo> motivosTotales) {
	    
	    boolean noResultadosEnBusquedaTexto = parametrosBusqueda.isTextSearch && 
	                                         motivosTotales.isEmpty() && 
	                                         !parametrosBusqueda.terminoBuscado.isEmpty();
	    
	    if (noResultadosEnBusquedaTexto) {
	        request.setAttribute("noResultados", true);
	        request.setAttribute("terminoBuscado", parametrosBusqueda.terminoBuscado);
	        this.message = "No se encontraron motivos que contengan '" + parametrosBusqueda.terminoBuscado + "'";
	    }
	}

	// Clase auxiliar para encapsular los parámetros de búsqueda
	private static class ParametrosBusqueda {
	    String codMotivo = "";
	    boolean isTextSearch = false;
	    String terminoBuscado = "";
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