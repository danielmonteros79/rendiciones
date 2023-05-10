package com.sa.entities;

import java.util.List;

public class ComboMoneda {

    private String id;
    private String descripcion;
    private List<ComboMoneda> moneda;

    public ComboMoneda() {

    }

    public ComboMoneda(String id, String descripcion) {
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

    public List<ComboMoneda> getMoneda() {
        // TODO Auto-generated method stub
        return this.moneda;
    }
}
