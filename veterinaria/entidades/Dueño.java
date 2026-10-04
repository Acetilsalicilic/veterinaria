package veterinaria.entidades;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.HashSet;
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

	public Dueño(String nombre, String teléfono, String dirección, String telefonoDeEmergencia, String nombreDeEmergencia)
	{
		// TODO adecuar el constructor para la persistencia
		//this.activo = activo;
		this.nombre = nombre;
		this.teléfono = teléfono;
		this.dirección = dirección;
		this.telefonoDeEmergencia = telefonoDeEmergencia;
		this.nombreDeEmergencia = nombreDeEmergencia;
		mascotas = new HashSet<>();
	}

	public List<Mascota> getListaMascotas() {
		return new LinkedList<>(mascotas);
	}

	public int getId() {
		return id;
	}

	public void setActivo(boolean v) {
		this.activo = v;
	}

	public Boolean isActivo() {
		return activo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTeléfono() {
		return teléfono;
	}

	public String getDirección() {
		return dirección;
	}

	public String getTelefonoDeEmergencia() {
		return telefonoDeEmergencia;
	}

	public String getNombreDeEmergencia() {
		return nombreDeEmergencia;
	}

	public Set<Mascota> getMascotas() {
		return mascotas;
	}

	public void agregarMascota(Mascota mascota) {
		mascotas.add(mascota);
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setTeléfono(String teléfono) {
		this.teléfono = teléfono;
	}

	public void setDirección(String dirección) {
		this.dirección = dirección;
	}

	public void setTelefonoDeEmergencia(String telefonoDeEmergencia) {
		this.telefonoDeEmergencia = telefonoDeEmergencia;
	}

	public void setNombreDeEmergencia(String nombreDeEmergencia) {
		this.nombreDeEmergencia = nombreDeEmergencia;
	}
}
