package com.sa.form;

import org.apache.struts.action.ActionForm;

public class CuponesForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String idRendicion;
	private String idGastoRend;
	private String nroTarjeta;
	private String cupon;
	private String cupCred;
	private String cupDeb;
	private String descCupon;
	private String importeCupon;
	private String subS;
	private String fechaD;
	private String fechaH;
	private String centroCostos;
	private String fechaPresentacion;
	private String estadoRendicion;
	private String codMotivo;
	private String moneda;
	private String esAdelanto;

	public String getIdRendicion() {
		return idRendicion;
	}

	public void setIdRendicion(String idRendicion) {
		this.idRendicion = idRendicion;
	}

	public String getIdGastoRend() {
		return idGastoRend;
	}

	public void setIdGastoRend(String idGastoRed) {
		this.idGastoRend = idGastoRed;
	}

	public String getCupones() {
		return null;
	}

	public String getNroTarjeta() {
		return nroTarjeta;
	}

	public void setNroTarjeta(String nroTarjeta) {
		this.nroTarjeta = nroTarjeta;
	}

	public String getCupon() {
		return cupon;
	}

	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	public String getCupCred() {
		return cupCred;
	}

	public void setCupCred(String cupCred) {
		this.cupCred = cupCred;
	}

	public String getCupDeb() {
		return cupDeb;
	}

	public void setCupDeb(String cupDeb) {
		this.cupDeb = cupDeb;
	}

	public String getDescCupon() {
		return descCupon;
	}

	public void setDescCupon(String descCupon) {
		this.descCupon = descCupon;
	}

	public String getImporteCupon() {
		return importeCupon;
	}

	public void setImporteCupon(String importeCupon) {
		this.importeCupon = importeCupon;
	}

	public String getSubS() {
		return subS;
	}

	public void setSubS(String subS) {
		this.subS = subS;
	}

	public void setCentroCostos(String centroCostos) {
		this.centroCostos = centroCostos;
	}

	public String getCentroCostos() {
		return centroCostos;
	}

	public String getFechaD() {
		return fechaD;
	}

	public void setFechaD(String fechaD) {
		this.fechaD = fechaD;
	}

	public String getFechaH() {
		return fechaH;
	}

	public void setFechaH(String fechaH) {
		this.fechaH = fechaH;
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setEstadoRendicion(String estadoRendicion) {
		this.estadoRendicion = estadoRendicion;
	}

	public String getEstadoRendicion() {
		return estadoRendicion;
	}

	public void setCodMotivo(String codMotivo) {
		this.codMotivo = codMotivo;
	}

	public String getCodMotivo() {
		return codMotivo;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getMoneda() {
		return moneda;
	}

	public String getEsAdelanto() {
		return esAdelanto;
	}

	public void setEsAdelanto(String esAdelanto) {
		this.esAdelanto = esAdelanto;
	}
}