package model.direccion;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import model.meLiApi.MeLiApi;


@Embeddable
public class Direccion {

	private String calle;
	private Integer altura;
	private Integer piso;
	private String departamento;
	@ManyToOne
	private DireccionPostal direccionPostal;

	public Direccion() {
	}
	
	public String getCalle() {
		return calle;
	}

	public Integer getAltura() {
		return altura;
	}

	public Integer getPiso() {
		return piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public DireccionPostal getDireccionPostal() {
		return direccionPostal;
	}

	public static DireccionBuilder getDireccionBuilder() {
		return new DireccionBuilder();
	}

	public static class DireccionBuilder {
		private String calle;
		private Integer altura;
		private Integer piso;
		private String departamento;
		private DireccionPostal direccionPostal;
		
		public DireccionBuilder calleBuild(String calle) {
			this.calle = calle;
			return this;
		}

		public DireccionBuilder alturaBuild(Integer altura) {
			this.altura = altura;
			return this;
		}

		public DireccionBuilder pisoBuild(Integer piso) {
			this.piso = piso;
			return this;
		}

		public DireccionBuilder departamentoBuild(String departamento) {
			this.departamento = departamento;
			return this;
		}

		public DireccionBuilder direccionPostalBuilder(String zipcode) {
			this.direccionPostal = MeLiApi.obtenerDireccionPostal("AR", zipcode);
			return this;
		}
		
		public Direccion buildDireccion() {
			Direccion direccion = new Direccion();
			direccion.calle = this.calle;
			direccion.altura = this.altura;
			direccion.piso = this.piso;
			direccion.departamento = this.departamento;
			direccion.direccionPostal = this.direccionPostal;
			return direccion;
		}
	}
}
