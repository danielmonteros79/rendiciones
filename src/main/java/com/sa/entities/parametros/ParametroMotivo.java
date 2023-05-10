package com.sa.entities.parametros;

import com.sa.entities.OSCAR;
import java.util.Date;
import java.util.List;

public class ParametroMotivo {

    private String codigo;
    private String estado;
    private String descripcion;
    private String idGlg;
    private String codAprobacionGlg;
    private String idCentroCostos;
    private String maInclExcl;
    private String codSup;
    private String codFirma;
    private String meAviso;
    private Date fechaDesde;
    private Date fechaHasta;
    private OSCAR oscar;
    private String idNivCarga;
    private String idNivAutoriz;
    private String txAviso;
    private String idOperEspe;
    private String meDiasInterv;
    private List<String> centrosCosto;

    public ParametroMotivo() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdGlg() {
        return idGlg;
    }

    public void setIdGlg(String idGlg) {
        this.idGlg = idGlg;
    }

    public String getCodAprobacionGlg() {
        return codAprobacionGlg;
    }

    public void setCodAprobacionGlg(String codAprobacionGlg) {
        this.codAprobacionGlg = codAprobacionGlg;
    }

    public String getIdCentroCostos() {
        return idCentroCostos;
    }

    public void setIdCentroCostos(String idCentroCostos) {
        this.idCentroCostos = idCentroCostos;
    }

    public String getMaInclExcl() {
        return maInclExcl;
    }

    public void setMaInclExcl(String maInclExcl) {
        this.maInclExcl = maInclExcl;
    }

    public String getCodSup() {
        return codSup;
    }

    public void setCodSup(String codSup) {
        this.codSup = codSup;
    }

    public String getCodFirma() {
        return codFirma;
    }

    public void setCodFirma(String codFirma) {
        this.codFirma = codFirma;
    }

    public String getMeAviso() {
        return meAviso;
    }

    public void setMeAviso(String meAviso) {
        this.meAviso = meAviso;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public OSCAR getOscar() {
        return oscar;
    }

    public void setOscar(OSCAR oscar) {
        this.oscar = oscar;
    }

    public String getIdNivCarga() {
        return idNivCarga;
    }

    public void setIdNivCarga(String idNivCarga) {
        this.idNivCarga = idNivCarga;
    }

    public String getIdNivAutoriz() {
        return idNivAutoriz;
    }

    public void setIdNivAutoriz(String idNivAutoriz) {
        this.idNivAutoriz = idNivAutoriz;
    }

    public String getTxAviso() {
        return txAviso;
    }

    public void setTxAviso(String txAviso) {
        this.txAviso = txAviso;
    }

    public String getIdOperEspe() {
        return idOperEspe;
    }

    public void setIdOperEspe(String idOperEspe) {
        this.idOperEspe = idOperEspe;
    }

    public String getMeDiasInterv() {
        return meDiasInterv;
    }

    public void setMeDiasInterv(String meDiasInterv) {
        this.meDiasInterv = meDiasInterv;
    }

    public List<String> getCentrosCosto() {
        return centrosCosto;
    }

    public void setCentrosCosto(List<String> centrosCosto) {
        this.centrosCosto = centrosCosto;
    }
}
