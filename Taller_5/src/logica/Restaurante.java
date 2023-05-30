package logica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import logica.ProductoRepetidoException;
import logica.IngredienteRepetidoException;

public class Restaurante {
    private Pedido pedido;
    private int numPedidos;
    private Map<String, Pedido> mapPedidos;
    private List<Ingrediente> lIngredientes;
    private List<Producto> menuBase;
    private Combo combo;

    public Restaurante() {
        super();
        this.numPedidos = 0;
        this.mapPedidos = new HashMap<>();
        this.menuBase = new ArrayList<>();
        this.lIngredientes = new ArrayList<>();

        Path archivoIngredientes = Paths.get("data", "ingredientes.txt");
        Path archivoMenu = Paths.get("data", "menu.txt");
        Path archivoCombos = Paths.get("data", "combos.txt");
        cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
        
    }

    public void iniciarPedido(String nombreCliente, String direccionCliente) {
        pedido = new Pedido(++numPedidos, nombreCliente, direccionCliente);
    }

    public int cerrarYGuardarPedido() {
        int pIgual = (int) mapPedidos.values().stream()
                .filter(recPedidos -> pedido.equals(recPedidos))
                .count();
        mapPedidos.put(pedido.getIdPedido(), pedido);
        Path facturaPath = Paths.get("data", "pedido.txt");
        pedido.guardarFactura(facturaPath);
        try {
            String facturaTexto = combo.generarTextoFactura();
            Files.writeString(facturaPath, facturaTexto, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pedido = null;
        return pIgual;
    }

    public List<Ingrediente> getlIngredientes() {
        return lIngredientes;
    }

    public Pedido getPedidoEnCurso() {
        return this.pedido;
    }

    public Pedido getID(String id) {
        if (mapPedidos.containsKey(id)) {
            pedido = mapPedidos.get(id);
        }
        return pedido;
    }

    public ArrayList<Producto> getMenuBase() {
        return (ArrayList<Producto>) menuBase;
    }

    public void cargarInformacionRestaurante(Path archivoIngredientes, Path archivoMenu, Path archivoCombos) {
            try {
				cargarIngredientes(archivoIngredientes);
				cargarMenu(archivoMenu);
	            cargarCombos(archivoCombos);
			} catch (IngredienteRepetidoException | ProductoRepetidoException | IOException e) {
	            e.printStackTrace();
	        }
            
        }



    private void cargarIngredientes(Path archivoIngredientes) throws IOException, IngredienteRepetidoException {
        try (Stream<String> ln = Files.lines(archivoIngredientes)) {
            ln.map(cadaln -> cadaln.split(";"))
                    .forEach(cadaUno -> {
                        String nombre = cadaUno[0];
                        int extra = Integer.parseInt(cadaUno[1]);
                        Ingrediente cadaIngrediente = new Ingrediente(nombre, extra);

                        if (lIngredientes.stream().anyMatch(ingrediente -> ingrediente.getNombre().equals(nombre))) {
                            throw new RuntimeException(new IngredienteRepetidoException(nombre));
                        }

                        lIngredientes.add(cadaIngrediente);
                    });
        }
    }

    public void cargarMenu(Path archivoMenu) throws IOException, ProductoRepetidoException {
    	try (Stream<String> ln = Files.lines(archivoMenu)) {
            ln.map(cadaln -> cadaln.split(";"))
                    .forEach(cadaUno -> {
                        String nombre = cadaUno[0];
                        int precioBase = Integer.parseInt(cadaUno[1]);
                        Producto cadaProducto = new ProductoMenu(nombre, String.valueOf(precioBase));

                        if (menuBase.stream().anyMatch(producto -> producto.getNombre().equals(nombre))) {
                            throw new RuntimeException(new ProductoRepetidoException(nombre));
                        }

                        menuBase.add(cadaProducto);
                    });
        }
    }


    public void cargarCombos(Path archivoCombos) throws IOException {
        try (Stream<String> ln = Files.lines(archivoCombos)) {
            ln.map(cadaln -> cadaln.split(";"))
                    .forEach(cadaUno -> {
                        String nombre = cadaUno[0];
                        int descuento = Integer.parseInt(cadaUno[1].substring(0, cadaUno[1].length() - 1));
                        Combo combo = new Combo(descuento, nombre);
                        Arrays.stream(cadaUno, 2, 5)
                                .map(nombreProducto -> (Producto) verNombre(nombreProducto))
                                .forEach(combo::agregarItemACombo);
                        Producto producto = combo;
                        menuBase.add(producto);
                    });
        }
    }

    private Producto verNombre(String nombre) {
        return menuBase.stream()
                .filter(producto -> nombre.equals(producto.getNombre()))
                .findFirst()
                .orElse(null);
    }
}

