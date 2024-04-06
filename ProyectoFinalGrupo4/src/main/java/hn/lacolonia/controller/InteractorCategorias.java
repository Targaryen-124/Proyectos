package hn.lacolonia.controller;

import hn.lacolonia.data.Categoria;

public interface InteractorCategorias {
	
	void consultarCategorias();
	void crearCategorias(Categoria nueva);
	void actualizarCategorias(Categoria cambiar);
}
