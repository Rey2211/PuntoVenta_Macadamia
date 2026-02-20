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
        registrarProductoEnDB(new Producto("Waffles de fruta y helado", 15000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Merengon", 10000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Oblea", 10000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Cheesecake", 7000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Brazo de reina", 6000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Leche asada", 6000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Milhoja", 6000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Pasion Tropical", 6000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Macadamia", 6000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Tentacion Silvestre", 6000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Nube de chocolate", 6000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Postre de limon", 6000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Piononos", 6000, 10, true, "Postres"));
        registrarProductoEnDB(new Producto("Tres Leches", 6000, 10, true, "Postres"));

        registrarProductoEnDB(new Producto("Café Americano", 3000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Expresso", 3000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Americano de Macadamia", 4000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Cafe en prensa Francesa", 6000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Cafe Irlandes", 8500, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Tinto Campesino", 3000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Capuccino con amaretto", 6000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Capuccino con amaretto y licor", 6000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Mocaccino", 5000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Latte Pequeño", 3000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Latte Grande", 4000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Latte de arequipe", 5000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Aromatica", 3000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Agua de panela con queso", 6000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Chocolate", 4000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Chocolate con Masmellos", 5000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Milo", 4000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Mazamorra dulce", 7000, 0, false, "Bebidas calientes"));
        registrarProductoEnDB(new Producto("Té negro", 3000, 0, false, "Bebidas calientes"));

        registrarProductoEnDB(new Producto("Avena Cubana", 5000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Mocaccino frio", 10000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Milo frio", 6000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Soda", 7000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Soda con zumo de limon", 5000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Malteada", 11000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Frappé", 8000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Granizado", 8000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Jugo natural en agua", 6000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Jugo natural en leche", 7000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Limonada natural", 5000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Limonada Hierbabuena", 6000, 0, false, "Bebidas frias"));
        registrarProductoEnDB(new Producto("Botella de agua", 3000, 0, false, "Bebidas frias"));

        registrarProductoEnDB(new Producto("Macadamia", 2500, 0, false, "Galletas"));
        registrarProductoEnDB(new Producto("Maizena", 2500, 0, false, "Galletas"));
        registrarProductoEnDB(new Producto("Red velvet", 2500, 0, false, "Galletas"));
        registrarProductoEnDB(new Producto("Triple Chocolate", 2500, 0, false, "Galletas"));

        registrarProductoEnDB(new Producto("Torta de Amapola", 3000, 10, true, "Tortas"));
        registrarProductoEnDB(new Producto("Torta de Chocolate", 3000, 10, true, "Tortas"));
        registrarProductoEnDB(new Producto("Torta de Mazorca", 3000, 10, true, "Tortas"));
        registrarProductoEnDB(new Producto("Torta de Red velvet", 3000, 10, true, "Tortas"));
        registrarProductoEnDB(new Producto("Torta de Zanahoria", 3000, 10, true, "Tortas"));

        registrarProductoEnDB(new Producto("Omelet Criollo", 6000, 0, false, "Desayunos"));
        registrarProductoEnDB(new Producto("Omelet Ranchero", 7000, 0, false, "Desayunos"));
        registrarProductoEnDB(new Producto("Omelet Mixto", 8000, 0, false, "Desayunos"));

        registrarProductoEnDB(new Producto("Margarita", 15000, 0, false, "Cocteles"));
        registrarProductoEnDB(new Producto("Mojito", 15000, 0, false, "Cocteles"));
        registrarProductoEnDB(new Producto("Tinto de verano", 15000, 0, false, "Cocteles"));
        registrarProductoEnDB(new Producto("Cuba Libre", 15000, 0, false, "Cocteles"));

        registrarProductoEnDB(new Producto("Croissant Salado", 8000, 0, false, "Otros"));
        registrarProductoEnDB(new Producto("Croissant Dulce", 8000, 0, false, "Otros"));
        registrarProductoEnDB(new Producto("Pastel de Pollo", 4000, 0, false, "Otros"));
        registrarProductoEnDB(new Producto("Migao", 12000, 0, false, "Otros"));
        registrarProductoEnDB(new Producto("Parfait", 10000, 0, false, "Otros"));

        registrarProductoEnDB(new Producto("Torta 3 Leches", 0, 0, false, "Pasteles"));
        registrarProductoEnDB(new Producto("Torta Milky Way", 0, 0, false, "Pasteles"));
        registrarProductoEnDB(new Producto("Ponque de Vino", 0, 0, false, "Pasteles"));
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