package model.meLiApi.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import model.direccion.DireccionPostal;
import model.egreso.Moneda;
import model.exception.MeliApiException;
import model.meLiApi.MeLiApi;


public class MeLiApiTest {


	@Test
	public void obtenerMonedaTest() {
		Moneda moneda = MeLiApi.obtenerMoneda("ARS");
		assertThat(moneda.getSymbol(), is("$"));
	}
   
	@Test
	public void obtenerMonedaExceptionTest() {
		Exception exception = assertThrows(MeliApiException.class, () -> MeLiApi.obtenerMoneda("DOL"));
		assertThat(exception.getMessage(), is("Ocurrio un error al obtener una respuesta. Código de respuesta recbido: Not Found"));
	}
	
	@Test
	public void obtenerDireccionPostalTest() {
		DireccionPostal direccionPostal = MeLiApi.obtenerDireccionPostal("AR", "1420");
		assertThat(direccionPostal.getPais().getName(), is("Argentina"));
	}
	
	@Test
	public void obtenerDireccionPostalExceptionTest() {
		Exception exception = assertThrows(MeliApiException.class, () -> MeLiApi.obtenerDireccionPostal("AR", "1410"));
		assertThat(exception.getMessage(), is("Ocurrio un error al obtener una respuesta. Código de respuesta recbido: Not Found"));
	}
}


