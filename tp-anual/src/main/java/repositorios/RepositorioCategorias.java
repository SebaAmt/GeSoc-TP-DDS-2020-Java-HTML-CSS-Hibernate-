package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import model.categoria.Categoria;

public class RepositorioCategorias implements WithGlobalEntityManager {
	
	public static RepositorioCategorias instancia = new RepositorioCategorias();
	
    public void agregarCategoria(Categoria categoria){
        entityManager().persist(categoria);
    }
    
    public Categoria getCategoriaPorId(Long categoriaId){
        return entityManager().find(Categoria.class, categoriaId);
    }
    
    public List<Categoria> getCategorias(){
    	entityManager().getTransaction().begin();
    	List<Categoria> listaDeCategoriasSinRepetir = new ArrayList<>();
    	listaDeCategoriasSinRepetir = entityManager().createQuery("from Categoria",
    		    Categoria.class)
    			.getResultList().stream().collect(Collectors.toSet())
    			.stream().collect(Collectors.toList());
    	return listaDeCategoriasSinRepetir;
   }
}

