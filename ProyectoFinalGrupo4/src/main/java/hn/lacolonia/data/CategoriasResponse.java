package hn.lacolonia.data;

import java.util.List;

public class CategoriasResponse {

	private List<Categoria> items;
	private int count;
	
	public List<Categoria> getItems() {
		return items;
	}
	public void setItems(List<Categoria> items) {
		this.items = items;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
