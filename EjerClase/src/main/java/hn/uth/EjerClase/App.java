package hn.uth.EjerClase;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    /**
     * Este metodo suma 2 numeros
     * @param primerNumero
     * @param segundoNumero
     * @return el resultado de la suma
     */
	public static double sumar(double primerNumero, double segundoNumero) {
		// TODO Auto-generated method stub
		return primerNumero+segundoNumero;
	}

	/**
	 * Este metodo resta 2 numeros
	 * @param primerNumero
	 * @param segundoNumero
	 * @return el resultado de la resta
	 */
	public static double resta(double primerNumero, double segundoNumero) {
		// TODO Auto-generated method stub
		return primerNumero-segundoNumero;
	}
	
	public static void menu() {
		System.out.println("1");
		System.out.println("2");
		System.out.println("3");
	}

}
