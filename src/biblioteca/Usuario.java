package biblioteca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa a una persona inscrita en la biblioteca.
 * Un usuario puede tener varios préstamos activos, hasta un máximo.
 */
public class Usuario {

    public static final int MAX_PRESTAMOS = 3;   // regla del negocio

    private final String documento;
    private final String nombre;
    private final List<Prestamo> prestamosActivos = new ArrayList<>();

    public Usuario(String documento, String nombre) {
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("El documento es obligatorio");
        }
        this.documento = documento;
        this.nombre = nombre;
    }

    /** ¿Puede pedir otro libro? Solo si no ha llegado al máximo. */
    public boolean puedePrestar() {
        return prestamosActivos.size() < MAX_PRESTAMOS;
    }

    // Estos dos métodos no son públicos (sin modificador = visibles solo
    // dentro del paquete): solo la Biblioteca debe poder cambiar los préstamos.
    void agregarPrestamo(Prestamo prestamo)  { prestamosActivos.add(prestamo); }
    void quitarPrestamo(Prestamo prestamo)   { prestamosActivos.remove(prestamo); }

    public String getDocumento() { return documento; }
    public String getNombre()    { return nombre; }

    /** Devuelve la lista de préstamos SOLO para leer (no se puede modificar desde fuera). */
    public List<Prestamo> getPrestamosActivos() {
        return Collections.unmodifiableList(prestamosActivos);
    }

    @Override
    public String toString() {
        return nombre + " (doc. " + documento + ") - " + prestamosActivos.size() + " préstamo(s)";
    }
}
