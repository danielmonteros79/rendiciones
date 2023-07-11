package com.sa.form;

import org.apache.struts.action.ActionForm;

public class AlertaForm extends ActionForm {
	private static final long serialVersionUID = -6695247101936377048L;
	private String nombreUsuario;
	private String user;
	private String codMotivo;
	private String motivo;
	private String fechaDesde;
	private String fechaHasta;
	private String descripcion;
	private Integer idRendicion;
	private String accion;
	private String idu;
	private String codAdea;
	private String html;
	private String nameFile = "rendicion";
	private String fileType;
	private String filePath = "\\C:\\Users\\usuario\\Desktop\\";
	private String usuarioRend;
	private String fechaHoy;
	private String aviso;
	private String alerta;
	private String alertaDetalle;

	
	public void reset() {
		this.motivo = null;
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.descripcion = null;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public AlertaForm() {

	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(Object object) {
		this.nombreUsuario = (String) object;
	}

	public String getUser() {
		return user;
	}

	public void setUser(Object object) {
		this.user = (String) object;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdRendicion() {
		return idRendicion;
	}

	public void setIdRendicion(Integer idRendicion) {
		this.idRendicion = idRendicion;
	}

	public void setUser(String user) {
		this.user = user;
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


	public String getIdu() {
		return idu;
	}

	public void setIdu(String idu) {
		this.idu = idu;
	}

	public String getCodAdea() {
		return codAdea;
	}

	public void setCodAdea(String codAdea) {
		this.codAdea = codAdea;
	}

	public String getCodMotivo() {
		return codMotivo;
	}

	public void setCodMotivo(String codMotivo) {
		this.codMotivo = codMotivo;
	}

	public void setUsuarioRend(String usuarioRend) {
		this.usuarioRend = usuarioRend;
	}

	public String getUsuarioRend() {
		return usuarioRend;
	}


	public void setFechaHoy(String fechaHoy) {
		this.fechaHoy = fechaHoy;
	}

	public String getFechaHoy() {
		return fechaHoy;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public String getAviso() {
		return aviso;
	}


	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}

	public String getAlertaDetalle() {
		return alertaDetalle;
	}

	public void setAlertaDetalle(String alertaDetalle) {
		this.alertaDetalle = alertaDetalle;
	}





}