package biblioteca;

import java.time.LocalDate;

/**
 * Pruebas de funcionalidad sin librerías externas.
 * Cada prueba crea objetos, ejecuta métodos y comprueba el resultado esperado.
 * Ejecutar: java biblioteca.PruebasBiblioteca
 */
public class PruebasBiblioteca {

    private static int aprobadas = 0, fallidas = 0;

    private static void verificar(String nombre, boolean condicion) {
        if (condicion) { aprobadas++; System.out.println("[OK]    " + nombre); }
        else           { fallidas++;  System.out.println("[FALLA] " + nombre); }
    }

    /** Verifica que una acción lance una excepción (es decir, que la regla se cumpla). */
    private static void verificarError(String nombre, Runnable accion) {
        try { accion.run(); verificar(nombre, false); }
        catch (RuntimeException e) { verificar(nombre + " -> \"" + e.getMessage() + "\"", true); }
    }

    private static Biblioteca nuevaBiblioteca() {
        Biblioteca b = new Biblioteca("Prueba");
        b.agregarLibro(new Libro("1", "Libro A", "Autor A", 2000));
        b.agregarLibro(new Libro("2", "Libro B", "Autor B", 2001));
        b.agregarLibro(new Libro("3", "Libro C", "Autor C", 2002));
        b.agregarLibro(new Libro("4", "Libro D", "Autor D", 2003));
        b.registrarUsuario(new Usuario("100", "Usuario Uno"));
        b.registrarUsuario(new Usuario("200", "Usuario Dos"));
        return b;
    }

    public static void main(String[] args) {
        LocalDate hoy = LocalDate.of(2026, 10, 31);
        System.out.println("=== Pruebas del sistema de biblioteca ===\n");

        // --- Clase Libro ---
        Libro libro = new Libro("9", "Prueba", "Autor", 2020);
        verificar("Un libro nuevo empieza disponible", libro.isDisponible());
        libro.marcarPrestado();
        verificar("Al prestarlo deja de estar disponible", !libro.isDisponible());
        libro.marcarDevuelto();
        verificar("Al devolverlo vuelve a estar disponible", libro.isDisponible());
        verificarError("No se puede crear un libro sin título", () -> new Libro("10", "", "X", 2020));

        // --- Clase Biblioteca: registro ---
        Biblioteca b = nuevaBiblioteca();
        verificar("La biblioteca registra 4 libros", b.cantidadLibros() == 4);
        verificar("La biblioteca registra 2 usuarios", b.cantidadUsuarios() == 2);
        verificar("Buscar un ISBN existente devuelve el libro", b.buscarLibro("2") != null);
        verificar("Buscar un ISBN inexistente devuelve null", b.buscarLibro("99") == null);
        verificarError("No se aceptan dos libros con el mismo ISBN",
                () -> b.agregarLibro(new Libro("1", "Repetido", "X", 2020)));

        // --- Interacción entre clases: préstamo ---
        Prestamo p = b.prestar("1", "100", hoy);
        verificar("El préstamo une al libro y al usuario correctos",
                p.getLibro().getIsbn().equals("1") && p.getUsuario().getDocumento().equals("100"));
        verificar("La fecha límite es 15 días después", p.getFechaLimite().equals(hoy.plusDays(15)));
        verificar("El usuario queda con 1 préstamo activo", b.buscarUsuario("100").getPrestamosActivos().size() == 1);
        verificar("Quedan 3 libros disponibles", b.librosDisponibles().size() == 3);
        verificarError("No se puede prestar un libro ya prestado", () -> b.prestar("1", "200", hoy));
        verificarError("No se puede prestar a un usuario inexistente", () -> b.prestar("2", "999", hoy));

        // --- Regla: máximo 3 préstamos por usuario ---
        b.prestar("2", "100", hoy);
        b.prestar("3", "100", hoy);
        verificarError("Un usuario no puede tener más de 3 préstamos", () -> b.prestar("4", "100", hoy));

        // --- Devoluciones y multas ---
        long multaATiempo = b.devolver("1", hoy.plusDays(10));
        verificar("Devolver a tiempo no genera multa", multaATiempo == 0);
        long multaTarde = b.devolver("2", hoy.plusDays(20));
        verificar("Devolver 5 días tarde genera $5.000 de multa", multaTarde == 5000);
        verificar("Tras devolver, el usuario tiene 1 préstamo activo",
                b.buscarUsuario("100").getPrestamosActivos().size() == 1);
        verificarError("No se puede devolver un libro que no está prestado", () -> b.devolver("1", hoy));

        System.out.println("\nResultado: " + aprobadas + " pruebas aprobadas, " + fallidas + " fallidas.");
        if (fallidas > 0) System.exit(1);
    }
}
