package repositorios;

import model.egreso.Moneda;
import model.entidades.Entidad;
import model.mediosDePago.MedioDePago;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioMediosDePago implements WithGlobalEntityManager {

    public static RepositorioMediosDePago instancia = new RepositorioMediosDePago();

    public List<MedioDePago> obtenerMediosDePago(){
        return entityManager()
                .createQuery("from MedioDePago", MedioDePago.class) //
                .getResultList();
    }

    public MedioDePago obtenerMedioDePagoPorId(Long id){
        return entityManager().find(MedioDePago.class, id);
    }
}
