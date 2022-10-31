package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.form.LoginForm;
import com.sa.services.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PuntoEntradaAction extends RestriccionTransaccionAction {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, SAMWebApplication samApplication,
            SAMWebClient samClient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        // paramsSUM.getParametro(ParametrosSUM.CONEXION_DB);
        LoginForm loginForm = (LoginForm) form;
        List errorList = new ArrayList();
        // Chequea que el usuario no est� vac�o
        if (loginForm.getUsername() == null || "".equals(loginForm.getUsername().trim())) {

            // Mensaje de error
            errorList.add(new String("Por favor, ingresar Usuario"));
            request.setAttribute("errores", errorList);
            // Se mantiene en la p�gina de login
            return mapping.findForward("failure");

        } // Chequea que la password no est� vac�a
        else if (loginForm.getPassword() == null || "".equals(loginForm.getPassword().trim())) {
            // Mensaje de error
            errorList.add(new String("Por favor, ingresar Contrase�a"));
            request.setAttribute("errores", errorList);
            // Mantiene en la p�gina de login
            return mapping.findForward("failure");

        }

        // verificar existencia de usuario
        UsuarioService serviceUsuario = new UsuarioService();
        Usuario usuario = serviceUsuario.obtenerDelegadosUsuario(loginForm.getUsername());
        if (usuario == null) {
            // Mensaje de error
            errorList.add(new String("Usuario inexistente"));
            request.setAttribute("errores", errorList);
            // Mantiene en la p�gina de login
            return mapping.findForward("failure");
        }

//		request.getSession().setAttribute("ivUser", loginForm.getUsername());	
        request.getSession().setAttribute("userWorking", usuario);
        request.getSession().setAttribute("usuario", usuario);

//			ManagerTransaction manager = new ManagerTransaction(new SU53());
//			manager.executeTrx(samClient, null);
        return mapping.findForward("success");
    }

}
