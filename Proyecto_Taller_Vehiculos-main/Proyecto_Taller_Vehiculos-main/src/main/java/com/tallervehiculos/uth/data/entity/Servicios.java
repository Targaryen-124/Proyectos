package com.tallervehiculos.uth.data.entity;

import jakarta.persistence.Entity;

@Entity
public class Servicios extends AbstractEntity {

    private Integer id_servicio;
    private String nombre_servicio;
    private String subservicio;
    private String costo;
    
    
	public Integer getId_servicio() {
		return id_servicio;
	}
	public void setId_servicio(Integer id_servicio) {
		this.id_servicio = id_servicio;
	}
	public String getNombre_servicio() {
		return nombre_servicio;
	}
	public void setNombre_servicio(String nombre_servicio) {
		this.nombre_servicio = nombre_servicio;
	}
	public String getSubservicio() {
		return subservicio;
	}
	public void setSubservicio(String subservicio) {
		this.subservicio = subservicio;
	}
	public String getCosto() {
		return costo;
	}
	public void setCosto(String costo) {
		this.costo = costo;
	}

    
}