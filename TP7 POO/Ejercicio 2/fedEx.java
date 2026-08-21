public class FedEx extends Correo {
    @Override
    public double calcularCosto(double peso) {
        return (peso * 50.0) + 100.0; // Costo + aduana
    }
}