package biblioteca;

import java.time.LocalDate;

/** Demostración: simula un día de trabajo en la biblioteca. */
public class Main {
    public static void main(String[] args) {
        Biblioteca biblio = new Biblioteca("Biblioteca Comunitaria Montería");

        // 1. Crear objetos (instancias) a partir de las clases
        biblio.agregarLibro(new Libro("978-0001", "Cien años de soledad", "Gabriel García Márquez", 1967));
        biblio.agregarLibro(new Libro("978-0002", "La vorágine", "José Eustasio Rivera", 1924));
        biblio.agregarLibro(new Libro("978-0003", "María", "Jorge Isaacs", 1867));
        biblio.registrarUsuario(new Usuario("1001", "Ana Gómez"));
        biblio.registrarUsuario(new Usuario("1002", "Luis Pérez"));

        System.out.println("=== " + biblio.getNombre() + " ===");
        System.out.println("Libros disponibles al abrir: " + biblio.librosDisponibles().size());

        // 2. Prestar libros
        LocalDate hoy = LocalDate.of(2026, 10, 31);
        Prestamo p1 = biblio.prestar("978-0001", "1001", hoy);
        Prestamo p2 = biblio.prestar("978-0002", "1002", hoy);
        System.out.println("Préstamo: " + p1);
        System.out.println("Préstamo: " + p2);
        System.out.println("Libros disponibles: " + biblio.librosDisponibles().size());

        // 3. Intentar prestar un libro que ya está prestado
        try {
            biblio.prestar("978-0001", "1002", hoy);
        } catch (IllegalStateException e) {
            System.out.println("No se pudo prestar: " + e.getMessage());
        }

        // 4. Devoluciones: una a tiempo y otra con 5 días de retraso
        long multa1 = biblio.devolver("978-0001", hoy.plusDays(10));
        long multa2 = biblio.devolver("978-0002", hoy.plusDays(20));
        System.out.println("Devolución de Ana a los 10 días, multa: $" + multa1);
        System.out.println("Devolución de Luis a los 20 días, multa: $" + multa2);
        System.out.println("Libros disponibles al cerrar: " + biblio.librosDisponibles().size());
    }
}
