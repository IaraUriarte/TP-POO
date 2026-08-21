public class OCA extends Correo {
    @Override
    public double calcularCosto(double peso) {
        return peso * 15.0;
    }
}