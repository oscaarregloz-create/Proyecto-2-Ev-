import java.util.Scanner;

public class App {
    private GestorUsuarios gUsuarios = new GestorUsuarios();
    private gestorReservas gReservas = new gestorReservas();
    private gestorprestamos gPrestamos = new gestorprestamos();
    private Aula aulaPrincipal = new Aula("Biblioteca Central", 20);
    private equipo laptop1 = new Portatil("P01", "Dell", "Latitude");

    public void ejecutar() {
        Scanner sc = new Scanner(System.in);
      
        gUsuarios.alta(new Alumno("100", "Ana García", "ana@edu.es", "1º DAW"));

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