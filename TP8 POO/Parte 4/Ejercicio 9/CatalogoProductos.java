import java.util.HashMap;
import java.util.Map;

/**
 * CREADOR de Producto: los contiene, los registra indexados por codigo
 * y posee los datos de alta. Fabricacion Pura: administra el catalogo,
 * no representa un concepto del negocio en si mismo.
 */
public class CatalogoProductos {
    private final Map<String, Producto> productos = new HashMap<>();

    public Producto alta(String codigo, String nombre, double precioBase, double porcentajeIva) {
        Producto p = new Producto(nombre, precioBase, porcentajeIva);
        productos.put(codigo, p);
        return p;
    }

    public Producto buscar(String codigo) {
        Producto p = productos.get(codigo);
        if (p == null) throw new IllegalArgumentException("Producto inexistente: " + codigo);
        return p;
    }
}
