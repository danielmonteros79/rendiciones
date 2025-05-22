package com.sa.decorator.parametros;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.decorator.SumTableDecorator;
import com.sa.entities.parametros.ParametroAlerta;

public class ParametrosAlertasTableDecorator extends SumTableDecorator {
	@Override
	protected String getVerLink() {
		return "";
	}

	@Override
	protected String getEditarLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		ParametroAlerta alerta = (ParametroAlerta) this.getCurrentRowObject();
		String ts = alerta.getTimeStamp().replace("-", "").replace(".", "");
		
		String form = "<form method='post' id='edit_" + alerta.getCodMotivo() + alerta.getCodGasto() + ts + "' action='" 
			+ contextPath + "/parametrosAlertasDetalle.do' style='display:none;'>"
			+ "<input type='hidden' name='codMotivo' value='" + alerta.getCodMotivo() + "'/>"
			+ "<input type='hidden' name='codGasto' value='" + alerta.getCodGasto() + "'/>"
			+ "<input type='hidden' name='codAlerta' value='" + alerta.getId() + "'/>"
			+ "<input type='hidden' name='timeStamp' value='" + alerta.getTimeStamp() + "'/>"
			+ "<input type='hidden' name='accion' value='modificacion'/>"
		+ "</form>";
		String imgTag = "<img src='" + contextPath + "/images/iconos/editar.png' alt='Modificar' title='Modificar' border='0'/>";
		
		String editarLink = "<a href='#' onclick='modificarAlerta(\"" + alerta.getCodMotivo() + alerta.getCodGasto() + ts + "\")'>" + imgTag + "</a>";
		
		return form + editarLink;
	}

	@Override
	protected String getBorrarLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		ParametroAlerta alerta = (ParametroAlerta) this.getCurrentRowObject();
		String ts = alerta.getTimeStamp().replace("-", "").replace(".", "");
		
		String form = "<form method='post' id='delete_" + alerta.getCodMotivo() + alerta.getCodGasto() + ts + "' action='" 
			+ contextPath + "/parametrosAlertasDetalle.do' style='display:none;'>"
			+ "<input type='hidden' name='codMotivo' value='" + alerta.getCodMotivo() + "'/>"
			+ "<input type='hidden' name='codGasto' value='" + alerta.getCodGasto() + "'/>"
			+ "<input type='hidden' name='codAlerta' value='" + alerta.getId() + "'/>"
			+ "<input type='hidden' name='timeStamp' value='" + alerta.getTimeStamp() + "'/>"
			+ "<input type='hidden' name='accion' value='baja'/>"
		+ "</form>";
		String imgTag = "<img src='" + contextPath + "/images/iconos/borrar.png' alt='Eliminar' title='Eliminar' border='0'/>";
		String borrarLink = "<a href='#' onclick='eliminarAlerta(\"" + alerta.getCodMotivo() + alerta.getCodGasto() + ts + "\")'>" + imgTag + "</a>";
		
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