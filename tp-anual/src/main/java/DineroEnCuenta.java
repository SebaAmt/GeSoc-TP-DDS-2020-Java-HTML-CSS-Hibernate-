import java.math.BigDecimal;

public class DineroEnCuenta implements MedioDePago {
	private int identificador;
	private BigDecimal saldo;

	public DineroEnCuenta(int identificador) {
		this.identificador = identificador;
		this.saldo = new BigDecimal(0);
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal monto) {
		this.saldo = monto;
	}

	public int getIdentificador() {
		return this.identificador;
	}
}
