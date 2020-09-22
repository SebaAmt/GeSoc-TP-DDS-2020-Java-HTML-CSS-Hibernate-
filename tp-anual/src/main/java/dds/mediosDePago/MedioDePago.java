package dds.mediosDePago;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "Medios_de_Pago")
public class MedioDePago {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.ORDINAL)
	@Column (name = "tipoMedioDePago_id")
	private TipoMedioDePago medio;
	private String identificador;

	public MedioDePago(TipoMedioDePago medio, String identificador) {
		this.medio = medio;
		this.identificador = identificador;
	}
	
	public MedioDePago() {
	}

	public TipoMedioDePago getMedio() {
		return this.medio;
	}

	public String getIdentificador() {
		return this.identificador;
	}
}
