package com.sa.decorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;

public class AprobacionDetalleTableDecorator extends SumTableDecorator {

	@Override
	protected String getVerLink() {
		return "";
	}

	@Override
	protected String getEditarLink() {
		Gastos gasto = (Gastos) this.getCurrentRowObject();
		
		String imgTag = "<i class=\"bbva-icon icon-coronita_contract fa-lg\" data-toggle=\"tooltip\" title=\"Editar\"></i>";
		String editarLink = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalGastoShow('" + gasto.getIdRendicion() + "', $('#estadoRend').val() ,'" +
				gasto.getIdGasto() + "', '" + gasto.getCodMotivo() + "', " + (!gasto.getCuponGasto().equalsIgnoreCase("") ? "1" : "0") + ")\">" + 
			imgTag + "</a>";

		return editarLink;

	}

	@Override
	protected String getBorrarLink() {
		return "";
	}
	
	public String getCupones() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		Gastos gasto = (Gastos) this.getCurrentRowObject();
		
		if (gasto.getTarjeta().equals("S")) {
			String idRendicion = gasto.getIdRendicion();
			String idGasto = gasto.getIdGasto();
			String codMotivo = request.getParameter("codMotivo");
			
			String imgTag = "<i class=\"bbva-icon icon-coronita_credit-card fa-lg\" data-toggle=\"tooltip\" title=\"Cupones\"></i>";
			String link = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalCuponesShow('" + idRendicion + "', $('#estadoRend').val(), '" + codMotivo +
					"', null, null, '" + idGasto + "', null, null, true)\">" + imgTag + "</a>";
			return link;
		} else {
			return "";}
	}
	
	public String getComentarios() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		Gastos gasto = (Gastos) this.getCurrentRowObject();
		Rendicion rend = (Rendicion) request.getAttribute("Rendicion");
		
		if (gasto.getObsObligatoria().equals("") || !gasto.getObsObligatoria().equals("S"))
			return "";
		else {
			String imgTag = "<i class=\"bbva-icon icon-uniE0D2 fa-lg\" data-toggle=\"tooltip\" title=\"Datos adicionales\"></i>";
			String link = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalDatosAdicionalesShow('" + rend.getId() + "', '" + rend.getCodMotivo() + "', '" + 
					gasto.getIdGasto() + "', '" + gasto.getNroGasto() + "', '" + gasto.getObs() + "', true)\">" + imgTag + "</a>";
			
			return link;
		}
	}

	public String getComprobante() {
		String comprobante = ((Gastos) this.getCurrentRowObject()).getComprobante();
		
		if (comprobante.equals("FACTU"))
			return "FACTURA";
		else if (comprobante.equals("MAIL-"))
			return "MAIL";
		else if (comprobante.equals("SCOMP"))
			return "SIN COMPROBANTE";
		else if (comprobante.equals("TICK-"))
			return "TICKET";
		else if (comprobante.equals("FOBL"))
			return "FACTURA OBLIGATORIA";
			
		return comprobante;
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
