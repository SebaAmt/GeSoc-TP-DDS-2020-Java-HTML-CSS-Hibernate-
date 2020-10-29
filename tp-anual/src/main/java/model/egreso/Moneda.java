package model.egreso;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "monedas")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Moneda {

	@Id
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

	public Moneda() {
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Moneda other = (Moneda) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
	
}