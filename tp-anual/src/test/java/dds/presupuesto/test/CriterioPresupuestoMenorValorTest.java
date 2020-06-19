package dds.presupuesto.test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dds.egreso.Presupuesto;
import dds.initialize.Initialize;

class CriterioPresupuestoMenorValorTest {

	Initialize prueba = new Initialize();

	@BeforeEach
	public void setDePrueba() {
		prueba.setDePrueba();
	}

	@Test
	public void comprobarPresupuestoSeleccionadoCorrecto() {
		Presupuesto presupuestoEsperado = prueba.presupuesto2;
				
		Presupuesto presupuestoActual = prueba.criterioMenorValor.seleccionarPresupuesto(prueba.presupuestos);
		assertEquals(presupuestoEsperado, presupuestoActual);
	}
}
