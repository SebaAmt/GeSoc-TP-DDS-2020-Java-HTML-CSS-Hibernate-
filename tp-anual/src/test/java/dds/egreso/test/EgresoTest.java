package dds.egreso.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.print.Printable;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import dds.initialize.Initialize;

public class EgresoTest {

	Initialize prueba = new Initialize();
	
	
	public void setDePrueba() {
		prueba.setDePrueba();
	}
	
	@Test
	public void valorTotalTest() {
		prueba.setDePrueba();
		
	}
}
