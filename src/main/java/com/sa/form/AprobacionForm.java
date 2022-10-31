package com.sa.form;

import org.apache.struts.action.ActionForm;

public class AprobacionForm extends ActionForm {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String estado;
    private String motivoRechazo;
    private String check;
    private String glg;
    private String cmboMotivo;

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public void setGlg(String glg) {
        this.glg = glg;
    }

    public String getGlg() {
        return glg;
    }

    public String getCmboMotivo() {
        return cmboMotivo;
    }

    public void setCmboMotivo(String cmboMotivo) {
        this.cmboMotivo = cmboMotivo;
    }

}
