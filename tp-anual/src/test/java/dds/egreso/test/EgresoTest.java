package dds.egreso.test;

import dds.initialize.Initialize;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class EgresoTest {

	Initialize prueba = new Initialize();
	

	@BeforeEach
	public void setDePrueba() {
		prueba.setDePrueba();
	}
	
	
	@Test
	public void valorTotalTest() {
		BigDecimal total = prueba.item1.valorTotal().add(prueba.item2.valorTotal());
		assertEquals(prueba.egreso1.valorTotal(), total);
	}
	
	@Test
	public void agregarItemTest() {
		prueba.egreso1.agregarItem(prueba.item4);
		assertEquals(prueba.egreso1.getItems().size(), prueba.items1.size());
	}
}
