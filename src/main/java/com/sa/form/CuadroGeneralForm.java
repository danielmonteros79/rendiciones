package com.sa.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.sa.entities.ComboMotivo;

public class CuadroGeneralForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String user;
	private String ccostos;
	private String estado;
	private String cantRen;
	private int montoTotal;
	private List<ComboMotivo> comboMotivo = new ArrayList<ComboMotivo>();
	private String glg;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCcostos() {
		return ccostos;
	}
	public void setCcostos(String ccostos) {
		this.ccostos = ccostos;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCantRen() {
		return cantRen;
	}
	public void setCantRen(String cantRen) {
		this.cantRen = cantRen;
	}
	public List<ComboMotivo> getComboMotivo() {
		return comboMotivo;
	}

	public void setComboMotivo(List<ComboMotivo> comboMotivo) {
		this.comboMotivo = comboMotivo;
	}
	public int getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(int montoTotal) {
		this.montoTotal = montoTotal;
	}
	public String getGlg() {
		return glg;
	}
	public void setGlg(String glg) {
		this.glg = glg;
	}
}