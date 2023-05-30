package Test;
import logica.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {
    private Restaurante restaurante;
    private Pedido pedido;

    @BeforeEach
    public void setUp() {
        restaurante = new Restaurante();
        pedido = new Pedido(1, "Cliente", "calle algo");
    }

    @Test
    public void testAgregarProducto() {
        Producto producto = new ProductoMenu("Hamburguesa", "10000");

        try {
            pedido.agregarProducto(producto);
            List<Producto> listaProductos = pedido.getListaProduc();
            assertEquals(1, listaProductos.size());
            assertEquals(producto, listaProductos.get(0));
        } catch (ExcepcionPrecio e) {
            fail("No se debería lanzar una excepción en este caso");
        }
    }

    @Test
    public void testAgregarProductoExcepcionPrecio() {
        Producto producto1 = new ProductoMenu("Hamburguesa", "80000");
        Producto producto2 = new ProductoMenu("Papas", "80000");

        try {
            pedido.agregarProducto(producto1);
            assertThrows(ExcepcionPrecio.class, () -> pedido.agregarProducto(producto2));
        } catch (ExcepcionPrecio e) {
            fail("No se debería lanzar una excepción en este caso");
        }
    }

    @Test
    public void testGenerarTextoFactura() {
        Producto producto1 = new ProductoMenu("Hamburguesa", "10000");
        Producto producto2 = new ProductoMenu("Papas", "5000");

        try {
            pedido.agregarProducto(producto1);
            pedido.agregarProducto(producto2);

            String facturaEsperada = "FACTURA BURGER " +
                    "ID Factura:                               " + pedido.getIdPedido() + "\n" +
                    "Direccion:                       calle algo\n\n" +
                    "Cliente:                            Cliente\n" +
                    "Hamburguesa                          10000\n" +
                    "Papas                                 5000\n" +
                    "\n\nValor Neto:                           15000" +
                    "\nValor IVA:                             2850" +
                    "\n\nValor Total:                          17850"+
                    "\nGuardado";
          assertEquals(facturaEsperada, pedido.generarTextoFactura());
        } catch (ExcepcionPrecio e) {
            fail("No se debería lanzar una excepción en este caso");
        }
    }

    @Test
    public void testGuardarFactura() {
        Producto producto1 = new ProductoMenu("Hamburguesa", "10000");
        Producto producto2 = new ProductoMenu("Papas", "5000");

        try {
            pedido.agregarProducto(producto1);
            pedido.agregarProducto(producto2);

            Path filePath = Paths.get("data", "pedido.txt");
            pedido.guardarFactura(filePath);

        } catch (ExcepcionPrecio e) {
            fail("No se debería lanzar una excepción en este caso");
        }
    }
}
