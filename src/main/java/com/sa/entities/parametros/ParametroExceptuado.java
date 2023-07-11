package com.sa.entities.parametros;

import java.util.Date;

public class ParametroExceptuado {
	private String motivoUsuario;
	private String descripcionNombre;
	private Date hasta;
	private Date desde;
	private String estado;
	private String tipo;
	
	public ParametroExceptuado() {
	}

	public String getMotivoUsuario() {
		return motivoUsuario;
	}

	public void setMotivoUsuario(String motivoUsuario) {
		this.motivoUsuario = motivoUsuario;
	}

	public String getDescripcionNombre() {
		return descripcionNombre;
	}

	public void setDescripcionNombre(String descripcionNombre) {
		this.descripcionNombre = descripcionNombre;
	}

	public Date getHasta() {
		return hasta;
	}

	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}

	public Date getDesde() {
		return desde;
	}

	public void setDesde(Date desde) {
		this.desde = desde;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}