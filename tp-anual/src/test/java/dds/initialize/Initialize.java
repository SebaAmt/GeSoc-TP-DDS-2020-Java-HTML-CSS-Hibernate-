package dds.initialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.CreadorMoneda;
import dds.egreso.CriterioPresupuestoMenorValor;
import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;
import dds.egreso.Item;
import dds.egreso.Moneda;
import dds.egreso.Presupuesto;
import dds.egreso.Proveedor;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;

public class Initialize {
	public Presupuesto presupuesto1;
	public Presupuesto presupuesto2;
	public Egreso egreso1;
	public Egreso egreso2;
	public Item item1;
	public Item item2;
	public Item item3;
	public Item item4;	
	public Proveedor proveedor1;
	public Proveedor proveedor2;	
	public DocumentoComercial documento1;
	public DocumentoComercial documento2;
	public MedioDePago medioDePago1;
	public MedioDePago medioDePago2;
	public CriterioPresupuestoMenorValor criterioMenorValor;
	public CreadorMoneda creadorPeso;
	public List<Item> items1 = new ArrayList<>();
	public List<Item> items2 = new ArrayList<>();
	public List<Presupuesto> presupuestos = new ArrayList<>();
	
	
	public void setDePrueba() {
		item1 = new Item("Rollo tela", new BigDecimal(300), 3);
		item2 = new Item("Lamina de cuero", new BigDecimal(300), 4);
		item3 = new Item("Botella de tintura", new BigDecimal(50), 1);
		item4 = new Item("Jabon en polvo", new BigDecimal(500), 1);
		
		items1.add(item1);
		items1.add(item2);
		items2.add(item3);
		
		proveedor1 = creadorProveedor.crearProveedor("Telas SA", 30258741, "TUxBUENBUGw3M2E1", "TUxBQ0NBUGZlZG1sYQ", "TUxBQkJFTDcyNTJa", "Av. Cabildo", 2000, 9, "A", "1379");
		proveedor2 = creadorProveedor.crearProveedor("Edenor", 40987654, "TUxBUENBUGw3M2E1", "TUxBQ0NBUGZlZG1sYQ", "TUxBQkNBQjM4MDda", "Av Rivadavia", 4400, null, null,"8520");
		
		documento1 = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 0000001);
		documento2 = new DocumentoComercial(TipoDocumentoComercial.ORDEN_DE_COMPRA, 9999999);
	
		medioDePago1 = new MedioDePago(TipoMedioDePago.CAJERO, "159753456");
		medioDePago2 = new MedioDePago(TipoMedioDePago.DINERO_EN_CUENTA, "456852159");
		
		
		egreso1 = new Egreso(LocalDate.of(2020,5,15), proveedor1, documento2, medioDePago1, items1, null, null, false, null);
		egreso2 = new Egreso(LocalDate.of(2020, 5, 15), proveedor2, documento1, medioDePago2, items2, null, null, false, null);
		
		presupuesto1 = new Presupuesto(proveedor1, documento1, items1);
		presupuesto2 = new Presupuesto(proveedor2, documento1, items2);
		presupuestos.add(presupuesto1);
		presupuestos.add(presupuesto2);
		
		criterioMenorValor = new CriterioPresupuestoMenorValor();
	}
}