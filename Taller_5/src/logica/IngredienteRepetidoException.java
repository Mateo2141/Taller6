package logica;

public class IngredienteRepetidoException extends HamburguesaException {
    private String nombreIngrediente;

    public IngredienteRepetidoException(String nombreIngrediente) {
        super("Error Ingrediente repetido: " + nombreIngrediente);
        this.nombreIngrediente = nombreIngrediente;
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }
}	
