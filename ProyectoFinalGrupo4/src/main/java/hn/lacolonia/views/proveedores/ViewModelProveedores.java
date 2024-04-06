package hn.lacolonia.views.proveedores;

import java.util.List;

import hn.lacolonia.data.Proveedor;

public interface ViewModelProveedores {

	void mostrarProveedoresEnGrid(List<Proveedor> items);
	void mostrarMensajeError(String mensaje);
}
