package com.sa.decorator.parametros;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.decorator.SumTableDecorator;
import com.sa.entities.parametros.ParametroGasto;

public class ParametrosGastosTableDecorator extends SumTableDecorator {
	@Override
	protected String getVerLink() {
		return "";
	}

	@Override
	protected String getEditarLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		ParametroGasto gasto = (ParametroGasto) this.getCurrentRowObject();
		
		String form = "<form method='post' id='edit_" + gasto.getGasto() + "' action='" 
			+ contextPath + "/parametrosGastosDetalle.do' style='display:none;'>"
			+ "<input type='hidden' name='codigo' value='" + gasto.getGasto() + "'/>"
			+ "<input type='hidden' name='descripcionGasto' value='" + gasto.getDescripcionGasto().trim() + "'/>"
			+ "<input type='hidden' name='motivo' value='" + gasto.getMotivo() + "'/>"
			+ "<input type='hidden' name='accion' value='modificacion'/>"
			+ "<input type='hidden' name='back' value='false'/>"
		+ "</form>";
		String imgTag = "<img src='" + contextPath + "/images/iconos/editar.png' alt='Modificar' title='Modificar' border='0'/>";
		String editarLink = "<a href='#' onclick='modificarGasto(\"" + gasto.getGasto() + "\")'>" + imgTag + "</a>";
		
		return form + editarLink;
	}

	@Override
	protected String getBorrarLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		ParametroGasto gasto = (ParametroGasto) this.getCurrentRowObject();
		
		String form = "<form method='post' id='delete_" + gasto.getGasto() + "' action='" 
			+ contextPath + "/parametrosGastosDetalle.do' style='display:none;'>"
			+ "<input type='hidden' name='codigo' value='" + gasto.getGasto() + "'/>"
			+ "<input type='hidden' name='descripcionGasto' value='" + gasto.getDescripcionGasto().trim() + "'/>"
			+ "<input type='hidden' name='motivo' value='" + gasto.getMotivo() + "'/>"
			+ "<input type='hidden' name='accion' value='baja'/>"
			+ "<input type='hidden' name='back' value='false'/>"
		+ "</form>";
		String imgTag = "<img src='" + contextPath + "/images/iconos/borrar.png' alt='Eliminar' title='Eliminar' border='0'/>";
		String borrarLink = "<a href='#' onclick='eliminarGasto(\"" + gasto.getGasto() + "\")'>" + imgTag + "</a>";
		
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
		return null;
	}
}