package com.tallervehiculos.uth.data.entity;

import jakarta.persistence.Entity;

@Entity
public class repuestos extends AbstractEntity {

    private Integer id_repuesto;
    private String nombre_repuesto;
    private String precio;
    
    
	public Integer getId_repuesto() {
		return id_repuesto;
	}
	public void setId_repuesto(Integer id_repuesto) {
		this.id_repuesto = id_repuesto;
	}
	public String getNombre_repuesto() {
		return nombre_repuesto;
	}
	public void setNombre_repuesto(String nombre_repuesto) {
		this.nombre_repuesto = nombre_repuesto;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
    
    
    
}