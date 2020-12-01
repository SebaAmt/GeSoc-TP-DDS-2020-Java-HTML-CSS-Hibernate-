package model.mediosDePago;

public enum TipoMedioDePago {
	CAJERO("Cajero"),
	TARJETA_CREDITO("Tarjeta de crédito"),
	TARJETA_DEBITO("Tarjeta de débito"),
	EFECTIVO("Efectivo"),
	DINERO_EN_CUENTA("Dinero en cuenta");

	private String nombre;

	TipoMedioDePago(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
