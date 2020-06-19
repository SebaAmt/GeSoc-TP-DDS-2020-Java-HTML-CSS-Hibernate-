package dds.presupuesto.test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import dds.initialize.Initialize;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PresupuestoTest {
	

	Initialize prueba = new Initialize();

	@BeforeEach
	public void setDePrueba() {
		prueba.setDePrueba();
	}

	@Test
	public void valorTotalTest() {
		BigDecimal totalEsperado = prueba.items1.stream().map(item->item.valorTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal totalActual = prueba.presupuesto1.valorTotal();
		assertEquals(totalEsperado, totalActual);
	}

}
