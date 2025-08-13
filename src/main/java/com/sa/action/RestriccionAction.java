package com.sa.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sa.core.AccesoNoPermitidoException;
import com.sa.core.SecurityActionMapping;
import com.sa.entities.Usuario;
import com.sa.exceptions.ActionExecutionException;
import com.sa.services.LoggerSUM;

public abstract class RestriccionAction extends Action {
	
	
	public static final Logger log = Logger.getLogger(RestriccionAction.class);
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionExecutionException {
		Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
		log.info("Entra al action RestriccionAction. Usuario ("+user.getIdUser()+")");

		return executeAction(mapping, form, request, response
				//paramsSUM
				);
	}

	/**
	 * Los Action clientes deben utilizar este metodo en lugar del execute()
	 * regular.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param paramsSIA
	 * @return
	 * @throws ActionExecutionException when action execution fails due to business logic errors, system errors, or validation failures
	 */
	public abstract ActionForward executeAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response
			//ParametrosSUM paramsSIA
			)
			throws ActionExecutionException;

	protected void doRestriccion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			LoggerSUM logger) throws AccesoNoPermitidoException {

		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		SecurityActionMapping sam = (SecurityActionMapping) mapping;
		int permisos = usuario.getPerfil();
		boolean puedePasar = false;
		
//			if (permisos == Integer.parseInt(sam
//					.getApplicationZone())) {
				puedePasar = true;
//
//			}

		if (!puedePasar) {
			
			log.info("El usuario "
					+ usuario.getIdUser() + " intento ingresar a "
					+ request.getRequestURI()
					+ " y fue rechazado por falta de permisos.");
			
			throw new AccesoNoPermitidoException("El usuario "
					+ usuario.getIdUser() + " intento ingresar a "
					+ request.getRequestURI()
					+ " y fue rechazado por falta de permisos.");
		}
	}
	
	protected void cerrarSesion(HttpServletRequest request) {
//		log.info("Cerrando session por timeout altamira");
//		Altamira altamira = (Altamira) request.getSession().getAttribute(
//				"altamira");
//		if (altamira != null) {
//			altamira.desconectar();
//		}
//		request.getSession().invalidate();
	}

	protected void chequearTimeOutAltamira(HttpServletRequest request)
//			throws AltamiraTimeOutException 
	{

//		DateTime fechaAltamira = (DateTime) request.getSession().getAttribute(
//				Constantes.FECHA_ACTIVIDAD_ALTAMIRA);
//		Altamira alt = (Altamira) request.getSession().getAttribute("altamira");
//		if (fechaAltamira != null){
//			if (fechaAltamira.plusSeconds(Constantes.ALTAMIRA_TIMEOUT_SEGUNDOS)
//					.isBeforeNow()) {				
//				throw new AltamiraTimeOutException();
//			} else {
//				log.info("Se actualiza la fecha ultima actividad altamira");
//				request.getSession().setAttribute(
//						Constantes.FECHA_ACTIVIDAD_ALTAMIRA, new DateTime());
//				
//			}
//		}else{
//			log.info("Se actualiza la fecha ultima actividad altamira");
//			request.getSession().setAttribute(
//					Constantes.FECHA_ACTIVIDAD_ALTAMIRA, new DateTime());
//		}
//		if (alt.timeOut()){
//			throw new AltamiraTimeOutException();
//		}
		

	}
}
