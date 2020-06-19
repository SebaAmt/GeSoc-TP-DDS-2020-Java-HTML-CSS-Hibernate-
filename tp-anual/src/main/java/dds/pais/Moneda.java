package dds.pais;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "symbol, description"})
public class Moneda {

	@NotNull(message = "Id no puede estar vacio")
	private String id;

	@NotNull(message = "Simbolo no puede estar vacio")
	private String symbol;
	
	@NotNull(message = "Descripcion no puede estar vacia")
	private String description;
	

	@JsonCreator
	public Moneda(@JsonProperty("id") String id, @JsonProperty("symbol") String symbol, @JsonProperty("description") String description) {
		this.id = id;
		this.symbol = symbol;
		this.description = description;
	}

	
	public String getId() {
		return id;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getDescription() {
		return description;
	}
}
