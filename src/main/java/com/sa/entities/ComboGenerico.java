package com.sa.entities;

import java.util.List;

public class ComboGenerico {

    private String id;
    private String descripcion;
    private String detalle;
    private List<ComboGenerico> gastos;

    public ComboGenerico() {

    }

    public ComboGenerico(String id, String descripcion) {
        super();
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ComboGenerico> getGastos() {
        // TODO Auto-generated method stub
        return this.gastos;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
