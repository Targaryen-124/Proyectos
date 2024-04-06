package hn.lacolonia.views.categorias;

import java.util.List;

import hn.lacolonia.data.Categoria;

public interface ViewModelCategorias {

	void mostrarCategoriasEnGrid(List<Categoria> items);
	void mostrarMensajeError(String mensaje);
	void mostrarMensajeExito(String mensaje);
}
