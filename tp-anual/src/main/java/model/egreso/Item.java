package model.egreso;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
@Table(name = "items")
public class Item {

	@Id
	@GeneratedValue
	private Long id;
	
	private String descripcion;
	private BigDecimal precioUnitario;
	private Integer cantidadUnidades;
	
	
	public Item() {
	}
	
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