package model.egreso;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import model.direccion.Direccion;


@Entity
@Table(name = "proveedores")
public class Proveedor {

	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	private Integer dni;
	@Embedded
	private Direccion direccion;

	
	public Proveedor(String nombre, Integer dni, Direccion direccion) {
		this.nombre = nombre;
		this.dni = dni;
		this.direccion = direccion;
	}
	
	public Proveedor() {
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public Integer getDni() {
		return dni;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}

}
