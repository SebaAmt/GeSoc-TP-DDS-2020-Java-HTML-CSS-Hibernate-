package dds.egreso.test;

import dds.initialize.Initialize;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;


public class EgresoTest {

	Initialize prueba = new Initialize();
	

	@BeforeEach
	public void setDePrueba() {
		prueba.setDePrueba();
	}
	
	
	@Test
	public void valorTotalTest() {
		BigDecimal total = prueba.item1.getValor().add(prueba.item2.getValor());
		assertEquals(prueba.egreso1.valorTotal(), total);
	}
	
	@Test
	public void descripcionTest() {
		String detalleItem = prueba.item3.getDescripcion() +" "+ prueba.item3.getValor();
		assertEquals(prueba.egreso2.detalle().get(0), detalleItem);
	}
	
	@Test
	public void agregarItemTest() {
		prueba.egreso1.agregarItem(prueba.item4);
		assertEquals(prueba.egreso1.getItems().size(), prueba.items1.size());
	}
}
