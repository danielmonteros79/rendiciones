package com.sa.entities;

import java.util.List;

public class ComboGasto {

    private String id;
    private String descripcion;
    private String detalle;
    private String descOblig;
    private List<ComboGasto> gastos;

    public ComboGasto() {

    }

    public ComboGasto(String id, String descripcion) {
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

    public List<ComboGasto> getGastos() {
        // TODO Auto-generated method stub
        return this.gastos;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public void setDescOblig(String descOblig) {
        this.descOblig = descOblig;
    }

    public String getDescOblig() {
        return descOblig;
    }
}
