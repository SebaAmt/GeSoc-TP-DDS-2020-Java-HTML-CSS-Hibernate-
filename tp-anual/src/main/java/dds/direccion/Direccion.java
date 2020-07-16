package dds.direccion;

import dds.meLiApi.MeLiApi;

public class Direccion {

	private String calle;
	private Integer altura;
	private Integer piso;
	private String departamento;
	private DireccionPostal direccionPostal;

	private Direccion(String calle, Integer altura, Integer piso, String departamento,
			DireccionPostal direccionPostal) {
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.departamento = departamento;
		this.direccionPostal = direccionPostal;
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
			return new Direccion(calle, altura, piso, departamento, direccionPostal);
		}
	}
}
