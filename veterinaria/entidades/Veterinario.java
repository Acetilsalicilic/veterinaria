package veterinaria.entidades;

import java.util.List;

public class Veterinario {
	private int id;
	private boolean activo;
	private String nombre;
	private String especialidad;
	private List<Consulta> consultas;

	public Veterinario(int id, boolean activo, String nombre, String especialidad)
	{
		this.id = id;
		this.activo = activo;
		this.nombre = nombre;
		this.especialidad = especialidad;
	}

	public int getId() {
		return id;
	}

	public boolean getActivo() {
		return activo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}
}
