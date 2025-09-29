package com.sa.action.cierre;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.action.RestriccionTransaccionAction;
import com.sa.services.CierreService;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

public class ReasignarBandejaAction extends RestriccionTransaccionAction {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
                                       SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String action = request.getParameter("action") == null ? "" : request.getParameter("action");
            if (action.equals("reasignarBandeja"))
                return this.reasignar(mapping, samClient, request, response);

            return mapping.findForward("success");
        } catch (Exception e) {
            log.error("Error en reasignación de bandeja", e);
            
            String errorMessage = e.getMessage();
            if (e.getCause() != null) {
                errorMessage = e.getCause().getMessage();
            }
            
            if (errorMessage != null && errorMessage.contains(":")) {
                errorMessage = errorMessage.substring(errorMessage.indexOf(":") + 1).trim();
            }
            errorMessage = errorMessage.replaceAll(":?\\s?[A-Z]{3}\\d{4}$", "").trim();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", errorMessage);
            return writeJson(response, errorResponse);
        }
    }

    private ActionForward reasignar(ActionMapping mapping, SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resp = new HashMap<>();
        CierreService cierreService = new CierreService(samClient);
        String userOrigen = request.getParameter("userOrigen");
        String userDestino = request.getParameter("userDestino");
        String tipoBandeja = request.getParameter("tipoBandeja");

        try {
            String serviceMessage = cierreService.reasignarBandeja(userOrigen, userDestino, tipoBandeja);
            resp.put("message", serviceMessage);
        } catch (Exception e) {
            log.error("Error al reasignar la bandeja", e);
            throw new Exception("Error en reasignación: " + e.getMessage(), e);
        }

        return writeJson(response, resp);
    }
}

