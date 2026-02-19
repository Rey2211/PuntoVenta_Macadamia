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
        lista.clear();
        String sql = "SELECT * FROM productos";
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getInt("maneja_stock") == 1,
                        rs.getString("categoria") // <--- Cargamos la categoría
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar el producto" + e.getMessage());
        }
    }

    private void insertarDatosBase() {
        registrarProductoEnDB(new Producto("Cheesecake", 12000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Café Americano", 5000, 0, false, "Bebidas"));
        registrarProductoEnDB(new Producto("Capuchino", 7000, 0, false, "Bebidas"));
        registrarProductoEnDB(new Producto("Muffin", 4500, 5, true, "Postres"));
    }


    // Metodo para guardar un nuevo producto en la DB
    public void registrarProductoEnDB(Producto p) {
        // 1. Agregamos maneja_stock a la consulta SQL
        String sql = "INSERT INTO productos(nombre, precio, stock, maneja_stock, categoria) VALUES(?,?,?,?,?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getnombre());
            pstmt.setDouble(2, p.getprecio());
            pstmt.setInt(3, p.getstock());
            // 2. Enviamos el booleano convertido a 1 o 0
            pstmt.setInt(4, p.isManejaStock() ? 1 : 0);
            pstmt.setString(5, p.getCategoria());

            pstmt.executeUpdate();
            pstmt.setString(1, p.getnombre());
            pstmt.setDouble(2, p.getprecio());
            pstmt.setInt(3, p.getstock());
            pstmt.setInt(4, p.isManejaStock() ? 1 : 0);
            pstmt.setString(5, p.getCategoria()); // <--- Guardamos la categoría

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

    public void actualizarStockEnDB(String nombre, int nuevoStock) {
        String sql = "UPDATE productos SET stock = ? WHERE nombre = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuevoStock);
            pstmt.setString(2, nombre);
            pstmt.executeUpdate();
            System.out.println("Stock sincronizado en DB para: " + nombre);
        } catch (SQLException e) {
            System.out.println("Error al actualizar stock: " + e.getMessage());
        }
    }
}