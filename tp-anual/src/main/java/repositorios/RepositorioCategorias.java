package repositorios;

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
}
