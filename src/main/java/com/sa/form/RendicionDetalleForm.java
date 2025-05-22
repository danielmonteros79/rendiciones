package com.sa.form;

import org.apache.struts.action.ActionForm;

public class RendicionDetalleForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String idRendicion;
	private String fechaD;
	private String fechaH;
	private String gasto;
	private String monto;
	private String accion;
	private String moneda;
	private String fechaGasto;
	private String tipoComprobante;
	private String tipoFactura;
	private String factura;
	private String codMotivo;
	private String centroCostos;
	private String opcion;
	private String idGasto;
	private String cuponesCheck;
	private String cupCred;
	private String cupDeb;
	private String cupon;
	private String descCupon;
	private String importeCupon;
	private String nroTarjeta;
	private String estadoRendicion;
	private String observacionGasto;
	private String usuarioRend;
	private String glg;
	private String montoMaximo;
	private String cuit;
	private String aviso;
	private String thubanLink;
	private String user;
	private String nombreUsuario;
	private int costos;
	private String costosDestino;
	private String esAdelanto;

	public void inicializarCupon() {
		this.cupon = null;
		this.cupCred = null;
		this.cupDeb = null;
		this.descCupon = null;
		this.importeCupon = null;
	}

	public String getCupDeb() {
		return cupDeb;
	}

	public void setCupDeb(String cupDeb) {
		this.cupDeb = cupDeb;
	}

	public String getCupon() {
		return cupon;
	}

	public void setCupon(String cupon) {
		this.cupon = cupon;
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

	public String getNroTarjeta() {
		return nroTarjeta;
	}

	public void setNroTarjeta(String nroTarjeta) {
		this.nroTarjeta = nroTarjeta;
	}

	public String getGasto() {
		return gasto;
	}

	public void setGasto(String gasto) {
		this.gasto = gasto;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getFechaGasto() {
		return fechaGasto;
	}

	public void setFechaGasto(String fechaGasto) {
		this.fechaGasto = fechaGasto;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public String getIdRendicion() {
		return idRendicion;
	}

	public void setIdRendicion(String idRendicion) {
		this.idRendicion = idRendicion;
	}

	public void setCodMotivo(String codMotivo) {
		this.codMotivo = codMotivo;
	}

	public String getCodMotivo() {
		return codMotivo;
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

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setIdGasto(String idGasto) {
		this.idGasto = idGasto;
	}

	public String getIdGasto() {
		return idGasto;
	}

	public void setCuponesCheck(String cuponesCheck) {
		this.cuponesCheck = cuponesCheck;
	}

	public String getCuponesCheck() {
		return cuponesCheck;
	}

	public void setCupCred(String cupCred) {
		this.cupCred = cupCred;
	}

	public String getCupCred() {
		return cupCred;
	}

	public void setEstadoRendicion(String estadoRendicion) {
		this.estadoRendicion = estadoRendicion;
	}

	public String getEstadoRendicion() {
		return estadoRendicion;
	}

	public void setObservacionGasto(String observacionGasto) {
		this.observacionGasto = observacionGasto;
	}

	public String getObservacionGasto() {
		return observacionGasto;
	}

	public void setUsuarioRend(String usuarioRend) {
		this.usuarioRend = usuarioRend;
	}

	public String getUsuarioRend() {
		return usuarioRend;
	}

	public void setGlg(String glg) {
		this.glg = glg;
	}

	public String getGlg() {
		return glg;
	}

	public void setMontoMaximo(String montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	public String getMontoMaximo() {
		return montoMaximo;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public String getAviso() {
		return aviso;
	}

	public void setThubanLink(String thubanLink) {
		this.thubanLink = thubanLink;
	}

	public String getThubanLink() {
		return thubanLink;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCostosDestino() {
		return costosDestino;
	}

	public void setCostosDestino(String costosDestino) {
		this.costosDestino = costosDestino;
	}

	public int getCostos() {
		return costos;
	}

	public void setCostos(int costos) {
		this.costos = costos;
	}

	public String getEsAdelanto() {
		return esAdelanto;
	}

	public void setEsAdelanto(String esAdelanto) {
		this.esAdelanto = esAdelanto;
	}

	@Override
	public String toString() {
		return "RendicionDetalleForm [idRendicion=" + idRendicion + ", fechaD=" + fechaD + ", fechaH=" + fechaH
				+ ", gasto=" + gasto + ", monto=" + monto + ", accion=" + accion + ", moneda=" + moneda
				+ ", fechaGasto=" + fechaGasto + ", tipoComprobante=" + tipoComprobante + ", tipoFactura=" + tipoFactura
				+ ", factura=" + factura + ", codMotivo=" + codMotivo + ", centroCostos=" + centroCostos + ", opcion="
				+ opcion + ", idGasto=" + idGasto + ", cuponesCheck=" + cuponesCheck + ", cupCred=" + cupCred
				+ ", cupDeb=" + cupDeb + ", cupon=" + cupon + ", descCupon=" + descCupon + ", importeCupon="
				+ importeCupon + ", nroTarjeta=" + nroTarjeta + ", estadoRendicion=" + estadoRendicion
				+ ", observacionGasto=" + observacionGasto + ", usuarioRend=" + usuarioRend + ", glg=" + glg
				+ ", montoMaximo=" + montoMaximo + ", cuit=" + cuit + ", aviso=" + aviso + ", thubanLink=" + thubanLink
				+ ", user=" + user + ", nombreUsuario=" + nombreUsuario + ", costos=" + costos + ", costosDestino="
				+ costosDestino + ", esAdelanto=" + esAdelanto + "]";
	}
	
	
}
