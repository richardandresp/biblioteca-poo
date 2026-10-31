# Sistema de gestión de biblioteca (POO en Java)

Actividad práctica #1 — Programación Orientada a Objetos, Universidad Cooperativa de Colombia.
Autor: Richard Andrés Peña Ríos.

## ¿Qué hace?
Modela una biblioteca pequeña: registra libros y usuarios, presta libros con fecha límite de 15 días,
controla que cada usuario tenga como máximo 3 préstamos activos y calcula una multa de $1.000 por día de retraso.

## Clases
| Clase | Responsabilidad |
|---|---|
| `Libro` | Datos del libro y su estado (disponible o prestado). |
| `Usuario` | Persona inscrita y sus préstamos activos (máximo 3). |
| `Prestamo` | Relaciona un libro con un usuario; calcula fecha límite y multa. |
| `Biblioteca` | Guarda libros, usuarios e historial; aplica las reglas de préstamo y devolución. |
| `Main` | Demostración de uso. |
| `PruebasBiblioteca` | 20 pruebas de funcionalidad. |

El diagrama UML está en `docs/diagrama_uml.png` (editable en `docs/diagrama_uml.drawio` con diagrams.net).

## Cómo ejecutarlo
Requiere Java 17 o superior.
```bash
javac -d out src/biblioteca/*.java
java -cp out biblioteca.Main               # demostración
java -cp out biblioteca.PruebasBiblioteca  # pruebas
```
En un IDE (IntelliJ, Eclipse, NetBeans o VS Code): abrir la carpeta, marcar `src` como carpeta de código fuente y ejecutar `Main` o `PruebasBiblioteca`.

## Resultado de las pruebas
`Resultado: 20 pruebas aprobadas, 0 fallidas.`
