public class portatil extends equipo {

    public portatil(String id, String marca, String modelo) {
        super(id, marca, modelo);
    }

    @Override
    public String getTipo() {
        return "Portátil";
    }
}