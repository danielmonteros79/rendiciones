package com.sa.form;

import com.sa.entities.ComboOpcion;
import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionForm;

public class ResumenForm extends ActionForm {

    private static final long serialVersionUID = 1L;
    private String resumen;
    private String estado;
    private List<ComboOpcion> cmbResumen = new ArrayList<ComboOpcion>();

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ComboOpcion> getCmbResumen() {
        return cmbResumen;
    }

    public void setCmbResumen(List<ComboOpcion> cmbResumen) {
        this.cmbResumen = cmbResumen;
    }
}
