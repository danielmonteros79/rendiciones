package com.sa.decorator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.entities.Rendicion;

public class CierreOrdenDePagoTableDecorator extends SumTableDecorator{
	@Override
	protected String getVerLink() {
		return "";
	}
	
	@Override
	protected String getBorrarLink() {
		return "";
	}
	
	protected String getEditarLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		Rendicion rendicion = (Rendicion) this.getCurrentRowObject();
		
		String imgTag = "<i class=\"bbva-icon icon-coronita_search\" data-toggle=\"tooltip\" title=\"Ver\"></i>";
		String link = "<a href=\"" + contextPath + "/cierreDetalle.do?codigo=" + rendicion.getId() + "&usuarioRend=" + rendicion.getUsuarioRendicion() + "\">" +
				imgTag + "</a>";

		return link;
	}
	
	protected String getThubanLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String linkThuban = (String) request.getSession().getServletContext().getAttribute("rendicion.link.thuban");
		Rendicion rend = (Rendicion) this.getCurrentRowObject();

		String imgTag = "<i class=\"bbva-icon icon-uniE0D2 fa-lg\" data-toggle=\"tooltip\" title=\"Thuban\"></i>";
		String link = "<a href=\"#a\" class=\"text-gray\" onclick=\"showThuban('" + linkThuban + rend.getId() + "')\">" + imgTag + "</a>";

		return link;
	}
	
	protected String getJournalLink() {
		Rendicion rend = (Rendicion) this.getCurrentRowObject();

		String imgTag = "<i class=\"bbva-icon icon-coronita_bookstore fa-lg\" data-toggle=\"tooltip\" title=\"Journal\"></i>";
		String link = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalJournalShow('" + rend.getId() + "')\">" + imgTag + "</a>";

		return link;
	}
	
	@Override
	public String getOpciones() {
		String editarLink = this.getEditarLink();
		String thubanLink = this.getThubanLink();
		String journalLink = this.getJournalLink();
		return editarLink + SPACER + thubanLink + SPACER + journalLink;
	}

	public String getCheck() {
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		return "<input type='checkbox' value='" + rend.getId() + "' onclick=\"clickCheckbox(" + rend.getId() + ", this)\">";
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