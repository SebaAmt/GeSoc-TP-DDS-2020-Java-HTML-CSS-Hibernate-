public class EntidadBase {
	private String nombreFicticio;
	private String descripcion;
	private EntidadJuridica entidadJuridica;
	private ArrayList<Egreso> egresos = new ArrayList<>();
	
	public EntidadBase(String nombreFicticio, String descripcion, EntidadJuridica entidadJUridica){
		this.nombreFicticio = nombreFicticio;
		this.descripcion = descripcion;
		this.entidadJuridica = entidadJuridica;
	}
	
	public void entidadJuridica(){
		return this.entidadJuridica;
	}
	
	public void nuevoEgreso(Egreso egreso){
        this.egresos.add(egreso);
    }

    public ArrayList<Egreso> egresos(){
    	return egresos;
    }
}
