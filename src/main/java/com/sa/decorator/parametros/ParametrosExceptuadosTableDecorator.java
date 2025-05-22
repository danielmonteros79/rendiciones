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
		String imgTagEditar = "<i class='bbva-icon icon-coronita_contract fa-lg text-gray' style='cursor:pointer' /> </i>";
		String editarLink = "<a href='#' onclick='modificarExcepcion(\"" + excepcion.getMotivoUsuario().trim() + "\")'>" + imgTagEditar + "</a>";
		
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
		if (!excepcion.getEstado().equals("A")) {
			return "";
		}else{
			String form = "<form method='post' id='delete_" + excepcion.getMotivoUsuario().trim() + "' action='" 
			+ contextPath + "/parametrosExceptuadosDetalle.do' style='display:none;'>"
			+ "<input type='hidden' name='motivoUsuario' value='" + excepcion.getMotivoUsuario().trim() + "'/> "
			+ "<input type='hidden' name='marca' value='" + tipo + "'/>"
			+ "<input type='hidden' name='accion' value='baja'/>"
			+ "</form>";
			String imgTagEliminar = "<i class=\"bbva-icon icon-coronita_trash text-gray fa-lg\" alt='Eliminar' style='cursor:pointer'  title='Eliminar' border='0' /> </i>";
			String borrarLink = "<a href='#' onclick='confirmEliminarExcepcion(\"" + excepcion.getMotivoUsuario().trim() + "\")'>" + imgTagEliminar + "</a>";
			
			return form + borrarLink;
		}
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