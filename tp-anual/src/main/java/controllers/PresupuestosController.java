package controllers;

import model.documentoComercial.DocumentoComercial;
import model.documentoComercial.TipoDocumentoComercial;
import model.egreso.*;
import model.entidades.Entidad;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioEgresos;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioMonedas;
import repositorios.RepositorioProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PresupuestosController implements WithGlobalEntityManager, TransactionalOps {

    public ModelAndView getFormCreacionPresupuesto(Request request, Response response){
        String idOrg = request.params(":idOrg");
        String idEntidad = request.params(":idEntidad");
        String idEgreso = request.params(":idEgreso");
        try{
            Entidad entidad = RepositorioEntidades.instancia.obtenerEntidadPorId(Long.parseLong(idEntidad));
            if(entidad == null){
                response.redirect("/error", 404); // not found
            }

            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(idOrg));

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("idEntidad", Long.parseLong(idEntidad));
            modelo.put("idOrganizacion", Long.parseLong(idOrg));
            modelo.put("idEgreso", Long.parseLong(idEgreso));
            modelo.put("monedaEgreso", RepositorioEgresos.instancia.getEgresoPorId(Long.parseLong(idEgreso)).getMoneda());
            modelo.put("itemsEgreso", RepositorioEgresos.instancia.getEgresoPorId(Long.parseLong(idEgreso)).getItems());
            modelo.put("proveedores", RepositorioProveedores.instancia.obtenerProveedores());
            modelo.put("tiposDocumentoComercial", TipoDocumentoComercial.values());

            return new ModelAndView(modelo, "form-creacion-presupuesto.html.hbs");
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }

    public Void crearPresupuesto(Request request, Response response){
        Proveedor proveedor = RepositorioProveedores.instancia.getProveedorPorId(Long.parseLong(request.queryParams("proveedor")));
        int numeroDocComercial = Integer.parseInt(request.queryParams("numeroDocComercial"));
        DocumentoComercial documentoComercial = new DocumentoComercial(TipoDocumentoComercial.valueOf(request.queryParams("tipo-doc-comercial")), numeroDocComercial);
        Moneda moneda = RepositorioMonedas.instancia.obtenerMonedaPorId(request.queryParams("moneda"));

        Egreso egreso = RepositorioEgresos.instancia.getEgresoPorId(Long.parseLong(request.params(":idEgreso")));
        List<Item> itemsEgreso = egreso.getItems();
        List<Item> itemsPresupuesto = new ArrayList<>();
        itemsEgreso.stream().forEach(i -> {
            BigDecimal precioPresupuesto = new BigDecimal(Double.parseDouble(request.queryParams(i.getId().toString())));
            Item nuevoItemPresupuesto = new Item(i.getDescripcion(), precioPresupuesto, i.getCantidadUnidades());
            itemsPresupuesto.add(nuevoItemPresupuesto);
        });

        Presupuesto nuevoPresupuesto = new Presupuesto(proveedor, documentoComercial, moneda, itemsPresupuesto);

        withTransaction(() ->{
            entityManager().persist(documentoComercial);
            itemsPresupuesto.stream().forEach(i -> entityManager().persist(i));
            entityManager().persist(nuevoPresupuesto);
            egreso.agregarPresupuesto(nuevoPresupuesto);
        });

        response.redirect("/organizaciones/" + request.params(":idOrg") + "/entidades/" + request.params(":idEntidad") + "/egresos/" + request.params(":idEgreso"));
        return null;
    }

}
