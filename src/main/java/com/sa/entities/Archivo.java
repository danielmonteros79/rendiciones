package com.sa.entities;

import java.io.InputStream;

public class Archivo {
	private String nomArchivo;
	private String idu;
	private String tipo;
	private String base64File;
	private String id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getBase64File() {
		return base64File;
	}

	public void setBase64File(String base64File) {
		this.base64File = base64File;
	}
}