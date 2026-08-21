public class Main {
    public static void main(String[] args) {

        // 1. Creamos los datos de la factura
        Factura factura = new Factura("Juan Pérez", 1000.0, "VIP");

        // 2. Creamos cada colaborador
        CalculadoraDescuento calculadora = new CalculadoraDescuento();
        RepositorioFactura repositorio = new RepositorioFactura(
                "jdbc:mysql://localhost:3306/db", "root", "1234");
        ImpresorFactura impresor = new ImpresorFactura();

        // 3. El propio main hace lo que antes hacía GestorFacturacion
        double total = calculadora.calcularTotal(factura);
        factura.setTotalFinal(total);

        repositorio.guardar(factura);
        impresor.imprimir(factura);
    }
}