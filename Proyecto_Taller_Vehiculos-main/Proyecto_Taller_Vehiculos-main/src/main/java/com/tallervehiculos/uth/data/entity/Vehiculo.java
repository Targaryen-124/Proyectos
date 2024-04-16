package com.tallervehiculos.uth.data.entity;

import jakarta.persistence.Entity;

@Entity
public class Vehiculo extends AbstractEntity {

    private String id_vehiculo;
    private String nombre_cliente;
    private String marca;
    private String modelo;
    private String placa;

    public String getId_vehiculo() {
        return id_vehiculo;
    }
    public void setId_vehiculo(String id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }
    public String getNombre_cliente() {
        return nombre_cliente;
    }
    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }

}
