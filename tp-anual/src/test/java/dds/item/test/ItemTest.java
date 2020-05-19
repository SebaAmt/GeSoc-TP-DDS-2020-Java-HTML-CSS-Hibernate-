package dds.item.test;

import dds.initialize.Initialize;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ItemTest {

	Initialize prueba = new Initialize();
	

	@BeforeEach
	public void setDePrueba() {
		prueba.setDePrueba();
	}
	
	@Test
	public void valorTotalTest( ) {
		BigDecimal precio = prueba.item1.getPrecioUnitario().multiply(BigDecimal.valueOf(prueba.item1.getCantidadUnidades()));
		assertEquals(prueba.item1.valorTotal(), precio);
	}
}
