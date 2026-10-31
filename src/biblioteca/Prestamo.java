package biblioteca;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Une a un Usuario con un Libro durante un tiempo.
 * Es la clase que RELACIONA a las otras dos (asociación).
 */
public class Prestamo {

    public static final int DIAS_PRESTAMO = 15;
    public static final int MULTA_POR_DIA = 1000;   // pesos por día de retraso

    private final Libro libro;
    private final Usuario usuario;
    private final LocalDate fechaPrestamo;
    private final LocalDate fechaLimite;
    private LocalDate fechaDevolucion;              // null mientras no se devuelva

    public Prestamo(Libro libro, Usuario usuario, LocalDate fechaPrestamo) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaPrestamo.plusDays(DIAS_PRESTAMO);
    }

    /** Registra la devolución en la fecha indicada. */
    public void registrarDevolucion(LocalDate fecha) {
        this.fechaDevolucion = fecha;
    }

    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    /** Días de retraso al devolver (0 si se devolvió a tiempo). */
    public long diasDeRetraso(LocalDate fecha) {
        long dias = ChronoUnit.DAYS.between(fechaLimite, fecha);
        return Math.max(0, dias);
    }

    /** Multa a pagar si se devuelve en la fecha indicada. */
    public long calcularMulta(LocalDate fecha) {
        return diasDeRetraso(fecha) * MULTA_POR_DIA;
    }

    public Libro getLibro()             { return libro; }
    public Usuario getUsuario()         { return usuario; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaLimite()   { return fechaLimite; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }

    @Override
    public String toString() {
        return libro.getTitulo() + " -> " + usuario.getNombre() + " (hasta " + fechaLimite + ")";
    }
}
