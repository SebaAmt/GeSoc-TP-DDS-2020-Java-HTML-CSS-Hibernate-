package dds.presupuesto.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.CreadorMoneda;
import dds.egreso.CurrencyID;
import dds.egreso.Item;
import dds.egreso.Moneda;
import dds.egreso.Presupuesto;
import dds.egreso.Proveedor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PresupuestoTest {

	@Test
	public void comprobarValorTotalCorrecto() {

		// Arrange

		final Presupuesto presupuesto1;
		final Proveedor proveedor1;
		final Item item1;
		final Item item2;
		final DocumentoComercial documento1;
		final List<Item> items1 = new ArrayList<>();
		final CreadorMoneda creadorPesos;
		final Moneda peso;
		
		documento1 = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 0000001);
		creadorPesos = new CreadorMoneda(CurrencyID.ARS);
		peso = creadorPesos.getMoneda();
		proveedor1 = new Proveedor("Telas SA", 30258741, null);
		item1 = new Item("Rollo tela", new BigDecimal(300), 3);
		item2 = new Item("Lamina de cuero", new BigDecimal(300), 4);
		items1.add(item1);
		items1.add(item2);
		presupuesto1 = new Presupuesto(proveedor1, documento1, peso, items1);

		// Act

		BigDecimal totalEsperado = items1.stream().map(item -> item.valorTotal()).reduce(BigDecimal.ZERO,
				BigDecimal::add);
		BigDecimal totalActual = presupuesto1.valorTotal();

		// Assert

		assertEquals(totalEsperado, totalActual);
	}

}
