package com.sa.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;

public class RendicionAvisoForm extends ActionForm {
	private static final long serialVersionUID = -6695247101936377048L;
	private String action;
	private String accion;
	private FormFile archivo;
	private List<Archivo> archivosASubir;
	private Rendicion rendicion;
	private Usuario usuario;
	 
	
	public void clean() {
		accion = null;
		archivo = null;
		archivosASubir = new ArrayList<Archivo>();
		rendicion = new Rendicion();
		usuario = null;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public FormFile getArchivo() {
	    if (!archivo.getFileName().matches("^[a-zA-Z0-9._-]+$")) {
	        throw new IllegalArgumentException("Nombre de archivo inválido.");
	    }
	    return archivo;
	}

	public void setArchivo(FormFile archivo) {
		this.archivo = archivo;
	}

	public List<Archivo> getArchivosASubir() {
		return archivosASubir;
	}

	public void setArchivosASubir(List<Archivo> archivosASubir) {
		this.archivosASubir = archivosASubir;
	}

	public Rendicion getRendicion() {
		return rendicion;
	}

	public void setRendicion(Rendicion rendicion) {
		this.rendicion = rendicion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}