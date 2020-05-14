package dds.mediosDePago.test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import dds.mediosDePago.Cajero;

class CajeroTest {

	@Test
	public void comprobarIdentificadorDeCajeroCorrecto() {
		// Arrange
		int identificadorEsperado = 1050;

		// Act
		Cajero cajero = new Cajero(identificadorEsperado);
		int identificadorActual = cajero.getIdentificador();

		// Assert
		assertEquals(identificadorEsperado, identificadorActual);
	}

	@Test
	public void comprobarSaldoDeCajeroCorrecto() {
		// Arrange
		BigDecimal saldoEsperado = new BigDecimal(1500.50);

		// Act
		Cajero cajero = new Cajero(6513165);
		cajero.setSaldo(saldoEsperado);
		BigDecimal saldoActual = cajero.getSaldo();

		// Assert
		assertEquals(saldoEsperado, saldoActual);
	}

	@Test
	public void comprobarSaldoInicialDeCajeroCorrecto() {
		// Arrange
		BigDecimal saldoInicialEsperado = new BigDecimal(0);

		// Act
		Cajero cajero = new Cajero(42515);
		BigDecimal saldoInicialActual = cajero.getSaldo();

		// Assert
		assertEquals(saldoInicialEsperado, saldoInicialActual);
	}

}
