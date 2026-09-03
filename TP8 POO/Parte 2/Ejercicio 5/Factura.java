import java.util.ArrayList;
import java.util.List;

public class Factura {
    private String tipoComprobante; // "A" o "B"
    private double porcentajeDescuento; // ej: 0.15, solo aplica a Factura B
    private List<LineaFactura> lineas;

    private double totalNeto;
    private double totalIva21;
    private double totalIva105;
    private double totalFinal;

    public Factura(String tipoComprobante, double porcentajeDescuento) {
        this.tipoComprobante = tipoComprobante;
        this.porcentajeDescuento = porcentajeDescuento;
        this.lineas = new ArrayList<>();
    }

    public void agregarLinea(LineaFactura linea) {
        // La regla de negocio dice: el descuento de "Jubilados" solo aplica a Consumidor Final (Factura B)
        if (this.tipoComprobante.equals("B")) {
            linea.aplicarDescuento(this.porcentajeDescuento);
        }
        this.lineas.add(linea);
    }

    public void calcularTotales() {
        totalNeto = 0;
        totalIva21 = 0;
        totalIva105 = 0;
        totalFinal = 0;

        for (LineaFactura linea : lineas) {
            totalNeto += linea.getSubtotalNeto();
            totalFinal += linea.getSubtotalFinal();

            if (linea.getPorcentajeIva() == 0.21) {
                totalIva21 += linea.getSubtotalIva();
            } else if (linea.getPorcentajeIva() == 0.105) {
                totalIva105 += linea.getSubtotalIva();
            }
        }
    }

    // Getters para que el Impresor consulte los datos ya calculados
    public String getTipoComprobante() { return tipoComprobante; }
    public double getTotalNeto() { return totalNeto; }
    public double getTotalIva21() { return totalIva21; }
    public double getTotalIva105() { return totalIva105; }
    public double getTotalFinal() { return totalFinal; }
}