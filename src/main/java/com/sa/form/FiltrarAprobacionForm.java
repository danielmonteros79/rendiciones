package com.sa.form;

import org.apache.struts.action.ActionForm;

public class FiltrarAprobacionForm extends ActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6695247101936377048L;
	private String user;
	private String motivo;
	private String estado;
	private String idRendicion;
	private String tipoAlerta;

	public FiltrarAprobacionForm(){
		
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getIdRendicion() {
		return idRendicion;
	}

	public void setIdRendicion(String idRendicion) {
		this.idRendicion = idRendicion;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getEstado() {
		return estado;
	}
	
	
	public String getTipoAlerta() {
		return tipoAlerta;
	}
	
	public void setTipoAlerta(String tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}
	

	
}
