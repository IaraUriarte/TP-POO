public class LineaFactura {

    private int cantidad;
    private double precioUnitario;
    private double porcentajeIva;
    private double subtotalConDescuentos;

    public LineaFactura(Producto producto, int cantidad) {
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecioBase();
        this.porcentajeIva = producto.getPorcentajeIva();

        this.subtotalConDescuentos = cantidad * precioUnitario;
    }

    public void aplicarDescuento(double porcentaje) {
        subtotalConDescuentos =
                subtotalConDescuentos * (1 - porcentaje);
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotalSinDescuento() {
        return cantidad * precioUnitario;
    }

    public double getMontoDescuento() {
        return getSubtotalSinDescuento() - subtotalConDescuentos;
    }

    public double getSubtotalNeto() {
        return subtotalConDescuentos;
    }

    public double getSubtotalIva() {
        return subtotalConDescuentos * porcentajeIva;
    }

    public double getSubtotalFinal() {
        return getSubtotalNeto() + getSubtotalIva();
    }

    public double getPorcentajeIva() {
        return porcentajeIva;
    }
}