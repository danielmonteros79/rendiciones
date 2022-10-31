package com.sa.form.parametros;

import org.apache.struts.action.ActionForm;

public class ParametrosExceptuadosFiltroForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String motivoUsuario;
    private String exceptuadoFiltro;

    public ParametrosExceptuadosFiltroForm() {
    }

    public void clear() {
        this.motivoUsuario = null;
        this.exceptuadoFiltro = null;
    }

    public String getExceptuadoFiltro() {
        return exceptuadoFiltro;
    }

    public void setExceptuadoFiltro(String exceptuadoFiltro) {
        this.exceptuadoFiltro = exceptuadoFiltro;
    }

    public String getMotivoUsuario() {
        return motivoUsuario;
    }

    public void setMotivoUsuario(String motivoUsuario) {
        this.motivoUsuario = motivoUsuario;
    }
}
