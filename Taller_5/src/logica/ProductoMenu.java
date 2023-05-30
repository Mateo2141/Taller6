package logica;

public class ProductoMenu implements Producto {
	private String nombre;
	private String precioBase;
	
	public ProductoMenu(String nombre, String precioBase) {
		super();
		this.nombre = nombre;
		this.precioBase = precioBase;
	}

	public String getNombre() {
		// TODO Auto-generated method stub
		return this.nombre;
	}
	
	public int getPrecio() {
		// TODO Auto-generated method stub
		return Integer.parseInt(this.precioBase);
	}


	public String generarTextoFactura() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(String.format("%-26s", nombre));
	    sb.append(String.format("%16s", precioBase));
	    return sb.toString();
	}

	
	
	
}
