package com.sa.decorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.sa.entities.Cupones;

public class CuponesDecorator extends SumTableDecorator {
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
		PageContext pc = this.getPageContext();
		HttpServletRequest request = (HttpServletRequest) pc.getRequest();
		Cupones cupon = (Cupones) this.getCurrentRowObject();
		String cuponSelect = (String) request.getAttribute("cuponGastoSelect");
		String nroCupon = cupon.getNroCupon();
		String impCuponTj = cupon.getLiquidacionNeto().replace(",", ".");
		if (cupon.getDisponible() != null)
			impCuponTj = cupon.getDisponible().replace(",", ".");
		String nroTarjeta = cupon.getNroTarjeta();
		String cuponAdmin = cupon.getNroCupon();
		String cuponDeb = cupon.getNroCuponDebito();
		String fechaPresentacion = cupon.getFechaPresentacion();
		String monedaCupon = cupon.getMoneda();
		String cuponCred = cupon.getNroCuponCredito();
		String desc = cupon.getEstablecimiento();
		String esAdelanto = cupon.isAdelanto() ? "1" : "0";
		
		String imgTag = "";
		if (nroCupon.equalsIgnoreCase(cuponSelect)) {
			imgTag = "<input type=\"radio\" name=\"radioCupon\"  id=\"cuponRadio\" onclick=\"checkCupon('"
					+ nroCupon
					+ "',"
					+ impCuponTj
					+ ","
					+ nroTarjeta
					+ ",'"
					+cuponAdmin + "','"+cuponDeb+"','"+cuponCred+"','" + "','"+monedaCupon+"')\" checked/>";
		} else {
			imgTag = "<input type=\"radio\" name=\"radioCupon\" id=\"cuponRadio\" onclick=\"checkCupon('"
					+ nroCupon + "',"
					+ impCuponTj + ","
					+ nroTarjeta + ",'"
					+ cuponAdmin + "','"
					+ cuponDeb + "','"
					+ cuponCred + "','"
					+ fechaPresentacion + "','"
					+ desc.replaceAll("'", "").replaceAll("\"", "") + "','"
					+ monedaCupon + "','"
					+ esAdelanto
					+ "')\" />";
		}
		return imgTag;

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
		return null;
	}
}