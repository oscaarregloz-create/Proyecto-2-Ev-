public class alumno extends usuario {
    private String curso;

    public alumno(String id, String nombre, String email, String curso) {
        super(id, nombre, email);
        this.curso = curso;
    }

    @Override
    public String getTipo() { return "Alumno"; }
}
import java.util.Scanner;

public class App {
    private GestorUsuarios gUsuarios = new GestorUsuarios();
    private gestorReservas gReservas = new gestorReservas();
    private gestorprestamos gPrestamos = new gestorprestamos();
    private Aula aulaPrincipal = new Aula("Biblioteca Central", 20);
    private equipo laptop1 = new Portatil("P01", "Dell", "Latitude");

    public void ejecutar() {
        Scanner sc = new Scanner(System.in);
      
        gUsuarios.alta(new alumno("100", "Ana García", "ana@edu.es", "1º DAW"));

        int opcion;
        do {
            System.out.println("\n--- SISTEMA GESTIÓN AULAS ---");
            System.out.println("1. Listar Usuarios\n2. Ver Estado Aula\n3. Reservar Puesto\n4. Préstamo Portátil\n5. Salir");
            System.out.print("Seleccione: ");
            opcion = sc.nextInt();

            switch(opcion) {
                case 1 -> gUsuarios.listar();
                case 2 -> gReservas.mostrarEstadoAula(aulaPrincipal);
                case 3 -> gReservas.reservar(gUsuarios.buscarPorId("100"), aulaPrincipal, 5);
                case 4 -> gPrestamos.prestar(gUsuarios.buscarPorId("100"), laptop1);
            }
        } while (opcion != 5);
    }
}
import java.util.ArrayList;
import java.util.List;

public class Aula {
    private String nombre;
    private int capacidad; 
    private List<Puesto> puestos;

    public Aula(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.puestos = new ArrayList<>();

        for (int i = 1; i <= capacidad; i++) {
            puestos.add(new Puesto(i));
        }
    }

    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public List<Puesto> getPuestos() { return puestos; }
}
public abstract class equipo {
    protected String id;
    protected String marca;
    protected String modelo;
    protected boolean disponible;

    public equipo(String id, String marca, String modelo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.disponible = true;
    }

    public String getId() { return id; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public abstract String getTipo();
}
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
import java.util.ArrayList;
import java.util.List;

public class gestorReservas {
    private List<reserva> historialReservas = new ArrayList<>();

    public void reservar(usuario u, Aula a, int numPuesto) {
        if (numPuesto > a.getCapacidad() || numPuesto < 1) {
            System.out.println("Error: El puesto no existe en esta aula.");
            return;
        }
        Puesto p = a.getPuestos().get(numPuesto - 1);
        if (!p.isOcupado()) {
            p.setOcupado(true);
            historialReservas.add(new reserva(u, a, p));
            System.out.println("Puesto " + numPuesto + " reservado para " + u.getNombre());
        } else {
            System.out.println("Error: Puesto ya ocupado.");
        }
    }

    public void mostrarEstadoAula(Aula a) {
        System.out.println("Estado de " + a.getNombre() + " (Capacidad: " + a.getCapacidad() + "):");
        for (Puesto p : a.getPuestos()) {
            System.out.print(p.isOcupado() ? "[X] " : "[ ] ");
        }
        System.out.println();
    }
}
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    private List<usuario> listaUsuarios = new ArrayList<>();

    public void alta(usuario u) {
        listaUsuarios.add(u);
        System.out.println("Usuario registrado correctamente.");
    }

    public void listar() {
        if (listaUsuarios.isEmpty()) System.out.println("No hay usuarios.");
        else listaUsuarios.forEach(System.out::println);
    }

    public usuario buscarPorId(String id) {
        return listaUsuarios.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }
}
public class Main {
    public static void main(String[] args) {
        
        new App().ejecutar();
    }
}
public class portatil extends equipo {
    public portatil(String id, String marca, String modelo) {
        super(id, marca, modelo);
    }

    @Override
    public String getTipo() { return "Portátil"; }
}
import java.time.LocalDate;

public class Prestamo {
    private usuario usuario;
    private equipo equipo;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private boolean devuelto;

    public Prestamo(usuario usuario, equipo equipo) {
        this.usuario = usuario;
        this.equipo = equipo;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionPrevista = LocalDate.now().plusDays(2);
        this.devuelto = false;
    }

    public usuario getUsuario() { return usuario; }
    public equipo getEquipo() { return equipo; }
    public LocalDate getFechaDevolucionPrevista() { return fechaDevolucionPrevista; }
    public boolean isDevuelto() { return devuelto; }
    public void registrarDevolucion() { this.devuelto = true; }
}
public class profesor extends usuario {
    private String departamento;

    public profesor(String id, String nombre, String email, String departamento) {
        super(id, nombre, email);
        this.departamento = departamento;
    }

    @Override
    public String getTipo() { return "Profesor"; }
}
public class Puesto {
    private int numero;
    private boolean ocupado;

    public Puesto(int numero) {
        this.numero = numero;
        this.ocupado = false;
    }

    public int getNumero() { return numero; }
    public boolean isOcupado() { return ocupado; }
    public void setOcupado(boolean ocupado) { this.ocupado = ocupado; }
}
import java.time.LocalDateTime;

public class reserva {
    private usuario  usuario;
    private Aula aula;
    private Puesto puesto;
    private LocalDateTime fechaHora;

    public reserva(usuario usuario, Aula aula, Puesto puesto) {
        this.usuario = usuario;
        this.aula = aula;
        this.puesto = puesto;
        this.fechaHora = LocalDateTime.now();
    }

    public usuario getUsuario() { return usuario; }
    public Aula getAula() { return aula; }
    public Puesto getPuesto() { return puesto; }
}
public class tablet extends equipo {
    public tablet(String id, String marca, String modelo) {
        super(id, marca, modelo);
    }

    @Override
    public String getTipo() { return "Tablet"; }
}
public abstract class usuario {
    protected String id;
    protected String nombre;
    protected String email;

    public usuario(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public abstract String getTipo(); 

    @Override
    public String toString() {
        return "[" + getTipo() + "] " + nombre + " (ID: " + id + ")";
    }
}