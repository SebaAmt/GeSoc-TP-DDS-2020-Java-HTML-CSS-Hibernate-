package model.direccion;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.exception.MeliApiException;

public class MeLiMapper <T>{

    private ObjectMapper mapper;
	
    public MeLiMapper() {
    	this.mapper = new ObjectMapper();
    }
    
	public T mappearGenerico(String jsonResponse, TypeReference<T> referencia, String modelo) {
		try {
			return mapper.readValue(jsonResponse, referencia);
		} catch (Exception e) {
			throw new MeliApiException(
					"Ocurrio un error al mapear el json de respuesta de " + modelo + " . Descripcion del error: "
							+ e.getMessage());
		}
	}
}
