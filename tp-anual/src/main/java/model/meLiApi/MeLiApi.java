package model.meLiApi;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import model.direccion.DireccionPostal;
import model.direccion.MeLiMapper;
import model.egreso.Moneda;
import model.exception.MeliApiException;

public class MeLiApi {


    private static Client client = Client.create() ;
    private static final String API_GOOGLE = "https://api.mercadolibre.com";
    private static final String ZIPCODE_RESOURCE = "countries/IDCOUNTRY/zip_codes/ZIPCODE";
    private static final String CURRENCY_RESOURCE = "currencies/IDCURRENCY";

    public static DireccionPostal obtenerDireccionPostal(String countryId, String zipcode) {
        String path = ZIPCODE_RESOURCE.replace("IDCOUNTRY", countryId);
        path = path.replace("ZIPCODE", zipcode);
        String jsonResponse = getResponse(path);
    	MeLiMapper <DireccionPostal>mapper = new MeLiMapper<DireccionPostal>();
    	return mapper.mappearGenerico(jsonResponse, new TypeReference<DireccionPostal>() {}, "direccion postal");
    }
    
    public static Moneda obtenerMoneda(String currencyId) {
    	String path = CURRENCY_RESOURCE.replace("IDCURRENCY", currencyId);
    	String jsonResponse = getResponse(path);
    	MeLiMapper <Moneda>mapper = new MeLiMapper<Moneda>();
    	return mapper.mappearGenerico(jsonResponse, new TypeReference<Moneda>() {}, "moneda");
    }
    	
    private static String getResponse(String path) {
        WebResource resource = client.resource(API_GOOGLE);
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

