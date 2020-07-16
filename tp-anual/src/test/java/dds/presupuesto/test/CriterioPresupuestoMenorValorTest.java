package dds.presupuesto.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.CreadorProveedor;
import dds.egreso.CriterioPresupuestoMenorValor;
import dds.egreso.Item;
import dds.egreso.Presupuesto;
import dds.egreso.Proveedor;

class CriterioPresupuestoMenorValorTest {

	@Test
	public void comprobarPresupuestoSeleccionadoCorrecto() {

		// Arrange

		final Presupuesto presupuesto1;
		final Presupuesto presupuesto2;
		final Proveedor proveedor1;
		final Proveedor proveedor2;
		final Item item1;
		final Item item2;
		final Item item3;
		final DocumentoComercial documento1;
		final List<Item> items1 = new ArrayList<>();
		final List<Item> items2 = new ArrayList<>();
		final List<Presupuesto> presupuestos = new ArrayList<>();
		final CriterioPresupuestoMenorValor criterioMenorValor;
		final CreadorProveedor creadorProveedor;

		criterioMenorValor = new CriterioPresupuestoMenorValor();
		documento1 = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 0000001);
		creadorProveedor = new CreadorProveedor();
		proveedor1 = creadorProveedor.crearProveedor("Telas SA", 30258741, "TUxBUENBUGw3M2E1", "TUxBQ0NBUGZlZG1sYQ",
				"TUxBQkJFTDcyNTJa", "Av. Cabildo", 2000, 9, "A", "1379");
		proveedor2 = creadorProveedor.crearProveedor("Edenor", 40987654, "TUxBUENBUGw3M2E1", "TUxBQ0NBUGZlZG1sYQ",
				"TUxBQkNBQjM4MDda", "Av Rivadavia", 4400, null, null, "8520");
		item1 = new Item("Rollo tela", new BigDecimal(300), 3);
		item2 = new Item("Lamina de cuero", new BigDecimal(300), 4);
		item3 = new Item("Botella de tintura", new BigDecimal(50), 1);
		items1.add(item1);
		items1.add(item2);
		items2.add(item3);
		presupuesto1 = new Presupuesto(proveedor1, documento1, items1);
		presupuesto2 = new Presupuesto(proveedor2, documento1, items2);
		presupuestos.add(presupuesto1);
		presupuestos.add(presupuesto2);

		// Act

		Presupuesto presupuestoEsperado = presupuesto2;
		Presupuesto presupuestoActual = criterioMenorValor.seleccionarPresupuesto(presupuestos);

		// Assert

		assertEquals(presupuestoEsperado, presupuestoActual);
	}
}
