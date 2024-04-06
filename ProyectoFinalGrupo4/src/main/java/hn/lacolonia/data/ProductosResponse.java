package hn.lacolonia.data;

import java.util.List;

public class ProductosResponse {

	private List<Producto> items;
	private int count;
	
	public List<Producto> getItems() {
		return items;
	}
	public void setItems(List<Producto> items) {
		this.items = items;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
