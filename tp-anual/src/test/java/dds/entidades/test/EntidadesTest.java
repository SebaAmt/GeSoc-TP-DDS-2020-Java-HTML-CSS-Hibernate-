package dds.entidades.test;

import dds.categoria.Categoria;
import dds.categoria.TipoCategoria;
import dds.comportamiento.Comportamiento;
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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EntidadesTest {
    private EntidadBase entidadBase;
    private EntidadJuridica entidadJuridica;
    private Egreso egreso1;
    private Egreso egreso2;
    private CreadorMoneda creadorPesos;
    private Proveedor proveedor;
    private Moneda moneda;
    
    
    @BeforeEach
    public void init(){
    	creadorPesos = new CreadorMoneda(CurrencyID.ARS);
        entidadJuridica = new EntidadJuridica("Razon Social Test", "Entidad Juridica Test", "11111111111", "Direccion 888", "Codigo 123");
        entidadBase = new EntidadBase("Entidad Base Test", "Entidad base para probar");
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
    
    @Test public void GenerarReporteMensualEntidadJuridica() {
    	egreso1.etiquetar("amoblamiento");
    	egreso1.etiquetar("textil");
    	egreso2.etiquetar("amoblamiento");
    	entidadJuridica.nuevoEgreso(egreso1);
    	entidadJuridica.agregarEntidadBase(entidadBase);
    	entidadBase.nuevoEgreso(egreso2);
    	HashMap<String, BigDecimal> reporteEsperado = new HashMap<>();
    	HashMap<String, BigDecimal> reporteActual = entidadJuridica.generarReporteMensual();
    	List<Egreso> egresosDeAmoblamiento; 
    	List<Egreso> egresosTotales = new ArrayList<>();
    	List<Egreso> egresosDeTextil;
    	egresosTotales.addAll(entidadBase.egresos());
    	egresosTotales.addAll(entidadJuridica.egresos());
    	egresosDeAmoblamiento = egresosTotales.stream().filter(egreso -> egreso.estaEtiquetadoComo("amoblamiento")).collect(Collectors.toList());
        egresosDeTextil = egresosTotales.stream().filter(egreso -> egreso.estaEtiquetadoComo("textil")).collect(Collectors.toList());
        BigDecimal gastosDeAmoblamiento = egresosDeAmoblamiento.stream().map(egreso -> egreso.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal gastosDeTextil = egresosDeTextil.stream().map(egreso -> egreso.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
    	reporteEsperado.put("AMOBLAMIENTO", gastosDeAmoblamiento);
    	reporteEsperado.put("TEXTIL", gastosDeTextil);
    	assertEquals(reporteEsperado, reporteActual);
    }
    
    @Test public void GenerarReporteMensualEntidadBase() {
    	egreso1.etiquetar("amoblamiento");
    	egreso1.etiquetar("textil");
    	egreso2.etiquetar("amoblamiento");
       	entidadBase.nuevoEgreso(egreso2);
    	entidadBase.nuevoEgreso(egreso1);
    	HashMap<String, BigDecimal> reporteEsperado = new HashMap<>();
    	HashMap<String, BigDecimal> reporteActual = entidadBase.generarReporteMensual();
    	List<Egreso> egresosDeAmoblamiento; 
    	List<Egreso> egresosTotales = new ArrayList<>();
    	List<Egreso> egresosDeTextil;
    	egresosTotales.addAll(entidadBase.egresos());
    	egresosDeAmoblamiento = egresosTotales.stream().filter(egreso -> egreso.estaEtiquetadoComo("amoblamiento")).collect(Collectors.toList());
        egresosDeTextil = egresosTotales.stream().filter(egreso -> egreso.estaEtiquetadoComo("textil")).collect(Collectors.toList());
        BigDecimal gastosDeAmoblamiento = egresosDeAmoblamiento.stream().map(egreso -> egreso.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal gastosDeTextil = egresosDeTextil.stream().map(egreso -> egreso.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
    	reporteEsperado.put("AMOBLAMIENTO", gastosDeAmoblamiento);
    	reporteEsperado.put("TEXTIL", gastosDeTextil);
    	assertEquals(reporteEsperado, reporteActual);
    }
    

}
