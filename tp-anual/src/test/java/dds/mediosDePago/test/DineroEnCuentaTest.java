package dds.mediosDePago.test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import dds.mediosDePago.DineroEnCuenta;

class DineroEnCuentaTest {

	@Test
	public void comprobacionSaldoDeCuentaCorrecto() {
		// Arrange
		BigDecimal saldoEsperado = new BigDecimal(1500);

		// Act
		DineroEnCuenta cuenta = new DineroEnCuenta(6513165);
		cuenta.setSaldo(saldoEsperado);
		BigDecimal saldoActual = cuenta.getSaldo();

		// Assert
		assertEquals(saldoEsperado, saldoActual);
	}

	@Test
	public void comprobacionSaldoInicialDeCuentaCorrecto() {
		// Arrange
		BigDecimal saldoInicialEsperado = new BigDecimal(0);

		// Act
		DineroEnCuenta cuenta = new DineroEnCuenta(6513165);
		BigDecimal saldoInicialActual = cuenta.getSaldo();

		// Assert
		assertEquals(saldoInicialEsperado, saldoInicialActual);
	}

	@Test
	public void comprobacionIdentificadorDeCuentaCorrecto() {
		// Arrange
		int identificadorEsperado = 125200;

		// Act
		DineroEnCuenta cuenta = new DineroEnCuenta(identificadorEsperado);
		int identificadorActual = cuenta.getIdentificador();

		// Assert
		assertEquals(identificadorEsperado, identificadorActual);
	}
}
