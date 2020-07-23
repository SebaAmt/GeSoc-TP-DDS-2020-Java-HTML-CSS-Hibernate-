package dds.entidades.test;

import dds.*;
import dds.categoria.Categoria;
import dds.categoria.TipoCategoria;
import dds.comportamiento.Comportamiento;
import dds.comportamiento.ComportamientoDefault;
import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.CreadorMoneda;
import dds.egreso.CurrencyID;
import dds.egreso.Egreso;
import dds.egreso.Item;
import dds.egreso.Moneda;
import dds.egreso.Proveedor;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;

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
    private CreadorMoneda creadorPesos;
    private Proveedor proveedor;
    private Moneda moneda;
    private Categoria categoria;
    private Comportamiento comportamiento;
    private List<Comportamiento> comportamientos = new ArrayList<>();
    
    @BeforeEach
    public void init(){
    	comportamiento = new ComportamientoDefault();
        comportamientos.add(comportamiento);
        categoria = new Categoria(TipoCategoria.EMPRESA_MICRO, comportamientos);
    	creadorPesos = new CreadorMoneda(CurrencyID.ARS);
        entidadJuridica = new EntidadJuridica("Razon Social Test", "Entidad Juridica Test", "11111111111", "Direccion 888", "Codigo 123");
        entidadJuridica.setCategoria(categoria);
        entidadBase = new EntidadBase("Entidad Base Test", "Entidad base para probar");
        entidadBase.setCategoria(categoria);
        proveedor = new Proveedor("Telas SA", 30258741, null);
        creadorPesos = new CreadorMoneda(CurrencyID.ARS);
        moneda = creadorPesos.getMoneda();
        DocumentoComercial factura = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1234);
        MedioDePago efectivo = new MedioDePago(TipoMedioDePago.EFECTIVO, "PF12345");
        List<Item> items1 = new ArrayList<Item>();
        items1.add(new Item("Martillo", new BigDecimal(30), 1));
        items1.add(new Item("Clavos", new BigDecimal(5), 10));
        items1.add(new Item("Madera", new BigDecimal(100), 5));
        List<Item> items2 = new ArrayList<Item>();
        items2.add(new Item("Pegamento", new BigDecimal(50), 2));
        egreso1 = new Egreso(LocalDate.now(), proveedor, factura, efectivo, moneda, items1, null, null, false, null);
        egreso2 = new Egreso(LocalDate.now(), proveedor, factura, efectivo, moneda, items2,null, null, false, null);
    }

    @Test
    public void CalcularEgresosEntidadJuridica(){
        entidadJuridica.nuevoEgreso(egreso1);
        entidadJuridica.nuevoEgreso(egreso2);
        assertEquals(egreso1.valorTotal().add(egreso2.valorTotal()), entidadJuridica.totalEgresos());
    }

    @Test 
    public void CalcularEgresosEntidadBase(){
        entidadBase.nuevoEgreso(egreso1);
        entidadBase.nuevoEgreso(egreso2);
        assertEquals(egreso1.valorTotal().add(egreso2.valorTotal()), entidadBase.totalEgresos());
    }

}
