package com.sa.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.sa.entities.Rendicion;

public class CierreForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Rendicion> rendicion;
	private Integer id;
	private String estado;
	private String cmboMotivo;
	private String motivoRechazo;
	public List<Rendicion> getRendicion() {
		return rendicion;
	}
	public void setRendicion(List<Rendicion> rendicion) {
		this.rendicion = rendicion;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCmboMotivo() {
		return cmboMotivo;
	}
	public void setCmboMotivo(String cmboMotivo) {
		this.cmboMotivo = cmboMotivo;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	
}
