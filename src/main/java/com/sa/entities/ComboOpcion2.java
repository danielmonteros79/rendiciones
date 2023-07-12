package com.sa.entities;

import java.util.ArrayList;
import java.util.List;

public class ComboOpcion2 {
	private String id;
	private String descripcion;
	private List<ComboOpcion2> estado ;
	public ComboOpcion2() {
		// TODO Auto-generated constructor stub
//		estado = new ArrayList<ComboEstado>();
//		estado.add(new ComboEstado("1", "Pendiente"));
//		estado.add(new ComboEstado("2", "Aprobado"));
//		estado.add(new ComboEstado("3", "Generado"));
		
	}

	public ComboOpcion2(String id, String descripcion) {
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
	public List<ComboOpcion2> getEstadoRendiciones() {
		// TODO Auto-generated method stub
		return this.estado;
	}
}