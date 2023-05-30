package vista;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

import logica.ExcepcionPrecio;
import logica.Ingrediente;
import logica.Pedido;
import logica.Producto;
import logica.ProductoAjustado;
import logica.ProductoMenu;
import logica.Restaurante;

public class Aplicacion {
	private Restaurante restaurante;
    private Scanner scanner;
    public Aplicacion() {
        restaurante = new Restaurante();
        scanner = new Scanner(System.in); 
    }

    public void mostrarMenu() {
        System.out.println("1. Mostrar El Menú");
        System.out.println("2. Iniciar Un Nuevo Pedido");
        System.out.println("3. Agregar Un Elemento Al Pedido");
        System.out.println("4. Cerrar El Pedido y Guardar La Factura");
        System.out.println("5. Consultar La Información De Un Pedido Dado Su ID");
    }

    public void ejecutarOpcion() throws ExcepcionPrecio {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione una opción:\n");
        boolean continuar = true;
        while (continuar) {
            if (continuar) {
            	mostrarMenu();
                int opcion = scanner.nextInt();
                if (opcion == 1) {
                    mostrarElMenu();
                } else if (opcion == 2) {
                    IniciarUnNuevoPedido();
                } else if (opcion == 3) {
                    AgregarUnElementoAlPedido();
                } else if (opcion == 4) {
                    CerrarElPedidoyGuardarLaFactura();
                } else if (opcion == 5) {
                    ConsultarLaInformacionDeUnPedidoDadoSuID();
                } else {
                    System.out.println("Rectifique la opción seleccionada");
                }
               } else {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }


    private void mostrarElMenu() {
        ArrayList<Producto> menuBase = restaurante.getMenuBase();
        ArrayList<Ingrediente> ingredientes = (ArrayList<Ingrediente>) restaurante.getlIngredientes();
        String formatPrint = "%-5.5s %-26.26s %6.6s";
        System.out.println("Menu\n");
        System.out.println();
        IntStream.range(0, menuBase.size())
                .forEach(index -> {
                    Producto producto = menuBase.get(index);
                    System.out.format(formatPrint, (index + 1) + "- ", producto.getNombre(), producto.getPrecio());
                    System.out.println();
                });

        System.out.println("Ingredientes\n"); 
        System.out.println();
        IntStream.range(0, ingredientes.size())
                .forEach(index -> {
                    Ingrediente ingrediente = ingredientes.get(index);
                    System.out.format(formatPrint, (index + 1) + "- ", ingrediente.getNombre(), ingrediente.getCostoAdicional());
                    System.out.println();
                });
    }


    private void IniciarUnNuevoPedido() {
        if (restaurante.getPedidoEnCurso() == null) {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Direccion: ");
            String direccion = scanner.nextLine();
            restaurante.iniciarPedido(nombre, direccion);
        } else {
            System.out.println("Actualmente existe un pedido en proceso");
        }
    }

    private Producto seleccionarElemento() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Elemento #: ");
        int elemento = scanner.nextInt();
        scanner.nextLine();
        Producto producto;
        if (elemento >= 1 && elemento <= restaurante.getMenuBase().size()) {
            producto = restaurante.getMenuBase().get(elemento - 1);
            if (producto instanceof ProductoMenu) {
                ProductoMenu productoMenu = (ProductoMenu) producto;
                System.out.println("¿Desea cambiar ingredientes de " + producto.getNombre() + "?");
                System.out.println("Opción 1: Sí");
                System.out.println("Opción 2: No");
                int cambioProduc = scanner.nextInt();
                if (cambioProduc == 1) {
                    ProductoAjustado productoAjustado = new ProductoAjustado(productoMenu);
                    boolean centinela = true;
                    while (centinela) {
                        System.out.print("\nNúmero del ingrediente: ");
                        int lnIngredi = scanner.nextInt();
                        if (lnIngredi >= 1 && lnIngredi <= restaurante.getlIngredientes().size()) {
                            Ingrediente ingrediente = restaurante.getlIngredientes().get(lnIngredi - 1);
                            System.out.println("¿Desea agregar o quitar ingredientes?");
                            System.out.println("Opción 1: Agregar");
                            System.out.println("Opción 2: Quitar");
                            cambioProduc = scanner.nextInt();
                            if (cambioProduc == 1) {
                                productoAjustado.agregados(ingrediente);
                            } else {
                                productoAjustado.eliminados(ingrediente);
                            }
                            System.out.println("¿Desea cambiar ingredientes de " + producto.getNombre() + "?");
                            System.out.println("Opción 1: Sí");
                            System.out.println("Opción 2: No");
                            int opcion = scanner.nextInt();
                            if (opcion == 2) {
                                centinela = false;
                            }
                        } else {
                            System.out.println("El número ingresado está fuera del intervalo");
                        }
                    }
                    producto = productoAjustado;
                }
            }
        } else {
            System.out.println("Opción inválida.");
            return null;
        }
        return producto;
    }

    private void AgregarUnElementoAlPedido() throws ExcepcionPrecio {
        if (restaurante.getPedidoEnCurso() == null) {
            System.out.println("No existe ningún pedido en curso.");
            return;
        }
        System.out.println("Elementos para agregar: ");

        Producto producto = seleccionarElemento();
        if (producto == null) {
            return;
        }

        restaurante.getPedidoEnCurso().agregarProducto(producto);
    }

    private void CerrarElPedidoyGuardarLaFactura() {
        Pedido pedidoActual = restaurante.getPedidoEnCurso();
        if (pedidoActual == null) {
            System.out.println("No existe ningún pedido en curso.");
            return;
        }
        int verificar = restaurante.cerrarYGuardarPedido();
    }

    private void ConsultarLaInformacionDeUnPedidoDadoSuID() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Cuál es el ID a consultar? ");
        String id = scanner.nextLine();
        Pedido pedido = restaurante.getID(id);
        String mensaje = pedido == null ? "No existe un pedido con ese ID." : pedido.generarTextoFactura();
        System.out.println(mensaje);
    }

    public static void main(String[] args) throws ExcepcionPrecio {
        Aplicacion app = new Aplicacion();
        app.ejecutarOpcion();
        app.cerrarScanner(); }

    private void cerrarScanner() {
        scanner.close();
    }
}
