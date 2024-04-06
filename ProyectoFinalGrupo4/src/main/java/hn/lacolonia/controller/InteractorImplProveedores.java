package hn.lacolonia.controller;

import hn.lacolonia.data.ProveedoresResponse;
import hn.lacolonia.model.DatabaseRepositoryImpl;
import hn.lacolonia.views.proveedores.ViewModelProveedores;

public class InteractorImplProveedores implements InteractorProveedores {

	private DatabaseRepositoryImpl modelo;
	private ViewModelProveedores vista;
	
	public InteractorImplProveedores(ViewModelProveedores view) {
		super();
		this.vista = view;
		this.modelo = DatabaseRepositoryImpl.getInstance("https://apex.oracle.com", 30000L);
	}
	
	@Override
	public void consultarProveedores() {
		try {
			ProveedoresResponse respuesta = this.modelo.consultarProveedores();
			if(respuesta == null || respuesta.getCount() == 0 || respuesta.getItems() == null) {
				this.vista.mostrarMensajeError("No hay proveedores a mostrar");
			}else {
				this.vista.mostrarProveedoresEnGrid(respuesta.getItems());
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
		
	}
}
