import java.util.ArrayList;
import java.util.List;

public class Factura {

    private String tipoComprobante;
    private List<LineaFactura> lineas;
    private List<Descuento> descuentos;

    private double totalNeto;
    private double totalIva21;
    private double totalIva105;
    private double totalFinal;

    public Factura(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
        this.lineas = new ArrayList<>();
        this.descuentos = new ArrayList<>();
    }

    public void agregarDescuento(Descuento descuento) {
        descuentos.add(descuento);
    }

    public void agregarLinea(LineaFactura linea) {

        for (Descuento descuento : descuentos) {
            descuento.aplicar(linea);
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

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public double getTotalNeto() {
        return totalNeto;
    }

    public double getTotalIva21() {
        return totalIva21;
    }

    public double getTotalIva105() {
        return totalIva105;
    }

    public double getTotalFinal() {
        return totalFinal;
    }
}