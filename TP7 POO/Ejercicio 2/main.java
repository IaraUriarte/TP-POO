public class Main {
    public static void main(String[] args) {
        CalculadoraEnvios calculadora = new CalculadoraEnvios();

        System.out.println("OCA: " + calculadora.obtenerCosto(new OCA(), 10));
        System.out.println("FedEx: " + calculadora.obtenerCosto(new FedEx(), 10));
        System.out.println("Andreani: " + calculadora.obtenerCosto(new Andreani(), 10));
        System.out.println("DHL: " + calculadora.obtenerCosto(new DHL(), 10));
    }
}