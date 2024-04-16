package com.tallervehiculos.uth.data.entity;

import jakarta.persistence.Entity;

@Entity
public class OrdenSR extends AbstractEntity {

    private Integer id_ordensr;
    private Integer orden_id;
    private Integer servicio_id;
    private Integer repuesto_id;
    
	public Integer getId_ordensr() {
		return id_ordensr;
	}
	public void setId_ordensr(Integer id_ordensr) {
		this.id_ordensr = id_ordensr;
	}
	public Integer getOrden_id() {
		return orden_id;
	}
	public void setOrden_id(Integer orden_id) {
		this.orden_id = orden_id;
	}
	public Integer getServicio_id() {
		return servicio_id;
	}
	public void setServicio_id(Integer servicio_id) {
		this.servicio_id = servicio_id;
	}
	public Integer getRepuesto_id() {
		return repuesto_id;
	}
	public void setRepuesto_id(Integer repuesto_id) {
		this.repuesto_id = repuesto_id;
	}

}