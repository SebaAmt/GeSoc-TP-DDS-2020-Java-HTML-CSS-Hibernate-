public class DocumentoComercial {

	private TipoDocumentoComercial tipoDocumentoComercial;
	private Integer identificadorDocumento;
	
	
	public DocumentoComercial(TipoDocumentoComercial tipoDocumentoComercial, Integer identificadorDocumento) {
		setTipoDocumentoComercial(tipoDocumentoComercial);
		setIdentificadorDocumento(identificadorDocumento);
	}
	
	
	public TipoDocumentoComercial getTipoDocumentoComercial() {
		return tipoDocumentoComercial;
	}
	
	public void setTipoDocumentoComercial(TipoDocumentoComercial tipoDocumentoComercial) {
		this.tipoDocumentoComercial = tipoDocumentoComercial;
	}
	
	public Integer getIdentificadorDocumento() {
		return identificadorDocumento;
	}
	
	public void setIdentificadorDocumento(Integer identificadorDocumento) {
		this.identificadorDocumento = identificadorDocumento;
	}
}
