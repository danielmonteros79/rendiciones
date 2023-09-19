package com.sa.entities;

import java.util.List;

public class ComboSupervisor {
	private String id;
	private String descripcion;
	private List<ComboSupervisor> supervisor ;
	
	public ComboSupervisor(){
		
	}
	
	public ComboSupervisor(String id, String descripcion){
		super();
		this.id = id;
		this.descripcion= descripcion;
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
	public List<ComboSupervisor> getComprobante() {
		// TODO Auto-generated method stub
		return this.supervisor;
	}
}