package com.sa.form.parametros;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.sa.entities.OSCAR;
import com.sa.entities.Ristra;

public class ParametrosGastosForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String descripcionGasto;
	private String motivo;
	private Ristra ristra;
	private String bimon;
	private String comprob;
	private String autoriz;
	private OSCAR oscar;
	private String observ;
	private String maInclExcl;
	private String antiguedad;
	private String estado;
	private String accion;
	private String avisoMonto;
	private String idCentroCostos;
	private String idNivAutoriz;
	private String plazoAprob;
	private boolean readonly;
	private boolean back;
	private List<String> centrosCosto;
	
	private String descripcionMotivo;
	private String detalleRistra;

	public void clear() {
		this.codigo = null;
		this.descripcionGasto = null;
		this.motivo = null;
		this.ristra = new Ristra();
		this.bimon = null;
		this.comprob = null;
		this.autoriz = null;
		this.oscar = new OSCAR();
		this.observ = null;
		this.maInclExcl = null;
		this.antiguedad = null;
		this.estado = null;
		this.avisoMonto = null;
		this.idCentroCostos = null;
		this.idNivAutoriz = null;
		this.plazoAprob = null;
		this.readonly = false;
		this.centrosCosto = new ArrayList<String>();
		this.descripcionMotivo = null;
		this.detalleRistra = null;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcionGasto() {
		return descripcionGasto;
	}

	public void setDescripcionGasto(String descripcionGasto) {
		this.descripcionGasto = descripcionGasto;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Ristra getRistra() {
		return ristra;
	}

	public void setRistra(String ristra) {
		this.ristra = new Ristra(ristra);
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

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public OSCAR getOscar() {
		return oscar;
	}

	public void setOscar(OSCAR oscar) {
		this.oscar = oscar;
	}

	public String getMaInclExcl() {
		return maInclExcl;
	}

	public void setMaInclExcl(String maInclExcl) {
		this.maInclExcl = maInclExcl;
	}

	public String getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(String antiguedad) {
		this.antiguedad = antiguedad;
	}

	public String getAvisoMonto() {
		return avisoMonto;
	}

	public void setAvisoMonto(String avisoMonto) {
		this.avisoMonto = avisoMonto;
	}

	public String getIdCentroCostos() {
		return idCentroCostos;
	}

	public void setIdCentroCostos(String idCentroCostos) {
		this.idCentroCostos = idCentroCostos;
	}

	public String getIdNivAutoriz() {
		return idNivAutoriz;
	}

	public void setIdNivAutoriz(String idNivAutoriz) {
		this.idNivAutoriz = idNivAutoriz;
	}

	public String getPlazoAprob() {
		return plazoAprob;
	}

	public void setPlazoAprob(String plazoAprob) {
		this.plazoAprob = plazoAprob;
	}

	public boolean getReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
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

	public String getDescripcionMotivo() {
		return descripcionMotivo;
	}

	public void setDescripcionMotivo(String descripcionMotivo) {
		this.descripcionMotivo = descripcionMotivo;
	}

	public String getDetalleRistra() {
		return detalleRistra;
	}

	public void setDetalleRistra(String detalleRistra) {
		this.detalleRistra = detalleRistra;
	}
	
}