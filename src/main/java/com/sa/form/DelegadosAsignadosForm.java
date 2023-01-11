package com.sa.form;

import org.apache.struts.action.ActionForm;

public class DelegadosAsignadosForm extends ActionForm {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String delegado;
    private String nomDel;

    public String getDelegado() {
        return delegado;
    }

    public String getNomDel() {
        return nomDel;
    }

    public void setNomDel(String nomDel) {
        this.nomDel = nomDel;
    }

    public void setDelegado(String delegado) {
        this.delegado = delegado;
    }

}
