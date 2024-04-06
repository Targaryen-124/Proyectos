package hn.lacolonia.views.productos;

import java.util.List;

import hn.lacolonia.data.Categoria;
import hn.lacolonia.data.Producto;

public interface ViewModelProductos {

	void mostrarProductosEnGrid(List<Producto> items);
	void mostrarCategoriasEnCombobox(List<Categoria> items);
	void mostrarMensajeError(String mensaje);
	void mostrarMensajeExito(String mensaje);
}
