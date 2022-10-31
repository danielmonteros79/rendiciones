package com.sa.entities;

import java.util.List;

public class CuadroGeneral {

    private String codEstado;
    private String codMotivo;
    private String codGlg;
    private String estado;
    private String cantRend;
    private String montoTotal;
    private String cons;
    private List<String> glg;

    public CuadroGeneral() {
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    public String getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(String codMotivo) {
        this.codMotivo = codMotivo;
    }

    public String getCodGlg() {
        return codGlg;
    }

    public void setCodGlg(String codGlg) {
        this.codGlg = codGlg;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCantRend() {
        return cantRend;
    }

    public void setCantRend(String cantRend) {
        this.cantRend = cantRend;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public List<String> getGlg() {
        return glg;
    }

    public void setGlg(List<String> glg) {
        this.glg = glg;
    }
}
