package dds.categoria.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dds.categoria.Categoria;
import dds.categoria.TipoCategoria;
import dds.comportamiento.Comportamiento;
import dds.comportamiento.ComportamientoBloquearAgregarEntidadBase;
import dds.comportamiento.ComportamientoBloquearEntidadBase;
import dds.comportamiento.ComportamientoMontoMaximo;
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
import dds.exception.ComportamientoException;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;

public class CategoriaTest {

	private EntidadBase entidadBase;
    private EntidadJuridica entidadJuridica;
    private Egreso egreso1;
    private Egreso egreso2;
    private CreadorMoneda creadorPesos;
    private Proveedor proveedor;
    private Moneda moneda;
    private Categoria categoria1;
    private Categoria categoria2;
    private Comportamiento comportamiento1;
    private Comportamiento comportamiento2;
    private List<Comportamiento> comportamientos1 = new ArrayList<>();
    private List<Comportamiento> comportamientos2 = new ArrayList<>();
    
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
        entidadJuridica.nuevoEgreso(egreso1);
    }
    
    
    @Test
    public void superaMontoMaximoTest() {
    	comportamiento1 = new ComportamientoMontoMaximo(new BigDecimal(140));
    	comportamientos1.add(comportamiento1);
    	categoria1 = new Categoria(TipoCategoria.EMPRESA_PEQUENIA, comportamientos1);
    	entidadJuridica.setCategoria(categoria1);
    	Exception exception = assertThrows(ComportamientoException.class, () -> entidadJuridica.nuevoEgreso(egreso2));
    	assertThat(exception.getMessage(), is("La entidad ya ha superado el monto maximo de egresos a realizar."));
    }
    
    @Test
    public void bloquearAgregarEntidadesBase() {
    	comportamiento1 = new ComportamientoBloquearAgregarEntidadBase();
    	comportamiento2 = new ComportamientoMontoMaximo(new BigDecimal(40));
    	comportamientos1.add(comportamiento1);
    	comportamientos2.add(comportamiento2);
    	categoria1 = new Categoria(TipoCategoria.EMPRESA_MEDIANA_TRAMO1, comportamientos1);
    	categoria2 = new Categoria(TipoCategoria.EMPRESA_MEDIANA_TRAMO2, comportamientos2);
    	entidadJuridica.setCategoria(categoria1);
    	entidadBase.setCategoria(categoria2);
    	Exception exception = assertThrows(ComportamientoException.class, () -> entidadJuridica.agregarEntidadBase(entidadBase));
    	assertThat(exception.getMessage(), is("No se pueden agregar entidades base"));
    }
    
    @Test
    public void entidadBaseNoSePuedeAgregar() {
    	comportamiento1 = new ComportamientoBloquearEntidadBase();
    	comportamiento2 = new ComportamientoMontoMaximo(new BigDecimal(40));
    	comportamientos1.add(comportamiento1);
    	comportamientos2.add(comportamiento2);
    	categoria1 = new Categoria(TipoCategoria.ONG, comportamientos1);
    	categoria2 = new Categoria(TipoCategoria.EMPRESA_MEDIANA_TRAMO2, comportamientos2);
    	entidadBase.setCategoria(categoria1);
    	entidadJuridica.setCategoria(categoria2);    	
    	Exception exception = assertThrows(ComportamientoException.class, () -> entidadJuridica.agregarEntidadBase(entidadBase));
    	assertThat(exception.getMessage(), is("Esta entidad base no puede ser agregada"));
    }
}
