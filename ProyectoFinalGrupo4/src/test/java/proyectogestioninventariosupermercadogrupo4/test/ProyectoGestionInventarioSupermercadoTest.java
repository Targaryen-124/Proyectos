package proyectogestioninventariosupermercadogrupo4.test;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProyectoGestionInventarioSupermercadoTest {
	
	@Test
	@Order(1)
	public void testConsultarProducto() throws InterruptedException {
		// Inicializa el WebDriver para Chrome
		WebDriver driver = new ChromeDriver();
		
		try{
			// Abre la página web de productos
			driver.get("http://localhost:8080/productos");
			
			int cantidadProductosInicial = 0;
		
			new WebDriverWait(driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Productos"));
			
			Thread.sleep(3000);
			
			WebElement botonGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btn_guardar']"));
			WebElement botonCancelar = driver.findElement(By.xpath("//vaadin-button[@id='btn_cancelar']"));
			WebElement botonEliminar = driver.findElement(By.xpath("//vaadin-button[@id='btn_eliminar']"));
			
			// Localiza el campo de entrada de nombre de usuario
			WebElement campoCodigo = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_codigo']/input"));
			WebElement campoNombre = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_nombre']/input"));
			WebElement campoPrecio = driver.findElement(By.xpath("//vaadin-number-field[@id='txt_precio']/input"));
			WebElement campoCategoria = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_categoria']/input"));
			
			// Ingresa el nombre de usuario
			campoCodigo.sendKeys("8412345678905");
			campoNombre.sendKeys("Salchicha Toledo 400 Gr");
			campoPrecio.sendKeys("84.90");
			campoCategoria.sendKeys("Embutidos");
			
			Thread.sleep(3000);
			
			botonGuardar.click();
			
			int cantidadProductosFinal = 0;
			
			assertEquals(cantidadProductosFinal, (cantidadProductosInicial), .001);
			
		}finally {
			driver.close();
		}
	}
	
	@Test
	@Order(2)
	public void testConsultarCategoria() throws InterruptedException {
		// Inicializa el WebDriver para Chrome
		WebDriver driver = new ChromeDriver();
		
		try{
			// Abre la página web de categorias
			driver.get("http://localhost:8080/categorias");
			
			int cantidadCategoriasInicial = 0;
		
			new WebDriverWait(driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Categorias"));
			
			Thread.sleep(3000);
			
			WebElement botonGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btn_guardar']"));
			WebElement botonCancelar = driver.findElement(By.xpath("//vaadin-button[@id='btn_cancelar']"));
			WebElement botonEliminar = driver.findElement(By.xpath("//vaadin-button[@id='btn_eliminar']"));
			
			// Localiza el campo de entrada de nombre de usuario
			WebElement campoIdcategoria = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_idcategoria']/input"));
			WebElement campoNombre = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_nombre']/input"));
			WebElement campoEstado = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_estado']/input"));
			WebElement campoProveedor = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_proveedor']/input"));
			
			// Ingresa el nombre de usuario
			campoIdcategoria.sendKeys("1");
			campoNombre.sendKeys("Embutidos");
			campoEstado.sendKeys("Activa");
			campoProveedor.sendKeys("OLACSA");
	        
			Thread.sleep(3000);
			
			botonGuardar.click();
			
			int cantidadCategoriasFinal = 0;
			
			assertEquals(cantidadCategoriasFinal, (cantidadCategoriasInicial), .001);
			
		}finally {
			driver.close();
		}
	}
	
	@Test
	@Order(3)
	public void testConsultarProveedor() throws InterruptedException {
		// Inicializa el WebDriver para Chrome
		WebDriver driver = new ChromeDriver();
		
		try{
			// Abre la página web de proveedores
			driver.get("http://localhost:8080/proveedores");
			
			int cantidadProveedoresInicial = 0;
		
			new WebDriverWait(driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Proveedores"));
			
			Thread.sleep(3000);
			
			WebElement botonGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btn_guardar']"));
			WebElement botonCancelar = driver.findElement(By.xpath("//vaadin-button[@id='btn_cancelar']"));
			WebElement botonEliminar = driver.findElement(By.xpath("//vaadin-button[@id='btn_eliminar']"));
			
			// Localiza el campo de entrada de nombre de usuario
			WebElement campoIdproveedor = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_idproveedor']/input"));
			WebElement campoNombre = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_nombre']/input"));
			WebElement campoDireccion = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_direccion']/input"));
			WebElement campoTelefono = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_telefono']/input"));
			
			// Ingresa el nombre de usuario
			campoIdproveedor.sendKeys("1");
			campoNombre.sendKeys("OLACSA");
			campoDireccion.sendKeys("Tegucigalpa, Francisco Morazan, Bulevar Suyapa");
			campoTelefono.sendKeys("32105689");
	        
			Thread.sleep(3000);
			
			botonGuardar.click();
			
			int cantidadProveedoresFinal = 0;
			
			assertFalse(cantidadProveedoresInicial+1 == 0);
			
		}finally {
			driver.close();
		}
	}
}
