package com.sa.entities;

import java.io.Serializable;

public class Ristra implements Serializable {
	private static final long serialVersionUID = 1L;
	private String producto;
	private String subproducto;
	private String garantia;
	private String tipoPlazo;
	private String plazo;
	private String subsector;
	private String sectorBE;
	private String cnae;
	private String empresaTutelada;
	private String ambito;
	private String morosidad;
	private String inversion;
	private String operacion;
	private String codigoContable;
	private String divisa;
	private String tipoDivisa;
	private String resto;
	private String varios;

	public Ristra() {
		this.producto = "";
		this.subproducto = "";
		this.garantia = "";
		this.tipoPlazo = "";
		this.plazo = "";
		this.subsector = "";
		this.sectorBE = "";
		this.cnae = "";
		this.empresaTutelada = "";
		this.ambito = "";
		this.morosidad = "";
		this.inversion = "";
		this.operacion = "";
		this.codigoContable = "";
		this.divisa = "";
		this.tipoDivisa = "";
		this.resto = "";
		this.varios = "";
	}

	public Ristra(String ristraStr) {
		int i = 0;
		this.producto = ristraStr.substring(i, i += 2);
		this.subproducto = ristraStr.substring(i, i += 4);
		this.garantia = ristraStr.substring(i, i += 3);
		this.tipoPlazo = ristraStr.substring(i, i += 1);
		this.plazo = ristraStr.substring(i, i += 3);
		this.subsector = ristraStr.substring(i, i += 1);
		this.sectorBE = ristraStr.substring(i, i += 2);
		this.cnae = ristraStr.substring(i, i += 5);
		this.empresaTutelada = ristraStr.substring(i, i += 4);
		this.ambito = ristraStr.substring(i, i += 2);
		this.morosidad = ristraStr.substring(i, i += 1);
		this.inversion = ristraStr.substring(i, i += 1);
		this.operacion = ristraStr.substring(i, i += 3);
		this.codigoContable = ristraStr.substring(i, i += 5);
		this.divisa = ristraStr.substring(i, i += 3);
		this.tipoDivisa = ristraStr.substring(i, i += 1);
		this.resto = ristraStr.substring(i, i += 10);
		this.varios = ristraStr.substring(i, i += 18);
	}

	public String getProducto() {
		return producto.trim();
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getSubproducto() {
		return subproducto.trim();
	}

	public void setSubproducto(String subproducto) {
		this.subproducto = subproducto;
	}

	public String getGarantia() {
		return garantia.trim();
	}

	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}

	public String getTipoPlazo() {
		return tipoPlazo.trim();
	}

	public void setTipoPlazo(String tipoPlazo) {
		this.tipoPlazo = tipoPlazo;
	}

	public String getPlazo() {
		return plazo.trim();
	}

	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

	public String getSubsector() {
		return subsector.trim();
	}

	public void setSubsector(String subsector) {
		this.subsector = subsector;
	}

	public String getSectorBE() {
		return sectorBE.trim();
	}

	public void setSectorBE(String sectorBE) {
		this.sectorBE = sectorBE;
	}

	public String getCnae() {
		return cnae.trim();
	}

	public void setCnae(String cnae) {
		this.cnae = cnae;
	}

	public String getEmpresaTutelada() {
		return empresaTutelada.trim();
	}

	public void setEmpresaTutelada(String empresaTutelada) {
		this.empresaTutelada = empresaTutelada;
	}

	public String getAmbito() {
		return ambito.trim();
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getMorosidad() {
		return morosidad.trim();
	}

	public void setMorosidad(String morosidad) {
		this.morosidad = morosidad; 
	}

	public String getInversion() {
		return inversion.trim();
	}

	public void setInversion(String inversion) {
		this.inversion =  inversion;
	}

	public String getOperacion() {
		return operacion.trim();
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getCodigoContable() {
		return codigoContable.trim();
	}

	public void setCodigoContable(String codigoContable) {
		this.codigoContable = codigoContable;
	}

	public String getDivisa() {
		return divisa.trim();
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	public String getTipoDivisa() {
		return tipoDivisa.trim();
	}

	public void setTipoDivisa(String tipoDivisa) {
		this.tipoDivisa = tipoDivisa;
	}

	public String getResto() {
		return resto.trim();
	}

	public void setResto(String resto) {
		this.resto = resto;
	}

	public String getVarios() {
		return varios.trim();
	}

	public void setVarios(String varios) {
		this.varios = varios;
	}

	@Override
	public String toString() {
		return padRight(producto, 2) + padRight(subproducto, 4) + padRight(garantia, 3) + padRight(tipoPlazo, 1) + padRight(plazo, 3) + padRight(subsector, 1) + padRight(sectorBE, 2) + padRight(cnae, 5) + padRight(empresaTutelada, 4) +
				padRight(ambito, 2) + padRight(morosidad, 1) + padRight(inversion, 1)+ padRight(operacion, 3) + padRight(codigoContable, 5) + padRight(divisa, 3) + padRight(tipoDivisa, 1) + padRight(resto, 10) + padRight(varios, 18);
	}
	
	private String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);
	}
}