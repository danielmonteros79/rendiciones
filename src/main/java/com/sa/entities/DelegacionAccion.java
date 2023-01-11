package com.sa.entities;

public class DelegacionAccion {

    private String accion;
    private String accionDesc;

    public DelegacionAccion(String accion, String accionDesc) {
        super();
        this.accion = accion;
        this.accionDesc = accionDesc;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getAccionDesc() {
        return accionDesc;
    }

    public void setAccionDesc(String accionDesc) {
        this.accionDesc = accionDesc;
    }

}
