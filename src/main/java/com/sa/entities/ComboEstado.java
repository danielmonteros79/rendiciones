package com.sa.entities;

import java.util.ArrayList;
import java.util.List;

public class ComboEstado {
	private String id;
	private String descripcion;
	private List<ComboEstado> estado ;
	public ComboEstado() {
		// TODO Auto-generated constructor stub
//		estado = new ArrayList<ComboEstado>();
//		estado.add(new ComboEstado("1", "Pendiente"));
//		estado.add(new ComboEstado("2", "Aprobado"));
//		estado.add(new ComboEstado("3", "Generado"));
		
	}

	public ComboEstado(String id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<ComboEstado> getEstadoRendiciones() {
		// TODO Auto-generated method stub
		return this.estado;
	}
}