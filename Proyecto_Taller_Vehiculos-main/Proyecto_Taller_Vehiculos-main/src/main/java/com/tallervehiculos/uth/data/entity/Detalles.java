package com.tallervehiculos.uth.data.entity;

import java.util.Collection;

import jakarta.persistence.Entity;

@Entity
public class Detalles extends AbstractEntity {

    private Integer id_ordenSR;
    private String Cliente;
    private String placa;
    private String total;
  
	private String Servicios;
    private String repuestos;

    public String getTotal() {
  		return total;
  	}
  	public void setTotal(String total) {
  		this.total = total;
  	}
    public Integer getId_ordenSR() {
		return id_ordenSR;
	}
	public void setId_ordenSR(Integer id_ordenSR) {
		this.id_ordenSR = id_ordenSR;
	}
	public String getCliente() {
		return Cliente;
	}
	public void setCliente(String cliente) {
		Cliente = cliente;
	}
	/*public Collection<String> getServicios() {
		return Servicios;
	}
	public void setServicios(Collection<String> servicios) {
		Servicios = servicios;
	}
	public Collection<String> getRepuestos() {
		return repuestos;
	}
	public void setRepuestos(Collection<String> repuestos) {
		this.repuestos = repuestos;
	}*/
	public String getPlaca() {
        return placa;
    }
    public String getServicios() {
		return Servicios;
	}
	public void setServicios(String servicios) {
		Servicios = servicios;
	}
	public String getRepuestos() {
		return repuestos;
	}
	public void setRepuestos(String repuestos) {
		this.repuestos = repuestos;
	}
	public void setPlaca(String placa) {
        this.placa = placa;
    }

}
