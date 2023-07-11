package com.sa.decorator.alertas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.decorator.SumTableDecorator;
import com.sa.entities.Rendicion;

public class ListadoAlertasTableDecorator extends SumTableDecorator {
	
	@Override
	protected String getVerLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
		
		Rendicion rendicion = (Rendicion) this.getCurrentRowObject();
		
		request.setAttribute("Rendicion", rendicion);
		
		String imgTag = "<i class=\"bbva-icon icon-coronita_search\" data-toggle=\"tooltip\" title=\"Ver\"></i>";
		String verLink = "<a href=\"" + contextPath + "/detalleAlerta.do?" + "codigo=" + rendicion.getId() +  "&codMotivo=" + rendicion.getCodMotivo() + "&codUsuario=" + rendicion.getUsuarioRendicion() + "\">" + imgTag + "</a>";
		return verLink;
		
		
	}

	@Override
	protected String getBorrarLink() {
		return "";
	}

	@Override
	protected String getEditarLink() {
		return "";
	}

	protected String getThubanLink() {
		return "";
	}

	protected String getJournalLink() {
		return "";
	}
	
	public String getAlertas() {
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		String txt = "";
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();

		if (rend.getAdea().substring(0,1).equalsIgnoreCase("2"))
			txt = "SUPERA CANTIDAD MENSUAL";
		else if (rend.getAdea().substring(0,1).equals("1")) {
			txt = "SUPERA MONTO MENSUAL";
		}
		else if (rend.getAdea().substring(0,1).equalsIgnoreCase("3"))
			txt = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_incidencia_grave.png' alt='Incidencia grave' title='Incidencia grave'/>";
		else if (rend.getAdea().substring(0,1).equalsIgnoreCase("4"))
			txt = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_incidente.png' alt='Incidente' title='Incidente'/>";
		else if (rend.getAdea().substring(0,1).equalsIgnoreCase("5"))
			txt = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_anomalia.png' alt='Anomal&iacute;a' title='Anomal&iacute;a'/>";
		
		return txt;
	}
	

	@Override
	protected String getDestinatariosLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScan() {
		return "";
	}
	@Override
	public String getCaratula() {
		return "";
	}
	@Override
	public String getComentarios() {
		return "";
		}
	
	@Override
	public String getCupones() {
	
		return "";
	}

	@Override
	protected String getCuponesLink() {
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		String img = "";
		String link ="";
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();
	
//		String random = String.valueOf(Math.round(Math.random()* (2 - 1) + 1));
//		rend.setAlerta(random);

		switch(rend.getId()){
		case 1287:
			rend.setAlerta("1");
			break;
		case 1249:
			rend.setAlerta("1");
			break;
		case 1282:
			rend.setAlerta("2");
			break;
		default:
			rend.setAlerta("0");
		}
		
		if (rend.getAlerta().equalsIgnoreCase("2"))
			img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_riesgo_grave.png' data-toggle=\"tooltip\" title=\"Riesgo grave\" alt='Riesgo grave' title='Riesgo grave'/>";
		else if (rend.getAlerta().equalsIgnoreCase("1"))
			img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_riesgo.png' data-toggle=\"tooltip\" title=\"Riesgo\" alt='Riesgo' title='Riesgo'/>";
		else if (rend.getAlerta().equalsIgnoreCase("3"))
			img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_incidencia_grave.png' data-toggle=\"tooltip\" title=\"Incidencia grave\" alt='Incidencia grave' title='Incidencia grave'/>";
		else if (rend.getAlerta().equalsIgnoreCase("4"))
			img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_incidente.png' data-toggle=\"tooltip\" title=\"Incidente\"  alt='Incidente' title='Incidente'/>";
		else if (rend.getAlerta().equalsIgnoreCase("5"))
			img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_anomalia.png' data-toggle=\"tooltip\" title=\"Anomal&iacute;a\" alt='Anomal&iacute;a' title='Anomal&iacute;a'/>";
		
		link = "<a href=\"#a\" alt=\"Riesgo Alto\"  class=\"text-gray\" onclick=\"obtenerDetalleAlerta("+rend.getAlerta() + "," + rend.getId() + "," + ")\">" + img + "</a>";
		
		String verLink = "<a href=\"" + contextPath + "/listadoAlertas.do?" + "idRend=" + rend.getId() + "\">" + img + "</a>";
		return verLink;
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

	
	