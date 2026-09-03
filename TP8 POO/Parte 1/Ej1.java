public class LineaFactura {
    private Producto producto;   
    private int cantidad;
    private double precioUnitario;   
    private double porcentajeIva;    

    public LineaFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecioBase();   // se copia 1 vez
        this.porcentajeIva = producto.getPorcentajeIva();  // se copia 1 vez
    }
}
