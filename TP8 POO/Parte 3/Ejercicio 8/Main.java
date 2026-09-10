public class Main {

    public static void main(String[] args) {

        Producto detergente = new Producto("Detergente", 1000, 0.21);
        Producto carne = new Producto("Carne", 2000, 0.105);
        Producto leche = new Producto("Leche", 500, 0.0);

        ImpresorFacturaConsola impresor = new ImpresorFacturaConsola();

        // FACTURA A
        System.out.println("FACTURA A");

        Factura facturaA = new Factura("A");

        facturaA.agregarLinea(new LineaFactura(detergente, 2));
        facturaA.agregarLinea(new LineaFactura(carne, 1));
        facturaA.agregarLinea(new LineaFactura(leche, 3));

        facturaA.calcularTotales();
        impresor.imprimir(facturaA);

        System.out.println();

        // FACTURA B
        System.out.println("FACTURA B");

        Factura facturaB = new Factura("B");

        // Descuento para Jubilados
        facturaB.agregarDescuento(
                new DescuentoPorCategoria(0.15)
        );

        facturaB.agregarLinea(new LineaFactura(detergente, 1));
        facturaB.agregarLinea(new LineaFactura(carne, 1));

        facturaB.calcularTotales();
        impresor.imprimir(facturaB);

        System.out.println();

        // FACTURA B con descuento por volumen
        System.out.println("FACTURA B - DESCUENTO POR VOLUMEN");

        Factura facturaC = new Factura("B");

        facturaC.agregarDescuento(
                new DescuentoPorVolumen(3, 0.10)
        );

        facturaC.agregarLinea(new LineaFactura(detergente, 3));
        facturaC.agregarLinea(new LineaFactura(carne, 1));

        facturaC.calcularTotales();
        impresor.imprimir(facturaC);
    }
}