package dds.meLiApi;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

import dds.exception.MeliApiException;
import dds.pais.Pais;
import dds.pais.Provincia;
import dds.pais.Ciudad;
import dds.pais.Barrio;
import dds.pais.Moneda;

public class MeLiApi {


    private Client client;
    private static final String API_GOOGLE = "https://api.mercadolibre.com";
    private static final String COUNTRY_RESOURCE = "classified_locations/";
    private ObjectMapper mapper;

    public MeLiApi(Client client) {
        this.client = client;
        this.mapper = new ObjectMapper();
  }

    public Pais obtenerPais(String idCountry) {
        String path = COUNTRY_RESOURCE + "countries/" + idCountry;
        String jsonResponse = getResponse(path);
        try {
          return mapper.readValue(jsonResponse, new TypeReference<Pais>() {});
        } catch (Exception e) {
          throw new MeliApiException("Ocurrio un error al mapear el json de respuesta de países. Descripcion del error: "
              + e.getMessage());
        }
    }

    public Provincia obtenerProvincia(String idState) {
        String path = COUNTRY_RESOURCE + "states/" + idState;
        String jsonResponse = getResponse(path);
        try {
          return mapper.readValue(jsonResponse, new TypeReference<Provincia>() {});
        } catch (Exception e) {
          throw new MeliApiException("Ocurrio un error al mapear el json de respuesta de países. Descripcion del error: "
              + e.getMessage());
        }
    }
    
    public Ciudad obtenerCiudad(String idCity) {
        String path = COUNTRY_RESOURCE + "cities/" + idCity;
        String jsonResponse = getResponse(path);
        try {
          return mapper.readValue(jsonResponse, new TypeReference<Ciudad>() {});
        } catch (Exception e) {
          throw new MeliApiException("Ocurrio un error al mapear el json de respuesta de países. Descripcion del error: "
              + e.getMessage());
        }
    }
    
    public Barrio obtenerBarrio(String idNeighborhood) {
        String path = COUNTRY_RESOURCE + "neighborhoods/" + idNeighborhood;
        String jsonResponse = getResponse(path);
        try {
          return mapper.readValue(jsonResponse, new TypeReference<Barrio>() {});
        } catch (Exception e) {
          throw new MeliApiException("Ocurrio un error al mapear el json de respuesta de países. Descripcion del error: "
              + e.getMessage());
        }
    }
    
    public Moneda obtenerMoneda(String currencyID) {
    	String path = "currencies/" + currencyID;
      String jsonResponse = getResponse(path);
         try {
          return mapper.readValue(jsonResponse, new TypeReference<Moneda>() {});
        } catch (Exception e) {
          throw new MeliApiException("Ocurrio un error al mapear el json de respuesta de monedas. Descripcion del error: "
              + e.getMessage());        
        }
    }

    private String getResponse(String path) {
        WebResource resource = this.client.resource(API_GOOGLE);
        resource = resource.path(path);
        WebResource.Builder builder = resource.accept(MediaType.APPLICATION_JSON);
        ClientResponse response = builder.get(ClientResponse.class);
        Status responseStatus = response.getClientResponseStatus();
        if(!Status.OK.equals(responseStatus)) {
          throw new MeliApiException("Ocurrio un error al obtener una respuesta. Código de respuesta recbido: "
              + responseStatus);
        }
        return response.getEntity(String.class);
    }
    
}

