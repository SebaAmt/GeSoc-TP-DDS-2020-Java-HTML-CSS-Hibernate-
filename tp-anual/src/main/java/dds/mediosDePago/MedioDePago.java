package dds.mediosDePago;

public class MedioDePago {
	private TipoMedioDePago medio;
	private String identificador;

	public MedioDePago(TipoMedioDePago medio, String identificador) {
		this.medio = medio;
		this.identificador = identificador;
	}

	public TipoMedioDePago getMedio() {
		return this.medio;
	}

	public String getIdentificador() {
		return this.identificador;
	}
}
