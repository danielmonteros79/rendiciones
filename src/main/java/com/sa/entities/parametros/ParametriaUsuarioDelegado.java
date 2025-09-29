package com.sa.entities.parametros;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.log.SysoCounter;
import com.sa.entities.DelegacionAccion;
import com.sa.util.ParamsConstants;

public class ParametriaUsuarioDelegado {
	private Integer id;
	private String usuario;
	private String delegadoUser;
	private String delegadoNombre;
	private String delegadoCentroCostos;
	private String delegadoSector;
	private String delegadoAccion;
	private String delegadoAccionDesc;
	// private List<DelegacionAccion> acciones = new
	// ArrayList<DelegacionAccion>();
	// private List<String> acciones = new ArrayList<String>();
	private Date feDesde;
	private Date feHasta;
	private String delegadoEstado;
	private String delegadoInforme;
	private String fechaAlta;
	private String usuarioAlta;
	private String fechaModif;
	private String opciones;

	// private List<ParametriaUsuarioDelegado> listaUserDelegado;

	public ParametriaUsuarioDelegado() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getDelegadoNombre() {
		return delegadoNombre;
	}

	public void setDelegadoNombre(String delegadoNombre) {
		this.delegadoNombre = delegadoNombre;
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

	public String getDelegadoAccion() {
		return delegadoAccion;
	}

	public void setDelegadoAccion(String delegadoAccion) {
		for (DelegacionAccion accion : ParamsConstants
				.getAccionesDelegaciones()) {
			if (accion.getAccion().equalsIgnoreCase(delegadoAccion)) {
				this.delegadoAccion = accion.getAccion();
				setDelegadoAccionDesc(accion.getAccionDesc());
				break;
			}
		}
	}

	public Date getFeDesde() {
		return feDesde;
	}

	public void setFeDesde(Date feDesde) {
		this.feDesde = feDesde;
	}

	public Date getFeHasta() {
		return feHasta;
	}

	public void setFeHasta(Date feHasta) {
		this.feHasta = feHasta;
	}

	public String getDelegadoEstado() {
		return delegadoEstado;
	}

	public void setDelegadoEstado(String delegadoEstado) {
		this.delegadoEstado = delegadoEstado;
	}

	public String getDelegadoInforme() {
		return delegadoInforme;
	}

	public void setDelegadoInforme(String delegadoInforme) {
		this.delegadoInforme = delegadoInforme;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public String getFechaModif() {
		return fechaModif;
	}

	public void setFechaModif(String fechaModif) {
		this.fechaModif = fechaModif;
	}

	public void setOpciones(String opciones) {
		this.opciones = opciones;
	}

	public String getOpciones() {
		return opciones;
	}

	public void setDelegadoAccionDesc(String delegadoAccionDesc) {
		this.delegadoAccionDesc = delegadoAccionDesc;
	}

	public String getDelegadoAccionDesc() {
		return delegadoAccionDesc;
	}

}
