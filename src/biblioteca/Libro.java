package biblioteca;

/**
 * Representa un libro de la biblioteca.
 * Sus atributos son privados (encapsulamiento): solo se pueden leer o
 * cambiar a través de sus métodos, que validan los datos.
 */
public class Libro {

    // ---------- Atributos (características) ----------
    private final String isbn;     // identificador único; no cambia (final)
    private final String titulo;
    private final String autor;
    private final int anio;
    private boolean disponible;    // true si está en la estantería

    // ---------- Constructor: crea el objeto con datos válidos ----------
    public Libro(String isbn, String titulo, String autor, int anio) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("El ISBN es obligatorio");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.disponible = true;    // todo libro nuevo empieza disponible
    }

    // ---------- Métodos (comportamientos) ----------
    /** Marca el libro como prestado. Falla si ya estaba prestado. */
    public void marcarPrestado() {
        if (!disponible) {
            throw new IllegalStateException("El libro '" + titulo + "' ya está prestado");
        }
        disponible = false;
    }

    /** Marca el libro como devuelto. */
    public void marcarDevuelto() {
        disponible = true;
    }

    // ---------- Getters: permiten LEER los atributos privados ----------
    public String getIsbn()      { return isbn; }
    public String getTitulo()    { return titulo; }
    public String getAutor()     { return autor; }
    public int getAnio()         { return anio; }
    public boolean isDisponible() { return disponible; }

    @Override
    public String toString() {
        return titulo + " (" + autor + ", " + anio + ") - "
                + (disponible ? "disponible" : "prestado");
    }
}
