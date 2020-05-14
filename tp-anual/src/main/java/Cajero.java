import java.math.BigDecimal;

public class Cajero implements MedioDePago {
	private BigDecimal saldo;
	private int identificador;

	public Cajero(int identificador) {
		this.identificador = identificador;
		this.saldo = new BigDecimal(0);
	}

	public int getIdentificador() {
		return this.identificador;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal monto) {
		this.saldo = monto;
	}
}
