package com.sa.entities;

import java.util.ArrayList;
import java.util.List;

public class ComboMotivo {
    private String id;
    private String descripcion;
    private String costosDestino;
    private String preFormato;
    private String cantDias;
    private List<ComboMotivo> motivo ;
    
    public ComboMotivo(){
        motivo = new ArrayList<ComboMotivo>();
        motivo.add(new ComboMotivo("1", "Capacitacion Cajero"));
        motivo.add(new ComboMotivo("2", "Reunion"));
        motivo.add(new ComboMotivo("3", "Campa\u00f1a"));
    }
    
    public String getPreFormato() {
        return preFormato;
    }

    public void setPreFormato(String preFormato) {
        this.preFormato = preFormato;
    }

    public String getCantDias() {
        return cantDias;
    }

    public void setCantDias(String cantDias) {
        this.cantDias = cantDias;
    }

    public ComboMotivo(String id, String descripcion){
        super();
        this.id = id;
        this.descripcion= descripcion;
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
    public String getCostosDestino() {
        return costosDestino;
    }

    public void setCostosDestino(String costosDestino) {
        this.costosDestino = costosDestino;
    }

    public List<ComboMotivo> getMotivoRendiciones() {
        return this.motivo;
    }
    
}