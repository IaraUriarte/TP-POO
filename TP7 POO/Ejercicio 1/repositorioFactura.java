import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RepositorioFactura {

    private final String url;
    private final String usuario;
    private final String password;

    public RepositorioFactura(String url, String usuario, String password) {
        this.url = url;
        this.usuario = usuario;
        this.password = password;
    }

    public void guardar(Factura factura) {
        String query = "INSERT INTO facturas (cliente, total) VALUES (?, ?)";

        try (Connection conexion = DriverManager.getConnection(url, usuario, password);
             PreparedStatement stmt = conexion.prepareStatement(query)) {

            stmt.setString(1, factura.getNombreCliente());
            stmt.setDouble(2, factura.getTotalFinal());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error bd: " + e.getMessage());
        }
    }
}