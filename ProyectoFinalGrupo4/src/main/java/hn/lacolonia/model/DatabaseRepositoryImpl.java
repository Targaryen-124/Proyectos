package hn.lacolonia.model;

import java.io.IOException;

import hn.lacolonia.data.Categoria;
import hn.lacolonia.data.CategoriasResponse;
import hn.lacolonia.data.Producto;
import hn.lacolonia.data.ProductosResponse;
import hn.lacolonia.data.ProveedoresResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DatabaseRepositoryImpl {
	
	private static DatabaseRepositoryImpl INSTANCE;
	private DatabaseClient client;
	
	private DatabaseRepositoryImpl(String url, Long timeout) {
		client = new DatabaseClient(url, timeout);
	}
	
	public static DatabaseRepositoryImpl getInstance(String url, Long timeout) {
		if(INSTANCE == null) {
			synchronized (DatabaseRepositoryImpl.class) {
				if(INSTANCE == null) {
					INSTANCE = new DatabaseRepositoryImpl(url, timeout);
				}
			}
		}
		return INSTANCE;
	}
	
	public ProductosResponse consultarProductos() throws IOException {
		Call<ProductosResponse> call = client.getDB().consultarProductos();
		Response<ProductosResponse> response = call.execute();
		if(response.isSuccessful()){
			return response.body();
		}else {
			return null;
		}
	}
	
	public boolean crearProductos(Producto nuevo) throws IOException {
		Call<ResponseBody> call = client.getDB().crearProductos(nuevo);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	public CategoriasResponse consultarCategorias() throws IOException {
		Call<CategoriasResponse> call = client.getDB().consultarCategorias();
		Response<CategoriasResponse> response = call.execute();
		if(response.isSuccessful()){
			return response.body();
		}else {
			return null;
		}
	}
	
	public boolean crearCategorias(Categoria nueva) throws IOException {
		Call<ResponseBody> call = client.getDB().crearCategorias(nueva);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	public boolean actualizarCategorias(Categoria cambiar) throws IOException {
		Call<ResponseBody> call = client.getDB().actualizarCategorias(cambiar);
		Response<ResponseBody> response = call.execute();
		return response.isSuccessful();
	}
	
	public ProveedoresResponse consultarProveedores() throws IOException {
		Call<ProveedoresResponse> call = client.getDB().consultarProveedores();
		Response<ProveedoresResponse> response = call.execute();
		if(response.isSuccessful()){
			return response.body();
		}else {
			return null;
		}
	}
}
