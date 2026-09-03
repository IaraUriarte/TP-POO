public class ImpresorFacturaConsola {
    public void imprimir(Factura factura) {
        System.out.println("Comprobante tipo: " + factura.getTipoComprobante());

        if (factura.getTipoComprobante().equals("A")) {
            System.out.println("Neto: $" + factura.getTotalNeto());
        }

        // Ambas (A y B) ahora deben discriminar el IVA (Parte 2, ARCA)
        System.out.println("IVA 21%: $" + factura.getTotalIva21());
        System.out.println("IVA 10.5%: $" + factura.getTotalIva105());

        System.out.println("Total a Pagar: $" + factura.getTotalFinal());
    }
}