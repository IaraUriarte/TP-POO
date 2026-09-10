public class LineaFactura {

    private int cantidad;
    private double precioUnitario;
    private double porcentajeIva;
    private double porcentajeDescuento;

    public LineaFactura(Producto producto, int cantidad) {
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecioBase();
        this.porcentajeIva = producto.getPorcentajeIva();
        this.porcentajeDescuento = 0.0;
    }

    public void aplicarDescuento(double porcentaje) {
        this.porcentajeDescuento = porcentaje;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotalSinDescuento() {
        return cantidad * precioUnitario;
    }

    public double getMontoDescuento() {
        return getSubtotalSinDescuento() * porcentajeDescuento;
    }

    public double getSubtotalNeto() {
        return getSubtotalSinDescuento() - getMontoDescuento();
    }

    public double getSubtotalIva() {
        return getSubtotalNeto() * porcentajeIva;
    }

    public double getSubtotalFinal() {
        return getSubtotalNeto() + getSubtotalIva();
    }

    public double getPorcentajeIva() {
        return porcentajeIva;
    }
}