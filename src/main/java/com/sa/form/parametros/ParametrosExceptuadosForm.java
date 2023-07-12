package com.sa.form.parametros;

import org.apache.struts.action.ActionForm;

public class ParametrosExceptuadosForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String motivoUsuario;
	private String descripcionNombre;
	private String hasta;
	private String desde;
	private String estado;
	private String accion;
	private String desMotivo;
	private String descripcionCodigo;
	private String marca;
	public void clear() {
		this.motivoUsuario = null;
		this.descripcionNombre = null;
		this.hasta = null;
		this.desde = null;
		this.estado = null;
		this.desMotivo = null;
		this.descripcionCodigo = null;
		this.marca = null;
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

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
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

	public String getDesMotivo() {
		return desMotivo;
	}

	public void setDesMotivo(String desMotivo) {
		this.desMotivo = desMotivo;
	}

	public String getDescripcionCodigo() {
		return descripcionCodigo;
	}

	public void setDescripcionCodigo(String descripcionCodigo) {
		this.descripcionCodigo = descripcionCodigo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
}