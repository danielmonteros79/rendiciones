package com.sa.entities;

public enum TipoPerfil {

	VIEW_ALL ("Rendiciones, Delegacion, Aprobacion, Parametros, Cierre, Resumen"),
	VIEW_ALL_LESS_PARAMS_CIERRE("Rendiciones, Delegacion, Aprobacion, Resumen"),
	VIEW_ALL_LESS_CIERRE ("Rendiciones, Delegacion, Aprobacion, Parametros, Resumen"),
	VIEW_ALL_LESS_PARAMS ("Rendiciones, Delegacion, Aprobacion, Cierre, Resumen"),
	VIEW_APROBACION ("Aprobacion"),
	VIEW_APROBACION_DELEGADO ("Aprobacion, Resumen"),
	VIEW_REND_DELEGADO ("Rendiciones, Resumen"),
	VIEW_REND_APROB_DELEGADO ("Rendiciones, Aprobacion, Resumen");
	
	private String pantalla;
	
	private TipoPerfil(String campo) {
		this.setPantalla(campo);
	}

	public void setPantalla(String pantalla) {
		this.pantalla = pantalla;
	}

	public String getPantalla() {
		return pantalla;
	}
}