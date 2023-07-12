package com.sa.entities;

import java.io.InputStream;

public class Archivo {
	private String nomArchivo;
	private String idu;
	private InputStream inputStream;

	public String getNomArchivo() {
		return nomArchivo;
	}

	public void setNomArchivo(String nomArchivo) {
		this.nomArchivo = nomArchivo;
	}

	public String getIdu() {
		return idu;
	}

	public void setIdu(String idu) {
		this.idu = idu;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}