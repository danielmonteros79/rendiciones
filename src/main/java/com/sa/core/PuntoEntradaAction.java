package com.sa.core;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.bbva.web.struts.sam.ISAMWebAction;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;
import com.sa.form.LoginForm;
import com.sa.services.AprobacionesService;
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
	private static final String FAILURE = "failure"; 
	private static final String ERRORES = "errores"; 
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
	        SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    log.info("Ingreso a la aplicación");
	    System.setProperty("http.proxyHost", "");
	    System.setProperty("http.proxyPort", "");

	    String ivUser = request.getHeader("iv-user") != null
	            ? request.getHeader("iv-user")
	            : (String) request.getAttribute("ivUser");
	    log.info("iv-user obtenido: " + ivUser);

	    LoginForm loginForm = (LoginForm) form;
	    List<String> errorList = new ArrayList<>();

	    if (ivUser != null && (loginForm.getUsername() == null || loginForm.getUsername().trim().equals(""))) {
	        return processUserLogin(ivUser.trim().toUpperCase(), samClient, request, mapping, errorList);
	    } else {
	        return processLoginForm(loginForm, samClient, request, mapping, errorList);
	    }
	}

	private ActionForward processUserLogin(String ivUser, SAMWebClient samClient, HttpServletRequest request,
	        ActionMapping mapping, List<String> errorList) throws TransactionException {
	    samClient.setAttribute("userLoggin", ivUser);
	    UsuarioService serviceUsuario = new UsuarioService(samClient);
	    Usuario usuario = serviceUsuario.obtenerDelegadosUsuario(ivUser);
	    if (usuario == null) {
	        errorList.add("Usuario inexistente");
	        request.setAttribute(ERRORES, errorList);
	        return mapping.findForward(FAILURE);
	    }
	    setUsuarioAttributes(request, usuario);
	    return processUserPermissions(usuario, samClient, request, mapping);
	}

	private ActionForward processLoginForm(LoginForm loginForm, SAMWebClient samClient, HttpServletRequest request,
	        ActionMapping mapping, List<String> errorList) throws TransactionException {
	    String username = loginForm.getUsername();
	    String password = loginForm.getPassword();

	    if (username != null && !"".equals(username.trim())) {
	        if (password != null && !"".equals(password.trim())) {
	            samClient.setAttribute("userLoggin", username.trim().toUpperCase());
	            UsuarioService serviceUsuario = new UsuarioService(samClient);
	            Usuario usuario = serviceUsuario.obtenerDelegadosUsuario(username.toUpperCase());
	            if (usuario == null) {
	                errorList.add("Usuario inexistente");
	                request.setAttribute(ERRORES, errorList);
	                return mapping.findForward(FAILURE);
	            }
	            setUsuarioAttributes(request, usuario);
	            return processUserPermissions(usuario, samClient, request, mapping);
	        } else {
	            errorList.add("Por favor, ingresar Contraseña");
	            request.setAttribute(ERRORES, errorList);
	            return mapping.findForward(FAILURE);
	        }
	    } else {
	        errorList.add("Por favor, ingresar Usuario");
	        request.setAttribute(ERRORES, errorList);
	        return mapping.findForward(FAILURE);
	    }
	}

	private ActionForward processUserPermissions(Usuario usuario, SAMWebClient samClient, HttpServletRequest request,
	        ActionMapping mapping) {
	    if (usuario.getTipoPerfil().getPantalla().contains("Aprobacion")) {
	        AprobacionesService aprobacionesService = new AprobacionesService(samClient);
	        for (int i = 1; i < 5; ++i) {
	            try {
	                aprobacionesService.getAprobacionesPendientes("", "", "", Integer.toString(i), usuario.getIdUser());
	                usuario.getGlgAprobacion().add(i);
	            } catch (Exception var13) {
	                // Handle exception
	            }
	        }
	    }

	    log.info("Se obtuvo el usuario: " + usuario.getIdUser());
	    return mapping.findForward("success");
	}

	private void setUsuarioAttributes(HttpServletRequest request, Usuario usuario) {
	    request.getSession().setAttribute("userWorking", usuario);
	    request.getSession().setAttribute("usuario", usuario);
	}

}