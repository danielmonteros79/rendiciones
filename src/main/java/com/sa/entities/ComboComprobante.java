package com.sa.entities;

import java.util.ArrayList;
import java.util.List;

public class ComboComprobante {
	private String id;
	private String descripcion;
	private List<ComboComprobante> comprobante ;
	
	public ComboComprobante(){
		comprobante = new ArrayList<ComboComprobante>();
		comprobante.add(new ComboComprobante("1", "Factura"));
		comprobante.add(new ComboComprobante("2", "Ticket"));
		comprobante.add(new ComboComprobante("3", "Otro"));
	}
	
	public ComboComprobante(String id, String descripcion){
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
	public List<ComboComprobante> getComprobante() {
		// TODO Auto-generated method stub
		return this.comprobante;
	}
}