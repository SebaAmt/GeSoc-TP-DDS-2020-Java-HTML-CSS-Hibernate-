package controllers;

import model.categoria.Categoria;
import model.direccion.Direccion;
import model.direccion.DireccionPostal;
import model.egreso.Moneda;
import model.egreso.Proveedor;
import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.entidades.Organizacion;
import model.meLiApi.MeLiApi;
import model.mediosDePago.MedioDePago;
import model.mediosDePago.TipoMedioDePago;
import model.reglaNegocio.ReglaNegocio;
import model.reglaNegocio.ReglaNegocioBloquearAgregarEntidadBase;
import model.reglaNegocio.ReglaNegocioMontoMaximo;
import model.usuario.CreadorDeUsuario;
import model.usuario.TipoUsuario;
import model.usuario.Usuario;
import model.validacionesContrasenias.ValidadorDeContrasenias;
import model.validacionesEgresos.ValidacionEgreso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

/**
 * Ejecutar antes de levantar el servidor por primera vez
 *
 * @author flbulgarelli
 */

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

    public static void main(String[] args) {
        new Bootstrap().run();
    }

    public void run() {
        withTransaction(() -> {
            Usuario usuarioPrueba = new Usuario("user1", "7815696ecbf1c96e6894b779456d330e", TipoUsuario.ESTANDAR); //password: asd
            Organizacion valve = new Organizacion("Valve");
            EntidadJuridica blackMesa = new EntidadJuridica("Black Mesa SRL", "Black Mesa", "123456789", "Direccion 123", "123");
            EntidadBase aperture = new EntidadBase("Aperture Laboratories", "Hacemos portales");
            List<String> mensajesDePrueba = new ArrayList<>();
            valve.agregarEntidadBase(aperture);
            valve.agregarEntidadJuridica(blackMesa);
            mensajesDePrueba.add("El Egreso 1 no fue aprobado");
            mensajesDePrueba.add("El Egreso 3 fue aprobado con éxito");
            usuarioPrueba.asignarOrganizacion(valve);
            usuarioPrueba.setBandejaDeMensajes(mensajesDePrueba);

            valve.nuevaEtiqueta("Etiqueta Valve 1");
            valve.nuevaEtiqueta("Etiqueta Valve 2");
            valve.nuevaEtiqueta("Etiqueta Valve 3");

            valve.agregarValidacionEgreso(ValidacionEgreso.COINCIDE_CON_CRITERIO);
            valve.agregarValidacionEgreso(ValidacionEgreso.COINCIDE_CON_PRESUPUESTO_CARGADO);

            
            List<ReglaNegocio> reglas = new ArrayList<>();
            ReglaNegocio bloquearAgregarEntidadBase = new ReglaNegocioBloquearAgregarEntidadBase();
            ReglaNegocio montoMaximo = new ReglaNegocioMontoMaximo(new BigDecimal(40));
            
            persist(bloquearAgregarEntidadBase);
            persist(montoMaximo);
            
        	reglas.add(bloquearAgregarEntidadBase);
        	reglas.add(montoMaximo);
        	Categoria categoria = new Categoria("Empresa Mediana Tramo1", reglas); 
        	Categoria categoria2 = new Categoria("Empresa Pequeña Tramo1", reglas);
            Categoria categoria3 = new Categoria("Empresa Grande Tramo1", reglas);
        	
            persist(categoria);
        	persist(categoria2);
        	persist(categoria3);
        	valve.agregarCategoria(categoria);
            valve.agregarCategoria(categoria2);
            blackMesa.setCategoria(categoria3);
        	
            persist(blackMesa);
            persist(aperture);
            persist(valve);
            persist(usuarioPrueba);

            Usuario usuarioPrueba2 = new Usuario("user2", "7815696ecbf1c96e6894b779456d330e", TipoUsuario.ESTANDAR); //password: asd
            Organizacion lucasArts = new Organizacion("Lucas Arts");
            EntidadJuridica monkeyIsland = new EntidadJuridica("Monkey Island SRL", "Monkey Island", "784521365", "Melee 123", "887");
            EntidadBase maniacMansion = new EntidadBase("Maniac Mansion SA", "Una descripción loca");
            lucasArts.agregarEntidadBase(maniacMansion);
            lucasArts.agregarEntidadJuridica(monkeyIsland);
            usuarioPrueba2.asignarOrganizacion(lucasArts);

            lucasArts.nuevaEtiqueta("Etiqueta LucasArts 101");
            lucasArts.nuevaEtiqueta("Etiqueta LucasArts 102");
            lucasArts.nuevaEtiqueta("Etiqueta LucasArts 103");

            lucasArts.agregarValidacionEgreso(ValidacionEgreso.CANTIDAD_MINIMA);

            persist(monkeyIsland);
            persist(maniacMansion);
            persist(lucasArts);
            persist(usuarioPrueba2);

            persist(new Usuario("admin", "7815696ecbf1c96e6894b779456d330e", TipoUsuario.ADMINISTRADOR)); //password: asd

            persist(MeLiApi.obtenerMoneda("ARS"));
            persist(MeLiApi.obtenerMoneda("BRL"));
            persist(MeLiApi.obtenerMoneda("EUR"));
            persist(MeLiApi.obtenerMoneda("USD"));

            persist(new MedioDePago(TipoMedioDePago.TARJETA_CREDITO, "VISA"));
            persist(new MedioDePago(TipoMedioDePago.TARJETA_CREDITO, "MASTERCARD"));
            persist(new MedioDePago(TipoMedioDePago.TARJETA_DEBITO, "VISA"));
            persist(new MedioDePago(TipoMedioDePago.TARJETA_DEBITO, "MASTERCARD"));
            persist(new MedioDePago(TipoMedioDePago.DINERO_EN_CUENTA, "MERCADOPAGO"));


            Direccion direccionBelgrano = new Direccion
                    .DireccionBuilder()
                    .calleBuild("Calle Falsa")
                    .alturaBuild(123)
                    .pisoBuild(1)
                    .departamentoBuild("A")
                    .direccionPostalBuilder("1407")
                    .buildDireccion();

            persist(direccionBelgrano.getDireccionPostal());
            persist(new Proveedor("Manuel Belgrano", 36157574, direccionBelgrano));

            Direccion direccionSanMartin = new Direccion
                    .DireccionBuilder()
                    .calleBuild("Florida")
                    .alturaBuild(555)
                    .direccionPostalBuilder("6300")
                    .buildDireccion();

            persist(direccionSanMartin.getDireccionPostal());
            persist(new Proveedor("José de San Martín", 33578415, direccionSanMartin));       	
            
        });
        
        System.out.println("Fin carga");
    }

}