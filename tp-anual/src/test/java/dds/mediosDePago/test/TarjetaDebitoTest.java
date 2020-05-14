package dds.mediosDePago.test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dds.mediosDePago.TarjetaDebito;
import dds.mediosDePago.TipoTarjeta;

class TarjetaDebitoTest {

	@Test
	public void comprobarIdentificadorDeTarjetaCorrecto() {
		// Arrange
		int identificadorEsperado = 7155135;

		// Act
		TarjetaDebito tarjeta = new TarjetaDebito(identificadorEsperado, TipoTarjeta.VISA);
		int identificadorActual = tarjeta.getIdentificador();

		// Assert
		assertEquals(identificadorEsperado, identificadorActual);
	}

	@Test
	public void comprobarTipoDeTarjetaCorrecto() {
		// Arrange
		TipoTarjeta tipoTarjetaEsperado = TipoTarjeta.MASTERCARD;

		// Act
		TarjetaDebito tarjeta = new TarjetaDebito(546416651, tipoTarjetaEsperado);
		TipoTarjeta tipoTarjetaActual = tarjeta.getTipo();

		// Assert
		assertEquals(tipoTarjetaEsperado, tipoTarjetaActual);
	}

}
