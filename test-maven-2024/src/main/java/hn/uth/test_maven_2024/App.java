package hn.uth.test_maven_2024;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Bienvenido a Java y Maven" );
        Scanner teclado = new Scanner(System.in);
        System.out.print("Ingrese su Nombre> ");
        String nombre = teclado.nextLine();
        System.out.println("Hola "+nombre);
        System.out.println("Fin del Programa");
        teclado.close();
     }
}
