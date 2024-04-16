package com.tallervehiculos.uth.data.service;


import com.tallervehiculos.uth.data.entity.ResponseTaller;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface TallerRepository {

	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/allantorres_pa/vehiculos/registro_vehiculo/")
	Call<ResponseTaller> obtenerVehiculo();
	
	
	
	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset: utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
	@GET("/pls/apex/allantorres_pa/vehiculos/orden_reparacion/")
	Call<ResponseTaller> obtenerOrden();
	
}
