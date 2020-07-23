package dds.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import dds.categoria.Categoria;
import dds.egreso.Egreso;

public abstract class Entidad {

    private String nombreFicticio;
    private Categoria categoria;
    List<Egreso> egresos = new ArrayList<Egreso>();
    
    public Entidad(String nombreFicticio) {
    	this.nombreFicticio = nombreFicticio;
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

}
