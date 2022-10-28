package com.sa.decorator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.entities.Rendicion;
public class CierreTableDecorator extends SumTableDecorator{
	@Override
	protected String getVerLink() {
		return "";
	}

	@Override
	protected String getEditarLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();

		Rendicion rendicion = (Rendicion) this.getCurrentRowObject();
		String imgTag = "<img width='24px' height='24px' src=\"" + contextPath + "/images/search_button_32x32.png\" alt=\"Ver detalle\" title=\"Ver detalle\" border=\"0\" /></a>";
		String verLink = "<a href=\""+contextPath+"/aprobacionDetalle.do?action=aprobacionDetalle&codigo="+rendicion.getId()+"&hide="+1+"&usuarioRendicion="+rendicion.getUsuarioRendicion()+"\">"+imgTag+"</a>";
		return verLink;
	}
	
	protected String getThubanLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		String linkThuban = (String) request.getSession().getServletContext().getAttribute("rendicion.link.thuban");
		String link = "<a href=\"#\" onclick=\"showThuban('" + linkThuban + "','" + rend.getId() + "')\">" +
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
	protected String getBorrarLink() {
		return "";
		
		
	}

	@Override
	protected String getDestinatariosLink() {

		
		
		return "";

	}
	@Override
	protected String getCuponesLink() {
		Rendicion aap = (Rendicion) getCurrentRowObject();
		
		return "<input type=\"checkbox\" name=\"asignada\" value=\"on\" onclick=\"checkRendiciones(this)\" id=\"checkCierre\">";	}
	
	@Override
	protected String getScanLink() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	protected String getCaratulaLink() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getOpciones() {
		String editarLink = this.getEditarLink();
		String thubanLink = this.getThubanLink();
		String journalLink = this.getJournalLink();
		return editarLink + SPACER + thubanLink + SPACER + journalLink;
	}
}