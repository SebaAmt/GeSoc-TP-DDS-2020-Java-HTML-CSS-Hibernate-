package dds.egreso.test;

import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.CreadorMoneda;
import dds.egreso.CurrencyID;
import dds.egreso.Egreso;
import dds.egreso.Item;
import dds.egreso.Moneda;
import dds.egreso.Proveedor;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EgresoTest {

	@Test
	public void comprobarValorTotalCorrecto() {
		// Arrange

		final Egreso egreso1;
		final Item item1;
		final Item item2;
		final Proveedor proveedor1;
		final DocumentoComercial documento2;
		final MedioDePago medioDePago1;
		final List<Item> items1 = new ArrayList<>();
		final CreadorMoneda creadorPesos;
		final Moneda peso;
		
		
		item1 = new Item("Rollo tela", new BigDecimal(300), 3);
		item2 = new Item("Lamina de cuero", new BigDecimal(300), 4);
		items1.add(item1);
		items1.add(item2);
		proveedor1 = new Proveedor("Telas SA", 30258741, null);
		creadorPesos = new CreadorMoneda(CurrencyID.ARS);
		peso = creadorPesos.getMoneda();
		documento2 = new DocumentoComercial(TipoDocumentoComercial.ORDEN_DE_COMPRA, 9999999);
		medioDePago1 = new MedioDePago(TipoMedioDePago.CAJERO, "159753456");
		egreso1 = new Egreso(LocalDate.of(2020, 5, 15), proveedor1, documento2, medioDePago1, peso, items1, null, null, false, null);

		// Act

		BigDecimal totalActual = egreso1.valorTotal();
		BigDecimal totalEsperado = item1.valorTotal().add(item2.valorTotal());

		// Assert

		assertEquals(totalEsperado, totalActual);
	}

	@Test
	public void comprobarItemAgregadoCorrectamente() {

		// Arrange

		final Egreso egreso1;
		final Item item1;
		final Item item2;
		final Item item4;
		final Proveedor proveedor1;
		final DocumentoComercial documento2;
		final MedioDePago medioDePago1;
		final List<Item> items1 = new ArrayList<>();
		final CreadorMoneda creadorPesos;
		final Moneda peso;
	
		item1 = new Item("Rollo tela", new BigDecimal(300), 3);
		item2 = new Item("Lamina de cuero", new BigDecimal(300), 4);
		item4 = new Item("Rollo aluminio", new BigDecimal(500), 1);
		items1.add(item1);
		items1.add(item2);
		proveedor1 = new Proveedor("Telas SA", 30258741, null);
		creadorPesos = new CreadorMoneda(CurrencyID.ARS);
		peso = creadorPesos.getMoneda();
		documento2 = new DocumentoComercial(TipoDocumentoComercial.ORDEN_DE_COMPRA, 9999999);
		medioDePago1 = new MedioDePago(TipoMedioDePago.CAJERO, "159753456");
		egreso1 = new Egreso(LocalDate.of(2020, 5, 15), proveedor1, documento2, medioDePago1, peso, items1, null, null, false,
				null);

		// Act

		egreso1.agregarItem(item4);
		int cantidadDeItemsEsperada = items1.size();
	    int cantidadDeItemsActual = egreso1.getItems().size();

		// Assert

		assertEquals(cantidadDeItemsEsperada, cantidadDeItemsActual);
	}
}
