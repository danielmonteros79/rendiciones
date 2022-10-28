package com.sa.decorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.entities.Rendicion;
import com.sa.form.FiltrarAprobacionForm;


public class AprobacionesTableDecorator extends SumTableDecorator{
	@Override
	protected String getVerLink() {
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
		return "<input type=\"checkbox\" name=\"asignada\" value=\"on\" onclick=\"checkRendiciones(this)\" id=\"checkAprobacion\">";
	}
	
	@Override
	protected String getScanLink() {
		return "";
	}

	@Override
	protected String getCaratulaLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();

		Rendicion rendicion = (Rendicion) this.getCurrentRowObject();
		
		String verLink = "";
		if (rendicion.getIdu()!=null &&!rendicion.getIdu().equals("")
				&& rendicion.getAdea()!=null && !rendicion.getAdea().equalsIgnoreCase("")) {
			String imgTag = "<img src=\""
				+ contextPath
				+ "/images/iconos/pdf.png\" alt=\"Caratula\" title=\"Caratula\" border=\"0\" />";
		verLink = "<a href=\""
				+ contextPath
				+ "/rendicionAviso.do?generate=anymode&rnd="
				+ rendicion.getId() + "\">" + imgTag + "</a>";
		}

		return verLink;
	}

	@Override
	protected String getEditarLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		Rendicion rendicion = (Rendicion) this.getCurrentRowObject();
		request.setAttribute("usuarioRendicion", rendicion.getUsuarioRendicion().trim());
		String glg = rendicion.getGlg();
		String imgTag = "<img width='24px' height='24px' src=\"" + contextPath + "/images/search_button_32x32.png\" alt=\"Ver detalle\" title=\"Ver detalle\" border=\"0\" /></a>";
		String verLink = "<a href=\""+contextPath+"/aprobacionDetalle.do?action=aprobacionDetalle&codigo="+rendicion.getId()+"&"+"usuarioRendicion="+rendicion.getUsuarioRendicion().trim()+"&glg="+glg+"&estadoRend="+rendicion.getEstado()+"\">"+imgTag+"</a>";
		return verLink;
	}
	
	protected String getThubanLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		String linkThuban = (String) request.getSession().getServletContext().getAttribute("rendicion.link.thuban");
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		String link = "<a href=\"#\" onclick=\"showThuban('"+linkThuban+"','"+rend.getId()+"')\">" +
			"<img src=\""+contextPath+"/images/iconos/info.png\" alt=\"Thuban\" title=\"Thuban\" border=\"0\" style=\"margin-bottom:4px;\"/>";		
		return link;
//		String link = "<a href=\""+contextPath+"/rendicionAviso.do?action=mostrarPantalla"+"&codigo="+rend.getId()+"&codMotivo="+rend.getCodMotivo()+"&estadoRend="+rend.getEstado()+"&usuarioRend="+rend.getUsuarioRendicion()+ "\">" +
//				"<img src=\""+contextPath+"/images/iconos/info.png\" alt=\"Thuban\" title=\"Thuban\" border=\"0\" style=\"margin-bottom:4px;\"/>";
//		return link;
	}
	
	protected String getJournalLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		String link = "<a href=\"#\" onclick=\"showJournal('" + rend.getId() + "')\">" +
			"<img width='20px' height='20px' style='margin-bottom:2px;' src=\"" + contextPath + 
			"/images/iconos/journal.png\" alt=\"Journal\" title=\"Journal\" border=\"0\" /></a>";

		return link;
	}

	@Override
	public String getOpciones() {
		String editarLink = this.getEditarLink();
		String thubanLink = this.getThubanLink();
		String journalLink = this.getJournalLink();
		return editarLink + SPACER + thubanLink + SPACER + journalLink;
	}
}