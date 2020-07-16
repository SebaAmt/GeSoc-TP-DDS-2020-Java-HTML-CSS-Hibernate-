package dds.direccion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DireccionPostal {

	private String codigoPostal;
	private Pais pais;
	private Provincia provincia;
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
