public class Andreani extends Correo {
    @Override
    public double calcularCosto(double peso) {
        return peso * 20.0;
    }
}