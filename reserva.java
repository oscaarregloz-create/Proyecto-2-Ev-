import java.time.LocalDateTime;

public class Reserva {
    private Usuario usuario;
    private Aula aula;
    private Puesto puesto;
    private LocalDateTime fechaHora;

    public Reserva(Usuario usuario, Aula aula, Puesto puesto) {
        this.usuario = usuario;
        this.aula = aula;
        this.puesto = puesto;
        this.fechaHora = LocalDateTime.now();
    }

    public Usuario getUsuario() { return usuario; }
    public Aula getAula() { return aula; }
    public Puesto getPuesto() { return puesto; }
}