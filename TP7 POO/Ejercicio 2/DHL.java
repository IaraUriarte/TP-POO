public class DHL extends Correo {
    @Override
    public double calcularCosto(double peso) {
        return (peso * 45.0) + 80.0;
    }
}