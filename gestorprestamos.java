import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class gestorprestamos {
    private List<Prestamo> prestamosActivos = new ArrayList<>();

    public void prestar(usuario u, equipo e) {
        if (e.isDisponible()) {
            e.setDisponible(false);
            prestamosActivos.add(new Prestamo(u, e));
            System.out.println("Préstamo de " + e.getTipo() + " realizado.");
        } else {
            System.out.println("Error: El equipo no está disponible.");
        }
    }

    public void devolver(String idEquipo) {
        for (Prestamo p : prestamosActivos) {
            if (p.getEquipo().getId().equals(idEquipo) && !p.isDevuelto()) {
                p.registrarDevolucion();
                p.getEquipo().setDisponible(true);
                if (LocalDate.now().isAfter(p.getFechaDevolucionPrevista())) {
                    System.out.println("DEVOLUCIÓN FUERA DE PLAZO. Sanción aplicada.");
                } else {
                    System.out.println("Devolución correcta.");
                }
                return;
            }
        }
    }
}