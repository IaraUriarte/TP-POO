public class CalculadoraEnvios {
    public double obtenerCosto(Correo correo, double peso) {
        return correo.calcularCosto(peso);  
    }
}