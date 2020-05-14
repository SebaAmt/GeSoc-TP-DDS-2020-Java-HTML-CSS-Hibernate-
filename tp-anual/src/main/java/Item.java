import java.math.BigDecimal;

public class Item {

	private String descripcion;
	private BigDecimal valor;
	
	
	public Item(String descripcion, BigDecimal valor) {
		setDescripcion(descripcion);
		setValor(valor);
	}
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}	

}