package com.sa.decorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.entities.Gastos;

public class GastosTableDecorator extends SumTableDecorator {
	
	@Override
	protected String getVerLink() {
		return "";
	}

	@Override
	protected String getEditarLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		Gastos gasto = (Gastos) this.getCurrentRowObject();
		boolean showEditar = (Boolean) request.getAttribute("showEditar");
		
		if (!showEditar)
			return "";
		
		String imgTag = "<i class=\"bbva-icon icon-coronita_contract fa-lg\" data-toggle=\"tooltip\" title=\"Editar\"></i>";
		String editarLink = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalGastoShow('" + gasto.getIdRendicion() + "', $('#estadoRend').val(), '" +
				gasto.getIdGasto() + "', '" + gasto.getCodMotivo() + "', '" + (!gasto.getCuponGasto().equalsIgnoreCase("") ? "1" : "0")  + "', '" + gasto.getCentroCostoGasto() + "')\">" + 
			imgTag + "</a>";
		return editarLink;
	}

	@Override
	protected String getBorrarLink() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		Gastos gasto = (Gastos) this.getCurrentRowObject();
		String idGasto = gasto.getIdGasto();
		boolean showBorrar = (Boolean) request.getAttribute("showBorrar");
		
		if (!showBorrar)
			return "";

		String imgTag = "<i class=\"bbva-icon icon-coronita_trash fa-lg\" data-toggle=\"tooltip\" title=\"Eliminar\"></i>";
		String borrarLink = "<a href=\"#a\" class=\"text-gray\" onclick=\"eliminarGasto('" + idGasto + "')\">" + imgTag + "</a>";
		
		return borrarLink;
	}

	public String getDatosAdicionales() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		Gastos gasto = (Gastos) this.getCurrentRowObject();
		if (gasto.getObsObligatoria().equals("") || gasto.getObs().equals("00000"))
			return "";
		
		String idRendicion = gasto.getIdRendicion();
		String codMotivo = gasto.getCodMotivo();
		String idGasto = gasto.getIdGasto();
		String codGasto = gasto.getNroGasto();
		String codObs = gasto.getObs();
		boolean readOnlyDatosAdicionales = (Boolean) request.getAttribute("readOnlyDatosAdicionales");

		String imgTag = "<i class=\"bbva-icon icon-uniE0D2 fa-lg\" data-toggle=\"tooltip\" title=\"Datos adicionales\"></i>";
		String link = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalDatosAdicionalesShow('" + idRendicion + "', '" + codMotivo + "', '" + 
				idGasto + "', '" + codGasto + "', '" + codObs + "', " + readOnlyDatosAdicionales + ")\">" + imgTag + "</a>";
		
		return link;
	}

	public String getCupones() {
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		Gastos gasto = (Gastos) this.getCurrentRowObject();

		String idRendicion = gasto.getIdRendicion();
		String idGasto = gasto.getIdGasto();
		String monto = gasto.getMonto();
		String moneda = gasto.getMoneda();
		String fechaGasto = gasto.getFechagastos();
		String codMotivo = request.getParameter("codMotivo");
		boolean readOnlyCupones = (Boolean) request.getAttribute("readOnlyCupones");
		String link = "";
	
		if (gasto.getTarjeta().equals("S")) {
			String imgTag = "<i class=\"bbva-icon icon-coronita_credit-card d-block h-1\" style=\"font-size:25px;\" data-toggle=\"tooltip\" " +
					"title=\"Ver cup&oacute;n\"></i>";
			link = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalCuponesShow('" + idRendicion + "', $('#estadoRend').val(), '" + codMotivo +
					"', null, null, '" + idGasto + "', null, null, true)\">" + imgTag + "</a>";
		} 
//		else if (!readOnlyCupones) {
//			String imgTag = "<i class=\"bbva-icon icon-coronita_charge_card d-block h-1\" style=\"font-size:18px; padding-top:4px;\" data-toggle=\"tooltip\" " + 
//					"title=\"Asignar cup&oacute;n\"></i>";
//			link = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalCuponesShow('" + idRendicion + "', $('#estadoRend').val(), '" + codMotivo +
//					"', '" + fechaGasto + "', '" + fechaGasto + "', '" + idGasto + "', '" + monto + "', '" + moneda + "', false)\">" + imgTag + "</a>";
//		}
		
		return link;
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
