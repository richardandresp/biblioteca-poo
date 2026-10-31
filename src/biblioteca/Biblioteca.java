package biblioteca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del modelo: guarda los libros, los usuarios y los préstamos,
 * y aplica las reglas para prestar y devolver.
 */
public class Biblioteca {

    private final String nombre;
    private final List<Libro> libros = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Prestamo> historial = new ArrayList<>();

    public Biblioteca(String nombre) {
        this.nombre = nombre;
    }

    // ---------- Registro ----------
    public void agregarLibro(Libro libro) {
        if (buscarLibro(libro.getIsbn()) != null) {
            throw new IllegalArgumentException("Ya existe un libro con ISBN " + libro.getIsbn());
        }
        libros.add(libro);
    }

    public void registrarUsuario(Usuario usuario) {
        if (buscarUsuario(usuario.getDocumento()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con documento " + usuario.getDocumento());
        }
        usuarios.add(usuario);
    }

    // ---------- Búsquedas ----------
    public Libro buscarLibro(String isbn) {
        for (Libro l : libros) {
            if (l.getIsbn().equals(isbn)) return l;
        }
        return null;   // no se encontró
    }

    public Usuario buscarUsuario(String documento) {
        for (Usuario u : usuarios) {
            if (u.getDocumento().equals(documento)) return u;
        }
        return null;
    }

    public List<Libro> librosDisponibles() {
        List<Libro> resultado = new ArrayList<>();
        for (Libro l : libros) {
            if (l.isDisponible()) resultado.add(l);
        }
        return resultado;
    }

    // ---------- Operaciones principales ----------
    /** Presta un libro a un usuario aplicando las reglas del negocio. */
    public Prestamo prestar(String isbn, String documento, LocalDate fecha) {
        Libro libro = buscarLibro(isbn);
        Usuario usuario = buscarUsuario(documento);
        if (libro == null)   throw new IllegalArgumentException("No existe el libro " + isbn);
        if (usuario == null) throw new IllegalArgumentException("No existe el usuario " + documento);
        if (!usuario.puedePrestar()) {
            throw new IllegalStateException(usuario.getNombre() + " ya tiene "
                    + Usuario.MAX_PRESTAMOS + " préstamos activos");
        }
        libro.marcarPrestado();                         // falla si ya está prestado
        Prestamo prestamo = new Prestamo(libro, usuario, fecha);
        usuario.agregarPrestamo(prestamo);
        historial.add(prestamo);
        return prestamo;
    }

    /** Devuelve un libro y retorna la multa a pagar (0 si llegó a tiempo). */
    public long devolver(String isbn, LocalDate fecha) {
        for (Prestamo p : historial) {
            if (p.estaActivo() && p.getLibro().getIsbn().equals(isbn)) {
                p.registrarDevolucion(fecha);
                p.getLibro().marcarDevuelto();
                p.getUsuario().quitarPrestamo(p);
                return p.calcularMulta(fecha);
            }
        }
        throw new IllegalStateException("El libro " + isbn + " no tiene un préstamo activo");
    }

    public String getNombre()            { return nombre; }
    public int cantidadLibros()          { return libros.size(); }
    public int cantidadUsuarios()        { return usuarios.size(); }
    public List<Prestamo> getHistorial() { return new ArrayList<>(historial); }
}
