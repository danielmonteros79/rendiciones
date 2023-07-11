package com.sa.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.sa.entities.ComboDelegado;
import com.sa.entities.ComboOpcion2;

public class FiltroRendicionForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String delegado;
	private String id;
	private String estado;
	private String fechaDesde;
	private String fechaHasta;
	private String opciones;
	private String scan;

	public FiltroRendicionForm() {
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public String getDelegado() {
		return delegado;
	}

	public void setDelegado(String delegado) {
		this.delegado = delegado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpciones() {
		return opciones;
	}

	public void setOpciones(String opciones) {
		this.opciones = opciones;
	}

	public String getScan() {
		return scan;
	}

	public void setScan(String scan) {
		this.scan = scan;
	}
	
}
