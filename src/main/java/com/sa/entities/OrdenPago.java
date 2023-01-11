package com.sa.entities;

import java.util.List;

public class OrdenPago {

    private List<OrdenPago> ordenPago;
    private List<Rendicion> rendicion;

    private Integer id;
    private Integer estado;

    public OrdenPago(Integer id, List<Rendicion> rendicion, Integer estado, List<OrdenPago> ordenPago) {
        this.id = id;
        this.rendicion = rendicion;
        this.estado = estado;
        this.ordenPago = (ordenPago);
    }

    public List<Rendicion> getRendicion() {
        return rendicion;
    }

    public void setRendicion(List<Rendicion> rendicion) {
        this.rendicion = rendicion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public List<OrdenPago> getOrdenPago() {
        return ordenPago;
    }

    public void setOrdenPago(List<OrdenPago> ordenPago) {
        this.ordenPago = ordenPago;
    }

}
