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
	protected String message = "";
	protected Usuario sessionUser;
	protected Usuario sessionUserWorking;
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

		this.sessionUser = (Usuario) arg4.getSession().getAttribute(USUARIO);
		this.sessionUserWorking = (Usuario) arg4.getSession().getAttribute("userWorking");

		String user = ((Usuario) arg4.getSession().getAttribute(USUARIO)).getIdUser();
		// SE SETEA EL USUARIO LOGUEADO A SAM WEB CLIENT.
		arg3.setAttribute("userLoggin", user);

		String action = arg4.getParameter("action") == null ? "" : arg4.getParameter("action");

		log.info("Class: " + this.getClass().getName() + " - User: " + this.sessionUser.getIdUser() + " - UserWorking: " + this.sessionUserWorking.getIdUser() +
								 " - Action: " + action);

		if (action.equals("getMessage"))
			return this.getMessage(arg5);

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
	    response.setContentType("application/json; charset=UTF-8");
	    PrintWriter writer = response.getWriter();

	    Map<String, Object> safeResp = new HashMap<>();
	    for (Map.Entry<String, Object> entry : resp.entrySet()) {
	        String key = entry.getKey();
	        Object value = entry.getValue();

	        if (value instanceof String) {
	            safeResp.put(key, StringEscapeUtils.escapeJson((String) value));
	        } else {
	            safeResp.put(key, value);
	        }
	    }

	    safeResp.put(STATUS, "OK");

	    writer.print(JSONObject.fromObject(safeResp));
	    writer.flush();
	    writer.close();

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
	    this.message = "ERROR: " + resp.get(ERROR);

	    writer.print(JSONObject.fromObject(resp));
	    writer.flush();
	    writer.close();

	    return null;
	}


	protected ActionForward writeError(HttpServletResponse response, String message) throws Exception {
	    response.setContentType("application/json; charset=UTF-8");
	    PrintWriter writer = response.getWriter();

	    Map<String, Object> resp = new HashMap<>();
	    resp.put(STATUS, ERROR);
	    resp.put(ERROR, StringEscapeUtils.escapeHtml4(message));

	    writer.print(JSONObject.fromObject(resp));
	    writer.flush();
	    writer.close();

	    return null;
	}

	protected void setErrorMessage(Exception e) throws Exception {
		this.message = "ERROR: " + (e instanceof TransactionException ? e.getCause().getMessage() :
																		"Ocurri&oacute; un error al realizar la acci&oacute;n solicitada.<br>Contacte al administrador del sistema.");
	}

	protected ActionForward getMessage(HttpServletResponse response) throws Exception {
		PrintWriter writer = response.getWriter();

		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("message", this.message);

		writer.print(JSONObject.fromObject(resp));
		writer.flush();
		writer.close();

		return null;
	}

	// Getters y Setters implementados por AWSoftware para facilitar el acceso a estas variables en pruebas unitarias.
	public Usuario getSessionUser() {
		return sessionUser;
	}
	public Usuario getSessionUserWorking() {
		return sessionUserWorking;
	}

	public void setSessionUser(Usuario sessionUser) {
		this.sessionUser = sessionUser;
	}

	public void setSessionUserWorking(Usuario sessionUserWorking) {
		this.sessionUserWorking = sessionUserWorking;
	}
}

