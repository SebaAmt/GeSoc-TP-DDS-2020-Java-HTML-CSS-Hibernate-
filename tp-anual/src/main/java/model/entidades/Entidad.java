package model.entidades;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import model.categoria.Categoria;
import model.egreso.Egreso;

import javax.persistence.*;

@Entity
@Table(name = "entidades")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_entidad")
@org.hibernate.annotations.DiscriminatorOptions(force=true) // con esto no rompe al recuperar organizaciones de la bd
public abstract class Entidad {

    @Id
    @GeneratedValue
    private Long id;
    private String nombreFicticio;
    @ManyToOne
    private Categoria categoria;
    @OneToMany
    @JoinColumn(name = "entidad_id")
    List<Egreso> egresos = new ArrayList<Egreso>();
    
    public Entidad(String nombreFicticio) {
    	this.nombreFicticio = nombreFicticio;
    }
    
    public Entidad() {
    }
    
    public void nuevoEgreso(Egreso egreso){
    	if(this.categoria != null)
    	    this.categoria.nuevoEgreso(this, egreso);

    	this.egresos.add(egreso);
    }
    
	public BigDecimal totalEgresos(){
		return egresos.stream().map(egreso -> egreso.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
    
	public abstract List<Egreso> egresosParaValidar();
	
    public List<Egreso> egresos(){
    	return egresos;
    }

    public void setCategoria(Categoria categoria) {
    	this.categoria = categoria;
    }

    public Categoria getCategoria() {
    	return this.categoria;
    }
    
    public String getNombreFicticio() {
    	return this.nombreFicticio;
    }

    public Long getId() {
        return id;
    }

    public List<Egreso> getEgresos() {
        return egresos;
    }
}
