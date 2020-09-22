package dds.documentoComercial;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "documentos_comerciales")
public class DocumentoComercial {

	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.ORDINAL)
	private TipoDocumentoComercial tipoDocumentoComercial;
	private Integer identificadorDocumento;
	
	
	public DocumentoComercial(TipoDocumentoComercial tipoDocumentoComercial, Integer identificadorDocumento) {
		this.tipoDocumentoComercial = tipoDocumentoComercial;
		this.identificadorDocumento = identificadorDocumento;
	}
	
	public DocumentoComercial() {
	}
	
	public TipoDocumentoComercial getTipoDocumentoComercial() {
		return tipoDocumentoComercial;
	}
	
	public Integer getIdentificadorDocumento() {
		return identificadorDocumento;
	}
}
