package Test;import org.junit.jupiter.api.Test;

import logica.Combo;
import logica.Producto;
import logica.ProductoMenu;

import static org.junit.jupiter.api.Assertions.*;

public class ComboTest {

    @Test
    public void testAgregarItemACombo() {
        Producto producto1 = new ProductoMenu("corral", "14000");
        Producto producto2 = new ProductoMenu("casera", "23000");
        Combo combo = new Combo(0.1, "Combo");
        combo.agregarItemACombo(producto1);
        combo.agregarItemACombo(producto2);
        assertEquals(37000, combo.getPrecio());
    }

    @Test
    public void testAgregarItemAComboConProductoNulo() {
        Combo combo = new Combo(0.1, "combo especial");
        combo.agregarItemACombo(null);
        assertEquals(0, combo.getPrecio());
    }

    @Test
    public void testGenerarTextoFactura() {
        Producto producto1 = new ProductoMenu("corral", "14000");
        Producto producto2 = new ProductoMenu("casera", "23000");
        Combo combo = new Combo(0.1, "combo especial");
        combo.agregarItemACombo(producto1);
        combo.agregarItemACombo(producto2);
        String textoFactura = combo.generarTextoFactura();
        assertEquals("combo especial                       37000", textoFactura);
    }

    @Test
    public void testGetNombreCombo() {
        Combo combo = new Combo(0.1, "combo especial");
        assertEquals("combo especial", combo.getNombreCombo());
    }


}
