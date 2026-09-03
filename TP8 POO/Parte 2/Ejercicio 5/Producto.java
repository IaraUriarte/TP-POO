public class Producto {
    private String nombre;
    private double precioBase;
    private double porcentajeIva; // 0.21, 0.105 o 0.0

    public Producto(String nombre, double precioBase, double porcentajeIva) {
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.porcentajeIva = porcentajeIva;
    }

    public double getPrecioBase() { return precioBase; }
    public double getPorcentajeIva() { return porcentajeIva; }
    public String getNombre() { return nombre; }
}