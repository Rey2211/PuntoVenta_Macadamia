import java.sql.*;
import java.util.ArrayList;

public class Inventario {
    private ArrayList<Producto> lista;

    public Inventario() {
        this.lista = new ArrayList<>();
        // 1. Aseguramos que la tabla exista al arrancar
        ConexionDB.crearTablas();
        // 2. Cargamos los datos desde la DB a la lista de memoria
        cargarDesdeDB();

        // 3. Si la base de datos está vacía (primera vez), insertamos datos iniciales
        if (lista.isEmpty()) {
            insertarDatosBase();
            cargarDesdeDB(); // Volvemos a cargar para llenar la lista
        }
    }

    private void cargarDesdeDB() {
        lista.clear(); // Limpiamos la lista actual para no duplicar
        String sql = "SELECT * FROM productos";

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar inventario: " + e.getMessage());
        }
    }

    private void insertarDatosBase() {
        // Esto solo se ejecuta la primerísima vez que corres el programa
        registrarProductoEnDB(new Producto("Milhoja", 10000, 12));
        registrarProductoEnDB(new Producto("Tiramisu", 8000, 10));
        registrarProductoEnDB(new Producto("Merengon", 12000, 5));
        System.out.println("Datos iniciales creados en la DB.");
    }

    // Metodo para guardar un nuevo producto en la DB
    public void registrarProductoEnDB(Producto p) {
        String sql = "INSERT INTO productos(nombre, precio, stock) VALUES(?,?,?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getnombre());
            pstmt.setDouble(2, p.getprecio());
            pstmt.setInt(3, p.getstock());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar producto: " + e.getMessage());
        }
    }

    // --- ACTUALIZACIÓN CLAVE ---
    // Este metodo lo usaremos cada vez que el Admin cambie un precio
    public void actualizarPrecioEnDB(String nombre, double nuevoPrecio) {
        String sql = "UPDATE productos SET precio = ? WHERE nombre = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, nuevoPrecio);
            pstmt.setString(2, nombre);
            pstmt.executeUpdate();

            // También actualizamos la lista en memoria para que se vea el cambio de inmediato
            buscarPorNombre(nombre).setPrecio(nuevoPrecio);

        } catch (SQLException e) {
            System.out.println("Error al actualizar precio: " + e.getMessage());
        }
    }

    public ArrayList<Producto> getListaProductos() {
        return lista;
    }

    public Producto buscarPorNombre(String nombre) {
        for (Producto p : lista) {
            if (p.getnombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }
}