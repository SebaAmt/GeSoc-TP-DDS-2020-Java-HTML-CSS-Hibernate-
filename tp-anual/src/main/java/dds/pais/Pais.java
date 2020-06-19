package dds.pais;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "name"})
public class Pais {

	@NotNull(message = "Id no puede estar vacio")
	private String id;
	
	@NotNull(message = "Nombre no puede estar vacio")
	private String name;

	@JsonIgnore
	private List<Provincia> provincias = new ArrayList<>();
	

	@JsonCreator
	public Pais(@JsonProperty("id") String id, @JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public List<Provincia> getProvincias() {
		return provincias;
	}
}
