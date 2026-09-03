public class Main {
    public static void main(String[] args) {

        // 1. Creamos el catálogo de productos
        Producto detergente = new Producto("Detergente", 1000, 0.21);
        Producto carne = new Producto("Carne", 2000, 0.105);
        Producto leche = new Producto("Leche", 500, 0.0); // Exento

        // 2. Creamos el impresor (fabricación Pura), lo usamos para ambas facturas
        ImpresorFacturaConsola impresor = new ImpresorFacturaConsola();

        // FACTURA A (empresa, sin descuento)
        System.out.println("FACTURA A");

        Factura facturaA = new Factura("A", 0.0); // sin descuento
        facturaA.agregarLinea(new LineaFactura(detergente, 2)); // 2 unidades
        facturaA.agregarLinea(new LineaFactura(carne, 1));
        facturaA.agregarLinea(new LineaFactura(leche, 3));

        facturaA.calcularTotales();
        impresor.imprimir(facturaA);

        System.out.println(); // línea en blanco para separar

        // FACTURA B (consumidor final, con descuento Jubilados)
        System.out.println("FACTURA B");

        Factura facturaB = new Factura("B", 0.15); // 15% descuento Jubilados
        facturaB.agregarLinea(new LineaFactura(detergente, 1));
        facturaB.agregarLinea(new LineaFactura(carne, 1));

        facturaB.calcularTotales();
        impresor.imprimir(facturaB);
    }
}