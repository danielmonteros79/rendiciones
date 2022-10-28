package com.sa.decorator.parametros;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.decorator.SumTableDecorator;
import com.sa.entities.parametros.ParametroExceptuado;

public class ParametrosExceptuadosTableDecorator extends SumTableDecorator {
	@Override
	protected String getVerLink() {
		return "";
	}

	@Override
	protected String getEditarLink() {
		PageContext pc = this.getPageContext();
		String tipo=" ";
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		ParametroExceptuado excepcion = (ParametroExceptuado) this.getCurrentRowObject();
		if(excepcion.getTipo()!=null)
			tipo=excepcion.getTipo().trim();
		String form = "<form method='post' id='edit_" + excepcion.getMotivoUsuario().trim() + "' action='" 
			+ contextPath + "/parametrosExceptuadosDetalle.do' style='display:none;'>"
			+ "<input type='hidden' name='motivoUsuario' value='" + excepcion.getMotivoUsuario().trim() + "'/>"
			+ "<input type='hidden' name='marca' value='" + tipo + "'/>"
			+ "<input type='hidden' name='accion' value='modificacion'/>"
		+ "</form>";
		String imgTag = "<img src='" + contextPath + "/images/iconos/editar.png' alt='Modificar' title='Modificar' border='0'/>";
		String editarLink = "<a href='#' onclick='modificarExcepcion(\"" + excepcion.getMotivoUsuario().trim() + "\")'>" + imgTag + "</a>";
		
		return form + editarLink;
	}

	@Override
	protected String getBorrarLink() {
		PageContext pc = this.getPageContext();
		String tipo=" ";
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		ParametroExceptuado excepcion = (ParametroExceptuado) this.getCurrentRowObject();
		if(excepcion.getTipo()!=null)
			tipo=excepcion.getTipo().trim();
		String form = "<form method='post' id='delete_" + excepcion.getMotivoUsuario().trim() + "' action='" 
			+ contextPath + "/parametrosExceptuadosDetalle.do' style='display:none;'>"
			+ "<input type='hidden' name='motivoUsuario' value='" + excepcion.getMotivoUsuario().trim() + "'/>"
			+ "<input type='hidden' name='marca' value='" + tipo + "'/>"
			+ "<input type='hidden' name='accion' value='baja'/>"
		+ "</form>";
		String imgTag = "<img src='" + contextPath + "/images/iconos/borrar.png' alt='Eliminar' title='Eliminar' border='0'/>";
		String borrarLink = "<a href='#' onclick='confirmEliminarExcepcion(\"" + excepcion.getMotivoUsuario().trim() + "\")'>" + imgTag + "</a>";
		
		return form + borrarLink;
	}

	@Override
	protected String getDestinatariosLink() {
		return "";
	}

	@Override
	protected String getCuponesLink() {
		return "";
	}

	@Override
	protected String getScanLink() {
		return "";
	}

	@Override
	protected String getCaratulaLink() {
		return "";
	}
}