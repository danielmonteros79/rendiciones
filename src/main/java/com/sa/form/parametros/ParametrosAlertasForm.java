package com.sa.form.parametros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.sa.entities.ComboOpcion;

public class ParametrosAlertasForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String codAlerta;
	private String montCant;
	private String impCant;
	private String rend;
	private String periodo;
	private String nivMin;
	private String nivMax;
	private String estado;
	private String accion;
	private String criticidad;
	private String txAviso;
	private String codMotivo;
	private String codGasto;
	private String timeStamp;
	private Map<String, String> mapGastoMotivo = new HashMap<String, String>();
	private Map<String, List<ComboOpcion>> mapMotivoGastos = new HashMap<String, List<ComboOpcion>>();
	private List<ComboOpcion> cmbGasto = new ArrayList<ComboOpcion>();
	private List<ComboOpcion> cmbMotivo = new ArrayList<ComboOpcion>();

	public void clear() {
		this.codAlerta = null;
		this.codMotivo = null;
		this.codGasto = null;
		this.montCant = null;
		this.impCant = null;
		this.rend = null;
		this.periodo = null;
		this.nivMin = null;
		this.nivMax = null;
		this.estado = null;
		this.criticidad = null;
		this.mapGastoMotivo = null;
		this.mapMotivoGastos = null;
		this.cmbGasto = null;
		this.cmbMotivo = null;
		this.txAviso = null;
	}
	
	public String getMontCant() {
		return montCant;
	}

	public void setMontCant(String estado) {
		this.montCant = estado;
	}
	
	public String getCodAlerta() {
		return codAlerta;
	}

	public void setCodAlerta(String codAlerta) {
		this.codAlerta = codAlerta;
	}

	public String getRend() {
		return rend;
	}

	public void setRend(String codSup) {
		this.rend = codSup;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String codFirma) {
		this.periodo = codFirma;
	}

	public String getNivMin() {
		return nivMin;
	}

	public void setNivMin(String codAprobacionGlg) {
		this.nivMin = codAprobacionGlg;
	}

	public String getNivMax() {
		return nivMax;
	}

	public void setNivMax(String idOscar) {
		this.nivMax = idOscar;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String fechaDesde) {
		this.estado = fechaDesde;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getCriticidad() {
		return criticidad;
	}

	public void setCriticidad(String criticidad) {
		this.criticidad = criticidad;
	}

	public String getTxAviso() {
		return txAviso;
	}

	public void setTxAviso(String txAviso) {
		this.txAviso = txAviso;
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

	public String getImpCant() {
		return impCant;
	}

	public void setImpCant(String impCant) {
		this.impCant = impCant;
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

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}