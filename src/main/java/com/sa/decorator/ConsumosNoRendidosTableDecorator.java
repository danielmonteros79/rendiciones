package com.sa.decorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.entities.parametros.Resumen;

public class ConsumosNoRendidosTableDecorator extends SumTableDecorator {
	
	public String getEstado() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		Resumen resumen = (Resumen) this.getCurrentRowObject();
		String div = "<div>";
		String img = "";
		
		if (resumen.getEstado().equals("A DEBITAR")) {
			div = "<div class='balloon' style='color:red; font-weight:bold;' title='El consumo se debitar&aacute; en caso de no rendirse'>";
			img = "&nbsp;&nbsp;&nbsp;"
				+ "<img class='blink' style='width:18px;vertical-align:middle;' src='" + contextPath + "/images/Warning_48x48.png' />";
		} else if (resumen.getEstado().equals("EN PROCESO")) {
			div = "<div class='balloon' title='Nro. Rendici&oacute;n: " + resumen.getIdRendicion() + "'>";
		}
		
		return div + resumen.getEstado() + img + "</div>";
	}
	
	@Override
	protected String getVerLink() {
		return "";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getCuponesLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getScanLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getCaratulaLink() {
		// TODO Auto-generated method stub
		return null;
	}
}