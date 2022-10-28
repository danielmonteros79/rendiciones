package com.sa.form;

import org.apache.struts.action.ActionForm;

public class RendicionForm extends ActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6695247101936377048L;
	private String nombreUsuario;
	private String user;
	private String codMotivo;
	private String motivo;
	private String fechaDesde;
	private String fechaHasta;
	private String descripcion;
	private Integer idRendicion;
	private int costos;
	private String accion;
	private String sector;
	private Integer estado;
	private String estadoRend;
	private String idu;
	private String codAdea;
	private String html;
	private String nameFile = "rendicion";
	private String fileType;
	private String filePath = "\\C:\\Users\\usuario\\Desktop\\";
	private String usuarioRend;
	private String glg;
	private String fechaHoy;
	private String usuarioAprobador;
	private String descripcionEstado;
	private Integer cantDias;
	private String motivoRechazo;
	private String fechaUltimaModificacion;
	private String aviso;
	private String linkThuban;
	private String costosDestino;
	
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
	public RendicionForm(){
		
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
	public int getCostos() {
		return costos;
	}
	public void setCostos(int costos) {
		this.costos = costos;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
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
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public String getEstadoRend() {
		return estadoRend;
	}
	public void setEstadoRend(String estadoRend) {
		this.estadoRend = estadoRend;
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
	public void setGlg(String glg) {
		this.glg = glg;
	}
	public String getGlg() {
		return glg;
	}
	public void setFechaHoy(String fechaHoy) {
		this.fechaHoy = fechaHoy;
	}
	public String getFechaHoy() {
		return fechaHoy;
	}
	public void setUsuarioAprobador(String usuarioAprobador) {
		this.usuarioAprobador = usuarioAprobador;
	}
	public String getUsuarioAprobador() {
		return usuarioAprobador;
	}
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	public void setCantDias(Integer cantDias) {
		this.cantDias = cantDias;
	}
	public Integer getCantDias() {
		return cantDias;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	public String getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	public void setAviso(String aviso) {
		this.aviso = aviso;
	}
	public String getAviso() {
		return aviso;
	}
	public void setLinkThuban(String linkThuban) {
		this.linkThuban = linkThuban;
	}
	public String getLinkThuban() {
		return linkThuban;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getCostosDestino() {
		return costosDestino;
	}
	public void setCostosDestino(String costosDestino) {
		this.costosDestino = costosDestino;
	}
}