/**
 * CREADOR de Factura: inicia y registra la venta, posee los datos de
 * inicializacion (tipo de comprobante) y usa a la Factura de punta a punta.
 * CONOCE el catalogo (inyectado), pero no lo crea ni lo administra.
 */
public class GestorDeVentas {
    private final CatalogoProductos catalogo;
    private Factura facturaEnCurso;

    public GestorDeVentas(CatalogoProductos catalogo) {
        this.catalogo = catalogo;
    }

    public void iniciarVenta(String tipoComprobante) {
        this.facturaEnCurso = new Factura(tipoComprobante);
    }

    /**
     * Los descuentos generales de Factura se aplican a las lineas
     * que se agreguen DESPUES de cargarlos (ver Factura.agregarLinea).
     * Por eso conviene aplicar las promociones generales antes de escanear.
     */
    public void aplicarDescuentoGeneral(Descuento descuento) {
        facturaEnCurso.agregarDescuento(descuento);
    }

    public void escanear(String codigoProducto, int cantidad) {
        Producto p = catalogo.buscar(codigoProducto);
        LineaFactura linea = new LineaFactura(p, cantidad);
        facturaEnCurso.agregarLinea(linea);
    }

    public Factura cerrarVenta() {
        facturaEnCurso.calcularTotales();
        Factura cerrada = facturaEnCurso;
        this.facturaEnCurso = null;
        return cerrada;
    }
}
