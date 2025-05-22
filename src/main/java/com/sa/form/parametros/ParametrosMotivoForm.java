package com.sa.form.parametros;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.sa.entities.OSCAR;

public class ParametrosMotivoForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String descripcion;
	private String idGlg;
	private String idCentroCostos;
	private String estado;
	private String codSup;
	private String codFirma;
	private String codAprobacionGlg;
	private OSCAR oscar;
	private String fechaDesde;
	private String fechaHasta;
	private String idNivCarga;
	private String idNivAutoriz;
	private String maInclExcl;
	private String txAviso;
	private String idOperEspe;
	private String meDiasInterv;
	private List<String> centrosCosto;
	private String accion;
	private String descOscar;
	private String idCentroCostosFijo;

	public void clear() {
		this.codigo = null;
		this.descripcion = null;
		this.idGlg = null;
		this.idCentroCostos = null;
		this.estado = null;
		this.codSup = null;
		this.codFirma = null;
		this.codAprobacionGlg = null;
		this.oscar = new OSCAR();
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.idNivCarga = null;
		this.idNivAutoriz = null;
		this.maInclExcl = null;
		this.txAviso = null;
		this.idOperEspe = null;
		this.meDiasInterv = null;
		this.centrosCosto = new ArrayList<String>();
		this.descOscar = null;
		this.idCentroCostosFijo = null;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public String getIdCentroCostos() {
		return idCentroCostos;
	}

	public void setIdCentroCostos(String idCentroCostos) {
		this.idCentroCostos = idCentroCostos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public String getCodAprobacionGlg() {
		return codAprobacionGlg;
	}

	public void setCodAprobacionGlg(String codAprobacionGlg) {
		this.codAprobacionGlg = codAprobacionGlg;
	}
	
	public OSCAR getOscar() {
		return oscar;
	}

	public void setOscar(OSCAR oscar) {
		this.oscar = oscar;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
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

	public String getMaInclExcl() {
		return maInclExcl;
	}

	public void setMaInclExcl(String maInclExcl) {
		this.maInclExcl = maInclExcl;
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

	public void setCentrosCosto(String centrosCosto) {
	}
	
	public void setCentrosCostoList(List<String> centrosCosto) {
		this.centrosCosto = centrosCosto;
	}

	public void setCentrosCostoI(int index, String value) {
	    this.centrosCosto.set(index, value);
	}
	
	public String getCentrosCostoI(int index) {
	    return this.centrosCosto.get(index);
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String action) {
		this.accion = action;
	}
	
	public String getDescOscar() {
		return descOscar;
	}

	public void setDescOscar(String descOscar) {
		this.descOscar = descOscar;
	}
	
	public String getidCentroCostosFijo() {
		return idCentroCostosFijo;
	}

	public void setidCentroCostosFijo(String idCentroCostosFijo) {
		this.idCentroCostosFijo = idCentroCostosFijo;
	}

	@Override
	public String toString() {
		return "ParametrosMotivoForm [codigo=" + codigo + ", descripcion=" + descripcion + ", idGlg=" + idGlg
				+ ", idCentroCostos=" + idCentroCostos + ", estado=" + estado + ", codSup=" + codSup + ", codFirma="
				+ codFirma + ", codAprobacionGlg=" + codAprobacionGlg + ", oscar=" + oscar + ", fechaDesde="
				+ fechaDesde + ", fechaHasta=" + fechaHasta + ", idNivCarga=" + idNivCarga + ", idNivAutoriz="
				+ idNivAutoriz + ", maInclExcl=" + maInclExcl + ", txAviso=" + txAviso + ", idOperEspe=" + idOperEspe
				+ ", meDiasInterv=" + meDiasInterv + ", centrosCosto=" + centrosCosto + ", accion=" + accion
				+ ", descOscar=" + descOscar + ", idCentroCostosFijo=" + idCentroCostosFijo + "]";
	}
}