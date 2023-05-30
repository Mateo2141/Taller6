package logica;

public class ProductoRepetidoException extends HamburguesaException {
    private String nombreProducto;

    public ProductoRepetidoException(String nombreProducto) {
        super("Error Producto repetido: " + nombreProducto);
        this.nombreProducto = nombreProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }
}