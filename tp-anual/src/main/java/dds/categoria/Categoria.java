 package dds.categoria;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dds.reglaNegocio.ReglaNegocio;
import dds.egreso.Egreso;
import dds.entidades.Entidad;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;

@Entity 
@Table (name = "Categorias")
public class Categoria {
	
    @Id
    @GeneratedValue
    Long categoria_id;
    
	@Column(name = "nombre")
	private String nombreCategoria;
	
	@OneToMany
	@JoinColumn (name = "categoria_id")
	private List<ReglaNegocio> reglasNegocio = new ArrayList<>();
	
	
	public Categoria(String nombreCategoria, List<ReglaNegocio> reglasNegocio) {
		this.nombreCategoria = nombreCategoria;
		this.reglasNegocio = reglasNegocio;
	}
	
	public Categoria() {
	}
	
	public void nuevoEgreso(Entidad entidad, Egreso egreso) {
		reglasNegocio.stream().forEach(c->c.nuevoEgreso(entidad, egreso));
	}

	public void agregarEntidadBase(EntidadJuridica entidad) {
		reglasNegocio.stream().forEach(c->c.agregarEntidadBase(entidad));
	}

	public void puedeAgregarse(EntidadBase entidad) {
		reglasNegocio.stream().forEachOrdered(c->c.puedeAgregarse(entidad));
	}
	
	public void agregarReglaNegocio(ReglaNegocio reglaNegocio) {
		this.reglasNegocio.add(reglaNegocio);
	}
	
	public void eliminarReglaNegocio(ReglaNegocio reglaNegocio) {
		this.reglasNegocio.remove(reglaNegocio);
	}
	
	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public List<ReglaNegocio> getReglasNegocio() {
		return reglasNegocio;
	}
}
