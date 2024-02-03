package hn.uth.EjerClase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTest {
	
	private Random rd;
	
	@Before
	public void inicializacion()
	{
		System.out.println("Ejecutando Prueba");
		rd = new Random();
	}
	
	@Test
	public void sumaConAleatorios()
	{
		//nextdouble genera un numero aleatorio decimal
		double numero1 = generarAleatorio();//Uso de metodo auxiliar debe estar dentro de AppTest
		double numero2 = rd.nextDouble();
		double resultadoEsperado = numero1 + numero2;
		
		assertEquals(App.sumar(numero1,numero2),resultadoEsperado, 0.01);
		System.out.println("Numero1 " + numero1);
		System.out.println("Numero2 " + numero2);
		System.out.println("Resultado " + resultadoEsperado);
	}
	
	@Test
	public void sumaEnterosPositivos()
	{
		assertTrue(App.sumar(25,30)==55);
	}
	
	@Test
	public void sumaEnterosNegativoss()
	{
		assertTrue(App.sumar(-5,-1)==-6);
	}
	
	@Test
	public void sumaDecimalesPositivos()
	{
		assertTrue(App.sumar(10.1,8.3)==18.4);
	}
	
	@Test
	public void sumaDecimalesNegativoss()
	{
		assertTrue(App.sumar(-0.5,-0.6)==-1.1);
	}
	
	@Test
	public void sumaCeroYPositivo()
	{
		assertTrue(App.sumar(0,5)==5);
	}
	
	@Test
	public void sumaDecimalesPeq()
	{
		assertEquals(App.sumar(3.99999,4.999999),8.990,.01); //8.990 Resultado esperado - .01 decimales a considerar
	}
	
	@Test
	public void restaEnterosPositivos()
	{
		assertTrue(App.resta(30,25)==5);
	}
	
	@Test
	public void restaEnterosNegativoss()
	{
		assertTrue(App.resta(-5,-1)==-4);
	}
	
	@Test
	public void restaDecimalesPositivos()
	{
		assertTrue(App.resta(10.5,5.1)==5.4);
	}
	
	@Test
	public void restaDecimalesNegativoss()
	{
		assertTrue(App.resta(-0.6,-0.1)==-0.5);
	}
	
	@Test
	public void restaCeroYPositivo()
	{
		assertTrue(App.resta(0,5)==-5);
	}
	
	@Test
	public void restaDecimalesPeq()
	{
		assertEquals(App.resta(3.55555,1.11111),2.444,.01);
	}
	
	@After
	public void finalizacion()
	{
		System.out.println("Prueba Finalizada");
	}
	
	//Metodo auxiliar
	private double generarAleatorio() {
		return rd.nextDouble();
	}

}
