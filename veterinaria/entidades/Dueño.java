package veterinaria.entidades;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dueño {
	private static int ultimoId = 0;
	private int id;
	private boolean activo;
	private String nombre;
	private String teléfono;
	private String dirección;
	private String telefonoDeEmergencia;
	private String nombreDeEmergencia;
	private Set<Mascota> mascotas;
	
	
	
	public Dueño(String nombre, String teléfono, String dirección, String telefonoDeEmergencia,
			String nombreDeEmergencia) {
		super();
		this.activo = activo;
		this.nombre = nombre;
		this.teléfono = teléfono;
		this.dirección = dirección;
		this.telefonoDeEmergencia = telefonoDeEmergencia;
		this.nombreDeEmergencia = nombreDeEmergencia;
		this.activo = true;
		
		this.mascotas = new HashSet<>();
		this.id = ultimoId++;
	}

	// TODO eliminar este constructor de prueba
	public Dueño(String nombre, String teléfono) {
		this.nombre = nombre;
		this.teléfono = teléfono;

		this.mascotas = new HashSet<>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTeléfono() {
		return teléfono;
	}
	public void setTeléfono(String teléfono) {
		this.teléfono = teléfono;
	}
	public String getDirección() {
		return dirección;
	}
	public void setDirección(String dirección) {
		this.dirección = dirección;
	}
	public String getTelefonoDeEmergencia() {
		return telefonoDeEmergencia;
	}
	public void setTelefonoDeEmergencia(String telefonoDeEmergencia) {
		this.telefonoDeEmergencia = telefonoDeEmergencia;
	}
	public String getNombreDeEmergencia() {
		return nombreDeEmergencia;
	}
	public void setNombreDeEmergencia(String nombreDeEmergencia) {
		this.nombreDeEmergencia = nombreDeEmergencia;
	}
	public Set<Mascota> getMascotas() {
		return mascotas;
	}
	public void setMascotas(Set<Mascota> mascotas) {
		this.mascotas = mascotas;
	}
	public List<Mascota> getListaMascotas() {
		return new LinkedList<>(mascotas);
	}
	
}
