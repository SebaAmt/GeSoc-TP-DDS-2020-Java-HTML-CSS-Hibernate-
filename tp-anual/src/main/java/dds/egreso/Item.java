package dds.egreso;
import java.math.BigDecimal;

public class Item {

	private String descripcion;
	private BigDecimal precioUnitario;
	private Integer cantidadUnidades;
	
	
	public Item(String descripcion, BigDecimal precioUnitario, Integer cantidadUnidades) {
		this.descripcion = descripcion;
		this.precioUnitario = precioUnitario;
		this.cantidadUnidades = cantidadUnidades;
	}
	
	
	public BigDecimal valorTotal() {
		return precioUnitario.multiply(BigDecimal.valueOf(cantidadUnidades));
	}
	
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	
	public Integer getCantidadUnidades() {
		return cantidadUnidades;
	}	
}