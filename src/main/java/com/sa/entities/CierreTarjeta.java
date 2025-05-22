package com.sa.entities;

import java.util.Date;

public class CierreTarjeta {
	private int idSecResumen;
	private String nroTarjeta;
	private Date fechaCupon;
	private String cuponTarjeta;
	private String cuponAdmDev;
	private String cuponAdmCred;
	private String establecimiento;
	private double montoCupon;
	private String moneda;
	private String importeGtosRend;
	private Date fechaResumen;
	private String estadoResumen;
	private Date fechaCierre;
	private String fechaAlta;
	private String userAlta;
	private String fechaUltModif;
	private String usuario;

	public int getIdSecResumen() {
		return idSecResumen;
	}

	public void setIdSecResumen(int idSecResumen) {
		this.idSecResumen = idSecResumen;
	}

	public String getNroTarjeta() {
		return nroTarjeta;
	}

	public void setNroTarjeta(String nroTarjeta) {
		this.nroTarjeta = nroTarjeta;
	}

	public Date getFechaCupon() {
		return fechaCupon;
	}

	public void setFechaCupon(Date fechaCupon) {
		this.fechaCupon = fechaCupon;
	}

	public String getCuponTarjeta() {
		return cuponTarjeta;
	}

	public void setCuponTarjeta(String cuponTarjeta) {
		this.cuponTarjeta = cuponTarjeta;
	}

	public String getCuponAdmDev() {
		return cuponAdmDev;
	}

	public void setCuponAdmDev(String cuponAdmDev) {
		this.cuponAdmDev = cuponAdmDev;
	}

	public String getCuponAdmCred() {
		return cuponAdmCred;
	}

	public void setCuponAdmCred(String cuponAdmCred) {
		this.cuponAdmCred = cuponAdmCred;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public double getMontoCupon() {
		return montoCupon;
	}

	public void setMontoCupon(double montoCupon) {
		this.montoCupon = montoCupon;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getImporteGtosRend() {
		return importeGtosRend;
	}

	public void setImporteGtosRend(String importeGtosRend) {
		this.importeGtosRend = importeGtosRend;
	}

	public Date getFechaResumen() {
		return fechaResumen;
	}

	public void setFechaResumen(Date fechaResumen) {
		this.fechaResumen = fechaResumen;
	}

	public String getEstadoResumen() {
		return estadoResumen;
	}

	public void setEstadoResumen(String estadoResumen) {
		this.estadoResumen = estadoResumen;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getUserAlta() {
		return userAlta;
	}

	public void setUserAlta(String userAlta) {
		this.userAlta = userAlta;
	}

	public String getFechaUltModif() {
		return fechaUltModif;
	}

	public void setFechaUltModif(String fechaUltModif) {
		this.fechaUltModif = fechaUltModif;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}