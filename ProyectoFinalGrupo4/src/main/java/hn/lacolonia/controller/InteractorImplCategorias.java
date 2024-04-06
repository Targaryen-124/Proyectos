package hn.lacolonia.controller;

import hn.lacolonia.data.Categoria;
import hn.lacolonia.data.CategoriasResponse;
import hn.lacolonia.model.DatabaseRepositoryImpl;
import hn.lacolonia.views.categorias.ViewModelCategorias;

public class InteractorImplCategorias implements InteractorCategorias {

	private DatabaseRepositoryImpl modelo;
	private ViewModelCategorias vista;
	
	public InteractorImplCategorias(ViewModelCategorias view) {
		super();
		this.vista = view;
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
	}
	
	@Override
	public void consultarCategorias() {
		try {
			CategoriasResponse respuesta = this.modelo.consultarCategorias();
			if(respuesta == null || respuesta.getCount() == 0 || respuesta.getItems() == null) {
				this.vista.mostrarMensajeError("No hay categorias a mostrar");
			}else {
				this.vista.mostrarCategoriasEnGrid(respuesta.getItems());
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
		
	}
	
	@Override
	public void crearCategorias(Categoria nueva) {
		try {
			boolean creada = this.modelo.crearCategorias(nueva);
			if(creada == true) {
				this.vista.mostrarMensajeExito("Categoria creada exitosamente");
			}else {
				this.vista.mostrarMensajeError("Hay un problema al crear la categoria");
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
		
	}
	
	@Override
	public void actualizarCategorias(Categoria cambiar) {
		try {
			boolean modificada = this.modelo.actualizarCategorias(cambiar);
			if(modificada == true) {
				this.vista.mostrarMensajeExito("Categoria modificada exitosamente");
			}else {
				this.vista.mostrarMensajeError("Hay un problema al modificar la categoria");
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
		
	}
	
}
