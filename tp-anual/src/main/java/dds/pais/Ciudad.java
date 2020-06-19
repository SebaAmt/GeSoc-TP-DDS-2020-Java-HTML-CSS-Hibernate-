package dds.pais;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name"})
public class Ciudad {

	@NotNull(message = "Id no puede estar vacio")
	private String id;
		
	@NotNull(message = "Nombre no puede estar vacio")
	private String name;
	
	
	@JsonCreator
	public Ciudad(@JsonProperty("id") String id, @JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
	}
	
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
