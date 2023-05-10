package com.sa.action;

import com.sa.core.AccesoNoPermitidoException;
import com.sa.core.SecurityActionMapping;
import com.sa.entities.Usuario;
import com.sa.services.LoggerSUM;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class RestriccionAction extends Action {

//	private String ruta="/syscfg";
//	File agendaPropFile = new File(getSysConfPathResource(ruta + "/agenda.properties"));
//	File agendaSysProFile = new File(getSysConfPathResource(ruta + "/agenda.sys.properties"));
//	Properties agendaProp = new Properties();
//	Properties agendaSysProp = new Properties();
//	Properties propPass = new Properties();
    public static final Logger log = Logger.getLogger(RestriccionAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Usuario user = ((Usuario) request.getSession().getAttribute("usuario"));
        log.info("Entra al action RestriccionAction. Usuario (" + user.getIdUser() + ")");
//		response.setCharacterEncoding("ISO-8859-1");
//		HttpSession session = request.getSession();
//
//		Usuario usuario = (Usuario) session.getAttribute("usuario");
//		if (usuario == null) {
//			request.setAttribute("sessionVencida", new Boolean(true));
//			return mapping.findForward("login");
//		}
////		else {
////			return mapping.findForward("inicio");
////		}
//
//		// se crean los objetos globales necesarios para la aplicacion que se
//		// encapsularan en el objeto de parametros
//		XMLConfigReader xml = null;
//		Connection con = null;
////		try {
////			// produccion
////			xml = new XMLConfigReader(request);
////			con = SybaseConnection.getConnection(xml);
////		} catch (ImposibleLeerXMLException e) {
////			log.error(e);
////			try {
////				// desarrollo
////				xml = new XMLConfigReader();
////				con = SybaseConnection.getConnection(xml);
////			} catch (ImposibleLeerXMLException ex) {
////				log.error(ex);
////				throw new RuntimeException(ex);
////			}
////		}
//		LoggerSUM logger = new LoggerSUM(xml);
//		final ParametrosSUM paramsSUM = new ParametrosSUM();
//		paramsSUM.agregarParametro(ParametrosSUM.CONEXION_DB, con);
//		paramsSUM.agregarParametro(ParametrosSUM.LOGGER_SIA, logger);
//		paramsSUM.agregarParametro(ParametrosSUM.XML_CONFIGURACION, xml);
//		paramsSUM.agregarParametro(ParametrosSUM.USUARIO, usuario);
//		paramsSUM.agregarParametro(ParametrosSUM.TRANSACCION_MANAGER, session
//				.getAttribute("transacionManager"));
//		paramsSUM.agregarParametro(ParametrosSUM.GLOBAL_CONFIG, usuario);
//
//		try {
//			doRestriccion(mapping, form, request, response, logger);
//		} catch (AccesoNoPermitidoException e) {
//			
//			// redirecciona a una pagina de error mediante el manejador de
//			// excepciones
//			e.printStackTrace();
//			log.error(e);
//			logger.logException(e);
//			throw e;
//		}
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
     * @throws Exception
     */
    public abstract ActionForward executeAction(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response
    //ParametrosSUM paramsSIA
    )
            throws Exception;

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

    protected void chequearTimeOutAltamira(HttpServletRequest request) //			throws AltamiraTimeOutException 
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
