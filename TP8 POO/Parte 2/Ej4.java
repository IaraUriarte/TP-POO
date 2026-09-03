public class ImpresorFacturaConsola {
    public void imprimir(Factura factura) {
        System.out.println("Total a Pagar: $" + factura.getTotalFinal());
        
        if (factura.getTipoComprobante().equals("A")) {
            System.out.println("IVA 21%: $" + factura.getTotalIva21());
            System.out.println("IVA 10.5%: $" + factura.getTotalIva10_5());
        }
    }
}

public class Factura {
//atributos y cálculo de totales
    
    public double getTotalFinal() { return totalFinal; }
    public double getTotalIva21() { return totalIva21; }
    public double getTotalIva10_5() { return totalIva10_5; }
}
