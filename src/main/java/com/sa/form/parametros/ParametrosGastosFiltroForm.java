package com.sa.form.parametros;

import org.apache.struts.action.ActionForm;

public class ParametrosGastosFiltroForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String gasto;

    public ParametrosGastosFiltroForm() {
    }

    public void reset() {
        this.gasto = null;
    }

    public String getGasto() {
        return gasto;
    }

    public void setGasto(String gasto) {
        this.gasto = gasto;
    }
}
