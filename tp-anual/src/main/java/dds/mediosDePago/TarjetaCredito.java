package dds.mediosDePago;

public class TarjetaCredito implements MedioDePago {
	private int identificador;
	private TipoTarjeta tipo;

	public TarjetaCredito(int identificador, TipoTarjeta tipo) {
		this.identificador = identificador;
		this.tipo = tipo;
	}

	public int getIdentificador() {
		return this.identificador;
	}

	public TipoTarjeta getTipo() {
		return this.tipo;
	}
}
