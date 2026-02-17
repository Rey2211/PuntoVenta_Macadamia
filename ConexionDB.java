import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    private static final String URL = "jdbc:sqlite:macadamia.db";

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conn;
    }

    public static void crearTablas() {
        String sql = "CREATE TABLE IF NOT EXISTS productos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "precio REAL NOT NULL," +
                "stock INTEGER NOT NULL);";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Base de datos lista.");
        } catch (SQLException e) {
            System.out.println("Error al crear tablas: " + e.getMessage());
        }
    }
}