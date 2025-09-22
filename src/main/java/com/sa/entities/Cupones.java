package com.sa.entities;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Cupones {
	private String idRendicion;
	private String idGastoRend;
	private String opciones;
	private String tipo;
	private String codAdmin;
	private String cuentaCredito;
	private String nroCupon;
	private String nroCliente;
	private String nroTarjeta;
	private String liquidacionDebito;
	private String liquidacionCredito;
	private String liquidacionNeto;
	private String fechaPresentacion;
	private String fechaCierre;
	private String moneda;
	private String establecimiento;
	private String codigoAutorizacion;
	private String tipoMovimiento;
	private String tipoConsumo;
	private String marcaFacturado;
	private String nroCuponDebito;
	private String nroCuponCredito;
	private String montoUtilizado;
	private String disponible;
	private String cuponCheck;
	private List<Cupones> cupones;
	private boolean adelanto;
	private static final Logger log = Logger.getLogger(Cupones.class);

	public Cupones() {
	
	}
	
	public String getNroTarjetaCliente() {
		try {
			return "XXXX-XXXX-XXXX-" + nroTarjeta.substring(12, 16);
		} catch (Exception e) {
			return "";
		}
	}
	
	public String getIdRendicion() {
		return idRendicion;
	}

	public void setIdRendicion(String idRendicion) {
		this.idRendicion = idRendicion;
	}

	public String getIdGastoRend() {
		return idGastoRend;
	}

	public void setIdGastoRend(String idGastoRend) {
		this.idGastoRend = idGastoRend;
	}


	public String getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getNroCupon() {
		return nroCupon;
	}

	public void setNroCupon(String nroCupon) {
		this.nroCupon = nroCupon;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public List<Cupones> getCupones() {
		return cupones;
	}

	public void setCupones(List<Cupones> cupones) {
		this.cupones = cupones;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodAdmin() {
		return codAdmin;
	}

	public void setCodAdmin(String codAdmin) {
		this.codAdmin = codAdmin;
	}

	public String getCuentaCredito() {
		return cuentaCredito;
	}

	public void setCuentaCredito(String cuentaCredito) {
		this.cuentaCredito = cuentaCredito;
	}

	public String getNroCliente() {
		return nroCliente;
	}

	public void setNroCliente(String nroCliente) {
		this.nroCliente = nroCliente;
	}

	public String getNroTarjeta() {
		return nroTarjeta;
	}

	public void setNroTarjeta(String nroTarjeta) {
		this.nroTarjeta = nroTarjeta;
	}

	public String getLiquidacionDebito() {
		return liquidacionDebito;
	}

	public void setLiquidacionDebito(String liquidacionDebito) {
		this.liquidacionDebito = liquidacionDebito;
	}

	public String getLiquidacionCredito() {
		return liquidacionCredito;
	}

	public void setLiquidacionCredito(String liquidacionCredito) {
		this.liquidacionCredito = liquidacionCredito;
	}

	public String getLiquidacionNeto() {
		return liquidacionNeto;
	}

	public void setLiquidacionNeto(String liquidacionNeto) {
		this.liquidacionNeto = liquidacionNeto;
	}

	public String getFechaPresentacion() {
		return fechaPresentacion;
	}

	public Date getFechaPresentacionDate() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(fechaPresentacion);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("Error al parsear fechaPresentacion: " + fechaPresentacion, e);
			}
			log.error("No se pudo parsear fechaPresentacion. Valor: " + fechaPresentacion, e);
			return null;
		}
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getCodigoAutorizacion() {
		return codigoAutorizacion;
	}

	public void setCodigoAutorizacion(String codigoAutorizacion) {
		this.codigoAutorizacion = codigoAutorizacion;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getTipoConsumo() {
		return tipoConsumo;
	}

	public void setTipoConsumo(String tipoConsumo) {
		this.tipoConsumo = tipoConsumo;
	}

	public String getMarcaFacturado() {
		return marcaFacturado;
	}

	public void setMarcaFacturado(String marcaFacturado) {
		this.marcaFacturado = marcaFacturado;
	}

	public String getNroCuponDebito() {
		return nroCuponDebito;
	}

	public void setNroCuponDebito(String nroCuponDebito) {
		this.nroCuponDebito = nroCuponDebito;
	}

	public String getNroCuponCredito() {
		return nroCuponCredito;
	}

	public void setNroCuponCredito(String nroCuponCredito) {
		this.nroCuponCredito = nroCuponCredito;
	}

	public String getMontoUtilizado() {
		return montoUtilizado;
	}

	public void setMontoUtilizado(String montoUtilizado) {
		this.montoUtilizado = montoUtilizado;
	}

	public String getDisponible() {
		return disponible;
	}

	public void setDisponible(String disponible) {
		this.disponible = disponible;
	}

	public String getOpciones() {
		return opciones;
	}

	public void setOpciones(String opciones) {
		this.opciones = opciones;
	}

	public void setCuponCheck(String cuponCheck) {
		this.cuponCheck = cuponCheck;
	}

	public String getCuponCheck() {
		return cuponCheck;
	}

	public boolean isAdelanto() {
		return adelanto;
	}

	public void setAdelanto(boolean adelanto) {
		this.adelanto = adelanto;
	}
}