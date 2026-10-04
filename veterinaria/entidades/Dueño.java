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

	public Dueño(int id, boolean activo, String nombre, String teléfono, String dirección, String telefonoDeEmergencia, String nombreDeEmergencia)
	{
		this.id = id;
		this.activo = activo;
		this.nombre = nombre;
		this.teléfono = teléfono;
		this.dirección = dirección;
		this.telefonoDeEmergencia = telefonoDeEmergencia;
		this.nombreDeEmergencia = nombreDeEmergencia;
		mascotas = new HashSet<>();
	}

	public int getId() {
		return id;
	}

	public Boolean getActivo() {
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
}
