package dds.entidades.test;

import dds.*;
import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.CreadorProveedor;
import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;
import dds.egreso.Item;
import dds.egreso.Proveedor;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntidadesTest {
    private EntidadBase entidadBase;
    private EntidadJuridica entidadJuridica;
    private Egreso egreso1;
    private Egreso egreso2;
    private CreadorProveedor creadorProveedor;

    @BeforeEach
    public void init(){
    	creadorProveedor = new CreadorProveedor();
        entidadJuridica = new EntidadJuridica("Razon Social Test", "Entidad Juridica Test", "11111111111", "Direccion 888", "Codigo 123");
        entidadBase = new EntidadBase("Entidad Base Test", "Entidad base para probar");
        Proveedor proveedor = creadorProveedor.crearProveedor("Telas SA", 30258741, "TUxBUENBUGw3M2E1", "TUxBQ0NBUGZlZG1sYQ", "TUxBQkJFTDcyNTJa", "Av. Cabildo", 2000, 9, "A", "1379");
        DocumentoComercial factura = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1234);
        MedioDePago efectivo = new MedioDePago(TipoMedioDePago.EFECTIVO, "PF12345");
        List<Item> items1 = new ArrayList<Item>();
        items1.add(new Item("Martillo", new BigDecimal(30), 1));
        items1.add(new Item("Clavos", new BigDecimal(5), 10));
        items1.add(new Item("Madera", new BigDecimal(100), 5));
        List<Item> items2 = new ArrayList<Item>();
        items2.add(new Item("Pegamento", new BigDecimal(50), 2));
        egreso1 = new Egreso(LocalDate.now(), proveedor, factura, efectivo, items1, null, null, false, EstadoEgreso.ACEPTADO, null);
        egreso2 = new Egreso(LocalDate.now(), proveedor, factura, efectivo, items2, null, null, false, EstadoEgreso.ACEPTADO, null);
    }

    @Test
    public void CalcularEgresosEntidadJuridica(){
        entidadJuridica.nuevoEgreso(egreso1);
        entidadJuridica.nuevoEgreso(egreso2);
        assertEquals(egreso1.valorTotal().add(egreso2.valorTotal()), entidadJuridica.totalEgresos());
    }

    @Test public void CalcularEgresosEntidadBase(){
        entidadBase.nuevoEgreso(egreso1);
        entidadBase.nuevoEgreso(egreso2);
        assertEquals(egreso1.valorTotal().add(egreso2.valorTotal()), entidadBase.totalEgresos());
    }

}
