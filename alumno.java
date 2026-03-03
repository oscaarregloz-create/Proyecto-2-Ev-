public class Alumno extends Usuario {
    private String curso;

    public Alumno(String id, String nombre, String email, String curso) {
        super(id, nombre, email);
        this.curso = curso;
    }

    @Override
    public String getTipo() { return "Alumno"; }
}