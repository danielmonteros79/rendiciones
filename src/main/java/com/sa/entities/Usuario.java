package com.sa.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.text.StringEscapeUtils;

public class Usuario {
	private String idUser;
	private int perfil;
	private TipoPerfil tipoPerfil;
	private String nombre;
	private int ccostos;
	private String sector;
	private boolean manejaFacultades;
	private List<Usuario> delegadosAsignados;
	private String facultades;
	private Set<Integer> glgAprobacion;

	public Usuario(String idUser, String perfil, String nombre, int ccostos, String sector, List<Usuario> delegados) {
	    this.idUser = idUser;
	    this.setTipoPerfil(perfil);
	    this.nombre = nombre;
	    this.ccostos = ccostos;
	    this.sector = sector;
	    this.delegadosAsignados = delegados;
	    this.facultades = perfil.length() > 0 && perfil.substring(0, 1).equals("S") ? "S" : "N";
	    this.glgAprobacion = new HashSet<>();
	}

	public List<Usuario> getDelegadosAsignados() {
		return this.delegadosAsignados;
	}

	public void setDelegadosAsignados(List<Usuario> delegadosAsignados) {
		this.delegadosAsignados = delegadosAsignados;
	}

	public String getIdUser() {
	    return this.idUser != null ? this.idUser.replaceAll("[^a-zA-Z0-9]", "") : null;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public int getPerfil() {
		return this.perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	public String getNombre() {
		if (this.nombre == null) return null;

		String decoded = StringEscapeUtils.unescapeHtml4(this.nombre);
		return decoded.replaceAll("[^a-zA-Z0-9 ñÑüÜ]", "");
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSector() {
		return this.sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public int getCcostos() {
		return this.ccostos;
	}

	public void setCcostos(int ccostos) {
		this.ccostos = ccostos;
	}

	public void setTipoPerfil(String tipoPerfil) {
		if (tipoPerfil.equalsIgnoreCase("SS")) {
			this.tipoPerfil = TipoPerfil.VIEW_ALL;
		} else if (tipoPerfil.equalsIgnoreCase("SN")) {
			this.tipoPerfil = TipoPerfil.VIEW_ALL_LESS_PARAMS;
		} else if (tipoPerfil.equalsIgnoreCase("NS")) {
			this.tipoPerfil = TipoPerfil.VIEW_ALL_LESS_CIERRE;
		} else if (tipoPerfil.equalsIgnoreCase("NN")) {
			this.tipoPerfil = TipoPerfil.VIEW_ALL_LESS_PARAMS_CIERRE;
		} else if (tipoPerfil.equalsIgnoreCase("99")) {
			this.tipoPerfil = TipoPerfil.VIEW_APROBACION;
		} else if (tipoPerfil.equalsIgnoreCase("DELEG_APROB")) {
			this.tipoPerfil = TipoPerfil.VIEW_APROBACION_DELEGADO;
		} else if (tipoPerfil.equalsIgnoreCase("DELEG_REND_APROB")) {
			this.tipoPerfil = TipoPerfil.VIEW_REND_APROB_DELEGADO;
		} else if (tipoPerfil.equalsIgnoreCase("DELEG_REND")) {
			this.tipoPerfil = TipoPerfil.VIEW_REND_DELEGADO;
		} else if (tipoPerfil.equalsIgnoreCase("S")) {
			this.manejaFacultades = true;
		} else if (tipoPerfil.equalsIgnoreCase("N")) {
			this.manejaFacultades = false;
		}

	}

	public TipoPerfil getTipoPerfil() {
		return this.tipoPerfil;
	}

	public void setManejaFacultades(boolean manejaFacultades) {
		this.manejaFacultades = manejaFacultades;
	}

	public boolean isManejaFacultades() {
		return this.manejaFacultades;
	}

	public String getFacultades() {
		return this.facultades;
	}

	public void setFacultades(String facultades) {
		this.facultades = facultades;
	}

	public Set<Integer> getGlgAprobacion() {
		return this.glgAprobacion;
	}

	@Override
	public String toString() {
		return "Usuario [idUser=" + idUser + ", perfil=" + perfil + ", tipoPerfil=" + tipoPerfil + ", nombre=" + nombre
				+ ", ccostos=" + ccostos + ", sector=" + sector + ", manejaFacultades=" + manejaFacultades
				+ ", delegadosAsignados=" + delegadosAsignados + ", facultades=" + facultades + ", glgAprobacion="
				+ glgAprobacion + "]";
	}
	
}