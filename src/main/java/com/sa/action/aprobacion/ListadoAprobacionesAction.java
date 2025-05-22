package com.sa.action.aprobacion;

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


import com.sa.action.RestriccionTransaccionAction;
import com.sa.entities.Rendicion;
import com.sa.services.AprobacionesService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import net.sf.json.JSONArray;


public class ListadoAprobacionesAction extends RestriccionTransaccionAction {
	    
	private static final Log log = LogFactory.getLog(ListadoAprobacionesAction.class);
    private AprobacionesService aprobacionesService;
    
    public ListadoAprobacionesAction() {
    }

    public ListadoAprobacionesAction(AprobacionesService aprobacionesService) {
        this.aprobacionesService = aprobacionesService;
    }
    
    protected void setAprobacionesService(AprobacionesService aprobacionesService) {
    	this.aprobacionesService = aprobacionesService;
    }
    
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
            String action = request.getParameter("action");
            if ("filtrar".equalsIgnoreCase(action)) {
                return this.filtrar(mapping, samClient, request, response);
            } else if ("aprobar".equalsIgnoreCase(action)) {
                return this.aprobar(mapping, samClient, request, response);
            }

            if (this.sessionUserWorking == null) {
                log.error("sessionUserWorking es null");
                return writeError(response, new Exception("Sesión no iniciada"));
            }
            
            String userId = this.sessionUserWorking.getIdUser();
            
            if (this.aprobacionesService == null) {
                System.out.println("⚠️ WARNING: `aprobacionesService` es NULL, creando una nueva instancia...");
                this.aprobacionesService = new AprobacionesService(samClient);
            }
            
            aprobacionesService.getAprobacionesPendientes("", "", "", request.getParameter("glg"), userId);

            request.setAttribute("cantRendiciones", 0);
            request.setAttribute("glg", request.getParameter("glg"));

            return mapping.findForward("success");
        } catch (Exception e) {
            log.error("Error en executeAction", e);
            System.out.println("Exception: " + e);
            return writeError(response, e);
        }
	}

	protected ActionForward filtrar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (this.sessionUserWorking == null) {
            return writeError(response, new Exception("Sesión no iniciada"));
        }

        AprobacionesService service = this.aprobacionesService;
        String alerta = request.getParameter("nroAlerta");
        String supervisado = request.getParameter("supervisado");
        
        if (supervisado == null || supervisado.isEmpty()) {
            supervisado = this.sessionUserWorking.getIdUser();
        }

        System.out.println("🔍 Valor de supervisado: [" + supervisado + "]");
        List<Rendicion> rendiciones = service.getAprobacionesPendientes(
            request.getParameter("idRendicion"),
            request.getParameter("usuario").trim().toUpperCase(),
            request.getParameter("motivo"),
            request.getParameter("glg"),
            supervisado
        );

        List<Rendicion> rendicionesFiltradas = new ArrayList<>();
        if ("1".equals(alerta)) {
            for (Rendicion r : rendiciones) {
                if (r.getAdea().startsWith("1")) {
                    rendicionesFiltradas.add(r);
                }
            }
        } else if ("0".equals(alerta)) {
            for (Rendicion r : rendiciones) {
                if ("0000000000".equals(r.getAdea()) || r.getIdu() == null) {
                    rendicionesFiltradas.add(r);
                }
            }
        } else {
            rendicionesFiltradas = rendiciones;
        }

        request.setAttribute("Rendicion", rendicionesFiltradas);
        request.setAttribute("cantRendiciones", service.getCantRendiciones());

        return mapping.findForward("aprobaciones");
    }

    private ActionForward aprobar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (this.sessionUserWorking == null) {
            return writeError(response, new Exception("Sesión no iniciada"));
        }

        String idRendicionesStr = request.getParameter("idRendiciones");
        if (idRendicionesStr == null) {
            return writeError(response, new IllegalArgumentException("Parámetro idRendiciones es requerido."));
        }

        JSONArray idRendicionesJSON = JSONArray.fromObject(idRendicionesStr);
        List<Integer> idRendiciones = new ArrayList<>();
        for (Object obj : idRendicionesJSON) {
            idRendiciones.add(Integer.parseInt(obj.toString()));
        }

        String resultado = aprobacionesService.cambiarEstadoRendiciones(
            this.sessionUserWorking.getIdUser(), idRendiciones, "APROB", null, request.getParameter("glg")
        );

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", resultado != null && resultado.equalsIgnoreCase("OPERACION EFECTUADA") ?
            "OK: APROBO CORRECTAMENTE " + (idRendiciones.size() == 1 ? "LA RENDICION" : "LAS RENDICIONES") :
            resultado);

        return writeJson(response, resp);
    }
}