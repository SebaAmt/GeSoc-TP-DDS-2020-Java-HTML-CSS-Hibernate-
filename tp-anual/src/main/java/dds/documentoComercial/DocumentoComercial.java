package dds.documentoComercial;
public class DocumentoComercial {

	private TipoDocumentoComercial tipoDocumentoComercial;
	private Integer identificadorDocumento;
	
	
	public DocumentoComercial(TipoDocumentoComercial tipoDocumentoComercial, Integer identificadorDocumento) {
		this.tipoDocumentoComercial = tipoDocumentoComercial;
		this.identificadorDocumento = identificadorDocumento;
	}
	
	
	public TipoDocumentoComercial getTipoDocumentoComercial() {
		return tipoDocumentoComercial;
	}
	
	public Integer getIdentificadorDocumento() {
		return identificadorDocumento;
	}
}
