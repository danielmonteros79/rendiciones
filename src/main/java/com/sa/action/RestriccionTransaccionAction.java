package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.bbva.web.struts.sam.ISAMWebAction;
import com.sa.core.AccesoNoPermitidoException;
import com.sa.core.SecurityActionMapping;
import com.sa.entities.Usuario;
import com.sa.exceptions.SessionTimeOutException;
import com.sa.services.LoggerSUM;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class RestriccionTransaccionAction extends ISAMWebAction {

    protected static final Logger log = Logger.getLogger(RestriccionTransaccionAction.class);

    public ActionForward execute(ActionMapping arg0, ActionForm arg1, SAMWebApplication arg2, SAMWebClient arg3,
            HttpServletRequest arg4, HttpServletResponse arg5) throws Exception {

        chequearTimeOut(arg4);
//		System.out.println("Restriction: " + ((Usuario) arg4.getSession().getAttribute("usuario")).getIdUser());
        String user = ((Usuario) arg4.getSession().getAttribute("usuario")).getIdUser();
        // SE SETEA EL USUARIO LOGUEADO A SAM WEB CLIENT.
        arg3.setAttribute("userLoggin", user);

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
            HttpServletResponse response, LoggerSUM logger) throws AccesoNoPermitidoException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        SecurityActionMapping sam = (SecurityActionMapping) mapping;
        int permisos = usuario.getPerfil();
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

    protected void chequearTimeOut(HttpServletRequest request) throws SessionTimeOutException {
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        if (u == null) {
            request.getSession().invalidate();
            throw new SessionTimeOutException("Finaliz� tiempo en sesi�n.");

        }
    }
}
