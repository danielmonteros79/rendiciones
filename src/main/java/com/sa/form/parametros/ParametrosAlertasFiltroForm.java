package com.sa.form.parametros;

import com.sa.entities.ComboOpcion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts.action.ActionForm;

public class ParametrosAlertasFiltroForm extends ActionForm {

    private static final long serialVersionUID = 1L;
    private String codMotivo;
    private String codGasto;
    private Map<String, String> mapGastoMotivo = new HashMap<String, String>();
    private Map<String, List<ComboOpcion>> mapMotivoGastos = new HashMap<String, List<ComboOpcion>>();
    private List<ComboOpcion> cmbGasto = new ArrayList<ComboOpcion>();
    private List<ComboOpcion> cmbMotivo = new ArrayList<ComboOpcion>();

    public ParametrosAlertasFiltroForm() {
    }

    public String getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(String codMotivo) {
        this.codMotivo = codMotivo;
    }

    public String getCodGasto() {
        return codGasto;
    }

    public void setCodGasto(String codGasto) {
        this.codGasto = codGasto;
    }

    public Map<String, String> getMapGastoMotivo() {
        return mapGastoMotivo;
    }

    public void setMapGastoMotivo(Map<String, String> mapGastoMotivo) {
        this.mapGastoMotivo = mapGastoMotivo;
    }

    public Map<String, List<ComboOpcion>> getMapMotivoGastos() {
        return mapMotivoGastos;
    }

    public void setMapMotivoGastos(Map<String, List<ComboOpcion>> mapMotivoGastos) {
        this.mapMotivoGastos = mapMotivoGastos;
    }

    public List<ComboOpcion> getCmbGasto() {
        return cmbGasto;
    }

    public void setCmbGasto(List<ComboOpcion> cmbGasto) {
        this.cmbGasto = cmbGasto;
    }

    public List<ComboOpcion> getCmbMotivo() {
        return cmbMotivo;
    }

    public void setCmbMotivo(List<ComboOpcion> cmbMotivo) {
        this.cmbMotivo = cmbMotivo;
    }
}
