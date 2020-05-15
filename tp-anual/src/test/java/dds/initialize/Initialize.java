package dds.initialize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import dds.DocumentoComercial;
import dds.Egreso;
import dds.Item;
import dds.Proveedor;
import dds.TipoDocumentoComercial;
import dds.mediosDePago.Cajero;
import dds.mediosDePago.DineroEnCuenta;

public class Initialize {
	
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
	public Cajero medioDePago1;
	public DineroEnCuenta medioDePago2;
	public List<Item> items1;
	public List<Item> items2;
	
	
	public void setDePrueba() {
		item1 = new Item("Tela", new BigDecimal(150));
		item2 = new Item("Cuero", new BigDecimal(200));
		item3 = new Item("Luz", new BigDecimal(1000));
		item4 = new Item("Agua", new BigDecimal(700));
		
		items1.add(item1);
		items1.add(item2);
		items2.add(item3);
		
		proveedor1 = new Proveedor("Telas SA", 30258741, "4568");
		proveedor2 = new Proveedor("Edenor", 40987654, "8520");
		
		documento1 = new DocumentoComercial(TipoDocumentoComercial.Ticket, 0000001);
		documento2 = new DocumentoComercial(TipoDocumentoComercial.Factura, 9999999);
	
		medioDePago1 = new Cajero(159753456);
		medioDePago1.setSaldo(new BigDecimal(3000));
		medioDePago2 = new DineroEnCuenta(456852159);
		medioDePago2.setSaldo(new BigDecimal(100));
		
		egreso1 = new Egreso(LocalDate.of(2020,5,15), proveedor1, documento2, medioDePago1, items1);
		egreso2 = new Egreso(LocalDate.of(2020, 5, 15), proveedor2, documento1, medioDePago2, items2);
	}
}