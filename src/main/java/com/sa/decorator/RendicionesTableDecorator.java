package com.sa.decorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.entities.Rendicion;

public class RendicionesTableDecorator extends SumTableDecorator {

	@Override
	protected String getVerLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		String contextPath = request.getContextPath();

		Rendicion rendicion = (Rendicion) this.getCurrentRowObject();
		String imgTag = "<i class=\"bbva-icon icon-coronita_search\" data-toggle=\"tooltip\" title=\"Ver\"></i>";
		String verLink = "<a href=\"" + contextPath + "/rendicionDetalleGastos.do?" + "codigo=" + rendicion.getId() + "\">" + imgTag + "</a>";
		return verLink;
	}

	@Override
	protected String getEditarLink() {
		return "";
	}

	@Override
	protected String getBorrarLink() {
		Rendicion rendicion = (Rendicion) this.getCurrentRowObject();

		String borrarLink = "";
		String idRendicion = rendicion.getId().toString();
		
		if (rendicion.getEstado().equalsIgnoreCase("PENDI")) {
			String imgTag = "<i class=\"bbva-icon icon-coronita_trash\" data-toggle=\"tooltip\" title=\"Eliminar\"></i>";
			borrarLink = "<a href=\"#a\" class=\"text-gray\" onclick=\"eliminarRendicion(" + idRendicion + ")\">" + imgTag + "</a>";
		}

		return borrarLink;
	}
 
	public String getStatusColor() {
		Rendicion rend = (Rendicion) this.getCurrentRowObject();
		String div = "";
		
		if (rend.getEstado().equalsIgnoreCase("PENDI"))
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-zero-in-a-circle.png); background-color: yellow;\" data-toggle=\"tooltip\" title=\"PENDIENTE\"> </div>";
		else if (rend.getEstado().equalsIgnoreCase("ESCAN"))
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-one-in-a-circle.png); background-color: lightblue;\" data-toggle=\"tooltip\" title=\"ESCANEADA\"> </div>";
		else if (rend.getEstado().trim().equalsIgnoreCase("PSUP"))
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-two-in-a-circle.png); background-color: lightblue;\" data-toggle=\"tooltip\" title=\"PENDIENTE SUPERVISOR\"> </div>";
		else if (rend.getEstado().equalsIgnoreCase("PFIRM"))
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-three-in-a-circle.png); background-color: lightblue;\" data-toggle=\"tooltip\" title=\"PENDIENTE FIRMA\"> </div>";
		else if (rend.getEstado().trim().equalsIgnoreCase("PGLG"))
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-four-in-circular-button.png); background-color: lightblue;\" data-toggle=\"tooltip\" title=\"PENDIENTE GLG\"> </div>";
		else if (rend.getEstado().equalsIgnoreCase("OBSER"))
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-four-in-circular-button.png); background-color: yellow;\" data-toggle=\"tooltip\" title=\"OBSERVADA\"> </div>";
		else if (rend.getEstado().equalsIgnoreCase("APROB"))
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: lightblue;\" data-toggle=\"tooltip\" title=\"APROBADA\"> </div>";
		else if (rend.getEstado().equalsIgnoreCase("ORDPG"))
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: #5cb85c;\" data-toggle=\"tooltip\" title=\"" + rend.getEstado() + "\"></div>";
		else if (rend.getEstado().equalsIgnoreCase("SUSPE"))
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: yellow;\" data-toggle=\"tooltip\" title=\"SUSPENDIDA\"> </div>";
		else if (rend.getEstado().equalsIgnoreCase("RECHA"))
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/circular-button.png); background-color: red;\" data-toggle=\"tooltip\" title=\"RECHAZADA\"> </div>";
		else
			div = "<div id=\"circulo\" style=\"background-image: url(./images/iconos/number-five-in-circular-button.png); background-color: red;\" data-toggle=\"tooltip\" title=\"" + rend.getEstado() + "\"></div>";

		return div;
	}

	@Override
	protected String getDestinatariosLink() {
		// TODO Auto-generated method stub
		return null;
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
	
		img = "<img width='25px' src='" + contextPath + "/images/iconos/alerta_riesgo_grave.png' alt='Riesgo' title='Riesgo' data-toggle='tooltip' title='Riesgo' />";
		
		String link = "<a href=\"#a\" class=\"text-gray\" onclick=\"obtenerDetalleAlerta("+rend.getId() + "," +  rend.getAdea() + ")\">" + img + "</a>";

		//String verLink = "<a href=\"" + contextPath + "/listadoAlertas.do?" + "codigo=" + rend.getId() +  "&glg=" + request.getParameter("glg") + "\">" + img + "</a>";
		return rend.getAlerta().equals("1") ? link : "";
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
