package com.sa.decorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.decorator.SumTableDecorator;
import com.sa.entities.CuadroDetallado;
import com.sa.entities.parametros.ParametroMotivo;

public class CuadroDetalladoTableDecorator extends SumTableDecorator {
	@Override
	protected String getVerLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();

		CuadroDetallado rendicion = (CuadroDetallado) this.getCurrentRowObject();
		String imgTag = "<img src=\""
				+ contextPath
				+ "/images/iconos/ver.png\" alt=\"Ver\" title=\"Ver\" border=\"0\" />";
	
		String verLink = 
			"<form action='rendicionDetalleGastos.do' method='post'>" + 
				"<input type='hidden' name='action' value='mostrarDetalleGastos'/>" +
				"<input type='hidden' name='codigo' value='" + rendicion.getId() + "'/>" +
				"<input type='hidden' name='codMotivo' value='" + rendicion.getCodMotivo() + "'/>" +
				"<input type='hidden' name='usuario' value='" + rendicion.getUsuario() + "'/>" +
				"<button type='submit' class='btn bbva-icon icon-coronita_search text-primary border-0 shadow-none btn-outline-none' name='submit' style='cursor:pointer' data-toggle=\"tooltip\" title=\"Ver\" </button>"  
			+ "</form>";
		
		return verLink;
	}

	@Override
	protected String getEditarLink() {
		return "";
	}

	@Override
	protected String getBorrarLink() {
		return "";
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