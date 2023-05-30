package logica;

public class Combo implements Producto{
	private double descuento;
	private String nombreCombo;
	private int precio;
	
	public Combo(double descuento, String nombreCombo) {
		super();
		this.descuento = descuento;
		this.nombreCombo = nombreCombo;
		this.precio = 0;
	}
	
	public void agregarItemACombo(Producto itemCombo) {
	    if (itemCombo != null) {
	        this.precio += itemCombo.getPrecio();
	    }
	}
	public int getPrecio() {
		return precio;
	}

	public String getNombreCombo() {
		return nombreCombo;
	}
	
	public String generarTextoFactura() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(String.format("%-26s", nombreCombo));
	    sb.append(String.format("%16s", precio));
	    return sb.toString();
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return this.nombreCombo;
	}
}
