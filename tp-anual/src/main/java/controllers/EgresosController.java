package controllers;

import model.documentoComercial.DocumentoComercial;
import model.documentoComercial.TipoDocumentoComercial;
import model.egreso.*;
import model.entidades.Entidad;
import model.entidades.Organizacion;
import model.mediosDePago.MedioDePago;
import model.mediosDePago.TipoMedioDePago;
import model.usuario.Usuario;
import org.joda.time.DateTime;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.*;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.*;

public class EgresosController implements WithGlobalEntityManager, TransactionalOps {

    public ModelAndView getFormCreacionEgreso(Request request, Response response){
        String idOrg = request.params(":idOrg");
        String idEntidad = request.params(":idEntidad");
        try{
            Entidad entidad = RepositorioEntidades.instancia.obtenerEntidadPorId(Long.parseLong(idEntidad));
            if(entidad == null){
                response.redirect("/error", 404); // not found
            }

            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(idOrg));

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("idEntidad", Long.parseLong(idEntidad));
            modelo.put("idOrganizacion", Long.parseLong(idOrg));
            modelo.put("mediosDePago", RepositorioMediosDePago.instancia.obtenerMediosDePago());
            modelo.put("etiquetasDisponibles", RepositorioOrganizaciones.instancia.obtenerEtiquetasDeOrganizacion(Long.parseLong(idOrg)));
            modelo.put("tiposDocumentoComercial", TipoDocumentoComercial.values());
            modelo.put("criteriosSeleccionPresupuesto", CriterioSeleccionPresupuesto.values());
            modelo.put("monedas", RepositorioMonedas.instancia.obtenerMonedas());

            return new ModelAndView(modelo, "form-creacion-egreso.html.hbs");
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }

    public Void crearEgreso(Request request, Response response){
        LocalDate fechaOperacion = LocalDate.parse(request.queryParams("fechaOperacion"));
        int numeroDocComercial = Integer.parseInt(request.queryParams("numeroDocComercial"));
        DocumentoComercial documentoComercial = new DocumentoComercial(TipoDocumentoComercial.valueOf(request.queryParams("tipo-doc-comercial")), numeroDocComercial);
        MedioDePago medioDePago = RepositorioMediosDePago.instancia.obtenerMedioDePagoPorId(Long.parseLong(request.queryParams("medioDePago")));
        Moneda moneda = RepositorioMonedas.instancia.obtenerMonedaPorId(request.queryParams("moneda"));
        String[] etiquetas = request.queryMap("etiquetas").hasValue() ? request.queryMap("etiquetas").values() : null;
        List<Usuario> revisores = new ArrayList<>();
        if(request.queryParams("revisorCheck") != null)
            revisores.add(SessionHelper.getUsuarioLogueado(request));
        boolean requierePresupuestos = request.queryParams("presupuestoCheck") != null;
        CriterioSeleccionPresupuesto criterio = null;
        if(request.queryParams("criterioSeleccionPresupuesto") != null)
             criterio = CriterioSeleccionPresupuesto.valueOf(request.queryParams("criterioSeleccionPresupuesto"));

        Egreso nuevoEgreso = new Egreso(fechaOperacion, null, documentoComercial, medioDePago, moneda, new ArrayList<Item>(), revisores, new ArrayList<Presupuesto>(), requierePresupuestos, criterio);
        if(etiquetas != null)
            Arrays.stream(etiquetas).forEach(e -> nuevoEgreso.etiquetar(e));

        withTransaction(() ->{
            entityManager().persist(documentoComercial);
            RepositorioEgresos.instancia.agregarEgreso(nuevoEgreso);
            Entidad entidad = RepositorioEntidades.instancia.obtenerEntidadPorId(Long.parseLong(request.params(":idEntidad")));
            entidad.nuevoEgreso(nuevoEgreso);
        });

        response.redirect("/organizaciones/" + request.params(":idOrg") + "/entidades/" + request.params(":idEntidad"));
        return null;
    }
}
