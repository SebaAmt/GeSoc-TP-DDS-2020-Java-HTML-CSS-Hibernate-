package dds.mediosDePago.test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dds.mediosDePago.TarjetaCredito;
import dds.mediosDePago.TipoTarjeta;

class TarjetaCreditoTest {

	@Test
	public void comprobarIdentificadorDeTarjetaCorrecto() {
		// Arrange
		int identificadorEsperado = 42355135;

		// Act
		TarjetaCredito tarjeta = new TarjetaCredito(identificadorEsperado, TipoTarjeta.MASTERCARD);
		int identificadorActual = tarjeta.getIdentificador();

		// Assert
		assertEquals(identificadorEsperado, identificadorActual);
	}

	@Test
	public void comprobarTipoDeTarjetaCorrecto() {
		// Arrange
		TipoTarjeta tipoTarjetaEsperado = TipoTarjeta.VISA;

		// Act
		TarjetaCredito tarjeta = new TarjetaCredito(54654651, tipoTarjetaEsperado);
		TipoTarjeta tipoTarjetaActual = tarjeta.getTipo();

		// Assert
		assertEquals(tipoTarjetaEsperado, tipoTarjetaActual);
	}

}
