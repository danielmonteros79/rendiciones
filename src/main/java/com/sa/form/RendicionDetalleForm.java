package com.sa.form;

import org.apache.struts.action.ActionForm;

public class RendicionDetalleForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idRendicion;
	private String fechaD;
	private String fechaH;
	private String gastos;
	private String monto;
	private String accion;
	private String moneda;
	private String fechagastos;
	private String comprobante;
	private String cmbComprobante;
	private String comprobante1;
	private String comprobante2;
	private String codMotivo;
	private String centroCostos;
	private String opcion;
	private String idG;
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
	private String cuit1;
	private String cuit2;
	private String cuit3;
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
	public String getGastos() {
		return gastos;
	}
	public void setGastos(String gastos) {
		this.gastos = gastos;
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
	public String getFechagastos() {
		return fechagastos;
	}
	public void setFechagastos(String fechagastos) {
		this.fechagastos = fechagastos;
	}
	public String getComprobante() {
		return comprobante;
	}
	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}
	public String getCmbComprobante() {
		return cmbComprobante;
	}
	public void setCmbComprobante(String cmbComprobante) {
		this.cmbComprobante = cmbComprobante;
	}
	public String getComprobante1() {
		return comprobante1;
	}
	public void setComprobante1(String comprobante1) {
		this.comprobante1 = comprobante1;
	}
	public String getComprobante2() {
		return comprobante2;
	}
	public void setComprobante2(String comprobante2) {
		this.comprobante2 = comprobante2;
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
	public void setIdG(String idG) {
		this.idG = idG;
	}
	public String getIdG() {
		return idG;
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
	public String getCuit1() {
		return cuit1;
	}
	public void setCuit1(String cuit1) {
		this.cuit1 = cuit1;
	}
	public String getCuit2() {
		return cuit2;
	}
	public void setCuit2(String cuit2) {
		this.cuit2 = cuit2;
	}
	public String getCuit3() {
		return cuit3;
	}
	public void setCuit3(String cuit3) {
		this.cuit3 = cuit3;
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
}
