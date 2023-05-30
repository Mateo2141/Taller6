package logica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pedido {
	private int numeroPedidos;
	private int idPedido;
	private String nombreCliente;
	private List<Producto> listaProduc = new ArrayList<>();
	private String direccionCliente;
	
	
	public Pedido(int numeroPedidos, String nombreCliente, String direccionCliente) {
		super();
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.numeroPedidos = numeroPedidos;
	}
	

	public List<Producto> getListaProduc() {
		return listaProduc;
	}


	public String getIdPedido() {
	    String data = numeroPedidos + nombreCliente + direccionCliente;
	    String encryptedId = encriptar(data);
	    return encryptedId;
	}

	private String encriptar(String data) {
		Random random = new Random();
	    StringBuilder encryptedId = new StringBuilder();
	    for (int i = 0; i < 10; i++) {
	        encryptedId.append(random.nextInt(10));
	    }
	    return encryptedId.toString();
	}
	
	public void agregarProducto(Producto nuevoItem) throws ExcepcionPrecio {
	    listaProduc.add(nuevoItem);

	    int precioTotalPedido = getPrecioTotalPedido();
	    if (precioTotalPedido > 150000) {
	        throw new ExcepcionPrecio("El total del pedido supera los $150.000");
	    }
	}
	
	
	private int getPrecioNetoPedido() {
	    int PNeto = listaProduc.stream()
	            .mapToInt(Producto::getPrecio)
	            .sum();
	    return PNeto;
	}

	
	private int getPrecioIVAPedido() {
	    int Iva = listaProduc.stream()
	            .mapToInt(item -> Math.round(item.getPrecio() * 0.19f))
	            .sum();
	    return Iva;
	}

	
	private int getPrecioTotalPedido() {
		int res = getPrecioNetoPedido() + getPrecioIVAPedido();
		return res;
	}
	
	public String generarTextoFactura() {
	    StringBuilder factura = new StringBuilder();
	    String formatPrint = "%-26.26s %16.16s";
	    factura.append("FACTURA BURGER ");
	    factura.append(String.format(formatPrint, "ID Factura:", idPedido)).append("\n");
	    factura.append(String.format(formatPrint, "Direccion:", direccionCliente)).append("\n\n");
	    factura.append(String.format(formatPrint, "Cliente:", nombreCliente)).append("\n");
	    listaProduc.forEach(producto -> factura.append(producto.generarTextoFactura()).append("\n"));
	    factura.append("\n\n").append(String.format(formatPrint, "Valor Neto:", getPrecioNetoPedido()));
	    factura.append("\n").append(String.format(formatPrint, "Valor IVA:", getPrecioIVAPedido()));
	    factura.append("\n\n").append(String.format(formatPrint, "Valor Total:", getPrecioTotalPedido()));

	    return factura.toString();
	}
	
	public void guardarFactura(Path filePath) {
	    try {
	        String factura = generarTextoFactura();
	        Files.writeString(filePath, factura, StandardOpenOption.CREATE);
	        System.out.println(factura);
	        System.out.println("Guardado");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
