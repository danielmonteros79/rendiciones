package com.sa.core;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import com.sa.exceptions.SessionTimeOutException;

public class ManejadorExcepciones extends org.apache.struts.action.ExceptionHandler {

	public ActionForward execute(Exception exception, ExceptionConfig config, ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws ServletException {




		if (exception.getClass().equals(AccesoNoPermitidoException.class)) {
			return mapping.findForward("permisoDenegado");

		} else if (exception.getClass().equals(ServiceException.class)) {
			// error de uso de la aplicacion
			String error = exception.getMessage();
			request.setAttribute("error", error);
			return mapping.findForward("errorService");
	
		} else if (exception.getClass().equals(SessionTimeOutException.class)) {
			// error de uso de la aplicacion
			String error = exception.getMessage();
			request.setAttribute("error", error);
			request.setAttribute("errorTimeOut", "si");
			return mapping.findForward("sessionTimeOut");

		} else {
			// error de programacion
			request.setAttribute("exception", exception);
			return mapping.findForward("errorService");
		}

	}
}
