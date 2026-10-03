package veterinaria.entidades;

import java.util.LinkedList;
import java.util.List;

public class Veterinario {
	private static int últimoId = 0;
	private int id;
	private boolean activo;
	private String nombre;
	private String especialidad;
	private List<Consulta> consultas;

	public Veterinario(String nombre, String especialidad) {
		this.activo = true;
		this.nombre = nombre;
		this.especialidad = especialidad;

		this.consultas = new LinkedList<>();
		this.id = últimoId++;
	}

	public static int getÚltimoId() {
		return últimoId;
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

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}
}
