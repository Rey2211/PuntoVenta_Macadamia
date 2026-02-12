import java.util.ArrayList;
public class Inventario {
    private ArrayList<Producto> lista; //Lista que almacena los objetos de tipo Producto y solo se puede acceder a ella mediante la clase inventario

    public Inventario() {
        this.lista = new ArrayList<>();
        cargarInventarioPredeterminado();
    }

    public void cargarInventarioPredeterminado() {
        lista.add(new Producto("Cheescake de Frutos Amarillos", 12000, 12));
        lista.add(new Producto("Milhoja", 10000, 12 ));
        lista.add(new Producto("Merengon", 10000, 5));
        lista.add(new Producto("Tiramisu", 8000, 12));
        lista.add(new Producto("Croissant Dulce", 7000, 4));
        lista.add(new Producto("Postre de la Casa", 8000, 12));
    }

    public void registrarProducto(Producto p) {
        lista.add(p);
    }

    public void listarTodo() {
        if (lista.isEmpty()) {
            System.out.println("No hay inventario");
            return;
        }
        System.out.println("\n--- LISTA DE PRODUCTOS ---");
        for (Producto p : lista) {
            System.out.println("- " + p.getnombre() + " | Precio: $" + p.getprecio() + " | Stock: " + p.getstock());
        }
    }
    public Producto buscarPorNombre(String nombre) {
        for (Producto p : lista) {
            if (p.getnombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }
    public void reiniciarInventario(){
        this.lista.clear(); // se limpia el inventario de ayer
        //Carga los valores predeterminados del dia
        lista.add(new Producto("Cheescake de Frutos", 12000, 12));
        lista.add(new Producto("Milhoja", 10000, 12));
        lista.add(new Producto("Merengon", 10000, 5));
        lista.add(new Producto("Tiramisu", 8000, 12));
        lista.add(new Producto("Croissant Dulce", 7000, 4));
        lista.add(new Producto("Postre de la Casa", 8000, 12));
        System.out.println("--- Inventario Reiniciado ---");
    }

    public ArrayList<Producto>getListaProductos() {
        return this.lista;
    }
}
