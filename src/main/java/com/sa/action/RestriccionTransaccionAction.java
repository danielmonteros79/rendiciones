package com.sa.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.core.AccesoNoPermitidoException;
import com.sa.entities.Usuario;
import com.sa.exceptions.SessionTimeOutException;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.bbva.web.struts.sam.ISAMWebAction;
import ar.com.itrsa.sam.TransactionException;
import net.sf.json.JSONObject;
import org.apache.commons.text.StringEscapeUtils;

public abstract class RestriccionTransaccionAction extends ISAMWebAction {
	protected static final Logger log = Logger.getLogger(RestriccionTransaccionAction.class);
	
	// Thread-local storage for session users to avoid mutable instance fields
	private static final ThreadLocal<Usuario> threadLocalSessionUser = new ThreadLocal<>();
	private static final ThreadLocal<Usuario> threadLocalSessionUserWorking = new ThreadLocal<>();

	private static final String ERROR = "error";
	private static final String STATUS = "status";
	private static final String USUARIO = "usuario";

	public ActionForward execute(ActionMapping arg0, ActionForm arg1, SAMWebApplication arg2, SAMWebClient arg3,
															 HttpServletRequest arg4, HttpServletResponse arg5) throws Exception {
		if (chequearTimeOut(arg4)) {
			if ("XMLHttpRequest".equals(arg4.getHeader("X-Requested-With")))
				return writeError(arg5, "Finaliz\u00f3 el tiempo de la sesi\u00f3n.");
			else
				throw new SessionTimeOutException("Finalizo tiempo en sesion.");
		}

		this.setSessionUser((Usuario) arg4.getSession().getAttribute(USUARIO));
		this.setSessionUserWorking((Usuario) arg4.getSession().getAttribute("userWorking"));

		String user = ((Usuario) arg4.getSession().getAttribute(USUARIO)).getIdUser();
		// SE SETEA EL USUARIO LOGUEADO A SAM WEB CLIENT.
		arg3.setAttribute("userLoggin", user);

		String action = arg4.getParameter("action") == null ? "" : arg4.getParameter("action");
		// Sanitize user input before logging to prevent log injection attacks
		String sanitizedAction = action.replaceAll("[\r\n\t]", "_").replaceAll("[\\p{Cntrl}]", "");

		log.info("Class: " + this.getClass().getName() + " - User: " + this.getSessionUser().getIdUser() + " - UserWorking: " + this.getSessionUserWorking().getIdUser() +
								 " - Action: " + sanitizedAction);

		if (action.equals("getMessage"))
			return this.getMessage(arg5, arg4);

		return executeAction(arg0, arg1, arg2, arg3, arg4, arg5);

	}

	/**
	 * Los Action clientes deben utilizar este metodo en lugar del execute()
	 * regular.
	 *
	 * @param actionMapping
	 * @param form
	 * @param samApplication
	 * @param samClient
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public abstract ActionForward executeAction(ActionMapping mapping, ActionForm form,
																							SAMWebApplication samApplication, SAMWebClient samClient, HttpServletRequest request,
																							HttpServletResponse response) throws Exception;

	protected void doRestriccion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
															 HttpServletResponse response) throws AccesoNoPermitidoException {

		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute(USUARIO);
//		SecurityActionMapping sam = (SecurityActionMapping) mapping;
//		int permisos = usuario.getPerfil();
		boolean puedePasar = false;

		// if (permisos == Integer.parseInt(sam
		// .getApplicationZone())) {
		puedePasar = true;
		//
		// }

		if (!puedePasar) {

			log.info("El usuario " + usuario.getIdUser() + " intento ingresar a " + request.getRequestURI()
									 + " y fue rechazado por falta de permisos.");

			throw new AccesoNoPermitidoException("El usuario " + usuario.getIdUser() + " intento ingresar a "
																							 + request.getRequestURI() + " y fue rechazado por falta de permisos.");
		}
	}

	protected void cerrarSesion(HttpServletRequest request) {
		request.getSession().invalidate();
	}

	protected boolean chequearTimeOut(HttpServletRequest request) throws SessionTimeOutException {
		Usuario u = (Usuario) request.getSession().getAttribute(USUARIO);
		if (u == null) {
			request.getSession().invalidate();
			return true;
		}

		return false;
	}

	protected ActionForward writeJson(HttpServletResponse response, Map<String, Object> resp) throws Exception {
	    Map<String, Object> sanitizedResp = new HashMap<>();
	    for (Map.Entry<String, Object> entry : resp.entrySet()) {
	        Object value = entry.getValue();
	        if (value instanceof String) {
	            sanitizedResp.put(entry.getKey(), StringEscapeUtils.escapeHtml4((String) value));
	        } else {
	            sanitizedResp.put(entry.getKey(), value);
	        }
	    }
	    sanitizedResp.put(STATUS, "OK");
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    try (PrintWriter writer = response.getWriter()) {
	        String jsonOutput = JSONObject.fromObject(sanitizedResp).toString();
	        System.out.println("Generated JSON: " + jsonOutput); // Depuración
	        writer.print(jsonOutput);
	        writer.flush();
	    }
	    return null;
	}

	protected ActionForward writeError(HttpServletResponse response, Exception e) throws Exception {
	    response.setContentType("application/json; charset=UTF-8");
	    PrintWriter writer = response.getWriter();

	    Map<String, Object> resp = new HashMap<>();
	    resp.put(STATUS, ERROR);

	    if (e instanceof TransactionException) {
	        String safeMessage = StringEscapeUtils.escapeHtml4(e.getCause().getMessage());
	        resp.put(ERROR, safeMessage);
	    } else {
	        resp.put(ERROR, "Ocurri&oacute; un error al realizar la acci&oacute;n solicitada.<br>Contacte al administrador del sistema.");
	    }
	    // Store error message in session for getMessage action
	    // Note: Consider using request attributes instead for better thread safety

	    writer.print(JSONObject.fromObject(resp));
	    writer.flush();
	    writer.close();

	    return null;
	}


	protected ActionForward writeError(HttpServletResponse response, String message) throws Exception {
	    Map<String, Object> resp = new HashMap<>();
	    resp.put(STATUS, ERROR);

	    String sanitizedMessage = StringEscapeUtils.escapeHtml4(message);
	    resp.put(ERROR, sanitizedMessage);

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    try (PrintWriter writer = response.getWriter()) {
	        writer.print(JSONObject.fromObject(resp));
	        writer.flush();
	        writer.close();
	    }

	    return null;
	}


	protected void setErrorMessage(Exception e, HttpServletRequest request) throws Exception {
		String errorMessage = "ERROR: " + (e instanceof TransactionException ? e.getCause().getMessage() :
																		"Ocurri&oacute; un error al realizar la acci&oacute;n solicitada.<br>Contacte al administrador del sistema.");
		request.getSession().setAttribute("lastErrorMessage", errorMessage);
	}

	protected ActionForward getMessage(HttpServletResponse response, HttpServletRequest request) throws Exception {
		PrintWriter writer = response.getWriter();

		Map<String, Object> resp = new HashMap<String, Object>();
		String lastMessage = (String) request.getSession().getAttribute("lastErrorMessage");
		resp.put("message", lastMessage != null ? lastMessage : "");

		writer.print(JSONObject.fromObject(resp));
		writer.flush();
		writer.close();

		return null;
	}

	// Getters y Setters implementados por AWSoftware para facilitar el acceso a estas variables en pruebas unitarias.
	public Usuario getSessionUser() {
		return threadLocalSessionUser.get();
	}
	public Usuario getSessionUserWorking() {
		return threadLocalSessionUserWorking.get();
	}

	public void setSessionUser(Usuario sessionUser) {
		threadLocalSessionUser.set(sessionUser);
	}

	public void setSessionUserWorking(Usuario sessionUserWorking) {
		threadLocalSessionUserWorking.set(sessionUserWorking);
	}

	/**
	 * Helper method for backward compatibility - sets service messages in session
	 * @param serviceMessage message from service to store
	 * @param request HTTP request to access session
	 */
	protected void setMessage(String serviceMessage, HttpServletRequest request) {
		if (serviceMessage != null && !serviceMessage.trim().isEmpty()) {
			request.getSession().setAttribute("lastErrorMessage", serviceMessage);
		}
	}

	/**
	 * Clean up ThreadLocal variables to prevent memory leaks.
	 * Should be called at the end of request processing.
	 */
	protected void cleanupThreadLocals() {
		threadLocalSessionUser.remove();
		threadLocalSessionUserWorking.remove();
	}
}

