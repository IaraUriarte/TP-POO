public class Main {

    public static void main(String[] args) {

        // CatalogoProductos: CREADOR de Producto (alta unica vez, se reutiliza en todas las ventas)
        CatalogoProductos catalogo = new CatalogoProductos();
        catalogo.alta("DET01", "Detergente", 1000, 0.21);
        catalogo.alta("CAR01", "Carne", 2000, 0.105);
        catalogo.alta("LEC01", "Leche", 500, 0.0);

        ImpresorFacturaConsola impresor = new ImpresorFacturaConsola();

        // GestorDeVentas: CREADOR de Factura, conoce el catalogo pero no lo administra
        GestorDeVentas gestor = new GestorDeVentas(catalogo);

        // FACTURA A
        System.out.println("FACTURA A");

        gestor.iniciarVenta("A");
        gestor.escanear("DET01", 2);
        gestor.escanear("CAR01", 1);
        gestor.escanear("LEC01", 3);

        Factura facturaA = gestor.cerrarVenta();
        impresor.imprimir(facturaA);

        System.out.println();

        // FACTURA B con descuento por categoria (Jubilados)
        System.out.println("FACTURA B");

        gestor.iniciarVenta("B");
        gestor.aplicarDescuentoGeneral(new DescuentoPorCategoria(0.15));
        gestor.escanear("DET01", 1);
        gestor.escanear("CAR01", 1);

        Factura facturaB = gestor.cerrarVenta();
        impresor.imprimir(facturaB);

        System.out.println();

        // FACTURA B con descuento por volumen
        System.out.println("FACTURA B - DESCUENTO POR VOLUMEN");

        gestor.iniciarVenta("B");
        gestor.aplicarDescuentoGeneral(new DescuentoPorVolumen(3, 0.10));
        gestor.escanear("DET01", 3);
        gestor.escanear("CAR01", 1);

        Factura facturaC = gestor.cerrarVenta();
        impresor.imprimir(facturaC);
    }
}
