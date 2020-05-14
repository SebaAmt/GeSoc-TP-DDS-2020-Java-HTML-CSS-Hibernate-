
public class Efectivo implements MedioDePago {
	private int identificador;
	private TipoEfectivo tipo;

	public Efectivo(int identificador, TipoEfectivo tipo) {
		this.identificador = identificador;
		this.tipo = tipo;
	}

	public int getIdentificador() {
		return this.identificador;
	}

	public TipoEfectivo getTipo() {
		return this.tipo;
	}

}
