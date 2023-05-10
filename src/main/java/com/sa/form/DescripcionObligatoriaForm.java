package com.sa.form;

import org.apache.struts.action.ActionForm;

public class DescripcionObligatoriaForm extends ActionForm {

    private static final long serialVersionUID = 1L;
    String idRendicion;
    String idGasto;
    String codGasto;
    String codDetOblig;
    String TXT1;
    String TXT2;
    Integer NUM1;
    Integer NUM2;
    String COD1;
    String COD2;
    String TXT250;
    String FEC1;
    String FEC2;
    String tipoEntrada;
    String codMotivo;
    String estadoRend;

    public void reset() {
        this.idRendicion = null;
        this.idGasto = null;
        this.codGasto = null;
        this.codDetOblig = null;
        this.TXT1 = null;
        this.TXT2 = null;
        this.NUM1 = null;
        this.NUM2 = null;
        this.COD1 = null;
        this.COD2 = null;
        this.TXT250 = null;
        this.FEC1 = null;
        this.FEC2 = null;
        this.tipoEntrada = null;
        this.codMotivo = null;
        this.estadoRend = null;
    }

    public String getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(String codMotivo) {
        this.codMotivo = codMotivo;
    }

    public String getEstadoRend() {
        return estadoRend;
    }

    public void setEstadoRend(String estadoRend) {
        this.estadoRend = estadoRend;
    }

    public String getIdRendicion() {
        return idRendicion;
    }

    public void setIdRendicion(String idRendicion) {
        this.idRendicion = idRendicion;
    }

    public String getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(String idGasto) {
        this.idGasto = idGasto;
    }

    public String getCodGasto() {
        return codGasto;
    }

    public void setCodGasto(String codGasto) {
        this.codGasto = codGasto;
    }

    public String getCodDetOblig() {
        return codDetOblig;
    }

    public void setCodDetOblig(String codDetOblig) {
        this.codDetOblig = codDetOblig;
    }

    public String getTXT1() {
        return TXT1;
    }

    public void setTXT1(String tXT1) {
        TXT1 = tXT1;
    }

    public String getTXT2() {
        return TXT2;
    }

    public void setTXT2(String tXT2) {
        TXT2 = tXT2;
    }

    public Integer getNUM1() {
        return NUM1;
    }

    public void setNUM1(Integer nUM1) {
        NUM1 = nUM1;
    }

    public Integer getNUM2() {
        return NUM2;
    }

    public void setNUM2(Integer nUM2) {
        NUM2 = nUM2;
    }

    public String getCOD1() {
        return COD1;
    }

    public void setCOD1(String cOD1) {
        COD1 = cOD1;
    }

    public String getCOD2() {
        return COD2;
    }

    public void setCOD2(String cOD2) {
        COD2 = cOD2;
    }

    public String getTXT250() {
        return TXT250;
    }

    public void setTXT250(String tXT250) {
        TXT250 = tXT250;
    }

    public String getFEC1() {
        return FEC1;
    }

    public void setFEC1(String fEC1) {
        FEC1 = fEC1;
    }

    public String getFEC2() {
        return FEC2;
    }

    public void setFEC2(String fEC2) {
        FEC2 = fEC2;
    }

    @Override
    public String toString() {
        return "DescripcionObligatoriaForm [COD1=" + COD1 + ", COD2=" + COD2
                + ", FEC1=" + FEC1 + ", FEC2=" + FEC2 + ", NUM1="
                + NUM1 + ", NUM2=" + NUM2 + ", TXT1=" + TXT1 + ", TXT2=" + TXT2
                + ", TXT250=" + TXT250 + ", codDetOblig=" + codDetOblig
                + ", codGasto=" + codGasto + ", idGasto=" + idGasto
                + ", idRendicion=" + idRendicion + "]";
    }

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }
}
