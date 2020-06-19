package dds.meLiApi.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.sun.jersey.api.client.Client;

import dds.exception.MeliApiException;
import dds.meLiApi.MeLiApi;
import dds.pais.Moneda;
import dds.pais.Pais;
import dds.pais.Provincia;
import dds.pais.Ciudad;

public class MeLiApiTest {

  @Test
  public void obtenerPaisTest() {
    MeLiApi meli = new MeLiApi(Client.create());
    Pais pais = meli.obtenerPais("AR");
    assertThat(pais.getId(), is("AR"));
    assertThat(pais.getName(), is("Argentina"));
  }

  @Test
  public void obtenerProvinciaTest() {
    MeLiApi meli = new MeLiApi(Client.create());
    Provincia provincia = meli.obtenerProvincia("TUxBUENBUGw3M2E1");
    assertThat(provincia.getId(), is("TUxBUENBUGw3M2E1"));
    assertThat(provincia.getName(), is("Capital Federal"));
  }
  
  @Test
  public void obtenerCiudadTest() {
    MeLiApi meli = new MeLiApi(Client.create());
    Ciudad ciudad = meli.obtenerCiudad("TUxBQ0NBUGZlZG1sYQ");
    assertThat(ciudad.getId(), is("TUxBQ0NBUGZlZG1sYQ"));
    assertThat(ciudad.getName(), is("Capital Federal"));
  }
  
  @Test
  public void obtenerMonedaTest() {
    MeLiApi meli = new MeLiApi(Client.create());
    Moneda moneda = meli.obtenerMoneda("ARS");
    assertThat(moneda.getSymbol(), is("$"));
  }

  @Test
  public void obtenerPaisFail() {
    MeLiApi meli = new MeLiApi(Client.create());
    Exception exception = assertThrows(MeliApiException.class, () -> meli.obtenerPais("ZZ"));
    assertThat(exception.getMessage(), is("Ocurrio un error al obtener una respuesta. Código de respuesta recbido: Not Found"));
  }
}

