package dds.entidades.test;

import dds.*;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;
import dds.mediosDePago.Efectivo;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoEfectivo;
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

    @BeforeEach
    public void init(){
        entidadJuridica = new EntidadJuridica("Razon Social Test", "Entidad Juridica Test", "11111111111", "Direccion 888", "Codigo 123");
        entidadBase = new EntidadBase("Entidad Base Test", "Entidad base para probar");
        Proveedor proveedor = new Proveedor("Proveedor", 11111111, "Direccion 123");
        DocumentoComercial factura = new DocumentoComercial(TipoDocumentoComercial.Factura, 1234);
        MedioDePago efectivo = new Efectivo(12345, TipoEfectivo.PAGOFACIL);
        List<Item> items1 = new ArrayList<Item>();
        items1.add(new Item("Martillo", new BigDecimal(30)));
        items1.add(new Item("Clavos", new BigDecimal(5)));
        items1.add(new Item("Madera", new BigDecimal(100)));
        List<Item> items2 = new ArrayList<Item>();
        items2.add(new Item("Pegamento", new BigDecimal(50)));
        egreso1 = new Egreso(LocalDate.now(), proveedor, factura, efectivo, items1);
        egreso2 = new Egreso(LocalDate.now(), proveedor, factura, efectivo, items2);
}

    @Test
    public void CalcularEgresosEntidadJuridica(){
        entidadJuridica.nuevoEgreso(egreso1);
        assertEquals(egreso1.valorTotal(), entidadJuridica.totalEgresos());
        entidadJuridica.nuevoEgreso(egreso2);
        assertEquals(egreso1.valorTotal().add(egreso2.valorTotal()), entidadJuridica.totalEgresos());
    }

    @Test public void CalcularEgresosEntidadBase(){
        entidadBase.nuevoEgreso(egreso1);
        assertEquals(egreso1.valorTotal(), entidadBase.totalEgresos());
        entidadBase.nuevoEgreso(egreso2);
        assertEquals(egreso1.valorTotal().add(egreso2.valorTotal()), entidadBase.totalEgresos());
    }

}
