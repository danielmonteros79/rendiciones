package com.sa.entities;

import java.util.ArrayList;
import java.util.List;

public class DatosPantallaDinamica {

	private String tipoCampo;
	private String mostrar;
	private String campoObligatorio;
	private String tituloCampo;
	private List<ComboGenerico> combo = new ArrayList<ComboGenerico>();

	public String getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public String getMostrar() {
		return mostrar;
	}

	public void setMostrar(String mostrar) {
		this.mostrar = mostrar;
	}

	public String getCampoObligatorio() {
		return campoObligatorio;
	}

	public void setCampoObligatorio(String campoObligatorio) {
		this.campoObligatorio = campoObligatorio;
	}

	public String getTituloCampo() {
		return tituloCampo;
	}

	public void setTituloCampo(String tituloCampo) {
		this.tituloCampo = tituloCampo;
	}

	public List<ComboGenerico> getOpcionesCombo() {
		return combo;
	}

	public void addOpcionCombo(ComboGenerico combo) {
		this.combo.add(combo);
	}

	public void setOpcionesCombo(List<ComboGenerico> opcionesCombo) {
		this.combo = opcionesCombo;
	}

}