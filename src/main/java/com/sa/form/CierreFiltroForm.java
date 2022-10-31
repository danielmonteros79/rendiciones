package com.sa.form;

import org.apache.struts.action.ActionForm;

public class CierreFiltroForm extends ActionForm {

    private static final long serialVersionUID = 1L;
    private String user;
    private String motivo;
    private String idRendicion;
    private String fechaDesde;
    private String fechaHasta;

    public CierreFiltroForm() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getIdRendicion() {
        return idRendicion;
    }

    public void setIdRendicion(String idRendicion) {
        this.idRendicion = idRendicion;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

}
