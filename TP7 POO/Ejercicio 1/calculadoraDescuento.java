public class CalculadoraDescuento {

    public double calcularTotal(Factura factura) {
        double descuento = obtenerPorcentajeDescuento(factura.getTipoCliente()) * factura.getMontoBase();
        return factura.getMontoBase() - descuento;
    }

    private double obtenerPorcentajeDescuento(String tipoCliente) {
        switch (tipoCliente) {
            case "VIP":
                return 0.20;
            case "REGULAR":
                return 0.10;
            default:
                return 0.0;
        }
    }
}



