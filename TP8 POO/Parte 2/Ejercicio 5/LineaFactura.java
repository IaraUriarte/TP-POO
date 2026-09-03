public class LineaFactura {
    private int cantidad;
    private double precioUnitario;   // snapshot histórico (Ej1)
    private double porcentajeIva;    // snapshot histórico (Ej1)
    private double porcentajeDescuento; // se aplica línea por línea (Parte 2)

    public LineaFactura(Producto producto, int cantidad) {
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecioBase();
        this.porcentajeIva = producto.getPorcentajeIva();
        this.porcentajeDescuento = 0.0; // sin descuento por defecto
    }

    public void aplicarDescuento(double porcentaje) {
        this.porcentajeDescuento = porcentaje;
    }

    // Experto en Información: LineaFactura calcula sus propios subtotales
    public double getSubtotalSinDescuento() {
        return cantidad * precioUnitario;
    }

    public double getMontoDescuento() {
        return getSubtotalSinDescuento() * porcentajeDescuento;
    }

    // Descuento aplicado ANTES del IVA (clave para el problema de ARCA)
    public double getSubtotalNeto() {
        return getSubtotalSinDescuento() - getMontoDescuento();
    }

    public double getSubtotalIva() {
        return getSubtotalNeto() * porcentajeIva;
    }

    public double getSubtotalFinal() {
        return getSubtotalNeto() + getSubtotalIva();
    }

    public double getPorcentajeIva() { return porcentajeIva; }
}