public class DescuentoPorVolumen implements Descuento {

    private int cantidadMinima;
    private double porcentaje;

    public DescuentoPorVolumen(int cantidadMinima, double porcentaje) {
        this.cantidadMinima = cantidadMinima;
        this.porcentaje = porcentaje;
    }

    @Override
    public void aplicar(LineaFactura linea) {
        if (linea.getCantidad() >= cantidadMinima) {
            linea.aplicarDescuento(porcentaje);
        }
    }
}