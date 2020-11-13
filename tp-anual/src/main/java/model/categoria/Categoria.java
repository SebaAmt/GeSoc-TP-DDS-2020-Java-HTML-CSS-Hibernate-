 package model.categoria;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.*;

import model.reglaNegocio.ReglaNegocio;
import model.egreso.Egreso;
import model.entidades.Entidad;
import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;

@Entity 
@Table (name = "Categorias")
public class Categoria {
	

	@Id
    @GeneratedValue
    Long categoria_id;
    
	@Column(name = "nombre")
	private String nombreCategoria;

	@OneToMany
	@JoinColumn(name = "categoria_id")
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
	
	public Long getId() {
		return this.categoria_id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoria_id == null) ? 0 : categoria_id.hashCode());
		result = prime * result + ((nombreCategoria == null) ? 0 : nombreCategoria.hashCode());
		result = prime * result + ((reglasNegocio == null) ? 0 : reglasNegocio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (categoria_id == null) {
			if (other.categoria_id != null)
				return false;
		} else if (!categoria_id.equals(other.categoria_id))
			return false;
		if (nombreCategoria == null) {
			if (other.nombreCategoria != null)
				return false;
		} else if (!nombreCategoria.equals(other.nombreCategoria))
			return false;
		if (reglasNegocio == null) {
			if (other.reglasNegocio != null)
				return false;
		} else if (!reglasNegocio.equals(other.reglasNegocio))
			return false;
		return true;
	}
}