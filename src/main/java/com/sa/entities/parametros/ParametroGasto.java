package com.sa.entities.parametros;

import com.sa.entities.OSCAR;
import java.util.Date;
import java.util.List;

public class ParametroGasto {

    private String gasto;
    private String descripcionGasto;
    private String motivo;
    private String descripcionMotivo;
    private String ristra;
    private String bimon;
    private String comprob;
    private String autoriz;
    private String observ;
    private String estado;
    private String ccostos;
    private String comboCCostos;
    private String maInclExcl;
    private String maMonto;
    private String impAviso;
    private OSCAR oscar;
    private String maCtrlImp;
    private String idAntg;
    private String plazoAprob;
    private Date feAlta;
    private String usrAlta;
    private Date feUltMod;
    private String usrUltMod;
    private Date feBaja;
    private String usrBaja;
    private String nroTerm;
    private String antiguedad;
    private String nivelIngreso;
    private List<String> centrosCosto;

    public ParametroGasto() {
    }

    public String getGasto() {
        return gasto;
    }

    public void setGasto(String gasto) {
        this.gasto = gasto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescripcionMotivo() {
        return descripcionMotivo;
    }

    public void setDescripcionMotivo(String descripcionMotivo) {
        this.descripcionMotivo = descripcionMotivo;
    }

    public String getRistra() {
        return ristra;
    }

    public void setRistra(String ristra) {
        this.ristra = ristra;
    }

    public String getBimon() {
        return bimon;
    }

    public void setBimon(String bimon) {
        this.bimon = bimon;
    }

    public String getComprob() {
        return comprob;
    }

    public void setComprob(String comprob) {
        this.comprob = comprob;
    }

    public String getAutoriz() {
        return autoriz;
    }

    public void setAutoriz(String autoriz) {
        this.autoriz = autoriz;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCcostos() {
        return ccostos;
    }

    public void setCcostos(String ccostos) {
        this.ccostos = ccostos;
    }

    public String getMaInclExcl() {
        return maInclExcl;
    }

    public void setMaInclExcl(String maInclExcl) {
        this.maInclExcl = maInclExcl;
    }

    public String getMaMonto() {
        return maMonto;
    }

    public void setMaMonto(String maMonto) {
        this.maMonto = maMonto;
    }

    public String getImpAviso() {
        return impAviso;
    }

    public void setImpAviso(String impAviso) {
        this.impAviso = impAviso;
    }

    public OSCAR getOscar() {
        return oscar;
    }

    public void setOscar(OSCAR oscar) {
        this.oscar = oscar;
    }

    public String getMaCtrlImp() {
        return maCtrlImp;
    }

    public void setMaCtrlImp(String maCtrlImp) {
        this.maCtrlImp = maCtrlImp;
    }

    public String getIdAntg() {
        return idAntg;
    }

    public void setIdAntg(String idAntg) {
        this.idAntg = idAntg;
    }

    public String getPlazoAprob() {
        return plazoAprob;
    }

    public void setPlazoAprob(String plazoAprob) {
        this.plazoAprob = plazoAprob;
    }

    public Date getFeAlta() {
        return feAlta;
    }

    public void setFeAlta(Date date) {
        this.feAlta = date;
    }

    public String getUsrAlta() {
        return usrAlta;
    }

    public void setUsrAlta(String usrAlta) {
        this.usrAlta = usrAlta;
    }

    public Date getFeUltMod() {
        return feUltMod;
    }

    public void setFeUltMod(Date feUltMod) {
        this.feUltMod = feUltMod;
    }

    public String getUsrUltMod() {
        return usrUltMod;
    }

    public void setUsrUltMod(String usrUltMod) {
        this.usrUltMod = usrUltMod;
    }

    public Date getFeBaja() {
        return feBaja;
    }

    public void setFeBaja(Date feBaja) {
        this.feBaja = feBaja;
    }

    public String getUsrBaja() {
        return usrBaja;
    }

    public void setUsrBaja(String usrBaja) {
        this.usrBaja = usrBaja;
    }

    public String getNroTerm() {
        return nroTerm;
    }

    public void setNroTerm(String nroTerm) {
        this.nroTerm = nroTerm;
    }

    public String getDescripcionGasto() {
        return descripcionGasto;
    }

    public void setDescripcionGasto(String descripcionGasto) {
        this.descripcionGasto = descripcionGasto;
    }

    public String getComboCCostos() {
        return comboCCostos;
    }

    public void setComboCCostos(String comboCCostos) {
        this.comboCCostos = comboCCostos;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getNivelIngreso() {
        return nivelIngreso;
    }

    public void setNivelIngreso(String nivelIngreso) {
        this.nivelIngreso = nivelIngreso;
    }

    public List<String> getCentrosCosto() {
        return centrosCosto;
    }

    public void setCentrosCosto(List<String> centrosCosto) {
        this.centrosCosto = centrosCosto;
    }
}
