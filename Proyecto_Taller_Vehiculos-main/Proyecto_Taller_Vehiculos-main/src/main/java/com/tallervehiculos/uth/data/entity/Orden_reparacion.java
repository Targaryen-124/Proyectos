package com.tallervehiculos.uth.data.entity;

import jakarta.persistence.Entity;

@Entity
public class Orden_reparacion extends AbstractEntity {

    private String id_orden;
    private String vehiculo_id;
    //private String nombre;
	private String descripcion_problema;
    private String estado_reparacion;

    
    /*public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}*/
    public String getId_orden() {
        return id_orden;
    }
    public void setId_orden(String id_orden) {
        this.id_orden = id_orden;
    }
    public String getVehiculo_id() {
        return vehiculo_id;
    }
    public void setVehiculo_id(String vehiculo_id) {
        this.vehiculo_id = vehiculo_id;
    }
    public String getDescripcion_problema() {
        return descripcion_problema;
    }
    public void setDescripcion_problema(String descripcion_problema) {
        this.descripcion_problema = descripcion_problema;
    }
    public String getEstado_reparacion() {
        return estado_reparacion;
    }
    public void setEstado_reparacion(String estado_reparacion) {
        this.estado_reparacion = estado_reparacion;
    }

}
