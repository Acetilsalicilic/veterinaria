package veterinaria.utiles;

public class Campo {
    public String etiqueta;
    public String valor;
    public Campo(String etiqueta, String valor) {
        this.etiqueta = etiqueta;
        this.valor = valor;
    }
    public Campo(String etiqueta, Integer valor) {
        this(etiqueta, Integer.toString(valor));
    }
}
