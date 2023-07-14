package com.sa.decorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.entities.Rendicion;

public class AprobacionesTableDecorator extends SumTableDecorator {
	
	private static final String IMG_TAG = "<img width='25px' src='";
	
	@Override
	protected String getVerLink() {
		return "";
	}

	@Override
	protected String getBorrarLink() {
		return "";
	}

	@Override
	protected String getEditarLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		Rendicion rendicion = (Rendicion) this.getCurrentRowObject();
		
		String imgTag = "<i class=\"bbva-icon icon-coronita_search\" data-toggle=\"tooltip\" title=\"Ver\"></i>";
		String link = "<a href=\"" + contextPath + "/aprobacionDetalle.do?codigo=" + rendicion.getId() + "&glg=" + rendicion.getGlg() + "\">" + imgTag + "</a>";

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
		return "<input class='seleccionar-todo' type='checkbox' value='" + rend.getId() + "' onchange=\"clickCheckbox(" + rend.getId() + ", this)\" \"' >";
	}
	
	public String getChecks() {
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		return "";
	}
	
	public String getAlertas() {
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		String img = "";
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		
		if (rend.getAlerta().equalsIgnoreCase("1"))
			img = IMG_TAG + contextPath + "/images/iconos/alerta_riesgo_grave.png' alt='Riesgo grave' title='Riesgo grave'/>";
		else if (rend.getAlerta().equalsIgnoreCase("2"))
			img = IMG_TAG + contextPath + "/images/iconos/alerta_riesgo.png' alt='Riesgo' title='Riesgo'/>";
		else if (rend.getAlerta().equalsIgnoreCase("3"))
			img = IMG_TAG+ contextPath + "/images/iconos/alerta_incidencia_grave.png' alt='Incidencia grave' title='Incidencia grave'/>";
		else if (rend.getAlerta().equalsIgnoreCase("4"))
			img = IMG_TAG + contextPath + "/images/iconos/alerta_incidente.png' alt='Incidente' title='Incidente'/>";
		else if (rend.getAlerta().equalsIgnoreCase("5"))
			img = IMG_TAG + contextPath + "/images/iconos/alerta_anomalia.png' alt='Anomal&iacute;a' title='Anomal&iacute;a'/>";
		
		return img;
	}
	

	@Override
	protected String getDestinatariosLink() {
		return null;
	}


	@Override
	public String getScan() {
		return  this.getScanLink();
	}
	@Override
	public String getCaratula() {
		return this.getCaratulaLink();
	}
	@Override
	public String getComentarios() {
		return this.getDestinatariosLink();
		}
	
	@Override
	public String getCupones() {

		return  this.getCuponesLink();
	}


	
	
	@Override
	protected String getCuponesLink() {
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		String img = "";
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		
		if( rend.getAdea() == null || rend.getAdea().equals("") || rend.getAdea().equals("00000000000")) rend.setAlerta("0"); 
		
		else rend.setAlerta(rend.getAdea().substring(0,1));
			
		img = IMG_TAG + contextPath + "/images/iconos/alerta_riesgo_grave.png' alt='riesgo' data-toggle='tooltip' title='Riesgo'/>";
		
		String link = "<a href=\"#a\" class=\"text-gray\" onclick=\"obtenerDetalleAlerta("+rend.getId() + "," +  rend.getAdea() + ")\">" + img + "</a>";

		//String verLink = "<a href=\"" + contextPath + "/listadoAlertas.do?" + "codigo=" + rend.getId() +  "&glg=" + request.getParameter("glg") + "\">" + img + "</a>";
		return rend.getAlerta().equals("1") ? link : "";
	}
	

	@Override
	protected String getScanLink() {
		return null;
	}

	@Override
	protected String getCaratulaLink() {
		return null;
	}
	
}

	
	
	
