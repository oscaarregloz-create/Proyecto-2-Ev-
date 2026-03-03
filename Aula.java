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