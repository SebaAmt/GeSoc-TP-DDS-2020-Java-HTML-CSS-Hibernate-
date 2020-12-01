package model.documentoComercial;

public enum TipoDocumentoComercial {
	FACTURA("Factura"),
	NOTA_DE_DEBITO("Nota de débito"),
	NOTA_DE_CREDITO("Nota de crédito"),
	RECIBO("Recibo"),
	CHEQUE("Cheque"),
	PAGARE("Pagaré"),
	ORDEN_DE_COMPRA("Orden de compra"),
	BOLETA_DE_DEPOSITO_BANCARIO("Boleta de depósito bancario"),
	REMITO("Remito");

	private String nombre;

	TipoDocumentoComercial(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}
