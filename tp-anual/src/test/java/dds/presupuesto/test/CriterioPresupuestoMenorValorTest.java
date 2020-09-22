package dds.presupuesto.test;

import static org.junit.jupiter.api.Assertions.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.CreadorMoneda;
import dds.egreso.CriterioSeleccionPresupuesto;
import dds.egreso.CurrencyID;
import dds.egreso.Item;
import dds.egreso.Moneda;
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
		final CreadorMoneda creadorPesos;
		final Moneda peso;

		CriterioSeleccionPresupuesto criterioMenorValor = CriterioSeleccionPresupuesto.MENOR_VALOR;
		documento1 = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 0000001);
		creadorPesos = new CreadorMoneda(CurrencyID.ARS);
		peso = creadorPesos.getMoneda();
		proveedor1 = new Proveedor("Telas SA", 30258741, null);
		proveedor2 = new Proveedor("Edenor", 40987654, null);
		item1 = new Item("Rollo tela", new BigDecimal(300), 3);
		item2 = new Item("Lamina de cuero", new BigDecimal(300), 4);
		item3 = new Item("Botella de tintura", new BigDecimal(50), 1);
		items1.add(item1);
		items1.add(item2);
		items2.add(item3);
		presupuesto1 = new Presupuesto(proveedor1, documento1, peso, items1);
		presupuesto2 = new Presupuesto(proveedor2, documento1, peso, items2);
		presupuestos.add(presupuesto1);
		presupuestos.add(presupuesto2);

		// Act

		Presupuesto presupuestoEsperado = presupuesto2;
		Presupuesto presupuestoActual = criterioMenorValor.seleccionarPresupuesto(presupuestos);

		// Assert

		assertEquals(presupuestoEsperado, presupuestoActual);
	}
}
