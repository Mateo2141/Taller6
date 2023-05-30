package Test;
import org.junit.Assert;
import org.junit.Test;

import logica.ProductoMenu;

public class ProductoMenuTest {

    @Test
    public void testGetNombre() {
        String nombre = "hawaiana";
        ProductoMenu producto = new ProductoMenu(nombre, "20000");
        String nombreObtenido = producto.getNombre();
        Assert.assertEquals(nombre, nombreObtenido);
    }

    @Test
    public void testGetPrecio() {
        String precioBase = "20000";
        ProductoMenu producto = new ProductoMenu("hawaiana", precioBase);
        int precioObtenido = producto.getPrecio();
        Assert.assertEquals(Integer.parseInt(precioBase), precioObtenido);
    }

    @Test
    public void testGenerarTextoFactura() {
        String nombre = "hawaiana";
        String precioBase = "20000";
        ProductoMenu producto = new ProductoMenu(nombre, precioBase);
        String textoFactura = producto.generarTextoFactura();
        String expected = String.format("%-26s%16s", nombre, precioBase);
        Assert.assertEquals(expected, textoFactura);
    }
}
