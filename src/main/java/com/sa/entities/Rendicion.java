package com.sa.entities;

import java.util.Date;
import java.util.List;

import org.aspectj.org.eclipse.jdt.core.dom.ThisExpression;

public class Rendicion {
	private Integer id;
	private String motivo;
	private String descripcion;
	private Date fechaDesde;
	private Date fechaHasta;
	private String importe;
	private String importeTarjeta;
	private String opciones;
	private String comentarios;
	private List<Gastos> gastosRendicion;
	private String usuarioRendicion;
	private String estado;
	private Integer idMotivo;
	private String codMotivo;
	private String statusColor;
	private String idu;
	private String adea;
	private String caratula;
	private String usuarioAprobador;
	private String descripcionEstado;
	private String codUsuarioAprobador;
	private String motivoRechazo;
	private String descripcionMotivo;
	private String nombreUsuarioRendicion;
	private String fechaUltimaModificacion;
	private String aviso;
	private String glg;
	private String journal;
	private String costosDestino;
	private String alerta;

	public Rendicion() {
	}

	public Rendicion(Integer id, String motivo, String descripcion, Date fechaDesde, Date fechaHasta, String importe,
			List<Gastos> gastosRendicion, String estado, String usuarioRendicion) {
		this.id = id;
		this.motivo = motivo;
		this.descripcion = descripcion;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.importe = importe;
		this.gastosRendicion = gastosRendicion;
		this.estado = estado;
		this.usuarioRendicion = usuarioRendicion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getOpciones() {
		return opciones;
	}

	public void setOpciones(String opciones) {
		this.opciones = opciones;
	}

	public List<Gastos> getGastosRendicion() {
		return gastosRendicion;
	}

	public void setGastosRendicion(List<Gastos> gastosRendicion) {
		this.gastosRendicion = gastosRendicion;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuarioRendicion() {
		return usuarioRendicion;
	}

	public void setUsuarioRendicion(String usuarioRendicion) {
		this.usuarioRendicion = usuarioRendicion;
	}

	public Integer getIdMotivo() {
		return idMotivo;
	}

	public void setIdMotivo(Integer idMotivo) {
		this.idMotivo = idMotivo;
	}

	public String getCodMotivo() {
		return codMotivo;
	}

	public void setCodMotivo(String codMotivo) {
		this.codMotivo = codMotivo;
	}

	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}

	public String getStatusColor() {
		return statusColor;
	}

	public String getIdu() {
		return idu;
	}

	public void setIdu(String idu) {
		this.idu = idu;
	}

	public String getAdea() {
		return adea;
	}

	public void setAdea(String adea) {
		this.adea = adea;
	}

	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}

	public String getCaratula() {
		return caratula;
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

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setCodUsuarioAprobador(String codUsuarioAprobador) {
		this.codUsuarioAprobador = codUsuarioAprobador;
	}

	public String getCodUsuarioAprobador() {
		return codUsuarioAprobador;
	}

	public void setDescripcionMotivo(String descripcionMotivo) {
		this.descripcionMotivo = descripcionMotivo;
	}

	public String getDescripcionMotivo() {
		return descripcionMotivo;
	}

	public void setNombreUsuarioRendicion(String nombreUsuarioRendicion) {
		this.nombreUsuarioRendicion = nombreUsuarioRendicion;
	}

	public String getNombreUsuarioRendicion() {
		return nombreUsuarioRendicion;
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

	public void setGlg(String glg) {
		this.glg = glg;
	}

	public String getGlg() {
		return glg;
	}

	public String getImporteTarjeta() {
		return importeTarjeta;
	}

	public void setImporteTarjeta(String importeTarjeta) {
		this.importeTarjeta = importeTarjeta;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getCostosDestino() {
		return costosDestino;
	}

	public void setCostosDestino(String costosDestino) {
		this.costosDestino = costosDestino;
	}

	public String getAlerta() {
		return alerta;
	}

	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}
}
