public class DescuentoPorCategoria implements Descuento {

    private double porcentaje;

    public DescuentoPorCategoria(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public void aplicar(LineaFactura linea) {
        linea.aplicarDescuento(porcentaje);
    }
}