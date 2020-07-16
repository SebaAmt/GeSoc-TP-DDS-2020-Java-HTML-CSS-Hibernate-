package dds.item.test;

import dds.egreso.Item;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

	@Test
	public void comprobarValorTotalCorrecto() {

		// Arrange

		Item item1;
		item1 = new Item("Rollo tela", new BigDecimal(300), 3);

		// Act

		BigDecimal precioActual = item1.getPrecioUnitario().multiply(BigDecimal.valueOf(item1.getCantidadUnidades()));
        BigDecimal precioEsperado = item1.valorTotal();
        
		// Assert

		assertEquals(precioEsperado, precioActual);
	}
}
