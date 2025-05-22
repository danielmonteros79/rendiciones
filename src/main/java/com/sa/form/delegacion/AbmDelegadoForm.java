package com.sa.form.delegacion;

import javax.servlet.ServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AbmDelegadoForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String delegadoUser;
	private String delegadoNombre;
	private String delegadoCentroCostos;
	private String delegadoSector;
	private String feDesde;
	private String feDesdeOld;
	private String feHasta;
	private String feHastaOld;
	private String informe;
	private String accion;
	private String estado;
	private String opcion;
	private String fechaAlta;
	private String userAlta;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDelegadoUser() {
		return delegadoUser;
	}

	public void setDelegadoUser(String delegadoUser) {
		this.delegadoUser = delegadoUser;
	}

	public String getDelegadoCentroCostos() {
		return delegadoCentroCostos;
	}

	public void setDelegadoCentroCostos(String delegadoCentroCostos) {
		this.delegadoCentroCostos = delegadoCentroCostos;
	}

	public String getDelegadoSector() {
		return delegadoSector;
	}

	public void setDelegadoSector(String delegadoSector) {
		this.delegadoSector = delegadoSector;
	}

	public String getFeDesde() {
		return feDesde;
	}

	public void setFeDesde(String feDesde) {
		this.feDesde = feDesde;
	}

	public String getFeHasta() {
		return feHasta;
	}

	public void setFeHasta(String feHasta) {
		this.feHasta = feHasta;
	}

	public String getInforme() {
		return informe;
	}

	public void setInforme(String informe) {
		this.informe = informe;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public void setDelegadoNombre(String delegadoNombre) {
		this.delegadoNombre = delegadoNombre;
	}

	public String getDelegadoNombre() {
		return delegadoNombre;
	}

	
	public String getFeDesdeOld() {
		return feDesdeOld;
	}

	public void setFeDesdeOld(String feDesdeOld) {
		this.feDesdeOld = feDesdeOld;
	}

	public String getFeHastaOld() {
		return feHastaOld;
	}

	public void setFeHastaOld(String feHastaOld) {
		this.feHastaOld = feHastaOld;
	}

	public void clearData() {
		// TODO Auto-generated method stub
//		super.reset(arg0, arg1);
		this.delegadoUser = "";
		this.delegadoNombre = "";
		this.delegadoCentroCostos = "";
		this.delegadoSector = "";
		this.feDesde = "";
		this.feHasta = "";
		this.estado = "";
		this.accion = "I";
		
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getUserAlta() {
		return userAlta;
	}

	public void setUserAlta(String userAlta) {
		this.userAlta = userAlta;
	}

}
