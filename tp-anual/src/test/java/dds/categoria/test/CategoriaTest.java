package dds.categoria.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.reglaNegocio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dds.categoria.Categoria;
import dds.categoria.TipoCategoria;
import dds.reglaNegocio.ReglaNegocio;
import dds.reglaNegocio.ReglaNegocioBloquearAgregarEntidadBase;
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
import dds.exception.ReglaNegocioException;
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
    private ReglaNegocio reglaNegocio1;
    private ReglaNegocio reglaNegocio2;
    private List<ReglaNegocio> reglasNegocio1 = new ArrayList<>();
    private List<ReglaNegocio> reglasNegocio2 = new ArrayList<>();
    
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
    	reglaNegocio1 = new ReglaNegocioMontoMaximo(new BigDecimal(140));
    	reglasNegocio1.add(reglaNegocio1);
    	categoria1 = new Categoria(TipoCategoria.EMPRESA_PEQUENIA, reglasNegocio1);
    	entidadJuridica.setCategoria(categoria1);    	
    	Exception exception = assertThrows(ReglaNegocioException.class, () -> entidadJuridica.nuevoEgreso(egreso2));
    	assertThat(exception.getMessage(), is("La entidad ya ha superado el monto maximo de egresos a realizar"));
    }
    
    @Test
    public void bloquearAgregarEntidadesBase() {
    	reglaNegocio1 = new ReglaNegocioBloquearAgregarEntidadBase();
    	reglaNegocio2 = new ReglaNegocioMontoMaximo(new BigDecimal(40));
    	reglasNegocio1.add(reglaNegocio1);
    	reglasNegocio2.add(reglaNegocio2);
    	categoria1 = new Categoria(TipoCategoria.EMPRESA_MEDIANA_TRAMO1, reglasNegocio1);
    	categoria2 = new Categoria(TipoCategoria.EMPRESA_MEDIANA_TRAMO2, reglasNegocio2);
    	entidadJuridica.setCategoria(categoria1);
    	entidadBase.setCategoria(categoria2);
    	Exception exception = assertThrows(ReglaNegocioException.class, () -> entidadJuridica.agregarEntidadBase(entidadBase));
    	assertThat(exception.getMessage(), is("No se pueden agregar entidades base"));
    }
    
    @Test
    public void entidadBaseNoSePuedeAgregar() {
    	reglaNegocio1 = new ReglaNegocioBloquearEntidadBase();
    	reglaNegocio2 = new ReglaNegocioMontoMaximo(new BigDecimal(40));
    	reglasNegocio1.add(reglaNegocio1);
    	reglasNegocio2.add(reglaNegocio2);
    	categoria1 = new Categoria(TipoCategoria.ONG, reglasNegocio1);
    	categoria2 = new Categoria(TipoCategoria.EMPRESA_MEDIANA_TRAMO2, reglasNegocio2);
    	entidadBase.setCategoria(categoria1);
    	entidadJuridica.setCategoria(categoria2);    	
    	Exception exception = assertThrows(ReglaNegocioException.class, () -> entidadJuridica.agregarEntidadBase(entidadBase));
    	assertThat(exception.getMessage(), is("Esta entidad base no puede ser agregada"));
    }
}
