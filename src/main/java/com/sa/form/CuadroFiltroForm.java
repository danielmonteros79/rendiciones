package com.sa.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.sa.entities.ComboMotivo;
import com.sa.entities.ComboOpcion;
import com.sa.entities.ComboOpcion2;

public class CuadroFiltroForm extends ActionForm {
	private static final long serialVersionUID = 1L;

	private String opcion;
	private String nombreUsuario;
	private int costos;
//	private List<String> glg;
//	private List<String> glgSel;
	private List<ComboOpcion> comboGlg = new ArrayList<ComboOpcion>();
	private String fechaDesde;
	private String fechaHasta;
	private String montoDesde;
	private String montoHasta;
	private String codMotivo;
	private String codEstado;
	private String codGlg;
	private String usuario;
	private List<ComboOpcion2> comboEstado = new ArrayList<ComboOpcion2>();
	private List<ComboMotivo> comboMotivo = new ArrayList<ComboMotivo>();

	public CuadroFiltroForm() {
	}

	public void clear() {
		this.opcion = "01";
		this.fechaDesde = "";
		this.fechaHasta = "";
		this.montoDesde = "";
		this.montoHasta = "";
		this.codEstado = "";
		this.codMotivo = "";
		this.codGlg = "";
		this.usuario = "";
//		this.glg = new ArrayList<String>();
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public int getCostos() {
		return costos;
	}

	public void setCostos(int costos) {
		this.costos = costos;
	}

//	public List<String> getGlg() {
//		return glg;
//	}
//
//	public void setGlg(String glg) {
//	}
//
//	public void setGlgList(List<String> glg) {
//		this.glg = glg;
//	}
//
//	public void setGlgI(int index, String value) {
//		this.glg.set(index, value);
//	}
//
//	public String getGlgI(int index) {
//		return this.glg.get(index);
//	}
//
//	public List<String> getGlgSel() {
//		return glgSel;
//	}
//
//	public void setGlgSel(String glgSel) {
//	}
//
//	public void setGlgSel(List<String> glgSel) {
//		this.glgSel = glgSel;
//	}
//
//	public void setGlgSelList(List<String> glgSel) {
//		this.glgSel = glgSel;
//	}
//
//	public void setGlgSelI(int index, String value) {
//		this.glgSel.set(index, value);
//	}
//
//	public String getGlgSelI(int index) {
//		return this.glgSel.get(index);
//	}

	public List<ComboOpcion> getComboGlg() {
		return comboGlg;
	}

	public void setComboGlg(List<ComboOpcion> comboGlg) {
		this.comboGlg = comboGlg;
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

	public String getMontoDesde() {
		return montoDesde;
	}

	public void setMontoDesde(String montoDesde) {
		this.montoDesde = montoDesde;
	}

	public String getMontoHasta() {
		return montoHasta;
	}

	public String getCodMotivo() {
		return codMotivo;
	}

	public void setCodMotivo(String codMotivo) {
		this.codMotivo = codMotivo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<ComboMotivo> getComboMotivo() {
		return comboMotivo;
	}

	public void setComboMotivo(List<ComboMotivo> comboMotivo) {
		this.comboMotivo = comboMotivo;
	}

	public void setMontoHasta(String montoHasta) {
		this.montoHasta = montoHasta;
	}

	public List<ComboOpcion2> getComboEstado() {
		return comboEstado;
	}

	public void setComboEstado(List<ComboOpcion2> comboEstado) {
		this.comboEstado = comboEstado;
	}

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public String getCodGlg() {
		return codGlg;
	}

	public void setCodGlg(String codGlg) {
		this.codGlg = codGlg;
	}
}