package com.sa.core;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.bbva.web.struts.sam.ISAMWebAction;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;
import com.sa.form.LoginForm;
import com.sa.services.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PuntoEntradaAction extends ISAMWebAction {

    private static final Log log = LogFactory.getLog(PuntoEntradaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication, SAMWebClient samClient,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> errorList = new ArrayList<String>();
        log.info("Ingreso a la aplicacion");
        System.setProperty("http.proxyHost", "");
        System.setProperty("http.proxyPort", "");
        String ivUser = request.getHeader("iv-user") != null ? (String) request.getHeader("iv-user") : (String) request.getAttribute("ivUser");

        log.info("iv-user obtenido: " + ivUser);

        Usuario usuario = null;

        try {
            LoginForm loginForm = (LoginForm) form;
            if (ivUser == null || (loginForm.getUsername() != null && !loginForm.getUsername().trim().equals(""))) {
                samClient.setAttribute("userLoggin", loginForm.getUsername().trim().toUpperCase());
                if (loginForm.getUsername() == null || "".equals(loginForm.getUsername().trim())) {
                    errorList.add(new String("Por favor, ingresar Usuario"));
                    request.setAttribute("errores", errorList);
                    return mapping.findForward("failure");
                } else if (loginForm.getPassword() == null || "".equals(loginForm.getPassword().trim())) {
                    errorList.add(new String("Por favor, ingresar Contrase\u00F1a"));
                    request.setAttribute("errores", errorList);
                    return mapping.findForward("failure");
                }

                UsuarioService serviceUsuario = new UsuarioService(samClient);
                usuario = serviceUsuario.obtenerDelegadosUsuario(loginForm.getUsername().toUpperCase());

                if (usuario == null) {
                    errorList.add(new String("Usuario inexistente"));
                    request.setAttribute("errores", errorList);
                    return mapping.findForward("failure");
                }
            } else {
                samClient.setAttribute("userLoggin", ivUser.trim().toUpperCase());
                UsuarioService serviceUsuario = new UsuarioService(samClient);
                usuario = serviceUsuario.obtenerDelegadosUsuario(ivUser.trim().toUpperCase());
            }
        } catch (TransactionException e) {
            log.error(e);
            errorList.add(new String(e.getCause().getMessage()));
            request.setAttribute("errores", errorList);
            request.getSession().invalidate();
            return mapping.findForward("failure");
        } catch (Exception e) {
            log.error(e);
            errorList.add(new String("Error al ingresar"));
            request.setAttribute("errores", errorList);
            request.getSession().invalidate();
            return mapping.findForward("failure");
        }

        log.info("Se obtuvo el usuario: " + usuario.getIdUser());
        request.getSession().setAttribute("userWorking", usuario);
        request.getSession().setAttribute("usuario", usuario);

        String redirect = request.getParameter("redirect");
        if (redirect != null && !redirect.trim().equals("")) {
            if (redirect.contains("aprobacion.do?glg=1")) {
                return mapping.findForward("aprob1");
            } else if (redirect.contains("aprobacion.do?glg=2")) {
                return mapping.findForward("aprob2");
            } else if (redirect.contains("aprobacion.do?glg=3")) {
                return mapping.findForward("aprob3");
            } else if (redirect.contains("cierre.do")) {
                return mapping.findForward("cierre");
            }
        }

        if (usuario.getTipoPerfil().toString().equals("VIEW_APROBACION")) {
            return mapping.findForward("aprob1");
        } else {
            return mapping.findForward("success");
        }
    }
}
