package logica;

public class ProductoAjustado implements Producto {
	private ProductoMenu base;
	private int valor;
	private String preFactura;

	public ProductoAjustado(ProductoMenu base) {
		super();
		this.base = base;
		this.valor = 0;
		this.preFactura = " ";
	}
	
	public String getNombre() {
		// TODO Auto-generated method stub
		return base.getNombre();
	}
	
	public int getPrecio() {
		// TODO Auto-generated method stub
		return base.getPrecio();
	}


	public String generarTextoFactura() {
		// TODO Auto-generated method stub
		return base.generarTextoFactura();
	}
	
	public void agregados (Ingrediente ingrediente) {
		this.valor += ingrediente.getCostoAdicional();
	    String ingredienteTexto = String.format("%-26s", "incluye " + ingrediente.getNombre());
	    String costoTexto = String.format("%+16s", "+" + Integer.toString(ingrediente.getCostoAdicional()));
	    this.preFactura += "\n" + ingredienteTexto + costoTexto;
	}
	
	public void eliminados(Ingrediente ingrediente) {
	    String ingredienteTexto = String.format("%-26s", " menos " + ingrediente.getNombre());
	    String costoTexto = String.format("%16s");
	    this.preFactura = preFactura.concat("\n").concat(ingredienteTexto).concat(costoTexto);
	}




}
