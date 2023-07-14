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
		List<String> errorList = new ArrayList();
		log.info("Ingreso a la aplicacion");
		System.setProperty("http.proxyHost", "");
		System.setProperty("http.proxyPort", "");
		String ivUser = request.getHeader("iv-user") != null
				? request.getHeader("iv-user")
				: (String) request.getAttribute("ivUser");
		log.info("iv-user obtenido: " + ivUser);
		Usuario usuario = null;

		try {
			LoginForm loginForm = (LoginForm) form;
			UsuarioService serviceUsuario;
			if (ivUser != null && (loginForm.getUsername() == null || loginForm.getUsername().trim().equals(""))) {
				samClient.setAttribute("userLoggin", ivUser.trim().toUpperCase());
				serviceUsuario = new UsuarioService(samClient);
				usuario = serviceUsuario.obtenerDelegadosUsuario(ivUser.trim().toUpperCase());
			} else {
				label78 : {
					samClient.setAttribute("userLoggin", loginForm.getUsername().trim().toUpperCase());
					if (loginForm.getUsername() != null && !"".equals(loginForm.getUsername().trim())) {
						if (loginForm.getPassword() != null && !"".equals(loginForm.getPassword().trim())) {
							serviceUsuario = new UsuarioService(samClient);
							usuario = serviceUsuario.obtenerDelegadosUsuario(loginForm.getUsername().toUpperCase());
							if (usuario == null) {
								errorList.add(new String("Usuario inexistente"));
								request.setAttribute(ERRORES, errorList);
								return mapping.findForward(FAILURE);
							}
							break label78;
						}

						errorList.add(new String("Por favor, ingresar Contrase�a"));
						request.setAttribute(ERRORES, errorList);
						return mapping.findForward(FAILURE);
					}

					errorList.add(new String("Por favor, ingresar Usuario"));
					request.setAttribute(ERRORES, errorList);
					return mapping.findForward(FAILURE);
				}
			}
		} catch (TransactionException var14) {
			log.error(var14);
			errorList.add(new String(var14.getCause().getMessage()));
			request.setAttribute(ERRORES, errorList);
			request.getSession().invalidate();
			return mapping.findForward(FAILURE);
		} catch (Exception var15) {
			log.error(var15);
			errorList.add(new String("Error al ingresar"));
			request.setAttribute(ERRORES, errorList);
			request.getSession().invalidate();
			return mapping.findForward(FAILURE);
		}

		if (usuario.getTipoPerfil().getPantalla().contains("Aprobacion")) {
			AprobacionesService aprobacionesService = new AprobacionesService(samClient);

			for (int i = 1; i < 5; ++i) {
				try {
					aprobacionesService.getAprobacionesPendientes("", "", "", Integer.toString(i), usuario.getIdUser());
					usuario.getGlgAprobacion().add(i);
				} catch (Exception var13) {
					;
				}
			}
		}

		log.info("Se obtuvo el usuario: " + usuario.getIdUser());
		request.getSession().setAttribute("userWorking", usuario);
		request.getSession().setAttribute("usuario", usuario);
		return mapping.findForward("success");
	}
}