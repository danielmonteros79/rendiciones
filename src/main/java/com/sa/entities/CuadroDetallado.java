package com.sa.entities;

import java.util.Date;

public class CuadroDetallado {

    private Integer id;
    private String motivo;
    private String descripcion;
    private String importe;
    private String usuario;
    private String proxUsuario;
    private Date fechaUltModif;
    private String codEstado;
    private String codMotivo;

    public CuadroDetallado() {

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getProxUsuario() {
        return proxUsuario;
    }

    public void setProxUsuario(String proxUsuario) {
        this.proxUsuario = proxUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getEstado() {
        return codEstado;
    }

    public void setEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    public Date getFechaUltModif() {
        return fechaUltModif;
    }

    public void setFechaUltModif(Date fechaUltModif) {
        this.fechaUltModif = fechaUltModif;
    }

    public String getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(String codMotivo) {
        this.codMotivo = codMotivo;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }
}
