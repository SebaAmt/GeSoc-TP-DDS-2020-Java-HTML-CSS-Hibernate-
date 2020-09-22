package dds.direccion;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "direcciones_postales")
public class DireccionPostal {

	@Id
	@GeneratedValue
	private Long id;
	private String codigoPostal;
	@AttributeOverride(column = @Column(name = "pais"), name = "name")
	private Pais pais;
	@AttributeOverride(column = @Column(name = "provincia"), name = "name")
	private Provincia provincia;
	@AttributeOverride(column = @Column(name = "ciudad"), name = "name")
	private Ciudad ciudad;

	@JsonCreator
	public DireccionPostal(@JsonProperty("zip_code") String codigoPostal,
						   @JsonProperty("country") Pais pais,
						   @JsonProperty("state") Provincia provincia,
						   @JsonProperty("city") Ciudad ciudad) {
		this.codigoPostal = codigoPostal;
		this.pais = pais;
		this.provincia = provincia;
		this.ciudad = ciudad;
	}

	public DireccionPostal() {
	}
	
	public String getCodigoPostal() {
		return codigoPostal;
	}

	public Pais getPais() {
		return pais;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

}
