package Test;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import logica.Ingrediente;
import logica.ProductoAjustado;
import logica.ProductoMenu;

public class ProductoAjustadoTest {
    private ProductoMenu base;
    private ProductoAjustado productoAjustado;

    @Before
    public void setUp() {
        base = new ProductoMenu("costeña", "20000");
        productoAjustado = new ProductoAjustado(base);
    }

    @Test
    public void testGetNombre() {
        String nombre = productoAjustado.getNombre();
        assertEquals("costeña", nombre);
    }

    @Test
    public void testGetPrecio() {
        int precio = productoAjustado.getPrecio();
        assertEquals(20000, precio);
    }

    @Test
    public void testGenerarTextoFactura() {
        String factura = productoAjustado.generarTextoFactura();
        String eFactura = String.format("%-26s", "costeña") + String.format("%16s", "20000");
        assertEquals(eFactura, factura);
    }
    
    @Test
    public void testAgregados() {
        Ingrediente ingrediente = new Ingrediente("lechuga", 1000);
        productoAjustado.agregados(ingrediente);
        int precio = productoAjustado.getPrecio();
        assertEquals(21000, precio);
    }

    @Test
    public void testEliminados() {
        Ingrediente ingrediente = new Ingrediente("lechuga", 0);
        productoAjustado.eliminados(ingrediente);
        String factura = productoAjustado.generarTextoFactura();
        String eFactura = String.format("%-26s", " menos lechuga") + String.format("%16s");
        assertEquals(eFactura, factura);
    }

    
}
