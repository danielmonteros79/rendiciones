package com.sa.form.parametros;

import org.apache.struts.action.ActionForm;

public class ParametrosMotivoFiltroForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String codigo;

    public ParametrosMotivoFiltroForm() {
    }

    public void clear() {
        this.codigo = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
