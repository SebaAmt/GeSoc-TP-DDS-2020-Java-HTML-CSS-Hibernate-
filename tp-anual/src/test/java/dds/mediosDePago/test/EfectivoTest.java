package dds.mediosDePago.test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dds.mediosDePago.Efectivo;
import dds.mediosDePago.TipoEfectivo;

class EfectivoTest {

	@Test
	public void comprobarIdentificadorDeEfectivoCorrectot() {
		// Arrange
		int identificadorEsperado = 10020;

		// Act
		Efectivo efectivo = new Efectivo(identificadorEsperado, TipoEfectivo.PAGOFACIL);
		int identificadorActual = efectivo.getIdentificador();

		// Assert
		assertEquals(identificadorEsperado, identificadorActual);
	}

	@Test
	public void comprobarTipoDeEfectivoCorrecto() {
		// Arrange
		TipoEfectivo tipoEsperado = TipoEfectivo.PAGOFACIL;

		// Act
		Efectivo efectivo = new Efectivo(1241245, tipoEsperado);
		TipoEfectivo tipoActual = efectivo.getTipo();

		// Assert
		assertEquals(tipoEsperado, tipoActual);
	}
}
