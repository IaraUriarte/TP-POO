public class LineaFactura {
    private int cantidad;
    private double precioUnitario;
    private double porcentajeIva;

    public double getSubtotalNeto() {
        return cantidad * precioUnitario;
    }

    public double getSubtotalIva() {
        return getSubtotalNeto() * porcentajeIva;
    }
}
