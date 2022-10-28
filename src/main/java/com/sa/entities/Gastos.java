package com.sa.entities;

import java.io.Serializable;
import java.util.List;

public class Gastos implements Serializable {
	private static final long serialVersionUID = 1L;
	private String idGasto;
	private String descGasto;
	private String cuponGasto;
	private String nroGasto;
	private String monto;
	private String moneda;
	private String observacionGasto;
	private String costosDestino;
	private String fechagastos;
	private String tipoComprobante;
	private String comprobante;
	private String obsObligatoria;
	private String obs;
	private String tarjeta;
	private String cmbComprobante;
	private String comprobante1;
	private String comprobante2;
	private String cuit1;
	private String cuit2;
	private String cuit3;
	private List<Cupones> listaCupones;
	private String centroCostoGasto;
	private String idGastoOriginal;

	public Gastos() {
		this.idGasto = "";
		this.descGasto = "";
		this.cuponGasto = "";
		this.nroGasto = "";
		this.monto = "";
		this.moneda = "";
		this.observacionGasto = "";
		this.cmbComprobante = "";
		this.comprobante1 = "";
		this.comprobante2 = "";
		this.cuit1 = "";
		this.cuit2 = "";
		this.cuit3 = "";
	}

	public Gastos(String nroGasto, String monto, String moneda, String fechagastos, String comprobante,
			List<Cupones> listaCupones) {
		this.nroGasto = nroGasto;
		this.monto = monto;
		this.moneda = moneda;
		this.fechagastos = fechagastos;
		this.comprobante = comprobante;
		this.listaCupones = listaCupones;
	}

	public String getIdGasto() {
		return idGasto;
	}
	public void setIdGasto(String idGasto) {
		this.idGasto = idGasto;
	}
	public String getDescGasto() {
		return descGasto;
	}
	public void setDescGasto(String descGasto) {
		this.descGasto = descGasto;
	}
	public String getTipoComprobante() {
		return tipoComprobante;
	}
	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getObsObligatoria() {
		return obsObligatoria;
	}
	public void setObsObligatoria(String obsObligatoria) {
		this.obsObligatoria = obsObligatoria;
	}
	public String getTarjeta() {
		return tarjeta;
	}
	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}
	public void setNroGasto(String nroGasto) {
		this.nroGasto = nroGasto;
	}
	
	public String getNroGasto() {
		return nroGasto;
	}
	public void setNroGastos(String nroGasto) {
		this.nroGasto = nroGasto;
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
	public List<Cupones> getListaCupones() {
		return listaCupones;
	}
	public void setListaCupones(List<Cupones> listaCupones) {
		this.listaCupones = listaCupones;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public void setCuponGasto(String cuponGasto) {
		this.cuponGasto = cuponGasto;
	}
	public String getCuponGasto() {
		return cuponGasto;
	}
	public void setObservacionGasto(String observacionGasto) {
		this.observacionGasto = observacionGasto;
	}
	public String getObservacionGasto() {
		return observacionGasto;
	}

	public String getCostosDestino() {
		return costosDestino;
	}

	public void setCostosDestino(String costosDestino) {
		this.costosDestino = costosDestino;
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

	public String getCmbComprobante() {
		return cmbComprobante;
	}

	public void setCmbComprobante(String cmbComprobante) {
		this.cmbComprobante = cmbComprobante;
	}

	public String getCentroCostoGasto() {
		return centroCostoGasto;
	}

	public void setCentroCostoGasto(String centroCostoGasto) {
		this.centroCostoGasto = centroCostoGasto;
	}

	public String getIdGastoOriginal() {
		return idGastoOriginal;
	}

	public void setIdGastoOriginal(String idGastoOriginal) {
		this.idGastoOriginal = idGastoOriginal;
	}
}
