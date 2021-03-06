package model.direccion;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Provincia {
		
	@Transient
	@NotNull(message = "Id no puede estar vacio")
	private String id;
		
	@NotNull(message = "Nombre no puede estar vacio")
	private String name;

	@JsonCreator
	public Provincia(@JsonProperty("id") String id, @JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
	}
	
	public Provincia() {
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
