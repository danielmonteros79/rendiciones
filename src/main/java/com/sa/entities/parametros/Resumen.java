package com.sa.entities.parametros;

import java.util.Date;

public class Resumen {

    private Date fecha;
    private Date fechaDebito;
    private String cupon;
    private String establecimiento;
    private String monto;
    private String moneda;
    private String estado;
    private String idRendicion;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaDebito() {
        return fechaDebito;
    }

    public void setFechaDebito(Date fechaDebito) {
        this.fechaDebito = fechaDebito;
    }

    public String getCupon() {
        return cupon;
    }

    public void setCupon(String cupon) {
        this.cupon = cupon;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdRendicion() {
        return idRendicion;
    }

    public void setIdRendicion(String idRendicion) {
        this.idRendicion = idRendicion;
    }
}
